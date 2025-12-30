try {
    Write-Host "Testing login page..."
    $response = Invoke-WebRequest -Uri 'http://127.0.0.1:8000/login' -UseBasicParsing -ErrorAction Stop

    if ($response.StatusCode -eq 200) {
        Write-Host "SUCCESS: Login page loaded (Status: $($response.StatusCode))"

        # Check if there are any errors in the page
        if ($response.Content -match 'MissingAppKeyException|UrlGenerationException|Missing parameter') {
            Write-Host "ERROR: Found errors in the page content"
            $response.Content | Select-String -Pattern 'Exception|Missing|Error' | Select-Object -First 5
        } else {
            Write-Host "Page loaded without errors"
        }
    }
} catch {
    Write-Host "ERROR: $($_.Exception.Message)"
    if ($_.Exception.Response) {
        Write-Host "Status Code: $($_.Exception.Response.StatusCode.value__)"
    }
}
