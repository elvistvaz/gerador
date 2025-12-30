@echo off
setlocal

REM =====================================================
REM  Gerador Unificado - MetaModel Framework
REM =====================================================

if %%1 ==  (
    echo Uso: gerar.bat [projeto]
    echo.
    echo Exemplos:
    echo   gerar.bat xandel    - Gera automaticamente baseado na configuracao
    echo   gerar.bat icep      - Gera automaticamente baseado na configuracao
    echo.
    echo O tipo de geracao Spring Boot ou Laravel eh detectado
    echo automaticamente pelo arquivo de configuracao na pasta.
    echo.
    exit /b 1
)

set PROJECT=%%1

echo.
echo =====================================================
echo  Iniciando Geracao para: %%PROJECT%%
echo =====================================================
echo.

C:\Program Files\Apache\apache-maven-3.9.11\bin\mvn.cmd -f c:\java\workspace\Gerador\pom.xml compile exec:java -Dexec.mainClass=br.com.gerador.generator.UnifiedGeneratorMain -Dexec.args=%%PROJECT%% -q

echo.
echo =====================================================
echo  Geracao Concluida\!
echo =====================================================
echo.

endlocal
