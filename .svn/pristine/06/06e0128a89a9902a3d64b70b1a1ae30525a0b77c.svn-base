package com.yier.platform.web;

import com.github.yingzhuo.carnival.restful.security.Logical;
import com.github.yingzhuo.carnival.restful.security.RequiresAuthentication;
import com.github.yingzhuo.carnival.restful.security.RequiresRoles;
import com.github.yingzhuo.carnival.restful.security.userdetails.UserDetails;
import com.github.yingzhuo.carnival.restful.security.util.UserDetailsUtils;
import com.yier.platform.annotation.ApiCheck;
import com.yier.platform.common.jsonResponse.CommonResponse;
import com.yier.platform.common.jsonResponse.ListResponse;
import com.yier.platform.common.model.*;
import com.yier.platform.common.requestParam.*;
import com.yier.platform.service.AuditInfoService;
import com.yier.platform.service.DoctorService;
import com.yier.platform.service.HospitalService;
import com.yier.platform.service.PharmacistService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.MutableTriple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@ApiModel(value = "审核信息管理相关的请求接口")
@RestController
@RequestMapping("/auditInfoManager")
@Slf4j
public class AuditInfoManagerController {
    private static final Logger logger = LoggerFactory.getLogger(AuditInfoManagerController.class);

    @Autowired
    private AuditInfoService auditInfoService;
    @Autowired
    private HospitalService serviceHospitalService;
    @Autowired
    private DoctorService serviceDoctorService;
    @Autowired
    private PharmacistService pharmacistService;

    @ApiOperation(value = "获取医院审核信息列表")
    @ApiCheck(check = false)
    @RequiresAuthentication
    @RequiresRoles(value = {"Audit"}, logical = Logical.AND)
    @RequestMapping(value = "/selectHospitalAuditInfoList.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ListResponse<HospitalPo> selectAuditInfoList(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "获取医院审核信息列表接口类") @RequestBody HospitalRequest params) {
        params.doWithSortOrStart();
        logger.info("获取医院审核信息列表, 请求参数：{}", params);
        ListResponse<HospitalPo> res = new ListResponse<HospitalPo>();
        List<HospitalPo> list = this.serviceHospitalService.listHospital(params);
        int count = this.serviceHospitalService.listHospitalCount(params);
        res.setData(list);
        res.setTotal(count);
        logger.debug(res.toJsonString());
        return res;
    }

    @ApiOperation(value = "获取医生审核信息列表")
    @ApiCheck(check = false)
    @RequiresAuthentication
    @RequiresRoles(value = {"Audit"}, logical = Logical.AND)
    @RequestMapping(value = "/selectDoctorAuditInfoList.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ListResponse<DoctorPo> selectDoctorAuditInfoList(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "获取医生审核信息列表接口类") @RequestBody DoctorRequest params) {
        params.doWithSortOrStart();
        logger.info("获取医生审核信息列表, 请求参数：{}", params);
        ListResponse<DoctorPo> res = new ListResponse<DoctorPo>();
        List<DoctorPo> list = this.serviceDoctorService.listDoctorPo(params);
        int count = this.serviceDoctorService.listDoctorCount(params);
        res.setData(list);
        res.setTotal(count);
        logger.debug(res.toJsonString());
        return res;
    }

    @ApiOperation(value = "获取药师审核信息列表")
    @ApiCheck(check = false)
    @RequiresAuthentication
    @RequiresRoles(value = {"Audit"}, logical = Logical.AND)
    @RequestMapping(value = "/selectPharmacistAuditInfoList.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ListResponse<PharmacistPo> selectPharmacistAuditInfoList(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "获取药师审核信息列表接口类") @RequestBody PharmacistRequest params) {
        params.doWithSortOrStart();
        logger.info("获取药师审核信息列表, 请求参数：{}", params);
        ListResponse<PharmacistPo> res = new ListResponse<PharmacistPo>();
        List<PharmacistPo> list = this.pharmacistService.listPharmacistPo(params);
        int count = this.pharmacistService.listPharmacistCount(params);
        res.setData(list);
        res.setTotal(count);
        logger.debug(res.toJsonString());
        return res;
    }

    @ApiOperation(value = "根据id获取医院审核信息")
    @ApiCheck(check = false)
    @RequiresAuthentication
    @RequiresRoles(value = {"Audit"}, logical = Logical.AND)
    @RequestMapping(value = "/selectAuditInfoByHospistalId.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse<MutableTriple<HospitalPo, AuditInfo, List<AuditInfo>>> selectAuditInfoByHospistalId(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "根据id获取医院审核信息接口类") @RequestBody HospitalRequest params) {
        logger.info("根据id获取医院审核信息, 请求参数：{}", params);
        CommonResponse<MutableTriple<HospitalPo, AuditInfo, List<AuditInfo>>> res = new CommonResponse<MutableTriple<HospitalPo, AuditInfo, List<AuditInfo>>>();
        MutableTriple<HospitalPo, AuditInfo, List<AuditInfo>> mutableTriple = this.auditInfoService.selectHospitalAuditInfo(params);
        res.setData(mutableTriple);
        logger.debug(res.toJsonString());
        return res;
    }

    @ApiOperation(value = "根据id获取医生审核信息")
    @ApiCheck(check = false)
    @RequiresAuthentication
    @RequiresRoles(value = {"Audit"}, logical = Logical.AND)
    @RequestMapping(value = "/selectAuditInfoByDoctorId.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse<MutableTriple<DoctorPo, AuditInfo, List<AuditInfo>>> selectAuditInfoByDoctorId(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "根据id获取医生审核信息接口类") @RequestBody DoctorRequest params) {
        logger.info("根据id获取医生审核信息, 请求参数：{}", params);
        CommonResponse<MutableTriple<DoctorPo, AuditInfo, List<AuditInfo>>> res = new CommonResponse<MutableTriple<DoctorPo, AuditInfo, List<AuditInfo>>>();
        MutableTriple<DoctorPo, AuditInfo, List<AuditInfo>> mutableTriple = this.auditInfoService.selectDoctorAuditInfo(params);
        res.setData(mutableTriple);
        logger.debug(res.toJsonString());
        return res;
    }

    @ApiOperation(value = "根据id获取药师审核信息")
    @ApiCheck(check = false)
    @RequiresAuthentication
    @RequiresRoles(value = {"Audit"}, logical = Logical.AND)
    @RequestMapping(value = "/selectAuditInfoByPharmacistId.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse<MutableTriple<PharmacistPo, AuditInfo, List<AuditInfo>>> selectAuditInfoByPharmacistId(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "根据id获取药师审核信息接口类") @RequestBody PharmacistRequest params) {
        logger.info("根据id获取药师审核信息, 请求参数：{}", params);
        CommonResponse<MutableTriple<PharmacistPo, AuditInfo, List<AuditInfo>>> res = new CommonResponse<MutableTriple<PharmacistPo, AuditInfo, List<AuditInfo>>>();
        MutableTriple<PharmacistPo, AuditInfo, List<AuditInfo>> mutableTriple = this.auditInfoService.selectPharmacistAuditInfo(params);
        res.setData(mutableTriple);
        logger.debug(res.toJsonString());
        return res;
    }

    @ApiOperation(value = "根据id获取公告审核信息")
    @ApiCheck(check = false)
    @RequiresAuthentication
    @RequiresRoles(value = {"Audit"}, logical = Logical.AND)
    @RequestMapping(value = "/selectAuditInfoByMassagesId.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse<MutableTriple<MessagesPo, AuditInfo, List<AuditInfo>>> selectAuditInfoByMassagesId(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "根据id获取公告审核信息接口类") @RequestBody MessagesRequest params) {
        logger.info("根据id获取公告审核信息, 请求参数：{}", params);
        CommonResponse<MutableTriple<MessagesPo, AuditInfo, List<AuditInfo>>> res = new CommonResponse<MutableTriple<MessagesPo, AuditInfo, List<AuditInfo>>>();
        MutableTriple<MessagesPo, AuditInfo, List<AuditInfo>> mutableTriple = this.auditInfoService.selectMessageAuditInfo(params);
        res.setData(mutableTriple);
        logger.debug(res.toJsonString());
        return res;
    }

    @ApiOperation(value = "根据id获取新闻资讯审核信息")
    @ApiCheck(check = false)
    @RequiresAuthentication
    @RequiresRoles(value = {"Audit"}, logical = Logical.AND)
    @RequestMapping(value = "/selectAuditInfoByNewsId.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse<MutableTriple<NewsPo, AuditInfo, List<AuditInfo>>> selectAuditInfoByNewsId(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "根据id获取新闻资讯审核信息接口类") @RequestBody NewsRequest params) {
        logger.info("根据id获取新闻资讯审核信息, 请求参数：{}", params);
        CommonResponse<MutableTriple<NewsPo, AuditInfo, List<AuditInfo>>> res = new CommonResponse<MutableTriple<NewsPo, AuditInfo, List<AuditInfo>>>();
        MutableTriple<NewsPo, AuditInfo, List<AuditInfo>> mutableTriple = this.auditInfoService.selectNewsAuditInfo(params);
        res.setData(mutableTriple);
        logger.debug(res.toJsonString());
        return res;
    }

    @ApiOperation(value = "根据用户id和端口类型获取用户手机号审核信息")
    @ApiCheck(check = false)
    @RequiresAuthentication
    @RequiresRoles(value = {"Audit"}, logical = Logical.AND)
    @RequestMapping(value = "/selectAuditInfoByUserIdAndClientType.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse<MutableTriple<UserPo, AuditInfo, List<AuditInfo>>> selectAuditInfoByUserIdAndClientType(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "根据用户id和端口类型获取用户手机号审核信息接口类") @RequestBody UserRequest params) {
        logger.info("根据用户id和端口类型获取用户手机号审核信息, 请求参数：{}", params);
        CommonResponse<MutableTriple<UserPo, AuditInfo, List<AuditInfo>>> res = new CommonResponse<MutableTriple<UserPo, AuditInfo, List<AuditInfo>>>();
        MutableTriple<UserPo, AuditInfo, List<AuditInfo>> mutableTriple = this.auditInfoService.selectAuditInfoByUserIdAndClientType(params);
        res.setData(mutableTriple);
        logger.debug(res.toJsonString());
        return res;
    }

    @ApiOperation(value = "审核医院信息{\"id\":500,\"remarks\":\"测试审核\",\"status\":\"5\"}")
    @ApiCheck(check = false)
    @RequiresAuthentication
    @RequiresRoles(value = {"Audit"}, logical = Logical.AND)
    @RequestMapping(value = "/auditHospital.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse<MutableTriple<HospitalPo, AuditInfo, List<AuditInfo>>> auditHospital(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "医院实体类") Hospital hospital, @ApiParam(value = "审核意见内容") @RequestParam String content) {
        logger.info("审核医院信息, 请求参数：hospital==={}, content==={}", hospital, content);
        CommonResponse<MutableTriple<HospitalPo, AuditInfo, List<AuditInfo>>> res = new CommonResponse<MutableTriple<HospitalPo, AuditInfo, List<AuditInfo>>>();
        //String userIdStr = request.getHeader("userId");
        UserDetails userDetails = UserDetailsUtils.get();
        Assert.isTrue(userDetails.getId() != null, "用户id不能为空！");
        Long userId = Long.parseLong(userDetails.getId().toString());
        MutableTriple<HospitalPo, AuditInfo, List<AuditInfo>> mutableTriple = this.auditInfoService.auditHospital(hospital, userId, content);
        res.setData(mutableTriple);
        logger.debug(res.toJsonString());
        return res;
    }

    @ApiOperation(value = "审核医生信息{\"id\":500,\"remarks\":\"测试审核\",\"status\":\"5\"}")
    @ApiCheck(check = false)
    @RequiresAuthentication
    @RequiresRoles(value = {"Audit"}, logical = Logical.AND)
    @RequestMapping(value = "/auditDoctor.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse<MutableTriple<DoctorPo, AuditInfo, List<AuditInfo>>> auditDoctor(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "医生实体类") Doctor doctor, @ApiParam(value = "审核意见内容") @RequestParam String content) {
        logger.info("审核医生信息, 请求参数：doctor==={}, content==={}", doctor, content);
        CommonResponse<MutableTriple<DoctorPo, AuditInfo, List<AuditInfo>>> res = new CommonResponse<MutableTriple<DoctorPo, AuditInfo, List<AuditInfo>>>();
        //String userIdStr = request.getHeader("userId");
        UserDetails userDetails = UserDetailsUtils.get();
        Assert.isTrue(userDetails.getId() != null, "用户id不能为空！");
        Long userId = Long.parseLong(userDetails.getId().toString());
        MutableTriple<DoctorPo, AuditInfo, List<AuditInfo>> mutableTriple = this.auditInfoService.auditDoctor(doctor, userId, content);
        res.setData(mutableTriple);
        logger.debug(res.toJsonString());
        return res;
    }

    @ApiOperation(value = "审核药师信息{\"id\":500,\"remarks\":\"测试审核\",\"status\":\"5\"}")
    @ApiCheck(check = false)
    @RequiresAuthentication
    @RequiresRoles(value = {"Audit"}, logical = Logical.AND)
    @RequestMapping(value = "/auditPharmacist.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse<MutableTriple<PharmacistPo, AuditInfo, List<AuditInfo>>> auditPharmacist(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "药师实体类") Pharmacist pharmacist, @ApiParam(value = "审核意见内容") @RequestParam String content) {
        logger.info("审核药师信息, 请求参数：pharmacist==={}, content==={}", pharmacist, content);
        CommonResponse<MutableTriple<PharmacistPo, AuditInfo, List<AuditInfo>>> res = new CommonResponse<MutableTriple<PharmacistPo, AuditInfo, List<AuditInfo>>>();
        //String userIdStr = request.getHeader("userId");
        UserDetails userDetails = UserDetailsUtils.get();
        Assert.isTrue(userDetails.getId() != null, "用户id不能为空！");
        Long userId = Long.parseLong(userDetails.getId().toString());
        MutableTriple<PharmacistPo, AuditInfo, List<AuditInfo>> mutableTriple = this.auditInfoService.auditPharmacist(pharmacist, userId, content);
        res.setData(mutableTriple);
        logger.debug(res.toJsonString());
        return res;
    }

    @ApiOperation(value = "审核公告信息{\"id\":500,\"remarks\":\"测试审核\",\"status\":\"5\"}")
    @ApiCheck(check = false)
    @RequiresAuthentication
    @RequiresRoles(value = {"Audit"}, logical = Logical.AND)
    @RequestMapping(value = "/auditMessages.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse<MutableTriple<MessagesPo, AuditInfo, List<AuditInfo>>> auditMessages(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "药师实体类") Messages messages, @ApiParam(value = "审核意见内容") @RequestParam String content) {
        logger.info("审核公告信息, 请求参数：messages==={}, content==={}", messages, content);
        CommonResponse<MutableTriple<MessagesPo, AuditInfo, List<AuditInfo>>> res = new CommonResponse<MutableTriple<MessagesPo, AuditInfo, List<AuditInfo>>>();
        //String userIdStr = request.getHeader("userId");
        UserDetails userDetails = UserDetailsUtils.get();
        Assert.isTrue(userDetails.getId() != null, "用户id不能为空！");
        Long userId = Long.parseLong(userDetails.getId().toString());
        MutableTriple<MessagesPo, AuditInfo, List<AuditInfo>> mutableTriple = this.auditInfoService.auditMessages(messages, userId, content);
        res.setData(mutableTriple);
        logger.debug(res.toJsonString());
        return res;
    }

    @ApiOperation(value = "审核新闻资讯信息{\"id\":500,\"remarks\":\"测试审核\",\"status\":\"5\"}")
    @ApiCheck(check = false)
    @RequiresAuthentication
    @RequiresRoles(value = {"Audit"}, logical = Logical.AND)
    @RequestMapping(value = "/auditNews.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse<MutableTriple<NewsPo, AuditInfo, List<AuditInfo>>> auditNews(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "药师实体类") News news, @ApiParam(value = "审核意见内容") @RequestParam String content) {
        logger.info("审核新闻资讯信息, 请求参数：news==={}, content==={}", news, content);
        CommonResponse<MutableTriple<NewsPo, AuditInfo, List<AuditInfo>>> res = new CommonResponse<MutableTriple<NewsPo, AuditInfo, List<AuditInfo>>>();
        //String userIdStr = request.getHeader("userId");
        UserDetails userDetails = UserDetailsUtils.get();
        Assert.isTrue(userDetails.getId() != null, "用户id不能为空！");
        Long userId = Long.parseLong(userDetails.getId().toString());
        MutableTriple<NewsPo, AuditInfo, List<AuditInfo>> mutableTriple = this.auditInfoService.auditNews(news, userId, content);
        res.setData(mutableTriple);
        logger.debug(res.toJsonString());
        return res;
    }

    @ApiOperation(value = "审核用户手机号信息")
    @ApiCheck(check = false)
    @RequiresAuthentication
    @RequiresRoles(value = {"Audit"}, logical = Logical.AND)
    @RequestMapping(value = "/auditMobilePhoneNumber.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse<MutableTriple<UserPo, AuditInfo, List<AuditInfo>>> auditMobilePhoneNumber(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "药师实体类") UserRequest params, @ApiParam(value = "审核意见内容") @RequestParam String content) {
        logger.info("审核用户手机号信息, 请求参数：params==={},content==={}", params, content);
        CommonResponse<MutableTriple<UserPo, AuditInfo, List<AuditInfo>>> res = new CommonResponse<MutableTriple<UserPo, AuditInfo, List<AuditInfo>>>();
        //String userIdStr = request.getHeader("userId");
        UserDetails userDetails = UserDetailsUtils.get();
        Assert.isTrue(userDetails.getId() != null, "用户id不能为空！");
        Long userId = Long.parseLong(userDetails.getId().toString());
        MutableTriple<UserPo, AuditInfo, List<AuditInfo>> mutableTriple = this.auditInfoService.auditMobilePhoneNumber(params, userId, content);
        res.setData(mutableTriple);
        logger.debug(res.toJsonString());
        return res;
    }
}
