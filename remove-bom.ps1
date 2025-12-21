$csvPath = 'c:\java\workspace\Gerador\metamodel\data\icep\carga-inicial\avaliacao_indicador.csv'
$bytes = [System.IO.File]::ReadAllBytes($csvPath)
if ($bytes[0] -eq 0xEF -and $bytes[1] -eq 0xBB -and $bytes[2] -eq 0xBF) {
    $newBytes = $bytes[3..($bytes.Length-1)]
    [System.IO.File]::WriteAllBytes($csvPath, [byte[]]$newBytes)
    Write-Host 'BOM removido com sucesso'
} else {
    Write-Host 'Arquivo nao possui BOM'
}
