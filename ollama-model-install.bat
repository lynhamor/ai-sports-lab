@echo off

cd /d "%~dp0"

echo ==========================
echo Starting Docker Compose...
echo ==========================

docker compose up -d

echo.
echo Waiting for Ollama...

:check
docker exec ollama ollama list > nul 2>&1
IF %ERRORLEVEL% NEQ 0 (
    timeout /t 2 > nul
    goto check
)

echo.
echo Pulling models...

docker exec ollama ollama pull llama3.1

echo.
echo Done.
pause