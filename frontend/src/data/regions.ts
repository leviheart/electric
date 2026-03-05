export interface Region {
  code: string
  adcode: string
  name: string
  level: 'country' | 'province' | 'city' | 'county'
  center: [number, number]
  zoom: number
  children?: Region[]
}

export const regionData: Region = {
  code: 'CN',
  adcode: '100000',
  name: '全网',
  level: 'country',
  center: [104.1954, 35.8617],
  zoom: 4,
  children: [
    {
      code: 'beijing',
      adcode: '110000',
      name: '北京市',
      level: 'province',
      center: [116.4074, 39.9042],
      zoom: 9,
      children: [
        { code: 'beijing-dongcheng', adcode: '110101', name: '东城区', level: 'county', center: [116.4187, 39.9282], zoom: 12 },
        { code: 'beijing-xicheng', adcode: '110102', name: '西城区', level: 'county', center: [116.3661, 39.9127], zoom: 12 },
        { code: 'beijing-chaoyang', adcode: '110105', name: '朝阳区', level: 'county', center: [116.4861, 39.9219], zoom: 12 },
        { code: 'beijing-fengtai', adcode: '110106', name: '丰台区', level: 'county', center: [116.2867, 39.8584], zoom: 12 },
        { code: 'beijing-shijingshan', adcode: '110107', name: '石景山区', level: 'county', center: [116.2227, 39.9066], zoom: 12 },
        { code: 'beijing-haidian', adcode: '110108', name: '海淀区', level: 'county', center: [116.2982, 39.9593], zoom: 12 },
        { code: 'beijing-mentougou', adcode: '110109', name: '门头沟区', level: 'county', center: [116.1020, 39.9406], zoom: 12 },
        { code: 'beijing-fangshan', adcode: '110111', name: '房山区', level: 'county', center: [116.1432, 39.7482], zoom: 12 },
        { code: 'beijing-tongzhou', adcode: '110112', name: '通州区', level: 'county', center: [116.6564, 39.9092], zoom: 12 },
        { code: 'beijing-shunyi', adcode: '110113', name: '顺义区', level: 'county', center: [116.6542, 40.1302], zoom: 12 },
        { code: 'beijing-changping', adcode: '110114', name: '昌平区', level: 'county', center: [116.2312, 40.2207], zoom: 12 },
        { code: 'beijing-daxing', adcode: '110115', name: '大兴区', level: 'county', center: [116.3414, 39.7269], zoom: 12 },
        { code: 'beijing-huairou', adcode: '110116', name: '怀柔区', level: 'county', center: [116.6320, 40.3160], zoom: 12 },
        { code: 'beijing-pinggu', adcode: '110117', name: '平谷区', level: 'county', center: [117.1215, 40.1407], zoom: 12 },
        { code: 'beijing-miyun', adcode: '110118', name: '密云区', level: 'county', center: [116.8433, 40.3769], zoom: 12 },
        { code: 'beijing-yanqing', adcode: '110119', name: '延庆区', level: 'county', center: [115.9750, 40.4567], zoom: 12 }
      ]
    },
    {
      code: 'tianjin',
      adcode: '120000',
      name: '天津市',
      level: 'province',
      center: [117.1902, 39.1256],
      zoom: 9,
      children: [
        { code: 'tianjin-heping', adcode: '120101', name: '和平区', level: 'county', center: [117.2147, 39.1171], zoom: 12 },
        { code: 'tianjin-hedong', adcode: '120102', name: '河东区', level: 'county', center: [117.2266, 39.1282], zoom: 12 },
        { code: 'tianjin-hexi', adcode: '120103', name: '河西区', level: 'county', center: [117.2234, 39.1096], zoom: 12 },
        { code: 'tianjin-nankai', adcode: '120104', name: '南开区', level: 'county', center: [117.1501, 39.1382], zoom: 12 },
        { code: 'tianjin-hebei', adcode: '120105', name: '河北区', level: 'county', center: [117.1967, 39.1482], zoom: 12 },
        { code: 'tianjin-hongqiao', adcode: '120106', name: '红桥区', level: 'county', center: [117.1509, 39.1673], zoom: 12 },
        { code: 'tianjin-dongli', adcode: '120110', name: '东丽区', level: 'county', center: [117.3143, 39.0866], zoom: 12 },
        { code: 'tianjin-xiqing', adcode: '120111', name: '西青区', level: 'county', center: [117.0092, 39.1413], zoom: 12 },
        { code: 'tianjin-jinnan', adcode: '120112', name: '津南区', level: 'county', center: [117.3821, 38.9386], zoom: 12 },
        { code: 'tianjin-beichen', adcode: '120113', name: '北辰区', level: 'county', center: [117.1352, 39.2238], zoom: 12 },
        { code: 'tianjin-wuqing', adcode: '120114', name: '武清区', level: 'county', center: [117.0444, 39.3841], zoom: 12 },
        { code: 'tianjin-baodi', adcode: '120115', name: '宝坻区', level: 'county', center: [117.3097, 39.7174], zoom: 12 },
        { code: 'tianjin-binhai', adcode: '120116', name: '滨海新区', level: 'county', center: [117.7005, 39.0329], zoom: 12 },
        { code: 'tianjin-ninghe', adcode: '120117', name: '宁河区', level: 'county', center: [117.8262, 39.3300], zoom: 12 },
        { code: 'tianjin-jinghai', adcode: '120118', name: '静海区', level: 'county', center: [116.9742, 38.9474], zoom: 12 },
        { code: 'tianjin-jizhou', adcode: '120119', name: '蓟州区', level: 'county', center: [117.4080, 40.0460], zoom: 12 }
      ]
    },
    {
      code: 'hebei',
      adcode: '130000',
      name: '河北省',
      level: 'province',
      center: [114.5025, 38.0455],
      zoom: 7,
      children: [
        {
          code: 'shijiazhuang',
          adcode: '130100',
          name: '石家庄市',
          level: 'city',
          center: [114.5149, 38.0428],
          zoom: 10,
          children: [
            { code: 'shijiazhuang-changan', adcode: '130102', name: '长安区', level: 'county', center: [114.5391, 38.0367], zoom: 12 },
            { code: 'shijiazhuang-qiaoxi', adcode: '130104', name: '桥西区', level: 'county', center: [114.4611, 38.0039], zoom: 12 },
            { code: 'shijiazhuang-xinhua', adcode: '130105', name: '新华区', level: 'county', center: [114.4633, 38.0511], zoom: 12 },
            { code: 'shijiazhuang-jingxing', adcode: '130107', name: '井陉矿区', level: 'county', center: [114.0618, 38.0639], zoom: 12 },
            { code: 'shijiazhuang-yuhua', adcode: '130108', name: '裕华区', level: 'county', center: [114.5315, 38.0066], zoom: 12 },
            { code: 'shijiazhuang-gaocheng', adcode: '130109', name: '藁城区', level: 'county', center: [114.8467, 38.0216], zoom: 12 },
            { code: 'shijiazhuang-luquan', adcode: '130110', name: '鹿泉区', level: 'county', center: [114.3131, 38.0859], zoom: 12 },
            { code: 'shijiazhuang-luancheng', adcode: '130111', name: '栾城区', level: 'county', center: [114.6483, 37.9002], zoom: 12 }
          ]
        },
        {
          code: 'tangshan',
          adcode: '130200',
          name: '唐山市',
          level: 'city',
          center: [118.1802, 39.6305],
          zoom: 10,
          children: [
            { code: 'tangshan-lunan', adcode: '130202', name: '路南区', level: 'county', center: [118.1548, 39.6252], zoom: 12 },
            { code: 'tangshan-lubei', adcode: '130203', name: '路北区', level: 'county', center: [118.2006, 39.6244], zoom: 12 },
            { code: 'tangshan-guye', adcode: '130204', name: '古冶区', level: 'county', center: [118.4580, 39.7141], zoom: 12 },
            { code: 'tangshan-kaiping', adcode: '130205', name: '开平区', level: 'county', center: [118.2618, 39.6713], zoom: 12 },
            { code: 'tangshan-fengnan', adcode: '130207', name: '丰南区', level: 'county', center: [118.0852, 39.5760], zoom: 12 },
            { code: 'tangshan-fengrun', adcode: '130208', name: '丰润区', level: 'county', center: [118.1299, 39.8244], zoom: 12 }
          ]
        },
        {
          code: 'qinhuangdao',
          adcode: '130300',
          name: '秦皇岛市',
          level: 'city',
          center: [119.6005, 39.9354],
          zoom: 10,
          children: [
            { code: 'qinhuangdao-haigang', adcode: '130302', name: '海港区', level: 'county', center: [119.6105, 39.9345], zoom: 12 },
            { code: 'qinhuangdao-shanhaiguan', adcode: '130303', name: '山海关区', level: 'county', center: [119.7757, 39.9788], zoom: 12 },
            { code: 'qinhuangdao-beidaihe', adcode: '130304', name: '北戴河区', level: 'county', center: [119.4869, 39.8342], zoom: 12 }
          ]
        },
        {
          code: 'handan',
          adcode: '130400',
          name: '邯郸市',
          level: 'city',
          center: [114.5391, 36.6256],
          zoom: 10,
          children: [
            { code: 'handan-hanshan', adcode: '130402', name: '邯山区', level: 'county', center: [114.4836, 36.6001], zoom: 12 },
            { code: 'handan-congtai', adcode: '130403', name: '丛台区', level: 'county', center: [114.4929, 36.6184], zoom: 12 },
            { code: 'handan-fuxing', adcode: '130404', name: '复兴区', level: 'county', center: [114.4591, 36.6115], zoom: 12 }
          ]
        },
        {
          code: 'langfang',
          adcode: '131000',
          name: '廊坊市',
          level: 'city',
          center: [116.6838, 39.5382],
          zoom: 10,
          children: [
            { code: 'langfang-anci', adcode: '131002', name: '安次区', level: 'county', center: [116.7039, 39.5024], zoom: 12 },
            { code: 'langfang-guangyang', adcode: '131003', name: '广阳区', level: 'county', center: [116.7107, 39.5219], zoom: 12 }
          ]
        }
      ]
    },
    {
      code: 'shanxi',
      adcode: '140000',
      name: '山西省',
      level: 'province',
      center: [112.5349, 37.8733],
      zoom: 7,
      children: [
        {
          code: 'taiyuan',
          adcode: '140100',
          name: '太原市',
          level: 'city',
          center: [112.5489, 37.8706],
          zoom: 10,
          children: [
            { code: 'taiyuan-xiaodian', adcode: '140105', name: '小店区', level: 'county', center: [112.5656, 37.7362], zoom: 12 },
            { code: 'taiyuan-yingze', adcode: '140106', name: '迎泽区', level: 'county', center: [112.5634, 37.8633], zoom: 12 },
            { code: 'taiyuan-xinghualing', adcode: '140107', name: '杏花岭区', level: 'county', center: [112.5623, 37.8843], zoom: 12 },
            { code: 'taiyuan-jiancaoping', adcode: '140108', name: '尖草坪区', level: 'county', center: [112.4871, 37.9401], zoom: 12 },
            { code: 'taiyuan-wanbailin', adcode: '140109', name: '万柏林区', level: 'county', center: [112.5157, 37.8592], zoom: 12 },
            { code: 'taiyuan-jinyuan', adcode: '140110', name: '晋源区', level: 'county', center: [112.4779, 37.7154], zoom: 12 }
          ]
        },
        {
          code: 'datong',
          adcode: '140200',
          name: '大同市',
          level: 'city',
          center: [113.3001, 40.0769],
          zoom: 10,
          children: [
            { code: 'datong-xinrong', adcode: '140212', name: '新荣区', level: 'county', center: [113.1400, 40.2560], zoom: 12 },
            { code: 'datong-pingcheng', adcode: '140213', name: '平城区', level: 'county', center: [113.2980, 40.0913], zoom: 12 },
            { code: 'datong-yungang', adcode: '140214', name: '云冈区', level: 'county', center: [113.1498, 40.0036], zoom: 12 },
            { code: 'datong-yunzhou', adcode: '140215', name: '云州区', level: 'county', center: [113.6121, 40.0401], zoom: 12 }
          ]
        }
      ]
    },
    {
      code: 'neimenggu',
      adcode: '150000',
      name: '内蒙古自治区',
      level: 'province',
      center: [111.6708, 40.8183],
      zoom: 6,
      children: [
        {
          code: 'huhehaote',
          adcode: '150100',
          name: '呼和浩特市',
          level: 'city',
          center: [111.7520, 40.8420],
          zoom: 10,
          children: [
            { code: 'huhehaote-xincheng', adcode: '150102', name: '新城区', level: 'county', center: [111.6655, 40.8583], zoom: 12 },
            { code: 'huhehaote-huimin', adcode: '150103', name: '回民区', level: 'county', center: [111.6240, 40.8083], zoom: 12 },
            { code: 'huhehaote-yuquan', adcode: '150104', name: '玉泉区', level: 'county', center: [111.6730, 40.7527], zoom: 12 },
            { code: 'huhehaote-saihan', adcode: '150105', name: '赛罕区', level: 'county', center: [111.7019, 40.7921], zoom: 12 }
          ]
        },
        {
          code: 'baotou',
          adcode: '150200',
          name: '包头市',
          level: 'city',
          center: [109.8403, 40.6572],
          zoom: 10,
          children: [
            { code: 'baotou-donghe', adcode: '150202', name: '东河区', level: 'county', center: [110.0442, 40.5760], zoom: 12 },
            { code: 'baotou-kundulun', adcode: '150203', name: '昆都仑区', level: 'county', center: [109.8386, 40.6418], zoom: 12 },
            { code: 'baotou-qingshan', adcode: '150204', name: '青山区', level: 'county', center: [109.9016, 40.6432], zoom: 12 }
          ]
        }
      ]
    },
    {
      code: 'liaoning',
      adcode: '210000',
      name: '辽宁省',
      level: 'province',
      center: [123.4315, 41.7969],
      zoom: 7,
      children: [
        {
          code: 'shenyang',
          adcode: '210100',
          name: '沈阳市',
          level: 'city',
          center: [123.4291, 41.7968],
          zoom: 10,
          children: [
            { code: 'shenyang-heping', adcode: '210102', name: '和平区', level: 'county', center: [123.4204, 41.7896], zoom: 12 },
            { code: 'shenyang-shenhe', adcode: '210103', name: '沈河区', level: 'county', center: [123.4589, 41.7958], zoom: 12 },
            { code: 'shenyang-dadong', adcode: '210104', name: '大东区', level: 'county', center: [123.4699, 41.8054], zoom: 12 },
            { code: 'shenyang-huanggu', adcode: '210105', name: '皇姑区', level: 'county', center: [123.4420, 41.8243], zoom: 12 },
            { code: 'shenyang-tiexi', adcode: '210106', name: '铁西区', level: 'county', center: [123.3763, 41.8028], zoom: 12 },
            { code: 'shenyang-sujiatun', adcode: '210111', name: '苏家屯区', level: 'county', center: [123.3441, 41.6659], zoom: 12 },
            { code: 'shenyang-hunnan', adcode: '210112', name: '浑南区', level: 'county', center: [123.4587, 41.7145], zoom: 12 },
            { code: 'shenyang-shenbei', adcode: '210113', name: '沈北新区', level: 'county', center: [123.5261, 42.0530], zoom: 12 },
            { code: 'shenyang-yuhong', adcode: '210114', name: '于洪区', level: 'county', center: [123.3080, 41.7944], zoom: 12 }
          ]
        },
        {
          code: 'dalian',
          adcode: '210200',
          name: '大连市',
          level: 'city',
          center: [121.6147, 38.9140],
          zoom: 10,
          children: [
            { code: 'dalian-zhongshan', adcode: '210202', name: '中山区', level: 'county', center: [121.6447, 38.9185], zoom: 12 },
            { code: 'dalian-xigang', adcode: '210203', name: '西岗区', level: 'county', center: [121.6123, 38.9147], zoom: 12 },
            { code: 'dalian-shahekou', adcode: '210204', name: '沙河口区', level: 'county', center: [121.5942, 38.9050], zoom: 12 },
            { code: 'dalian-ganjingzi', adcode: '210211', name: '甘井子区', level: 'county', center: [121.5265, 38.9533], zoom: 12 },
            { code: 'dalian-lvshunkou', adcode: '210212', name: '旅顺口区', level: 'county', center: [121.2619, 38.8513], zoom: 12 },
            { code: 'dalian-jinzhou', adcode: '210213', name: '金州区', level: 'county', center: [121.7827, 39.0504], zoom: 12 }
          ]
        }
      ]
    },
    {
      code: 'shandong',
      adcode: '370000',
      name: '山东省',
      level: 'province',
      center: [117.0009, 36.6758],
      zoom: 7,
      children: [
        {
          code: 'jinan',
          adcode: '370100',
          name: '济南市',
          level: 'city',
          center: [117.1205, 36.6519],
          zoom: 10,
          children: [
            { code: 'jinan-lixia', adcode: '370102', name: '历下区', level: 'county', center: [117.0765, 36.6667], zoom: 12 },
            { code: 'jinan-shizhong', adcode: '370103', name: '市中区', level: 'county', center: [116.9974, 36.6514], zoom: 12 },
            { code: 'jinan-huaiyin', adcode: '370104', name: '槐荫区', level: 'county', center: [116.9008, 36.6514], zoom: 12 },
            { code: 'jinan-tianqiao', adcode: '370105', name: '天桥区', level: 'county', center: [116.9872, 36.6782], zoom: 12 },
            { code: 'jinan-licheng', adcode: '370112', name: '历城区', level: 'county', center: [117.0652, 36.6800], zoom: 12 },
            { code: 'jinan-changqing', adcode: '370113', name: '长清区', level: 'county', center: [116.7519, 36.5536], zoom: 12 },
            { code: 'jinan-zhangqiu', adcode: '370114', name: '章丘区', level: 'county', center: [117.5262, 36.6814], zoom: 12 },
            { code: 'jinan-jiyang', adcode: '370115', name: '济阳区', level: 'county', center: [117.1733, 36.9785], zoom: 12 },
            { code: 'jinan-laiwu', adcode: '370116', name: '莱芜区', level: 'county', center: [117.6768, 36.2139], zoom: 12 },
            { code: 'jinan-gangcheng', adcode: '370117', name: '钢城区', level: 'county', center: [117.8113, 36.0595], zoom: 12 }
          ]
        },
        {
          code: 'qingdao',
          adcode: '370200',
          name: '青岛市',
          level: 'city',
          center: [120.3826, 36.0671],
          zoom: 10,
          children: [
            { code: 'qingdao-shinan', adcode: '370202', name: '市南区', level: 'county', center: [120.4124, 36.0753], zoom: 12 },
            { code: 'qingdao-shibei', adcode: '370203', name: '市北区', level: 'county', center: [120.3749, 36.0877], zoom: 12 },
            { code: 'qingdao-huangdao', adcode: '370211', name: '黄岛区', level: 'county', center: [120.1981, 35.9607], zoom: 12 },
            { code: 'qingdao-laoshan', adcode: '370212', name: '崂山区', level: 'county', center: [120.4696, 36.1072], zoom: 12 },
            { code: 'qingdao-licang', adcode: '370213', name: '李沧区', level: 'county', center: [120.4329, 36.1455], zoom: 12 },
            { code: 'qingdao-chengyang', adcode: '370214', name: '城阳区', level: 'county', center: [120.3965, 36.3073], zoom: 12 },
            { code: 'qingdao-jimo', adcode: '370215', name: '即墨区', level: 'county', center: [120.4467, 36.3891], zoom: 12 }
          ]
        },
        {
          code: 'zibo',
          adcode: '370300',
          name: '淄博市',
          level: 'city',
          center: [118.0479, 36.8149],
          zoom: 10,
          children: [
            { code: 'zibo-zichuan', adcode: '370302', name: '淄川区', level: 'county', center: [117.9666, 36.6434], zoom: 12 },
            { code: 'zibo-zhangdian', adcode: '370303', name: '张店区', level: 'county', center: [118.0179, 36.8067], zoom: 12 },
            { code: 'zibo-boshan', adcode: '370304', name: '博山区', level: 'county', center: [117.8617, 36.4947], zoom: 12 },
            { code: 'zibo-linzi', adcode: '370305', name: '临淄区', level: 'county', center: [118.3091, 36.8270], zoom: 12 },
            { code: 'zibo-zhoucun', adcode: '370306', name: '周村区', level: 'county', center: [117.8690, 36.8031], zoom: 12 },
            { code: 'zibo-huantai', adcode: '370307', name: '桓台县', level: 'county', center: [118.0979, 36.9604], zoom: 12 }
          ]
        },
        {
          code: 'yantai',
          adcode: '370600',
          name: '烟台市',
          level: 'city',
          center: [121.4479, 37.4638],
          zoom: 10,
          children: [
            { code: 'yantai-zhifu', adcode: '370602', name: '芝罘区', level: 'county', center: [121.4000, 37.5400], zoom: 12 },
            { code: 'yantai-fushan', adcode: '370611', name: '福山区', level: 'county', center: [121.2677, 37.4980], zoom: 12 },
            { code: 'yantai-muping', adcode: '370612', name: '牟平区', level: 'county', center: [121.6004, 37.3869], zoom: 12 },
            { code: 'yantai-laishan', adcode: '370613', name: '莱山区', level: 'county', center: [121.4452, 37.5114], zoom: 12 },
            { code: 'yantai-penglai', adcode: '370614', name: '蓬莱区', level: 'county', center: [120.7588, 37.8112], zoom: 12 }
          ]
        },
        {
          code: 'SD-WF',
          adcode: '370700',
          name: '潍坊市',
          level: 'city',
          center: [119.1619, 36.7068],
          zoom: 10,
          children: [
            { code: 'SD-WF-WC', adcode: '370702', name: '潍城区', level: 'county', center: [119.1058, 36.7284], zoom: 12 },
            { code: 'SD-WF-HT', adcode: '370703', name: '寒亭区', level: 'county', center: [119.2197, 36.7755], zoom: 12 },
            { code: 'SD-WF-FL', adcode: '370704', name: '坊子区', level: 'county', center: [119.1648, 36.6536], zoom: 12 },
            { code: 'SD-WF-KW', adcode: '370705', name: '奎文区', level: 'county', center: [119.1325, 36.7073], zoom: 12 },
            { code: 'SD-WF-LW', adcode: '370724', name: '临朐县', level: 'county', center: [118.5440, 36.5125], zoom: 12 },
            { code: 'SD-WF-CL', adcode: '370725', name: '昌乐县', level: 'county', center: [118.8301, 36.7071], zoom: 12 }
          ]
        }
      ]
    },
    {
      code: 'jiangsu',
      adcode: '320000',
      name: '江苏省',
      level: 'province',
      center: [118.7674, 32.0415],
      zoom: 7,
      children: [
        {
          code: 'nanjing',
          adcode: '320100',
          name: '南京市',
          level: 'city',
          center: [118.7969, 32.0603],
          zoom: 10,
          children: [
            { code: 'nanjing-xuanwu', adcode: '320102', name: '玄武区', level: 'county', center: [118.7972, 32.0482], zoom: 12 },
            { code: 'nanjing-qinhuai', adcode: '320104', name: '秦淮区', level: 'county', center: [118.7946, 32.0391], zoom: 12 },
            { code: 'nanjing-jianye', adcode: '320105', name: '建邺区', level: 'county', center: [118.7313, 32.0039], zoom: 12 },
            { code: 'nanjing-gulou', adcode: '320106', name: '鼓楼区', level: 'county', center: [118.7697, 32.0663], zoom: 12 },
            { code: 'nanjing-pukou', adcode: '320111', name: '浦口区', level: 'county', center: [118.6283, 32.0588], zoom: 12 },
            { code: 'nanjing-qixia', adcode: '320113', name: '栖霞区', level: 'county', center: [118.9091, 32.0962], zoom: 12 },
            { code: 'nanjing-yuhuatai', adcode: '320114', name: '雨花台区', level: 'county', center: [118.7787, 31.9914], zoom: 12 },
            { code: 'nanjing-jiangning', adcode: '320115', name: '江宁区', level: 'county', center: [118.8397, 31.9535], zoom: 12 },
            { code: 'nanjing-liuhe', adcode: '320116', name: '六合区', level: 'county', center: [118.8218, 32.3221], zoom: 12 },
            { code: 'nanjing-lishui', adcode: '320117', name: '溧水区', level: 'county', center: [119.0287, 31.6513], zoom: 12 },
            { code: 'nanjing-gaochun', adcode: '320118', name: '高淳区', level: 'county', center: [118.8918, 31.3275], zoom: 12 }
          ]
        },
        {
          code: 'wuxi',
          adcode: '320200',
          name: '无锡市',
          level: 'city',
          center: [120.3119, 31.4912],
          zoom: 10,
          children: [
            { code: 'wuxi-xishan', adcode: '320205', name: '锡山区', level: 'county', center: [120.3570, 31.5896], zoom: 12 },
            { code: 'wuxi-huishan', adcode: '320206', name: '惠山区', level: 'county', center: [120.2988, 31.6816], zoom: 12 },
            { code: 'wuxi-binhu', adcode: '320211', name: '滨湖区', level: 'county', center: [120.2831, 31.5273], zoom: 12 },
            { code: 'wuxi-liangxi', adcode: '320213', name: '梁溪区', level: 'county', center: [120.3030, 31.5666], zoom: 12 },
            { code: 'wuxi-xinwu', adcode: '320214', name: '新吴区', level: 'county', center: [120.3644, 31.4912], zoom: 12 },
            { code: 'wuxi-jiangyin', adcode: '320281', name: '江阴市', level: 'county', center: [120.2852, 31.9198], zoom: 12 },
            { code: 'wuxi-yixing', adcode: '320282', name: '宜兴市', level: 'county', center: [119.8233, 31.3398], zoom: 12 }
          ]
        },
        {
          code: 'xuzhou',
          adcode: '320300',
          name: '徐州市',
          level: 'city',
          center: [117.2841, 34.2045],
          zoom: 10,
          children: [
            { code: 'xuzhou-gulou', adcode: '320302', name: '鼓楼区', level: 'county', center: [117.1850, 34.2880], zoom: 12 },
            { code: 'xuzhou-yunlong', adcode: '320303', name: '云龙区', level: 'county', center: [117.2510, 34.2530], zoom: 12 },
            { code: 'xuzhou-jiawang', adcode: '320305', name: '贾汪区', level: 'county', center: [117.4500, 34.4360], zoom: 12 },
            { code: 'xuzhou-quanshan', adcode: '320311', name: '泉山区', level: 'county', center: [117.1820, 34.2460], zoom: 12 },
            { code: 'xuzhou-tongshan', adcode: '320312', name: '铜山区', level: 'county', center: [117.1690, 34.1810], zoom: 12 },
            { code: 'xuzhou-fengxian', adcode: '320321', name: '丰县', level: 'county', center: [116.5950, 34.4990], zoom: 12 },
            { code: 'xuzhou-peixian', adcode: '320322', name: '沛县', level: 'county', center: [116.9370, 34.7220], zoom: 12 },
            { code: 'xuzhou-suining', adcode: '320324', name: '睢宁县', level: 'county', center: [117.9410, 33.9130], zoom: 12 },
            { code: 'xuzhou-xinyi', adcode: '320381', name: '新沂市', level: 'county', center: [118.3540, 34.3690], zoom: 12 },
            { code: 'xuzhou-pizhou', adcode: '320382', name: '邳州市', level: 'county', center: [117.9640, 34.3130], zoom: 12 }
          ]
        },
        {
          code: 'changzhou',
          adcode: '320400',
          name: '常州市',
          level: 'city',
          center: [119.9742, 31.8112],
          zoom: 10,
          children: [
            { code: 'changzhou-tianning', adcode: '320402', name: '天宁区', level: 'county', center: [119.9510, 31.7520], zoom: 12 },
            { code: 'changzhou-zhonglou', adcode: '320404', name: '钟楼区', level: 'county', center: [119.9010, 31.8020], zoom: 12 },
            { code: 'changzhou-xinbei', adcode: '320411', name: '新北区', level: 'county', center: [119.9710, 31.8300], zoom: 12 },
            { code: 'changzhou-wujin', adcode: '320412', name: '武进区', level: 'county', center: [119.9420, 31.7010], zoom: 12 },
            { code: 'changzhou-jintan', adcode: '320413', name: '金坛区', level: 'county', center: [119.5980, 31.7400], zoom: 12 },
            { code: 'changzhou-liyang', adcode: '320481', name: '溧阳市', level: 'county', center: [119.4840, 31.4270], zoom: 12 }
          ]
        },
        {
          code: 'suzhou',
          adcode: '320500',
          name: '苏州市',
          level: 'city',
          center: [120.5853, 31.2994],
          zoom: 10,
          children: [
            { code: 'suzhou-huqiu', adcode: '320505', name: '虎丘区', level: 'county', center: [120.5664, 31.2949], zoom: 12 },
            { code: 'suzhou-wuzhong', adcode: '320506', name: '吴中区', level: 'county', center: [120.6325, 31.2629], zoom: 12 },
            { code: 'suzhou-xiangcheng', adcode: '320507', name: '相城区', level: 'county', center: [120.6423, 31.3688], zoom: 12 },
            { code: 'suzhou-gusu', adcode: '320508', name: '姑苏区', level: 'county', center: [120.6173, 31.3354], zoom: 12 },
            { code: 'suzhou-wujiang', adcode: '320509', name: '吴江区', level: 'county', center: [120.6452, 31.1389], zoom: 12 },
            { code: 'suzhou-changshu', adcode: '320581', name: '常熟市', level: 'county', center: [120.7525, 31.6544], zoom: 12 },
            { code: 'suzhou-zhangjiagang', adcode: '320582', name: '张家港市', level: 'county', center: [120.5534, 31.8704], zoom: 12 },
            { code: 'suzhou-kunshan', adcode: '320583', name: '昆山市', level: 'county', center: [120.9804, 31.3853], zoom: 12 },
            { code: 'suzhou-taicang', adcode: '320585', name: '太仓市', level: 'county', center: [121.1291, 31.4580], zoom: 12 }
          ]
        },
        {
          code: 'nantong',
          adcode: '320600',
          name: '南通市',
          level: 'city',
          center: [120.8943, 31.9802],
          zoom: 10,
          children: [
            { code: 'nantong-chongchuan', adcode: '320602', name: '崇川区', level: 'county', center: [120.8570, 32.0500], zoom: 12 },
            { code: 'nantong-gangzha', adcode: '320611', name: '港闸区', level: 'county', center: [120.8180, 32.0300], zoom: 12 },
            { code: 'nantong-tongzhou', adcode: '320612', name: '通州区', level: 'county', center: [121.0720, 32.0670], zoom: 12 },
            { code: 'nantong-haimen', adcode: '320684', name: '海门区', level: 'county', center: [121.1650, 31.8710], zoom: 12 },
            { code: 'nantong-haian', adcode: '320621', name: '海安市', level: 'county', center: [120.4590, 32.5460], zoom: 12 },
            { code: 'nantong-rudong', adcode: '320623', name: '如东县', level: 'county', center: [121.1850, 32.3300], zoom: 12 },
            { code: 'nantong-qidong', adcode: '320681', name: '启东市', level: 'county', center: [121.6600, 31.8050], zoom: 12 },
            { code: 'nantong-rugao', adcode: '320682', name: '如皋市', level: 'county', center: [120.5600, 32.3710], zoom: 12 }
          ]
        },
        {
          code: 'lianyungang',
          adcode: '320700',
          name: '连云港市',
          level: 'city',
          center: [119.2216, 34.5967],
          zoom: 10,
          children: [
            { code: 'lianyungang-lianyun', adcode: '320703', name: '连云区', level: 'county', center: [119.3390, 34.7320], zoom: 12 },
            { code: 'lianyungang-haizhou', adcode: '320706', name: '海州区', level: 'county', center: [119.1310, 34.5710], zoom: 12 },
            { code: 'lianyungang-ganyu', adcode: '320707', name: '赣榆区', level: 'county', center: [119.1730, 34.8390], zoom: 12 },
            { code: 'lianyungang-donghai', adcode: '320722', name: '东海县', level: 'county', center: [118.7510, 34.5420], zoom: 12 },
            { code: 'lianyungang-guanyun', adcode: '320723', name: '灌云县', level: 'county', center: [119.2390, 34.3120], zoom: 12 },
            { code: 'lianyungang-guannan', adcode: '320724', name: '灌南县', level: 'county', center: [119.3560, 34.0920], zoom: 12 }
          ]
        },
        {
          code: 'huaian',
          adcode: '320800',
          name: '淮安市',
          level: 'city',
          center: [119.0213, 33.5975],
          zoom: 10,
          children: [
            { code: 'huaian-huicheng', adcode: '320802', name: '淮安区', level: 'county', center: [119.1410, 33.5030], zoom: 12 },
            { code: 'huaian-huayin', adcode: '320804', name: '淮阴区', level: 'county', center: [119.0340, 33.6320], zoom: 12 },
            { code: 'huaian-qingjiangpu', adcode: '320812', name: '清江浦区', level: 'county', center: [119.0070, 33.5990], zoom: 12 },
            { code: 'huaian-hongze', adcode: '320813', name: '洪泽区', level: 'county', center: [118.8730, 33.2940], zoom: 12 },
            { code: 'huaian-lianshui', adcode: '320826', name: '涟水县', level: 'county', center: [119.2610, 33.7820], zoom: 12 },
            { code: 'huaian-xuyi', adcode: '320830', name: '盱眙县', level: 'county', center: [118.5290, 33.0120], zoom: 12 },
            { code: 'huaian-jinhu', adcode: '320831', name: '金湖县', level: 'county', center: [119.0230, 33.0260], zoom: 12 }
          ]
        },
        {
          code: 'yancheng',
          adcode: '320900',
          name: '盐城市',
          level: 'city',
          center: [120.1631, 33.3474],
          zoom: 10,
          children: [
            { code: 'yancheng-tinghu', adcode: '320902', name: '亭湖区', level: 'county', center: [120.1960, 33.3920], zoom: 12 },
            { code: 'yancheng-yandu', adcode: '320903', name: '盐都区', level: 'county', center: [120.1540, 33.3380], zoom: 12 },
            { code: 'yancheng-dafeng', adcode: '320904', name: '大丰区', level: 'county', center: [120.4550, 33.1990], zoom: 12 },
            { code: 'yancheng-xiangshui', adcode: '320921', name: '响水县', level: 'county', center: [119.5790, 34.1990], zoom: 12 },
            { code: 'yancheng-binhai', adcode: '320922', name: '滨海县', level: 'county', center: [119.8210, 33.9910], zoom: 12 },
            { code: 'yancheng-funing', adcode: '320923', name: '阜宁县', level: 'county', center: [119.8020, 33.7830], zoom: 12 },
            { code: 'yancheng-sheyang', adcode: '320924', name: '射阳县', level: 'county', center: [120.2610, 33.7740], zoom: 12 },
            { code: 'yancheng-jianhu', adcode: '320925', name: '建湖县', level: 'county', center: [119.7990, 33.4720], zoom: 12 },
            { code: 'yancheng-dongtai', adcode: '320981', name: '东台市', level: 'county', center: [120.3210, 32.8520], zoom: 12 }
          ]
        },
        {
          code: 'yangzhou',
          adcode: '321000',
          name: '扬州市',
          level: 'city',
          center: [119.4210, 32.3932],
          zoom: 10,
          children: [
            { code: 'yangzhou-guangling', adcode: '321002', name: '广陵区', level: 'county', center: [119.4360, 32.4030], zoom: 12 },
            { code: 'yangzhou-jiangdu', adcode: '321012', name: '江都区', level: 'county', center: [119.5670, 32.4270], zoom: 12 },
            { code: 'yangzhou-hanjiang', adcode: '321003', name: '邗江区', level: 'county', center: [119.3980, 32.3760], zoom: 12 },
            { code: 'yangzhou-baoying', adcode: '321023', name: '宝应县', level: 'county', center: [119.3120, 33.2400], zoom: 12 },
            { code: 'yangzhou-yizheng', adcode: '321081', name: '仪征市', level: 'county', center: [119.1840, 32.2720], zoom: 12 },
            { code: 'yangzhou-gaoyou', adcode: '321084', name: '高邮市', level: 'county', center: [119.4590, 32.7820], zoom: 12 }
          ]
        },
        {
          code: 'zhenjiang',
          adcode: '321100',
          name: '镇江市',
          level: 'city',
          center: [119.4258, 32.1878],
          zoom: 10,
          children: [
            { code: 'zhenjiang-jingkou', adcode: '321102', name: '京口区', level: 'county', center: [119.4700, 32.1980], zoom: 12 },
            { code: 'zhenjiang-runzhou', adcode: '321111', name: '润州区', level: 'county', center: [119.4110, 32.1950], zoom: 12 },
            { code: 'zhenjiang-dantu', adcode: '321112', name: '丹徒区', level: 'county', center: [119.4330, 32.1320], zoom: 12 },
            { code: 'zhenjiang-danyang', adcode: '321181', name: '丹阳市', level: 'county', center: [119.5950, 32.0000], zoom: 12 },
            { code: 'zhenjiang-yangzhong', adcode: '321182', name: '扬中市', level: 'county', center: [119.7970, 32.2360], zoom: 12 },
            { code: 'zhenjiang-jurong', adcode: '321183', name: '句容市', level: 'county', center: [119.1650, 31.9560], zoom: 12 }
          ]
        },
        {
          code: 'taizhou-js',
          adcode: '321200',
          name: '泰州市',
          level: 'city',
          center: [119.9152, 32.4849],
          zoom: 10,
          children: [
            { code: 'taizhou-js-hailing', adcode: '321202', name: '海陵区', level: 'county', center: [119.9190, 32.4910], zoom: 12 },
            { code: 'taizhou-js-gaogang', adcode: '321203', name: '高港区', level: 'county', center: [119.8820, 32.3190], zoom: 12 },
            { code: 'taizhou-js-jiangyan', adcode: '321204', name: '姜堰区', level: 'county', center: [120.1280, 32.5080], zoom: 12 },
            { code: 'taizhou-js-xinghua', adcode: '321281', name: '兴化市', level: 'county', center: [119.8530, 32.9100], zoom: 12 },
            { code: 'taizhou-js-jingjiang', adcode: '321282', name: '靖江市', level: 'county', center: [120.2730, 32.0160], zoom: 12 },
            { code: 'taizhou-js-taixing', adcode: '321283', name: '泰兴市', level: 'county', center: [120.0520, 32.1720], zoom: 12 }
          ]
        },
        {
          code: 'suqian',
          adcode: '321300',
          name: '宿迁市',
          level: 'city',
          center: [118.2752, 33.9630],
          zoom: 10,
          children: [
            { code: 'suqian-sucheng', adcode: '321302', name: '宿城区', level: 'county', center: [118.2410, 33.9630], zoom: 12 },
            { code: 'suqian-suyu', adcode: '321311', name: '宿豫区', level: 'county', center: [118.3310, 33.9470], zoom: 12 },
            { code: 'suqian-siyang', adcode: '321323', name: '泗阳县', level: 'county', center: [118.7030, 33.7210], zoom: 12 },
            { code: 'suqian-sihong', adcode: '321324', name: '泗洪县', level: 'county', center: [118.3720, 33.4590], zoom: 12 }
          ]
        }
      ]
    },
    {
      code: 'zhejiang',
      adcode: '330000',
      name: '浙江省',
      level: 'province',
      center: [120.1536, 30.2875],
      zoom: 7,
      children: [
        {
          code: 'hangzhou',
          adcode: '330100',
          name: '杭州市',
          level: 'city',
          center: [120.1551, 30.2741],
          zoom: 10,
          children: [
            { code: 'hangzhou-shangcheng', adcode: '330102', name: '上城区', level: 'county', center: [120.1692, 30.2437], zoom: 12 },
            { code: 'hangzhou-gongshu', adcode: '330105', name: '拱墅区', level: 'county', center: [120.1421, 30.3191], zoom: 12 },
            { code: 'hangzhou-xihu', adcode: '330106', name: '西湖区', level: 'county', center: [120.1302, 30.2590], zoom: 12 },
            { code: 'hangzhou-binjiang', adcode: '330108', name: '滨江区', level: 'county', center: [120.2120, 30.2083], zoom: 12 },
            { code: 'hangzhou-xiaoshan', adcode: '330109', name: '萧山区', level: 'county', center: [120.2643, 30.1853], zoom: 12 },
            { code: 'hangzhou-yuhang', adcode: '330110', name: '余杭区', level: 'county', center: [120.2994, 30.4190], zoom: 12 },
            { code: 'hangzhou-fuyang', adcode: '330111', name: '富阳区', level: 'county', center: [119.9603, 30.0489], zoom: 12 },
            { code: 'hangzhou-linan', adcode: '330112', name: '临安区', level: 'county', center: [119.7245, 30.2343], zoom: 12 },
            { code: 'hangzhou-linping', adcode: '330113', name: '临平区', level: 'county', center: [120.3000, 30.4180], zoom: 12 },
            { code: 'hangzhou-qiantang', adcode: '330114', name: '钱塘区', level: 'county', center: [120.4940, 30.3210], zoom: 12 },
            { code: 'hangzhou-tonglu', adcode: '330122', name: '桐庐县', level: 'county', center: [119.6910, 29.7930], zoom: 12 },
            { code: 'hangzhou-chunan', adcode: '330127', name: '淳安县', level: 'county', center: [119.0430, 29.6090], zoom: 12 },
            { code: 'hangzhou-jiande', adcode: '330182', name: '建德市', level: 'county', center: [119.2810, 29.4740], zoom: 12 }
          ]
        },
        {
          code: 'ningbo',
          adcode: '330200',
          name: '宁波市',
          level: 'city',
          center: [121.5440, 29.8683],
          zoom: 10,
          children: [
            { code: 'ningbo-haishu', adcode: '330203', name: '海曙区', level: 'county', center: [121.5512, 29.8602], zoom: 12 },
            { code: 'ningbo-jiangbei', adcode: '330205', name: '江北区', level: 'county', center: [121.5552, 29.8865], zoom: 12 },
            { code: 'ningbo-beilun', adcode: '330206', name: '北仑区', level: 'county', center: [121.8446, 29.8993], zoom: 12 },
            { code: 'ningbo-zhenhai', adcode: '330211', name: '镇海区', level: 'county', center: [121.7161, 29.9487], zoom: 12 },
            { code: 'ningbo-yinzhou', adcode: '330212', name: '鄞州区', level: 'county', center: [121.5475, 29.8166], zoom: 12 },
            { code: 'ningbo-fenghua', adcode: '330213', name: '奉化区', level: 'county', center: [121.4061, 29.6554], zoom: 12 },
            { code: 'ningbo-xiangshan', adcode: '330225', name: '象山县', level: 'county', center: [121.8690, 29.4770], zoom: 12 },
            { code: 'ningbo-ninghai', adcode: '330226', name: '宁海县', level: 'county', center: [121.4300, 29.2880], zoom: 12 },
            { code: 'ningbo-yuyao', adcode: '330281', name: '余姚市', level: 'county', center: [121.1540, 30.0390], zoom: 12 },
            { code: 'ningbo-cixi', adcode: '330282', name: '慈溪市', level: 'county', center: [121.2660, 30.1700], zoom: 12 }
          ]
        },
        {
          code: 'wenzhou',
          adcode: '330300',
          name: '温州市',
          level: 'city',
          center: [120.6994, 27.9942],
          zoom: 10,
          children: [
            { code: 'wenzhou-lucheng', adcode: '330302', name: '鹿城区', level: 'county', center: [120.6720, 28.0010], zoom: 12 },
            { code: 'wenzhou-longwan', adcode: '330303', name: '龙湾区', level: 'county', center: [120.8300, 27.9330], zoom: 12 },
            { code: 'wenzhou-ouhai', adcode: '330304', name: '瓯海区', level: 'county', center: [120.6380, 28.0070], zoom: 12 },
            { code: 'wenzhou-dongtou', adcode: '330305', name: '洞头区', level: 'county', center: [121.1560, 27.8360], zoom: 12 },
            { code: 'wenzhou-yongjia', adcode: '330324', name: '永嘉县', level: 'county', center: [120.6940, 28.1510], zoom: 12 },
            { code: 'wenzhou-pingyang', adcode: '330326', name: '平阳县', level: 'county', center: [120.5660, 27.6620], zoom: 12 },
            { code: 'wenzhou-cangnan', adcode: '330327', name: '苍南县', level: 'county', center: [120.4260, 27.5170], zoom: 12 },
            { code: 'wenzhou-wencheng', adcode: '330328', name: '文成县', level: 'county', center: [120.0910, 27.7870], zoom: 12 },
            { code: 'wenzhou-taishun', adcode: '330329', name: '泰顺县', level: 'county', center: [119.7180, 27.5570], zoom: 12 },
            { code: 'wenzhou-ruian', adcode: '330381', name: '瑞安市', level: 'county', center: [120.6550, 27.7800], zoom: 12 },
            { code: 'wenzhou-yueqing', adcode: '330382', name: '乐清市', level: 'county', center: [120.9670, 28.1160], zoom: 12 },
            { code: 'wenzhou-longgang', adcode: '330383', name: '龙港市', level: 'county', center: [120.5520, 27.5850], zoom: 12 }
          ]
        },
        {
          code: 'jiaxing',
          adcode: '330400',
          name: '嘉兴市',
          level: 'city',
          center: [120.7509, 30.7627],
          zoom: 10,
          children: [
            { code: 'jiaxing-nanhu', adcode: '330402', name: '南湖区', level: 'county', center: [120.7830, 30.7460], zoom: 12 },
            { code: 'jiaxing-xiuzhou', adcode: '330411', name: '秀洲区', level: 'county', center: [120.7090, 30.7650], zoom: 12 },
            { code: 'jiaxing-jiashan', adcode: '330421', name: '嘉善县', level: 'county', center: [120.9270, 30.8300], zoom: 12 },
            { code: 'jiaxing-haiyan', adcode: '330424', name: '海盐县', level: 'county', center: [120.9460, 30.5260], zoom: 12 },
            { code: 'jiaxing-haining', adcode: '330481', name: '海宁市', level: 'county', center: [120.6810, 30.5090], zoom: 12 },
            { code: 'jiaxing-pinghu', adcode: '330482', name: '平湖市', level: 'county', center: [121.0220, 30.6960], zoom: 12 },
            { code: 'jiaxing-tongxiang', adcode: '330483', name: '桐乡市', level: 'county', center: [120.5650, 30.6300], zoom: 12 }
          ]
        },
        {
          code: 'huzhou',
          adcode: '330500',
          name: '湖州市',
          level: 'city',
          center: [120.0868, 30.8672],
          zoom: 10,
          children: [
            { code: 'huzhou-wuxing', adcode: '330502', name: '吴兴区', level: 'county', center: [120.1260, 30.8700], zoom: 12 },
            { code: 'huzhou-nanxun', adcode: '330503', name: '南浔区', level: 'county', center: [120.2420, 30.8720], zoom: 12 },
            { code: 'huzhou-deqing', adcode: '330521', name: '德清县', level: 'county', center: [119.9710, 30.5340], zoom: 12 },
            { code: 'huzhou-changxing', adcode: '330522', name: '长兴县', level: 'county', center: [119.9080, 31.0060], zoom: 12 },
            { code: 'huzhou-anji', adcode: '330523', name: '安吉县', level: 'county', center: [119.6820, 30.6380], zoom: 12 }
          ]
        },
        {
          code: 'shaoxing',
          adcode: '330600',
          name: '绍兴市',
          level: 'city',
          center: [120.0823, 29.9291],
          zoom: 10,
          children: [
            { code: 'shaoxing-yuecheng', adcode: '330602', name: '越城区', level: 'county', center: [120.0820, 29.9290], zoom: 12 },
            { code: 'shaoxing-keqiao', adcode: '330603', name: '柯桥区', level: 'county', center: [120.0750, 30.0820], zoom: 12 },
            { code: 'shaoxing-shangyu', adcode: '330604', name: '上虞区', level: 'county', center: [120.8680, 30.0330], zoom: 12 },
            { code: 'shaoxing-xinchang', adcode: '330624', name: '新昌县', level: 'county', center: [120.9040, 29.4990], zoom: 12 },
            { code: 'shaoxing-zhuji', adcode: '330681', name: '诸暨市', level: 'county', center: [120.2360, 29.7140], zoom: 12 },
            { code: 'shaoxing-shengzhou', adcode: '330683', name: '嵊州市', level: 'county', center: [120.8220, 29.5880], zoom: 12 }
          ]
        },
        {
          code: 'jinhua',
          adcode: '330700',
          name: '金华市',
          level: 'city',
          center: [119.6495, 29.0895],
          zoom: 10,
          children: [
            { code: 'jinhua-wucheng', adcode: '330702', name: '婺城区', level: 'county', center: [119.5710, 29.0870], zoom: 12 },
            { code: 'jinhua-jindong', adcode: '330703', name: '金东区', level: 'county', center: [119.6930, 29.0990], zoom: 12 },
            { code: 'jinhua-wuyi', adcode: '330723', name: '武义县', level: 'county', center: [119.8160, 28.8910], zoom: 12 },
            { code: 'jinhua-pujiang', adcode: '330726', name: '浦江县', level: 'county', center: [119.8920, 29.4530], zoom: 12 },
            { code: 'jinhua-panan', adcode: '330727', name: '磐安县', level: 'county', center: [120.4500, 29.0540], zoom: 12 },
            { code: 'jinhua-lanxi', adcode: '330781', name: '兰溪市', level: 'county', center: [119.4600, 29.2080], zoom: 12 },
            { code: 'jinhua-yiwu', adcode: '330782', name: '义乌市', level: 'county', center: [120.0750, 29.3060], zoom: 12 },
            { code: 'jinhua-dongyang', adcode: '330783', name: '东阳市', level: 'county', center: [120.2410, 29.2890], zoom: 12 },
            { code: 'jinhua-yongkang', adcode: '330784', name: '永康市', level: 'county', center: [120.0470, 28.8880], zoom: 12 }
          ]
        },
        {
          code: 'quzhou',
          adcode: '330800',
          name: '衢州市',
          level: 'city',
          center: [118.8726, 28.9414],
          zoom: 10,
          children: [
            { code: 'quzhou-kecheng', adcode: '330802', name: '柯城区', level: 'county', center: [118.8720, 28.9680], zoom: 12 },
            { code: 'quzhou-qujiang', adcode: '330803', name: '衢江区', level: 'county', center: [118.9590, 28.9790], zoom: 12 },
            { code: 'quzhou-changshan', adcode: '330822', name: '常山县', level: 'county', center: [118.5110, 28.9010], zoom: 12 },
            { code: 'quzhou-kaihua', adcode: '330824', name: '开化县', level: 'county', center: [118.4160, 29.1370], zoom: 12 },
            { code: 'quzhou-longyou', adcode: '330825', name: '龙游县', level: 'county', center: [119.1720, 29.0280], zoom: 12 },
            { code: 'quzhou-jiangshan', adcode: '330881', name: '江山市', level: 'county', center: [118.6270, 28.7370], zoom: 12 }
          ]
        },
        {
          code: 'zhoushan',
          adcode: '330900',
          name: '舟山市',
          level: 'city',
          center: [122.2072, 29.9855],
          zoom: 10,
          children: [
            { code: 'zhoushan-dinghai', adcode: '330902', name: '定海区', level: 'county', center: [122.1060, 30.0200], zoom: 12 },
            { code: 'zhoushan-putuo', adcode: '330903', name: '普陀区', level: 'county', center: [122.3240, 29.9520], zoom: 12 },
            { code: 'zhoushan-daishan', adcode: '330921', name: '岱山县', level: 'county', center: [122.2040, 30.2640], zoom: 12 },
            { code: 'zhoushan-shengsi', adcode: '330922', name: '嵊泗县', level: 'county', center: [122.4510, 30.7260], zoom: 12 }
          ]
        },
        {
          code: 'taizhou-zj',
          adcode: '331000',
          name: '台州市',
          level: 'city',
          center: [121.4286, 28.6614],
          zoom: 10,
          children: [
            { code: 'taizhou-zj-jiaojiang', adcode: '331002', name: '椒江区', level: 'county', center: [121.4290, 28.6730], zoom: 12 },
            { code: 'taizhou-zj-huangyan', adcode: '331003', name: '黄岩区', level: 'county', center: [121.2590, 28.6500], zoom: 12 },
            { code: 'taizhou-zj-luqiao', adcode: '331004', name: '路桥区', level: 'county', center: [121.3650, 28.5820], zoom: 12 },
            { code: 'taizhou-zj-yuhuan', adcode: '331083', name: '玉环市', level: 'county', center: [121.2320, 28.1360], zoom: 12 },
            { code: 'taizhou-zj-sanmen', adcode: '331022', name: '三门县', level: 'county', center: [121.3960, 29.1050], zoom: 12 },
            { code: 'taizhou-zj-tiantai', adcode: '331023', name: '天台县', level: 'county', center: [121.0080, 29.1440], zoom: 12 },
            { code: 'taizhou-zj-xianju', adcode: '331024', name: '仙居县', level: 'county', center: [120.7350, 28.8490], zoom: 12 },
            { code: 'taizhou-zj-wenling', adcode: '331081', name: '温岭市', level: 'county', center: [121.3850, 28.3720], zoom: 12 },
            { code: 'taizhou-zj-linhai', adcode: '331082', name: '临海市', level: 'county', center: [121.1450, 28.8580], zoom: 12 }
          ]
        },
        {
          code: 'lishui',
          adcode: '331100',
          name: '丽水市',
          level: 'city',
          center: [119.9218, 28.4516],
          zoom: 10,
          children: [
            { code: 'lishui-liandu', adcode: '331102', name: '莲都区', level: 'county', center: [119.9120, 28.4460], zoom: 12 },
            { code: 'lishui-qingtian', adcode: '331121', name: '青田县', level: 'county', center: [120.2900, 28.1390], zoom: 12 },
            { code: 'lishui-jinyun', adcode: '331122', name: '缙云县', level: 'county', center: [120.0920, 28.6590], zoom: 12 },
            { code: 'lishui-suichang', adcode: '331123', name: '遂昌县', level: 'county', center: [119.2760, 28.5920], zoom: 12 },
            { code: 'lishui-songyang', adcode: '331124', name: '松阳县', level: 'county', center: [119.4820, 28.4490], zoom: 12 },
            { code: 'lishui-yunhe', adcode: '331125', name: '云和县', level: 'county', center: [119.5730, 28.1160], zoom: 12 },
            { code: 'lishui-qingyuan', adcode: '331126', name: '庆元县', level: 'county', center: [119.0630, 27.6190], zoom: 12 },
            { code: 'lishui-jingning', adcode: '331127', name: '景宁畲族自治县', level: 'county', center: [119.6380, 27.9730], zoom: 12 },
            { code: 'lishui-longquan', adcode: '331181', name: '龙泉市', level: 'county', center: [119.1320, 28.0750], zoom: 12 }
          ]
        }
      ]
    },
    {
      code: 'guangdong',
      adcode: '440000',
      name: '广东省',
      level: 'province',
      center: [113.2644, 23.1291],
      zoom: 7,
      children: [
        {
          code: 'guangzhou',
          adcode: '440100',
          name: '广州市',
          level: 'city',
          center: [113.2644, 23.1291],
          zoom: 10,
          children: [
            { code: 'guangzhou-liwan', adcode: '440103', name: '荔湾区', level: 'county', center: [113.2442, 23.1250], zoom: 12 },
            { code: 'guangzhou-yuexiu', adcode: '440104', name: '越秀区', level: 'county', center: [113.2669, 23.1291], zoom: 12 },
            { code: 'guangzhou-haizhu', adcode: '440105', name: '海珠区', level: 'county', center: [113.3172, 23.0833], zoom: 12 },
            { code: 'guangzhou-tianhe', adcode: '440106', name: '天河区', level: 'county', center: [113.3612, 23.1247], zoom: 12 },
            { code: 'guangzhou-baiyun', adcode: '440111', name: '白云区', level: 'county', center: [113.2730, 23.1570], zoom: 12 },
            { code: 'guangzhou-huangpu', adcode: '440112', name: '黄埔区', level: 'county', center: [113.4598, 23.1064], zoom: 12 },
            { code: 'guangzhou-panyu', adcode: '440113', name: '番禺区', level: 'county', center: [113.3842, 22.9372], zoom: 12 },
            { code: 'guangzhou-huadu', adcode: '440114', name: '花都区', level: 'county', center: [113.2202, 23.4044], zoom: 12 },
            { code: 'guangzhou-nansha', adcode: '440115', name: '南沙区', level: 'county', center: [113.5250, 22.8017], zoom: 12 },
            { code: 'guangzhou-conghua', adcode: '440117', name: '从化区', level: 'county', center: [113.5874, 23.5453], zoom: 12 },
            { code: 'guangzhou-zengcheng', adcode: '440118', name: '增城区', level: 'county', center: [113.8109, 23.2608], zoom: 12 }
          ]
        },
        {
          code: 'shaoguan',
          adcode: '440200',
          name: '韶关市',
          level: 'city',
          center: [113.5915, 24.8013],
          zoom: 10,
          children: [
            { code: 'shaoguan-wujiang', adcode: '440203', name: '武江区', level: 'county', center: [113.5880, 24.7940], zoom: 12 },
            { code: 'shaoguan-zhenjiang', adcode: '440204', name: '浈江区', level: 'county', center: [113.6120, 24.8050], zoom: 12 },
            { code: 'shaoguan-qujiang', adcode: '440205', name: '曲江区', level: 'county', center: [113.6020, 24.6820], zoom: 12 },
            { code: 'shaoguan-shixing', adcode: '440222', name: '始兴县', level: 'county', center: [114.0620, 24.9460], zoom: 12 },
            { code: 'shaoguan-renhua', adcode: '440224', name: '仁化县', level: 'county', center: [113.7480, 25.0870], zoom: 12 },
            { code: 'shaoguan-wengyuan', adcode: '440229', name: '翁源县', level: 'county', center: [114.1310, 24.3500], zoom: 12 },
            { code: 'shaoguan-ruyuan', adcode: '440232', name: '乳源瑶族自治县', level: 'county', center: [113.2760, 24.7760], zoom: 12 },
            { code: 'shaoguan-xinfeng', adcode: '440233', name: '新丰县', level: 'county', center: [114.2070, 24.0590], zoom: 12 },
            { code: 'shaoguan-lechang', adcode: '440281', name: '乐昌市', level: 'county', center: [113.3470, 25.1300], zoom: 12 },
            { code: 'shaoguan-nanxiong', adcode: '440282', name: '南雄市', level: 'county', center: [114.3120, 25.1160], zoom: 12 }
          ]
        },
        {
          code: 'shenzhen',
          adcode: '440300',
          name: '深圳市',
          level: 'city',
          center: [114.0579, 22.5431],
          zoom: 10,
          children: [
            { code: 'shenzhen-nanshan', adcode: '440303', name: '南山区', level: 'county', center: [113.9302, 22.5329], zoom: 12 },
            { code: 'shenzhen-futian', adcode: '440304', name: '福田区', level: 'county', center: [114.0550, 22.5220], zoom: 12 },
            { code: 'shenzhen-luohu', adcode: '440303', name: '罗湖区', level: 'county', center: [114.1310, 22.5480], zoom: 12 },
            { code: 'shenzhen-baoan', adcode: '440306', name: '宝安区', level: 'county', center: [113.8830, 22.5553], zoom: 12 },
            { code: 'shenzhen-longgang', adcode: '440307', name: '龙岗区', level: 'county', center: [114.2467, 22.7203], zoom: 12 },
            { code: 'shenzhen-yantian', adcode: '440308', name: '盐田区', level: 'county', center: [114.2367, 22.5570], zoom: 12 },
            { code: 'shenzhen-longhua', adcode: '440309', name: '龙华区', level: 'county', center: [114.0367, 22.6867], zoom: 12 },
            { code: 'shenzhen-pingshan', adcode: '440310', name: '坪山区', level: 'county', center: [114.3460, 22.7080], zoom: 12 },
            { code: 'shenzhen-guangming', adcode: '440311', name: '光明区', level: 'county', center: [113.9360, 22.7480], zoom: 12 }
          ]
        },
        {
          code: 'zhuhai',
          adcode: '440400',
          name: '珠海市',
          level: 'city',
          center: [113.5539, 22.2249],
          zoom: 10,
          children: [
            { code: 'zhuhai-xiangzhou', adcode: '440402', name: '香洲区', level: 'county', center: [113.5430, 22.2660], zoom: 12 },
            { code: 'zhuhai-doumen', adcode: '440403', name: '斗门区', level: 'county', center: [113.2960, 22.2090], zoom: 12 },
            { code: 'zhuhai-jinwan', adcode: '440404', name: '金湾区', level: 'county', center: [113.3640, 22.1460], zoom: 12 }
          ]
        },
        {
          code: 'shantou',
          adcode: '440500',
          name: '汕头市',
          level: 'city',
          center: [116.6822, 23.3535],
          zoom: 10,
          children: [
            { code: 'shantou-longhu', adcode: '440507', name: '龙湖区', level: 'county', center: [116.7160, 23.3720], zoom: 12 },
            { code: 'shantou-jinping', adcode: '440511', name: '金平区', level: 'county', center: [116.7040, 23.3660], zoom: 12 },
            { code: 'shantou-haojiang', adcode: '440512', name: '濠江区', level: 'county', center: [116.7270, 23.2860], zoom: 12 },
            { code: 'shantou-chaoyang', adcode: '440513', name: '潮阳区', level: 'county', center: [116.6020, 23.2650], zoom: 12 },
            { code: 'shantou-chaonan', adcode: '440514', name: '潮南区', level: 'county', center: [116.4260, 23.2500], zoom: 12 },
            { code: 'shantou-chenghai', adcode: '440515', name: '澄海区', level: 'county', center: [116.7560, 23.4670], zoom: 12 },
            { code: 'shantou-nanao', adcode: '440523', name: '南澳县', level: 'county', center: [117.0290, 23.4220], zoom: 12 }
          ]
        },
        {
          code: 'foshan',
          adcode: '440600',
          name: '佛山市',
          level: 'city',
          center: [113.1220, 23.0288],
          zoom: 10,
          children: [
            { code: 'foshan-chancheng', adcode: '440604', name: '禅城区', level: 'county', center: [113.1220, 23.0090], zoom: 12 },
            { code: 'foshan-nanhai', adcode: '440605', name: '南海区', level: 'county', center: [113.1420, 23.0190], zoom: 12 },
            { code: 'foshan-shunde', adcode: '440606', name: '顺德区', level: 'county', center: [113.2930, 22.8050], zoom: 12 },
            { code: 'foshan-sanshui', adcode: '440607', name: '三水区', level: 'county', center: [112.8960, 23.1560], zoom: 12 },
            { code: 'foshan-gaoming', adcode: '440608', name: '高明区', level: 'county', center: [112.8920, 22.9000], zoom: 12 }
          ]
        },
        {
          code: 'jiangmen',
          adcode: '440700',
          name: '江门市',
          level: 'city',
          center: [113.0949, 22.5904],
          zoom: 10,
          children: [
            { code: 'jiangmen-pengjiang', adcode: '440703', name: '蓬江区', level: 'county', center: [113.0780, 22.5960], zoom: 12 },
            { code: 'jiangmen-jianghai', adcode: '440704', name: '江海区', level: 'county', center: [113.1110, 22.5600], zoom: 12 },
            { code: 'jiangmen-xinhui', adcode: '440705', name: '新会区', level: 'county', center: [113.0340, 22.4580], zoom: 12 },
            { code: 'jiangmen-taishan', adcode: '440781', name: '台山市', level: 'county', center: [112.7940, 22.2520], zoom: 12 },
            { code: 'jiangmen-kaiping', adcode: '440783', name: '开平市', level: 'county', center: [112.6980, 22.3770], zoom: 12 },
            { code: 'jiangmen-heshan', adcode: '440784', name: '鹤山市', level: 'county', center: [112.9640, 22.7660], zoom: 12 },
            { code: 'jiangmen-enping', adcode: '440785', name: '恩平市', level: 'county', center: [112.3050, 22.1830], zoom: 12 }
          ]
        },
        {
          code: 'zhanjiang',
          adcode: '440800',
          name: '湛江市',
          level: 'city',
          center: [110.3649, 21.2749],
          zoom: 10,
          children: [
            { code: 'zhanjiang-chikan', adcode: '440802', name: '赤坎区', level: 'county', center: [110.3660, 21.2660], zoom: 12 },
            { code: 'zhanjiang-xiashan', adcode: '440803', name: '霞山区', level: 'county', center: [110.3980, 21.1930], zoom: 12 },
            { code: 'zhanjiang-poshan', adcode: '440804', name: '坡头区', level: 'county', center: [110.4560, 21.2450], zoom: 12 },
            { code: 'zhanjiang-mazhang', adcode: '440811', name: '麻章区', level: 'county', center: [110.3340, 21.2620], zoom: 12 },
            { code: 'zhanjiang-suixi', adcode: '440823', name: '遂溪县', level: 'county', center: [110.2500, 21.3770], zoom: 12 },
            { code: 'zhanjiang-xuwen', adcode: '440825', name: '徐闻县', level: 'county', center: [110.1740, 20.3260], zoom: 12 },
            { code: 'zhanjiang-lianjiang', adcode: '440881', name: '廉江市', level: 'county', center: [110.2870, 21.6090], zoom: 12 },
            { code: 'zhanjiang-leizhou', adcode: '440882', name: '雷州市', level: 'county', center: [110.0960, 20.9140], zoom: 12 },
            { code: 'zhanjiang-wuchuan', adcode: '440883', name: '吴川市', level: 'county', center: [110.7780, 21.4420], zoom: 12 }
          ]
        },
        {
          code: 'maoming',
          adcode: '440900',
          name: '茂名市',
          level: 'city',
          center: [110.9192, 21.6598],
          zoom: 10,
          children: [
            { code: 'maoming-maonan', adcode: '440902', name: '茂南区', level: 'county', center: [110.9190, 21.6410], zoom: 12 },
            { code: 'maoming-dianbai', adcode: '440904', name: '电白区', level: 'county', center: [111.0140, 21.5140], zoom: 12 },
            { code: 'maoming-gaozhou', adcode: '440981', name: '高州市', level: 'county', center: [110.8550, 21.9180], zoom: 12 },
            { code: 'maoming-huazhou', adcode: '440982', name: '化州市', level: 'county', center: [110.6400, 21.6640], zoom: 12 },
            { code: 'maoming-xinyi', adcode: '440983', name: '信宜市', level: 'county', center: [110.9470, 22.3540], zoom: 12 }
          ]
        },
        {
          code: 'zhaoqing',
          adcode: '441200',
          name: '肇庆市',
          level: 'city',
          center: [112.4651, 23.0472],
          zoom: 10,
          children: [
            { code: 'zhaoqing-duanzhou', adcode: '441202', name: '端州区', level: 'county', center: [112.4850, 23.0520], zoom: 12 },
            { code: 'zhaoqing-dinghu', adcode: '441203', name: '鼎湖区', level: 'county', center: [112.5670, 23.1580], zoom: 12 },
            { code: 'zhaoqing-guangning', adcode: '441223', name: '广宁县', level: 'county', center: [112.4410, 23.6350], zoom: 12 },
            { code: 'zhaoqing-huaiji', adcode: '441224', name: '怀集县', level: 'county', center: [112.1840, 23.9120], zoom: 12 },
            { code: 'zhaoqing-fengkai', adcode: '441225', name: '封开县', level: 'county', center: [111.5120, 23.4240], zoom: 12 },
            { code: 'zhaoqing-deqing', adcode: '441226', name: '德庆县', level: 'county', center: [111.7860, 23.1430], zoom: 12 },
            { code: 'zhaoqing-gaoyao', adcode: '441283', name: '高要区', level: 'county', center: [112.4580, 23.0250], zoom: 12 },
            { code: 'zhaoqing-sihui', adcode: '441284', name: '四会市', level: 'county', center: [112.7340, 23.3260], zoom: 12 }
          ]
        },
        {
          code: 'huizhou',
          adcode: '441300',
          name: '惠州市',
          level: 'city',
          center: [114.4126, 23.0794],
          zoom: 10,
          children: [
            { code: 'huizhou-huicheng', adcode: '441302', name: '惠城区', level: 'county', center: [114.3820, 23.0840], zoom: 12 },
            { code: 'huizhou-huiyang', adcode: '441303', name: '惠阳区', level: 'county', center: [114.4560, 22.7890], zoom: 12 },
            { code: 'huizhou-boluo', adcode: '441322', name: '博罗县', level: 'county', center: [114.2900, 23.1730], zoom: 12 },
            { code: 'huizhou-huidong', adcode: '441323', name: '惠东县', level: 'county', center: [114.7200, 22.9850], zoom: 12 },
            { code: 'huizhou-longmen', adcode: '441324', name: '龙门县', level: 'county', center: [114.2550, 23.7280], zoom: 12 }
          ]
        },
        {
          code: 'meizhou',
          adcode: '441400',
          name: '梅州市',
          level: 'city',
          center: [116.1175, 24.2991],
          zoom: 10,
          children: [
            { code: 'meizhou-meijiang', adcode: '441402', name: '梅江区', level: 'county', center: [116.1170, 24.3100], zoom: 12 },
            { code: 'meizhou-meixian', adcode: '441403', name: '梅县区', level: 'county', center: [116.0980, 24.2650], zoom: 12 },
            { code: 'meizhou-dabu', adcode: '441422', name: '大埔县', level: 'county', center: [116.6950, 24.3470], zoom: 12 },
            { code: 'meizhou-fengshun', adcode: '441423', name: '丰顺县', level: 'county', center: [116.1820, 23.7400], zoom: 12 },
            { code: 'meizhou-wuhua', adcode: '441424', name: '五华县', level: 'county', center: [115.7760, 23.9320], zoom: 12 },
            { code: 'meizhou-pingyuan', adcode: '441426', name: '平远县', level: 'county', center: [115.8910, 24.5710], zoom: 12 },
            { code: 'meizhou-jiaoling', adcode: '441427', name: '蕉岭县', level: 'county', center: [116.1710, 24.6580], zoom: 12 },
            { code: 'meizhou-xingning', adcode: '441481', name: '兴宁市', level: 'county', center: [115.7310, 24.1360], zoom: 12 }
          ]
        },
        {
          code: 'shanwei',
          adcode: '441500',
          name: '汕尾市',
          level: 'city',
          center: [115.3647, 22.7745],
          zoom: 10,
          children: [
            { code: 'shanwei-chengqu', adcode: '441502', name: '城区', level: 'county', center: [115.3650, 22.7790], zoom: 12 },
            { code: 'shanwei-haifeng', adcode: '441521', name: '海丰县', level: 'county', center: [115.3230, 22.9660], zoom: 12 },
            { code: 'shanwei-luhe', adcode: '441523', name: '陆河县', level: 'county', center: [115.6600, 23.3030], zoom: 12 },
            { code: 'shanwei-lufeng', adcode: '441581', name: '陆丰市', level: 'county', center: [115.6440, 22.9450], zoom: 12 }
          ]
        },
        {
          code: 'heyuan',
          adcode: '441600',
          name: '河源市',
          level: 'city',
          center: [114.6975, 23.7463],
          zoom: 10,
          children: [
            { code: 'heyuan-yuancheng', adcode: '441602', name: '源城区', level: 'county', center: [114.7020, 23.7340], zoom: 12 },
            { code: 'heyuan-dongyuan', adcode: '441621', name: '东源县', level: 'county', center: [114.7460, 23.7880], zoom: 12 },
            { code: 'heyuan-heping', adcode: '441622', name: '和平县', level: 'county', center: [114.9380, 24.4430], zoom: 12 },
            { code: 'heyuan-longchuan', adcode: '441623', name: '龙川县', level: 'county', center: [115.2600, 24.1010], zoom: 12 },
            { code: 'heyuan-zijin', adcode: '441624', name: '紫金县', level: 'county', center: [115.1840, 23.6350], zoom: 12 },
            { code: 'heyuan-lianping', adcode: '441625', name: '连平县', level: 'county', center: [114.4890, 24.3710], zoom: 12 }
          ]
        },
        {
          code: 'yangjiang',
          adcode: '441700',
          name: '阳江市',
          level: 'city',
          center: [111.9822, 21.8579],
          zoom: 10,
          children: [
            { code: 'yangjiang-jiangcheng', adcode: '441702', name: '江城区', level: 'county', center: [111.9550, 21.8620], zoom: 12 },
            { code: 'yangjiang-yangdong', adcode: '441704', name: '阳东区', level: 'county', center: [112.0070, 21.8690], zoom: 12 },
            { code: 'yangjiang-yangxi', adcode: '441721', name: '阳西县', level: 'county', center: [111.6170, 21.7520], zoom: 12 },
            { code: 'yangjiang-yangchun', adcode: '441781', name: '阳春市', level: 'county', center: [111.7920, 22.1700], zoom: 12 }
          ]
        },
        {
          code: 'qingyuan',
          adcode: '441800',
          name: '清远市',
          level: 'city',
          center: [113.0515, 23.7047],
          zoom: 10,
          children: [
            { code: 'qingyuan-qingcheng', adcode: '441802', name: '清城区', level: 'county', center: [113.0620, 23.6970], zoom: 12 },
            { code: 'qingyuan-qingxin', adcode: '441803', name: '清新区', level: 'county', center: [113.0170, 23.7350], zoom: 12 },
            { code: 'qingyuan-fogang', adcode: '441821', name: '佛冈县', level: 'county', center: [113.5330, 23.8790], zoom: 12 },
            { code: 'qingyuan-yangshan', adcode: '441823', name: '阳山县', level: 'county', center: [112.6410, 24.4650], zoom: 12 },
            { code: 'qingyuan-lianshan', adcode: '441825', name: '连山壮族瑶族自治县', level: 'county', center: [112.0930, 24.5710], zoom: 12 },
            { code: 'qingyuan-liannan', adcode: '441826', name: '连南瑶族自治县', level: 'county', center: [112.2870, 24.7260], zoom: 12 },
            { code: 'qingyuan-yingde', adcode: '441881', name: '英德市', level: 'county', center: [113.4150, 24.1710], zoom: 12 },
            { code: 'qingyuan-lianzhou', adcode: '441882', name: '连州市', level: 'county', center: [112.3820, 24.7820], zoom: 12 }
          ]
        },
        {
          code: 'dongguan',
          adcode: '441900',
          name: '东莞市',
          level: 'city',
          center: [113.7463, 23.0460],
          zoom: 10,
          children: [
            { code: 'dongguan-nancheng', adcode: '441900003', name: '南城街道', level: 'county', center: [113.7520, 23.0180], zoom: 12 },
            { code: 'dongguan-dongcheng', adcode: '441900004', name: '东城街道', level: 'county', center: [113.7800, 23.0300], zoom: 12 },
            { code: 'dongguan-wanjiang', adcode: '441900005', name: '万江街道', level: 'county', center: [113.7400, 23.0500], zoom: 12 },
            { code: 'dongguan-guancheng', adcode: '441900006', name: '莞城街道', level: 'county', center: [113.7510, 23.0490], zoom: 12 },
            { code: 'dongguan-shilong', adcode: '441900101', name: '石龙镇', level: 'county', center: [113.8230, 23.1060], zoom: 12 },
            { code: 'dongguan-humen', adcode: '441900121', name: '虎门镇', level: 'county', center: [113.6740, 22.8130], zoom: 12 },
            { code: 'dongguan-changping', adcode: '441900122', name: '常平镇', level: 'county', center: [114.0030, 23.0180], zoom: 12 }
          ]
        },
        {
          code: 'zhongshan',
          adcode: '442000',
          name: '中山市',
          level: 'city',
          center: [113.3824, 22.5211],
          zoom: 10,
          children: [
            { code: 'zhongshan-shiqi', adcode: '442000001', name: '石岐街道', level: 'county', center: [113.3820, 22.5180], zoom: 12 },
            { code: 'zhongshan-dongqu', adcode: '442000002', name: '东区街道', level: 'county', center: [113.4080, 22.5170], zoom: 12 },
            { code: 'zhongshan-xiqu', adcode: '442000003', name: '西区街道', level: 'county', center: [113.3640, 22.5240], zoom: 12 },
            { code: 'zhongshan-nanqu', adcode: '442000004', name: '南区街道', level: 'county', center: [113.3740, 22.4850], zoom: 12 }
          ]
        },
        {
          code: 'chaozhou',
          adcode: '445100',
          name: '潮州市',
          level: 'city',
          center: [116.6227, 23.6566],
          zoom: 10,
          children: [
            { code: 'chaozhou-xiangqiao', adcode: '445102', name: '湘桥区', level: 'county', center: [116.6280, 23.6740], zoom: 12 },
            { code: 'chaozhou-chaoan', adcode: '445103', name: '潮安区', level: 'county', center: [116.6780, 23.4630], zoom: 12 },
            { code: 'chaozhou-raoping', adcode: '445122', name: '饶平县', level: 'county', center: [117.0040, 23.6640], zoom: 12 }
          ]
        },
        {
          code: 'jieyang',
          adcode: '445200',
          name: '揭阳市',
          level: 'city',
          center: [116.3727, 23.5497],
          zoom: 10,
          children: [
            { code: 'jieyang-rongcheng', adcode: '445202', name: '榕城区', level: 'county', center: [116.3670, 23.5250], zoom: 12 },
            { code: 'jieyang-jiedong', adcode: '445203', name: '揭东区', level: 'county', center: [116.4120, 23.5670], zoom: 12 },
            { code: 'jieyang-jiexi', adcode: '445222', name: '揭西县', level: 'county', center: [115.8420, 23.4320], zoom: 12 },
            { code: 'jieyang-huilai', adcode: '445224', name: '惠来县', level: 'county', center: [116.2950, 23.0330], zoom: 12 },
            { code: 'jieyang-puning', adcode: '445281', name: '普宁市', level: 'county', center: [116.1660, 23.2970], zoom: 12 }
          ]
        },
        {
          code: 'yunfu',
          adcode: '445300',
          name: '云浮市',
          level: 'city',
          center: [112.0440, 22.9158],
          zoom: 10,
          children: [
            { code: 'yunfu-yuncheng', adcode: '445302', name: '云城区', level: 'county', center: [112.0440, 22.9280], zoom: 12 },
            { code: 'yunfu-yunan', adcode: '445321', name: '郁南县', level: 'county', center: [111.5340, 23.2340], zoom: 12 },
            { code: 'yunfu-xinxing', adcode: '445321', name: '新兴县', level: 'county', center: [112.2250, 22.6960], zoom: 12 },
            { code: 'yunfu-luoding', adcode: '445381', name: '罗定市', level: 'county', center: [111.5690, 22.7690], zoom: 12 }
          ]
        }
      ]
    },
    {
      code: 'henan',
      adcode: '410000',
      name: '河南省',
      level: 'province',
      center: [113.6654, 34.7570],
      zoom: 7,
      children: [
        {
          code: 'zhengzhou',
          adcode: '410100',
          name: '郑州市',
          level: 'city',
          center: [113.6654, 34.7570],
          zoom: 10,
          children: [
            { code: 'zhengzhou-zhongyuan', adcode: '410102', name: '中原区', level: 'county', center: [113.6130, 34.7480], zoom: 12 },
            { code: 'zhengzhou-erqi', adcode: '410103', name: '二七区', level: 'county', center: [113.6400, 34.7240], zoom: 12 },
            { code: 'zhengzhou-guancheng', adcode: '410104', name: '管城回族区', level: 'county', center: [113.6770, 34.7540], zoom: 12 },
            { code: 'zhengzhou-jinshui', adcode: '410105', name: '金水区', level: 'county', center: [113.6600, 34.8000], zoom: 12 },
            { code: 'zhengzhou-shangjie', adcode: '410106', name: '上街区', level: 'county', center: [113.3090, 34.8020], zoom: 12 },
            { code: 'zhengzhou-huiji', adcode: '410108', name: '惠济区', level: 'county', center: [113.6170, 34.8670], zoom: 12 },
            { code: 'zhengzhou-zhongmou', adcode: '410122', name: '中牟县', level: 'county', center: [114.0230, 34.7190], zoom: 12 },
            { code: 'zhengzhou-gongyi', adcode: '410181', name: '巩义市', level: 'county', center: [113.0220, 34.7480], zoom: 12 },
            { code: 'zhengzhou-xingyang', adcode: '410182', name: '荥阳市', level: 'county', center: [113.3830, 34.7870], zoom: 12 },
            { code: 'zhengzhou-xinmi', adcode: '410183', name: '新密市', level: 'county', center: [113.3900, 34.5400], zoom: 12 },
            { code: 'zhengzhou-xinzheng', adcode: '410184', name: '新郑市', level: 'county', center: [113.7360, 34.3950], zoom: 12 },
            { code: 'zhengzhou-dengfeng', adcode: '410185', name: '登封市', level: 'county', center: [113.0500, 34.4540], zoom: 12 }
          ]
        },
        {
          code: 'kaifeng',
          adcode: '410200',
          name: '开封市',
          level: 'city',
          center: [114.3076, 35.0053],
          zoom: 10,
          children: [
            { code: 'kaifeng-longting', adcode: '410202', name: '龙亭区', level: 'county', center: [114.3550, 34.9980], zoom: 12 },
            { code: 'kaifeng-shunhe', adcode: '410203', name: '顺河回族区', level: 'county', center: [114.3610, 34.7960], zoom: 12 },
            { code: 'kaifeng-gulou', adcode: '410204', name: '鼓楼区', level: 'county', center: [114.3410, 34.7950], zoom: 12 },
            { code: 'kaifeng-yuwangtai', adcode: '410205', name: '禹王台区', level: 'county', center: [114.3480, 34.7770], zoom: 12 },
            { code: 'kaifeng-xiangfu', adcode: '410212', name: '祥符区', level: 'county', center: [114.4410, 34.7570], zoom: 12 },
            { code: 'kaifeng-qixian', adcode: '410221', name: '杞县', level: 'county', center: [114.7830, 34.5500], zoom: 12 },
            { code: 'kaifeng-tongxu', adcode: '410222', name: '通许县', level: 'county', center: [114.4670, 34.4800], zoom: 12 },
            { code: 'kaifeng-weishi', adcode: '410223', name: '尉氏县', level: 'county', center: [114.1930, 34.4120], zoom: 12 },
            { code: 'kaifeng-lankao', adcode: '410225', name: '兰考县', level: 'county', center: [114.8210, 34.8220], zoom: 12 }
          ]
        },
        {
          code: 'luoyang',
          adcode: '410300',
          name: '洛阳市',
          level: 'city',
          center: [112.4540, 34.6197],
          zoom: 10,
          children: [
            { code: 'luoyang-laocheng', adcode: '410302', name: '老城区', level: 'county', center: [112.4690, 34.6830], zoom: 12 },
            { code: 'luoyang-xigong', adcode: '410303', name: '西工区', level: 'county', center: [112.4280, 34.6600], zoom: 12 },
            { code: 'luoyang-chanhe', adcode: '410304', name: '瀍河回族区', level: 'county', center: [112.5000, 34.6790], zoom: 12 },
            { code: 'luoyang-jianxi', adcode: '410305', name: '涧西区', level: 'county', center: [112.3960, 34.6580], zoom: 12 },
            { code: 'luoyang-jili', adcode: '410306', name: '吉利区', level: 'county', center: [112.5890, 34.9010], zoom: 12 },
            { code: 'luoyang-luolong', adcode: '410311', name: '洛龙区', level: 'county', center: [112.4640, 34.6190], zoom: 12 },
            { code: 'luoyang-mengjin', adcode: '410322', name: '孟津区', level: 'county', center: [112.4450, 34.8260], zoom: 12 },
            { code: 'luoyang-xinan', adcode: '410323', name: '新安县', level: 'county', center: [112.1320, 34.7280], zoom: 12 },
            { code: 'luoyang-luanchuan', adcode: '410324', name: '栾川县', level: 'county', center: [111.6170, 33.7810], zoom: 12 },
            { code: 'luoyang-songxian', adcode: '410325', name: '嵩县', level: 'county', center: [112.0850, 34.1340], zoom: 12 },
            { code: 'luoyang-ruyang', adcode: '410326', name: '汝阳县', level: 'county', center: [112.4730, 34.1540], zoom: 12 },
            { code: 'luoyang-yiyang', adcode: '410327', name: '宜阳县', level: 'county', center: [112.1790, 34.5150], zoom: 12 },
            { code: 'luoyang-luoning', adcode: '410328', name: '洛宁县', level: 'county', center: [111.6540, 34.3890], zoom: 12 },
            { code: 'luoyang-yichuan', adcode: '410329', name: '伊川县', level: 'county', center: [112.4290, 34.4210], zoom: 12 },
            { code: 'luoyang-yanshi', adcode: '410381', name: '偃师区', level: 'county', center: [112.7890, 34.7270], zoom: 12 }
          ]
        },
        {
          code: 'pingdingshan',
          adcode: '410400',
          name: '平顶山市',
          level: 'city',
          center: [113.1927, 33.7662],
          zoom: 10,
          children: [
            { code: 'pingdingshan-xinhua', adcode: '410402', name: '新华区', level: 'county', center: [113.2940, 33.7350], zoom: 12 },
            { code: 'pingdingshan-weidong', adcode: '410403', name: '卫东区', level: 'county', center: [113.3350, 33.7340], zoom: 12 },
            { code: 'pingdingshan-shilong', adcode: '410404', name: '石龙区', level: 'county', center: [112.8990, 33.8980], zoom: 12 },
            { code: 'pingdingshan-zhanhe', adcode: '410411', name: '湛河区', level: 'county', center: [113.2920, 33.7360], zoom: 12 },
            { code: 'pingdingshan-baofeng', adcode: '410421', name: '宝丰县', level: 'county', center: [113.0550, 33.8680], zoom: 12 },
            { code: 'pingdingshan-ye', adcode: '410422', name: '叶县', level: 'county', center: [113.3580, 33.6200], zoom: 12 },
            { code: 'pingdingshan-lushan', adcode: '410423', name: '鲁山县', level: 'county', center: [112.9060, 33.7380], zoom: 12 },
            { code: 'pingdingshan-cheng', adcode: '410425', name: '郏县', level: 'county', center: [113.2120, 33.9710], zoom: 12 },
            { code: 'pingdingshan-wugang', adcode: '410481', name: '舞钢市', level: 'county', center: [113.5240, 33.2940], zoom: 12 },
            { code: 'pingdingshan-ruzhou', adcode: '410482', name: '汝州市', level: 'county', center: [112.8430, 34.1670], zoom: 12 }
          ]
        },
        {
          code: 'anyang',
          adcode: '410500',
          name: '安阳市',
          level: 'city',
          center: [114.3931, 36.0962],
          zoom: 10,
          children: [
            { code: 'anyang-wenfeng', adcode: '410502', name: '文峰区', level: 'county', center: [114.3870, 36.0890], zoom: 12 },
            { code: 'anyang-beiguan', adcode: '410503', name: '北关区', level: 'county', center: [114.3550, 36.1070], zoom: 12 },
            { code: 'anyang-yindu', adcode: '410505', name: '殷都区', level: 'county', center: [114.3030, 36.1100], zoom: 12 },
            { code: 'anyang-longan', adcode: '410506', name: '龙安区', level: 'county', center: [114.3480, 36.0810], zoom: 12 },
            { code: 'anyang-anyang', adcode: '410522', name: '安阳县', level: 'county', center: [114.1260, 36.0640], zoom: 12 },
            { code: 'anyang-tangyin', adcode: '410523', name: '汤阴县', level: 'county', center: [114.3580, 35.9220], zoom: 12 },
            { code: 'anyang-hua', adcode: '410526', name: '滑县', level: 'county', center: [114.5200, 35.5750], zoom: 12 },
            { code: 'anyang-neihuang', adcode: '410527', name: '内黄县', level: 'county', center: [114.9010, 35.9720], zoom: 12 },
            { code: 'anyang-linzhou', adcode: '410581', name: '林州市', level: 'county', center: [113.8160, 36.0760], zoom: 12 }
          ]
        },
        {
          code: 'hebi',
          adcode: '410600',
          name: '鹤壁市',
          level: 'city',
          center: [114.2973, 35.7472],
          zoom: 10,
          children: [
            { code: 'hebi-heshan', adcode: '410602', name: '鹤山区', level: 'county', center: [114.1630, 35.9530], zoom: 12 },
            { code: 'hebi-shancheng', adcode: '410603', name: '山城区', level: 'county', center: [114.1840, 35.8960], zoom: 12 },
            { code: 'hebi-qibin', adcode: '410611', name: '淇滨区', level: 'county', center: [114.2990, 35.7410], zoom: 12 },
            { code: 'hebi-xun', adcode: '410621', name: '浚县', level: 'county', center: [114.5490, 35.6760], zoom: 12 },
            { code: 'hebi-qi', adcode: '410622', name: '淇县', level: 'county', center: [114.1980, 35.6080], zoom: 12 }
          ]
        },
        {
          code: 'xinxiang',
          adcode: '410700',
          name: '新乡市',
          level: 'city',
          center: [113.9268, 35.3030],
          zoom: 10,
          children: [
            { code: 'xinxiang-hongqi', adcode: '410702', name: '红旗区', level: 'county', center: [113.8750, 35.3030], zoom: 12 },
            { code: 'xinxiang-weibin', adcode: '410703', name: '卫滨区', level: 'county', center: [113.8660, 35.3020], zoom: 12 },
            { code: 'xinxiang-fengquan', adcode: '410704', name: '凤泉区', level: 'county', center: [113.9150, 35.3840], zoom: 12 },
            { code: 'xinxiang-muye', adcode: '410711', name: '牧野区', level: 'county', center: [113.9090, 35.3150], zoom: 12 },
            { code: 'xinxiang-xinxiang', adcode: '410721', name: '新乡县', level: 'county', center: [113.8050, 35.1910], zoom: 12 },
            { code: 'xinxiang-huojia', adcode: '410724', name: '获嘉县', level: 'county', center: [113.6570, 35.2620], zoom: 12 },
            { code: 'xinxiang-yuanyang', adcode: '410725', name: '原阳县', level: 'county', center: [113.9670, 35.0650], zoom: 12 },
            { code: 'xinxiang-yanjin', adcode: '410726', name: '延津县', level: 'county', center: [114.2060, 35.1410], zoom: 12 },
            { code: 'xinxiang-fengqiu', adcode: '410727', name: '封丘县', level: 'county', center: [114.4190, 35.0410], zoom: 12 },
            { code: 'xinxiang-changyuan', adcode: '410728', name: '长垣市', level: 'county', center: [114.2950, 35.2010], zoom: 12 },
            { code: 'xinxiang-weihui', adcode: '410781', name: '卫辉市', level: 'county', center: [114.0650, 35.3980], zoom: 12 }
          ]
        },
        {
          code: 'jiaozuo',
          adcode: '410800',
          name: '焦作市',
          level: 'city',
          center: [113.2383, 35.2169],
          zoom: 10,
          children: [
            { code: 'jiaozuo-jiefang', adcode: '410802', name: '解放区', level: 'county', center: [113.2290, 35.2160], zoom: 12 },
            { code: 'jiaozuo-shanyang', adcode: '410803', name: '山阳区', level: 'county', center: [113.2560, 35.2140], zoom: 12 },
            { code: 'jiaozuo-zhongzhan', adcode: '410804', name: '中站区', level: 'county', center: [113.1830, 35.2180], zoom: 12 },
            { code: 'jiaozuo-macun', adcode: '410811', name: '马村区', level: 'county', center: [113.3190, 35.2570], zoom: 12 },
            { code: 'jiaozuo-xiuwu', adcode: '410821', name: '修武县', level: 'county', center: [113.4480, 35.2240], zoom: 12 },
            { code: 'jiaozuo-boai', adcode: '410822', name: '博爱县', level: 'county', center: [113.0640, 35.1690], zoom: 12 },
            { code: 'jiaozuo-wuzhi', adcode: '410823', name: '武陟县', level: 'county', center: [113.3970, 35.1040], zoom: 12 },
            { code: 'jiaozuo-wen', adcode: '410825', name: '温县', level: 'county', center: [113.0790, 34.9410], zoom: 12 },
            { code: 'jiaozuo-qinyang', adcode: '410882', name: '沁阳市', level: 'county', center: [112.9500, 35.0880], zoom: 12 },
            { code: 'jiaozuo-mengzhou', adcode: '410883', name: '孟州市', level: 'county', center: [112.7910, 34.9090], zoom: 12 }
          ]
        },
        {
          code: 'puyang',
          adcode: '410900',
          name: '濮阳市',
          level: 'city',
          center: [115.0292, 35.7617],
          zoom: 10,
          children: [
            { code: 'puyang-hualong', adcode: '410902', name: '华龙区', level: 'county', center: [115.0740, 35.7630], zoom: 12 },
            { code: 'puyang-qingfeng', adcode: '410922', name: '清丰县', level: 'county', center: [115.1040, 35.8850], zoom: 12 },
            { code: 'puyang-nanle', adcode: '410923', name: '南乐县', level: 'county', center: [115.2060, 36.0700], zoom: 12 },
            { code: 'puyang-fan', adcode: '410926', name: '范县', level: 'county', center: [115.5040, 35.9020], zoom: 12 },
            { code: 'puyang-taiqian', adcode: '410927', name: '台前县', level: 'county', center: [115.8520, 35.9950], zoom: 12 },
            { code: 'puyang-puyang', adcode: '410928', name: '濮阳县', level: 'county', center: [115.0290, 35.6120], zoom: 12 }
          ]
        },
        {
          code: 'xuchang',
          adcode: '411000',
          name: '许昌市',
          level: 'city',
          center: [113.8526, 34.0357],
          zoom: 10,
          children: [
            { code: 'xuchang-weidu', adcode: '411002', name: '魏都区', level: 'county', center: [113.8230, 34.0260], zoom: 12 },
            { code: 'xuchang-jianan', adcode: '411003', name: '建安区', level: 'county', center: [113.8470, 34.0050], zoom: 12 },
            { code: 'xuchang-yanling', adcode: '411024', name: '鄢陵县', level: 'county', center: [114.1870, 34.1030], zoom: 12 },
            { code: 'xuchang-xiangcheng', adcode: '411025', name: '襄城县', level: 'county', center: [113.4820, 33.8490], zoom: 12 },
            { code: 'xuchang-yuzhou', adcode: '411081', name: '禹州市', level: 'county', center: [113.4880, 34.1620], zoom: 12 },
            { code: 'xuchang-changge', adcode: '411082', name: '长葛市', level: 'county', center: [113.7680, 34.2180], zoom: 12 }
          ]
        },
        {
          code: 'luohe',
          adcode: '411100',
          name: '漯河市',
          level: 'city',
          center: [114.0461, 33.5769],
          zoom: 10,
          children: [
            { code: 'luohe-yuanhui', adcode: '411102', name: '源汇区', level: 'county', center: [114.0060, 33.5560], zoom: 12 },
            { code: 'luohe-yancheng', adcode: '411103', name: '郾城区', level: 'county', center: [114.0060, 33.5870], zoom: 12 },
            { code: 'luohe-shaoling', adcode: '411104', name: '召陵区', level: 'county', center: [114.0940, 33.5870], zoom: 12 },
            { code: 'luohe-wuyang', adcode: '411121', name: '舞阳县', level: 'county', center: [113.5980, 33.4380], zoom: 12 },
            { code: 'luohe-linyin', adcode: '411122', name: '临颍县', level: 'county', center: [113.9360, 33.8070], zoom: 12 }
          ]
        },
        {
          code: 'sanmenxia',
          adcode: '411200',
          name: '三门峡市',
          level: 'city',
          center: [111.2001, 34.7726],
          zoom: 10,
          children: [
            { code: 'sanmenxia-hubin', adcode: '411202', name: '湖滨区', level: 'county', center: [111.2000, 34.7780], zoom: 12 },
            { code: 'sanmenxia-shanzhou', adcode: '411203', name: '陕州区', level: 'county', center: [111.1040, 34.7200], zoom: 12 },
            { code: 'sanmenxia-mianchi', adcode: '411221', name: '渑池县', level: 'county', center: [111.7620, 34.7670], zoom: 12 },
            { code: 'sanmenxia-lushi', adcode: '411224', name: '卢氏县', level: 'county', center: [111.0480, 34.0540], zoom: 12 },
            { code: 'sanmenxia-yima', adcode: '411281', name: '义马市', level: 'county', center: [111.8690, 34.7470], zoom: 12 },
            { code: 'sanmenxia-lingbao', adcode: '411282', name: '灵宝市', level: 'county', center: [110.8940, 34.5170], zoom: 12 }
          ]
        },
        {
          code: 'nanyang',
          adcode: '411300',
          name: '南阳市',
          level: 'city',
          center: [112.5283, 32.9908],
          zoom: 10,
          children: [
            { code: 'nanyang-wancheng', adcode: '411302', name: '宛城区', level: 'county', center: [112.5400, 33.0040], zoom: 12 },
            { code: 'nanyang-wolong', adcode: '411303', name: '卧龙区', level: 'county', center: [112.5350, 32.9870], zoom: 12 },
            { code: 'nanyang-nanzhao', adcode: '411321', name: '南召县', level: 'county', center: [112.4310, 33.4900], zoom: 12 },
            { code: 'nanyang-fangcheng', adcode: '411322', name: '方城县', level: 'county', center: [113.0130, 33.2540], zoom: 12 },
            { code: 'nanyang-xixia', adcode: '411323', name: '西峡县', level: 'county', center: [111.4820, 33.2980], zoom: 12 },
            { code: 'nanyang-zhenping', adcode: '411324', name: '镇平县', level: 'county', center: [112.2340, 33.0360], zoom: 12 },
            { code: 'nanyang-neixiang', adcode: '411325', name: '内乡县', level: 'county', center: [111.8500, 33.0460], zoom: 12 },
            { code: 'nanyang-xichuan', adcode: '411326', name: '淅川县', level: 'county', center: [111.4870, 33.1380], zoom: 12 },
            { code: 'nanyang-sheqi', adcode: '411327', name: '社旗县', level: 'county', center: [112.9470, 33.0560], zoom: 12 },
            { code: 'nanyang-tanghe', adcode: '411328', name: '唐河县', level: 'county', center: [112.8070, 32.6900], zoom: 12 },
            { code: 'nanyang-xinye', adcode: '411329', name: '新野县', level: 'county', center: [112.3620, 32.5210], zoom: 12 },
            { code: 'nanyang-tongbai', adcode: '411330', name: '桐柏县', level: 'county', center: [113.4060, 32.3790], zoom: 12 },
            { code: 'nanyang-dengzhou', adcode: '411381', name: '邓州市', level: 'county', center: [112.0870, 32.6860], zoom: 12 }
          ]
        },
        {
          code: 'shangqiu',
          adcode: '411400',
          name: '商丘市',
          level: 'city',
          center: [115.6505, 34.4371],
          zoom: 10,
          children: [
            { code: 'shangqiu-liangyuan', adcode: '411402', name: '梁园区', level: 'county', center: [115.6140, 34.4440], zoom: 12 },
            { code: 'shangqiu-suiyang', adcode: '411403', name: '睢阳区', level: 'county', center: [115.6540, 34.3890], zoom: 12 },
            { code: 'shangqiu-minquan', adcode: '411421', name: '民权县', level: 'county', center: [115.1470, 34.6460], zoom: 12 },
            { code: 'shangqiu-suixian', adcode: '411422', name: '睢县', level: 'county', center: [115.0720, 34.4460], zoom: 12 },
            { code: 'shangqiu-ningling', adcode: '411423', name: '宁陵县', level: 'county', center: [115.3130, 34.4540], zoom: 12 },
            { code: 'shangqiu-tancheng', adcode: '411424', name: '柘城县', level: 'county', center: [115.3060, 34.0910], zoom: 12 },
            { code: 'shangqiu-yucheng', adcode: '411425', name: '虞城县', level: 'county', center: [115.8630, 34.4020], zoom: 12 },
            { code: 'shangqiu-xiayi', adcode: '411426', name: '夏邑县', level: 'county', center: [116.1320, 34.2390], zoom: 12 },
            { code: 'shangqiu-yongcheng', adcode: '411481', name: '永城市', level: 'county', center: [116.4500, 33.9390], zoom: 12 }
          ]
        },
        {
          code: 'xinyang',
          adcode: '411500',
          name: '信阳市',
          level: 'city',
          center: [114.0919, 32.1470],
          zoom: 10,
          children: [
            { code: 'xinyang-shihe', adcode: '411502', name: '浉河区', level: 'county', center: [114.0580, 32.1170], zoom: 12 },
            { code: 'xinyang-pingqiao', adcode: '411503', name: '平桥区', level: 'county', center: [114.1250, 32.1010], zoom: 12 },
            { code: 'xinyang-luoshan', adcode: '411521', name: '罗山县', level: 'county', center: [114.5330, 32.2030], zoom: 12 },
            { code: 'xinyang-guangshan', adcode: '411522', name: '光山县', level: 'county', center: [114.9190, 32.0100], zoom: 12 },
            { code: 'xinyang-xin', adcode: '411523', name: '新县', level: 'county', center: [114.8790, 31.6440], zoom: 12 },
            { code: 'xinyang-shangcheng', adcode: '411524', name: '商城县', level: 'county', center: [115.4090, 31.7990], zoom: 12 },
            { code: 'xinyang-gushi', adcode: '411525', name: '固始县', level: 'county', center: [115.6830, 32.1680], zoom: 12 },
            { code: 'xinyang-huangchuan', adcode: '411526', name: '潢川县', level: 'county', center: [115.0470, 32.1310], zoom: 12 },
            { code: 'xinyang-huaibin', adcode: '411527', name: '淮滨县', level: 'county', center: [115.4200, 32.4730], zoom: 12 },
            { code: 'xinyang-xixian', adcode: '411528', name: '息县', level: 'county', center: [114.7400, 32.3430], zoom: 12 }
          ]
        },
        {
          code: 'zhoukou',
          adcode: '411600',
          name: '周口市',
          level: 'city',
          center: [114.6969, 33.6262],
          zoom: 10,
          children: [
            { code: 'zhoukou-chuanhui', adcode: '411602', name: '川汇区', level: 'county', center: [114.6960, 33.6260], zoom: 12 },
            { code: 'zhoukou-huaiyang', adcode: '411603', name: '淮阳区', level: 'county', center: [114.8860, 33.7320], zoom: 12 },
            { code: 'zhoukou-fugou', adcode: '411621', name: '扶沟县', level: 'county', center: [114.3950, 34.0600], zoom: 12 },
            { code: 'zhoukou-xihua', adcode: '411622', name: '西华县', level: 'county', center: [114.5300, 33.7660], zoom: 12 },
            { code: 'zhoukou-shangshui', adcode: '411623', name: '商水县', level: 'county', center: [114.6100, 33.5420], zoom: 12 },
            { code: 'zhoukou-taikang', adcode: '411627', name: '太康县', level: 'county', center: [114.8580, 34.0640], zoom: 12 },
            { code: 'zhoukou-luyi', adcode: '411628', name: '鹿邑县', level: 'county', center: [115.4840, 33.8600], zoom: 12 },
            { code: 'zhoukou-dancheng', adcode: '411629', name: '郸城县', level: 'county', center: [115.1770, 33.6430], zoom: 12 },
            { code: 'zhoukou-shenqiu', adcode: '411681', name: '沈丘县', level: 'county', center: [115.0980, 33.4090], zoom: 12 },
            { code: 'zhoukou-xiangcheng', adcode: '411682', name: '项城市', level: 'county', center: [114.9030, 33.4660], zoom: 12 }
          ]
        },
        {
          code: 'zhumadian',
          adcode: '411700',
          name: '驻马店市',
          level: 'city',
          center: [114.0223, 32.9800],
          zoom: 10,
          children: [
            { code: 'zhumadian-yicheng', adcode: '411702', name: '驿城区', level: 'county', center: [114.0070, 32.9720], zoom: 12 },
            { code: 'zhumadian-queyang', adcode: '411721', name: '确山县', level: 'county', center: [114.0260, 32.8020], zoom: 12 },
            { code: 'zhumadian-biyang', adcode: '411722', name: '泌阳县', level: 'county', center: [113.3270, 32.7240], zoom: 12 },
            { code: 'zhumadian-suiping', adcode: '411723', name: '遂平县', level: 'county', center: [114.0130, 33.1460], zoom: 12 },
            { code: 'zhumadian-xiping', adcode: '411724', name: '西平县', level: 'county', center: [114.0230, 33.3840], zoom: 12 },
            { code: 'zhumadian-shangcai', adcode: '411725', name: '上蔡县', level: 'county', center: [114.2640, 33.2630], zoom: 12 },
            { code: 'zhumadian-runan', adcode: '411726', name: '汝南县', level: 'county', center: [114.3620, 33.0060], zoom: 12 },
            { code: 'zhumadian-pingyu', adcode: '411727', name: '平舆县', level: 'county', center: [114.6190, 32.9630], zoom: 12 },
            { code: 'zhumadian-xincai', adcode: '411728', name: '新蔡县', level: 'county', center: [114.9890, 32.7500], zoom: 12 },
            { code: 'zhumadian-zhengyang', adcode: '411729', name: '正阳县', level: 'county', center: [114.3930, 32.6050], zoom: 12 }
          ]
        },
        {
          code: 'jiyuan',
          adcode: '419001',
          name: '济源市',
          level: 'city',
          center: [112.6023, 35.0672],
          zoom: 10,
          children: [
            { code: 'jiyuan-jicheng', adcode: '419001001', name: '济水街道', level: 'county', center: [112.6020, 35.0670], zoom: 12 }
          ]
        }
      ]
    },
    {
      code: 'sichuan',
      adcode: '510000',
      name: '四川省',
      level: 'province',
      center: [104.0657, 30.6595],
      zoom: 7,
      children: [
        {
          code: 'chengdu',
          adcode: '510100',
          name: '成都市',
          level: 'city',
          center: [104.0657, 30.6595],
          zoom: 10,
          children: [
            { code: 'chengdu-jiangjin', adcode: '510104', name: '锦江区', level: 'county', center: [104.0830, 30.6550], zoom: 12 },
            { code: 'chengdu-qingyang', adcode: '510105', name: '青羊区', level: 'county', center: [104.0620, 30.6740], zoom: 12 },
            { code: 'chengdu-jinniu', adcode: '510106', name: '金牛区', level: 'county', center: [104.0520, 30.6920], zoom: 12 },
            { code: 'chengdu-wuhou', adcode: '510107', name: '武侯区', level: 'county', center: [104.0430, 30.6420], zoom: 12 },
            { code: 'chengdu-chenghua', adcode: '510108', name: '成华区', level: 'county', center: [104.1010, 30.6600], zoom: 12 },
            { code: 'chengdu-longquanyi', adcode: '510112', name: '龙泉驿区', level: 'county', center: [104.2750, 30.5560], zoom: 12 },
            { code: 'chengdu-qingbaijiang', adcode: '510113', name: '青白江区', level: 'county', center: [104.2510, 30.8780], zoom: 12 },
            { code: 'chengdu-xindu', adcode: '510114', name: '新都区', level: 'county', center: [104.1590, 30.8230], zoom: 12 },
            { code: 'chengdu-wenjiang', adcode: '510115', name: '温江区', level: 'county', center: [103.8560, 30.6820], zoom: 12 },
            { code: 'chengdu-shuangliu', adcode: '510116', name: '双流区', level: 'county', center: [103.9230, 30.5740], zoom: 12 },
            { code: 'chengdu-pidu', adcode: '510117', name: '郫都区', level: 'county', center: [103.9010, 30.7950], zoom: 12 },
            { code: 'chengdu-xinjin', adcode: '510118', name: '新津区', level: 'county', center: [103.8110, 30.4100], zoom: 12 },
            { code: 'chengdu-jintang', adcode: '510121', name: '金堂县', level: 'county', center: [104.4120, 30.8620], zoom: 12 },
            { code: 'chengdu-dayi', adcode: '510129', name: '大邑县', level: 'county', center: [103.5110, 30.5870], zoom: 12 },
            { code: 'chengdu-pujiang', adcode: '510131', name: '蒲江县', level: 'county', center: [103.5060, 30.1970], zoom: 12 },
            { code: 'chengdu-dujiangyan', adcode: '510181', name: '都江堰市', level: 'county', center: [103.6170, 31.0050], zoom: 12 },
            { code: 'chengdu-pengzhou', adcode: '510182', name: '彭州市', level: 'county', center: [103.9580, 30.9900], zoom: 12 },
            { code: 'chengdu-qionglai', adcode: '510183', name: '邛崃市', level: 'county', center: [103.4640, 30.4110], zoom: 12 },
            { code: 'chengdu-chongzhou', adcode: '510184', name: '崇州市', level: 'county', center: [103.6730, 30.6310], zoom: 12 },
            { code: 'chengdu-jianyang', adcode: '510185', name: '简阳市', level: 'county', center: [104.5470, 30.3900], zoom: 12 }
          ]
        },
        {
          code: 'zigong',
          adcode: '510300',
          name: '自贡市',
          level: 'city',
          center: [104.7784, 29.3398],
          zoom: 10,
          children: [
            { code: 'zigong-ziliujing', adcode: '510302', name: '自流井区', level: 'county', center: [104.7780, 29.3370], zoom: 12 },
            { code: 'zigong-gongjing', adcode: '510303', name: '贡井区', level: 'county', center: [104.7150, 29.3450], zoom: 12 },
            { code: 'zigong-daan', adcode: '510304', name: '大安区', level: 'county', center: [104.7740, 29.3640], zoom: 12 },
            { code: 'zigong-yantan', adcode: '510311', name: '沿滩区', level: 'county', center: [104.8740, 29.2720], zoom: 12 },
            { code: 'zigong-rong', adcode: '510321', name: '荣县', level: 'county', center: [104.4180, 29.4450], zoom: 12 },
            { code: 'zigong-fushun', adcode: '510322', name: '富顺县', level: 'county', center: [104.9750, 29.1810], zoom: 12 }
          ]
        },
        {
          code: 'panzhihua',
          adcode: '510400',
          name: '攀枝花市',
          level: 'city',
          center: [101.7186, 26.5809],
          zoom: 10,
          children: [
            { code: 'panzhihua-dongqu', adcode: '510402', name: '东区', level: 'county', center: [101.7040, 26.5820], zoom: 12 },
            { code: 'panzhihua-xiqu', adcode: '510403', name: '西区', level: 'county', center: [101.6310, 26.5970], zoom: 12 },
            { code: 'panzhihua-renhe', adcode: '510411', name: '仁和区', level: 'county', center: [101.7380, 26.4970], zoom: 12 },
            { code: 'panzhihua-miyi', adcode: '510421', name: '米易县', level: 'county', center: [102.1110, 26.8900], zoom: 12 },
            { code: 'panzhihua-yanbian', adcode: '510422', name: '盐边县', level: 'county', center: [101.8540, 26.6830], zoom: 12 }
          ]
        },
        {
          code: 'luzhou',
          adcode: '510500',
          name: '泸州市',
          level: 'city',
          center: [105.4433, 28.8890],
          zoom: 10,
          children: [
            { code: 'luzhou-jiangyang', adcode: '510502', name: '江阳区', level: 'county', center: [105.4350, 28.8790], zoom: 12 },
            { code: 'luzhou-naxi', adcode: '510503', name: '纳溪区', level: 'county', center: [105.3710, 28.7730], zoom: 12 },
            { code: 'luzhou-longmatan', adcode: '510504', name: '龙马潭区', level: 'county', center: [105.4380, 28.9140], zoom: 12 },
            { code: 'luzhou-lu', adcode: '510521', name: '泸县', level: 'county', center: [105.3820, 29.1520], zoom: 12 },
            { code: 'luzhou-hejiang', adcode: '510522', name: '合江县', level: 'county', center: [105.8310, 28.8110], zoom: 12 },
            { code: 'luzhou-xuyong', adcode: '510524', name: '叙永县', level: 'county', center: [105.4440, 28.1660], zoom: 12 },
            { code: 'luzhou-gulin', adcode: '510525', name: '古蔺县', level: 'county', center: [105.8130, 28.0390], zoom: 12 }
          ]
        },
        {
          code: 'deyang',
          adcode: '510600',
          name: '德阳市',
          level: 'city',
          center: [104.3979, 31.1268],
          zoom: 10,
          children: [
            { code: 'deyang-jingyang', adcode: '510603', name: '旌阳区', level: 'county', center: [104.3980, 31.1270], zoom: 12 },
            { code: 'deyang-luojiang', adcode: '510604', name: '罗江区', level: 'county', center: [104.5100, 31.3170], zoom: 12 },
            { code: 'deyang-zhongjiang', adcode: '510623', name: '中江县', level: 'county', center: [104.6790, 31.0340], zoom: 12 },
            { code: 'deyang-guanghan', adcode: '510681', name: '广汉市', level: 'county', center: [104.2820, 30.9770], zoom: 12 },
            { code: 'deyang-shifang', adcode: '510682', name: '什邡市', level: 'county', center: [104.1670, 31.1270], zoom: 12 },
            { code: 'deyang-mianzhu', adcode: '510683', name: '绵竹市', level: 'county', center: [104.1970, 31.3220], zoom: 12 }
          ]
        },
        {
          code: 'mianyang',
          adcode: '510700',
          name: '绵阳市',
          level: 'city',
          center: [104.6794, 31.4670],
          zoom: 10,
          children: [
            { code: 'mianyang-fucheng', adcode: '510703', name: '涪城区', level: 'county', center: [104.7570, 31.4550], zoom: 12 },
            { code: 'mianyang-youxian', adcode: '510704', name: '游仙区', level: 'county', center: [104.7660, 31.4740], zoom: 12 },
            { code: 'mianyang-anzhou', adcode: '510705', name: '安州区', level: 'county', center: [104.5670, 31.5340], zoom: 12 },
            { code: 'mianyang-santai', adcode: '510722', name: '三台县', level: 'county', center: [105.0940, 31.0960], zoom: 12 },
            { code: 'mianyang-yanting', adcode: '510723', name: '盐亭县', level: 'county', center: [105.3890, 31.2220], zoom: 12 },
            { code: 'mianyang-zitong', adcode: '510725', name: '梓潼县', level: 'county', center: [105.1610, 31.6350], zoom: 12 },
            { code: 'mianyang-beichuan', adcode: '510726', name: '北川羌族自治县', level: 'county', center: [104.4680, 31.8270], zoom: 12 },
            { code: 'mianyang-pingwu', adcode: '510727', name: '平武县', level: 'county', center: [104.5280, 32.4080], zoom: 12 },
            { code: 'mianyang-jiangyou', adcode: '510781', name: '江油市', level: 'county', center: [104.7460, 31.7780], zoom: 12 }
          ]
        },
        {
          code: 'guangyuan',
          adcode: '510800',
          name: '广元市',
          level: 'city',
          center: [105.8438, 32.4353],
          zoom: 10,
          children: [
            { code: 'guangyuan-lizhou', adcode: '510802', name: '利州区', level: 'county', center: [105.8450, 32.4350], zoom: 12 },
            { code: 'guangyuan-zhaohua', adcode: '510811', name: '昭化区', level: 'county', center: [105.9640, 32.3230], zoom: 12 },
            { code: 'guangyuan-chaotian', adcode: '510812', name: '朝天区', level: 'county', center: [105.8910, 32.6440], zoom: 12 },
            { code: 'guangyuan-wangcang', adcode: '510821', name: '旺苍县', level: 'county', center: [106.2900, 32.2290], zoom: 12 },
            { code: 'guangyuan-qingchuan', adcode: '510822', name: '青川县', level: 'county', center: [105.2410, 32.5850], zoom: 12 },
            { code: 'guangyuan-jiange', adcode: '510823', name: '剑阁县', level: 'county', center: [105.5250, 32.2880], zoom: 12 },
            { code: 'guangyuan-cangxi', adcode: '510824', name: '苍溪县', level: 'county', center: [105.9360, 31.7330], zoom: 12 }
          ]
        },
        {
          code: 'suining',
          adcode: '510900',
          name: '遂宁市',
          level: 'city',
          center: [105.5713, 30.5133],
          zoom: 10,
          children: [
            { code: 'suining-chuanqu', adcode: '510903', name: '船山区', level: 'county', center: [105.5680, 30.5260], zoom: 12 },
            { code: 'suining-anju', adcode: '510904', name: '安居区', level: 'county', center: [105.4640, 30.3550], zoom: 12 },
            { code: 'suining-pengxi', adcode: '510921', name: '蓬溪县', level: 'county', center: [105.7080, 30.7580], zoom: 12 },
            { code: 'suining-shehong', adcode: '510922', name: '射洪市', level: 'county', center: [105.3880, 30.8720], zoom: 12 },
            { code: 'suining-daying', adcode: '510923', name: '大英县', level: 'county', center: [105.2360, 30.5940], zoom: 12 }
          ]
        },
        {
          code: 'neijiang',
          adcode: '511000',
          name: '内江市',
          level: 'city',
          center: [105.0584, 29.5802],
          zoom: 10,
          children: [
            { code: 'neijiang-shizhong', adcode: '511002', name: '市中区', level: 'county', center: [105.0670, 29.5880], zoom: 12 },
            { code: 'neijiang-dongxing', adcode: '511011', name: '东兴区', level: 'county', center: [105.0750, 29.5930], zoom: 12 },
            { code: 'neijiang-weiyuan', adcode: '511024', name: '威远县', level: 'county', center: [104.6690, 29.5280], zoom: 12 },
            { code: 'neijiang-zizhong', adcode: '511025', name: '资中县', level: 'county', center: [104.8520, 29.7640], zoom: 12 },
            { code: 'neijiang-longchang', adcode: '511028', name: '隆昌市', level: 'county', center: [105.2870, 29.3390], zoom: 12 }
          ]
        },
        {
          code: 'leshan',
          adcode: '511100',
          name: '乐山市',
          level: 'city',
          center: [103.7656, 29.5521],
          zoom: 10,
          children: [
            { code: 'leshan-shizhong', adcode: '511102', name: '市中区', level: 'county', center: [103.7610, 29.5550], zoom: 12 },
            { code: 'leshan-shawan', adcode: '511111', name: '沙湾区', level: 'county', center: [103.5500, 29.4130], zoom: 12 },
            { code: 'leshan-wutongqiao', adcode: '511112', name: '五通桥区', level: 'county', center: [103.8230, 29.4070], zoom: 12 },
            { code: 'leshan-jinkouhe', adcode: '511113', name: '金口河区', level: 'county', center: [103.0790, 29.2450], zoom: 12 },
            { code: 'leshan-qianwei', adcode: '511123', name: '犍为县', level: 'county', center: [103.9490, 29.2080], zoom: 12 },
            { code: 'leshan-jingyan', adcode: '511124', name: '井研县', level: 'county', center: [104.0700, 29.6520], zoom: 12 },
            { code: 'leshan-jiajiang', adcode: '511126', name: '夹江县', level: 'county', center: [103.5720, 29.7380], zoom: 12 },
            { code: 'leshan-muchuan', adcode: '511129', name: '沐川县', level: 'county', center: [103.9030, 28.9560], zoom: 12 },
            { code: 'leshan-ebian', adcode: '511132', name: '峨边彝族自治县', level: 'county', center: [103.2620, 29.2300], zoom: 12 },
            { code: 'leshan-mabian', adcode: '511133', name: '马边彝族自治县', level: 'county', center: [103.5460, 28.8360], zoom: 12 },
            { code: 'leshan-emeishan', adcode: '511181', name: '峨眉山市', level: 'county', center: [103.4850, 29.6000], zoom: 12 }
          ]
        },
        {
          code: 'nanchong',
          adcode: '511300',
          name: '南充市',
          level: 'city',
          center: [106.1107, 30.8378],
          zoom: 10,
          children: [
            { code: 'nanchong-shunqing', adcode: '511302', name: '顺庆区', level: 'county', center: [106.0920, 30.7960], zoom: 12 },
            { code: 'nanchong-gaoping', adcode: '511303', name: '高坪区', level: 'county', center: [106.1190, 30.7820], zoom: 12 },
            { code: 'nanchong-jialing', adcode: '511304', name: '嘉陵区', level: 'county', center: [106.0720, 30.7580], zoom: 12 },
            { code: 'nanchong-nanbu', adcode: '511321', name: '南部县', level: 'county', center: [106.0670, 31.3540], zoom: 12 },
            { code: 'nanchong-yingshan', adcode: '511322', name: '营山县', level: 'county', center: [106.5660, 31.0760], zoom: 12 },
            { code: 'nanchong-pengan', adcode: '511323', name: '蓬安县', level: 'county', center: [106.4120, 31.0290], zoom: 12 },
            { code: 'nanchong-yilong', adcode: '511324', name: '仪陇县', level: 'county', center: [106.3030, 31.2710], zoom: 12 },
            { code: 'nanchong-xichong', adcode: '511325', name: '西充县', level: 'county', center: [105.9000, 30.9960], zoom: 12 },
            { code: 'nanchong-langzhong', adcode: '511381', name: '阆中市', level: 'county', center: [105.9760, 31.5580], zoom: 12 }
          ]
        },
        {
          code: 'meishan',
          adcode: '511400',
          name: '眉山市',
          level: 'city',
          center: [103.8485, 30.0759],
          zoom: 10,
          children: [
            { code: 'meishan-dongpo', adcode: '511402', name: '东坡区', level: 'county', center: [103.8320, 30.0430], zoom: 12 },
            { code: 'meishan-pengshan', adcode: '511403', name: '彭山区', level: 'county', center: [103.8730, 30.1930], zoom: 12 },
            { code: 'meishan-renshou', adcode: '511421', name: '仁寿县', level: 'county', center: [104.1340, 29.9960], zoom: 12 },
            { code: 'meishan-hongya', adcode: '511423', name: '洪雅县', level: 'county', center: [103.3730, 29.9060], zoom: 12 },
            { code: 'meishan-danleng', adcode: '511424', name: '丹棱县', level: 'county', center: [103.5130, 30.0150], zoom: 12 },
            { code: 'meishan-qingshen', adcode: '511425', name: '青神县', level: 'county', center: [103.8480, 29.8320], zoom: 12 }
          ]
        },
        {
          code: 'yibin',
          adcode: '511500',
          name: '宜宾市',
          level: 'city',
          center: [104.6308, 28.7602],
          zoom: 10,
          children: [
            { code: 'yibin-cuiping', adcode: '511502', name: '翠屏区', level: 'county', center: [104.6200, 28.7650], zoom: 12 },
            { code: 'yibin-nanxi', adcode: '511503', name: '南溪区', level: 'county', center: [104.9810, 28.8460], zoom: 12 },
            { code: 'yibin-xuzhou', adcode: '511504', name: '叙州区', level: 'county', center: [104.5330, 28.6890], zoom: 12 },
            { code: 'yibin-jiangan', adcode: '511523', name: '江安县', level: 'county', center: [105.0670, 28.7140], zoom: 12 },
            { code: 'yibin-changning', adcode: '511524', name: '长宁县', level: 'county', center: [104.8700, 28.5820], zoom: 12 },
            { code: 'yibin-gao', adcode: '511525', name: '高县', level: 'county', center: [104.5180, 28.4360], zoom: 12 },
            { code: 'yibin-gong', adcode: '511526', name: '珙县', level: 'county', center: [104.7100, 28.4450], zoom: 12 },
            { code: 'yibin-lian', adcode: '511527', name: '筠连县', level: 'county', center: [104.5120, 28.1640], zoom: 12 },
            { code: 'yibin-xingwen', adcode: '511528', name: '兴文县', level: 'county', center: [105.2360, 28.3040], zoom: 12 },
            { code: 'yibin-pingshan', adcode: '511529', name: '屏山县', level: 'county', center: [104.1630, 28.6430], zoom: 12 }
          ]
        },
        {
          code: 'guangan',
          adcode: '511600',
          name: '广安市',
          level: 'city',
          center: [106.6333, 30.4564],
          zoom: 10,
          children: [
            { code: 'guangan-guangan', adcode: '511602', name: '广安区', level: 'county', center: [106.6420, 30.4740], zoom: 12 },
            { code: 'guangan-qianfeng', adcode: '511603', name: '前锋区', level: 'county', center: [106.8930, 30.4960], zoom: 12 },
            { code: 'guangan-yuechi', adcode: '511621', name: '岳池县', level: 'county', center: [106.4400, 30.5390], zoom: 12 },
            { code: 'guangan-wusheng', adcode: '511622', name: '武胜县', level: 'county', center: [106.2960, 30.3490], zoom: 12 },
            { code: 'guangan-linshui', adcode: '511623', name: '邻水县', level: 'county', center: [106.9300, 30.3350], zoom: 12 },
            { code: 'guangan-huaying', adcode: '511681', name: '华蓥市', level: 'county', center: [106.7830, 30.3900], zoom: 12 }
          ]
        },
        {
          code: 'dazhou',
          adcode: '511700',
          name: '达州市',
          level: 'city',
          center: [107.5023, 31.2090],
          zoom: 10,
          children: [
            { code: 'dazhou-tongchuan', adcode: '511702', name: '通川区', level: 'county', center: [107.5050, 31.2150], zoom: 12 },
            { code: 'dazhou-dachuan', adcode: '511703', name: '达川区', level: 'county', center: [107.5110, 31.1960], zoom: 12 },
            { code: 'dazhou-xuanhan', adcode: '511722', name: '宣汉县', level: 'county', center: [107.7280, 31.3560], zoom: 12 },
            { code: 'dazhou-kaijiang', adcode: '511723', name: '开江县', level: 'county', center: [107.8680, 31.0830], zoom: 12 },
            { code: 'dazhou-dazhu', adcode: '511724', name: '大竹县', level: 'county', center: [107.2050, 30.7360], zoom: 12 },
            { code: 'dazhou-qu', adcode: '511725', name: '渠县', level: 'county', center: [106.9730, 30.8370], zoom: 12 },
            { code: 'dazhou-wanyuan', adcode: '511781', name: '万源市', level: 'county', center: [108.0360, 32.0820], zoom: 12 }
          ]
        },
        {
          code: 'yaan',
          adcode: '511800',
          name: '雅安市',
          level: 'city',
          center: [103.0010, 29.9877],
          zoom: 10,
          children: [
            { code: 'yaan-yucheng', adcode: '511802', name: '雨城区', level: 'county', center: [103.0050, 29.9800], zoom: 12 },
            { code: 'yaan-mingshan', adcode: '511803', name: '名山区', level: 'county', center: [103.1120, 30.0690], zoom: 12 },
            { code: 'yaan-yingjing', adcode: '511822', name: '荥经县', level: 'county', center: [102.8470, 29.7950], zoom: 12 },
            { code: 'yaan-hanyuan', adcode: '511823', name: '汉源县', level: 'county', center: [102.6780, 29.3520], zoom: 12 },
            { code: 'yaan-shimian', adcode: '511824', name: '石棉县', level: 'county', center: [102.3590, 29.2280], zoom: 12 },
            { code: 'yaan-tianquan', adcode: '511825', name: '天全县', level: 'county', center: [102.7580, 30.0660], zoom: 12 },
            { code: 'yaan-lushan', adcode: '511826', name: '芦山县', level: 'county', center: [102.9280, 30.1440], zoom: 12 },
            { code: 'yaan-baoxing', adcode: '511827', name: '宝兴县', level: 'county', center: [102.8150, 30.3680], zoom: 12 }
          ]
        },
        {
          code: 'bazhong',
          adcode: '511900',
          name: '巴中市',
          level: 'city',
          center: [106.7537, 31.8588],
          zoom: 10,
          children: [
            { code: 'bazhong-bazhou', adcode: '511902', name: '巴州区', level: 'county', center: [106.7690, 31.8590], zoom: 12 },
            { code: 'bazhong-enyang', adcode: '511903', name: '恩阳区', level: 'county', center: [106.6300, 31.7890], zoom: 12 },
            { code: 'bazhong-tongjiang', adcode: '511921', name: '通江县', level: 'county', center: [107.2450, 31.9120], zoom: 12 },
            { code: 'bazhong-nanjiang', adcode: '511922', name: '南江县', level: 'county', center: [106.8290, 32.3530], zoom: 12 },
            { code: 'bazhong-pingchang', adcode: '511923', name: '平昌县', level: 'county', center: [107.1040, 31.5600], zoom: 12 }
          ]
        },
        {
          code: 'ziyang',
          adcode: '512000',
          name: '资阳市',
          level: 'city',
          center: [104.6419, 30.1222],
          zoom: 10,
          children: [
            { code: 'ziyang-yanjiang', adcode: '512002', name: '雁江区', level: 'county', center: [104.6420, 30.1150], zoom: 12 },
            { code: 'ziyang-anyue', adcode: '512021', name: '安岳县', level: 'county', center: [105.3360, 30.0970], zoom: 12 },
            { code: 'ziyang-lezhi', adcode: '512022', name: '乐至县', level: 'county', center: [105.0320, 30.2760], zoom: 12 }
          ]
        },
        {
          code: 'aba',
          adcode: '513200',
          name: '阿坝藏族羌族自治州',
          level: 'city',
          center: [102.2214, 31.8998],
          zoom: 8,
          children: [
            { code: 'aba-maerkang', adcode: '513229', name: '马尔康市', level: 'county', center: [102.2060, 31.9060], zoom: 12 },
            { code: 'aba-wenchuan', adcode: '513221', name: '汶川县', level: 'county', center: [103.5910, 31.4770], zoom: 12 },
            { code: 'aba-li', adcode: '513222', name: '理县', level: 'county', center: [103.1670, 31.4360], zoom: 12 },
            { code: 'aba-mao', adcode: '513223', name: '茂县', level: 'county', center: [103.8530, 31.6820], zoom: 12 },
            { code: 'aba-songpan', adcode: '513224', name: '松潘县', level: 'county', center: [103.5990, 32.6380], zoom: 12 },
            { code: 'aba-jiuzhaigou', adcode: '513225', name: '九寨沟县', level: 'county', center: [103.9200, 33.2620], zoom: 12 },
            { code: 'aba-jinchuan', adcode: '513226', name: '金川县', level: 'county', center: [102.0650, 31.4760], zoom: 12 },
            { code: 'aba-xiaojin', adcode: '513227', name: '小金县', level: 'county', center: [102.3640, 30.9990], zoom: 12 },
            { code: 'aba-heishui', adcode: '513228', name: '黑水县', level: 'county', center: [102.9910, 32.0620], zoom: 12 },
            { code: 'aba-rangtang', adcode: '513230', name: '壤塘县', level: 'county', center: [100.9780, 32.2650], zoom: 12 },
            { code: 'aba-aba', adcode: '513231', name: '阿坝县', level: 'county', center: [101.7060, 32.9030], zoom: 12 },
            { code: 'aba-ruoergai', adcode: '513232', name: '若尔盖县', level: 'county', center: [102.9610, 33.5760], zoom: 12 },
            { code: 'aba-hongyuan', adcode: '513233', name: '红原县', level: 'county', center: [102.5450, 32.7910], zoom: 12 }
          ]
        },
        {
          code: 'ganzi',
          adcode: '513300',
          name: '甘孜藏族自治州',
          level: 'city',
          center: [101.9638, 30.0509],
          zoom: 7,
          children: [
            { code: 'ganzi-kangding', adcode: '513301', name: '康定市', level: 'county', center: [101.9580, 30.0450], zoom: 12 },
            { code: 'ganzi-luding', adcode: '513322', name: '泸定县', level: 'county', center: [102.2350, 29.9140], zoom: 12 },
            { code: 'ganzi-danba', adcode: '513323', name: '丹巴县', level: 'county', center: [101.8900, 30.8790], zoom: 12 },
            { code: 'ganzi-jiulong', adcode: '513324', name: '九龙县', level: 'county', center: [101.5080, 29.0000], zoom: 12 },
            { code: 'ganzi-yajiang', adcode: '513325', name: '雅江县', level: 'county', center: [101.0140, 30.0320], zoom: 12 },
            { code: 'ganzi-daofu', adcode: '513326', name: '道孚县', level: 'county', center: [101.1260, 30.9800], zoom: 12 },
            { code: 'ganzi-luhuo', adcode: '513327', name: '炉霍县', level: 'county', center: [100.6760, 31.3920], zoom: 12 },
            { code: 'ganzi-garze', adcode: '513328', name: '甘孜县', level: 'county', center: [99.9930, 31.6230], zoom: 12 },
            { code: 'ganzi-xinlong', adcode: '513329', name: '新龙县', level: 'county', center: [100.3120, 30.9390], zoom: 12 },
            { code: 'ganzi-dege', adcode: '513330', name: '德格县', level: 'county', center: [98.5810, 31.8060], zoom: 12 },
            { code: 'ganzi-baiyu', adcode: '513331', name: '白玉县', level: 'county', center: [98.8250, 31.2100], zoom: 12 },
            { code: 'ganzi-shiqu', adcode: '513332', name: '石渠县', level: 'county', center: [98.1030, 32.9780], zoom: 12 },
            { code: 'ganzi-serda', adcode: '513333', name: '色达县', level: 'county', center: [100.3320, 32.2680], zoom: 12 },
            { code: 'ganzi-litang', adcode: '513334', name: '理塘县', level: 'county', center: [100.2700, 29.9960], zoom: 12 },
            { code: 'ganzi-batang', adcode: '513335', name: '巴塘县', level: 'county', center: [99.1110, 30.0050], zoom: 12 },
            { code: 'ganzi-xiangcheng', adcode: '513336', name: '乡城县', level: 'county', center: [99.7990, 28.9310], zoom: 12 },
            { code: 'ganzi-daocheng', adcode: '513337', name: '稻城县', level: 'county', center: [100.2980, 29.0380], zoom: 12 },
            { code: 'ganzi-derong', adcode: '513338', name: '得荣县', level: 'county', center: [99.2860, 28.7130], zoom: 12 }
          ]
        },
        {
          code: 'liangshan',
          adcode: '513400',
          name: '凉山彝族自治州',
          level: 'city',
          center: [102.2587, 27.8868],
          zoom: 8,
          children: [
            { code: 'liangshan-xichang', adcode: '513401', name: '西昌市', level: 'county', center: [102.2640, 27.8940], zoom: 12 },
            { code: 'liangshan-muli', adcode: '513422', name: '木里藏族自治县', level: 'county', center: [101.2860, 27.9290], zoom: 12 },
            { code: 'liangshan-yanyuan', adcode: '513423', name: '盐源县', level: 'county', center: [101.5100, 27.4220], zoom: 12 },
            { code: 'liangshan-dechang', adcode: '513424', name: '德昌县', level: 'county', center: [102.1800, 27.4040], zoom: 12 },
            { code: 'liangshan-huili', adcode: '513425', name: '会理市', level: 'county', center: [102.2450, 26.6550], zoom: 12 },
            { code: 'liangshan-huiddong', adcode: '513426', name: '会东县', level: 'county', center: [102.5780, 26.6350], zoom: 12 },
            { code: 'liangshan-ningnan', adcode: '513427', name: '宁南县', level: 'county', center: [102.7610, 27.0660], zoom: 12 },
            { code: 'liangshan-puge', adcode: '513428', name: '普格县', level: 'county', center: [102.5410, 27.3770], zoom: 12 },
            { code: 'liangshan-butuo', adcode: '513429', name: '布拖县', level: 'county', center: [102.8120, 27.7080], zoom: 12 },
            { code: 'liangshan-jinyang', adcode: '513430', name: '金阳县', level: 'county', center: [103.2490, 27.6970], zoom: 12 },
            { code: 'liangshan-zhaojue', adcode: '513431', name: '昭觉县', level: 'county', center: [102.8430, 28.0110], zoom: 12 },
            { code: 'liangshan-xide', adcode: '513432', name: '喜德县', level: 'county', center: [102.4430, 28.3070], zoom: 12 },
            { code: 'liangshan-mianning', adcode: '513433', name: '冕宁县', level: 'county', center: [102.1780, 28.5510], zoom: 12 },
            { code: 'liangshan-yuexi', adcode: '513434', name: '越西县', level: 'county', center: [102.5080, 28.6410], zoom: 12 },
            { code: 'liangshan-ganluo', adcode: '513435', name: '甘洛县', level: 'county', center: [102.7720, 28.9660], zoom: 12 },
            { code: 'liangshan-meigu', adcode: '513436', name: '美姑县', level: 'county', center: [103.1320, 28.3290], zoom: 12 },
            { code: 'liangshan-leibo', adcode: '513437', name: '雷波县', level: 'county', center: [103.5720, 28.2630], zoom: 12 }
          ]
        }
      ]
    },
    {
      code: 'hubei',
      adcode: '420000',
      name: '湖北省',
      level: 'province',
      center: [114.2986, 30.5844],
      zoom: 7,
      children: [
        {
          code: 'wuhan',
          adcode: '420100',
          name: '武汉市',
          level: 'city',
          center: [114.2986, 30.5844],
          zoom: 10,
          children: [
            { code: 'wuhan-jiangan', adcode: '420102', name: '江岸区', level: 'county', center: [114.3090, 30.6000], zoom: 12 },
            { code: 'wuhan-jianghan', adcode: '420103', name: '江汉区', level: 'county', center: [114.2710, 30.6010], zoom: 12 },
            { code: 'wuhan-qiaokou', adcode: '420104', name: '硚口区', level: 'county', center: [114.2150, 30.5820], zoom: 12 },
            { code: 'wuhan-hanyang', adcode: '420105', name: '汉阳区', level: 'county', center: [114.2180, 30.5540], zoom: 12 },
            { code: 'wuhan-wuchang', adcode: '420106', name: '武昌区', level: 'county', center: [114.3160, 30.5540], zoom: 12 },
            { code: 'wuhan-qingshan', adcode: '420107', name: '青山区', level: 'county', center: [114.3850, 30.6400], zoom: 12 },
            { code: 'wuhan-hongshan', adcode: '420111', name: '洪山区', level: 'county', center: [114.3430, 30.5000], zoom: 12 },
            { code: 'wuhan-dongxihu', adcode: '420112', name: '东西湖区', level: 'county', center: [114.1370, 30.6200], zoom: 12 },
            { code: 'wuhan-hannan', adcode: '420113', name: '汉南区', level: 'county', center: [114.0840, 30.3090], zoom: 12 },
            { code: 'wuhan-caidian', adcode: '420114', name: '蔡甸区', level: 'county', center: [114.0290, 30.5820], zoom: 12 },
            { code: 'wuhan-jiangxia', adcode: '420115', name: '江夏区', level: 'county', center: [114.3210, 30.3470], zoom: 12 },
            { code: 'wuhan-huangpi', adcode: '420116', name: '黄陂区', level: 'county', center: [114.3750, 30.8820], zoom: 12 },
            { code: 'wuhan-xinzhou', adcode: '420117', name: '新洲区', level: 'county', center: [114.8010, 30.8410], zoom: 12 }
          ]
        },
        {
          code: 'huangshi',
          adcode: '420200',
          name: '黄石市',
          level: 'city',
          center: [115.0386, 29.9986],
          zoom: 10,
          children: [
            { code: 'huangshi-huangshigang', adcode: '420202', name: '黄石港区', level: 'county', center: [115.0660, 30.2220], zoom: 12 },
            { code: 'huangshi-xisaishan', adcode: '420203', name: '西塞山区', level: 'county', center: [115.1100, 30.2040], zoom: 12 },
            { code: 'huangshi-xialu', adcode: '420204', name: '下陆区', level: 'county', center: [114.9610, 30.1740], zoom: 12 },
            { code: 'huangshi-tieshan', adcode: '420205', name: '铁山区', level: 'county', center: [114.9010, 30.2070], zoom: 12 },
            { code: 'huangshi-yangxin', adcode: '420222', name: '阳新县', level: 'county', center: [115.2150, 29.8330], zoom: 12 },
            { code: 'huangshi-daye', adcode: '420281', name: '大冶市', level: 'county', center: [114.9790, 30.0950], zoom: 12 }
          ]
        },
        {
          code: 'shiyan',
          adcode: '420300',
          name: '十堰市',
          level: 'city',
          center: [110.7879, 32.6290],
          zoom: 10,
          children: [
            { code: 'shiyan-maojian', adcode: '420302', name: '茅箭区', level: 'county', center: [110.8120, 32.5920], zoom: 12 },
            { code: 'shiyan-zhangwan', adcode: '420303', name: '张湾区', level: 'county', center: [110.7710, 32.6520], zoom: 12 },
            { code: 'shiyan-yunyang', adcode: '420304', name: '郧阳区', level: 'county', center: [110.8180, 32.8350], zoom: 12 },
            { code: 'shiyan-yunxi', adcode: '420322', name: '郧西县', level: 'county', center: [110.4260, 32.9930], zoom: 12 },
            { code: 'shiyan-zhushan', adcode: '420323', name: '竹山县', level: 'county', center: [110.2280, 32.2250], zoom: 12 },
            { code: 'shiyan-zhuxi', adcode: '420324', name: '竹溪县', level: 'county', center: [109.7170, 32.3180], zoom: 12 },
            { code: 'shiyan-fangxian', adcode: '420325', name: '房县', level: 'county', center: [110.7440, 32.0570], zoom: 12 },
            { code: 'shiyan-danjiangkou', adcode: '420381', name: '丹江口市', level: 'county', center: [111.5130, 32.5400], zoom: 12 }
          ]
        },
        {
          code: 'yichang',
          adcode: '420500',
          name: '宜昌市',
          level: 'city',
          center: [111.2866, 30.6919],
          zoom: 10,
          children: [
            { code: 'yichang-xiling', adcode: '420502', name: '西陵区', level: 'county', center: [111.2860, 30.7110], zoom: 12 },
            { code: 'yichang-wujiagang', adcode: '420503', name: '伍家岗区', level: 'county', center: [111.3610, 30.6440], zoom: 12 },
            { code: 'yichang-dianjun', adcode: '420504', name: '点军区', level: 'county', center: [111.2680, 30.6930], zoom: 12 },
            { code: 'yichang-xiaoting', adcode: '420505', name: '猇亭区', level: 'county', center: [111.4350, 30.5290], zoom: 12 },
            { code: 'yichang-yiling', adcode: '420506', name: '夷陵区', level: 'county', center: [111.3260, 30.7700], zoom: 12 },
            { code: 'yichang-yuanan', adcode: '420525', name: '远安县', level: 'county', center: [111.6410, 31.0610], zoom: 12 },
            { code: 'yichang-xingshan', adcode: '420526', name: '兴山县', level: 'county', center: [110.7470, 31.3470], zoom: 12 },
            { code: 'yichang-zigui', adcode: '420527', name: '秭归县', level: 'county', center: [110.6410, 30.8230], zoom: 12 },
            { code: 'yichang-changyang', adcode: '420528', name: '长阳土家族自治县', level: 'county', center: [111.2070, 30.4730], zoom: 12 },
            { code: 'yichang-wufeng', adcode: '420529', name: '五峰土家族自治县', level: 'county', center: [110.6740, 30.2000], zoom: 12 },
            { code: 'yichang-yidu', adcode: '420581', name: '宜都市', level: 'county', center: [111.4500, 30.3780], zoom: 12 },
            { code: 'yichang-dangyang', adcode: '420582', name: '当阳市', level: 'county', center: [111.7890, 30.8210], zoom: 12 },
            { code: 'yichang-zhijiang', adcode: '420583', name: '枝江市', level: 'county', center: [111.7610, 30.4260], zoom: 12 }
          ]
        },
        {
          code: 'xiangyang',
          adcode: '420600',
          name: '襄阳市',
          level: 'city',
          center: [112.1226, 32.0091],
          zoom: 10,
          children: [
            { code: 'xiangyang-xiangcheng', adcode: '420602', name: '襄城区', level: 'county', center: [112.1330, 32.0100], zoom: 12 },
            { code: 'xiangyang-fancheng', adcode: '420606', name: '樊城区', level: 'county', center: [112.1250, 32.0440], zoom: 12 },
            { code: 'xiangyang-xiangzhou', adcode: '420607', name: '襄州区', level: 'county', center: [112.2120, 32.0860], zoom: 12 },
            { code: 'xiangyang-nanzhang', adcode: '420624', name: '南漳县', level: 'county', center: [111.8390, 31.7750], zoom: 12 },
            { code: 'xiangyang-gucheng', adcode: '420625', name: '谷城县', level: 'county', center: [111.6530, 32.2630], zoom: 12 },
            { code: 'xiangyang-baokang', adcode: '420626', name: '保康县', level: 'county', center: [111.2610, 31.8780], zoom: 12 },
            { code: 'xiangyang-laohekou', adcode: '420682', name: '老河口市', level: 'county', center: [111.6760, 32.3850], zoom: 12 },
            { code: 'xiangyang-zhaoyang', adcode: '420683', name: '枣阳市', level: 'county', center: [112.7740, 32.1280], zoom: 12 },
            { code: 'xiangyang-yicheng', adcode: '420684', name: '宜城市', level: 'county', center: [112.2580, 31.7200], zoom: 12 }
          ]
        },
        {
          code: 'ezhou',
          adcode: '420700',
          name: '鄂州市',
          level: 'city',
          center: [114.8949, 30.3844],
          zoom: 10,
          children: [
            { code: 'ezhou-liangzihu', adcode: '420702', name: '梁子湖区', level: 'county', center: [114.6850, 30.1000], zoom: 12 },
            { code: 'ezhou-huarong', adcode: '420703', name: '华容区', level: 'county', center: [114.7300, 30.5340], zoom: 12 },
            { code: 'ezhou-echeng', adcode: '420704', name: '鄂城区', level: 'county', center: [114.8910, 30.4010], zoom: 12 }
          ]
        },
        {
          code: 'jingmen',
          adcode: '420800',
          name: '荆门市',
          level: 'city',
          center: [112.1991, 31.0354],
          zoom: 10,
          children: [
            { code: 'jingmen-dongbao', adcode: '420802', name: '东宝区', level: 'county', center: [112.2010, 31.0320], zoom: 12 },
            { code: 'jingmen-daohe', adcode: '420804', name: '掇刀区', level: 'county', center: [112.1980, 30.9730], zoom: 12 },
            { code: 'jingmen-jingshan', adcode: '420821', name: '京山市', level: 'county', center: [113.1190, 31.0180], zoom: 12 },
            { code: 'jingmen-shayang', adcode: '420822', name: '沙洋县', level: 'county', center: [112.3880, 30.7090], zoom: 12 },
            { code: 'jingmen-zhongxiang', adcode: '420881', name: '钟祥市', level: 'county', center: [112.5890, 31.1680], zoom: 12 }
          ]
        },
        {
          code: 'xiaogan',
          adcode: '420900',
          name: '孝感市',
          level: 'city',
          center: [113.9270, 30.9264],
          zoom: 10,
          children: [
            { code: 'xiaogan-xiaonan', adcode: '420902', name: '孝南区', level: 'county', center: [113.9110, 30.9170], zoom: 12 },
            { code: 'xiaogan-xiaochang', adcode: '420921', name: '孝昌县', level: 'county', center: [113.9980, 31.2580], zoom: 12 },
            { code: 'xiaogan-dawu', adcode: '420922', name: '大悟县', level: 'county', center: [114.1270, 31.5610], zoom: 12 },
            { code: 'xiaogan-yunmeng', adcode: '420923', name: '云梦县', level: 'county', center: [113.7530, 31.0210], zoom: 12 },
            { code: 'xiaogan-yingcheng', adcode: '420981', name: '应城市', level: 'county', center: [113.5730, 30.9280], zoom: 12 },
            { code: 'xiaogan-anlu', adcode: '420982', name: '安陆市', level: 'county', center: [113.6880, 31.2560], zoom: 12 },
            { code: 'xiaogan-hanchuan', adcode: '420984', name: '汉川市', level: 'county', center: [113.8390, 30.6610], zoom: 12 }
          ]
        },
        {
          code: 'jingzhou',
          adcode: '421000',
          name: '荆州市',
          level: 'city',
          center: [112.2397, 30.3352],
          zoom: 10,
          children: [
            { code: 'jingzhou-shashi', adcode: '421002', name: '沙市区', level: 'county', center: [112.2550, 30.3110], zoom: 12 },
            { code: 'jingzhou-jingzhou', adcode: '421003', name: '荆州区', level: 'county', center: [112.1990, 30.3530], zoom: 12 },
            { code: 'jingzhou-gongan', adcode: '421022', name: '公安县', level: 'county', center: [112.2290, 30.0590], zoom: 12 },
            { code: 'jingzhou-jianli', adcode: '421023', name: '监利市', level: 'county', center: [112.8950, 29.8110], zoom: 12 },
            { code: 'jingzhou-jiangling', adcode: '421024', name: '江陵县', level: 'county', center: [112.4160, 30.0340], zoom: 12 },
            { code: 'jingzhou-shishou', adcode: '421081', name: '石首市', level: 'county', center: [112.4260, 29.7210], zoom: 12 },
            { code: 'jingzhou-honghu', adcode: '421083', name: '洪湖市', level: 'county', center: [113.4670, 29.8060], zoom: 12 },
            { code: 'jingzhou-songzi', adcode: '421087', name: '松滋市', level: 'county', center: [111.7670, 30.1690], zoom: 12 }
          ]
        },
        {
          code: 'huanggang',
          adcode: '421100',
          name: '黄冈市',
          level: 'city',
          center: [114.8722, 30.4534],
          zoom: 10,
          children: [
            { code: 'huanggang-huangzhou', adcode: '421102', name: '黄州区', level: 'county', center: [114.8720, 30.4340], zoom: 12 },
            { code: 'huanggang-tuanfeng', adcode: '421121', name: '团风县', level: 'county', center: [114.8720, 30.6440], zoom: 12 },
            { code: 'huanggang-hongan', adcode: '421122', name: '红安县', level: 'county', center: [114.6170, 31.2880], zoom: 12 },
            { code: 'huanggang-luotian', adcode: '421123', name: '罗田县', level: 'county', center: [115.3990, 30.7830], zoom: 12 },
            { code: 'huanggang-yingshan', adcode: '421124', name: '英山县', level: 'county', center: [115.6810, 30.7350], zoom: 12 },
            { code: 'huanggang-xishui', adcode: '421125', name: '浠水县', level: 'county', center: [115.2650, 30.4520], zoom: 12 },
            { code: 'huanggang-qichun', adcode: '421126', name: '蕲春县', level: 'county', center: [115.4360, 30.2260], zoom: 12 },
            { code: 'huanggang-huangmei', adcode: '421127', name: '黄梅县', level: 'county', center: [115.9440, 30.0700], zoom: 12 },
            { code: 'huanggang-macheng', adcode: '421181', name: '麻城市', level: 'county', center: [115.0100, 31.1730], zoom: 12 },
            { code: 'huanggang-wuxue', adcode: '421182', name: '武穴市', level: 'county', center: [115.5610, 29.8440], zoom: 12 }
          ]
        },
        {
          code: 'xianning',
          adcode: '421200',
          name: '咸宁市',
          level: 'city',
          center: [114.3210, 29.8414],
          zoom: 10,
          children: [
            { code: 'xianning-xianan', adcode: '421202', name: '咸安区', level: 'county', center: [114.2990, 29.8270], zoom: 12 },
            { code: 'xianning-jiayu', adcode: '421221', name: '嘉鱼县', level: 'county', center: [113.9390, 29.9710], zoom: 12 },
            { code: 'xianning-tongcheng', adcode: '421222', name: '通城县', level: 'county', center: [113.8170, 29.2450], zoom: 12 },
            { code: 'xianning-chongyang', adcode: '421223', name: '崇阳县', level: 'county', center: [114.0390, 29.5550], zoom: 12 },
            { code: 'xianning-tongshan', adcode: '421224', name: '通山县', level: 'county', center: [114.4830, 29.6060], zoom: 12 },
            { code: 'xianning-chibi', adcode: '421281', name: '赤壁市', level: 'county', center: [113.9000, 29.7250], zoom: 12 }
          ]
        },
        {
          code: 'suizhou',
          adcode: '421300',
          name: '随州市',
          level: 'city',
          center: [113.3826, 31.6904],
          zoom: 10,
          children: [
            { code: 'suizhou-zengdu', adcode: '421303', name: '曾都区', level: 'county', center: [113.3740, 31.7170], zoom: 12 },
            { code: 'suizhou-suixian', adcode: '421321', name: '随县', level: 'county', center: [113.2990, 31.8540], zoom: 12 },
            { code: 'suizhou-guangshui', adcode: '421381', name: '广水市', level: 'county', center: [113.8270, 31.6170], zoom: 12 }
          ]
        },
        {
          code: 'enshi',
          adcode: '422800',
          name: '恩施土家族苗族自治州',
          level: 'city',
          center: [109.4868, 30.2722],
          zoom: 9,
          children: [
            { code: 'enshi-enishi', adcode: '422801', name: '恩施市', level: 'county', center: [109.4790, 30.2950], zoom: 12 },
            { code: 'enshi-lishui', adcode: '422802', name: '利川市', level: 'county', center: [108.9360, 30.2910], zoom: 12 },
            { code: 'enshi-jianshi', adcode: '422822', name: '建始县', level: 'county', center: [109.7220, 30.6020], zoom: 12 },
            { code: 'enshi-badong', adcode: '422823', name: '巴东县', level: 'county', center: [110.3410, 31.0420], zoom: 12 },
            { code: 'enshi-xuanen', adcode: '422825', name: '宣恩县', level: 'county', center: [109.4920, 29.9870], zoom: 12 },
            { code: 'enshi-xianfeng', adcode: '422826', name: '咸丰县', level: 'county', center: [109.1400, 29.6650], zoom: 12 },
            { code: 'enshi-laifeng', adcode: '422827', name: '来凤县', level: 'county', center: [109.4070, 29.4930], zoom: 12 },
            { code: 'enshi-heshan', adcode: '422828', name: '鹤峰县', level: 'county', center: [110.0340, 29.8900], zoom: 12 }
          ]
        },
        {
          code: 'xiantao',
          adcode: '429004',
          name: '仙桃市',
          level: 'city',
          center: [113.4540, 30.3640],
          zoom: 10,
          children: []
        },
        {
          code: 'qianjiang',
          adcode: '429005',
          name: '潜江市',
          level: 'city',
          center: [112.9000, 30.4020],
          zoom: 10,
          children: []
        },
        {
          code: 'tianmen',
          adcode: '429006',
          name: '天门市',
          level: 'city',
          center: [113.1660, 30.6530],
          zoom: 10,
          children: []
        },
        {
          code: 'shennongjia',
          adcode: '429021',
          name: '神农架林区',
          level: 'city',
          center: [110.6760, 31.7440],
          zoom: 10,
          children: []
        }
      ]
    },
    {
      code: 'hunan',
      adcode: '430000',
      name: '湖南省',
      level: 'province',
      center: [112.9834, 28.1127],
      zoom: 7,
      children: [
        {
          code: 'changsha',
          adcode: '430100',
          name: '长沙市',
          level: 'city',
          center: [112.9834, 28.1127],
          zoom: 10,
          children: [
            { code: 'changsha-furong', adcode: '430102', name: '芙蓉区', level: 'county', center: [113.0320, 28.1850], zoom: 12 },
            { code: 'changsha-tianxin', adcode: '430103', name: '天心区', level: 'county', center: [112.9900, 28.1120], zoom: 12 },
            { code: 'changsha-yuelu', adcode: '430104', name: '岳麓区', level: 'county', center: [112.9310, 28.2350], zoom: 12 },
            { code: 'changsha-kaifu', adcode: '430105', name: '开福区', level: 'county', center: [112.9850, 28.2560], zoom: 12 },
            { code: 'changsha-yuhua', adcode: '430111', name: '雨花区', level: 'county', center: [113.0380, 28.1370], zoom: 12 },
            { code: 'changsha-wangcheng', adcode: '430112', name: '望城区', level: 'county', center: [112.8310, 28.3530], zoom: 12 },
            { code: 'changsha-changsha', adcode: '430121', name: '长沙县', level: 'county', center: [113.0800, 28.2450], zoom: 12 },
            { code: 'changsha-liuyang', adcode: '430181', name: '浏阳市', level: 'county', center: [113.6430, 28.1630], zoom: 12 },
            { code: 'changsha-ningxiang', adcode: '430182', name: '宁乡市', level: 'county', center: [112.5530, 28.2540], zoom: 12 }
          ]
        },
        {
          code: 'zhuzhou',
          adcode: '430200',
          name: '株洲市',
          level: 'city',
          center: [113.1339, 27.8274],
          zoom: 10,
          children: [
            { code: 'zhuzhou-hetang', adcode: '430202', name: '荷塘区', level: 'county', center: [113.1730, 27.8560], zoom: 12 },
            { code: 'zhuzhou-lusong', adcode: '430203', name: '芦淞区', level: 'county', center: [113.1520, 27.7860], zoom: 12 },
            { code: 'zhuzhou-shifeng', adcode: '430204', name: '石峰区', level: 'county', center: [113.1180, 27.8750], zoom: 12 },
            { code: 'zhuzhou-tianyuan', adcode: '430211', name: '天元区', level: 'county', center: [113.0820, 27.8260], zoom: 12 },
            { code: 'zhuzhou-liling', adcode: '430281', name: '醴陵市', level: 'county', center: [113.4970, 27.6460], zoom: 12 },
            { code: 'zhuzhou-zhuzhou', adcode: '430221', name: '株洲县', level: 'county', center: [113.1440, 27.7000], zoom: 12 },
            { code: 'zhuzhou-youxian', adcode: '430223', name: '攸县', level: 'county', center: [113.3440, 27.0000], zoom: 12 },
            { code: 'zhuzhou-chaling', adcode: '430224', name: '茶陵县', level: 'county', center: [113.5440, 26.7750], zoom: 12 },
            { code: 'zhuzhou-yanling', adcode: '430225', name: '炎陵县', level: 'county', center: [113.7720, 26.4890], zoom: 12 }
          ]
        },
        {
          code: 'xiangtan',
          adcode: '430300',
          name: '湘潭市',
          level: 'city',
          center: [112.9443, 27.8296],
          zoom: 10,
          children: [
            { code: 'xiangtan-yuhu', adcode: '430302', name: '雨湖区', level: 'county', center: [112.9040, 27.8710], zoom: 12 },
            { code: 'xiangtan-yuetang', adcode: '430304', name: '岳塘区', level: 'county', center: [112.9690, 27.8720], zoom: 12 },
            { code: 'xiangtan-xiangtan', adcode: '430321', name: '湘潭县', level: 'county', center: [112.9510, 27.7780], zoom: 12 },
            { code: 'xiangtan-xiangxiang', adcode: '430381', name: '湘乡市', level: 'county', center: [112.5350, 27.7340], zoom: 12 },
            { code: 'xiangtan-shaoshan', adcode: '430382', name: '韶山市', level: 'county', center: [112.5270, 27.9150], zoom: 12 }
          ]
        },
        {
          code: 'hengyang',
          adcode: '430400',
          name: '衡阳市',
          level: 'city',
          center: [112.5720, 26.8932],
          zoom: 10,
          children: [
            { code: 'hengyang-zhuhui', adcode: '430405', name: '珠晖区', level: 'county', center: [112.6200, 26.8940], zoom: 12 },
            { code: 'hengyang-yanfeng', adcode: '430406', name: '雁峰区', level: 'county', center: [112.5720, 26.8660], zoom: 12 },
            { code: 'hengyang-shigu', adcode: '430407', name: '石鼓区', level: 'county', center: [112.6240, 26.9540], zoom: 12 },
            { code: 'hengyang-zhengxiang', adcode: '430408', name: '蒸湘区', level: 'county', center: [112.5670, 26.9090], zoom: 12 },
            { code: 'hengyang-nanyue', adcode: '430412', name: '南岳区', level: 'county', center: [112.7380, 27.2320], zoom: 12 },
            { code: 'hengyang-hengyang', adcode: '430421', name: '衡阳县', level: 'county', center: [112.3700, 26.9700], zoom: 12 },
            { code: 'hengyang-hengnan', adcode: '430422', name: '衡南县', level: 'county', center: [112.6780, 26.7380], zoom: 12 },
            { code: 'hengyang-hengshan', adcode: '430423', name: '衡山县', level: 'county', center: [112.8680, 27.2300], zoom: 12 },
            { code: 'hengyang-hengdong', adcode: '430424', name: '衡东县', level: 'county', center: [112.9530, 27.0810], zoom: 12 },
            { code: 'hengyang-qidong', adcode: '430426', name: '祁东县', level: 'county', center: [112.0900, 26.7990], zoom: 12 },
            { code: 'hengyang-leiyang', adcode: '430481', name: '耒阳市', level: 'county', center: [112.8590, 26.4220], zoom: 12 },
            { code: 'hengyang-changning', adcode: '430482', name: '常宁市', level: 'county', center: [112.4000, 26.3810], zoom: 12 }
          ]
        },
        {
          code: 'shaoyang',
          adcode: '430500',
          name: '邵阳市',
          level: 'city',
          center: [111.4692, 27.2391],
          zoom: 10,
          children: [
            { code: 'shaoyang-shuangqing', adcode: '430502', name: '双清区', level: 'county', center: [111.4960, 27.2330], zoom: 12 },
            { code: 'shaoyang-daxiang', adcode: '430503', name: '大祥区', level: 'county', center: [111.4540, 27.2210], zoom: 12 },
            { code: 'shaoyang-beita', adcode: '430511', name: '北塔区', level: 'county', center: [111.4520, 27.3110], zoom: 12 },
            { code: 'shaoyang-shaodong', adcode: '430521', name: '邵东市', level: 'county', center: [111.7440, 27.2580], zoom: 12 },
            { code: 'shaoyang-xinshao', adcode: '430522', name: '新邵县', level: 'county', center: [111.4590, 27.3220], zoom: 12 },
            { code: 'shaoyang-shaoyang', adcode: '430523', name: '邵阳县', level: 'county', center: [111.2740, 26.9900], zoom: 12 },
            { code: 'shaoyang-longhui', adcode: '430524', name: '隆回县', level: 'county', center: [111.0320, 27.1140], zoom: 12 },
            { code: 'shaoyang-dongkou', adcode: '430525', name: '洞口县', level: 'county', center: [110.5730, 27.0590], zoom: 12 },
            { code: 'shaoyang-suining', adcode: '430527', name: '绥宁县', level: 'county', center: [110.1550, 26.5820], zoom: 12 },
            { code: 'shaoyang-xinning', adcode: '430528', name: '新宁县', level: 'county', center: [110.8560, 26.4430], zoom: 12 },
            { code: 'shaoyang-chengbu', adcode: '430529', name: '城步苗族自治县', level: 'county', center: [110.3220, 26.3910], zoom: 12 },
            { code: 'shaoyang-wugang', adcode: '430581', name: '武冈市', level: 'county', center: [110.6320, 26.7270], zoom: 12 }
          ]
        },
        {
          code: 'yueyang',
          adcode: '430600',
          name: '岳阳市',
          level: 'city',
          center: [113.1289, 29.3572],
          zoom: 10,
          children: [
            { code: 'yueyang-yueyanglou', adcode: '430602', name: '岳阳楼区', level: 'county', center: [113.1290, 29.3730], zoom: 12 },
            { code: 'yueyang-yunxi', adcode: '430603', name: '云溪区', level: 'county', center: [113.2770, 29.4740], zoom: 12 },
            { code: 'yueyang-junshan', adcode: '430611', name: '君山区', level: 'county', center: [113.0060, 29.4610], zoom: 12 },
            { code: 'yueyang-yueyang', adcode: '430621', name: '岳阳县', level: 'county', center: [113.1160, 29.1440], zoom: 12 },
            { code: 'yueyang-huarong', adcode: '430623', name: '华容县', level: 'county', center: [112.5400, 29.5310], zoom: 12 },
            { code: 'yueyang-xiangyin', adcode: '430624', name: '湘阴县', level: 'county', center: [112.8720, 28.6820], zoom: 12 },
            { code: 'yueyang-pingjiang', adcode: '430626', name: '平江县', level: 'county', center: [113.5810, 28.7020], zoom: 12 },
            { code: 'yueyang-yuanjiang', adcode: '430681', name: '汨罗市', level: 'county', center: [113.0670, 28.8060], zoom: 12 },
            { code: 'yueyang-linxiang', adcode: '430682', name: '临湘市', level: 'county', center: [113.4500, 29.4770], zoom: 12 }
          ]
        },
        {
          code: 'changde',
          adcode: '430700',
          name: '常德市',
          level: 'city',
          center: [111.6882, 29.0317],
          zoom: 10,
          children: [
            { code: 'changde-wuling', adcode: '430702', name: '武陵区', level: 'county', center: [111.7000, 29.0290], zoom: 12 },
            { code: 'changde-dingcheng', adcode: '430703', name: '鼎城区', level: 'county', center: [111.6810, 29.0190], zoom: 12 },
            { code: 'changde-anxiang', adcode: '430721', name: '安乡县', level: 'county', center: [112.1710, 29.4110], zoom: 12 },
            { code: 'changde-hanshou', adcode: '430722', name: '汉寿县', level: 'county', center: [111.9510, 28.9060], zoom: 12 },
            { code: 'changde-li', adcode: '430723', name: '澧县', level: 'county', center: [111.6490, 29.6330], zoom: 12 },
            { code: 'changde-linli', adcode: '430724', name: '临澧县', level: 'county', center: [111.6510, 29.4410], zoom: 12 },
            { code: 'changde-taoyuan', adcode: '430725', name: '桃源县', level: 'county', center: [111.4890, 28.9020], zoom: 12 },
            { code: 'changde-shimen', adcode: '430726', name: '石门县', level: 'county', center: [111.3800, 29.5850], zoom: 12 },
            { code: 'changde-jinshi', adcode: '430781', name: '津市市', level: 'county', center: [111.8770, 29.6050], zoom: 12 }
          ]
        },
        {
          code: 'zhangjiajie',
          adcode: '430800',
          name: '张家界市',
          level: 'city',
          center: [110.4791, 29.1171],
          zoom: 10,
          children: [
            { code: 'zhangjiajie-yongding', adcode: '430802', name: '永定区', level: 'county', center: [110.4760, 29.1300], zoom: 12 },
            { code: 'zhangjiajie-wulingyuan', adcode: '430811', name: '武陵源区', level: 'county', center: [110.5500, 29.3260], zoom: 12 },
            { code: 'zhangjiajie-cili', adcode: '430821', name: '慈利县', level: 'county', center: [111.1280, 29.4300], zoom: 12 },
            { code: 'zhangjiajie-sangzhi', adcode: '430822', name: '桑植县', level: 'county', center: [110.1640, 29.3990], zoom: 12 }
          ]
        },
        {
          code: 'yiyang',
          adcode: '430900',
          name: '益阳市',
          level: 'city',
          center: [112.3550, 28.5539],
          zoom: 10,
          children: [
            { code: 'yiyang-ziyang', adcode: '430902', name: '资阳区', level: 'county', center: [112.3240, 28.5910], zoom: 12 },
            { code: 'yiyang-heshan', adcode: '430903', name: '赫山区', level: 'county', center: [112.3740, 28.5810], zoom: 12 },
            { code: 'yiyang-nan', adcode: '430921', name: '南县', level: 'county', center: [112.3960, 29.3610], zoom: 12 },
            { code: 'yiyang-taojiang', adcode: '430922', name: '桃江县', level: 'county', center: [112.1560, 28.5180], zoom: 12 },
            { code: 'yiyang-anhua', adcode: '430923', name: '安化县', level: 'county', center: [111.2130, 28.3740], zoom: 12 },
            { code: 'yiyang-yuanjiang', adcode: '430981', name: '沅江市', level: 'county', center: [112.3540, 28.8440], zoom: 12 }
          ]
        },
        {
          code: 'chenzhou',
          adcode: '431000',
          name: '郴州市',
          level: 'city',
          center: [113.0147, 25.7706],
          zoom: 10,
          children: [
            { code: 'chenzhou-beihu', adcode: '431002', name: '北湖区', level: 'county', center: [113.0110, 25.7920], zoom: 12 },
            { code: 'chenzhou-suxian', adcode: '431003', name: '苏仙区', level: 'county', center: [113.0420, 25.8000], zoom: 12 },
            { code: 'chenzhou-guiyang', adcode: '431021', name: '桂阳县', level: 'county', center: [112.7340, 25.7540], zoom: 12 },
            { code: 'chenzhou-yizhang', adcode: '431022', name: '宜章县', level: 'county', center: [113.0960, 25.3990], zoom: 12 },
            { code: 'chenzhou-yongxing', adcode: '431023', name: '永兴县', level: 'county', center: [113.1170, 26.1270], zoom: 12 },
            { code: 'chenzhou-jiahe', adcode: '431024', name: '嘉禾县', level: 'county', center: [112.3690, 25.5870], zoom: 12 },
            { code: 'chenzhou-linwu', adcode: '431025', name: '临武县', level: 'county', center: [112.5630, 25.2760], zoom: 12 },
            { code: 'chenzhou-rucheng', adcode: '431026', name: '汝城县', level: 'county', center: [113.6850, 25.5520], zoom: 12 },
            { code: 'chenzhou-guidong', adcode: '431027', name: '桂东县', level: 'county', center: [113.9440, 26.0800], zoom: 12 },
            { code: 'chenzhou-anzren', adcode: '431028', name: '安仁县', level: 'county', center: [113.2690, 26.7090], zoom: 12 },
            { code: 'chenzhou-zixing', adcode: '431081', name: '资兴市', level: 'county', center: [113.2370, 25.9760], zoom: 12 }
          ]
        },
        {
          code: 'yongzhou',
          adcode: '431100',
          name: '永州市',
          level: 'city',
          center: [111.6134, 26.4206],
          zoom: 10,
          children: [
            { code: 'yongzhou-zhishan', adcode: '431102', name: '芝山区', level: 'county', center: [111.6260, 26.4220], zoom: 12 },
            { code: 'yongzhou-lengshuitan', adcode: '431103', name: '冷水滩区', level: 'county', center: [111.5920, 26.4610], zoom: 12 },
            { code: 'yongzhou-qiyang', adcode: '431121', name: '祁阳县', level: 'county', center: [111.8400, 26.5800], zoom: 12 },
            { code: 'yongzhou-dongan', adcode: '431122', name: '东安县', level: 'county', center: [111.3160, 26.3920], zoom: 12 },
            { code: 'yongzhou-shuangpai', adcode: '431123', name: '双牌县', level: 'county', center: [111.6590, 25.9620], zoom: 12 },
            { code: 'yongzhou-dao', adcode: '431124', name: '道县', level: 'county', center: [111.6010, 25.5260], zoom: 12 },
            { code: 'yongzhou-jiangyong', adcode: '431125', name: '江永县', level: 'county', center: [111.3440, 25.2730], zoom: 12 },
            { code: 'yongzhou-ningyuan', adcode: '431126', name: '宁远县', level: 'county', center: [111.9460, 25.5690], zoom: 12 },
            { code: 'yongzhou-lanshan', adcode: '431127', name: '蓝山县', level: 'county', center: [112.1930, 25.3700], zoom: 12 },
            { code: 'yongzhou-xintian', adcode: '431128', name: '新田县', level: 'county', center: [112.2210, 25.9060], zoom: 12 },
            { code: 'yongzhou-jianghua', adcode: '431129', name: '江华瑶族自治县', level: 'county', center: [111.5880, 25.1840], zoom: 12 }
          ]
        },
        {
          code: 'huaihua',
          adcode: '431200',
          name: '怀化市',
          level: 'city',
          center: [109.9985, 27.5490],
          zoom: 9,
          children: [
            { code: 'huaihua-hecheng', adcode: '431202', name: '鹤城区', level: 'county', center: [109.9650, 27.5500], zoom: 12 },
            { code: 'huaihua-zhongfang', adcode: '431221', name: '中方县', level: 'county', center: [109.9450, 27.4400], zoom: 12 },
            { code: 'huaihua-yuanling', adcode: '431222', name: '沅陵县', level: 'county', center: [110.3920, 28.4530], zoom: 12 },
            { code: 'huaihua-chenxi', adcode: '431223', name: '辰溪县', level: 'county', center: [110.1830, 28.0060], zoom: 12 },
            { code: 'huaihua-xupu', adcode: '431224', name: '溆浦县', level: 'county', center: [110.5950, 27.9080], zoom: 12 },
            { code: 'huaihua-huitong', adcode: '431225', name: '会同县', level: 'county', center: [109.7360, 26.8870], zoom: 12 },
            { code: 'huaihua-mayang', adcode: '431226', name: '麻阳苗族自治县', level: 'county', center: [109.8020, 27.8660], zoom: 12 },
            { code: 'huaihua-xinhuang', adcode: '431227', name: '新晃侗族自治县', level: 'county', center: [109.1750, 27.3530], zoom: 12 },
            { code: 'huaihua-zhijiang', adcode: '431228', name: '芷江侗族自治县', level: 'county', center: [109.6850, 27.4430], zoom: 12 },
            { code: 'huaihua-jingzhou', adcode: '431229', name: '靖州苗族侗族自治县', level: 'county', center: [109.6980, 26.5760], zoom: 12 },
            { code: 'huaihua-tongdao', adcode: '431230', name: '通道侗族自治县', level: 'county', center: [109.7840, 26.1580], zoom: 12 },
            { code: 'huaihua-hongjiang', adcode: '431281', name: '洪江市', level: 'county', center: [110.1480, 27.2090], zoom: 12 }
          ]
        },
        {
          code: 'loudi',
          adcode: '431300',
          name: '娄底市',
          level: 'city',
          center: [111.9937, 27.6995],
          zoom: 10,
          children: [
            { code: 'loudi-louxing', adcode: '431302', name: '娄星区', level: 'county', center: [112.0020, 27.6990], zoom: 12 },
            { code: 'loudi-shuangfeng', adcode: '431321', name: '双峰县', level: 'county', center: [112.1990, 27.4560], zoom: 12 },
            { code: 'loudi-xinhua', adcode: '431322', name: '新化县', level: 'county', center: [111.3270, 27.7260], zoom: 12 },
            { code: 'loudi-lengshuijiang', adcode: '431381', name: '冷水江市', level: 'county', center: [111.4360, 27.6860], zoom: 12 },
            { code: 'loudi-lianyuan', adcode: '431382', name: '涟源市', level: 'county', center: [111.6720, 27.6930], zoom: 12 }
          ]
        },
        {
          code: 'xiangxi',
          adcode: '433100',
          name: '湘西土家族苗族自治州',
          level: 'city',
          center: [109.7395, 28.3143],
          zoom: 9,
          children: [
            { code: 'xiangxi-jishou', adcode: '433101', name: '吉首市', level: 'county', center: [109.6980, 28.2620], zoom: 12 },
            { code: 'xiangxi-luxi', adcode: '433122', name: '泸溪县', level: 'county', center: [110.2190, 28.2160], zoom: 12 },
            { code: 'xiangxi-fenghuang', adcode: '433123', name: '凤凰县', level: 'county', center: [109.5980, 27.9480], zoom: 12 },
            { code: 'xiangxi-huayuan', adcode: '433124', name: '花垣县', level: 'county', center: [109.4820, 28.5720], zoom: 12 },
            { code: 'xiangxi-baojing', adcode: '433125', name: '保靖县', level: 'county', center: [109.6600, 28.6990], zoom: 12 },
            { code: 'xiangxi-guzhang', adcode: '433126', name: '古丈县', level: 'county', center: [109.9510, 28.6170], zoom: 12 },
            { code: 'xiangxi-yongshun', adcode: '433127', name: '永顺县', level: 'county', center: [109.8530, 29.0010], zoom: 12 },
            { code: 'xiangxi-longshan', adcode: '433130', name: '龙山县', level: 'county', center: [109.4440, 29.4580], zoom: 12 }
          ]
        }
      ]
    },
    {
      code: 'fujian',
      adcode: '350000',
      name: '福建省',
      level: 'province',
      center: [119.3062, 26.0753],
      zoom: 7,
      children: [
        {
          code: 'fuzhou',
          adcode: '350100',
          name: '福州市',
          level: 'city',
          center: [119.3062, 26.0753],
          zoom: 10,
          children: [
            { code: 'fuzhou-gulou', adcode: '350102', name: '鼓楼区', level: 'county', center: [119.3040, 26.0830], zoom: 12 },
            { code: 'fuzhou-taijiang', adcode: '350103', name: '台江区', level: 'county', center: [119.3140, 26.0530], zoom: 12 },
            { code: 'fuzhou-cangshan', adcode: '350104', name: '仓山区', level: 'county', center: [119.2730, 26.0460], zoom: 12 },
            { code: 'fuzhou-mawei', adcode: '350105', name: '马尾区', level: 'county', center: [119.4550, 25.9900], zoom: 12 },
            { code: 'fuzhou-jinan', adcode: '350111', name: '晋安区', level: 'county', center: [119.3280, 26.0820], zoom: 12 },
            { code: 'fuzhou-changle', adcode: '350112', name: '长乐区', level: 'county', center: [119.5230, 25.9630], zoom: 12 },
            { code: 'fuzhou-minhou', adcode: '350121', name: '闽侯县', level: 'county', center: [119.1320, 26.1500], zoom: 12 },
            { code: 'fuzhou-lianjiang', adcode: '350122', name: '连江县', level: 'county', center: [119.5340, 26.1980], zoom: 12 },
            { code: 'fuzhou-luoyuan', adcode: '350123', name: '罗源县', level: 'county', center: [119.5500, 26.4870], zoom: 12 },
            { code: 'fuzhou-minqing', adcode: '350124', name: '闽清县', level: 'county', center: [118.8630, 26.2210], zoom: 12 },
            { code: 'fuzhou-yongtai', adcode: '350125', name: '永泰县', level: 'county', center: [118.9360, 25.8670], zoom: 12 },
            { code: 'fuzhou-pingtan', adcode: '350128', name: '平潭县', level: 'county', center: [119.7910, 25.5040], zoom: 12 },
            { code: 'fuzhou-fuqing', adcode: '350181', name: '福清市', level: 'county', center: [119.3840, 25.7210], zoom: 12 }
          ]
        },
        {
          code: 'xiamen',
          adcode: '350200',
          name: '厦门市',
          level: 'city',
          center: [118.0894, 24.4798],
          zoom: 10,
          children: [
            { code: 'xiamen-siming', adcode: '350203', name: '思明区', level: 'county', center: [118.0820, 24.4460], zoom: 12 },
            { code: 'xiamen-haicang', adcode: '350205', name: '海沧区', level: 'county', center: [118.0330, 24.4840], zoom: 12 },
            { code: 'xiamen-huli', adcode: '350206', name: '湖里区', level: 'county', center: [118.1460, 24.5120], zoom: 12 },
            { code: 'xiamen-jimei', adcode: '350211', name: '集美区', level: 'county', center: [118.0970, 24.5760], zoom: 12 },
            { code: 'xiamen-tongan', adcode: '350212', name: '同安区', level: 'county', center: [118.1520, 24.7230], zoom: 12 },
            { code: 'xiamen-xiangan', adcode: '350213', name: '翔安区', level: 'county', center: [118.2480, 24.6190], zoom: 12 }
          ]
        },
        {
          code: 'putian',
          adcode: '350300',
          name: '莆田市',
          level: 'city',
          center: [119.0078, 25.4541],
          zoom: 10,
          children: [
            { code: 'putian-chengxiang', adcode: '350302', name: '城厢区', level: 'county', center: [118.9940, 25.4190], zoom: 12 },
            { code: 'putian-hanjiang', adcode: '350303', name: '涵江区', level: 'county', center: [119.1160, 25.4590], zoom: 12 },
            { code: 'putian-licheng', adcode: '350304', name: '荔城区', level: 'county', center: [119.0150, 25.4320], zoom: 12 },
            { code: 'putian-xiuyu', adcode: '350305', name: '秀屿区', level: 'county', center: [119.1060, 25.3180], zoom: 12 },
            { code: 'putian-xianyou', adcode: '350322', name: '仙游县', level: 'county', center: [118.6920, 25.3620], zoom: 12 }
          ]
        },
        {
          code: 'sanming',
          adcode: '350400',
          name: '三明市',
          level: 'city',
          center: [117.6389, 26.2634],
          zoom: 10,
          children: [
            { code: 'sanming-meiie', adcode: '350402', name: '梅列区', level: 'county', center: [117.6460, 26.2740], zoom: 12 },
            { code: 'sanming-sanyuan', adcode: '350403', name: '三元区', level: 'county', center: [117.5720, 26.2340], zoom: 12 },
            { code: 'sanming-mingxi', adcode: '350421', name: '明溪县', level: 'county', center: [117.2020, 26.3580], zoom: 12 },
            { code: 'sanming-qingliu', adcode: '350423', name: '清流县', level: 'county', center: [116.8150, 26.1780], zoom: 12 },
            { code: 'sanming-ninghua', adcode: '350424', name: '宁化县', level: 'county', center: [116.6540, 26.2590], zoom: 12 },
            { code: 'sanming-datian', adcode: '350425', name: '大田县', level: 'county', center: [117.8470, 25.6920], zoom: 12 },
            { code: 'sanming-youxi', adcode: '350426', name: '尤溪县', level: 'county', center: [118.1900, 26.1700], zoom: 12 },
            { code: 'sanming-shaxian', adcode: '350427', name: '沙县', level: 'county', center: [117.7920, 26.3970], zoom: 12 },
            { code: 'sanming-jiangle', adcode: '350428', name: '将乐县', level: 'county', center: [117.4730, 26.7280], zoom: 12 },
            { code: 'sanming-taining', adcode: '350429', name: '泰宁县', level: 'county', center: [117.1780, 26.9020], zoom: 12 },
            { code: 'sanming-jianning', adcode: '350430', name: '建宁县', level: 'county', center: [116.8460, 26.8310], zoom: 12 },
            { code: 'sanming-yongan', adcode: '350481', name: '永安市', level: 'county', center: [117.3650, 25.9420], zoom: 12 }
          ]
        },
        {
          code: 'quanzhou',
          adcode: '350500',
          name: '泉州市',
          level: 'city',
          center: [118.6757, 24.8741],
          zoom: 10,
          children: [
            { code: 'quanzhou-licheng', adcode: '350502', name: '鲤城区', level: 'county', center: [118.5860, 24.9070], zoom: 12 },
            { code: 'quanzhou-fengze', adcode: '350503', name: '丰泽区', level: 'county', center: [118.6130, 24.8910], zoom: 12 },
            { code: 'quanzhou-luojiang', adcode: '350504', name: '洛江区', level: 'county', center: [118.6710, 24.9390], zoom: 12 },
            { code: 'quanzhou-quangang', adcode: '350505', name: '泉港区', level: 'county', center: [118.9160, 25.1200], zoom: 12 },
            { code: 'quanzhou-huian', adcode: '350521', name: '惠安县', level: 'county', center: [118.7970, 25.0310], zoom: 12 },
            { code: 'quanzhou-anxi', adcode: '350524', name: '安溪县', level: 'county', center: [118.1870, 25.0560], zoom: 12 },
            { code: 'quanzhou-yongchun', adcode: '350525', name: '永春县', level: 'county', center: [118.2950, 25.3220], zoom: 12 },
            { code: 'quanzhou-dehua', adcode: '350526', name: '德化县', level: 'county', center: [118.2420, 25.4910], zoom: 12 },
            { code: 'quanzhou-jinmen', adcode: '350527', name: '金门县', level: 'county', center: [118.3230, 24.4290], zoom: 12 },
            { code: 'quanzhou-shishi', adcode: '350581', name: '石狮市', level: 'county', center: [118.6480, 24.7320], zoom: 12 },
            { code: 'quanzhou-jinjiang', adcode: '350582', name: '晋江市', level: 'county', center: [118.5520, 24.7910], zoom: 12 },
            { code: 'quanzhou-nanan', adcode: '350583', name: '南安市', level: 'county', center: [118.3860, 24.9600], zoom: 12 }
          ]
        },
        {
          code: 'zhangzhou',
          adcode: '350600',
          name: '漳州市',
          level: 'city',
          center: [117.6472, 24.5130],
          zoom: 10,
          children: [
            { code: 'zhangzhou-xiangcheng', adcode: '350602', name: '芗城区', level: 'county', center: [117.6540, 24.5110], zoom: 12 },
            { code: 'zhangzhou-longwen', adcode: '350603', name: '龙文区', level: 'county', center: [117.7090, 24.5030], zoom: 12 },
            { code: 'zhangzhou-yunxiao', adcode: '350622', name: '云霄县', level: 'county', center: [117.3400, 23.9560], zoom: 12 },
            { code: 'zhangzhou-zhangpu', adcode: '350623', name: '漳浦县', level: 'county', center: [117.6140, 24.1180], zoom: 12 },
            { code: 'zhangzhou-zhaoan', adcode: '350624', name: '诏安县', level: 'county', center: [117.1750, 23.7120], zoom: 12 },
            { code: 'zhangzhou-changtai', adcode: '350625', name: '长泰县', level: 'county', center: [117.7580, 24.6260], zoom: 12 },
            { code: 'zhangzhou-dongshan', adcode: '350626', name: '东山县', level: 'county', center: [117.4300, 23.7010], zoom: 12 },
            { code: 'zhangzhou-nanjing', adcode: '350627', name: '南靖县', level: 'county', center: [117.3570, 24.5150], zoom: 12 },
            { code: 'zhangzhou-pinghe', adcode: '350628', name: '平和县', level: 'county', center: [117.3150, 24.3640], zoom: 12 },
            { code: 'zhangzhou-huaan', adcode: '350629', name: '华安县', level: 'county', center: [117.5340, 25.0030], zoom: 12 },
            { code: 'zhangzhou-longhai', adcode: '350681', name: '龙海市', level: 'county', center: [117.8180, 24.4470], zoom: 12 }
          ]
        },
        {
          code: 'nanping',
          adcode: '350700',
          name: '南平市',
          level: 'city',
          center: [118.1785, 26.6356],
          zoom: 10,
          children: [
            { code: 'nanping-yanping', adcode: '350702', name: '延平区', level: 'county', center: [118.1820, 26.6380], zoom: 12 },
            { code: 'nanping-jianyang', adcode: '350703', name: '建阳区', level: 'county', center: [118.1220, 27.3320], zoom: 12 },
            { code: 'nanping-shunchang', adcode: '350721', name: '顺昌县', level: 'county', center: [117.8100, 26.7930], zoom: 12 },
            { code: 'nanping-pucheng', adcode: '350722', name: '浦城县', level: 'county', center: [118.5410, 27.9180], zoom: 12 },
            { code: 'nanping-guangze', adcode: '350723', name: '光泽县', level: 'county', center: [117.3340, 27.5420], zoom: 12 },
            { code: 'nanping-songxi', adcode: '350724', name: '松溪县', level: 'county', center: [118.7850, 27.5260], zoom: 12 },
            { code: 'nanping-zhenghe', adcode: '350725', name: '政和县', level: 'county', center: [118.8570, 27.3660], zoom: 12 },
            { code: 'nanping-shaowu', adcode: '350781', name: '邵武市', level: 'county', center: [117.4930, 27.3400], zoom: 12 },
            { code: 'nanping-wuyishan', adcode: '350782', name: '武夷山市', level: 'county', center: [118.0350, 27.7560], zoom: 12 },
            { code: 'nanping-jianou', adcode: '350783', name: '建瓯市', level: 'county', center: [118.3220, 27.0350], zoom: 12 }
          ]
        },
        {
          code: 'longyan',
          adcode: '350800',
          name: '龙岩市',
          level: 'city',
          center: [117.0175, 25.0786],
          zoom: 10,
          children: [
            { code: 'longyan-xinluo', adcode: '350802', name: '新罗区', level: 'county', center: [117.0370, 25.0980], zoom: 12 },
            { code: 'longyan-yongding', adcode: '350803', name: '永定区', level: 'county', center: [116.7320, 24.7230], zoom: 12 },
            { code: 'longyan-changting', adcode: '350821', name: '长汀县', level: 'county', center: [116.3580, 25.8280], zoom: 12 },
            { code: 'longyan-shanghang', adcode: '350823', name: '上杭县', level: 'county', center: [116.4200, 25.0490], zoom: 12 },
            { code: 'longyan-wuping', adcode: '350824', name: '武平县', level: 'county', center: [116.1020, 25.0920], zoom: 12 },
            { code: 'longyan-liancheng', adcode: '350825', name: '连城县', level: 'county', center: [116.7550, 25.7110], zoom: 12 },
            { code: 'longyan-zhangping', adcode: '350881', name: '漳平市', level: 'county', center: [117.4200, 25.2900], zoom: 12 }
          ]
        },
        {
          code: 'ningde',
          adcode: '350900',
          name: '宁德市',
          level: 'city',
          center: [119.5271, 26.6590],
          zoom: 10,
          children: [
            { code: 'ningde-jiaocheng', adcode: '350902', name: '蕉城区', level: 'county', center: [119.5260, 26.6610], zoom: 12 },
            { code: 'ningde-xiapu', adcode: '350921', name: '霞浦县', level: 'county', center: [120.0060, 26.8850], zoom: 12 },
            { code: 'ningde-gutian', adcode: '350922', name: '古田县', level: 'county', center: [118.7430, 26.5770], zoom: 12 },
            { code: 'ningde-pingnan', adcode: '350923', name: '屏南县', level: 'county', center: [118.9860, 26.9110], zoom: 12 },
            { code: 'ningde-shouning', adcode: '350924', name: '寿宁县', level: 'county', center: [119.5040, 27.4580], zoom: 12 },
            { code: 'ningde-zherong', adcode: '350925', name: '周宁县', level: 'county', center: [119.3590, 27.1040], zoom: 12 },
            { code: 'ningde-zherong', adcode: '350926', name: '柘荣县', level: 'county', center: [119.9000, 27.2360], zoom: 12 },
            { code: 'ningde-fuan', adcode: '350981', name: '福安市', level: 'county', center: [119.6500, 27.0880], zoom: 12 },
            { code: 'ningde-fuding', adcode: '350982', name: '福鼎市', level: 'county', center: [120.2170, 27.3270], zoom: 12 }
          ]
        }
      ]
    },
    {
      code: 'anhui',
      adcode: '340000',
      name: '安徽省',
      level: 'province',
      center: [117.2830, 31.8612],
      zoom: 7,
      children: [
        {
          code: 'hefei',
          adcode: '340100',
          name: '合肥市',
          level: 'city',
          center: [117.2830, 31.8612],
          zoom: 10,
          children: [
            { code: 'hefei-yaohai', adcode: '340102', name: '瑶海区', level: 'county', center: [117.3090, 31.8580], zoom: 12 },
            { code: 'hefei-luyang', adcode: '340103', name: '庐阳区', level: 'county', center: [117.2650, 31.8790], zoom: 12 },
            { code: 'hefei-shushan', adcode: '340104', name: '蜀山区', level: 'county', center: [117.2600, 31.8510], zoom: 12 },
            { code: 'hefei-baohe', adcode: '340111', name: '包河区', level: 'county', center: [117.3100, 31.7930], zoom: 12 },
            { code: 'hefei-changfeng', adcode: '340121', name: '长丰县', level: 'county', center: [117.1680, 32.4780], zoom: 12 },
            { code: 'hefei-feidong', adcode: '340122', name: '肥东县', level: 'county', center: [117.4690, 31.8880], zoom: 12 },
            { code: 'hefei-feixi', adcode: '340123', name: '肥西县', level: 'county', center: [117.1580, 31.7070], zoom: 12 },
            { code: 'hefei-lujiang', adcode: '340124', name: '庐江县', level: 'county', center: [117.2880, 31.2560], zoom: 12 },
            { code: 'hefei-chaohu', adcode: '340181', name: '巢湖市', level: 'county', center: [117.8900, 31.6000], zoom: 12 }
          ]
        },
        {
          code: 'wuhu',
          adcode: '340200',
          name: '芜湖市',
          level: 'city',
          center: [118.3761, 31.3263],
          zoom: 10,
          children: [
            { code: 'wuhu-jinghu', adcode: '340202', name: '镜湖区', level: 'county', center: [118.3850, 31.3400], zoom: 12 },
            { code: 'wuhu-yijiang', adcode: '340203', name: '弋江区', level: 'county', center: [118.3730, 31.2950], zoom: 12 },
            { code: 'wuhu-jiujiang', adcode: '340207', name: '鸠江区', level: 'county', center: [118.3920, 31.3690], zoom: 12 },
            { code: 'wuhu-sanshan', adcode: '340208', name: '三山区', level: 'county', center: [118.2680, 31.2080], zoom: 12 },
            { code: 'wuhu-wuhu', adcode: '340221', name: '芜湖县', level: 'county', center: [118.5750, 31.1460], zoom: 12 },
            { code: 'wuhu-fan chang', adcode: '340222', name: '繁昌区', level: 'county', center: [118.1990, 31.0830], zoom: 12 },
            { code: 'wuhu-nanling', adcode: '340223', name: '南陵县', level: 'county', center: [118.3340, 30.9150], zoom: 12 },
            { code: 'wuhu-wuwei', adcode: '340281', name: '无为市', level: 'county', center: [117.9040, 31.3030], zoom: 12 }
          ]
        },
        {
          code: 'bengbu',
          adcode: '340300',
          name: '蚌埠市',
          level: 'city',
          center: [117.3890, 33.9188],
          zoom: 10,
          children: [
            { code: 'bengbu-longzihu', adcode: '340302', name: '龙子湖区', level: 'county', center: [117.3920, 32.9430], zoom: 12 },
            { code: 'bengbu-bengshan', adcode: '340303', name: '蚌山区', level: 'county', center: [117.3670, 32.9390], zoom: 12 },
            { code: 'bengbu-yuhui', adcode: '340304', name: '禹会区', level: 'county', center: [117.3540, 32.9300], zoom: 12 },
            { code: 'bengbu-huaishang', adcode: '340311', name: '淮上区', level: 'county', center: [117.3580, 33.0170], zoom: 12 },
            { code: 'bengbu-huaiyuan', adcode: '340321', name: '怀远县', level: 'county', center: [117.2050, 33.0000], zoom: 12 },
            { code: 'bengbu-wuhe', adcode: '340322', name: '五河县', level: 'county', center: [117.8910, 33.1460], zoom: 12 },
            { code: 'bengbu-guzhen', adcode: '340323', name: '固镇县', level: 'county', center: [117.3170, 33.3180], zoom: 12 }
          ]
        },
        {
          code: 'huainan',
          adcode: '340400',
          name: '淮南市',
          level: 'city',
          center: [117.0254, 32.6459],
          zoom: 10,
          children: [
            { code: 'huainan-datong', adcode: '340402', name: '大通区', level: 'county', center: [117.0530, 32.6320], zoom: 12 },
            { code: 'huainan-tianjiaan', adcode: '340403', name: '田家庵区', level: 'county', center: [117.0180, 32.6470], zoom: 12 },
            { code: 'huainan-xiejiaji', adcode: '340404', name: '谢家集区', level: 'county', center: [116.8690, 32.5990], zoom: 12 },
            { code: 'huainan-bagongshan', adcode: '340405', name: '八公山区', level: 'county', center: [116.8370, 32.6300], zoom: 12 },
            { code: 'huainan-panji', adcode: '340406', name: '潘集区', level: 'county', center: [116.8330, 32.7820], zoom: 12 },
            { code: 'huainan-fengtai', adcode: '340421', name: '凤台县', level: 'county', center: [116.7100, 32.7090], zoom: 12 },
            { code: 'huainan-shou', adcode: '340422', name: '寿县', level: 'county', center: [116.7850, 32.5760], zoom: 12 }
          ]
        },
        {
          code: 'maanshan',
          adcode: '340500',
          name: '马鞍山市',
          level: 'city',
          center: [118.5069, 31.6704],
          zoom: 10,
          children: [
            { code: 'maanshan-jinjiazhuang', adcode: '340503', name: '金家庄区', level: 'county', center: [118.4110, 31.7350], zoom: 12 },
            { code: 'maanshan-huashan', adcode: '340504', name: '花山区', level: 'county', center: [118.5110, 31.7000], zoom: 12 },
            { code: 'maanshan-yushan', adcode: '340506', name: '雨山区', level: 'county', center: [118.4980, 31.6820], zoom: 12 },
            { code: 'maanshan-dangtu', adcode: '340521', name: '当涂县', level: 'county', center: [118.4980, 31.5710], zoom: 12 },
            { code: 'maanshan-he', adcode: '340522', name: '和县', level: 'county', center: [118.3710, 31.7420], zoom: 12 },
            { code: 'maanshan-hanshan', adcode: '340523', name: '含山县', level: 'county', center: [118.1050, 31.7360], zoom: 12 },
            { code: 'maanshan-bo', adcode: '340581', name: '博望区', level: 'county', center: [118.8450, 31.5590], zoom: 12 }
          ]
        },
        {
          code: 'huaibei',
          adcode: '340600',
          name: '淮北市',
          level: 'city',
          center: [116.7947, 33.9717],
          zoom: 10,
          children: [
            { code: 'huaibei-duji', adcode: '340602', name: '杜集区', level: 'county', center: [116.8280, 33.9910], zoom: 12 },
            { code: 'huaibei-xiangshan', adcode: '340603', name: '相山区', level: 'county', center: [116.7950, 33.9590], zoom: 12 },
            { code: 'huaibei-lieshan', adcode: '340604', name: '烈山区', level: 'county', center: [116.8130, 33.8950], zoom: 12 },
            { code: 'huaibei-suixi', adcode: '340621', name: '濉溪县', level: 'county', center: [116.7660, 33.9150], zoom: 12 }
          ]
        },
        {
          code: 'tongling',
          adcode: '340700',
          name: '铜陵市',
          level: 'city',
          center: [117.8121, 30.9454],
          zoom: 10,
          children: [
            { code: 'tongling-tongguan', adcode: '340705', name: '铜官区', level: 'county', center: [117.8150, 30.9340], zoom: 12 },
            { code: 'tongling-yian', adcode: '340706', name: '义安区', level: 'county', center: [117.7920, 30.9530], zoom: 12 },
            { code: 'tongling-jiao', adcode: '340711', name: '郊区', level: 'county', center: [117.8090, 30.9030], zoom: 12 },
            { code: 'tongling-zongyang', adcode: '340722', name: '枞阳县', level: 'county', center: [117.2200, 30.7000], zoom: 12 }
          ]
        },
        {
          code: 'anqing',
          adcode: '340800',
          name: '安庆市',
          level: 'city',
          center: [117.0635, 30.5434],
          zoom: 10,
          children: [
            { code: 'anqing-yingjiang', adcode: '340802', name: '迎江区', level: 'county', center: [117.0910, 30.5110], zoom: 12 },
            { code: 'anqing-daguan', adcode: '340803', name: '大观区', level: 'county', center: [117.0340, 30.5120], zoom: 12 },
            { code: 'anqing-yixiu', adcode: '340811', name: '宜秀区', level: 'county', center: [117.0420, 30.6200], zoom: 12 },
            { code: 'anqing-huaining', adcode: '340822', name: '怀宁县', level: 'county', center: [116.8300, 30.7340], zoom: 12 },
            { code: 'anqing-taihu', adcode: '340825', name: '太湖县', level: 'county', center: [116.3090, 30.4540], zoom: 12 },
            { code: 'anqing-susong', adcode: '340826', name: '宿松县', level: 'county', center: [116.1290, 30.1540], zoom: 12 },
            { code: 'anqing-wangjiang', adcode: '340827', name: '望江县', level: 'county', center: [116.6940, 30.1260], zoom: 12 },
            { code: 'anqing-yuesi', adcode: '340828', name: '岳西县', level: 'county', center: [116.3600, 30.8500], zoom: 12 },
            { code: 'anqing-tongcheng', adcode: '340881', name: '桐城市', level: 'county', center: [116.9510, 31.0350], zoom: 12 },
            { code: 'anqing-qianshan', adcode: '340882', name: '潜山市', level: 'county', center: [116.5810, 30.6310], zoom: 12 }
          ]
        },
        {
          code: 'huangshan',
          adcode: '341000',
          name: '黄山市',
          level: 'city',
          center: [118.3377, 29.7147],
          zoom: 10,
          children: [
            { code: 'huangshan-tunxi', adcode: '341002', name: '屯溪区', level: 'county', center: [118.3150, 29.6960], zoom: 12 },
            { code: 'huangshan-huizhou', adcode: '341003', name: '徽州区', level: 'county', center: [118.3360, 29.8270], zoom: 12 },
            { code: 'huangshan-qimen', adcode: '341024', name: '祁门县', level: 'county', center: [117.7180, 29.8570], zoom: 12 },
            { code: 'huangshan-shexian', adcode: '341021', name: '歙县', level: 'county', center: [118.4370, 29.8680], zoom: 12 },
            { code: 'huangshan-xiuning', adcode: '341022', name: '休宁县', level: 'county', center: [118.1990, 29.7890], zoom: 12 },
            { code: 'huangshan-yixian', adcode: '341023', name: '黟县', level: 'county', center: [117.9380, 29.9250], zoom: 12 },
            { code: 'huangshan-huangshan', adcode: '341025', name: '黄山区', level: 'county', center: [118.1420, 30.2730], zoom: 12 }
          ]
        },
        {
          code: 'chuzhou',
          adcode: '341100',
          name: '滁州市',
          level: 'city',
          center: [118.3169, 32.3036],
          zoom: 10,
          children: [
            { code: 'chuzhou-langya', adcode: '341102', name: '琅琊区', level: 'county', center: [118.3120, 32.2950], zoom: 12 },
            { code: 'chuzhou-nanqiao', adcode: '341103', name: '南谯区', level: 'county', center: [118.2960, 32.3290], zoom: 12 },
            { code: 'chuzhou-laian', adcode: '341122', name: '来安县', level: 'county', center: [118.4360, 32.4520], zoom: 12 },
            { code: 'chuzhou-quanjiao', adcode: '341124', name: '全椒县', level: 'county', center: [118.2730, 32.0860], zoom: 12 },
            { code: 'chuzhou-dingyuan', adcode: '341125', name: '定远县', level: 'county', center: [117.6980, 32.5240], zoom: 12 },
            { code: 'chuzhou-fengyang', adcode: '341126', name: '凤阳县', level: 'county', center: [117.5620, 32.8660], zoom: 12 },
            { code: 'chuzhou-tianchang', adcode: '341181', name: '天长市', level: 'county', center: [119.0040, 32.6860], zoom: 12 },
            { code: 'chuzhou-mingguang', adcode: '341182', name: '明光市', level: 'county', center: [117.9900, 32.7780], zoom: 12 }
          ]
        },
        {
          code: 'fuyang',
          adcode: '341200',
          name: '阜阳市',
          level: 'city',
          center: [115.8197, 32.8970],
          zoom: 10,
          children: [
            { code: 'fuyang-yingzhou', adcode: '341202', name: '颍州区', level: 'county', center: [115.8070, 32.8830], zoom: 12 },
            { code: 'fuyang-yingdong', adcode: '341203', name: '颍东区', level: 'county', center: [115.8540, 32.9120], zoom: 12 },
            { code: 'fuyang-yingquan', adcode: '341204', name: '颍泉区', level: 'county', center: [115.8090, 32.9250], zoom: 12 },
            { code: 'fuyang-linan', adcode: '341221', name: '临泉县', level: 'county', center: [115.2620, 33.0680], zoom: 12 },
            { code: 'fuyang-taihe', adcode: '341222', name: '太和县', level: 'county', center: [115.6220, 33.1600], zoom: 12 },
            { code: 'fuyang-funan', adcode: '341225', name: '阜南县', level: 'county', center: [115.5950, 32.6350], zoom: 12 },
            { code: 'fuyang-yingshang', adcode: '341226', name: '颍上县', level: 'county', center: [116.2640, 32.6290], zoom: 12 },
            { code: 'fuyang-jieshou', adcode: '341282', name: '界首市', level: 'county', center: [115.3740, 33.2570], zoom: 12 }
          ]
        },
        {
          code: 'suzhou',
          adcode: '341300',
          name: '宿州市',
          level: 'city',
          center: [116.9841, 33.6332],
          zoom: 10,
          children: [
            { code: 'suzhou-yongqiao', adcode: '341302', name: '埇桥区', level: 'county', center: [116.9770, 33.6400], zoom: 12 },
            { code: 'suzhou-dangshan', adcode: '341321', name: '砀山县', level: 'county', center: [116.3660, 34.4280], zoom: 12 },
            { code: 'suzhou-xiao', adcode: '341322', name: '萧县', level: 'county', center: [116.9450, 34.1870], zoom: 12 },
            { code: 'suzhou-lingbi', adcode: '341323', name: '灵璧县', level: 'county', center: [117.5580, 33.5430], zoom: 12 },
            { code: 'suzhou-si', adcode: '341324', name: '泗县', level: 'county', center: [117.8850, 33.4830], zoom: 12 }
          ]
        },
        {
          code: 'luan',
          adcode: '341500',
          name: '六安市',
          level: 'city',
          center: [116.5078, 31.7529],
          zoom: 10,
          children: [
            { code: "lu'an-jin'an", adcode: '341502', name: '金安区', level: 'county', center: [116.5400, 31.7500], zoom: 12 },
            { code: "lu'an-yu'an", adcode: '341503', name: '裕安区', level: 'county', center: [116.4790, 31.7370], zoom: 12 },
            { code: 'luan-yeji', adcode: '341504', name: '叶集区', level: 'county', center: [116.2790, 31.8640], zoom: 12 },
            { code: 'luan-huoqiu', adcode: '341522', name: '霍邱县', level: 'county', center: [116.2780, 32.3530], zoom: 12 },
            { code: 'luan-shucheng', adcode: '341523', name: '舒城县', level: 'county', center: [116.9440, 31.4620], zoom: 12 },
            { code: 'luan-jinzhai', adcode: '341524', name: '金寨县', level: 'county', center: [115.9340, 31.7270], zoom: 12 },
            { code: 'luan-huoshan', adcode: '341525', name: '霍山县', level: 'county', center: [116.3330, 31.3930], zoom: 12 }
          ]
        },
        {
          code: 'bozhou',
          adcode: '341600',
          name: '亳州市',
          level: 'city',
          center: [115.7783, 33.8446],
          zoom: 10,
          children: [
            { code: 'bozhou-qiaocheng', adcode: '341602', name: '谯城区', level: 'county', center: [115.7790, 33.8760], zoom: 12 },
            { code: 'bozhou-woyang', adcode: '341621', name: '涡阳县', level: 'county', center: [116.2170, 33.4960], zoom: 12 },
            { code: 'bozhou-mengcheng', adcode: '341622', name: '蒙城县', level: 'county', center: [116.5650, 33.2660], zoom: 12 },
            { code: 'bozhou-lixin', adcode: '341623', name: '利辛县', level: 'county', center: [116.2090, 33.1450], zoom: 12 }
          ]
        },
        {
          code: 'chizhou',
          adcode: '341700',
          name: '池州市',
          level: 'city',
          center: [117.4892, 30.6560],
          zoom: 10,
          children: [
            { code: 'chizhou-guichi', adcode: '341702', name: '贵池区', level: 'county', center: [117.5670, 30.6870], zoom: 12 },
            { code: 'chizhou-dongzhi', adcode: '341721', name: '东至县', level: 'county', center: [117.0270, 30.0980], zoom: 12 },
            { code: 'chizhou-shitai', adcode: '341722', name: '石台县', level: 'county', center: [117.4870, 30.2110], zoom: 12 },
            { code: 'chizhou-qingyang', adcode: '341723', name: '青阳县', level: 'county', center: [117.8470, 30.6390], zoom: 12 }
          ]
        },
        {
          code: 'xuancheng',
          adcode: '341800',
          name: '宣城市',
          level: 'city',
          center: [118.7588, 30.9406],
          zoom: 10,
          children: [
            { code: 'xuancheng-xuanzhou', adcode: '341802', name: '宣州区', level: 'county', center: [118.7550, 30.9460], zoom: 12 },
            { code: 'xuancheng-langxi', adcode: '341821', name: '郎溪县', level: 'county', center: [119.1790, 31.1260], zoom: 12 },
            { code: 'xuancheng-guangde', adcode: '341822', name: '广德市', level: 'county', center: [119.4170, 30.8920], zoom: 12 },
            { code: 'xuancheng-ningguo', adcode: '341823', name: '宁国市', level: 'county', center: [118.9560, 30.6340], zoom: 12 },
            { code: 'xuancheng-jing', adcode: '341824', name: '泾县', level: 'county', center: [118.4190, 30.6850], zoom: 12 },
            { code: 'xuancheng-jixi', adcode: '341825', name: '绩溪县', level: 'county', center: [118.5800, 30.0680], zoom: 12 },
            { code: 'xuancheng-jingde', adcode: '341826', name: '旌德县', level: 'county', center: [118.5410, 30.2870], zoom: 12 }
          ]
        }
      ]
    },
    {
      code: 'jiangxi',
      adcode: '360000',
      name: '江西省',
      level: 'province',
      center: [115.8581, 28.6829],
      zoom: 7,
      children: [
        {
          code: 'nanchang',
          adcode: '360100',
          name: '南昌市',
          level: 'city',
          center: [115.8581, 28.6829],
          zoom: 10,
          children: [
            { code: 'nanchang-donghu', adcode: '360102', name: '东湖区', level: 'county', center: [115.8990, 28.6850], zoom: 12 },
            { code: 'nanchang-xihu', adcode: '360103', name: '西湖区', level: 'county', center: [115.8770, 28.6570], zoom: 12 },
            { code: 'nanchang-qingyunpu', adcode: '360104', name: '青云谱区', level: 'county', center: [115.9250, 28.6210], zoom: 12 },
            { code: 'nanchang-qingshanhu', adcode: '360111', name: '青山湖区', level: 'county', center: [115.9620, 28.6820], zoom: 12 },
            { code: 'nanchang-xinjian', adcode: '360112', name: '新建区', level: 'county', center: [115.8150, 28.6920], zoom: 12 },
            { code: 'nanchang-honggutan', adcode: '360113', name: '红谷滩区', level: 'county', center: [115.8580, 28.6980], zoom: 12 },
            { code: 'nanchang-nanchang', adcode: '360121', name: '南昌县', level: 'county', center: [115.9440, 28.5460], zoom: 12 },
            { code: 'nanchang-anyi', adcode: '360123', name: '安义县', level: 'county', center: [115.5490, 28.8450], zoom: 12 },
            { code: 'nanchang-jinxian', adcode: '360124', name: '进贤县', level: 'county', center: [116.2410, 28.3770], zoom: 12 }
          ]
        },
        {
          code: 'jingdezhen',
          adcode: '360200',
          name: '景德镇市',
          level: 'city',
          center: [117.1784, 29.2688],
          zoom: 10,
          children: [
            { code: 'jingdezhen-changjiang', adcode: '360202', name: '昌江区', level: 'county', center: [117.1840, 29.2740], zoom: 12 },
            { code: 'jingdezhen-zhushan', adcode: '360203', name: '珠山区', level: 'county', center: [117.2020, 29.3010], zoom: 12 },
            { code: 'jingdezhen-fuliang', adcode: '360222', name: '浮梁县', level: 'county', center: [117.2150, 29.3520], zoom: 12 },
            { code: 'jingdezhen-leping', adcode: '360281', name: '乐平市', level: 'county', center: [117.1290, 28.9620], zoom: 12 }
          ]
        },
        {
          code: 'pingxiang',
          adcode: '360300',
          name: '萍乡市',
          level: 'city',
          center: [113.8546, 27.6229],
          zoom: 10,
          children: [
            { code: 'pingxiang-anyuan', adcode: '360302', name: '安源区', level: 'county', center: [113.8720, 27.6170], zoom: 12 },
            { code: 'pingxiang-xiangdong', adcode: '360313', name: '湘东区', level: 'county', center: [113.7440, 27.6400], zoom: 12 },
            { code: 'pingxiang-lianhua', adcode: '360321', name: '莲花县', level: 'county', center: [113.8910, 27.1280], zoom: 12 },
            { code: 'pingxiang-shangli', adcode: '360322', name: '上栗县', level: 'county', center: [113.7950, 27.8800], zoom: 12 },
            { code: 'pingxiang-luxi', adcode: '360323', name: '芦溪县', level: 'county', center: [114.0300, 27.6310], zoom: 12 }
          ]
        },
        {
          code: 'jiujiang',
          adcode: '360400',
          name: '九江市',
          level: 'city',
          center: [115.9929, 29.7121],
          zoom: 10,
          children: [
            { code: 'jiujiang-lianxi', adcode: '360402', name: '濂溪区', level: 'county', center: [116.0120, 29.6780], zoom: 12 },
            { code: 'jiujiang-xunyang', adcode: '360403', name: '浔阳区', level: 'county', center: [115.9900, 29.7120], zoom: 12 },
            { code: 'jiujiang-chaisang', adcode: '360404', name: '柴桑区', level: 'county', center: [115.9050, 29.6080], zoom: 12 },
            { code: 'jiujiang-wuning', adcode: '360423', name: '武宁县', level: 'county', center: [115.1010, 29.2580], zoom: 12 },
            { code: 'jiujiang-xiushui', adcode: '360424', name: '修水县', level: 'county', center: [114.5470, 29.0260], zoom: 12 },
            { code: 'jiujiang-yongxiu', adcode: '360425', name: '永修县', level: 'county', center: [115.8090, 29.0210], zoom: 12 },
            { code: 'jiujiang-dean', adcode: '360426', name: '德安县', level: 'county', center: [115.7560, 29.3140], zoom: 12 },
            { code: 'jiujiang-duchang', adcode: '360428', name: '都昌县', level: 'county', center: [116.2040, 29.2730], zoom: 12 },
            { code: 'jiujiang-hukou', adcode: '360429', name: '湖口县', level: 'county', center: [116.2180, 29.7390], zoom: 12 },
            { code: 'jiujiang-pengze', adcode: '360430', name: '彭泽县', level: 'county', center: [116.5500, 29.8960], zoom: 12 },
            { code: 'jiujiang-ruichang', adcode: '360481', name: '瑞昌市', level: 'county', center: [115.6820, 29.6760], zoom: 12 },
            { code: 'jiujiang-gongqingcheng', adcode: '360482', name: '共青城市', level: 'county', center: [115.7870, 29.2480], zoom: 12 },
            { code: 'jiujiang-lushan', adcode: '360483', name: '庐山市', level: 'county', center: [116.0420, 29.4480], zoom: 12 }
          ]
        },
        {
          code: 'xinyu',
          adcode: '360500',
          name: '新余市',
          level: 'city',
          center: [114.9168, 27.8178],
          zoom: 10,
          children: [
            { code: 'xinyu-yushui', adcode: '360502', name: '渝水区', level: 'county', center: [114.9440, 27.7980], zoom: 12 },
            { code: 'xinyu-fenyi', adcode: '360521', name: '分宜县', level: 'county', center: [114.6920, 27.8140], zoom: 12 }
          ]
        },
        {
          code: 'yingtan',
          adcode: '360600',
          name: '鹰潭市',
          level: 'city',
          center: [117.0692, 28.2386],
          zoom: 10,
          children: [
            { code: 'yingtan-yuehu', adcode: '360602', name: '月湖区', level: 'county', center: [117.0370, 28.2390], zoom: 12 },
            { code: 'yingtan-yujiang', adcode: '360622', name: '余江区', level: 'county', center: [116.8220, 28.2100], zoom: 12 },
            { code: 'yingtan-guixi', adcode: '360681', name: '贵溪市', level: 'county', center: [117.2450, 28.2920], zoom: 12 }
          ]
        },
        {
          code: 'ganzhou',
          adcode: '360700',
          name: '赣州市',
          level: 'city',
          center: [114.9350, 25.8319],
          zoom: 9,
          children: [
            { code: 'ganzhou-zhanggong', adcode: '360702', name: '章贡区', level: 'county', center: [114.9420, 25.8290], zoom: 12 },
            { code: 'ganzhou-nankang', adcode: '360703', name: '南康区', level: 'county', center: [114.7650, 25.6610], zoom: 12 },
            { code: 'ganzhou-ganxian', adcode: '360704', name: '赣县区', level: 'county', center: [115.0000, 25.8610], zoom: 12 },
            { code: 'ganzhou-xinfeng', adcode: '360722', name: '信丰县', level: 'county', center: [114.9230, 25.3870], zoom: 12 },
            { code: 'ganzhou-dayu', adcode: '360723', name: '大余县', level: 'county', center: [114.3620, 25.3960], zoom: 12 },
            { code: 'ganzhou-shangyou', adcode: '360724', name: '上犹县', level: 'county', center: [114.5410, 25.7930], zoom: 12 },
            { code: 'ganzhou-chongyi', adcode: '360725', name: '崇义县', level: 'county', center: [114.3080, 25.6820], zoom: 12 },
            { code: 'ganzhou-anning', adcode: '360726', name: '安远县', level: 'county', center: [115.3950, 25.1370], zoom: 12 },
            { code: 'ganzhou-longnan', adcode: '360727', name: '龙南市', level: 'county', center: [114.7890, 24.9110], zoom: 12 },
            { code: 'ganzhou-dingnan', adcode: '360728', name: '定南县', level: 'county', center: [115.0280, 24.7840], zoom: 12 },
            { code: 'ganzhou-quannan', adcode: '360729', name: '全南县', level: 'county', center: [114.5300, 24.7420], zoom: 12 },
            { code: 'ganzhou-ningdu', adcode: '360730', name: '宁都县', level: 'county', center: [116.0120, 26.4710], zoom: 12 },
            { code: 'ganzhou-yudu', adcode: '360731', name: '于都县', level: 'county', center: [115.4160, 25.9520], zoom: 12 },
            { code: 'ganzhou-xingguo', adcode: '360732', name: '兴国县', level: 'county', center: [115.3630, 26.3380], zoom: 12 },
            { code: 'ganzhou-huichang', adcode: '360733', name: '会昌县', level: 'county', center: [115.7860, 25.6000], zoom: 12 },
            { code: 'ganzhou-xunwu', adcode: '360734', name: '寻乌县', level: 'county', center: [115.6480, 24.9550], zoom: 12 },
            { code: 'ganzhou-shicheng', adcode: '360735', name: '石城县', level: 'county', center: [116.3440, 26.3270], zoom: 12 },
            { code: 'ganzhou-ruijin', adcode: '360781', name: '瑞金市', level: 'county', center: [116.0270, 25.8860], zoom: 12 }
          ]
        },
        {
          code: 'jian',
          adcode: '360800',
          name: '吉安市',
          level: 'city',
          center: [114.9926, 27.1139],
          zoom: 10,
          children: [
            { code: 'jian-jizhou', adcode: '360802', name: '吉州区', level: 'county', center: [114.9760, 27.1070], zoom: 12 },
            { code: 'jian-qingyuan', adcode: '360803', name: '青原区', level: 'county', center: [115.0170, 27.1060], zoom: 12 },
            { code: 'jian-jian', adcode: '360821', name: '吉安县', level: 'county', center: [114.9070, 27.0400], zoom: 12 },
            { code: 'jian-jishui', adcode: '360822', name: '吉水县', level: 'county', center: [115.1350, 27.2100], zoom: 12 },
            { code: 'jian-xiajiang', adcode: '360823', name: '峡江县', level: 'county', center: [115.3170, 27.5830], zoom: 12 },
            { code: 'jian-xingan', adcode: '360824', name: '新干县', level: 'county', center: [115.3930, 27.7410], zoom: 12 },
            { code: 'jian-yongfeng', adcode: '360825', name: '永丰县', level: 'county', center: [115.4420, 27.3180], zoom: 12 },
            { code: 'jian-taihe', adcode: '360826', name: '泰和县', level: 'county', center: [114.9080, 26.7900], zoom: 12 },
            { code: 'jian-suichuan', adcode: '360827', name: '遂川县', level: 'county', center: [114.5170, 26.3120], zoom: 12 },
            { code: 'jian-wanan', adcode: '360828', name: '万安县', level: 'county', center: [114.7860, 26.4590], zoom: 12 },
            { code: 'jian-ninggang', adcode: '360829', name: '宁冈县', level: 'county', center: [113.9700, 26.5700], zoom: 12 },
            { code: 'jian-yongxin', adcode: '360830', name: '永新县', level: 'county', center: [114.2430, 26.9450], zoom: 12 },
            { code: 'jian-jinggangshan', adcode: '360881', name: '井冈山市', level: 'county', center: [114.2890, 26.7480], zoom: 12 }
          ]
        },
        {
          code: 'yichun',
          adcode: '360900',
          name: '宜春市',
          level: 'city',
          center: [114.4166, 27.8156],
          zoom: 10,
          children: [
            { code: 'yichun-yuanzhou', adcode: '360902', name: '袁州区', level: 'county', center: [114.3830, 27.8000], zoom: 12 },
            { code: 'yichun-fengxin', adcode: '360921', name: '奉新县', level: 'county', center: [115.3850, 28.7000], zoom: 12 },
            { code: 'yichun-wanzai', adcode: '360922', name: '万载县', level: 'county', center: [114.4460, 28.1060], zoom: 12 },
            { code: 'yichun-shanggao', adcode: '360923', name: '上高县', level: 'county', center: [114.9250, 28.2340], zoom: 12 },
            { code: 'yichun-yifeng', adcode: '360924', name: '宜丰县', level: 'county', center: [114.7800, 28.3940], zoom: 12 },
            { code: 'yichun-jingan', adcode: '360925', name: '靖安县', level: 'county', center: [115.3620, 28.8610], zoom: 12 },
            { code: 'yichun-tonggu', adcode: '360926', name: '铜鼓县', level: 'county', center: [114.3700, 28.5210], zoom: 12 },
            { code: 'yichun-fengcheng', adcode: '360981', name: '丰城市', level: 'county', center: [115.7710, 28.1590], zoom: 12 },
            { code: 'yichun-zhangshu', adcode: '360982', name: '樟树市', level: 'county', center: [115.5460, 28.0550], zoom: 12 },
            { code: 'yichun-gaoan', adcode: '360983', name: '高安市', level: 'county', center: [115.3760, 28.4170], zoom: 12 }
          ]
        },
        {
          code: 'fuzhou',
          adcode: '361000',
          name: '抚州市',
          level: 'city',
          center: [116.3583, 27.9489],
          zoom: 10,
          children: [
            { code: 'fuzhou-linchuan', adcode: '361002', name: '临川区', level: 'county', center: [116.3130, 27.9340], zoom: 12 },
            { code: 'fuzhou-dongxiang', adcode: '361003', name: '东乡区', level: 'county', center: [116.6010, 28.2360], zoom: 12 },
            { code: 'fuzhou-nancheng', adcode: '361021', name: '南城县', level: 'county', center: [116.6260, 27.5550], zoom: 12 },
            { code: 'fuzhou-lichuan', adcode: '361022', name: '黎川县', level: 'county', center: [116.9080, 27.2820], zoom: 12 },
            { code: 'fuzhou-nanfeng', adcode: '361023', name: '南丰县', level: 'county', center: [116.5260, 27.2180], zoom: 12 },
            { code: 'fuzhou-chongren', adcode: '361024', name: '崇仁县', level: 'county', center: [116.0610, 27.7590], zoom: 12 },
            { code: 'fuzhou-lean', adcode: '361025', name: '乐安县', level: 'county', center: [115.8310, 27.4280], zoom: 12 },
            { code: 'fuzhou-yihuang', adcode: '361026', name: '宜黄县', level: 'county', center: [116.2220, 27.5460], zoom: 12 },
            { code: 'fuzhou-jinxi', adcode: '361027', name: '金溪县', level: 'county', center: [116.7740, 27.9180], zoom: 12 },
            { code: 'fuzhou-zixi', adcode: '361028', name: '资溪县', level: 'county', center: [117.0690, 27.7060], zoom: 12 },
            { code: 'fuzhou-jinxi', adcode: '361029', name: '进贤县', level: 'county', center: [116.2410, 28.3770], zoom: 12 },
            { code: 'fuzhou-guangchang', adcode: '361030', name: '广昌县', level: 'county', center: [116.3260, 26.8380], zoom: 12 }
          ]
        },
        {
          code: 'shangrao',
          adcode: '361100',
          name: '上饶市',
          level: 'city',
          center: [117.9434, 28.4544],
          zoom: 10,
          children: [
            { code: 'shangrao-xinzhou', adcode: '361102', name: '信州区', level: 'county', center: [117.9660, 28.4310], zoom: 12 },
            { code: 'shangrao-guangfeng', adcode: '361103', name: '广丰区', level: 'county', center: [118.1910, 28.4360], zoom: 12 },
            { code: 'shangrao-shangrao', adcode: '361121', name: '上饶县', level: 'county', center: [117.9090, 28.4490], zoom: 12 },
            { code: 'shangrao-yushan', adcode: '361123', name: '玉山县', level: 'county', center: [118.2450, 28.6820], zoom: 12 },
            { code: 'shangrao-qianshan', adcode: '361124', name: '铅山县', level: 'county', center: [117.7090, 28.3150], zoom: 12 },
            { code: 'shangrao-hengfeng', adcode: '361125', name: '横峰县', level: 'county', center: [117.5960, 28.4070], zoom: 12 },
            { code: 'shangrao-yiyang', adcode: '361126', name: '弋阳县', level: 'county', center: [117.4500, 28.3780], zoom: 12 },
            { code: 'shangrao-yugan', adcode: '361127', name: '余干县', level: 'county', center: [116.6950, 28.7020], zoom: 12 },
            { code: 'shangrao-poyang', adcode: '361128', name: '鄱阳县', level: 'county', center: [116.6990, 29.0010], zoom: 12 },
            { code: 'shangrao-wannian', adcode: '361129', name: '万年县', level: 'county', center: [117.0690, 28.6950], zoom: 12 },
            { code: 'shangrao-wuyuan', adcode: '361130', name: '婺源县', level: 'county', center: [117.8620, 29.2480], zoom: 12 },
            { code: 'shangrao-dexing', adcode: '361181', name: '德兴市', level: 'county', center: [117.5780, 28.9460], zoom: 12 }
          ]
        }
      ]
    },
    {
      code: 'shaanxi',
      adcode: '610000',
      name: '陕西省',
      level: 'province',
      center: [108.9402, 34.3416],
      zoom: 7,
      children: [
        {
          code: 'xian',
          adcode: '610100',
          name: '西安市',
          level: 'city',
          center: [108.9402, 34.3416],
          zoom: 10,
          children: [
            { code: 'xian-xincheng', adcode: '610102', name: '新城区', level: 'county', center: [108.9600, 34.2670], zoom: 12 },
            { code: 'xian-beilin', adcode: '610103', name: '碑林区', level: 'county', center: [108.9340, 34.2310], zoom: 12 },
            { code: 'xian-lianhu', adcode: '610104', name: '莲湖区', level: 'county', center: [108.9400, 34.2650], zoom: 12 },
            { code: 'xian-baqiao', adcode: '610111', name: '灞桥区', level: 'county', center: [109.0650, 34.2730], zoom: 12 },
            { code: 'xian-weiyang', adcode: '610112', name: '未央区', level: 'county', center: [108.9460, 34.2930], zoom: 12 },
            { code: 'xian-yanta', adcode: '610113', name: '雁塔区', level: 'county', center: [108.9270, 34.2220], zoom: 12 },
            { code: 'xian-yanliang', adcode: '610114', name: '阎良区', level: 'county', center: [109.2260, 34.6620], zoom: 12 },
            { code: 'xian-lintong', adcode: '610115', name: '临潼区', level: 'county', center: [109.2140, 34.3670], zoom: 12 },
            { code: 'xian-changan', adcode: '610116', name: '长安区', level: 'county', center: [108.9070, 34.1580], zoom: 12 },
            { code: 'xian-gaoling', adcode: '610117', name: '高陵区', level: 'county', center: [109.0880, 34.5350], zoom: 12 },
            { code: 'xian-huyi', adcode: '610118', name: '鄠邑区', level: 'county', center: [108.6050, 34.1090], zoom: 12 },
            { code: 'xian-lantian', adcode: '610122', name: '蓝田县', level: 'county', center: [109.3230, 34.1510], zoom: 12 },
            { code: 'xian-zhouzhi', adcode: '610124', name: '周至县', level: 'county', center: [108.2220, 34.1630], zoom: 12 }
          ]
        },
        {
          code: 'tongchuan',
          adcode: '610200',
          name: '铜川市',
          level: 'city',
          center: [108.9452, 34.8969],
          zoom: 10,
          children: [
            { code: 'tongchuan-wangyi', adcode: '610202', name: '王益区', level: 'county', center: [109.0760, 35.0690], zoom: 12 },
            { code: 'tongchuan-yintai', adcode: '610203', name: '印台区', level: 'county', center: [109.1020, 35.1150], zoom: 12 },
            { code: 'tongchuan-yaozhou', adcode: '610204', name: '耀州区', level: 'county', center: [108.9810, 34.9100], zoom: 12 },
            { code: 'tongchuan-yijun', adcode: '610222', name: '宜君县', level: 'county', center: [109.1170, 35.4020], zoom: 12 }
          ]
        },
        {
          code: 'baoji',
          adcode: '610300',
          name: '宝鸡市',
          level: 'city',
          center: [107.1449, 34.3693],
          zoom: 10,
          children: [
            { code: 'baoji-weibin', adcode: '610302', name: '渭滨区', level: 'county', center: [107.1490, 34.3710], zoom: 12 },
            { code: 'baoji-jintai', adcode: '610303', name: '金台区', level: 'county', center: [107.1470, 34.3760], zoom: 12 },
            { code: 'baoji-chencang', adcode: '610304', name: '陈仓区', level: 'county', center: [107.3870, 34.3540], zoom: 12 },
            { code: 'baoji-fengxiang', adcode: '610322', name: '凤翔区', level: 'county', center: [107.3950, 34.5210], zoom: 12 },
            { code: 'baoji-qishan', adcode: '610323', name: '岐山县', level: 'county', center: [107.6210, 34.4440], zoom: 12 },
            { code: 'baoji-fufeng', adcode: '610324', name: '扶风县', level: 'county', center: [107.9000, 34.3760], zoom: 12 },
            { code: 'baoji-meixian', adcode: '610326', name: '眉县', level: 'county', center: [107.7520, 34.2750], zoom: 12 },
            { code: 'baoji-lingyou', adcode: '610327', name: '陇县', level: 'county', center: [106.8590, 34.8940], zoom: 12 },
            { code: 'baoji-qianyang', adcode: '610328', name: '千阳县', level: 'county', center: [107.1320, 34.6430], zoom: 12 },
            { code: 'baoji-linyou', adcode: '610329', name: '麟游县', level: 'county', center: [107.7930, 34.6780], zoom: 12 },
            { code: 'baoji-fengxian', adcode: '610330', name: '凤县', level: 'county', center: [106.5220, 33.9120], zoom: 12 },
            { code: 'baoji-taibai', adcode: '610331', name: '太白县', level: 'county', center: [107.3190, 34.0580], zoom: 12 }
          ]
        },
        {
          code: 'xianyang',
          adcode: '610400',
          name: '咸阳市',
          level: 'city',
          center: [108.7093, 34.3296],
          zoom: 10,
          children: [
            { code: 'xianyang-qindu', adcode: '610402', name: '秦都区', level: 'county', center: [108.7150, 34.3300], zoom: 12 },
            { code: 'xianyang-yangling', adcode: '610403', name: '杨陵区', level: 'county', center: [108.0840, 34.2720], zoom: 12 },
            { code: 'xianyang-weicheng', adcode: '610404', name: '渭城区', level: 'county', center: [108.7370, 34.3620], zoom: 12 },
            { code: 'xianyang-xingping', adcode: '610481', name: '兴平市', level: 'county', center: [108.4900, 34.3000], zoom: 12 },
            { code: 'xianyang-sanyuan', adcode: '610422', name: '三原县', level: 'county', center: [108.9410, 34.6170], zoom: 12 },
            { code: 'xianyang-jingyang', adcode: '610423', name: '泾阳县', level: 'county', center: [108.8430, 34.5270], zoom: 12 },
            { code: 'xianyang-qianxian', adcode: '610424', name: '乾县', level: 'county', center: [108.2430, 34.5280], zoom: 12 },
            { code: 'xianyang-liquan', adcode: '610425', name: '礼泉县', level: 'county', center: [108.4260, 34.4820], zoom: 12 },
            { code: 'xianyang-yongshou', adcode: '610426', name: '永寿县', level: 'county', center: [108.1440, 34.6910], zoom: 12 },
            { code: 'xianyang-changwu', adcode: '610428', name: '长武县', level: 'county', center: [107.7950, 35.2060], zoom: 12 },
            { code: 'xianyang-xunyi', adcode: '610429', name: '旬邑县', level: 'county', center: [108.3350, 35.1130], zoom: 12 },
            { code: 'xianyang-chunhua', adcode: '610430', name: '淳化县', level: 'county', center: [108.5810, 34.7980], zoom: 12 },
            { code: 'xianyang-wugong', adcode: '610431', name: '武功县', level: 'county', center: [108.2040, 34.2600], zoom: 12 },
            { code: 'xianyang-binzhou', adcode: '610482', name: '彬州市', level: 'county', center: [108.0850, 35.0340], zoom: 12 }
          ]
        },
        {
          code: 'weinan',
          adcode: '610500',
          name: '渭南市',
          level: 'city',
          center: [109.5097, 34.5024],
          zoom: 10,
          children: [
            { code: 'weinan-linwei', adcode: '610502', name: '临渭区', level: 'county', center: [109.4930, 34.4980], zoom: 12 },
            { code: 'weinan-huazhou', adcode: '610503', name: '华州区', level: 'county', center: [109.7720, 34.5150], zoom: 12 },
            { code: 'weinan-tongguan', adcode: '610522', name: '潼关县', level: 'county', center: [110.2460, 34.4890], zoom: 12 },
            { code: 'weinan-dali', adcode: '610523', name: '大荔县', level: 'county', center: [109.9420, 34.7950], zoom: 12 },
            { code: 'weinan-heyang', adcode: '610524', name: '合阳县', level: 'county', center: [110.1490, 35.2380], zoom: 12 },
            { code: 'weinan-chengcheng', adcode: '610525', name: '澄城县', level: 'county', center: [109.9330, 35.1840], zoom: 12 },
            { code: 'weinan-pucheng', adcode: '610526', name: '蒲城县', level: 'county', center: [109.5860, 34.9560], zoom: 12 },
            { code: 'weinan-baishui', adcode: '610527', name: '白水县', level: 'county', center: [109.5910, 35.1760], zoom: 12 },
            { code: 'weinan-fuping', adcode: '610528', name: '富平县', level: 'county', center: [109.1800, 34.7530], zoom: 12 },
            { code: 'weinan-huayin', adcode: '610582', name: '华阴市', level: 'county', center: [110.0920, 34.5670], zoom: 12 },
            { code: 'weinan-hancheng', adcode: '610581', name: '韩城市', level: 'county', center: [110.4430, 35.4790], zoom: 12 }
          ]
        },
        {
          code: 'yanan',
          adcode: '610600',
          name: '延安市',
          level: 'city',
          center: [109.4908, 36.5965],
          zoom: 9,
          children: [
            { code: 'yanan-baota', adcode: '610602', name: '宝塔区', level: 'county', center: [109.4930, 36.5910], zoom: 12 },
            { code: 'yanan-ansai', adcode: '610603', name: '安塞区', level: 'county', center: [109.3270, 36.8650], zoom: 12 },
            { code: 'yanan-yanchang', adcode: '610621', name: '延长县', level: 'county', center: [110.0120, 36.5790], zoom: 12 },
            { code: 'yanan-yanchuan', adcode: '610622', name: '延川县', level: 'county', center: [110.1940, 36.8780], zoom: 12 },
            { code: 'yanan-zichang', adcode: '610623', name: '子长市', level: 'county', center: [109.6750, 37.1430], zoom: 12 },
            { code: 'yanan-zhidan', adcode: '610625', name: '志丹县', level: 'county', center: [108.7680, 36.8220], zoom: 12 },
            { code: 'yanan-wuqi', adcode: '610626', name: '吴起县', level: 'county', center: [108.1200, 36.9270], zoom: 12 },
            { code: 'yanan-ganquan', adcode: '610627', name: '甘泉县', level: 'county', center: [109.3510, 36.2770], zoom: 12 },
            { code: 'yanan-fuxian', adcode: '610628', name: '富县', level: 'county', center: [109.3790, 35.9880], zoom: 12 },
            { code: 'yanan-luochuan', adcode: '610629', name: '洛川县', level: 'county', center: [109.4330, 35.7610], zoom: 12 },
            { code: 'yanan-yichuan', adcode: '610630', name: '宜川县', level: 'county', center: [110.1720, 36.0500], zoom: 12 },
            { code: 'yanan-huanglong', adcode: '610631', name: '黄龙县', level: 'county', center: [109.8400, 35.5840], zoom: 12 },
            { code: 'yanan-huangling', adcode: '610632', name: '黄陵县', level: 'county', center: [109.2630, 35.5830], zoom: 12 }
          ]
        },
        {
          code: 'hanzhong',
          adcode: '610700',
          name: '汉中市',
          level: 'city',
          center: [107.0231, 33.0677],
          zoom: 9,
          children: [
            { code: 'hanzhong-hantai', adcode: '610702', name: '汉台区', level: 'county', center: [107.0320, 33.0680], zoom: 12 },
            { code: 'hanzhong-nanzheng', adcode: '610703', name: '南郑区', level: 'county', center: [106.9360, 32.9990], zoom: 12 },
            { code: 'hanzhong-chenggu', adcode: '610722', name: '城固县', level: 'county', center: [107.3340, 33.1570], zoom: 12 },
            { code: 'hanzhong-yangxian', adcode: '610723', name: '洋县', level: 'county', center: [107.5460, 33.2230], zoom: 12 },
            { code: 'hanzhong-xixiang', adcode: '610724', name: '西乡县', level: 'county', center: [107.7680, 32.9840], zoom: 12 },
            { code: 'hanzhong-mianxian', adcode: '610725', name: '勉县', level: 'county', center: [106.6730, 33.1530], zoom: 12 },
            { code: 'hanzhong-ningqiang', adcode: '610726', name: '宁强县', level: 'county', center: [106.2590, 32.8300], zoom: 12 },
            { code: 'hanzhong-lueyang', adcode: '610727', name: '略阳县', level: 'county', center: [106.1570, 33.3300], zoom: 12 },
            { code: 'hanzhong-zhenba', adcode: '610728', name: '镇巴县', level: 'county', center: [107.8960, 32.5360], zoom: 12 },
            { code: 'hanzhong-liuba', adcode: '610729', name: '留坝县', level: 'county', center: [106.9220, 33.6170], zoom: 12 },
            { code: 'hanzhong-fooping', adcode: '610730', name: '佛坪县', level: 'county', center: [107.9900, 33.5240], zoom: 12 }
          ]
        },
        {
          code: 'yulin',
          adcode: '610800',
          name: '榆林市',
          level: 'city',
          center: [109.7346, 38.2854],
          zoom: 9,
          children: [
            { code: 'yulin-yuyang', adcode: '610802', name: '榆阳区', level: 'county', center: [109.7210, 38.2770], zoom: 12 },
            { code: 'yulin-hengshan', adcode: '610803', name: '横山区', level: 'county', center: [109.2940, 37.9560], zoom: 12 },
            { code: 'yulin-shenmu', adcode: '610881', name: '神木市', level: 'county', center: [110.4990, 38.8420], zoom: 12 },
            { code: 'yulin-fugu', adcode: '610822', name: '府谷县', level: 'county', center: [111.0670, 39.0280], zoom: 12 },
            { code: 'yulin-jingbian', adcode: '610824', name: '靖边县', level: 'county', center: [108.7940, 37.5990], zoom: 12 },
            { code: 'yulin-dingbian', adcode: '610825', name: '定边县', level: 'county', center: [107.6010, 37.5940], zoom: 12 },
            { code: 'yulin-suide', adcode: '610826', name: '绥德县', level: 'county', center: [110.2610, 37.5030], zoom: 12 },
            { code: 'yulin-mizhi', adcode: '610827', name: '米脂县', level: 'county', center: [110.1840, 37.7550], zoom: 12 },
            { code: 'yulin-jiaxian', adcode: '610828', name: '佳县', level: 'county', center: [110.4910, 38.0190], zoom: 12 },
            { code: 'yulin-wubu', adcode: '610829', name: '吴堡县', level: 'county', center: [110.7400, 37.4520], zoom: 12 },
            { code: 'yulin-qingjian', adcode: '610830', name: '清涧县', level: 'county', center: [110.1210, 37.0890], zoom: 12 },
            { code: 'yulin-zizhou', adcode: '610831', name: '子洲县', level: 'county', center: [110.0350, 37.6100], zoom: 12 }
          ]
        },
        {
          code: 'ankang',
          adcode: '610900',
          name: '安康市',
          level: 'city',
          center: [109.0293, 32.6847],
          zoom: 9,
          children: [
            { code: 'ankang-hanbin', adcode: '610902', name: '汉滨区', level: 'county', center: [109.0270, 32.6950], zoom: 12 },
            { code: 'ankang-hanyin', adcode: '610921', name: '汉阴县', level: 'county', center: [108.5090, 32.8920], zoom: 12 },
            { code: 'ankang-shiquan', adcode: '610922', name: '石泉县', level: 'county', center: [108.2470, 33.0380], zoom: 12 },
            { code: 'ankang-ningxia', adcode: '610923', name: '宁陕县', level: 'county', center: [108.3130, 33.3110], zoom: 12 },
            { code: 'ankang-ziyang', adcode: '610924', name: '紫阳县', level: 'county', center: [108.5350, 32.5200], zoom: 12 },
            { code: 'ankang-langao', adcode: '610925', name: '岚皋县', level: 'county', center: [108.9020, 32.3080], zoom: 12 },
            { code: 'ankang-pingli', adcode: '610926', name: '平利县', level: 'county', center: [109.3580, 32.3890], zoom: 12 },
            { code: 'ankang-zhenping', adcode: '610927', name: '镇坪县', level: 'county', center: [109.5240, 31.8840], zoom: 12 },
            { code: 'ankang-xunyang', adcode: '610928', name: '旬阳市', level: 'county', center: [109.3620, 32.8320], zoom: 12 },
            { code: 'ankang-baihe', adcode: '610929', name: '白河县', level: 'county', center: [110.1130, 32.8090], zoom: 12 }
          ]
        },
        {
          code: 'shangluo',
          adcode: '611000',
          name: '商洛市',
          level: 'city',
          center: [109.9403, 33.8704],
          zoom: 9,
          children: [
            { code: 'shangluo-shangzhou', adcode: '611002', name: '商州区', level: 'county', center: [109.9410, 33.8630], zoom: 12 },
            { code: 'shangluo-luonan', adcode: '611021', name: '洛南县', level: 'county', center: [110.1460, 34.0900], zoom: 12 },
            { code: 'shangluo-danfeng', adcode: '611022', name: '丹凤县', level: 'county', center: [110.3270, 33.6950], zoom: 12 },
            { code: 'shangluo-shangnan', adcode: '611023', name: '商南县', level: 'county', center: [110.8810, 33.5310], zoom: 12 },
            { code: 'shangluo-shanyang', adcode: '611024', name: '山阳县', level: 'county', center: [109.8870, 33.5300], zoom: 12 },
            { code: 'shangluo-zhenan', adcode: '611025', name: '镇安县', level: 'county', center: [109.1530, 33.4230], zoom: 12 },
            { code: 'shangluo-zhashui', adcode: '611026', name: '柞水县', level: 'county', center: [109.1110, 33.6860], zoom: 12 }
          ]
        }
      ]
    },
    {
      code: 'CQ',
      adcode: '500000',
      name: '重庆市',
      level: 'province',
      center: [106.5516, 29.5630],
      zoom: 8,
      children: [
        { code: 'CQ-WZ', adcode: '500101', name: '万州区', level: 'county', center: [108.4090, 30.8080], zoom: 12 },
        { code: 'CQ-FL', adcode: '500102', name: '涪陵区', level: 'county', center: [107.3950, 29.7030], zoom: 12 },
        { code: 'CQ-YZ', adcode: '500103', name: '渝中区', level: 'county', center: [106.5690, 29.5530], zoom: 12 },
        { code: 'CQ-DK', adcode: '500104', name: '大渡口区', level: 'county', center: [106.4830, 29.4840], zoom: 12 },
        { code: 'CQ-JB', adcode: '500105', name: '江北区', level: 'county', center: [106.5740, 29.6060], zoom: 12 },
        { code: 'CQ-SB', adcode: '500106', name: '沙坪坝区', level: 'county', center: [106.4540, 29.5410], zoom: 12 },
        { code: 'CQ-JL', adcode: '500107', name: '九龙坡区', level: 'county', center: [106.5110, 29.5020], zoom: 12 },
        { code: 'CQ-NA', adcode: '500108', name: '南岸区', level: 'county', center: [106.6440, 29.5210], zoom: 12 },
        { code: 'CQ-BN', adcode: '500109', name: '北碚区', level: 'county', center: [106.3960, 29.8050], zoom: 12 },
        { code: 'CQ-QJ', adcode: '500110', name: '綦江区', level: 'county', center: [106.6510, 29.0280], zoom: 12 },
        { code: 'CQ-DZ', adcode: '500111', name: '大足区', level: 'county', center: [105.7210, 29.7070], zoom: 12 },
        { code: 'CQ-YB', adcode: '500112', name: '渝北区', level: 'county', center: [106.6310, 29.7180], zoom: 12 },
        { code: 'CQ-BQ', adcode: '500113', name: '巴南区', level: 'county', center: [106.5400, 29.4030], zoom: 12 },
        { code: 'CQ-QL', adcode: '500114', name: '黔江区', level: 'county', center: [108.7710, 29.5330], zoom: 12 },
        { code: 'CQ-CS', adcode: '500115', name: '长寿区', level: 'county', center: [107.0810, 29.8570], zoom: 12 },
        { code: 'CQ-JJ', adcode: '500116', name: '江津区', level: 'county', center: [106.2590, 29.2900], zoom: 12 },
        { code: 'CQ-HC', adcode: '500117', name: '合川区', level: 'county', center: [106.2760, 29.9720], zoom: 12 },
        { code: 'CQ-YY', adcode: '500118', name: '永川区', level: 'county', center: [105.9270, 29.3560], zoom: 12 },
        { code: 'CQ-NC', adcode: '500119', name: '南川区', level: 'county', center: [107.0990, 29.1570], zoom: 12 },
        { code: 'CQ-BJ', adcode: '500120', name: '璧山区', level: 'county', center: [106.2310, 29.5930], zoom: 12 },
        { code: 'CQ-TR', adcode: '500151', name: '铜梁区', level: 'county', center: [106.0550, 29.8440], zoom: 12 },
        { code: 'CQ-TL', adcode: '500152', name: '潼南区', level: 'county', center: [105.8410, 30.1910], zoom: 12 },
        { code: 'CQ-RC', adcode: '500153', name: '荣昌区', level: 'county', center: [105.5940, 29.4050], zoom: 12 }
      ]
    },
    {
      code: 'SH',
      adcode: '310000',
      name: '上海市',
      level: 'province',
      center: [121.4737, 31.2304],
      zoom: 9,
      children: [
        { code: 'SH-HK', adcode: '310101', name: '黄浦区', level: 'county', center: [121.4840, 31.2310], zoom: 12 },
        { code: 'SH-XH', adcode: '310104', name: '徐汇区', level: 'county', center: [121.4370, 31.1880], zoom: 12 },
        { code: 'SH-CA', adcode: '310105', name: '长宁区', level: 'county', center: [121.4240, 31.2200], zoom: 12 },
        { code: 'SH-JA', adcode: '310106', name: '静安区', level: 'county', center: [121.4590, 31.2470], zoom: 12 },
        { code: 'SH-PT', adcode: '310107', name: '普陀区', level: 'county', center: [121.3950, 31.2500], zoom: 12 },
        { code: 'SH-HK2', adcode: '310109', name: '虹口区', level: 'county', center: [121.5050, 31.2640], zoom: 12 },
        { code: 'SH-YP', adcode: '310110', name: '杨浦区', level: 'county', center: [121.5260, 31.2590], zoom: 12 },
        { code: 'SH-MH', adcode: '310112', name: '闵行区', level: 'county', center: [121.3810, 31.1120], zoom: 12 },
        { code: 'SH-BS', adcode: '310113', name: '宝山区', level: 'county', center: [121.4890, 31.4050], zoom: 12 },
        { code: 'SH-JD', adcode: '310114', name: '嘉定区', level: 'county', center: [121.2650, 31.3750], zoom: 12 },
        { code: 'SH-PD', adcode: '310115', name: '浦东新区', level: 'county', center: [121.5440, 31.2210], zoom: 12 },
        { code: 'SH-JS', adcode: '310116', name: '金山区', level: 'county', center: [121.3420, 30.7420], zoom: 12 },
        { code: 'SH-SJ', adcode: '310117', name: '松江区', level: 'county', center: [121.2280, 31.0320], zoom: 12 },
        { code: 'SH-QP', adcode: '310118', name: '青浦区', level: 'county', center: [121.1240, 31.1500], zoom: 12 },
        { code: 'SH-FX', adcode: '310120', name: '奉贤区', level: 'county', center: [121.4740, 30.9180], zoom: 12 },
        { code: 'SH-CM', adcode: '310151', name: '崇明区', level: 'county', center: [121.3970, 31.6230], zoom: 12 }
      ]
    },
    {
      code: 'GX',
      adcode: '450000',
      name: '广西壮族自治区',
      level: 'province',
      center: [108.3200, 22.8240],
      zoom: 7,
      children: [
        {
          code: 'GX-NN',
          adcode: '450100',
          name: '南宁市',
          level: 'city',
          center: [108.3200, 22.8240],
          zoom: 10,
          children: [
            { code: 'GX-NN-XN', adcode: '450102', name: '兴宁区', level: 'county', center: [108.3660, 22.8540], zoom: 12 },
            { code: 'GX-NN-QX', adcode: '450103', name: '青秀区', level: 'county', center: [108.4950, 22.7850], zoom: 12 },
            { code: 'GX-NN-JN', adcode: '450105', name: '江南区', level: 'county', center: [108.2730, 22.7810], zoom: 12 },
            { code: 'GX-NN-XT', adcode: '450107', name: '西乡塘区', level: 'county', center: [108.3130, 22.8340], zoom: 12 },
            { code: 'GX-NN-LB', adcode: '450108', name: '良庆区', level: 'county', center: [108.3220, 22.7590], zoom: 12 },
            { code: 'GX-NN-YN', adcode: '450109', name: '邕宁区', level: 'county', center: [108.4870, 22.7580], zoom: 12 }
          ]
        },
        {
          code: 'GX-LZ',
          adcode: '450200',
          name: '柳州市',
          level: 'city',
          center: [109.4286, 24.3263],
          zoom: 10,
          children: [
            { code: 'GX-LZ-CH', adcode: '450202', name: '城中区', level: 'county', center: [109.4280, 24.3150], zoom: 12 },
            { code: 'GX-LZ-YF', adcode: '450203', name: '鱼峰区', level: 'county', center: [109.4520, 24.3180], zoom: 12 },
            { code: 'GX-LZ-LN', adcode: '450204', name: '柳南区', level: 'county', center: [109.3850, 24.3360], zoom: 12 },
            { code: 'GX-LZ-LB', adcode: '450205', name: '柳北区', level: 'county', center: [109.4020, 24.3620], zoom: 12 }
          ]
        },
        {
          code: 'GX-GL',
          adcode: '450300',
          name: '桂林市',
          level: 'city',
          center: [110.1799, 25.2345],
          zoom: 10,
          children: [
            { code: 'GX-GL-XP', adcode: '450302', name: '秀峰区', level: 'county', center: [110.2640, 25.2730], zoom: 12 },
            { code: 'GX-GL-DH', adcode: '450303', name: '叠彩区', level: 'county', center: [110.3020, 25.3140], zoom: 12 },
            { code: 'GX-GL-XS', adcode: '450304', name: '象山区', level: 'county', center: [110.2810, 25.2610], zoom: 12 },
            { code: 'GX-GL-QX', adcode: '450305', name: '七星区', level: 'county', center: [110.3180, 25.2520], zoom: 12 },
            { code: 'GX-GL-YS', adcode: '450311', name: '雁山区', level: 'county', center: [110.2900, 25.0600], zoom: 12 }
          ]
        }
      ]
    },
    {
      code: 'YN',
      adcode: '530000',
      name: '云南省',
      level: 'province',
      center: [102.7103, 25.0406],
      zoom: 7,
      children: [
        {
          code: 'YN-KM',
          adcode: '530100',
          name: '昆明市',
          level: 'city',
          center: [102.7103, 25.0406],
          zoom: 10,
          children: [
            { code: 'YN-KM-WH', adcode: '530102', name: '五华区', level: 'county', center: [102.7070, 25.0350], zoom: 12 },
            { code: 'YN-KM-PS', adcode: '530103', name: '盘龙区', level: 'county', center: [102.7520, 25.1160], zoom: 12 },
            { code: 'YN-KM-GS', adcode: '530111', name: '官渡区', level: 'county', center: [102.7490, 25.0150], zoom: 12 },
            { code: 'YN-KM-XS', adcode: '530112', name: '西山区', level: 'county', center: [102.6640, 25.0380], zoom: 12 },
            { code: 'YN-KM-DC', adcode: '530113', name: '东川区', level: 'county', center: [103.1880, 26.0830], zoom: 12 },
            { code: 'YN-KM-CG', adcode: '530114', name: '呈贡区', level: 'county', center: [102.8210, 24.8860], zoom: 12 },
            { code: 'YN-KM-JN', adcode: '530115', name: '晋宁区', level: 'county', center: [102.5950, 24.6690], zoom: 12 }
          ]
        },
        {
          code: 'YN-QJ',
          adcode: '530300',
          name: '曲靖市',
          level: 'city',
          center: [103.7964, 25.4895],
          zoom: 10,
          children: [
            { code: 'YN-QJ-QL', adcode: '530302', name: '麒麟区', level: 'county', center: [103.8050, 25.4950], zoom: 12 },
            { code: 'YN-QJ-MG', adcode: '530303', name: '马龙区', level: 'county', center: [103.5680, 25.4280], zoom: 12 },
            { code: 'YN-QJ-ZF', adcode: '530304', name: '沾益区', level: 'county', center: [103.8220, 25.6010], zoom: 12 }
          ]
        },
        {
          code: 'YN-YX',
          adcode: '530400',
          name: '玉溪市',
          level: 'city',
          center: [102.5467, 24.3527],
          zoom: 10,
          children: [
            { code: 'YN-YX-HT', adcode: '530402', name: '红塔区', level: 'county', center: [102.5450, 24.3540], zoom: 12 },
            { code: 'YN-YX-JC', adcode: '530403', name: '江川区', level: 'county', center: [102.5840, 24.2880], zoom: 12 }
          ]
        }
      ]
    },
    {
      code: 'GZ',
      adcode: '520000',
      name: '贵州省',
      level: 'province',
      center: [106.7135, 26.5783],
      zoom: 7,
      children: [
        {
          code: 'GZ-GY',
          adcode: '520100',
          name: '贵阳市',
          level: 'city',
          center: [106.7135, 26.5783],
          zoom: 10,
          children: [
            { code: 'GZ-GY-NS', adcode: '520102', name: '南明区', level: 'county', center: [106.7140, 26.5680], zoom: 12 },
            { code: 'GZ-GY-YQ', adcode: '520103', name: '云岩区', level: 'county', center: [106.7240, 26.6040], zoom: 12 },
            { code: 'GZ-GY-HS', adcode: '520111', name: '花溪区', level: 'county', center: [106.6700, 26.4100], zoom: 12 },
            { code: 'GZ-GY-WD', adcode: '520112', name: '乌当区', level: 'county', center: [106.7520, 26.6300], zoom: 12 },
            { code: 'GZ-GY-BY', adcode: '520113', name: '白云区', level: 'county', center: [106.6230, 26.6830], zoom: 12 },
            { code: 'GZ-GY-GM', adcode: '520115', name: '观山湖区', level: 'county', center: [106.6220, 26.6010], zoom: 12 }
          ]
        },
        {
          code: 'GZ-ZY',
          adcode: '520300',
          name: '遵义市',
          level: 'city',
          center: [106.9373, 27.7066],
          zoom: 10,
          children: [
            { code: 'GZ-ZY-HC', adcode: '520302', name: '红花岗区', level: 'county', center: [106.8940, 27.6450], zoom: 12 },
            { code: 'GZ-ZY-HZ', adcode: '520303', name: '汇川区', level: 'county', center: [106.9340, 27.7500], zoom: 12 },
            { code: 'GZ-ZY-BS', adcode: '520304', name: '播州区', level: 'county', center: [106.8290, 27.5360], zoom: 12 }
          ]
        },
        {
          code: 'GZ-LPS',
          adcode: '520200',
          name: '六盘水市',
          level: 'city',
          center: [104.8305, 26.5948],
          zoom: 10,
          children: [
            { code: 'GZ-LPS-ZS', adcode: '520201', name: '钟山区', level: 'county', center: [104.8470, 26.5770], zoom: 12 },
            { code: 'GZ-LPS-SD', adcode: '520203', name: '六枝特区', level: 'county', center: [105.4800, 26.0560], zoom: 12 }
          ]
        }
      ]
    },
    {
      code: 'HLJ',
      adcode: '230000',
      name: '黑龙江省',
      level: 'province',
      center: [126.6424, 45.7569],
      zoom: 6,
      children: [
        {
          code: 'HLJ-HB',
          adcode: '230100',
          name: '哈尔滨市',
          level: 'city',
          center: [126.6424, 45.7569],
          zoom: 10,
          children: [
            { code: 'HLJ-HB-DL', adcode: '230102', name: '道里区', level: 'county', center: [126.6170, 45.7550], zoom: 12 },
            { code: 'HLJ-HB-NG', adcode: '230103', name: '南岗区', level: 'county', center: [126.6690, 45.7600], zoom: 12 },
            { code: 'HLJ-HB-DW', adcode: '230104', name: '道外区', level: 'county', center: [126.6490, 45.7920], zoom: 12 },
            { code: 'HLJ-HB-PT', adcode: '230108', name: '平房区', level: 'county', center: [126.6370, 45.5980], zoom: 12 },
            { code: 'HLJ-HB-SB', adcode: '230109', name: '松北区', level: 'county', center: [126.5630, 45.7940], zoom: 12 },
            { code: 'HLJ-HB-XF', adcode: '230110', name: '香坊区', level: 'county', center: [126.6620, 45.7070], zoom: 12 },
            { code: 'HLJ-HB-HL', adcode: '230111', name: '呼兰区', level: 'county', center: [126.5870, 45.8890], zoom: 12 },
            { code: 'HLJ-HB-AC', adcode: '230112', name: '阿城区', level: 'county', center: [126.9580, 45.5480], zoom: 12 },
            { code: 'HLJ-HB-SZ', adcode: '230113', name: '双城区', level: 'county', center: [126.3120, 45.3830], zoom: 12 }
          ]
        },
        {
          code: 'HLJ-QQHE',
          adcode: '230200',
          name: '齐齐哈尔市',
          level: 'city',
          center: [123.9180, 47.3543],
          zoom: 10,
          children: [
            { code: 'HLJ-QQHE-LH', adcode: '230202', name: '龙沙区', level: 'county', center: [123.9580, 47.3170], zoom: 12 },
            { code: 'HLJ-QQHE-JH', adcode: '230203', name: '建华区', level: 'county', center: [123.9550, 47.3540], zoom: 12 },
            { code: 'HLJ-QQHE-TQ', adcode: '230204', name: '铁锋区', level: 'county', center: [123.9780, 47.3400], zoom: 12 },
            { code: 'HLJ-QQHE-AG', adcode: '230205', name: '昂昂溪区', level: 'county', center: [123.8220, 47.1550], zoom: 12 },
            { code: 'HLJ-QQHE-FL', adcode: '230206', name: '富拉尔基区', level: 'county', center: [123.6290, 47.2090], zoom: 12 }
          ]
        },
        {
          code: 'HLJ-JMS',
          adcode: '230800',
          name: '佳木斯市',
          level: 'city',
          center: [130.3616, 46.8096],
          zoom: 10,
          children: [
            { code: 'HLJ-JMS-XQ', adcode: '230803', name: '向阳区', level: 'county', center: [130.3650, 46.8080], zoom: 12 },
            { code: 'HLJ-JMS-QJ', adcode: '230804', name: '前进区', level: 'county', center: [130.3750, 46.8130], zoom: 12 },
            { code: 'HLJ-JMS-DF', adcode: '230805', name: '东风区', level: 'county', center: [130.4040, 46.8230], zoom: 12 },
            { code: 'HLJ-JMS-GB', adcode: '230811', name: '郊区', level: 'county', center: [130.3270, 46.8330], zoom: 12 }
          ]
        }
      ]
    },
    {
      code: 'JL',
      adcode: '220000',
      name: '吉林省',
      level: 'province',
      center: [125.3245, 43.8868],
      zoom: 7,
      children: [
        {
          code: 'JL-CC',
          adcode: '220100',
          name: '长春市',
          level: 'city',
          center: [125.3245, 43.8868],
          zoom: 10,
          children: [
            { code: 'JL-CC-NC', adcode: '220102', name: '南关区', level: 'county', center: [125.3500, 43.8640], zoom: 12 },
            { code: 'JL-CC-KC', adcode: '220103', name: '宽城区', level: 'county', center: [125.3260, 43.9430], zoom: 12 },
            { code: 'JL-CC-CY', adcode: '220104', name: '朝阳区', level: 'county', center: [125.2880, 43.8330], zoom: 12 },
            { code: 'JL-CC-ED', adcode: '220105', name: '二道区', level: 'county', center: [125.3740, 43.8650], zoom: 12 },
            { code: 'JL-CC-LS', adcode: '220106', name: '绿园区', level: 'county', center: [125.2560, 43.8800], zoom: 12 },
            { code: 'JL-CC-SQ', adcode: '220112', name: '双阳区', level: 'county', center: [125.6640, 43.5250], zoom: 12 },
            { code: 'JL-CC-JY', adcode: '220113', name: '九台区', level: 'county', center: [125.8400, 44.1520], zoom: 12 }
          ]
        },
        {
          code: 'JL-JL',
          adcode: '220200',
          name: '吉林市',
          level: 'city',
          center: [126.5530, 43.8436],
          zoom: 10,
          children: [
            { code: 'JL-JL-CY', adcode: '220202', name: '昌邑区', level: 'county', center: [126.5740, 43.8820], zoom: 12 },
            { code: 'JL-JL-LS', adcode: '220203', name: '龙潭区', level: 'county', center: [126.5620, 43.9100], zoom: 12 },
            { code: 'JL-JL-CB', adcode: '220204', name: '船营区', level: 'county', center: [126.5410, 43.8330], zoom: 12 },
            { code: 'JL-JL-FM', adcode: '220211', name: '丰满区', level: 'county', center: [126.5620, 43.7190], zoom: 12 }
          ]
        },
        {
          code: 'JL-SP',
          adcode: '220300',
          name: '四平市',
          level: 'city',
          center: [124.3505, 43.1666],
          zoom: 10,
          children: [
            { code: 'JL-SP-TX', adcode: '220302', name: '铁西区', level: 'county', center: [124.3450, 43.1460], zoom: 12 },
            { code: 'JL-SP-TD', adcode: '220303', name: '铁东区', level: 'county', center: [124.4100, 43.1620], zoom: 12 }
          ]
        }
      ]
    },
    {
      code: 'XZ',
      adcode: '540000',
      name: '西藏自治区',
      level: 'province',
      center: [91.1174, 29.6470],
      zoom: 6,
      children: [
        {
          code: 'XZ-LS',
          adcode: '540100',
          name: '拉萨市',
          level: 'city',
          center: [91.1174, 29.6470],
          zoom: 10,
          children: [
            { code: 'XZ-LS-CG', adcode: '540102', name: '城关区', level: 'county', center: [91.1380, 29.6540], zoom: 12 },
            { code: 'XZ-LS-DZ', adcode: '540103', name: '堆龙德庆区', level: 'county', center: [91.0030, 29.6460], zoom: 12 },
            { code: 'XZ-LS-DQ', adcode: '540104', name: '达孜区', level: 'county', center: [91.3490, 29.6690], zoom: 12 }
          ]
        },
        {
          code: 'XZ-RKZ',
          adcode: '540200',
          name: '日喀则市',
          level: 'city',
          center: [88.8810, 29.2690],
          zoom: 10,
          children: [
            { code: 'XZ-RKZ-SC', adcode: '540202', name: '桑珠孜区', level: 'county', center: [88.8810, 29.2690], zoom: 12 }
          ]
        },
        {
          code: 'XZ-CD',
          adcode: '540300',
          name: '昌都市',
          level: 'city',
          center: [97.1785, 31.1369],
          zoom: 10,
          children: [
            { code: 'XZ-CD-KD', adcode: '540302', name: '卡若区', level: 'county', center: [97.1780, 31.1370], zoom: 12 }
          ]
        }
      ]
    },
    {
      code: 'NX',
      adcode: '640000',
      name: '宁夏回族自治区',
      level: 'province',
      center: [106.2586, 38.4680],
      zoom: 7,
      children: [
        {
          code: 'NX-YC',
          adcode: '640100',
          name: '银川市',
          level: 'city',
          center: [106.2586, 38.4680],
          zoom: 10,
          children: [
            { code: 'NX-YC-XQ', adcode: '640104', name: '兴庆区', level: 'county', center: [106.2880, 38.4740], zoom: 12 },
            { code: 'NX-YC-XX', adcode: '640105', name: '西夏区', level: 'county', center: [106.1300, 38.4920], zoom: 12 },
            { code: 'NX-YC-JF', adcode: '640106', name: '金凤区', level: 'county', center: [106.2420, 38.4730], zoom: 12 }
          ]
        },
        {
          code: 'NX-SZS',
          adcode: '640200',
          name: '石嘴山市',
          level: 'city',
          center: [106.3838, 39.0231],
          zoom: 10,
          children: [
            { code: 'NX-SZS-DC', adcode: '640202', name: '大武口区', level: 'county', center: [106.3680, 39.0190], zoom: 12 },
            { code: 'NX-SZS-HK', adcode: '640205', name: '惠农区', level: 'county', center: [106.7110, 39.2390], zoom: 12 }
          ]
        },
        {
          code: 'NX-WZ',
          adcode: '640300',
          name: '吴忠市',
          level: 'city',
          center: [106.1994, 37.9862],
          zoom: 10,
          children: [
            { code: 'NX-WZ-LB', adcode: '640302', name: '利通区', level: 'county', center: [106.2030, 37.9900], zoom: 12 },
            { code: 'NX-WZ-HT', adcode: '640303', name: '红寺堡区', level: 'county', center: [106.0620, 37.4260], zoom: 12 }
          ]
        }
      ]
    },
    {
      code: 'QH',
      adcode: '630000',
      name: '青海省',
      level: 'province',
      center: [101.7782, 36.6171],
      zoom: 6,
      children: [
        {
          code: 'QH-XN',
          adcode: '630100',
          name: '西宁市',
          level: 'city',
          center: [101.7782, 36.6171],
          zoom: 10,
          children: [
            { code: 'QH-XN-CY', adcode: '630102', name: '城东区', level: 'county', center: [101.8030, 36.5990], zoom: 12 },
            { code: 'QH-XN-CX', adcode: '630103', name: '城中区', level: 'county', center: [101.7840, 36.6230], zoom: 12 },
            { code: 'QH-XN-CW', adcode: '630104', name: '城西区', level: 'county', center: [101.7660, 36.6280], zoom: 12 },
            { code: 'QH-XN-CN', adcode: '630105', name: '城北区', level: 'county', center: [101.7660, 36.6500], zoom: 12 }
          ]
        },
        {
          code: 'QH-HD',
          adcode: '630200',
          name: '海东市',
          level: 'city',
          center: [102.1043, 36.5029],
          zoom: 10,
          children: [
            { code: 'QH-HD-LD', adcode: '630202', name: '乐都区', level: 'county', center: [102.4020, 36.4800], zoom: 12 },
            { code: 'QH-HD-PA', adcode: '630203', name: '平安区', level: 'county', center: [102.1050, 36.5030], zoom: 12 }
          ]
        }
      ]
    },
    {
      code: 'XJ',
      adcode: '650000',
      name: '新疆维吾尔自治区',
      level: 'province',
      center: [87.6177, 43.7928],
      zoom: 5,
      children: [
        {
          code: 'XJ-WS',
          adcode: '650100',
          name: '乌鲁木齐市',
          level: 'city',
          center: [87.6177, 43.7928],
          zoom: 10,
          children: [
            { code: 'XJ-WS-TS', adcode: '650102', name: '天山区', level: 'county', center: [87.6310, 43.7940], zoom: 12 },
            { code: 'XJ-WS-SY', adcode: '650103', name: '沙依巴克区', level: 'county', center: [87.5980, 43.8010], zoom: 12 },
            { code: 'XJ-WS-XG', adcode: '650104', name: '新市区', level: 'county', center: [87.5690, 43.8550], zoom: 12 },
            { code: 'XJ-WS-SB', adcode: '650105', name: '水磨沟区', level: 'county', center: [87.6420, 43.8320], zoom: 12 },
            { code: 'XJ-WS-TB', adcode: '650106', name: '头屯河区', level: 'county', center: [87.4280, 43.8770], zoom: 12 },
            { code: 'XJ-WS-DM', adcode: '650107', name: '达坂城区', level: 'county', center: [88.3110, 43.3630], zoom: 12 },
            { code: 'XJ-WS-MQ', adcode: '650109', name: '米东区', level: 'county', center: [87.6550, 43.9740], zoom: 12 }
          ]
        },
        {
          code: 'XJ-KL',
          adcode: '650200',
          name: '克拉玛依市',
          level: 'city',
          center: [84.8730, 45.5795],
          zoom: 10,
          children: [
            { code: 'XJ-KL-DB', adcode: '650202', name: '独山子区', level: 'county', center: [84.8860, 44.3290], zoom: 12 },
            { code: 'XJ-KL-KM', adcode: '650203', name: '克拉玛依区', level: 'county', center: [84.8490, 45.5790], zoom: 12 },
            { code: 'XJ-KL-BJ', adcode: '650204', name: '白碱滩区', level: 'county', center: [85.1320, 45.6840], zoom: 12 },
            { code: 'XJ-KL-WS', adcode: '650205', name: '乌尔禾区', level: 'county', center: [85.6940, 46.0900], zoom: 12 }
          ]
        },
        {
          code: 'XJ-TLF',
          adcode: '650400',
          name: '吐鲁番市',
          level: 'city',
          center: [89.1841, 42.9476],
          zoom: 10,
          children: [
            { code: 'XJ-TLF-GC', adcode: '650402', name: '高昌区', level: 'county', center: [89.1840, 42.9480], zoom: 12 }
          ]
        }
      ]
    },
    {
      code: 'HAN',
      adcode: '460000',
      name: '海南省',
      level: 'province',
      center: [110.3312, 20.0311],
      zoom: 8,
      children: [
        {
          code: 'HAN-HK',
          adcode: '460100',
          name: '海口市',
          level: 'city',
          center: [110.3312, 20.0311],
          zoom: 10,
          children: [
            { code: 'HAN-HK-XY', adcode: '460105', name: '秀英区', level: 'county', center: [110.2940, 20.0080], zoom: 12 },
            { code: 'HAN-HK-LS', adcode: '460106', name: '龙华区', level: 'county', center: [110.3280, 20.0310], zoom: 12 },
            { code: 'HAN-HK-QS', adcode: '460107', name: '琼山区', level: 'county', center: [110.3540, 20.0030], zoom: 12 },
            { code: 'HAN-HK-ML', adcode: '460108', name: '美兰区', level: 'county', center: [110.3660, 20.0290], zoom: 12 }
          ]
        },
        {
          code: 'HAN-SY',
          adcode: '460200',
          name: '三亚市',
          level: 'city',
          center: [109.5082, 18.2479],
          zoom: 10,
          children: [
            { code: 'HAN-SY-HP', adcode: '460202', name: '海棠区', level: 'county', center: [109.7520, 18.4080], zoom: 12 },
            { code: 'HAN-SY-JY', adcode: '460203', name: '吉阳区', level: 'county', center: [109.5780, 18.2810], zoom: 12 },
            { code: 'HAN-SY-TY', adcode: '460204', name: '天涯区', level: 'county', center: [109.4520, 18.2990], zoom: 12 },
            { code: 'HAN-SY-YZ', adcode: '460205', name: '崖州区', level: 'county', center: [109.1710, 18.3570], zoom: 12 }
          ]
        },
        {
          code: 'HAN-DS',
          adcode: '460400',
          name: '儋州市',
          level: 'city',
          center: [109.5768, 19.5215],
          zoom: 10,
          children: [
            { code: 'HAN-DS-NA', adcode: '460400100', name: '那大镇', level: 'county', center: [109.5770, 19.5220], zoom: 12 }
          ]
        }
      ]
    },
    {
      code: 'GAT',
      adcode: '810000',
      name: '香港特别行政区',
      level: 'province',
      center: [114.1694, 22.3193],
      zoom: 11,
      children: [
        { code: 'GAT-ZH', adcode: '810001', name: '中西区', level: 'county', center: [114.1540, 22.2860], zoom: 13 },
        { code: 'GAT-WC', adcode: '810002', name: '湾仔区', level: 'county', center: [114.1760, 22.2780], zoom: 13 },
        { code: 'GAT-DE', adcode: '810003', name: '东区', level: 'county', center: [114.2260, 22.2790], zoom: 13 },
        { code: 'GAT-SC', adcode: '810004', name: '南区', level: 'county', center: [114.1600, 22.2470], zoom: 13 },
        { code: 'GAT-YT', adcode: '810005', name: '油尖旺区', level: 'county', center: [114.1730, 22.3120], zoom: 13 },
        { code: 'GAT-SS', adcode: '810006', name: '深水埗区', level: 'county', center: [114.1630, 22.3310], zoom: 13 },
        { code: 'GAT-KC', adcode: '810007', name: '九龙城区', level: 'county', center: [114.1950, 22.3120], zoom: 13 },
        { code: 'GAT-WT', adcode: '810008', name: '黄大仙区', level: 'county', center: [114.2030, 22.3360], zoom: 13 },
        { code: 'GAT-KT', adcode: '810009', name: '观塘区', level: 'county', center: [114.2310, 22.3130], zoom: 13 },
        { code: 'GAT-TW', adcode: '810010', name: '荃湾区', level: 'county', center: [114.1220, 22.3690], zoom: 13 },
        { code: 'GAT-TM', adcode: '810011', name: '屯门区', level: 'county', center: [113.9770, 22.3910], zoom: 13 },
        { code: 'GAT-YL', adcode: '810012', name: '元朗区', level: 'county', center: [114.0390, 22.4430], zoom: 13 },
        { code: 'GAT-NC', adcode: '810013', name: '北区', level: 'county', center: [114.1480, 22.4960], zoom: 13 },
        { code: 'GAT-TP', adcode: '810014', name: '大埔区', level: 'county', center: [114.1720, 22.4460], zoom: 13 },
        { code: 'GAT-ST', adcode: '810015', name: '沙田区', level: 'county', center: [114.1950, 22.3870], zoom: 13 },
        { code: 'GAT-SK', adcode: '810016', name: '西贡区', level: 'county', center: [114.2790, 22.3790], zoom: 13 },
        { code: 'GAT-KW', adcode: '810017', name: '离岛区', level: 'county', center: [113.9460, 22.2870], zoom: 13 },
        { code: 'GAT-PW', adcode: '810018', name: '葵青区', level: 'county', center: [114.1320, 22.3640], zoom: 13 }
      ]
    },
    {
      code: 'AM',
      adcode: '820000',
      name: '澳门特别行政区',
      level: 'province',
      center: [113.5439, 22.2006],
      zoom: 13,
      children: [
        { code: 'AM-HD', adcode: '820001', name: '花地玛堂区', level: 'county', center: [113.5510, 22.2080], zoom: 14 },
        { code: 'AM-SA', adcode: '820002', name: '圣安多尼堂区', level: 'county', center: [113.5490, 22.1990], zoom: 14 },
        { code: 'AM-WC', adcode: '820003', name: '望德堂区', level: 'county', center: [113.5500, 22.1940], zoom: 14 },
        { code: 'AM-LL', adcode: '820004', name: '大堂区', level: 'county', center: [113.5530, 22.1890], zoom: 14 },
        { code: 'AM-SL', adcode: '820005', name: '圣老楞佐堂区', level: 'county', center: [113.5480, 22.1870], zoom: 14 },
        { code: 'AM-FH', adcode: '820006', name: '风顺堂区', level: 'county', center: [113.5410, 22.1870], zoom: 14 },
        { code: 'AM-JA', adcode: '820007', name: '嘉模堂区', level: 'county', center: [113.5590, 22.1540], zoom: 14 },
        { code: 'AM-SF', adcode: '820008', name: '圣方济各堂区', level: 'county', center: [113.5800, 22.1290], zoom: 14 }
      ]
    },
    {
      code: 'TW',
      adcode: '710000',
      name: '台湾省',
      level: 'province',
      center: [121.5091, 25.0444],
      zoom: 7,
      children: [
        { code: 'TW-TPE', adcode: '710100', name: '台北市', level: 'city', center: [121.5091, 25.0444], zoom: 11 },
        { code: 'TW-KHH', adcode: '710200', name: '高雄市', level: 'city', center: [120.3119, 22.6270], zoom: 11 },
        { code: 'TW-TXG', adcode: '710300', name: '台中市', level: 'city', center: [120.6790, 24.1380], zoom: 11 },
        { code: 'TW-TNN', adcode: '710400', name: '台南市', level: 'city', center: [120.2270, 23.0000], zoom: 11 },
        { code: 'TW-TPQ', adcode: '710500', name: '新北市', level: 'city', center: [121.4650, 25.0120], zoom: 11 },
        { code: 'TW-TYQ', adcode: '710600', name: '桃园市', level: 'city', center: [121.3010, 24.9940], zoom: 11 }
      ]
    }
  ]
}

export const findRegionByCode = (code: string, region: Region = regionData): Region | null => {
  if (region.code === code) return region
  if (region.children) {
    for (const child of region.children) {
      const found = findRegionByCode(code, child)
      if (found) return found
    }
  }
  return null
}

export const findRegionByAdcode = (adcode: string, region: Region = regionData): Region | null => {
  if (region.adcode === adcode) return region
  if (region.children) {
    for (const child of region.children) {
      const found = findRegionByAdcode(adcode, child)
      if (found) return found
    }
  }
  return null
}

export const getRegionPath = (code: string, region: Region = regionData, path: Region[] = []): Region[] | null => {
  if (region.code === code) {
    return [...path, region]
  }
  if (region.children) {
    for (const child of region.children) {
      const found = getRegionPath(code, child, [...path, region])
      if (found) return found
    }
  }
  return null
}

export const getGeoJsonUrl = (adcode: string): string => {
  return `https://geo.datav.aliyun.com/areas_v3/bound/${adcode}_full.json`
}
