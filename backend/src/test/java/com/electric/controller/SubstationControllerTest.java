package com.electric.controller;

import com.electric.model.Substation;
import com.electric.service.SubstationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * SubstationControllerTest - 变电站控制器单元测试
 * 
 * 功能说明：
 * 使用 MockMvc 测试变电站控制器的 RESTful API 接口
 * 使用 Mockito 模拟服务层，实现控制器层的独立测试
 * 
 * 测试范围：
 * 1. 获取所有变电站列表
 * 2. 根据ID获取单个变电站
 * 3. 新增变电站（包括参数校验）
 * 4. 更新变电站信息
 * 5. 删除变电站
 * 
 * 文件关联：
 * - SubstationController: 被测试的控制器
 * - SubstationService: 被模拟的服务层
 */
@WebMvcTest(SubstationController.class)
class SubstationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SubstationService substationService;

    @Autowired
    private ObjectMapper objectMapper;

    private Substation testSubstation;

    @BeforeEach
    void setUp() {
        testSubstation = new Substation("测试变电站", 39.9042, 116.4074, "110kV", "运行中");
        testSubstation.setId(1L);
    }

    @Test
    @DisplayName("获取所有变电站列表 - 成功")
    void getAllSubstations_Success() throws Exception {
        List<Substation> substations = Arrays.asList(testSubstation);
        when(substationService.getAllSubstations()).thenReturn(substations);

        mockMvc.perform(get("/api/substations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].name").value("测试变电站"));

        verify(substationService, times(1)).getAllSubstations();
    }

    @Test
    @DisplayName("根据ID获取变电站 - 成功")
    void getSubstationById_Success() throws Exception {
        when(substationService.getSubstationById(1L)).thenReturn(testSubstation);

        mockMvc.perform(get("/api/substations/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.name").value("测试变电站"));

        verify(substationService, times(1)).getSubstationById(1L);
    }

    @Test
    @DisplayName("根据ID获取变电站 - 不存在")
    void getSubstationById_NotFound() throws Exception {
        when(substationService.getSubstationById(999L)).thenReturn(null);

        mockMvc.perform(get("/api/substations/999"))
                .andExpect(status().isNotFound());

        verify(substationService, times(1)).getSubstationById(999L);
    }

    @Test
    @DisplayName("新增变电站 - 成功")
    void createSubstation_Success() throws Exception {
        when(substationService.saveSubstation(any(Substation.class))).thenReturn(testSubstation);

        mockMvc.perform(post("/api/substations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testSubstation)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.name").value("测试变电站"));

        verify(substationService, times(1)).saveSubstation(any(Substation.class));
    }

    @Test
    @DisplayName("新增变电站 - 参数校验失败（名称为空）")
    void createSubstation_ValidationFail_NameBlank() throws Exception {
        Substation invalidSubstation = new Substation("", 39.9042, 116.4074, "110kV", "运行中");

        mockMvc.perform(post("/api/substations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidSubstation)))
                .andExpect(status().isBadRequest());

        verify(substationService, never()).saveSubstation(any());
    }

    @Test
    @DisplayName("新增变电站 - 参数校验失败（纬度超出范围）")
    void createSubstation_ValidationFail_InvalidLatitude() throws Exception {
        Substation invalidSubstation = new Substation("测试变电站", 100.0, 116.4074, "110kV", "运行中");

        mockMvc.perform(post("/api/substations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidSubstation)))
                .andExpect(status().isBadRequest());

        verify(substationService, never()).saveSubstation(any());
    }

    @Test
    @DisplayName("更新变电站 - 成功")
    void updateSubstation_Success() throws Exception {
        when(substationService.getSubstationById(1L)).thenReturn(testSubstation);
        when(substationService.saveSubstation(any(Substation.class))).thenReturn(testSubstation);

        mockMvc.perform(put("/api/substations/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testSubstation)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));

        verify(substationService, times(1)).getSubstationById(1L);
        verify(substationService, times(1)).saveSubstation(any(Substation.class));
    }

    @Test
    @DisplayName("删除变电站 - 成功")
    void deleteSubstation_Success() throws Exception {
        doNothing().when(substationService).deleteSubstation(1L);

        mockMvc.perform(delete("/api/substations/1"))
                .andExpect(status().isNoContent());

        verify(substationService, times(1)).deleteSubstation(1L);
    }
}
