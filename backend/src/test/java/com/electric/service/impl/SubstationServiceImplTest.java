package com.electric.service.impl;

import com.electric.model.Substation;
import com.electric.repository.SubstationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * SubstationServiceImplTest - 变电站服务实现类单元测试
 * 
 * 功能说明：
 * 使用 Mockito 测试变电站服务实现类的业务逻辑
 * 使用 @ExtendWith(MockitoExtension.class) 启用 Mockito 支持
 * 
 * 测试范围：
 * 1. 获取所有变电站列表
 * 2. 根据ID获取单个变电站
 * 3. 保存变电站
 * 4. 删除变电站
 * 
 * 文件关联：
 * - SubstationServiceImpl: 被测试的服务实现类
 * - SubstationRepository: 被模拟的数据访问层
 */
@ExtendWith(MockitoExtension.class)
class SubstationServiceImplTest {

    @Mock
    private SubstationRepository substationRepository;

    @InjectMocks
    private SubstationServiceImpl substationService;

    private Substation testSubstation;

    @BeforeEach
    void setUp() {
        testSubstation = new Substation("测试变电站", 39.9042, 116.4074, "110kV", "运行中");
        testSubstation.setId(1L);
    }

    @Test
    @DisplayName("获取所有变电站列表 - 成功")
    void getAllSubstations_Success() {
        List<Substation> expectedSubstations = Arrays.asList(testSubstation);
        when(substationRepository.findAll()).thenReturn(expectedSubstations);

        List<Substation> actualSubstations = substationService.getAllSubstations();

        assertNotNull(actualSubstations);
        assertEquals(1, actualSubstations.size());
        assertEquals("测试变电站", actualSubstations.get(0).getName());
        verify(substationRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("根据ID获取变电站 - 存在")
    void getSubstationById_Exists() {
        when(substationRepository.findById(1L)).thenReturn(Optional.of(testSubstation));

        Substation actualSubstation = substationService.getSubstationById(1L);

        assertNotNull(actualSubstation);
        assertEquals("测试变电站", actualSubstation.getName());
        verify(substationRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("根据ID获取变电站 - 不存在")
    void getSubstationById_NotExists() {
        when(substationRepository.findById(999L)).thenReturn(Optional.empty());

        Substation actualSubstation = substationService.getSubstationById(999L);

        assertNull(actualSubstation);
        verify(substationRepository, times(1)).findById(999L);
    }

    @Test
    @DisplayName("保存变电站 - 成功")
    void saveSubstation_Success() {
        when(substationRepository.save(any(Substation.class))).thenReturn(testSubstation);

        Substation savedSubstation = substationService.saveSubstation(testSubstation);

        assertNotNull(savedSubstation);
        assertEquals("测试变电站", savedSubstation.getName());
        verify(substationRepository, times(1)).save(testSubstation);
    }

    @Test
    @DisplayName("删除变电站 - 成功")
    void deleteSubstation_Success() {
        doNothing().when(substationRepository).deleteById(1L);

        substationService.deleteSubstation(1L);

        verify(substationRepository, times(1)).deleteById(1L);
    }
}
