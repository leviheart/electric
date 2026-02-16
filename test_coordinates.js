// 测试API返回的数据格式，特别是坐标数据
const fetch = require('node-fetch');

async function testApiData() {
  try {
    console.log('测试变电站API数据...');
    const response = await fetch('http://localhost:8080/api/substations');
    const data = await response.json();
    
    console.log('API响应:', JSON.stringify(data, null, 2));
    
    if (data.data && Array.isArray(data.data)) {
      console.log('\n变电站数据详情:');
      data.data.forEach((substation, index) => {
        console.log(`\n变电站${index + 1}:`);
        console.log(`  名称: ${substation.name}`);
        console.log(`  ID: ${substation.id}`);
        console.log(`  经度: ${substation.longitude} (类型: ${typeof substation.longitude})`);
        console.log(`  纬度: ${substation.latitude} (类型: ${typeof substation.latitude})`);
        console.log(`  电压等级: ${substation.voltageLevel}`);
        console.log(`  状态: ${substation.status}`);
      });
    }
  } catch (error) {
    console.error('测试失败:', error);
  }
}

testApiData();
