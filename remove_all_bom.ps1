$csvFolder = 'c:\java\workspace\Gerador\metamodel\data\icep\carga-inicial'
$utf8NoBom = New-Object System.Text.UTF8Encoding($false)

Get-ChildItem -Path $csvFolder -Filter "*.csv" | ForEach-Object {
    $filePath = $_.FullName
    $content = [System.IO.File]::ReadAllText($filePath)
    $newContent = $content.TrimStart([char]0xFEFF)
    if ($content -ne $newContent) {
        [System.IO.File]::WriteAllText($filePath, $newContent, $utf8NoBom)
        Write-Host "BOM removido: $($_.Name)"
    }
}
Write-Host "Concluido!"
