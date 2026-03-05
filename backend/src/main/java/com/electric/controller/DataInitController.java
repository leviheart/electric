package com.electric.controller;

import com.electric.model.*;
import com.electric.repository.*;
import com.electric.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/data-init")
@CrossOrigin(origins = "*")
public class DataInitController {
    
    @Autowired private SubstationRepository substationRepository;
    @Autowired private TransmissionLineRepository lineRepository;
    @Autowired private AreaRepository areaRepository;
    @Autowired private MaintenancePlanService maintenancePlanService;
    @Autowired private WorkTicketService workTicketService;
    @Autowired private OperationTicketService operationTicketService;
    @Autowired private InspectionTaskService inspectionTaskService;
    @Autowired private DefectService defectService;
    @Autowired private OperationLogService operationLogService;
    @Autowired private DispatchOrderService dispatchOrderService;
    @Autowired private PowerOutageService powerOutageService;
    @Autowired private LineLossService lineLossService;
    @Autowired private LoadForecastService loadForecastService;
    @Autowired private PowerFlowService powerFlowService;
    @Autowired private N1CheckService n1CheckService;
    @Autowired private DeviceHealthService deviceHealthService;
    @Autowired private ReliabilityIndexService reliabilityIndexService;
    @Autowired private DocumentService documentService;
    @Autowired private VideoMonitorService videoMonitorService;
    
    private final Random random = new Random();
    
    private double round2(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
    
    @PostMapping("/generate-all")
    public Map<String, Object> generateAllData() {
        Map<String, Object> result = new HashMap<>();
        
        result.put("maintenancePlans", generateMaintenancePlans());
        result.put("workTickets", generateWorkTickets());
        result.put("operationTickets", generateOperationTickets());
        result.put("inspectionTasks", generateInspectionTasks());
        result.put("defects", generateDefects());
        result.put("operationLogs", generateOperationLogs());
        result.put("dispatchOrders", generateDispatchOrders());
        result.put("powerOutages", generatePowerOutages());
        result.put("lineLosses", generateLineLosses());
        result.put("loadForecasts", generateLoadForecasts());
        result.put("powerFlows", generatePowerFlows());
        result.put("n1Checks", generateN1Checks());
        result.put("deviceHealths", generateDeviceHealths());
        result.put("reliabilityIndices", generateReliabilityIndices());
        result.put("documents", generateDocuments());
        result.put("videoMonitors", generateVideoMonitors());
        
        return result;
    }
    
    @PostMapping("/generate-maintenance-plans")
    public List<MaintenancePlan> generateMaintenancePlans() {
        List<MaintenancePlan> plans = new ArrayList<>();
        String[] types = {"定期检修", "故障检修", "预防性检修", "专项检修"};
        String[] priorities = {"高", "中", "低"};
        String[] statuses = {"待审批", "已批准", "执行中", "已完成"};
        String[] contents = {"变压器检修", "开关柜检修", "线路检修", "二次设备检修", "配电装置检修"};
        
        List<Substation> substations = substationRepository.findAll();
        
        for (int i = 0; i < 20; i++) {
            MaintenancePlan plan = new MaintenancePlan();
            plan.setPlanCode("MP" + String.format("%05d", i + 1));
            plan.setTitle(contents[i % contents.length] + "-" + (i + 1));
            plan.setDeviceType("变电站");
            plan.setDeviceId(substations.isEmpty() ? 1L : substations.get(i % substations.size()).getId());
            plan.setDeviceName(substations.isEmpty() ? "测试变电站" : substations.get(i % substations.size()).getName());
            plan.setMaintenanceType(types[i % types.length]);
            plan.setPriority(priorities[i % priorities.length]);
            plan.setStatus(statuses[i % statuses.length]);
            plan.setPlanStartTime(LocalDateTime.now().plusDays(i));
            plan.setPlanEndTime(LocalDateTime.now().plusDays(i).plusHours(8));
            plan.setContent(contents[i % contents.length] + "具体内容");
            plan.setReason("设备运行周期到期");
            plan.setEstimatedCost(round2(5000.0 + random.nextDouble() * 10000));
            plan.setActualCost(round2(4500.0 + random.nextDouble() * 9000));
            plan.setApplicant("张工");
            plan.setApplyTime(LocalDateTime.now().minusDays(10 - i % 10));
            plan.setExecutor("李工");
            plan.setExecutorTeam("检修一班");
            plan.setSafetyMeasures("停电、验电、挂接地线");
            plans.add(maintenancePlanService.save(plan));
        }
        return plans;
    }
    
    @PostMapping("/generate-work-tickets")
    public List<WorkTicket> generateWorkTickets() {
        List<WorkTicket> tickets = new ArrayList<>();
        String[] types = {"第一种工作票", "第二种工作票", "带电作业工作票"};
        String[] statuses = {"待审批", "已签发", "已接收", "许可中", "已终结"};
        String[] contents = {"变压器检修", "开关更换", "线路巡视", "电缆敷设", "设备调试"};
        
        List<Substation> substations = substationRepository.findAll();
        
        for (int i = 0; i < 15; i++) {
            WorkTicket ticket = new WorkTicket();
            ticket.setTicketCode("WT" + String.format("%05d", i + 1));
            ticket.setTicketType(types[i % types.length]);
            ticket.setTitle(contents[i % contents.length] + "工作票-" + (i + 1));
            ticket.setDeviceType("变电站");
            ticket.setDeviceId(substations.isEmpty() ? 1L : substations.get(i % substations.size()).getId());
            ticket.setDeviceName(substations.isEmpty() ? "测试变电站" : substations.get(i % substations.size()).getName());
            ticket.setWorkLocation("变电站内");
            ticket.setWorkContent(contents[i % contents.length]);
            ticket.setPlanStartTime(LocalDateTime.now().plusDays(i % 10));
            ticket.setPlanEndTime(LocalDateTime.now().plusDays(i % 10).plusHours(6));
            ticket.setStatus(statuses[i % statuses.length]);
            ticket.setPriority(i % 3 == 0 ? "高" : "中");
            ticket.setApplicant("王工");
            ticket.setWorkLeader("赵工");
            ticket.setWorkTeam("检修二班");
            ticket.setWorkerCount(3 + random.nextInt(5));
            ticket.setSafetyMeasures("停电、验电、挂接地线、设置遮拦");
            ticket.setDangerPoints("高压触电、高空坠落");
            ticket.setPowerOffScope("全站停电");
            tickets.add(workTicketService.save(ticket));
        }
        return tickets;
    }
    
    @PostMapping("/generate-operation-tickets")
    public List<OperationTicket> generateOperationTickets() {
        List<OperationTicket> tickets = new ArrayList<>();
        String[] types = {"倒闸操作票", "检修操作票"};
        String[] statuses = {"待审批", "已签发", "执行中", "已完成"};
        String[] tasks = {"倒闸操作", "设备投运", "设备停运", "负荷切换"};
        
        for (int i = 0; i < 12; i++) {
            OperationTicket ticket = new OperationTicket();
            ticket.setTicketCode("OT" + String.format("%05d", i + 1));
            ticket.setTicketType(types[i % types.length]);
            ticket.setTitle(tasks[i % tasks.length] + "-" + (i + 1));
            ticket.setOperationTask(tasks[i % tasks.length]);
            ticket.setOperationItems("[\"检查设备状态\",\"拉开开关\",\"合上接地刀闸\",\"设置标示牌\"]");
            ticket.setTotalSteps(4);
            ticket.setCompletedSteps(i % 4);
            ticket.setStatus(statuses[i % statuses.length]);
            ticket.setPlanTime(LocalDateTime.now().plusHours(i));
            ticket.setApplicant("刘工");
            ticket.setOperator("陈工");
            ticket.setGuardian("周工");
            tickets.add(operationTicketService.save(ticket));
        }
        return tickets;
    }
    
    @PostMapping("/generate-inspection-tasks")
    public List<InspectionTask> generateInspectionTasks() {
        List<InspectionTask> tasks = new ArrayList<>();
        String[] types = {"日常巡检", "特殊巡检", "夜间巡检", "故障巡检"};
        String[] statuses = {"待执行", "执行中", "已完成"};
        
        List<Substation> substations = substationRepository.findAll();
        
        for (int i = 0; i < 18; i++) {
            InspectionTask task = new InspectionTask();
            task.setTaskCode("IT" + String.format("%05d", i + 1));
            task.setTitle(types[i % types.length] + "-" + (i + 1));
            task.setInspectionType(types[i % types.length]);
            task.setDeviceType("变电站");
            task.setDeviceId(substations.isEmpty() ? 1L : substations.get(i % substations.size()).getId());
            task.setDeviceName(substations.isEmpty() ? "测试变电站" : substations.get(i % substations.size()).getName());
            task.setStatus(statuses[i % statuses.length]);
            task.setPlanStartTime(LocalDateTime.now().plusHours(i));
            task.setPlanEndTime(LocalDateTime.now().plusHours(i + 2));
            task.setInspector("巡检员" + (i % 5 + 1));
            task.setInspectorTeam("巡检班");
            task.setTotalItems(10);
            task.setNormalItems(8 + random.nextInt(3));
            task.setAbnormalItems(10 - task.getNormalItems());
            task.setWeather("晴");
            task.setTemperature("25");
            tasks.add(inspectionTaskService.save(task));
        }
        return tasks;
    }
    
    @PostMapping("/generate-defects")
    public List<Defect> generateDefects() {
        List<Defect> defects = new ArrayList<>();
        String[] types = {"设备缺陷", "线路缺陷", "安全隐患"};
        String[] levels = {"紧急", "重大", "一般", "轻微"};
        String[] statuses = {"待处理", "处理中", "待验证", "已闭环"};
        String[] descriptions = {"设备异响", "温度异常", "绝缘老化", "接头过热", "油位异常"};
        
        List<Substation> substations = substationRepository.findAll();
        
        for (int i = 0; i < 25; i++) {
            Defect defect = new Defect();
            defect.setDefectCode("DF" + String.format("%05d", i + 1));
            defect.setTitle(descriptions[i % descriptions.length] + "-" + (i + 1));
            defect.setDeviceType("变电站");
            defect.setDeviceId(substations.isEmpty() ? 1L : substations.get(i % substations.size()).getId());
            defect.setDeviceName(substations.isEmpty() ? "测试变电站" : substations.get(i % substations.size()).getName());
            defect.setDefectType(types[i % types.length]);
            defect.setDefectLevel(levels[i % levels.length]);
            defect.setStatus(statuses[i % statuses.length]);
            defect.setDiscoverySource("巡检发现");
            defect.setDiscoveryTime(LocalDateTime.now().minusDays(i % 10));
            defect.setDiscoverer("巡检员" + (i % 5 + 1));
            defect.setDescription(descriptions[i % descriptions.length] + "，需要及时处理");
            defect.setSolution("更换设备/维修处理");
            defects.add(defectService.save(defect));
        }
        return defects;
    }
    
    @PostMapping("/generate-operation-logs")
    public List<OperationLog> generateOperationLogs() {
        List<OperationLog> logs = new ArrayList<>();
        String[] modules = {"设备管理", "告警管理", "用户管理", "系统设置", "数据查询"};
        String[] operations = {"新增", "修改", "删除", "查询", "登录", "导出"};
        String[] methods = {"POST", "PUT", "DELETE", "GET"};
        
        for (int i = 0; i < 50; i++) {
            OperationLog log = new OperationLog();
            log.setUsername("user" + (i % 5 + 1));
            log.setUserId(String.valueOf(i % 5 + 1));
            log.setModule(modules[i % modules.length]);
            log.setOperation(operations[i % operations.length]);
            log.setMethod(methods[i % methods.length]);
            log.setStatus(random.nextBoolean() ? 1 : 0);
            log.setIp("192.168.1." + (i % 255));
            log.setDuration((long) (10 + random.nextInt(100)));
            log.setOperationTime(LocalDateTime.now().minusHours(i));
            logs.add(operationLogService.save(log));
        }
        return logs;
    }
    
    @PostMapping("/generate-dispatch-orders")
    public List<DispatchOrder> generateDispatchOrders() {
        List<DispatchOrder> orders = new ArrayList<>();
        String[] types = {"调度指令", "操作指令", "应急指令"};
        String[] statuses = {"待接收", "已接收", "执行中", "已完成"};
        String[] contents = {"负荷调整", "设备投退", "故障处理", "方式变更"};
        
        for (int i = 0; i < 15; i++) {
            DispatchOrder order = new DispatchOrder();
            order.setOrderCode("DO" + String.format("%05d", i + 1));
            order.setTitle(contents[i % contents.length] + "-" + (i + 1));
            order.setOrderType(types[i % types.length]);
            order.setContent(contents[i % contents.length] + "具体内容");
            order.setStatus(statuses[i % statuses.length]);
            order.setPriority(i % 3 == 0 ? "高" : "中");
            order.setPlanTime(LocalDateTime.now().plusHours(i));
            order.setIssuer("调度员" + (i % 3 + 1));
            order.setIssuerDept("调度中心");
            order.setIssueTime(LocalDateTime.now().minusHours(i));
            orders.add(dispatchOrderService.save(order));
        }
        return orders;
    }
    
    @PostMapping("/generate-power-outages")
    public List<PowerOutage> generatePowerOutages() {
        List<PowerOutage> outages = new ArrayList<>();
        String[] types = {"计划停电", "故障停电", "临时停电"};
        String[] statuses = {"待审批", "已批准", "执行中", "已完成"};
        String[] reasons = {"设备检修", "线路改造", "故障处理", "配合施工"};
        
        for (int i = 0; i < 10; i++) {
            PowerOutage outage = new PowerOutage();
            outage.setOutageCode("PO" + String.format("%05d", i + 1));
            outage.setTitle(reasons[i % reasons.length] + "停电-" + (i + 1));
            outage.setOutageType(types[i % types.length]);
            outage.setAffectedArea("区域" + (i % 5 + 1));
            outage.setAffectedCustomers(100 + random.nextInt(500));
            outage.setReason(reasons[i % reasons.length]);
            outage.setStatus(statuses[i % statuses.length]);
            outage.setPlanStartTime(LocalDateTime.now().plusDays(i % 5));
            outage.setPlanEndTime(LocalDateTime.now().plusDays(i % 5).plusHours(4));
            outage.setDuration(240);
            outages.add(powerOutageService.save(outage));
        }
        return outages;
    }
    
    @PostMapping("/generate-line-losses")
    public List<LineLoss> generateLineLosses() {
        List<LineLoss> losses = new ArrayList<>();
        List<TransmissionLine> lines = lineRepository.findAll();
        
        for (int i = 0; i < 20; i++) {
            LineLoss loss = new LineLoss();
            loss.setLineId(lines.isEmpty() ? 1L : lines.get(i % lines.size()).getId());
            loss.setLineName(lines.isEmpty() ? "测试线路" : lines.get(i % lines.size()).getName());
            loss.setLineType("输电线路");
            loss.setRecordTime(LocalDateTime.now().minusDays(i));
            loss.setTimeGranularity("日");
            loss.setSupplyPower(round2(10000.0 + random.nextDouble() * 5000));
            loss.setSellPower(round2(9500.0 + random.nextDouble() * 4500));
            loss.setLossPower(round2(loss.getSupplyPower() - loss.getSellPower()));
            loss.setLossRate(round2(loss.getLossPower() / loss.getSupplyPower() * 100));
            loss.setLineResistance(round2(0.1 + random.nextDouble() * 0.5));
            loss.setLineCurrent(round2(100.0 + random.nextDouble() * 200));
            loss.setLineVoltage(110.0);
            loss.setPowerFactor(round2(0.9 + random.nextDouble() * 0.09));
            losses.add(lineLossService.save(loss));
        }
        return losses;
    }
    
    @PostMapping("/generate-load-forecasts")
    public List<LoadForecast> generateLoadForecasts() {
        List<Substation> substations = substationRepository.findAll();
        if (!substations.isEmpty()) {
            Substation sub = substations.get(0);
            return loadForecastService.generateForecast("变电站", sub.getId(), sub.getName(), 100.0, 24);
        }
        return loadForecastService.generateForecast("变电站", 1L, "测试变电站", 100.0, 24);
    }
    
    @PostMapping("/generate-power-flows")
    public List<PowerFlow> generatePowerFlows() {
        List<PowerFlow> flows = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            PowerFlow pf = powerFlowService.calculate(
                "潮流计算-" + (i + 1),
                "[]",
                "[]"
            );
            flows.add(pf);
        }
        return flows;
    }
    
    @PostMapping("/generate-n1-checks")
    public List<N1Check> generateN1Checks() {
        List<N1Check> checks = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            N1Check check = n1CheckService.performCheck(
                "N-1校验-" + (i + 1),
                "全电网",
                "[]"
            );
            checks.add(check);
        }
        return checks;
    }
    
    @PostMapping("/generate-device-healths")
    public List<DeviceHealth> generateDeviceHealths() {
        List<DeviceHealth> healths = new ArrayList<>();
        List<Substation> substations = substationRepository.findAll();
        
        for (int i = 0; i < 10; i++) {
            Substation sub = substations.isEmpty() ? null : substations.get(i % substations.size());
            DeviceHealth health = deviceHealthService.evaluate(
                "变电站",
                sub == null ? 1L : sub.getId(),
                sub == null ? "测试变电站" : sub.getName(),
                5 + random.nextInt(15),
                random.nextInt(5),
                random.nextInt(10),
                random.nextInt(5),
                round2(50.0 + random.nextDouble() * 40),
                round2(70.0 + random.nextDouble() * 30)
            );
            healths.add(health);
        }
        return healths;
    }
    
    @PostMapping("/generate-reliability-indices")
    public List<ReliabilityIndex> generateReliabilityIndices() {
        List<ReliabilityIndex> indices = new ArrayList<>();
        List<Substation> substations = substationRepository.findAll();
        
        for (int i = 0; i < 5; i++) {
            Substation sub = substations.isEmpty() ? null : substations.get(i % substations.size());
            ReliabilityIndex index = reliabilityIndexService.calculate(
                "变电站",
                sub == null ? 1L : sub.getId(),
                sub == null ? "测试变电站" : sub.getName(),
                "月度",
                1000 + random.nextInt(500),
                2 + random.nextInt(5),
                1 + random.nextInt(3),
                1 + random.nextInt(3),
                60 + random.nextInt(120),
                30 + random.nextInt(60),
                30 + random.nextInt(60),
                50 + random.nextInt(100),
                random.nextInt(10)
            );
            indices.add(index);
        }
        return indices;
    }
    
    @PostMapping("/generate-documents")
    public List<Document> generateDocuments() {
        List<Document> docs = new ArrayList<>();
        String[] types = {"技术文档", "操作规程", "检修记录", "图纸资料"};
        String[] categories = {"变电", "输电", "配电", "综合"};
        
        for (int i = 0; i < 15; i++) {
            Document doc = new Document();
            doc.setDocCode("DOC" + String.format("%05d", i + 1));
            doc.setTitle(types[i % types.length] + "-" + (i + 1));
            doc.setDocType(types[i % types.length]);
            doc.setCategory(categories[i % categories.length]);
            doc.setVersion("V1.0");
            doc.setAuthor("技术员" + (i % 5 + 1));
            doc.setDepartment("技术部");
            doc.setFileName("document_" + (i + 1) + ".pdf");
            doc.setFileType("pdf");
            doc.setFileSize(100000L + random.nextInt(1000000));
            doc.setDescription(types[i % types.length] + "描述内容");
            doc.setKeywords("电力,设备,技术");
            doc.setUploader("admin");
            docs.add(documentService.upload(doc));
        }
        return docs;
    }
    
    @PostMapping("/generate-video-monitors")
    public List<VideoMonitor> generateVideoMonitors() {
        List<VideoMonitor> monitors = new ArrayList<>();
        String[] locations = {"主控室", "高压室", "变压器区", "开关场", "电缆沟"};
        String[] statuses = {"在线", "离线", "故障"};
        
        List<Substation> substations = substationRepository.findAll();
        
        for (int i = 0; i < 20; i++) {
            VideoMonitor monitor = new VideoMonitor();
            monitor.setMonitorCode("VM" + String.format("%05d", i + 1));
            monitor.setName("监控摄像头-" + (i + 1));
            monitor.setDeviceType("变电站");
            monitor.setDeviceId(substations.isEmpty() ? 1L : substations.get(i % substations.size()).getId());
            monitor.setDeviceName(substations.isEmpty() ? "测试变电站" : substations.get(i % substations.size()).getName());
            monitor.setLocation(locations[i % locations.length]);
            monitor.setIp("192.168.1." + (100 + i));
            monitor.setPort(8080);
            monitor.setStreamUrl("rtsp://192.168.1." + (100 + i) + ":8080/stream");
            monitor.setStatus(statuses[i % 10 == 0 ? 2 : (i % 5 == 0 ? 1 : 0)]);
            monitor.setManufacturer("海康威视");
            monitor.setModel("DS-2CD2T45D");
            monitor.setResolution("1080P");
            monitor.setPtzSupport(i % 3 == 0 ? "支持" : "不支持");
            monitors.add(videoMonitorService.save(monitor));
        }
        return monitors;
    }
}
