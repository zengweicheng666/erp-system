# ERP System Setup Script
# This script will install Maven and set up the project

Write-Host "=== ERP System Setup ===" -ForegroundColor Green

# Check Java
$javaVersion = java -version 2>&1
if ($LASTEXITCODE -ne 0) {
    Write-Host "Java is not installed. Please install JDK 17+." -ForegroundColor Red
    exit 1
}
Write-Host "Java: OK" -ForegroundColor Green

# Install Maven via winget if available
if (Get-Command winget -ErrorAction SilentlyContinue) {
    Write-Host "Installing Maven via winget..." -ForegroundColor Yellow
    winget install Apache.Maven -e --accept-package-agreements
    if ($?) {
        $env:Path = [Environment]::GetEnvironmentVariable("Path", "Machine") + ";" + [Environment]::GetEnvironmentVariable("Path", "User")
    }
}

# If mvn still not found, try chocolatey
if (-not (Get-Command mvn -ErrorAction SilentlyContinue)) {
    if (Get-Command choco -ErrorAction SilentlyContinue) {
        Write-Host "Installing Maven via Chocolatey..." -ForegroundColor Yellow
        choco install maven -y
    }
}

# If still not found, download manually
if (-not (Get-Command mvn -ErrorAction SilentlyContinue)) {
    Write-Host "Downloading Maven..." -ForegroundColor Yellow
    $mavenVersion = "3.9.6"
    $url = "https://dlcdn.apache.org/maven/maven-3/$mavenVersion/binaries/apache-maven-$mavenVersion-bin.zip"
    $zipPath = "$env:TEMP\apache-maven-$mavenVersion-bin.zip"
    
    try {
        Invoke-WebRequest -Uri $url -OutFile $zipPath -UseBasicParsing -ErrorAction Stop
    } catch {
        # Try alternative URL
        $url = "https://archive.apache.org/dist/maven/maven-3/$mavenVersion/binaries/apache-maven-$mavenVersion-bin.zip"
        Invoke-WebRequest -Uri $url -OutFile $zipPath -UseBasicParsing -ErrorAction Stop
    }
    
    $extractPath = "$env:ProgramFiles\Maven"
    Expand-Archive -Path $zipPath -DestinationPath $extractPath -Force
    $mvnDir = Get-ChildItem $extractPath -Directory | Select-Object -First 1
    
    # Add to PATH
    [Environment]::SetEnvironmentVariable("Path", "$env:Path;$($mvnDir.FullName)\bin", "User")
    $env:Path += ";$($mvnDir.FullName)\bin"
    Write-Host "Maven installed to $($mvnDir.FullName)" -ForegroundColor Green
}

# Build backend
Write-Host "Building backend..." -ForegroundColor Yellow
Set-Location "$PSScriptRoot\erp-backend"
mvn clean install -DskipTests

if ($?) {
    Write-Host "Backend build successful!" -ForegroundColor Green
} else {
    Write-Host "Backend build failed. Please check errors above." -ForegroundColor Red
}

# Build frontend
Write-Host "Building frontend..." -ForegroundColor Yellow
Set-Location "$PSScriptRoot\erp-web"
npm install
npm run build

if ($?) {
    Write-Host "Frontend build successful!" -ForegroundColor Green
} else {
    Write-Host "Frontend build failed. Please check errors above." -ForegroundColor Red
}

Write-Host "=== Setup Complete ===" -ForegroundColor Green
Write-Host ""
Write-Host "To start the system:" -ForegroundColor Cyan
Write-Host "1. Start Docker MySQL: docker-compose up -d" -ForegroundColor White
Write-Host "2. Start backend: cd erp-backend && mvn spring-boot:run -pl erp-admin" -ForegroundColor White
Write-Host "3. Start frontend: cd erp-web && npm run dev" -ForegroundColor White
Write-Host ""
Write-Host "Default login: admin / 123456" -ForegroundColor Yellow
