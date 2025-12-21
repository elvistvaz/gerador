$filePath = 'c:\java\workspace\Gerador\metamodel\data\icep\carga-inicial\carga_horaria_formacao.csv'
$content = [System.IO.File]::ReadAllText($filePath)
$content = $content.TrimStart([char]0xFEFF)
$utf8NoBom = New-Object System.Text.UTF8Encoding($false)
[System.IO.File]::WriteAllText($filePath, $content, $utf8NoBom)
Write-Host 'BOM removido com sucesso'
