import requests

# 测试变电站API
print("测试 /api/substations API:")
try:
    response = requests.get('http://localhost:8080/api/substations')
    print(f"状态码: {response.status_code}")
    print(f"响应内容: {response.text}")
except Exception as e:
    print(f"错误: {e}")

print("\n测试 /api/transmission-lines API:")
try:
    response = requests.get('http://localhost:8080/api/transmission-lines')
    print(f"状态码: {response.status_code}")
    print(f"响应内容: {response.text}")
except Exception as e:
    print(f"错误: {e}")

print("\n测试 /api/areas API:")
try:
    response = requests.get('http://localhost:8080/api/areas')
    print(f"状态码: {response.status_code}")
    print(f"响应内容: {response.text}")
except Exception as e:
    print(f"错误: {e}")
