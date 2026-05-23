@echo off
setlocal

set MVNW_REPOURL=https://repo1.maven.org/maven2/org/apache/maven/
set WRAPPER_JAR=%DIR%\.mvn\wrapper\maven-wrapper.jar

if exist "%JAVA_HOME%\bin\java.exe" (
  set JAVA_EXE=%JAVA_HOME%\bin\java.exe
) else (
  set JAVA_EXE=java
)

"%JAVA_EXE%" ^
  -Dmaven.multiModuleProjectDirectory="%CD%" ^
  -Dmaven.wrapper.http.connectionTimeout=120000 ^
  -jar "%WRAPPER_JAR%" ^
  %*

if errorlevel 1 exit /b 1

endlocal
