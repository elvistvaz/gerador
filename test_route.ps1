try {
    $response = Invoke-WebRequest -Uri 'http://127.0.0.1:8000/bancos' -UseBasicParsing -ErrorAction Stop
    Write-Host "Status: $($response.StatusCode)"

    if ($response.Content -match 'Missing parameter') {
        Write-Host "ERROR: Route error still exists"
        exit 1
    } elseif ($response.Content -match 'bancos\.edit') {
        Write-Host "SUCCESS: Page loaded with edit route"
        exit 0
    } else {
        Write-Host "INFO: Page loaded"
        exit 0
    }
} catch {
    Write-Host "ERROR: $($_.Exception.Message)"
    exit 1
}
