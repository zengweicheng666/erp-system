@echo off
chcp 65001 >nul
echo ========================================
echo     ERP System Startup
echo ========================================
echo.

set BACKEND_DIR=%~dp0erp-backend
set FRONTEND_DIR=%~dp0erp-web
set JAVA_HOME=D:\Program Files\Java\jdk-17.0.1

echo [1/3] Checking MySQL...
mysqladmin -u root -proot ping >nul 2>&1
if %errorlevel% neq 0 (
    echo   MySQL not responding, trying Docker...
    docker compose -f "%~dp0docker-compose.yml" up -d 2>nul
    if %errorlevel% neq 0 (
        echo   Docker not available, please start MySQL manually
    ) else (
        echo   MySQL started via Docker
    )
) else (
    echo   MySQL is running
)

echo.
echo [2/3] Starting backend (port 8080)...
cd /d "%BACKEND_DIR%"
start "ERP Backend" /MIN "%JAVA_HOME%\bin\java.exe" -Dmaven.multiModuleProjectDirectory="%CD%" -classpath "%CD%\.mvn\wrapper\maven-wrapper.jar" org.apache.maven.wrapper.MavenWrapperMain spring-boot:run -pl erp-admin

echo.
echo [3/3] Starting frontend (port 3000)...
cd /d "%FRONTEND_DIR%"
if not exist "node_modules" (
    echo   Installing dependencies...
    call npm install
)
start "ERP Frontend" /MIN cmd /c "npm run dev"

echo.
echo ========================================
echo   Backend:  http://localhost:8080
echo   Frontend: http://localhost:3000
echo   API Docs: http://localhost:8080/doc.html
echo   Login:    admin / 123456
echo ========================================
echo   Backend takes ~10s to start...
echo ========================================
echo.

timeout /t 8 /nobreak >nul
start http://localhost:3000
