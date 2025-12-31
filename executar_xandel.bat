@echo off
cls
echo ================================================================
echo   EXECUTAR XANDEL - Laravel Server
echo ================================================================
echo.

REM Define o diretorio do projeto
set "PROJECT_DIR=%~dp0generated\xandel-laravel"

REM Verifica se o projeto existe
if not exist "%PROJECT_DIR%" (
    echo   ERRO: Projeto nao encontrado!
    echo.
    echo   Execute primeiro: gerador_xandel.bat
    echo.
    pause
    exit /b 1
)

echo [1/3] Parando servidor anterior (se estiver rodando)...
for /f "tokens=5" %%a in ('netstat -ano ^| findstr :8888 2^>nul') do (
    echo   - Matando processo %%a na porta 8888...
    taskkill /F /PID %%a >nul 2>&1
)
echo   OK - Porta 8888 liberada
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
echo   URL: http://localhost:8888
echo.
echo   Usuario padrao:
echo     Email: admin@admin.com
echo     Senha: admin
echo.
echo   Pressione Ctrl+C para parar o servidor
echo ================================================================
echo.

REM Inicia o servidor usando PHP built-in server
C:\php82\php.exe -S 127.0.0.1:8888 -t public
