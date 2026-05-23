# ERP System Startup Script
$ErrorActionPreference = "Continue"

Write-Host "========================================" -ForegroundColor Green
Write-Host "    ERP System Startup" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Green
Write-Host ""

$rootDir = Split-Path -Parent $MyInvocation.MyCommand.Path
$backendDir = Join-Path $rootDir "erp-backend"
$frontendDir = Join-Path $rootDir "erp-web"

# 1. Check MySQL
Write-Host "[1/3] Checking MySQL..." -ForegroundColor Yellow
$mysqlRunning = $false
try {
    $result = & mysqladmin -u root -proot ping 2>&1
    if ($LASTEXITCODE -eq 0) {
        Write-Host "  MySQL is running" -ForegroundColor Green
        $mysqlRunning = $true
    }
} catch {}

if (-not $mysqlRunning) {
    Write-Host "  Trying Docker..." -ForegroundColor Yellow
    docker compose -f (Join-Path $rootDir "docker-compose.yml") up -d 2>&1 | Out-Null
    if ($LASTEXITCODE -eq 0) {
        Write-Host "  MySQL started via Docker" -ForegroundColor Green
    } else {
        Write-Host "  Docker unavailable, please ensure MySQL is running manually" -ForegroundColor Red
    }
}

# 2. Start Backend
Write-Host ""
Write-Host "[2/3] Starting backend (port 8080)..." -ForegroundColor Yellow
if (-not (Get-Command mvn -ErrorAction SilentlyContinue)) {
    Write-Host "  Maven not found, using Maven Wrapper..." -ForegroundColor Yellow
}

Set-Location $backendDir
$backendJob = Start-Job -Name "ERP-Backend" -ScriptBlock {
    Set-Location $using:backendDir
    $env:JAVA_HOME = if (Test-Path "D:\Program Files\Java\jdk-17.0.1") { "D:\Program Files\Java\jdk-17.0.1" } else { $env:JAVA_HOME }
    if ($env:JAVA_HOME) {
        & "$env:JAVA_HOME\bin\java.exe" "-Dmaven.multiModuleProjectDirectory=$PWD" -classpath "$PWD\.mvn\wrapper\maven-wrapper.jar" org.apache.maven.wrapper.MavenWrapperMain spring-boot:run -pl erp-admin 2>&1
    } else {
        mvn spring-boot:run -pl erp-admin 2>&1
    }
} | Out-Null

# 3. Start Frontend
Write-Host ""
Write-Host "[3/3] Starting frontend (port 3000)..." -ForegroundColor Yellow
Set-Location $frontendDir
if (-not (Test-Path "node_modules")) {
    Write-Host "  Installing dependencies..." -ForegroundColor Yellow
    npm install 2>&1 | Out-Null
}

$frontendJob = Start-Job -Name "ERP-Frontend" -ScriptBlock {
    Set-Location $using:frontendDir
    npm run dev 2>&1
} | Out-Null

# Wait for startup
Write-Host ""
Write-Host "Waiting for services to start..." -ForegroundColor Yellow
Start-Sleep -Seconds 10

Write-Host ""
Write-Host "========================================" -ForegroundColor Green
Write-Host "  Backend:  http://localhost:8080" -ForegroundColor Cyan
Write-Host "  Frontend: http://localhost:3000" -ForegroundColor Cyan  
Write-Host "  API Docs: http://localhost:8080/doc.html" -ForegroundColor Cyan
Write-Host "  Login:    admin / 123456" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Green
Write-Host ""
Write-Host "Press Ctrl+C to stop all services" -ForegroundColor Gray

# Open browser
Start-Process "http://localhost:3000"

# Wait for Ctrl+C
try {
    while ($true) {
        Start-Sleep -Seconds 1
        if ([Console]::KeyAvailable) {
            $key = [Console]::ReadKey($true)
            if ($key.Key -eq "C" -and $key.Modifiers -eq "Control") { break }
        }
        if ($backendJob.State -eq "Failed") { break }
    }
} finally {
    Write-Host "`nShutting down..." -ForegroundColor Yellow
    Stop-Job -Name "ERP-Backend" -ErrorAction SilentlyContinue
    Stop-Job -Name "ERP-Frontend" -ErrorAction SilentlyContinue
    Remove-Job -Name "ERP-Backend" -Force -ErrorAction SilentlyContinue
    Remove-Job -Name "ERP-Frontend" -Force -ErrorAction SilentlyContinue
    Write-Host "Stopped." -ForegroundColor Green
}
