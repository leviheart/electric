# 测试变电站API数据
$response = Invoke-WebRequest -Uri 'http://localhost:8080/api/substations' -UseBasicParsing
$content = $response.Content
$data = $content | ConvertFrom-Json

Write-Host "API响应:"
$content | ConvertFrom-Json | ConvertTo-Json -Depth 3

if ($data.data -and $data.data.Count -gt 0) {
    Write-Host "\n变电站数据详情:"
    for ($i = 0; $i -lt $data.data.Count; $i++) {
        $substation = $data.data[$i]
        Write-Host "\n变电站$($i + 1):"
        Write-Host "  名称: $($substation.name)"
        Write-Host "  ID: $($substation.id)"
        Write-Host "  经度: $($substation.longitude) (类型: $($substation.longitude.GetType()))"
        Write-Host "  纬度: $($substation.latitude) (类型: $($substation.latitude.GetType()))"
        Write-Host "  电压等级: $($substation.voltageLevel)"
        Write-Host "  状态: $($substation.status)"
    }
}
