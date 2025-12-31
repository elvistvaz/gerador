@echo off
echo ================================================================
echo   GERADOR XANDEL - Laravel Full Stack
echo ================================================================
echo.
echo   Inicio: %TIME%
echo.

REM ==============================================================
REM [1/8] Parando servidor Laravel (se estiver rodando)
REM ==============================================================
echo [1/8] Parando servidor Laravel (se estiver rodando)...
for /f "tokens=5" %%a in ('netstat -ano ^| findstr :8888 2^>nul') do (
    echo   - Matando processo %%a na porta 8888...
    taskkill /F /PID %%a >nul 2>&1
)
echo   OK - Porta 8888 liberada [%TIME%]
echo.

REM ==============================================================
REM [2/8] Removendo projeto anterior
REM ==============================================================
echo [2/8] Removendo projeto anterior...
if exist "generated\xandel-laravel" (
    rmdir /S /Q "generated\xandel-laravel"
    echo   OK - Projeto anterior removido [%TIME%]
) else (
    echo   - Nenhum projeto anterior encontrado [%TIME%]
)
echo.

REM ==============================================================
REM [3/8] Compilando gerador
REM ==============================================================
echo [3/8] Compilando gerador...
call "C:\Program Files\Apache\apache-maven-3.9.11\bin\mvn.cmd" -f pom.xml clean compile -q
if errorlevel 1 (
    echo   ERRO na compilacao!
    pause
    exit /b 1
)
echo   OK - Compilacao concluida [%TIME%]
echo.

REM ==============================================================
REM [4/8] Gerando projeto XANDEL Laravel
REM ==============================================================
echo [4/8] Gerando projeto XANDEL Laravel...
call "C:\Program Files\Apache\apache-maven-3.9.11\bin\mvn.cmd" -f pom.xml exec:java -Dexec.mainClass="br.com.gerador.generator.UnifiedGeneratorMain" -Dexec.args="xandel" -q
if errorlevel 1 (
    echo   ERRO na geracao!
    pause
    exit /b 1
)
echo   OK - Projeto gerado [%TIME%]
echo.

REM ==============================================================
REM [5/8] Instalando dependencias do Laravel (composer install)
REM ==============================================================
echo [5/8] Instalando dependencias do Laravel (composer install)...
cd generated\xandel-laravel
C:\php82\php.exe C:\php82\composer.phar install --no-interaction --prefer-dist --quiet
if errorlevel 1 (
    echo   ERRO: Falha ao instalar dependencias
    cd ..\..
    pause
    exit /b 1
)
echo   OK - Dependencias instaladas [%TIME%]
echo.

REM ==============================================================
REM [6/8] Gerando chave de criptografia (APP_KEY)
REM ==============================================================
echo [6/8] Gerando chave de criptografia (APP_KEY)...
C:\php82\php.exe artisan key:generate --force >nul 2>&1
if errorlevel 1 (
    echo   AVISO: Erro ao gerar APP_KEY
) else (
    echo   OK - APP_KEY gerada [%TIME%]
)
echo.

REM ==============================================================
REM [7/8] Configurando banco de dados SQLite
REM ==============================================================
echo [7/8] Configurando banco de dados SQLite...
if not exist "database" mkdir database
if not exist "database\database.sqlite" (
    type nul > database\database.sqlite
    echo   OK - Banco SQLite criado [%TIME%]
)
echo.

REM ==============================================================
REM [8/8] Executando migrations e carga inicial
REM ==============================================================
echo [8/8] Executando migrations...
C:\php82\php.exe artisan migrate --force 2>nul
if errorlevel 1 (
    echo   AVISO: Erro nas migrations (isso e normal na primeira execucao)
) else (
    echo   OK - Migrations executadas [%TIME%]
)

echo.
echo   Carregando dados iniciais (194 registros)...
C:\php82\php.exe artisan db:seed --class=InitialDataSeeder --force 2>nul
if errorlevel 1 (
    echo   AVISO: Erro ao carregar dados
) else (
    echo   OK - Dados iniciais carregados [%TIME%]
)
echo.

cd ..\..

echo.
echo ================================================================
echo   GERACAO CONCLUIDA COM SUCESSO!
echo ================================================================
echo.
echo   Finalizado: %TIME%
echo.
echo   Projeto gerado em: generated\xandel-laravel
echo.
echo   Para executar o projeto, use:
echo     executar_xandel.bat
echo.
pause
