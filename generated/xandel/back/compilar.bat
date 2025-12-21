@echo off
echo ============================================
echo Compilando xandel...
echo ============================================
echo.

REM Verifica se o Maven esta instalado
where mvn >nul 2>nul
if %ERRORLEVEL% NEQ 0 (
    echo ERRO: Maven nao encontrado no PATH.
    echo Instale o Maven ou adicione-o ao PATH.
    pause
    exit /b 1
)

REM Compila o projeto
call mvn clean compile -DskipTests

if %ERRORLEVEL% EQU 0 (
    echo.
    echo ============================================
    echo Compilacao concluida com sucesso!
    echo ============================================
) else (
    echo.
    echo ============================================
    echo ERRO na compilacao!
    echo ============================================
)

pause
