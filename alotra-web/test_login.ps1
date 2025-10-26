# Test login script for debugging
# Sử dụng PowerShell để test đăng nhập

# Thông tin đăng nhập test
$loginData = @{
    usernameOrEmail = "admin"
    password = "123456"
    rememberMe = $false
}

# Chuyển đổi sang JSON
$jsonData = $loginData | ConvertTo-Json

Write-Host "Testing login with data: $jsonData"

try {
    # Thực hiện request đăng nhập
    $response = Invoke-WebRequest -Uri "http://localhost:8080/admin/login" -Method POST -Body $loginData -ContentType "application/x-www-form-urlencoded" -UseBasicParsing
    
    Write-Host "Response Status: $($response.StatusCode)"
    Write-Host "Response Headers: $($response.Headers)"
    Write-Host "Response Content (first 500 chars): $($response.Content.Substring(0, [Math]::Min(500, $response.Content.Length)))"
    
} catch {
    Write-Host "Error occurred: $($_.Exception.Message)"
    Write-Host "Response: $($_.Exception.Response)"
}