# ERP System Startup (PowerShell)
$ErrorActionPreference = "Continue"
$rootDir = Split-Path -Parent $MyInvocation.MyCommand.Path
$backendDir = Join-Path $rootDir "erp-backend"
$frontendDir = Join-Path $rootDir "erp-web"
$javaHome = if (Test-Path "D:\Program Files\Java\jdk-17.0.1") { "D:\Program Files\Java\jdk-17.0.1" } else { $env:JAVA_HOME }

Write-Host "========================================" -ForegroundColor Green
Write-Host "    ERP System Startup" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Green

# 1. MySQL
Write-Host "`n[1/3] Checking MySQL..." -ForegroundColor Yellow
try { & mysqladmin -u root -proot ping 2>&1 | Out-Null; if ($LASTEXITCODE -eq 0) { Write-Host "  MySQL is running" -ForegroundColor Green } else { throw } } catch {
    Write-Host "  Trying Docker..." -ForegroundColor Yellow
    docker compose -f (Join-Path $rootDir "docker-compose.yml") up -d 2>&1 | Out-Null
    if ($LASTEXITCODE -eq 0) { Write-Host "  MySQL started via Docker" -ForegroundColor Green }
    else { Write-Host "  MySQL unavailable - start it manually" -ForegroundColor Red }
}

# 2. Backend
Write-Host "`n[2/3] Starting backend (port 8080)..." -ForegroundColor Yellow
$jb = Start-Job -Name "erp-backend" -ArgumentList $backendDir, $javaHome -ScriptBlock {
    param($dir, $jh); Set-Location $dir
    & "$jh\bin\java.exe" "-Dmaven.multiModuleProjectDirectory=$pwd" -classpath "$pwd\.mvn\wrapper\maven-wrapper.jar" org.apache.maven.wrapper.MavenWrapperMain spring-boot:run -pl erp-admin 2>&1
}

# 3. Frontend
Write-Host "`n[3/3] Starting frontend (port 3000)..." -ForegroundColor Yellow
$jf = Start-Job -Name "erp-frontend" -ArgumentList $frontendDir -ScriptBlock {
    param($dir); Set-Location $dir
    if (-not (Test-Path "node_modules")) { npm install 2>&1 | Out-Null }
    npm run dev 2>&1
}

Start-Sleep -Seconds 10

Write-Host "`n========================================" -ForegroundColor Green
Write-Host "  Backend:  http://localhost:8080" -ForegroundColor Cyan
Write-Host "  Frontend: http://localhost:3000" -ForegroundColor Cyan
Write-Host "  API Docs: http://localhost:8080/doc.html" -ForegroundColor Cyan
Write-Host "  Login:    admin / 123456" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Green
Write-Host "`nPress Ctrl+C to stop all services" -ForegroundColor Gray

Start-Process "http://localhost:3000"

try { while ($true) { Start-Sleep -Seconds 1 } }
finally {
    Write-Host "`nShutting down..." -ForegroundColor Yellow
    Stop-Job -Name "erp-backend","erp-frontend" -ErrorAction SilentlyContinue
    Remove-Job -Name "erp-backend","erp-frontend" -Force -ErrorAction SilentlyContinue
    Write-Host "Stopped." -ForegroundColor Green
}
