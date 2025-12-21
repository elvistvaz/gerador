@echo off
setlocal enabledelayedexpansion

REM ============================================================
REM  GERADOR FULL STACK (Spring Boot + Angular)
REM
REM  Uso:
REM    gerar.bat         - Usa a pasta "icep" como padrao
REM    gerar.bat xandel  - Usa a pasta "xandel"
REM    gerar.bat icep    - Usa a pasta "icep"
REM ============================================================

set MAVEN_CMD="C:\Program Files\Apache\apache-maven-3.9.11\bin\mvn.cmd"
set PROJECT_DIR=%~dp0

REM Pasta padrao: icep
set FOLDER_NAME=icep

REM Verifica se foi passado argumento
if not "%~1"=="" (
    set FOLDER_NAME=%~1
)

echo.
echo ============================================================
echo   GERADOR FULL STACK - Spring Boot + Angular
echo ============================================================
echo.
echo Pasta do modelo: %FOLDER_NAME%
echo.
echo ------------------------------------------------------------
echo   Compilando o projeto...
echo ------------------------------------------------------------

%MAVEN_CMD% -f "%PROJECT_DIR%pom.xml" clean compile -q

if errorlevel 1 (
    echo.
    echo [ERRO] Falha na compilacao do projeto!
    pause
    exit /b 1
)

echo.
echo ------------------------------------------------------------
echo   Executando o gerador...
echo ------------------------------------------------------------

%MAVEN_CMD% -f "%PROJECT_DIR%pom.xml" exec:java -Dexec.mainClass="br.com.gerador.generator.FullStackGeneratorMain" -Dexec.args="%FOLDER_NAME%" -q

if errorlevel 1 (
    echo.
    echo [ERRO] Falha na execucao do gerador!
    pause
    exit /b 1
)

echo.
echo ============================================================
echo   Geracao concluida com sucesso!
echo ============================================================
echo.

pause
