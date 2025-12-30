@echo off
cls
echo ================================================================
echo   EXECUTAR ICEP - Laravel Server
echo ================================================================
echo.

REM Define o diretorio do projeto
set "PROJECT_DIR=%~dp0generated\icep-laravel"

REM Verifica se o projeto existe
if not exist "%PROJECT_DIR%" (
    echo   ERRO: Projeto nao encontrado!
    echo.
    echo   Execute primeiro: gerar_icep.bat
    echo.
    pause
    exit /b 1
)

echo [1/3] Parando servidor anterior (se estiver rodando)...
for /f "tokens=5" %%a in ('netstat -ano ^| findstr :8000 2^>nul') do (
    echo   - Matando processo %%a na porta 8000...
    taskkill /F /PID %%a >nul 2>&1
)
echo   OK - Porta 8000 liberada
echo.

echo [2/3] Limpando caches Laravel...
cd /d "%PROJECT_DIR%"
C:\php82\php.exe artisan config:clear >nul 2>&1
C:\php82\php.exe artisan cache:clear >nul 2>&1
C:\php82\php.exe artisan route:clear >nul 2>&1
C:\php82\php.exe artisan view:clear >nul 2>&1
echo   OK - Caches limpos
echo.

echo [3/3] Iniciando servidor Laravel...
echo.
echo ================================================================
echo   SERVIDOR INICIADO!
echo ================================================================
echo.
echo   URL: http://localhost:8000
echo.
echo   Usuario padrao:
echo     Email: admin@admin.com
echo     Senha: admin
echo.
echo   Pressione Ctrl+C para parar o servidor
echo ================================================================
echo.

REM Inicia o servidor usando PHP built-in server
C:\php82\php.exe -S 127.0.0.1:8000 -t public
