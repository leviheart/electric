package com.electric.config;

import java.util.*;

public class ChinaPowerGridData {
    
    public static class ProvinceData {
        public String name;
        public String shortName;
        public double centerLat;
        public double centerLng;
        public int substationCount;
        public String[] cities;
        
        public ProvinceData(String name, String shortName, double centerLat, double centerLng, int substationCount, String[] cities) {
            this.name = name;
            this.shortName = shortName;
            this.centerLat = centerLat;
            this.centerLng = centerLng;
            this.substationCount = substationCount;
            this.cities = cities;
        }
    }
    
    public static class CityData {
        public String name;
        public double lat;
        public double lng;
        public int substationCount;
        
        public CityData(String name, double lat, double lng, int substationCount) {
            this.name = name;
            this.lat = lat;
            this.lng = lng;
            this.substationCount = substationCount;
        }
    }
    
    public static final Map<String, ProvinceData> PROVINCES = new LinkedHashMap<>();
    public static final Map<String, List<CityData>> CITIES = new LinkedHashMap<>();
    
    static {
        PROVINCES.put("北京", new ProvinceData("北京市", "京", 39.9042, 116.4074, 25, 
            new String[]{"东城", "西城", "朝阳", "海淀", "丰台", "石景山", "通州", "顺义", "昌平", "大兴", "房山", "门头沟", "怀柔", "密云", "平谷", "延庆"}));
        
        PROVINCES.put("上海", new ProvinceData("上海市", "沪", 31.2304, 121.4737, 22,
            new String[]{"黄浦", "徐汇", "长宁", "静安", "普陀", "虹口", "杨浦", "浦东", "闵行", "宝山", "嘉定", "金山", "松江", "青浦", "奉贤", "崇明"}));
        
        PROVINCES.put("广东", new ProvinceData("广东省", "粤", 23.1291, 113.2644, 45,
            new String[]{"广州", "深圳", "珠海", "汕头", "佛山", "韶关", "湛江", "肇庆", "江门", "茂名", "惠州", "梅州", "汕尾", "河源", "阳江", "清远", "东莞", "中山", "潮州", "揭阳", "云浮"}));
        
        PROVINCES.put("江苏", new ProvinceData("江苏省", "苏", 32.0603, 118.7969, 40,
            new String[]{"南京", "无锡", "徐州", "常州", "苏州", "南通", "连云港", "淮安", "盐城", "扬州", "镇江", "泰州", "宿迁"}));
        
        PROVINCES.put("浙江", new ProvinceData("浙江省", "浙", 30.2741, 120.1551, 35,
            new String[]{"杭州", "宁波", "温州", "嘉兴", "湖州", "绍兴", "金华", "衢州", "舟山", "台州", "丽水"}));
        
        PROVINCES.put("山东", new ProvinceData("山东省", "鲁", 36.0671, 120.3826, 38,
            new String[]{"济南", "青岛", "淄博", "枣庄", "东营", "烟台", "潍坊", "济宁", "泰安", "威海", "日照", "临沂", "德州", "聊城", "滨州", "菏泽"}));
        
        PROVINCES.put("福建", new ProvinceData("福建省", "闽", 26.0745, 119.2965, 25,
            new String[]{"福州", "厦门", "莆田", "三明", "泉州", "漳州", "南平", "龙岩", "宁德"}));
        
        PROVINCES.put("天津", new ProvinceData("天津市", "津", 39.0842, 117.2009, 15,
            new String[]{"和平", "河东", "河西", "南开", "河北", "红桥", "东丽", "西青", "津南", "北辰", "武清", "宝坻", "滨海新区", "宁河", "静海", "蓟州"}));
        
        PROVINCES.put("河北", new ProvinceData("河北省", "冀", 38.0428, 114.5149, 30,
            new String[]{"石家庄", "唐山", "秦皇岛", "邯郸", "邢台", "保定", "张家口", "承德", "沧州", "廊坊", "衡水"}));
        
        PROVINCES.put("辽宁", new ProvinceData("辽宁省", "辽", 41.8057, 123.4315, 25,
            new String[]{"沈阳", "大连", "鞍山", "抚顺", "本溪", "丹东", "锦州", "营口", "阜新", "辽阳", "盘锦", "铁岭", "朝阳", "葫芦岛"}));
        
        PROVINCES.put("河南", new ProvinceData("河南省", "豫", 34.7466, 113.6254, 32,
            new String[]{"郑州", "开封", "洛阳", "平顶山", "安阳", "鹤壁", "新乡", "焦作", "濮阳", "许昌", "漯河", "三门峡", "南阳", "商丘", "信阳", "周口", "驻马店"}));
        
        PROVINCES.put("湖北", new ProvinceData("湖北省", "鄂", 30.5928, 114.3055, 28,
            new String[]{"武汉", "黄石", "十堰", "宜昌", "襄阳", "鄂州", "荆门", "孝感", "荆州", "黄冈", "咸宁", "随州", "恩施"}));
        
        PROVINCES.put("湖南", new ProvinceData("湖南省", "湘", 28.2282, 112.9388, 26,
            new String[]{"长沙", "株洲", "湘潭", "衡阳", "邵阳", "岳阳", "常德", "张家界", "益阳", "郴州", "永州", "怀化", "娄底", "湘西"}));
        
        PROVINCES.put("安徽", new ProvinceData("安徽省", "皖", 31.8612, 117.2478, 28,
            new String[]{"合肥", "芜湖", "蚌埠", "淮南", "马鞍山", "淮北", "铜陵", "安庆", "黄山", "滁州", "阜阳", "宿州", "六安", "亳州", "池州", "宣城"}));
        
        PROVINCES.put("江西", new ProvinceData("江西省", "赣", 28.6760, 115.8922, 22,
            new String[]{"南昌", "景德镇", "萍乡", "九江", "新余", "鹰潭", "赣州", "吉安", "宜春", "抚州", "上饶"}));
        
        PROVINCES.put("四川", new ProvinceData("四川省", "川", 30.5723, 104.0665, 30,
            new String[]{"成都", "自贡", "攀枝花", "泸州", "德阳", "绵阳", "广元", "遂宁", "内江", "乐山", "南充", "眉山", "宜宾", "广安", "达州", "雅安", "巴中", "资阳", "阿坝", "甘孜", "凉山"}));
        
        PROVINCES.put("重庆", new ProvinceData("重庆市", "渝", 29.5630, 106.5516, 18,
            new String[]{"渝中", "大渡口", "江北", "沙坪坝", "九龙坡", "南岸", "北碚", "渝北", "巴南", "万州", "涪陵", "长寿", "江津", "合川", "永川", "南川", "綦江", "大足"}));
        
        PROVINCES.put("陕西", new ProvinceData("陕西省", "陕", 34.3416, 108.9398, 20,
            new String[]{"西安", "铜川", "宝鸡", "咸阳", "渭南", "延安", "汉中", "榆林", "安康", "商洛"}));
        
        CITIES.put("北京", Arrays.asList(
            new CityData("东城", 39.9289, 116.4165, 2),
            new CityData("西城", 39.9127, 116.3660, 2),
            new CityData("朝阳", 39.9211, 116.4868, 3),
            new CityData("海淀", 39.9561, 116.3103, 3),
            new CityData("丰台", 39.8584, 116.2863, 2),
            new CityData("石景山", 39.9063, 116.1954, 1),
            new CityData("通州", 39.9025, 116.6586, 2),
            new CityData("顺义", 40.1302, 116.6545, 2),
            new CityData("昌平", 40.2206, 116.2318, 2),
            new CityData("大兴", 39.7268, 116.3416, 2),
            new CityData("房山", 39.7489, 115.9938, 2),
            new CityData("门头沟", 39.9408, 116.1020, 1),
            new CityData("怀柔", 40.3163, 116.6420, 1),
            new CityData("密云", 40.3769, 116.8432, 1),
            new CityData("平谷", 40.1446, 117.1115, 1),
            new CityData("延庆", 40.4654, 115.9750, 1)
        ));
        
        CITIES.put("上海", Arrays.asList(
            new CityData("黄浦", 31.2304, 121.4840, 2),
            new CityData("徐汇", 31.1880, 121.4368, 2),
            new CityData("长宁", 31.2204, 121.4245, 2),
            new CityData("静安", 31.2282, 121.4589, 2),
            new CityData("普陀", 31.2498, 121.3955, 2),
            new CityData("虹口", 31.2648, 121.5050, 1),
            new CityData("杨浦", 31.2599, 121.5265, 2),
            new CityData("浦东", 31.2453, 121.5676, 4),
            new CityData("闵行", 31.1128, 121.3810, 2),
            new CityData("宝山", 31.4045, 121.4896, 2),
            new CityData("嘉定", 31.3746, 121.2655, 2),
            new CityData("金山", 30.7418, 121.3422, 1),
            new CityData("松江", 31.0322, 121.2277, 2),
            new CityData("青浦", 31.1501, 121.1243, 1),
            new CityData("奉贤", 30.9179, 121.4737, 1),
            new CityData("崇明", 31.6231, 121.3974, 1)
        ));
        
        CITIES.put("广东", Arrays.asList(
            new CityData("广州", 23.1291, 113.2644, 6),
            new CityData("深圳", 22.5431, 114.0579, 5),
            new CityData("珠海", 22.2710, 113.5767, 2),
            new CityData("汕头", 23.3540, 116.6819, 2),
            new CityData("佛山", 23.0218, 113.1219, 3),
            new CityData("韶关", 24.8101, 113.5972, 2),
            new CityData("湛江", 21.2707, 110.3594, 2),
            new CityData("肇庆", 23.0472, 112.4651, 2),
            new CityData("江门", 22.5787, 113.0949, 2),
            new CityData("茂名", 21.6633, 110.9253, 2),
            new CityData("惠州", 23.1115, 114.4152, 2),
            new CityData("梅州", 24.2889, 116.1176, 2),
            new CityData("汕尾", 22.7864, 115.3647, 1),
            new CityData("河源", 23.7435, 114.7006, 1),
            new CityData("阳江", 21.8577, 111.9827, 1),
            new CityData("清远", 23.6817, 113.0560, 2),
            new CityData("东莞", 23.0207, 113.7518, 3),
            new CityData("中山", 22.5170, 113.3927, 2),
            new CityData("潮州", 23.6567, 116.6228, 1),
            new CityData("揭阳", 23.5438, 116.3728, 1),
            new CityData("云浮", 22.9151, 112.0440, 1)
        ));
        
        CITIES.put("江苏", Arrays.asList(
            new CityData("南京", 32.0603, 118.7969, 5),
            new CityData("无锡", 31.4912, 120.3119, 4),
            new CityData("徐州", 34.2044, 117.2848, 3),
            new CityData("常州", 31.8122, 119.9692, 3),
            new CityData("苏州", 31.2989, 120.5853, 5),
            new CityData("南通", 31.9802, 120.8943, 3),
            new CityData("连云港", 34.5969, 119.2216, 2),
            new CityData("淮安", 33.5597, 119.1410, 2),
            new CityData("盐城", 33.3798, 120.1394, 3),
            new CityData("扬州", 32.3912, 119.4128, 2),
            new CityData("镇江", 32.1875, 119.4250, 2),
            new CityData("泰州", 32.4558, 119.9231, 2),
            new CityData("宿迁", 33.9630, 118.2755, 2)
        ));
        
        CITIES.put("浙江", Arrays.asList(
            new CityData("杭州", 30.2741, 120.1551, 5),
            new CityData("宁波", 29.8683, 121.5440, 4),
            new CityData("温州", 28.0006, 120.6994, 3),
            new CityData("嘉兴", 30.7468, 120.7507, 3),
            new CityData("湖州", 30.8673, 120.0868, 2),
            new CityData("绍兴", 30.0326, 120.5820, 3),
            new CityData("金华", 29.0789, 119.6478, 2),
            new CityData("衢州", 28.9700, 118.8590, 2),
            new CityData("舟山", 29.9853, 122.2071, 2),
            new CityData("台州", 28.6564, 121.4208, 3),
            new CityData("丽水", 28.4676, 119.9229, 2)
        ));
        
        CITIES.put("山东", Arrays.asList(
            new CityData("济南", 36.0671, 117.1205, 4),
            new CityData("青岛", 36.0671, 120.3826, 4),
            new CityData("淄博", 36.8131, 118.0549, 2),
            new CityData("枣庄", 34.8107, 117.3237, 2),
            new CityData("东营", 37.4341, 118.6748, 2),
            new CityData("烟台", 37.4638, 121.4478, 3),
            new CityData("潍坊", 36.7067, 119.1619, 3),
            new CityData("济宁", 35.4154, 116.5874, 2),
            new CityData("泰安", 36.2003, 117.0874, 2),
            new CityData("威海", 37.5131, 122.1202, 2),
            new CityData("日照", 35.4164, 119.5269, 2),
            new CityData("临沂", 35.1041, 118.3564, 3),
            new CityData("德州", 37.4356, 116.3594, 2),
            new CityData("聊城", 36.4570, 115.9859, 2),
            new CityData("滨州", 37.3815, 117.9708, 2),
            new CityData("菏泽", 35.2333, 115.4807, 2)
        ));
        
        CITIES.put("福建", Arrays.asList(
            new CityData("福州", 26.0745, 119.2965, 4),
            new CityData("厦门", 24.4798, 118.0894, 3),
            new CityData("莆田", 25.4541, 119.0078, 2),
            new CityData("三明", 26.2634, 117.6389, 2),
            new CityData("泉州", 24.8741, 118.6758, 3),
            new CityData("漳州", 24.5109, 117.6472, 2),
            new CityData("南平", 26.6356, 118.1771, 2),
            new CityData("龙岩", 25.0786, 117.0173, 2),
            new CityData("宁德", 26.6590, 119.5477, 2)
        ));
        
        CITIES.put("天津", Arrays.asList(
            new CityData("和平", 39.1173, 117.2147, 2),
            new CityData("河东", 39.1286, 117.2571, 1),
            new CityData("河西", 39.1033, 117.2233, 1),
            new CityData("南开", 39.1381, 117.1502, 1),
            new CityData("河北", 39.1467, 117.2015, 1),
            new CityData("红桥", 39.1667, 117.1506, 1),
            new CityData("东丽", 39.0866, 117.3134, 2),
            new CityData("西青", 39.1413, 117.0088, 2),
            new CityData("津南", 38.9389, 117.3572, 1),
            new CityData("北辰", 39.2241, 117.1354, 1),
            new CityData("武清", 39.3841, 117.0444, 2),
            new CityData("宝坻", 39.7176, 117.3097, 1),
            new CityData("滨海新区", 39.0065, 117.7017, 3),
            new CityData("宁河", 39.3305, 117.8263, 1),
            new CityData("静海", 38.9475, 116.9742, 1),
            new CityData("蓟州", 40.0457, 117.4080, 1)
        ));
        
        CITIES.put("河北", Arrays.asList(
            new CityData("石家庄", 38.0428, 114.5149, 4),
            new CityData("唐山", 39.6305, 118.1748, 3),
            new CityData("秦皇岛", 39.9354, 119.6004, 2),
            new CityData("邯郸", 36.6116, 114.4894, 2),
            new CityData("邢台", 37.0682, 114.5046, 2),
            new CityData("保定", 38.8739, 115.4648, 3),
            new CityData("张家口", 40.8108, 114.8794, 2),
            new CityData("承德", 40.9514, 117.9634, 2),
            new CityData("沧州", 38.3037, 116.8388, 2),
            new CityData("廊坊", 39.5216, 116.6838, 3),
            new CityData("衡水", 37.7389, 115.6701, 2)
        ));
        
        CITIES.put("辽宁", Arrays.asList(
            new CityData("沈阳", 41.8057, 123.4315, 4),
            new CityData("大连", 38.9140, 121.6147, 3),
            new CityData("鞍山", 41.1089, 122.9946, 2),
            new CityData("抚顺", 41.8773, 123.9573, 2),
            new CityData("本溪", 41.2978, 123.7651, 2),
            new CityData("丹东", 40.1290, 124.3542, 2),
            new CityData("锦州", 41.0956, 121.1270, 2),
            new CityData("营口", 40.6676, 122.2352, 2),
            new CityData("阜新", 42.0118, 121.6708, 1),
            new CityData("辽阳", 41.2694, 123.2373, 1),
            new CityData("盘锦", 41.1190, 122.0481, 1),
            new CityData("铁岭", 42.2906, 123.8446, 1),
            new CityData("朝阳", 41.5763, 120.4507, 1),
            new CityData("葫芦岛", 40.7430, 120.8369, 1)
        ));
        
        CITIES.put("河南", Arrays.asList(
            new CityData("郑州", 34.7466, 113.6254, 5),
            new CityData("开封", 34.7971, 114.3074, 2),
            new CityData("洛阳", 34.6181, 112.4540, 3),
            new CityData("平顶山", 33.7662, 113.1927, 2),
            new CityData("安阳", 36.0998, 114.3931, 2),
            new CityData("鹤壁", 35.7480, 114.2973, 1),
            new CityData("新乡", 35.3026, 113.9268, 2),
            new CityData("焦作", 35.2159, 113.2118, 2),
            new CityData("濮阳", 35.7617, 115.0292, 2),
            new CityData("许昌", 34.0357, 113.8526, 2),
            new CityData("漯河", 33.5817, 114.0162, 1),
            new CityData("三门峡", 34.7733, 111.2002, 1),
            new CityData("南阳", 33.0039, 112.5283, 2),
            new CityData("商丘", 34.4142, 115.6564, 2),
            new CityData("信阳", 32.1470, 114.0913, 2),
            new CityData("周口", 33.6259, 114.6965, 2),
            new CityData("驻马店", 32.9803, 114.0223, 2)
        ));
        
        CITIES.put("湖北", Arrays.asList(
            new CityData("武汉", 30.5928, 114.3055, 5),
            new CityData("黄石", 30.2000, 115.0387, 2),
            new CityData("十堰", 32.6292, 110.7980, 2),
            new CityData("宜昌", 30.6920, 111.2865, 2),
            new CityData("襄阳", 32.0091, 112.1226, 3),
            new CityData("鄂州", 30.3915, 114.8949, 1),
            new CityData("荆门", 31.0354, 112.1991, 2),
            new CityData("孝感", 30.9269, 113.9169, 2),
            new CityData("荆州", 30.3352, 112.2410, 2),
            new CityData("黄冈", 30.4539, 114.8722, 2),
            new CityData("咸宁", 29.8417, 114.3222, 1),
            new CityData("随州", 31.6904, 113.3798, 1),
            new CityData("恩施", 30.2722, 109.4868, 2)
        ));
        
        CITIES.put("湖南", Arrays.asList(
            new CityData("长沙", 28.2282, 112.9388, 4),
            new CityData("株洲", 27.8274, 113.1342, 2),
            new CityData("湘潭", 27.8504, 112.9070, 2),
            new CityData("衡阳", 26.8968, 112.5718, 2),
            new CityData("邵阳", 27.2390, 111.4677, 2),
            new CityData("岳阳", 29.3570, 113.0965, 2),
            new CityData("常德", 29.0398, 111.6980, 2),
            new CityData("张家界", 29.1167, 110.4792, 1),
            new CityData("益阳", 28.5539, 112.3550, 2),
            new CityData("郴州", 25.7709, 113.0143, 2),
            new CityData("永州", 26.4206, 111.5927, 1),
            new CityData("怀化", 27.5698, 109.9789, 1),
            new CityData("娄底", 27.6983, 111.9937, 1),
            new CityData("湘西", 28.3115, 109.7388, 1)
        ));
        
        CITIES.put("安徽", Arrays.asList(
            new CityData("合肥", 31.8206, 117.2272, 4),
            new CityData("芜湖", 31.3526, 118.3761, 2),
            new CityData("蚌埠", 32.9397, 117.3889, 2),
            new CityData("淮南", 32.6475, 116.9999, 2),
            new CityData("马鞍山", 31.6707, 118.5069, 2),
            new CityData("淮北", 33.9717, 116.7947, 1),
            new CityData("铜陵", 30.9455, 117.8121, 1),
            new CityData("安庆", 30.5433, 117.0631, 2),
            new CityData("黄山", 29.7151, 118.3376, 1),
            new CityData("滁州", 32.3173, 118.3162, 2),
            new CityData("阜阳", 32.8899, 115.8145, 2),
            new CityData("宿州", 33.6461, 116.9642, 2),
            new CityData("六安", 31.7529, 116.5078, 2),
            new CityData("亳州", 33.8712, 115.7783, 1),
            new CityData("池州", 30.6666, 117.4892, 1),
            new CityData("宣城", 30.9406, 118.7588, 1)
        ));
        
        CITIES.put("江西", Arrays.asList(
            new CityData("南昌", 28.6760, 115.8922, 4),
            new CityData("景德镇", 29.2687, 117.1784, 1),
            new CityData("萍乡", 27.6229, 113.8520, 1),
            new CityData("九江", 29.7051, 116.0004, 2),
            new CityData("新余", 27.8176, 114.9168, 1),
            new CityData("鹰潭", 28.2386, 117.0692, 1),
            new CityData("赣州", 25.8317, 114.9353, 3),
            new CityData("吉安", 27.1139, 114.9926, 2),
            new CityData("宜春", 27.8154, 114.4166, 2),
            new CityData("抚州", 27.9484, 116.3583, 2),
            new CityData("上饶", 28.4544, 117.9434, 2)
        ));
        
        CITIES.put("四川", Arrays.asList(
            new CityData("成都", 30.5723, 104.0665, 5),
            new CityData("自贡", 29.3398, 104.7785, 1),
            new CityData("攀枝花", 26.5804, 101.7183, 1),
            new CityData("泸州", 28.8719, 105.4429, 2),
            new CityData("德阳", 31.1270, 104.3979, 2),
            new CityData("绵阳", 31.4640, 104.6736, 2),
            new CityData("广元", 32.4353, 105.8635, 1),
            new CityData("遂宁", 30.5328, 105.5713, 1),
            new CityData("内江", 29.5804, 105.0584, 1),
            new CityData("乐山", 29.5521, 103.7656, 2),
            new CityData("南充", 30.8378, 106.1106, 2),
            new CityData("眉山", 30.0757, 103.8485, 1),
            new CityData("宜宾", 28.7519, 104.6308, 2),
            new CityData("广安", 30.4564, 106.6333, 1),
            new CityData("达州", 31.2090, 107.4680, 2),
            new CityData("雅安", 30.0103, 103.0011, 1),
            new CityData("巴中", 31.8692, 106.7475, 1),
            new CityData("资阳", 30.1282, 104.6419, 1),
            new CityData("阿坝", 32.9011, 101.7020, 1),
            new CityData("甘孜", 30.0498, 101.9638, 1),
            new CityData("凉山", 27.8868, 102.2587, 1)
        ));
        
        CITIES.put("重庆", Arrays.asList(
            new CityData("渝中", 29.5530, 106.5690, 2),
            new CityData("大渡口", 29.4835, 106.4821, 1),
            new CityData("江北", 29.5767, 106.5328, 2),
            new CityData("沙坪坝", 29.5409, 106.4542, 2),
            new CityData("九龙坡", 29.5024, 106.5109, 2),
            new CityData("南岸", 29.5215, 106.5614, 1),
            new CityData("北碚", 29.8051, 106.3956, 1),
            new CityData("渝北", 29.7184, 106.6313, 2),
            new CityData("巴南", 29.4030, 106.5404, 1),
            new CityData("万州", 30.8078, 108.4089, 2),
            new CityData("涪陵", 29.7030, 107.3949, 1),
            new CityData("长寿", 29.8337, 107.0814, 1),
            new CityData("江津", 29.2903, 106.2592, 1),
            new CityData("合川", 29.9720, 106.2756, 1),
            new CityData("永川", 29.3563, 105.9273, 1),
            new CityData("南川", 29.1575, 107.0994, 1),
            new CityData("綦江", 29.0281, 106.6515, 1),
            new CityData("大足", 29.7006, 105.7211, 1)
        ));
        
        CITIES.put("陕西", Arrays.asList(
            new CityData("西安", 34.3416, 108.9398, 4),
            new CityData("铜川", 34.8969, 108.9450, 1),
            new CityData("宝鸡", 34.3610, 107.1372, 2),
            new CityData("咸阳", 34.3456, 108.7093, 2),
            new CityData("渭南", 34.5000, 109.4630, 2),
            new CityData("延安", 36.5853, 109.4897, 2),
            new CityData("汉中", 33.0677, 107.0231, 2),
            new CityData("榆林", 38.2655, 109.7341, 2),
            new CityData("安康", 32.6850, 109.0293, 1),
            new CityData("商洛", 33.8683, 109.9404, 1)
        ));
    }
    
    public static final String[] VOLTAGE_LEVELS = {"1000kV", "500kV", "220kV", "110kV", "35kV"};
    public static final String[] STATUSES = {"运行中", "备用", "检修中"};
    public static final String[] LINE_TYPES = {"overhead", "cable"};
    public static final String[] AREA_STATUSES = {"正常", "预警", "过载", "故障", "检修"};
}
