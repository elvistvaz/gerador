@echo off
setlocal enabledelayedexpansion

echo ============================================
echo Executando projeto Angular...
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
    pause
    exit /b 1
)

REM Verifica se node_modules existe
if not exist "node_modules" (
    echo AVISO: Dependencias nao instaladas.
    echo Execute instalar.bat primeiro.
    echo.
    set /p "resposta=Deseja instalar agora? (S/N): "
    if /i "!resposta!"=="S" (
        call instalar.bat
        if errorlevel 1 (
            echo Falha na instalacao.
            pause
            exit /b 1
        )
    ) else (
        echo Instalacao cancelada.
        pause
        exit /b 1
    )
)

echo Iniciando servidor de desenvolvimento...
echo.
echo A aplicacao estara disponivel em: http://localhost:4200
echo Pressione Ctrl+C para parar o servidor.
echo.

REM Inicia o servidor Angular
call npm start
