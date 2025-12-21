@echo off
echo ============================================
echo Instalando dependencias do projeto Angular...
echo ============================================
echo.

REM Verifica se o Node.js esta instalado
where node >nul 2>nul
if %ERRORLEVEL% NEQ 0 (
    echo ERRO: Node.js nao encontrado no PATH.
    echo Instale o Node.js em https://nodejs.org/
    pause
    exit /b 1
)

REM Verifica se o npm esta instalado
where npm >nul 2>nul
if %ERRORLEVEL% NEQ 0 (
    echo ERRO: npm nao encontrado no PATH.
    echo O npm geralmente vem com o Node.js.
    pause
    exit /b 1
)

REM Exibe versões
echo Versoes instaladas:
node --version
npm --version
echo.

REM Instala as dependencias
echo Instalando dependencias (isso pode levar alguns minutos)...
call npm install

if %ERRORLEVEL% EQU 0 (
    echo.
    echo ============================================
    echo Instalacao concluida com sucesso!
    echo ============================================
    echo.
    echo Para executar o projeto, use: executar.bat
) else (
    echo.
    echo ============================================
    echo ERRO na instalacao!
    echo ============================================
)

pause
