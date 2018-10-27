//package com.yier.platform.controller;
//
//import com.alibaba.fastjson.JSONObject;
//import com.yier.platform.TestBase;
//import com.yier.platform.common.jsonResponse.CommonResponse;
//import com.yier.platform.common.jsonResponse.ListResponse;
//import com.yier.platform.common.model.Doctor;
//import com.yier.platform.common.model.DoctorPo;
//import com.yier.platform.common.requestParam.DoctorRequest;
//import com.yier.platform.service.DoctorService;
//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.ApiParam;
//import org.apache.http.entity.ContentType;
//import org.junit.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.util.List;
//
//@ApiModel(value = "医生相关的请求接口测试类")
//public class DoctorManagerControllerTest extends TestBase {
//    private static final Logger logger = LoggerFactory.getLogger(DoctorManagerControllerTest.class);
//
//    @Autowired
//    private DoctorService serviceDoctorService;
//
//    @ApiOperation(value = "获取医生称信息列表")
//    @Test
//    public void getDoctorList() {
//        DoctorRequest params = new DoctorRequest();
//        params.doWithSortOrStart();
//        logger.info("获取医生称信息列表, 目前分页条件如下：" + params.toJsonString());
//        ListResponse<Doctor> res = new ListResponse<Doctor>();
//        List<Doctor> doctorList = serviceDoctorService.listDoctor(params);
//        int doctorListCount = serviceDoctorService.listDoctorCount(params);
//        res.setData(doctorList);
//        res.setTotal(doctorListCount);
//        logger.info("获取医生称信息列表, 测试结果：res==={}", res.toJsonString());
//    }
//
//    @ApiOperation(value = "通过id获取医生信息")
//    @Test
//    public void getDoctorPoById() {
//        String json = "{\"id\":505,\"trueName\":\"测试1\"}";
//        DoctorRequest params = JSONObject.parseObject(json, DoctorRequest.class);
//        CommonResponse<DoctorPo> res = new CommonResponse<DoctorPo>();
//        logger.info("通过id获取医生信息，查询条件:{}", params);
//        DoctorPo doctorPo = serviceDoctorService.getDoctorPoById(params);
//        res.setData(doctorPo);
//        logger.info("通过id获取医生信息, 测试结果：res==={}", res.toJsonString());
//    }
//
//    @ApiOperation(value = "新增医生信息")
//    @Test
//    public void insertDoctor() {
//        String json = "{\"trueName\":\"测试新增医生\",\"idCardNo\":\"130102199003073191\",\"phoneNumber\":\"16666666888\",\"hospitalId\":33,\"catalogId\":1,\"departmentId\":5,\"titleId\":1,\"field\":\"擅长\",\"visit\":0,\"practicePoint\":\"[]\",\"medical\":\"1\",\"medicalPayment\":0,\"medicalNum\":0,\"remarks\":\"测试新增医生\",\"profile\":\"介绍\",\"doctorTitle\":\"医士\",\"shareTitle\":\"分享标题\",\"shareSummary\":\"分享简介\"}";
//        Doctor record = JSONObject.parseObject(json, Doctor.class);
//        String fileName1 = "F:\\pic\\1.jpg";
//        String fileName2 = "F:\\pic\\2.jpg";
//        String fileName3 = "F:\\pic\\3.jpg";
//        File file1 = new File(fileName1);
//        File file2 = new File(fileName2);
//        File file3 = new File(fileName3);
//        try {
//            FileInputStream fileInputStream1 = new FileInputStream(file1);
//            MultipartFile avatarPic = new MockMultipartFile(file1.getName(), file1.getName(),
//                    ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream1);
//
//            FileInputStream fileInputStream2 = new FileInputStream(file2);
//            MultipartFile photoPic = new MockMultipartFile(file2.getName(), file2.getName(),
//                    ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream2);
//
//            FileInputStream fileInputStream3 = new FileInputStream(file3);
//            MultipartFile idCardPic = new MockMultipartFile(file3.getName(), file3.getName(),
//                    ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream3);
//
//            logger.info("新增医生信息, 请求参数：record==={}, avatarPic==={}, photoPic==={}, idCardPic==={}", record, avatarPic, photoPic, idCardPic);
//            CommonResponse<DoctorPo> res = new CommonResponse<DoctorPo>();
//            DoctorPo doctorPo = this.serviceDoctorService.insertDoctorWithPic(record, avatarPic, photoPic, idCardPic, 8L);
//            res.setData(doctorPo);
//            logger.debug("新增医生信息, 返回参数：{}", doctorPo);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @ApiOperation(value = "更新医生信息")
//    @Test
//    public void updateDoctor() {
//        String json = "{\"id\":508,\"trueName\":\"更新医生信息\",\"idCardNo\":\"130102199003073191\",\"phoneNumber\":\"16666666888\",\"hospitalId\":33,\"catalogId\":1,\"departmentId\":5,\"titleId\":1,\"field\":\"擅长\",\"visit\":0,\"practicePoint\":\"[]\",\"medical\":\"1\",\"medicalPayment\":0,\"medicalNum\":0,\"remarks\":\"测试新增医生\",\"profile\":\"介绍\",\"doctorTitle\":\"医士\",\"shareTitle\":\"分享标题\",\"shareSummary\":\"分享简介\"}";
//        Doctor record = JSONObject.parseObject(json, Doctor.class);
//        String fileName1 = "F:\\pic\\1.jpg";
//        String fileName2 = "F:\\pic\\2.jpg";
//        String fileName3 = "F:\\pic\\3.jpg";
//        File file1 = new File(fileName1);
//        File file2 = new File(fileName2);
//        File file3 = new File(fileName3);
//        try {
//            FileInputStream fileInputStream1 = new FileInputStream(file1);
//            MultipartFile avatarPic = new MockMultipartFile(file1.getName(), file1.getName(),
//                    ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream1);
//
//            FileInputStream fileInputStream2 = new FileInputStream(file2);
//            MultipartFile photoPic = new MockMultipartFile(file2.getName(), file2.getName(),
//                    ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream2);
//
//            FileInputStream fileInputStream3 = new FileInputStream(file3);
//            MultipartFile idCardPic = new MockMultipartFile(file3.getName(), file3.getName(),
//                    ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream3);
//
//            logger.info("更新医生信息, 请求参数：record==={}, avatarPic==={}, photoPic==={}, idCardPic==={}", record, avatarPic, photoPic, idCardPic);
//            CommonResponse<DoctorPo> res = new CommonResponse<DoctorPo>();
//            DoctorPo doctorPo = this.serviceDoctorService.updateDoctorWithPic(record, avatarPic, photoPic, idCardPic, 8L);
//            res.setData(doctorPo);
//            logger.debug("更新医生信息, 返回参数：{}", doctorPo);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @ApiOperation(value = "删除医生信息")
//    @Test
//    public void deleteDoctor() {
//        String json = "{\"id\":508,\"status\":\"1\"\"remarks\":\"删除医生信息\"}";
//        Doctor record = JSONObject.parseObject(json, Doctor.class);
//        CommonResponse<DoctorPo> res = new CommonResponse<DoctorPo>();
//        this.serviceDoctorService.deleteDoctor(record, 2L);
//        res.setData(new DoctorPo(record));
//        logger.debug("删除医生信息, 返回参数：{}", record);
//    }
//
//    @ApiOperation(value = "后台管理系统启用/停用医生信息")
//    @Test
//    public void enableOrDisableDoctor() {
//        String json = "{\"id\":508,\"status\":\"0\"\"remarks\":\"后台管理系统启用/停用医生信息\"}";
//        Doctor record = JSONObject.parseObject(json, Doctor.class);
//        logger.info("后台管理系统启用/停用医生信息, 请求参数参数：{}", record);
//        CommonResponse<DoctorPo> res = new CommonResponse<DoctorPo>();
//        this.serviceDoctorService.enableOrDisableDoctor(record);
//        logger.debug("后台管理系统启用/停用医生信息, 返回参数：{}", res.toJsonString());
//    }
//}
