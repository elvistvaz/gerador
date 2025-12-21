@echo off
echo ============================================
echo Iniciando xandel...
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

echo Servidor iniciando na porta 8080...
echo.
echo Acesse: http://localhost:8080/api/swagger-ui.html
echo.
echo Pressione Ctrl+C para parar o servidor.
echo ============================================
echo.

REM Executa o Spring Boot
call mvn spring-boot:run

pause
