package com.yier.platform.service;

import com.alibaba.fastjson.JSONObject;
import com.yier.platform.TestBase;
import com.yier.platform.common.jsonResponse.CommonResponse;
import com.yier.platform.common.jsonResponse.ListResponse;
import com.yier.platform.common.model.*;
import com.yier.platform.common.requestParam.HospitalRequest;
import com.yier.platform.common.requestParam.PracticeRequest;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.tuple.MutableTriple;
import org.apache.http.entity.ContentType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class HospitalServiceTest extends TestBase {
    private static final Logger logger = LoggerFactory.getLogger(HospitalServiceTest.class);
    @Autowired
    private HospitalService serviceHospitalService;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getHospitalPoWithNameByIds() {
        PracticeRequest params = new PracticeRequest();
        params.setHospitalId(1L);
        params.setCatalogId(4L);
        params.setHospitalDepartmentId(3L);
        HospitalPo po = serviceHospitalService.getHospitalPoWithNameByIds(params);
        logger.info("1目前查询的结果是：{}", po);

        params = new PracticeRequest();
        params.setHospitalId(1L);
        params.setCatalogId(4L);
        po = serviceHospitalService.getHospitalPoWithNameByIds(params);
        logger.info("2目前查询的结果是：{}", po);

        params = new PracticeRequest();
        params.setHospitalId(1L);
        params.setCatalogId(8L);
        params.setHospitalDepartmentId(6L);
        po = serviceHospitalService.getHospitalPoWithNameByIds(params);
        logger.info("3目前查询的结果是：{}", po);
    }

    @ApiOperation(value = "获取医院信息列表")
    @Test
    public void getHospitalList() {
        HospitalRequest params = new HospitalRequest();
        params.doWithSortOrStart();
        logger.info("获取医院信息列表, 请求参数：params==={}", params);
        ListResponse<HospitalPo> res = new ListResponse<HospitalPo>();
        List<HospitalPo> hospitalPoList = serviceHospitalService.listHospital(params);
        int count = serviceHospitalService.listHospitalCount(params);
        res.setData(hospitalPoList);
        res.setTotal(count);
        logger.info("获取医院信息列表, 测试结果：res==={}", res.toJsonString());
    }

    @ApiOperation(value = "通过id获取医院信息")
    @Test
    public void getHospitalById() {
        CommonResponse<MutableTriple<HospitalPo, AuditInfo, List<AuditInfo>>> res = new CommonResponse<MutableTriple<HospitalPo, AuditInfo, List<AuditInfo>>>();
        MutableTriple<HospitalPo, AuditInfo, List<AuditInfo>> mutableTriple = serviceHospitalService.getHospitalById(61L);
        Assert.isTrue(mutableTriple != null, "不存在该医院！");
        res.setData(mutableTriple);
        logger.info("通过id获取医院信息, 测试结果：res==={}", res.toJsonString());
    }

    @ApiOperation(value = "新增医院信息")
    @Test
    public void insertHospital() {
        String json = "{\"name\":\"单元测试新增医院\",\"levelId\":9,\"businessType\":\"私立\",\"webSite\":\"\",\"provinceId\":\"666888\",\"cityId\":\"666888\",\"areaId\":\"666888\",\"address\":\"123\",\"contact\":\"\",\"lng\":1,\"lat\":2,\"remarks\":\"单元测试新增医院\",\"profile\":\"单元测试新增医院\",\"levelInfo\":\"三级甲等\"}";
        Hospital hospital = JSONObject.parseObject(json, Hospital.class);
        CommonResponse<HospitalPo> res = new CommonResponse<HospitalPo>();
        String fileName = "F:\\pic\\1.jpg";
        File file = new File(fileName);
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            MultipartFile multipartFile = new MockMultipartFile(file.getName(), file.getName(),
                    ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream);
            MultipartFile[] files = new MultipartFile[]{multipartFile};
            logger.info("[新增医院信息 请求参数：hospital==={}, files==={}", hospital, new FileInputStream(fileName));
            HospitalPo hospitalPo = this.serviceHospitalService.insertHospital(hospital, files, 8L);
            res.setData(hospitalPo);
            logger.info("新增医院信息, 测试结果：res==={}", res.toJsonString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @ApiOperation(value = "更新医院信息")
    @Test
    public void updateHospital() {
        String json = "{\"id\":62,\"hospitalCodeNo\":\"1540185946846H768933504\",\"name\":\"单元测试新增医院\",\"levelId\":9,\"businessType\":\"私立\",\"webSite\":\"\",\"provinceId\":\"666888\",\"cityId\":\"666888\",\"areaId\":\"666888\",\"address\":\"123\",\"contact\":\"\",\"lng\":1,\"lat\":2,\"remarks\":\"单元测试新增医院\",\"profile\":\"单元测试新增医院\",\"levelInfo\":\"三级甲等\"}";
        Hospital hospital = JSONObject.parseObject(json, Hospital.class);
        CommonResponse<HospitalPo> res = new CommonResponse<HospitalPo>();
        String fileName = "F:\\pic\\1.jpg";
        File file = new File(fileName);
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            MultipartFile multipartFile = new MockMultipartFile(file.getName(), file.getName(),
                    ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream);
            MultipartFile[] files = new MultipartFile[]{multipartFile};
            logger.info("更新医院信息 请求参数：hospital==={}, files==={}", hospital, new FileInputStream(fileName));
            HospitalPo hospitalPo = this.serviceHospitalService.updateHospital(hospital, files, 8L);
            res.setData(hospitalPo);
            logger.info("更新医院信息, 测试结果：res==={}", res.toJsonString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @ApiOperation(value = "删除医院信息")
    @Test
    public void deleteHospital() {
        CommonResponse<HospitalPo> res = new CommonResponse<HospitalPo>();
        Hospital hospital = this.serviceHospitalService.deleteHospital(62L);
        res.setData(new HospitalPo(hospital));
        logger.info("删除医院信息, 测试结果：res==={}", res.toJsonString());
    }

    @ApiOperation(value = "根据医院id停用/启用医院")
    @Test
    public void enableOrDisabelHospital() {
        String json = "{\"id\":62,\"status\":\"0\"}";
        Hospital record = JSONObject.parseObject(json, Hospital.class);
        logger.info("根据医院id停用/启用医院 参数如下：record==={}", record);
        CommonResponse<HospitalPo> res = new CommonResponse<HospitalPo>();
        Hospital hospital = this.serviceHospitalService.enableOrDisabelHospital(record);
        res.setData(new HospitalPo(hospital));
        logger.info("根据医院id停用/启用医院, 测试结果：res==={}", res.toJsonString());
    }

    @ApiOperation(value = "通过查询获取医院科室大分类信息列表")
    @Test
    public void getDepartmentCatalogList() {
        HospitalRequest params = new HospitalRequest();
        params.doWithSortOrStart();
        logger.info("[filterShow跟patientId 都有值患者需要过滤满足 开通聊天设置并且是有效数据]查询大分类 目前条件如下：{}", params);
        ListResponse<DepartmentCatalog> res = new ListResponse<DepartmentCatalog>();
        List<DepartmentCatalog> departmentCatalogList = this.serviceHospitalService.listDepartmentCatalog(params);
        int count = this.serviceHospitalService.listDepartmentCatalogCount(params);
        res.setData(departmentCatalogList);
        res.setTotal(count);
        logger.info("通过查询获取医院科室大分类信息列表, 测试结果：res==={}", res.toJsonString());
    }

    @ApiOperation(value = "测试新增大科室")
    @Test
    public void insertDepartmentCatalog() {
        String json = "{\"name\":\"测试新增大科室\",\"hospitalId\":31,\"useType\":2,\"remarks\":\"测试新增大科室\"}";
        DepartmentCatalog departmentCatalog = JSONObject.parseObject(json, DepartmentCatalog.class);
        logger.info("测试新增大科室 参数如下：departmentCatalog==={}", departmentCatalog);
        CommonResponse<DepartmentCatalog> res = new CommonResponse<DepartmentCatalog>();
        DepartmentCatalog dCatalog = this.serviceHospitalService.insertDepartmentCatalog(departmentCatalog);
        res.setData(dCatalog);
        logger.info("测试新增大科室, 测试结果：res==={}", res.toJsonString());
    }

    @ApiOperation(value = "测试更新大科室")
    @Test
    public void updateDepartmentCatalog() {
        String json = "{\"id\":93,\"name\":\"测试更新大科室\",\"hospitalId\":31,\"useType\":2,\"remarks\":\"测试更新大科室\"}";
        DepartmentCatalog departmentCatalog = JSONObject.parseObject(json, DepartmentCatalog.class);
        logger.info("测试更新大科室 参数如下：departmentCatalog==={}", departmentCatalog);
        CommonResponse<DepartmentCatalog> res = new CommonResponse<DepartmentCatalog>();
        DepartmentCatalog dCatalog = this.serviceHospitalService.updateDepartmentCatalog(departmentCatalog);
        res.setData(dCatalog);
        logger.info("测试更新大科室, 测试结果：res==={}", res.toJsonString());
    }

    @ApiOperation(value = "根据id删除大科室")
    @Test
    public void deleteDepartmentCatalogById() {
        CommonResponse<Long> res = new CommonResponse<Long>();
        Long delId = this.serviceHospitalService.deleteDepartmentCatalogById(93L);
        res.setData(delId);
        logger.info("测试根据id删除大科室, 测试结果：res==={}", res.toJsonString());
    }

    @ApiOperation(value = "测试启用/停用大科室")
    @Test
    public void enableOrDisableDepartmentCatalog() {
        String json = "{\"id\":93,\"status\":\"0\",\"remarks\":\"测试启用/停用大科室\"}";
        DepartmentCatalog record = JSONObject.parseObject(json, DepartmentCatalog.class);
        logger.info("测试启用/停用大科室 参数如下：record==={}", record);
        CommonResponse<DepartmentCatalog> res = new CommonResponse<DepartmentCatalog>();
        DepartmentCatalog departmentCatalog = this.serviceHospitalService.enableOrDisableDepartmentCatalog(record);
        res.setData(departmentCatalog);
        logger.info("测试启用/停用大科室, 测试结果：res==={}", res.toJsonString());
    }

    @ApiOperation(value = "通过查询获取医院科室小分类信息列表")
    @Test
    public void getHospitalDepartmentList() {
        HospitalRequest params = new HospitalRequest();
        params.doWithSortOrStart();
        logger.info("[filterShow跟patientId 都有值患者需要过滤满足 开通聊天设置并且是有效数据]查询小分类 目前条件如下：{}", params);
        ListResponse<HospitalDepartment> res = new ListResponse<HospitalDepartment>();
        List<HospitalDepartment> hospitalDepartmentList = this.serviceHospitalService.listHospitalDepartment(params);
        int count = this.serviceHospitalService.listHospitalDepartmentCount(params);
        res.setData(hospitalDepartmentList);
        res.setTotal(count);
        logger.info("通过查询获取医院科室小分类信息列表, 测试结果：res==={}", res.toJsonString());
    }

    @ApiOperation(value = "测试根据医院id和大科室新增小科室")
    @Test
    public void insertHospitalDepartment() {
        String json = "{\"name\":\"测试新增小科室\",\"hospitalId\":4,\"catalogId\":62,\"useType\":2,\"remarks\":\"测试新增小科室\"}";
        HospitalDepartment hospitalDepartment = JSONObject.parseObject(json, HospitalDepartment.class);
        logger.info("测试根据医院id和大科室新增小科室 参数如下：hospitalDepartment==={}", hospitalDepartment);
        CommonResponse<HospitalDepartment> res = new CommonResponse<HospitalDepartment>();
        HospitalDepartment hDepartment = this.serviceHospitalService.insertHospitalDepartment(hospitalDepartment);
        res.setData(hDepartment);
        logger.info("测试根据医院id和大科室新增小科室, 测试结果：res==={}", res.toJsonString());
    }

    @ApiOperation(value = "根据医院id和大科室更新小科室")
    @Test
    public void updateHospitalDepartment() {
        String json = "{\"id\":182,\"name\":\"测试更新小科室\",\"hospitalId\":4,\"catalogId\":62,\"useType\":2,\"remarks\":\"测试更新小科室\"}";
        HospitalDepartment hospitalDepartment = JSONObject.parseObject(json, HospitalDepartment.class);
        logger.info("根据医院id和大科室更新小科室, 参数如下：hospitalDepartment==={}", hospitalDepartment);
        CommonResponse<HospitalDepartment> res = new CommonResponse<HospitalDepartment>();
        HospitalDepartment hDepartment = this.serviceHospitalService.updateHospitalDepartment(hospitalDepartment);
        res.setData(hDepartment);
        logger.info("根据医院id和大科室更新小科室, 测试结果：res==={}", res.toJsonString());
    }

    @ApiOperation(value = "根据id删除小科室")
    @Test
    public void deleteHospitalDepartmentById() {
        CommonResponse<Long> res = new CommonResponse<Long>();
        Long delId = this.serviceHospitalService.deleteHospitalDepartmentById(182L);
        res.setData(delId);
        logger.info("根据id删除小科室, 测试结果：res==={}", res.toJsonString());
    }

    @ApiOperation(value = "启用/停用小科室")
    @Test
    public void enableOrDisableHospitalDepartment() {
        String json = "{\"id\":182,\"status\":\"0\",\"remarks\":\"启用/停用小科室\"}";
        HospitalDepartment record = JSONObject.parseObject(json, HospitalDepartment.class);
        logger.info("启用/停用小科室, 参数如下：record==={}", record);
        CommonResponse<HospitalDepartment> res = new CommonResponse<HospitalDepartment>();
        HospitalDepartment hospitalDepartment = this.serviceHospitalService.enableOrDisableHospitalDepartment(record);
        res.setData(hospitalDepartment);
        logger.info("启用/停用小科室, 测试结果：res==={}", res.toJsonString());
    }
}