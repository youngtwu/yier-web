package com.yier.platform.controller;


import com.yier.platform.annotation.ApiCheck;
import com.yier.platform.common.costant.ConstantAll;
import com.yier.platform.common.jsonResponse.CommonResponse;
import com.yier.platform.common.jsonResponse.ListResponse;
import com.yier.platform.common.model.Patient;
import com.yier.platform.common.model.PatientPo;
import com.yier.platform.common.requestParam.PatientRequest;
import com.yier.platform.common.util.IPUtils;
import com.yier.platform.common.util.RandomUtils;
import com.yier.platform.service.PatientService;
import com.yier.platform.service.SmsService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@ApiModel(value = "患者用户请求接口")
@RestController
@RequestMapping("/patient")
@Slf4j
public class PatientController {
    @Autowired
    private PatientService servicePatientService;
    @Autowired
    private SmsService smsService;

    @RequestMapping(value = "*", method = {RequestMethod.GET, RequestMethod.POST})
    public String allFallback() {
        return "Fallback for All Requests";
    }

    @ApiOperation(value = "修改上传用户头像图片")
    @ApiCheck(check = true)
    @RequestMapping(value = "/adjustPatientInfo.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse<String> adjustPatientInfo(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "file", required = true) MultipartFile file, @RequestParam(value = "id", required = true) Long id) { //){//}, @ApiParam(value = "上传的文件")

        CommonResponse<String> res = new CommonResponse<String>();
        Patient record = servicePatientService.adjustPatientInfo(file, id);
        res.setData(record.getAvatarUrl());
        log.info(res.toJsonString());
        return res;
    }

    @ApiOperation(value = "通过用户手机号码获取对应的手机验证码")
    @ApiCheck(check = false)
    @RequestMapping(value = "/getVerifyCode.json", method = {RequestMethod.GET})
    public CommonResponse<String> getVerifyCode(HttpServletRequest request, HttpServletResponse response,
                                                @ApiParam(value = "手机号码") @RequestParam(value = "phoneNumber", required = true) String phoneNumber,
                                                @ApiParam(value = "用途:0 表示注册[用户不能存在] 1 表示修改手机号[用户已经存在]") @RequestParam(value = "useType", required = true) String useType) {
        CommonResponse<String> result = new CommonResponse<String>();
        servicePatientService.verifyPhoneNumber(phoneNumber, useType);
        int length = 4;
        String validationCode = RandomUtils.randomNumeric(length);
        String clientIP = IPUtils.getIpAddress(request);
        smsService.sendSms(phoneNumber, ConstantAll.TYPE_ID_PATIENT, validationCode, useType, clientIP);
        result.setData("[正式接口不会显示此机密内容]目前的验证码是：" + validationCode);
        return result;
    }

    @ApiOperation(value = "用户注册接口[插入数据时的实名认证]")
    @ApiCheck(check = false)
    @RequestMapping(value = "/register.json", method = {RequestMethod.POST})
    public CommonResponse<Patient> register(@ApiParam(value = "患者信息body类") @RequestBody Patient record, HttpServletRequest request, HttpServletResponse response) {
        CommonResponse<Patient> result = new CommonResponse<Patient>();
        String osType = IPUtils.getHeaderValueByKey(request, "osType", "unknownOrH5");
        PatientPo po = servicePatientService.registerPatientSelective(record, osType);
        result.setData(po);
        return result;
    }


    @ApiOperation(value = "通过用户Id重新刷新融云token")
    @ApiCheck(check = true)
    @RequestMapping(value = "/refreshToken.json", method = {RequestMethod.POST})
    public CommonResponse<String> refreshToken(HttpServletRequest request, HttpServletResponse response,
                                               @ApiParam(value = "患者id") @RequestParam(value = "id", required = true) long id) {
        CommonResponse<String> result = new CommonResponse<String>();
        Patient record = servicePatientService.selectByPrimaryKey(id);
        String token = servicePatientService.updateImtoken(record, true);
        result.setData(token);
        return result;
    }

    @ApiOperation(value = "用户实名注册接口[更新库时的实名认证]")
    @RequestMapping(value = "/trueNameRegister.json", method = {RequestMethod.POST})
    @ApiCheck(check = true)
    public CommonResponse<Patient> trueNameRegister(@ApiParam(value = "患者信息body类") @RequestBody Patient record, HttpServletRequest request, HttpServletResponse response) {
        CommonResponse<Patient> result = new CommonResponse<Patient>();
        log.info(record.toJsonString());
        long idP = servicePatientService.updatePatientBySelective(record);
        result.setData(record);
        return result;
    }

    @ApiOperation(value = "通过手机号及验证码登录返回用户信息")
    @ApiCheck(check = false)
    @RequestMapping(value = "/loginByVerifyCode.json", method = {RequestMethod.POST})
    public CommonResponse<Patient> loginByVerifyCode(HttpServletRequest request, HttpServletResponse response,
                                                     @ApiParam(value = "手机号码") @RequestParam(value = "phoneNumber", required = true) String phoneNumber,
                                                     @ApiParam(value = "验证码") @RequestParam(value = "verifyCode", required = true) String verifyCode) {
        String osType = IPUtils.getHeaderValueByKey(request, "osType", "unknownOrH5");
        CommonResponse<Patient> result = new CommonResponse<Patient>();
        PatientRequest params = new PatientRequest();
        params.setPhoneNumber(phoneNumber);
        result.setData(servicePatientService.loginByVerifyCode(params, verifyCode, osType));
        return result;
    }

    @ApiOperation(value = "通过手机号及密码登录返回用户信息,如果传递onlyVerify=true，仅仅是验证不重新分配token等信息")
    @ApiCheck(check = false)
    @RequestMapping(value = "/loginByPassword.json", method = {RequestMethod.POST})
    public CommonResponse<PatientPo> loginByPassword(HttpServletRequest request, HttpServletResponse response,
                                                     @ApiParam(value = "手机号码") @RequestParam(value = "phoneNumber", required = true) String phoneNumber,
                                                     @ApiParam(value = "密码") @RequestParam(value = "password", required = true) String password,
                                                     @ApiParam(value = "是否只是手机密码验证") @RequestParam(value = "onlyVerify", required = false, defaultValue = "false") boolean onlyVerify) {
        String osType = IPUtils.getHeaderValueByKey(request, "osType", "unknownOrH5");
        CommonResponse<PatientPo> result = new CommonResponse<PatientPo>();
        PatientRequest params = new PatientRequest();
        params.setPhoneNumber(phoneNumber);
        result.setData(servicePatientService.loginOrVerifyPhoneNumberAndPassword(params, password, osType, onlyVerify));
        return result;
    }

    @ApiOperation(value = "4.5 验证手机号码是否存在")
    @RequestMapping(value = "/verifyPhoneNumber.json", method = {RequestMethod.POST})
    @ApiCheck(check = false)
    public CommonResponse<Patient> verifyPhoneNumber(HttpServletRequest request, HttpServletResponse response,
                                                     @ApiParam(value = "手机号码") @RequestParam(value = "phoneNumber", required = true) String phoneNumber) {
        CommonResponse<Patient> result = new CommonResponse<Patient>();
        servicePatientService.verifyPhoneNumber(phoneNumber, "0");
        return result;
    }

    @ApiOperation(value = "验证手机号码跟验证码是否注册或可找回密码")
    @ApiCheck(check = false)
    @RequestMapping(value = "/verifyPhoneNumberAndVerifyCode.json", method = {RequestMethod.GET})
    public CommonResponse<Patient> verifyPhoneNumberAndVerifyCode(HttpServletRequest request, HttpServletResponse response,
                                                                  @ApiParam(value = "手机号码") @RequestParam(value = "phoneNumber", required = true) String phoneNumber,
                                                                  @ApiParam(value = "验证码") @RequestParam(value = "verifyCode", required = true) String verifyCode,
                                                                  @ApiParam(value = "用途:0 表示注册[用户不能存在] 1 表示修改手机号[用户已经存在]") @RequestParam(value = "useType", required = true) String useType) {
        smsService.checkCode(phoneNumber, ConstantAll.TYPE_ID_PATIENT, verifyCode);//验证码验证
        CommonResponse<Patient> result = new CommonResponse<Patient>();
        List<Patient> list = servicePatientService.verifyPhoneNumber(phoneNumber, useType);
        if (StringUtils.equals("1", useType)) {
            result.setData(list.get(0));
        }
        log.debug(result.toJsonString());
        return result;
    }

    @ApiOperation(value = "验证手机号码跟验证码是否正确，然后直接更新手机号码")
    @ApiCheck(check = false)
    @RequestMapping(value = "/verifyPhoneNumberAndCodeThenSave.json", method = {RequestMethod.POST})
    public CommonResponse<Patient> verifyPhoneNumberCodeThenSave(HttpServletRequest request, HttpServletResponse response,
                                                                 @ApiParam(value = "要修改的手机号码") @RequestParam(value = "phoneNumber", required = true) String phoneNumber,
                                                                 @ApiParam(value = "验证码") @RequestParam(value = "verifyCode", required = true) String verifyCode,
                                                                 @ApiParam(value = "要修改的信息id") @RequestParam(value = "id", required = true) long id) {
        CommonResponse<Patient> result = new CommonResponse<Patient>();
        result.setData(servicePatientService.updatePhoneNumberByVerifyCodeId(phoneNumber, verifyCode, id));
        return result;
    }

    @ApiOperation(value = "通过用户Id，验证原登录密码是否正确，然后直接更新新登录密码")
    @RequestMapping(value = "/verifyPasswordNewOldThenSave.json", method = {RequestMethod.POST})
    public CommonResponse<Patient> verifyPasswordNewOldThenSave(HttpServletRequest request, HttpServletResponse response,
                                                                @ApiParam(value = "原登录密码") @RequestParam(value = "passwordOld", required = true) String passwordOld,
                                                                @ApiParam(value = "新登录密码") @RequestParam(value = "password", required = true) String password,
                                                                @ApiParam(value = "要修改的信息id") @RequestParam(value = "id", required = true) long id) {
        CommonResponse<Patient> result = new CommonResponse<Patient>();
        result.setData(servicePatientService.updatePasswordById(passwordOld, password, id));
        return result;
    }

    @ApiOperation(value = "更换手机号码输入实名信息获取目前ID")
    @ApiCheck(check = false)
    @RequestMapping(value = "/getIdByTrueInfo.json", method = {RequestMethod.GET})
    public CommonResponse<Long> getIdAndByTrueInfo(HttpServletRequest request, HttpServletResponse response,
                                                   @ApiParam(value = "手机号码") @RequestParam(value = "phoneNumber", required = true) String phoneNumber,
                                                   @ApiParam(value = "真实姓名") @RequestParam(value = "trueName", required = true) String trueName,
                                                   @ApiParam(value = "身份证号") @RequestParam(value = "idCardNo", required = true) String idCardNo) {
        CommonResponse<Long> result = new CommonResponse<Long>();
        result.setData(servicePatientService.getIdByTrueInfo(phoneNumber, trueName, idCardNo));
        return result;
    }


    @ApiOperation(value = "调整注册信息，更换手机号码，需要上传图片后端审核，后台是取DB固定颜值，不能随意传递加盐信息")
    @ApiCheck(check = false)
    @RequestMapping(value = "/adjustRegisterInfo.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse<String> adjustRegisterInfo(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "file", required = true) MultipartFile file,
                                                     @ApiParam(value = "用户Id") @RequestParam(value = "id", required = true) Long id,
                                                     @ApiParam(value = "要修改的手机号码") @RequestParam(value = "phoneNumber", required = false, defaultValue = "") String phoneNumber,
                                                     @ApiParam(value = "要修改的密码") @RequestParam(value = "password", required = false, defaultValue = "") String password) {

        CommonResponse<String> res = new CommonResponse<String>();
        Patient record = this.servicePatientService.adjustRegisterInfo(file, id, phoneNumber, password);
        res.setData(record.getCheckUrl());
        log.info(res.toJsonString());
        return res;
    }

    @ApiOperation(value = "修改更新密码等个人信息，后台是取DB固定颜值，不能随意传递加盐信息")
    @ApiCheck(check = false)
    @RequestMapping(value = "/updatePatient.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse<Patient> updatePatient(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "患者信息body类") @RequestBody Patient record) {
        CommonResponse<Patient> result = new CommonResponse<Patient>();
        servicePatientService.updateAnyInfoById(record);
        result.setData(record);
        return result;
    }


    @ApiOperation(value = "通过查询获取患者信息列表")
    @ApiCheck(check = false)
    @RequestMapping(value = "/listPatient.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ListResponse<Patient> listPatient(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "通用请求接口类") @RequestBody(required = true) PatientRequest params) {
        params.doWithSortOrStart();
        log.info("目前分页条件如下：" + params.toJsonString());
        ListResponse<Patient> res = new ListResponse<Patient>();
        List<Patient> list = this.servicePatientService.listPatient(params);
        int count = this.servicePatientService.listPatientCount(params);
        res.setData(list);
        res.setTotal(count);
        log.debug(res.toJsonString());
        return res;
    }

}
