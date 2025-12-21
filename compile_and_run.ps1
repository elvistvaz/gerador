Set-Location "c:\java\workspace\Gerador"

# Classpath with Gson
$gsonJar = "C:\Users\suporte\.m2\repository\com\google\code\gson\gson\2.10.1\gson-2.10.1.jar"

# Create target directory
if (-not (Test-Path "target\classes")) {
    New-Item -ItemType Directory -Path "target\classes" -Force | Out-Null
}

Write-Host "Compilando arquivos Java..."

# Get all Java files
$javaFiles = Get-ChildItem -Path "src\main\java" -Filter "*.java" -Recurse | Select-Object -ExpandProperty FullName

# Create sources file
$javaFiles | Out-File -FilePath "sources.txt" -Encoding ASCII

Write-Host "Total de arquivos: $($javaFiles.Count)"

# Compile with classpath
javac -d target/classes -encoding UTF-8 -cp "$gsonJar" "@sources.txt" 2>&1

if ($LASTEXITCODE -eq 0) {
    Write-Host ""
    Write-Host "Compilacao bem sucedida! Executando gerador..."
    Write-Host ""

    # Run with classpath
    java -cp "target/classes;$gsonJar" br.com.gerador.generator.GeneratorMain
} else {
    Write-Host "Erro na compilacao"
}
