package com.yier.platform.controller;

import com.google.common.collect.Lists;
import com.yier.platform.annotation.ApiCheck;
import com.yier.platform.common.costant.ConstantAll;
import com.yier.platform.common.jsonResponse.CommonResponse;
import com.yier.platform.common.jsonResponse.ListResponse;
import com.yier.platform.common.model.*;
import com.yier.platform.common.requestParam.BaseRequest;
import com.yier.platform.common.requestParam.ChatStateRequest;
import com.yier.platform.common.requestParam.PharmacistRequest;
import com.yier.platform.common.typeEnum.EvaluationType;
import com.yier.platform.common.util.IPUtils;
import com.yier.platform.common.util.RandomUtils;
import com.yier.platform.service.PharmacistService;
import com.yier.platform.service.SmsService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.MutableTriple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@ApiModel(value = "药师相关的请求接口")
@RestController
@RequestMapping("/pharmacist")
@Slf4j
public class PharmacistController {
    @Autowired
    private PharmacistService servicePharmacistService;
    @Autowired
    private SmsService smsService;

    @ApiOperation(value = "修改上传用户头像或近照图片")
    @ApiCheck(check = true)
    @RequestMapping(value = "/adjustPharmacistInfo.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse<String> adjustPharmacistInfo(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "file", required = true) MultipartFile file,
                                                       @ApiParam(value = "用户Id") @RequestParam(value = "id", required = true) Long id,
                                                       @ApiParam(value = "用途：avatar 表示修改头像 photo 表示上传近照") @RequestParam(value = "use", required = true) String use) {

        CommonResponse<String> res = new CommonResponse<String>();
        Pharmacist record = servicePharmacistService.adjustPharmacistInfo(file, id, use);
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
        log.info("验证条件:phoneNumber{} useType:{}",phoneNumber,useType);
        this.servicePharmacistService.verifyPhoneNumber(phoneNumber, useType);
        int length = 4;
        String validationCode = RandomUtils.randomNumeric(length);
        String clientIP = IPUtils.getIpAddress(request);
        smsService.sendSms(phoneNumber, ConstantAll.TYPE_ID_PHARMACIST, validationCode, useType, clientIP);
        result.setData("[正式接口不会显示此机密内容]目前的验证码是：" + validationCode);
        return result;
    }

    @ApiOperation(value = "用户注册接口[插入数据时的实名认证]")
    @ApiCheck(check = false)
    @RequestMapping(value = "/register.json", method = {RequestMethod.POST})
    public CommonResponse<Pharmacist> register(@RequestBody Pharmacist record, HttpServletRequest request, HttpServletResponse response) {
        CommonResponse<Pharmacist> result = new CommonResponse<Pharmacist>();
        String osType = IPUtils.getHeaderValueByKey(request, "osType", "unknownOrH5");
        PharmacistPo po = this.servicePharmacistService.insertPharmacistSelective(record, osType);
        result.setData(po);
        return result;
    }

    @ApiOperation(value = "通过用户Id重新刷新融云token")
    @ApiCheck(check = true)
    @RequestMapping(value = "/refreshToken.json", method = {RequestMethod.POST})
    public CommonResponse<String> refreshToken(HttpServletRequest request, HttpServletResponse response,
                                               @ApiParam(value = "药师id") @RequestParam(value = "id", required = true) long id) {
        CommonResponse<String> result = new CommonResponse<String>();
        Pharmacist record = servicePharmacistService.selectByPrimaryKey(id);
        String token = servicePharmacistService.updateImtoken(record, true);
        result.setData(token);
        return result;
    }

    @ApiOperation(value = "通过手机号及验证码登录返回用户信息,如果传递onlyVerify=true，仅仅是验证不重新分配token等信息")
    @ApiCheck(check = false)
    @RequestMapping(value = "/loginByVerifyCode.json", method = {RequestMethod.POST})
    public CommonResponse<PharmacistPo> loginByVerifyCode(HttpServletRequest request, HttpServletResponse response,
                                                          @ApiParam(value = "手机号码") @RequestParam(value = "phoneNumber", required = true) String phoneNumber,
                                                          @ApiParam(value = "验证码") @RequestParam(value = "verifyCode", required = true) String verifyCode) {
        CommonResponse<PharmacistPo> result = new CommonResponse<PharmacistPo>();
        PharmacistRequest params = new PharmacistRequest();
        params.setPhoneNumber(phoneNumber);
        String osType = IPUtils.getHeaderValueByKey(request, "osType", "unknownOrH5");
        result.setData(servicePharmacistService.loginByVerifyCode(params, verifyCode, osType));
        return result;
    }

    @ApiOperation(value = "通过手机号及密码登录返回用户信息")
    @ApiCheck(check = false)
    @RequestMapping(value = "/loginByPassword.json", method = {RequestMethod.POST})
    public CommonResponse<PharmacistPo> loginByPassword(HttpServletRequest request, HttpServletResponse response,
                                                        @ApiParam(value = "手机号码") @RequestParam(value = "phoneNumber", required = true) String phoneNumber,
                                                        @ApiParam(value = "密码") @RequestParam(value = "password", required = true) String password,
                                                        @ApiParam(value = "是否只是手机密码验证") @RequestParam(value = "onlyVerify", required = false, defaultValue = "false") boolean onlyVerify) {
        CommonResponse<PharmacistPo> result = new CommonResponse<PharmacistPo>();
        PharmacistRequest params = new PharmacistRequest();
        params.setPhoneNumber(phoneNumber);
        String osType = IPUtils.getHeaderValueByKey(request, "osType", "unknownOrH5");
        result.setData(servicePharmacistService.loginByPassword(params, password, osType, onlyVerify));
        return result;
    }

    @ApiOperation(value = "验证手机号码跟验证码是否可注册")
    @ApiCheck(check = false)
    @RequestMapping(value = "/verifyPhoneNumberAndVerifyCode.json", method = {RequestMethod.POST})
    public CommonResponse<PharmacistPo> verifyPhoneNumberAndVerifyCode(HttpServletRequest request, HttpServletResponse response,
                                                                       @ApiParam(value = "手机号码") @RequestParam(value = "phoneNumber", required = true) String phoneNumber,
                                                                       @ApiParam(value = "验证码") @RequestParam(value = "verifyCode", required = true) String verifyCode,
                                                                       @ApiParam(value = "用途:0 表示注册[用户不能存在] 1 表示修改手机号[用户已经存在]") @RequestParam(value = "useType", required = true) String useType) {
        smsService.checkCode(phoneNumber, ConstantAll.TYPE_ID_PHARMACIST, verifyCode);//验证码验证
        CommonResponse<PharmacistPo> result = new CommonResponse<PharmacistPo>();
        PharmacistRequest params = new PharmacistRequest();
        params.setPhoneNumber(phoneNumber);
        List<PharmacistPo> list = servicePharmacistService.verifyPhoneNumber(phoneNumber, useType);
        if (StringUtils.equals("1", useType)) {
            result.setData(list.get(0));
        }
        log.info(result.toJsonString());
        return result;
    }


    @ApiOperation(value = "更换手机号码时输入实名信息获取目前ID")
    @ApiCheck(check = false)
    @RequestMapping(value = "/getIdByTrueInfo.json", method = {RequestMethod.GET})
    public CommonResponse<Long> getIdAndByTrueInfo(HttpServletRequest request, HttpServletResponse response,
                                                   @ApiParam(value = "手机号码") @RequestParam(value = "phoneNumber", required = true) String phoneNumber,
                                                   @ApiParam(value = "真实姓名") @RequestParam(value = "trueName", required = true) String trueName,
                                                   @ApiParam(value = "身份证号") @RequestParam(value = "idCardNo", required = true) String idCardNo) {
        CommonResponse<Long> result = new CommonResponse<Long>();
        result.setData(servicePharmacistService.getIdByTrueInfo(phoneNumber, trueName, idCardNo));
        return result;
    }


    @ApiOperation(value = "验证手机号码跟验证码是否正确，然后直接更新手机号码")
    @ApiCheck(check = false)
    @RequestMapping(value = "/verifyPhoneNumberAndCodeThenSave.json", method = {RequestMethod.POST})
    public CommonResponse<Pharmacist> verifyPhoneNumberCodeThenSave(HttpServletRequest request, HttpServletResponse response,
                                                                    @ApiParam(value = "要修改的手机号码") @RequestParam(value = "phoneNumber", required = true) String phoneNumber,
                                                                    @ApiParam(value = "验证码") @RequestParam(value = "verifyCode", required = true) String verifyCode,
                                                                    @ApiParam(value = "要修改的信息id") @RequestParam(value = "id", required = true) long id) {
        CommonResponse<Pharmacist> result = new CommonResponse<Pharmacist>();
        result.setData(servicePharmacistService.updatePhoneNumberByVerifyCodeId(phoneNumber, verifyCode, id));
        return result;
    }

    @ApiOperation(value = "通过用户Id，验证原登录密码是否正确，然后直接更新新登录密码")
    @ApiCheck(check = false)
    @RequestMapping(value = "/verifyPasswordNewOldThenSave.json", method = {RequestMethod.POST})
    public CommonResponse<Pharmacist> verifyPasswordNewOldThenSave(HttpServletRequest request, HttpServletResponse response,
                                                                   @ApiParam(value = "原登录密码") @RequestParam(value = "passwordOld", required = true) String passwordOld,
                                                                   @ApiParam(value = "新登录密码") @RequestParam(value = "password", required = true) String password,
                                                                   @ApiParam(value = "要修改的信息id") @RequestParam(value = "id", required = true) long id) {
        CommonResponse<Pharmacist> result = new CommonResponse<Pharmacist>();
        result.setData(servicePharmacistService.updatePasswordById(passwordOld, password, id));
        return result;
    }


    @ApiOperation(value = "调整注册信息，更换手机号码，需要上传图片后端审核")
    @ApiCheck(check = false)
    @RequestMapping(value = "/adjustRegisterInfo.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse<String> adjustRegisterInfo(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "图片文件") @RequestParam(value = "file", required = true) MultipartFile file,
                                                     @ApiParam(value = "要修改的用户Id") @RequestParam(value = "id", required = true) Long id,
                                                     @ApiParam(value = "要修改的手机号码") @RequestParam(value = "phoneNumber", required = false, defaultValue = "") String phoneNumber,
                                                     @ApiParam(value = "要修改的密码") @RequestParam(value = "password", required = false, defaultValue = "") String password,
                                                     @ApiParam(value = "是否要验证") @RequestParam(value = "check", required = false, defaultValue = "true") boolean check) {
        CommonResponse<String> res = new CommonResponse<String>();
        Pharmacist record = this.servicePharmacistService.adjustRegisterInfo(file, id, phoneNumber, password,check);
        res.setData(record.getCheckUrl());
        log.info(res.toJsonString());
        return res;
    }

    @ApiOperation(value = "修改更新密码等信息，除图片信息")
    @ApiCheck(check = false)
    @RequestMapping(value = "/updatePharmacist.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse<Pharmacist> updatePharmacist(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "药师body信息") @RequestBody Pharmacist record) {
        CommonResponse<Pharmacist> result = new CommonResponse<Pharmacist>();
        this.servicePharmacistService.updateAnyInfoById(record);
        result.setData(record);
        return result;
    }

    @ApiOperation(value = "开通或关闭药师聊天设定对应的影响：推送，延迟，提醒等")
    @ApiCheck(check = true)
    @RequestMapping(value = "/setPharmacistChat.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse<String> setPharmacistChat(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "聊天状态body信息") @RequestBody ChatStateRequest params) {
        CommonResponse<String> result = new CommonResponse<String>();
        result.setData(this.servicePharmacistService.updateChatState(params));
        return result;
    }

    @ApiOperation(value = "获取我的按照医生或药师分组评价列表")
    @ApiCheck(check = false)
    @RequestMapping(value = "/getEvaluationWithGroupPharmacistContentList.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ListResponse<PharmacistEvaluationPo> getEvaluationWithGroupPharmacistContentList(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "药师请求接口类") @RequestBody PharmacistRequest params) {
        params.doWithSortOrStart();
        ListResponse<PharmacistEvaluationPo> res = new ListResponse<PharmacistEvaluationPo>();
        res.setData(this.servicePharmacistService.listEvaluationWithGroupPharmacistContent(params));
        res.setTotal(this.servicePharmacistService.listEvaluationWithGroupPharmacistContentCount(params));
        log.debug(res.toJsonString());
        return res;
    }

    @ApiOperation(value = "获取我的药师评价列表")
    @ApiCheck(check = true)
    @RequestMapping(value = "/getEvaluationWithPharmacistContentList.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ListResponse<PharmacistEvaluationPo> getEvaluationWithPharmacistContentList(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "药师请求接口类") @RequestBody PharmacistRequest params) {
        params.doWithSortOrStart();
        ListResponse<PharmacistEvaluationPo> res = new ListResponse<PharmacistEvaluationPo>();
        res.setData(this.servicePharmacistService.listEvaluationWithPharmacistContent(params));
        res.setTotal(this.servicePharmacistService.listEvaluationWithPharmacistContentCount(params));
        log.debug(res.toJsonString());
        return res;
    }

    @ApiOperation(value = "获取ID对应的药师评价")
    @ApiCheck(check = true)
    @RequestMapping(value = "/getPharmacistEvaluationPoWithPharmacistById.json", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse<PharmacistEvaluationPo> getPharmacistEvaluationPoWithPharmacistById(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "评价ID") @RequestParam(value = "id", required = true) Long id) {
        CommonResponse<PharmacistEvaluationPo> res = new CommonResponse<PharmacistEvaluationPo>();
        res.setData(this.servicePharmacistService.getPharmacistEvaluationPoWithPharmacistById(id));
        log.info(res.toJsonString());
        return res;
    }

    @ApiOperation(value = "获取药师评价列表")
    @ApiCheck(check = true)
    @RequestMapping(value = "/getPharmacistEvaluationPoList.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ListResponse<PharmacistEvaluationPo> getPharmacistEvaluationPoList(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "药师请求接口类") @RequestBody PharmacistRequest params) {
        params.doWithSortOrStart();
        ListResponse<PharmacistEvaluationPo> res = new ListResponse<PharmacistEvaluationPo>();
        res.setData(this.servicePharmacistService.listPharmacistEvaluationPo(params));
        res.setTotal(this.servicePharmacistService.listPharmacistEvaluationPoCount(params));
        log.debug(res.toJsonString());
        return res;
    }

    @ApiOperation(value = "获取药师评价类型列表")
    @ApiCheck(check = true)
    @RequestMapping(value = "/getEvaluationTypeList.json", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ListResponse<MutableTriple<Integer, String, String>> getEvaluationTypeList(HttpServletRequest request, HttpServletResponse response) {
        ListResponse<MutableTriple<Integer, String, String>> res = new ListResponse<MutableTriple<Integer, String, String>>();
        List<MutableTriple<Integer, String, String>> list = Lists.newArrayList();
        for (EvaluationType item : EvaluationType.values()) {
            if (item.getType() == "-1") {
                continue;
            }
            list.add(new MutableTriple(item.getType(), item.getDesc(), "其他支持多选"));
        }
        res.setData(list);
        log.debug(res.toJsonString());
        return res;
    }


    @ApiOperation(value = "通过id获取药师信息，包括评价情况")
    @ApiCheck(check = true)
    @RequestMapping(value = "/getPharmacistPoEvaluationCase.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)//produces = MediaType.APPLICATION_JSON_UTF8_VALUE @ResponseBody响应结果类型及编码是"application/json;charset=UTF-8" produces两个好处：一个是浏览器查看方便（json自动格式化，带搜索），另一个可以防止中文乱码。
    public CommonResponse<PharmacistPo> getPharmacistPoEvaluationCase(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "药师请求接口类") @RequestBody PharmacistRequest params) {
        params.doWithSortOrStart();
        log.info("getPharmacistPoEvaluationCase.json ，包括评价情况,params:{}",params);
        CommonResponse<PharmacistPo> res = new CommonResponse<PharmacistPo>();
        PharmacistPo p = servicePharmacistService.getPharmacistPoWithHospitalDepartmentAndEvaluationList(params);
        res.setData(p);
        log.info(res.toJsonString());
        return res;
    }


    @ApiOperation(value = "插入药师评价")
    @ApiCheck(check = true)
    @RequestMapping(value = "/insertPharmacistEvaluation.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse<PharmacistEvaluation> insertPharmacistEvaluation(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "药师body信息") @RequestBody PharmacistEvaluation record) {
        CommonResponse<PharmacistEvaluation> res = new CommonResponse<PharmacistEvaluation>();
        log.info("目前插入条件是：{}", record);
        servicePharmacistService.insertPharmacistEvaluation(record);
        res.setData(record);
        log.info(res.toJsonString());
        return res;
    }


    @ApiOperation(value = "通过条件查询获取药师信息列表")
    @ApiCheck(check = true)
    @RequestMapping(value = "/getPharmacistPoList.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ListResponse<PharmacistPo> getPharmacistPoList(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "药师请求接口类") @RequestBody PharmacistRequest params) {
        params.doWithSortOrStart();
        log.info("通过条件查询获取药师信息列表,目前分页条件如下：{}",params);
        ListResponse<PharmacistPo> res = new ListResponse<PharmacistPo>();
        List<PharmacistPo> list = servicePharmacistService.listPharmacistPo(params);
        int count = servicePharmacistService.listPharmacistPoCount(params);
        res.setData(list);
        res.setTotal(count);
        log.debug(res.toJsonString());
        return res;
    }

    @ApiOperation(value = "通过id获取药师信息，包括职位，职业点")
    @ApiCheck(check = false)
    @RequestMapping(value = "/getPharmacistPoById.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse<PharmacistPo> getPharmacistPoById(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "药师请求接口类") @RequestBody PharmacistRequest params) {
        CommonResponse<PharmacistPo> res = new CommonResponse<PharmacistPo>();
        log.info("通过id获取药师信息查询条件:{}", params);
        PharmacistPo p = servicePharmacistService.getPharmacistPoById(params);
        res.setData(p);
        log.info("通过id获取药师信息查询结果:{}", res);
        return res;
    }


    @ApiOperation(value = "通过id获取药师信息，包括职位，职业点")
    @ApiCheck(check = true)
    @RequestMapping(value = "/selectByPrimaryKey.json", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse<Pharmacist> selectByPrimaryKey(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "药师ID") @RequestParam(value = "id", required = true) Long id) {
        CommonResponse<Pharmacist> res = new CommonResponse<Pharmacist>();
        Pharmacist p = servicePharmacistService.selectByPrimaryKey(id);
        Assert.isTrue(p != null, "不存在该药师");
        res.setData(p);
        log.info(res.toJsonString());
        return res;
    }

    @ApiOperation(value = "通过条件查询获取药师职称信息列表")
    @ApiCheck(check = false)
    @RequestMapping(value = "/getPharmacistTitleList.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ListResponse<PharmacistTitle> getPharmacistTitleList(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "基本请求接口类") @RequestBody BaseRequest params) {
        params.doWithSortOrStart();
        log.info("目前分页条件如下：" + params.toJsonString());
        ListResponse<PharmacistTitle> res = new ListResponse<PharmacistTitle>();
        List<PharmacistTitle> list = servicePharmacistService.listPharmacistTitle(params);
        int count = servicePharmacistService.listPharmacistTitleCount(params);
        res.setData(list);
        res.setTotal(count);
        log.debug(res.toJsonString());
        return res;
    }
}
