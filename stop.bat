@echo off
chcp 65001 >nul
echo Stopping ERP System...

echo Killing backend (port 8080)...
for /f "tokens=5" %%a in ('netstat -ano ^| findstr :8080 ^| findstr LISTENING') do (
    taskkill /F /PID %%a >nul 2>&1
    echo   Killed PID %%a
)

echo Killing frontend (port 3000)...
for /f "tokens=5" %%a in ('netstat -ano ^| findstr :3000 ^| findstr LISTENING') do (
    taskkill /F /PID %%a >nul 2>&1
    echo   Killed PID %%a
)

echo Done.
pause
