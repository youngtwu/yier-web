package com.yier.platform.web;

import com.github.yingzhuo.carnival.restful.security.Logical;
import com.github.yingzhuo.carnival.restful.security.RequiresAuthentication;
import com.github.yingzhuo.carnival.restful.security.RequiresRoles;
import com.yier.platform.annotation.ApiCheck;
import com.yier.platform.common.jsonResponse.CommonResponse;
import com.yier.platform.common.jsonResponse.ListResponse;
import com.yier.platform.common.model.Doctor;
import com.yier.platform.common.model.DoctorPo;
import com.yier.platform.common.requestParam.DoctorRequest;
import com.yier.platform.service.DoctorService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@ApiModel(value = "医生相关的请求接口")
@RestController
@RequestMapping("/doctorManager")
@Slf4j
public class DoctorManagerController {
    private static final Logger logger = LoggerFactory.getLogger(DoctorManagerController.class);

    @Autowired
    private DoctorService serviceDoctorService;

    @ApiOperation(value = "获取医生称信息列表({})")
    @ApiCheck(check = false)
    @RequiresAuthentication
    @RequiresRoles(value = {"Super", "Hospital"}, logical = Logical.OR)
    @RequestMapping(value = "/getDoctorList.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ListResponse<Doctor> getDoctorList(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "医生请求接口类") @RequestBody DoctorRequest params) {
        params.doWithSortOrStart();
        logger.info("目前分页条件如下：" + params.toJsonString());
        ListResponse<Doctor> res = new ListResponse<Doctor>();
        List<Doctor> doctorList = serviceDoctorService.listDoctor(params);
        int doctorListCount = serviceDoctorService.listDoctorCount(params);
        res.setData(doctorList);
        res.setTotal(doctorListCount);
        logger.debug(res.toJsonString());
        return res;
    }

    @ApiOperation(value = "通过id获取医生信息，包括职位，职业点")
    @ApiCheck(check = false)
    @RequiresAuthentication
    @RequiresRoles(value = {"Super", "Hospital"}, logical = Logical.OR)
    @RequestMapping(value = "/getDoctorPoById.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse<DoctorPo> getDoctorPoById(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "医生请求接口类") @RequestBody DoctorRequest params) {
        CommonResponse<DoctorPo> res = new CommonResponse<DoctorPo>();
        logger.info("通过id获取医生信息，查询条件:{}", params);
        DoctorPo doctorPo = serviceDoctorService.getDoctorPoById(params);
        res.setData(doctorPo);
        logger.debug("通过id获取医生信息，返回结果:{}", res);
        return res;
    }

    @ApiOperation(value = "新增医生信息 状态（0正常有效 审核通过 1删除 2未提交 点击保存 4无效 5未通过 A审核中 未审核  B未复审 复审中 未通过的提交）")
    @ApiCheck(check = false)
    @RequiresAuthentication
    @RequiresRoles(value = {"Super", "Hospital"}, logical = Logical.OR)
    @RequestMapping(value = "/insertDoctor.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse<DoctorPo> insertDoctor(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "医生实体类") Doctor record, @RequestParam(value = "avatarPic", required = false) MultipartFile avatarPic, @RequestParam(value = "photoPic", required = false) MultipartFile photoPic, @RequestParam(value = "idCardPic", required = false) MultipartFile idCardPic) {
        logger.info("新增医生信息, 请求参数：{}", record);
        String userIdStr = request.getHeader("userId");
        Assert.isTrue(StringUtils.isNotBlank(userIdStr), "用户名不能为空！");
        Long userId = Long.parseLong(userIdStr);
        CommonResponse<DoctorPo> res = new CommonResponse<DoctorPo>();
        DoctorPo doctorPo = this.serviceDoctorService.insertDoctorWithPic(record, avatarPic, photoPic, idCardPic, userId);
        res.setData(doctorPo);
        logger.debug("新增医生信息, 返回参数：{}", doctorPo);
        return res;
    }

    @ApiOperation(value = "更新医生信息 状态（0正常有效 审核通过 1删除 2未提交 点击保存 4无效 5未通过 A审核中 未审核  B未复审 复审中 未通过的提交）")
    @ApiCheck(check = false)
    @RequiresAuthentication
    @RequiresRoles(value = {"Super", "Hospital"}, logical = Logical.OR)
    @RequestMapping(value = "/updateDoctor.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse<DoctorPo> updateDoctor(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "医生实体类") Doctor record, @RequestParam(value = "avatarPic", required = false) MultipartFile avatarPic, @RequestParam(value = "photoPic", required = false) MultipartFile photoPic, @RequestParam(value = "idCardPic", required = false) MultipartFile idCardPic) {
        logger.info("更新医生信息, 请求参数参数：{}", record);
        String userIdStr = request.getHeader("userId");
        Assert.isTrue(StringUtils.isNotBlank(userIdStr), "用户名不能为空！");
        Long userId = Long.parseLong(userIdStr);
        CommonResponse<DoctorPo> res = new CommonResponse<DoctorPo>();
        this.serviceDoctorService.updateDoctorWithPic(record, avatarPic, photoPic, idCardPic, userId);
        logger.debug("更新医生信息, 返回参数：{}", record);
        return res;
    }

    @ApiOperation(value = "后台管理系统启用/停用医生信息 状态（0正常有效 审核通过 1删除 2未提交 点击保存 4无效 5未通过 A审核中 未审核  B未复审 复审中 未通过的提交）")
    @ApiCheck(check = false)
    @RequiresAuthentication
    @RequiresRoles(value = {"Super", "Hospital"}, logical = Logical.OR)
    @RequestMapping(value = "/enableOrDisableDoctor.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse<DoctorPo> enableOrDisableDoctor(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "医生实体类") @RequestBody Doctor record) {
        logger.info("后台管理系统启用/停用医生信息, 请求参数参数：{}", record);
        CommonResponse<DoctorPo> res = new CommonResponse<DoctorPo>();
        this.serviceDoctorService.enableOrDisableDoctor(record);
        logger.debug("更新医生信息, 返回参数：{}", record);
        return res;
    }

    @ApiOperation(value = "删除医生信息 状态（0正常有效 审核通过 1删除 2未提交 点击保存 4无效 5未通过 A审核中 未审核  B未复审 复审中 未通过的提交）")
    @ApiCheck(check = false)
    @RequiresAuthentication
    @RequiresRoles(value = {"Super", "Hospital"}, logical = Logical.OR)
    @RequestMapping(value = "/deleteDoctor.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse<DoctorPo> deleteDoctor(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "客户端标识 1-患者端，2-医生端，3-药师端") @RequestParam Long typeId, @ApiParam(value = "医生实体类") @RequestBody Doctor record) {
        logger.info("删除医生信息, 请求参数参数：{}, {}", record, typeId);
        CommonResponse<DoctorPo> res = new CommonResponse<DoctorPo>();
        this.serviceDoctorService.deleteDoctor(record, typeId);
        res.setData(new DoctorPo(record));
        logger.debug("删除医生信息, 返回参数：{}", record);
        return res;
    }

}
