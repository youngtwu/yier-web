package com.yier.platform.controller;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.collect.Lists;
import com.yier.platform.annotation.ApiCheck;
import com.yier.platform.common.costant.ConstantAll;
import com.yier.platform.common.jsonResponse.CommonResponse;
import com.yier.platform.common.jsonResponse.ListResponse;
import com.yier.platform.common.model.*;
import com.yier.platform.common.po.*;
import com.yier.platform.common.redis.RedisSystemParameterCache;
import com.yier.platform.common.requestParam.*;
import com.yier.platform.common.typeEnum.AppType;
import com.yier.platform.common.typeEnum.RefreshRedisFlag;
import com.yier.platform.common.typeEnum.SystemParameterKey;
import com.yier.platform.common.util.*;
import com.yier.platform.conf.ApplicationConfig;
import com.yier.platform.service.amqp.RabbitService;
import com.yier.platform.service.feign.RemotePharmacyService;
import com.yier.platform.event.AppPushEvent;
import com.yier.platform.event.EventPublisher;
import com.yier.platform.event.RefreshRedisCacheEvent;
import com.yier.platform.service.*;
import com.yier.platform.service.feign.RemoteService;
import com.yier.platform.service.medical.DrugSendOrderService;
import com.yier.platform.service.medical.DrugTakeOrderService;
import com.yier.platform.service.medical.MedicalDateService;
import com.yier.platform.service.medical.MedicalPrescriptionService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.concurrent.TimeUnit;

@ApiModel(value = "公用的请求接口")
@RestController
@RequestMapping("/test")
@Slf4j
public class TestTempController {
    private static final String JuHe_CarId_App = "635b9cbebf4efde2cb14f6b806fd26b7";//身份证
    @Autowired
    private ImagesService serviceImagesService;
    @Autowired
    private SystemParametersService serviceSystemParametersService;
    @Autowired
    private CommonService serviceCommonService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private Uploader uploader;
    @Autowired
    private SmsService smsService;
    @Autowired
    private ApplicationConfig applicationConfig;//来自于可配置的外部属性文件
    @Autowired
    private RedisSystemParameterCache redisSystemParameterCache;//redis测试
    @Autowired
    private EventPublisher eventPublisher;
    @Autowired
    private ChatRecordService serviceChatRecordService;
    @Autowired
    private ApplicationContext context;
    @Autowired
    private AppPushService appPushService;
    @Autowired
    private JwtTokenService jwtTokenService;
    @Autowired
    private PracticePointTimeService servicePracticePointTimeService;
    @Autowired
    private ContentDetailService serviceContentDetailService;
    @Autowired
    private UserRoleService userRoleService;//用户角色关联表
    @Autowired
    private RemotePharmacyService remotePharmacyService;
    @Autowired
    private MedicalDateService medicalDateService;
    @Autowired
    private PharmacyService pharmacyService;
    @Autowired
    private RemoteService remoteService;
    @Autowired
    private DrugSendOrderService drugSendOrderService;
    @Autowired
    private DrugTakeOrderService drugTakeOrderService;
    @Autowired
    private MedicalPrescriptionService medicalPrescriptionService;
    @Autowired
    private RabbitService rabbitService;

    @ApiOperation(value = "测试激光推送")
    @ApiCheck(check = false)
    @RequestMapping(value = "/rabbit.json", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse<String> rabbit(HttpServletRequest request, HttpServletResponse response) {
        CommonResponse<String> res = new CommonResponse<String>();
        OrderDelayQueue orderDelayQueue = new OrderDelayQueue();
        orderDelayQueue.setCaseInfo(ConstantAll.CASE_INFO_ORDER_MEDICAL_PAYMENT_TIME);
        orderDelayQueue.setOrderId(0L);
        orderDelayQueue.setRedisKey("测试延迟队列");
        orderDelayQueue.setCurrentTime(new Date());
        rabbitService.sendDelayMessage(orderDelayQueue,(int)(10*ConstantAll.VALUE_MICROSECOND_1000 ));
        log.info("等待延迟队列:{}",orderDelayQueue);
        return res;
    }

    @ApiOperation(value = "测试激光推送")
    @ApiCheck(check = false)
    @RequestMapping(value = "/testPush.json", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse<String> testPush(HttpServletRequest request, HttpServletResponse response) {
        CommonResponse<String> res = new CommonResponse<String>();
        appPushService.pushAppTest();
        log.info(res.toJsonString());
        return res;
    }

    @ApiOperation(value = "送药，取药测试")
    @ApiCheck(check = false)
    @RequestMapping(value = "/acceptPrescription.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ListResponse<OrderDrugs> acceptPrescription(HttpServletRequest request, HttpServletResponse response,@ApiParam(value = "基本请求接口类") @RequestBody OrderDrugsRequest parms){
        parms.doWithSortOrStart();

        MedicalPrescriptionRequest params = new MedicalPrescriptionRequest();
        params.setMedicalPrescriptionId(34L);
        MedicalPrescriptionPo medicalPrescription = this.medicalPrescriptionService.getMedicalPrescriptionPoById(params);
        this.remoteService.acceptPrescription(medicalPrescription,null);//[上报审核已通过处方]

        ListResponse<OrderDrugs> res = new ListResponse<OrderDrugs>();
        log.debug(res.toJsonString());
        return res;
    }



    @ApiOperation(value = "送药，取药测试")
    @ApiCheck(check = false)
    @RequestMapping(value = "/sendTakeDrugs.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ListResponse<OrderDrugs> sendTakeDrugs(HttpServletRequest request, HttpServletResponse response,@ApiParam(value = "基本请求接口类") @RequestBody OrderDrugsRequest parms){
        parms.doWithSortOrStart();
        ListResponse<OrderDrugs> res = new ListResponse<OrderDrugs>();

        res.setData(remoteService.getListDrugs(parms));
        log.debug(res.toJsonString());
        return res;
    }

    @ApiOperation(value = "    根据条件查询药库携带距离信息列表")
    @ApiCheck(check = false)
    @RequestMapping(value = "/getListDrugs.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ListResponse<OrderDrugs> getListDrugs(HttpServletRequest request, HttpServletResponse response,@ApiParam(value = "基本请求接口类") @RequestBody OrderDrugsRequest parms){
        parms.doWithSortOrStart();
        ListResponse<OrderDrugs> res = new ListResponse<OrderDrugs>();

        res.setData(remoteService.getListDrugs(parms));
        log.debug(res.toJsonString());
        return res;
    }


    @ApiOperation(value = "    根据条件查询药库携带距离信息列表")
    @ApiCheck(check = false)
    @RequestMapping(value = "/listPharmacyPo.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ListResponse<PharmacyPo> listPharmacyPo(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "基本请求接口类") @RequestBody HospitalRequest params) {
        ListResponse<PharmacyPo> res = new ListResponse<PharmacyPo>();
        res.setData(pharmacyService.listPharmacyPo(params));
        res.setTotal(pharmacyService.listPharmacyPoCount(params));
        log.debug(res.toJsonString());
        return res;
    }
    @ApiOperation(value = "    根据条件查询药库携带距离信息列表")
    @ApiCheck(check = false)
    @RequestMapping(value = "/listPharmacy.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ListResponse<Pharmacy> listPharmacy(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "基本请求接口类") @RequestBody HospitalRequest params) {
        ListResponse<Pharmacy> res = new ListResponse<Pharmacy>();
        res.setData(pharmacyService.listPharmacy(params));
        res.setTotal(pharmacyService.listPharmacyCount(params));
        log.debug(res.toJsonString());
        return res;
    }

    @ApiOperation(value = "用于定期将redis缓存中的数据清空，让数据库重新加载")
    @ApiCheck(check = false)
    @RequestMapping(value = "/deleteRedisCache.json", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void deleteRedisCache(HttpServletRequest request, HttpServletResponse response,@ApiParam(value = "使用方向")  @RequestParam(value = "delete", required = false,defaultValue = "0") String delete) {
        log.info(">>用于定期将redis缓存中的数据清空，让数据库重新加载");
        List<String> list = Lists.newArrayList();
        Set<String> chufang_ids = redisService.getRedisKeysByPattern(ConstantAll.REDIS_PRESCRIPTION_ORDER+"*");
        chufang_ids.forEach(id->{
            list.add(id);
        });
        Set<String> a_ids = redisService.getRedisKeysByPattern(ConstantAll.REDIS_PRESCRIPTION_CHECK_A_ORDER_ID+"*");
        a_ids.forEach(id->{
            list.add(id);
        });
        Set<String> b_ids = redisService.getRedisKeysByPattern(ConstantAll.REDIS_PRESCRIPTION_CHECK_B_ORDER_ID+"*");
        b_ids.forEach(id->{
            list.add(id);
        });
        Set<String> c_ids = redisService.getRedisKeysByPattern(ConstantAll.REDIS_PRESCRIPTION_CHECK_C_ORDER_ID+"*");
        c_ids.forEach(id->{
            list.add(id);
        });
        Set<String> d_ids = redisService.getRedisKeysByPattern(ConstantAll.REDIS_PRESCRIPTION_CHECK_D_ORDER_ID+"*");
        d_ids.forEach(id->{
            list.add(id);
        });
        Set<String> e_ids = redisService.getRedisKeysByPattern(ConstantAll.REDIS_PRESCRIPTION_CHECK_E_ORDER_ID+"*");
        e_ids.forEach(id->{
            list.add(id);
        });
        Set<String> appoint_ids = redisService.getRedisKeysByPattern(ConstantAll.REDIS_DOCTOR_APPOINT_LOCK_ORDER+"*");
        appoint_ids.forEach(id->{
            list.add(id);
        });

        for (String key : list) {
            log.info("目前的key:{} 在缓存中是否存在:{}", key, redisService.hasRedisKey(key,false));
        }
        if(delete.equalsIgnoreCase("1")){
            Long result = redisService.deleteRedisByKeys(list,false);
            log.info("<<用于定期将redis缓存中的数据清空 共删除:{}", result);
        }

    }



    @ApiOperation(value = "测试UUID")
    @ApiCheck(check = false)
    @RequestMapping(value = "/unLockForCondition.json", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse<String> unLockForCondition(HttpServletRequest request, HttpServletResponse response) {
        CommonResponse<String> res = new CommonResponse<String>();
        medicalDateService.unLockForCondition(new Date());
        log.debug(res.toJsonString());
        return res;
    }
    @ApiOperation(value = "测试UUID")
    @ApiCheck(check = false)
    @RequestMapping(value = "/lockForCondition.json", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse<String> lockForCondition(HttpServletRequest request, HttpServletResponse response) {
        CommonResponse<String> res = new CommonResponse<String>();
        String orderNo = OrderIdUtils.genOrderIdWithUUID("TTT");
        //medicalDateService.lockForCondition(new Date(),2L,ConstantAll.REDIS_DOCTOR_APPOINT_LOCK_ORDER+2+ConstantAll.REDIS_DOCTOR_APPOINT_LOCK_ORDER_PART_ORDER+orderNo,"锁定订单"+orderNo);
        log.debug(res.toJsonString());
        return res;
    }





    @ApiOperation(value = "测试UUID")
    @ApiCheck(check = false)
    @RequestMapping(value = "/remotePharmacyService.json", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse<String> remotePharmacyService(HttpServletRequest request, HttpServletResponse response) {
        CommonResponse<String> res = new CommonResponse<String>();
//        String patientId = "410423197808083511";
//        Map<String, Object>  result = this.remotePharmacyService.listPrescriptions(patientId);
//        log.info("result:{}",result);
//        Map<String, Object>  result1 = this.remotePharmacyService.listDrugs("100001","赖氨",0,10);
        Map<String, Object> result1 = null;
        try {
            result1 = this.remotePharmacyService.listDrugs("100001", "赖氨", 0, 10);
            log.info("第一返回结果:result0000000:{}",result1);
        }catch (Exception e){
            log.info("错误信息:"+e.getMessage(),e);
        }
        log.info("第二返回结果result1:{}",result1);
        res.setData("UUID:"+UUID.randomUUID().toString().replace("-","").toUpperCase());
        log.debug(res.toJsonString());
        return res;
    }

    @ApiOperation(value = "测试UUID")
    @ApiCheck(check = false)
    @RequestMapping(value = "/getUUID.json", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse<String> getUUID(HttpServletRequest request, HttpServletResponse response) {
        CommonResponse<String> res = new CommonResponse<String>();
        res.setData("UUID:"+UUID.randomUUID().toString().replace("-","").toUpperCase());
        log.debug(res.toJsonString());
        return res;
    }
    @ApiOperation(value = "通过条件查询获取用户角色信息列表")
    @ApiCheck(check = false)
    @RequestMapping(value = "/getListAllUserRolePo.json", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ListResponse<UserRolePo> getListAllUserRolePo(HttpServletRequest request, HttpServletResponse response) {
        ListResponse<UserRolePo> res = new ListResponse<UserRolePo>();
        List<UserRolePo> list = this.userRoleService.getListAllUserRolePo();
        res.setData(list);
        res.setTotal(list.size());
        log.debug(res.toJsonString());
        return res;
    }
    @ApiOperation(value = "通过条件查询获取用户角色信息列表")
    @ApiCheck(check = false)
    @RequestMapping(value = "/updateListAllUserRolePo.json", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse<String> updateListAllUserRolePo(HttpServletRequest request, HttpServletResponse response) {
        CommonResponse<String> res = new CommonResponse<String>();
        this.userRoleService.updateListAllUserRolePo();
        res.setData("已经清楚");
        log.debug(res.toJsonString());
        return res;
    }

    @ApiOperation(value = "通过条件查询获取用户角色信息列表")
    @ApiCheck(check = false)
    @RequestMapping(value = "/testUserRolePoList.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ListResponse<UserRolePo> testUserRolePoList(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "基本请求接口类") @RequestBody UserRoleRequest params) {
        ListResponse<UserRolePo> res = new ListResponse<UserRolePo>();
        List<UserRolePo> list = this.userRoleService.listUserRolePo(params);
        int count = this.userRoleService.listUserRolePoCount(params);
        res.setData(list);
        res.setTotal(count);
        log.debug(res.toJsonString());
        return res;
    }



    @ApiOperation(value = "通过身份证号码获取对应的身份信息")
    //@ApiCheck(check = true)
    @RequestMapping(value = "/getIdCardClass.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse<IdCardClass> getIdCardClass(HttpServletRequest request, HttpServletResponse response,
                                                      @ApiParam(value = "身份证号")@RequestParam(value = "idCard", required = true)  String idCard,
                                                      @ApiParam(value = "使用方向")  @RequestParam(value = "useType", required = false,defaultValue = "0") String useType){
        CommonResponse<IdCardClass> res = new CommonResponse<IdCardClass>();
        res.setData(this.serviceCommonService.getIdCardClass(idCard));
        log.info(res.toJsonString());
        return res;
    }

    @ApiOperation(value = "获取设备异常信息")
    //@ApiCheck(check = true)
    @RequestMapping(value = "/getDeviceInfoException.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse<DeviceInfoException> getDeviceInfoException(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "设备异常信息") Long id){
        CommonResponse<DeviceInfoException> res = new CommonResponse<DeviceInfoException>();
        res.setData(this.serviceCommonService.selectDeviceInfoExceptionByPrimaryKey(id));
        log.info(res.toJsonString());
        return res;
    }

    @ApiOperation(value = "根据上传图片，通过fast上传图片")
    @ApiCheck(check = true)
    @RequestMapping(value = "/uploadImageToUrl.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    //@PostMapping(value = "/uploadImageToUrl.json",  produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse<String> uploadImageToUrl(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "file", required = true) MultipartFile file) { //){//}, @ApiParam(value = "上传的文件")

        CommonResponse<String> res = new CommonResponse<String>();
        if (file != null) {
            String url = uploader.upload(file);
            res.setData(url);
        }
        log.info(res.toJsonString());
        return res;
    }

    @ApiOperation(value = "APP患者端通栏头号标题信息列表")
    @RequestMapping(value = "/getAppBannerInfo1.json", method = {RequestMethod.GET})
    public CommonResponse<ImageList> getAppBannerInfo1(HttpServletRequest request, HttpServletResponse response) {
        String typeId = IPUtils.getHeaderValueByKey(request,"typeId",null);
        log.info("目前的端口id是："+ typeId);
        Assert.isTrue(StringUtils.isNotBlank(typeId), "请传递有效的应用端口typeId(1表示患者端2表示医生端3表示药师端)");
        CommonResponse<ImageList> result = new CommonResponse<ImageList>();
        String info = null;
        if(StringUtils.equalsIgnoreCase(typeId,"1")){//患者端广告图
            info = serviceSystemParametersService.getParameter(SystemParameterKey.APP_PATIENT_BANNER_IMAGES_JSON);
        }
        else if(StringUtils.equalsIgnoreCase(typeId,"2")){//医生端广告图
            info = serviceSystemParametersService.getParameter(SystemParameterKey.APP_DOCTOR_BANNER_IMAGES_JSON);
        }
        else if(StringUtils.equalsIgnoreCase(typeId,"3")){//药师端广告图
            info = serviceSystemParametersService.getParameter(SystemParameterKey.APP_PHARMACIST_BANNER_IMAGES_JSON);
        }
        if (StringUtils.isNotBlank(info)) {
            ImageList list = JsonUtils.fromJson(info, ImageList.class);
            result.setData(list);
        }
        return result;
    }


    @ApiOperation(value = "验证并获取应用端APP信息，目前可以获取广告信息列表,验证目前安卓的版本号，返回是否是登录用户")
    @RequestMapping(value = "/getAppInfo1.json", method = {RequestMethod.GET})
    public CommonResponse<VerificationInfo> getAppADInfo1(HttpServletRequest request, HttpServletResponse response,
//                                                         @ApiParam(value = "应用key") @RequestParam(value = "appKey", required = false) String appKey,
//                                                         @ApiParam(value = "随机数") @RequestParam(value = "nonce", required = false) String nonce,
//                                                         @ApiParam(value = "时间戳") @RequestParam(value = "timestamp", required = false) String timestamp,
//                                                         @ApiParam(value = "签名") @RequestParam(value = "signature", required = false) String signature,
//                                                         @ApiParam(value = "端口类型") @RequestParam(value = "typeId",defaultValue = "0",required = true) long typeId,
//                                                         @ApiParam(value = "操作来源") @RequestParam(value = "osType", required = true) String osType,
                                                          @ApiParam(value = "目前版本号") @RequestParam(value = "version",defaultValue = "0",required = true) String version) {
        log.debug("目前版本version:---------->{}",version);
        long versionLong = Long.parseLong(version);
        String appKey = request.getHeader("appKey");
        if(StringUtils.isBlank(appKey)){
            appKey = request.getParameter("appKey");
        }
        String nonce = request.getHeader("nonce");
        if(StringUtils.isBlank(nonce)){
            nonce = request.getParameter("nonce");
        }
        String timestamp = request.getHeader("timestamp");
        if(StringUtils.isBlank(timestamp)){
            timestamp = request.getParameter("timestamp");
        }
        String signature = request.getHeader("signature");
        if(StringUtils.isBlank(signature)){
            signature = request.getParameter("signature");
        }
        String typeIdString =IPUtils.getHeaderValueByKey(request,"typeId",null);
        log.info("typeIdString:{}",typeIdString);
        long typeId = Long.parseLong(typeIdString);
        String osType = IPUtils.getHeaderValueByKey(request,"osType",null);
        Assert.isTrue(StringUtils.isNotBlank(osType), "请传递有效的操作类型osType");

        log.info("验证及传递的信息是：appKey - {} - nonce - {} - timestamp - {} - signature - {} - typeId - {} - osType - {} - version - {}",appKey,nonce,timestamp,signature,typeId,osType,version);
        CommonResponse<VerificationInfo> result = new CommonResponse<VerificationInfo>();
        VerificationInfo verificationInfo = new VerificationInfo();
        String info = null;
        if(typeId == 1){//患者端广告图
            info = serviceSystemParametersService.getParameter(SystemParameterKey.APP_PATIENT_AD_IMAGES_JSON);
            if (StringUtils.equalsIgnoreCase(osType, "Android")) {
                if (versionLong < applicationConfig.getAndroid().getVersionForPatient()) {
                    String path = applicationConfig.getAndroid().getVersionPathForPatient();
                    verificationInfo.setNewVersionPath(path);
                }
            }
        }
        else if(typeId == 2){//医生端广告图
            info = serviceSystemParametersService.getParameter(SystemParameterKey.APP_DOCTOR_AD_IMAGES_JSON);
            if (StringUtils.equalsIgnoreCase(osType, "Android")) {
                if (versionLong < applicationConfig.getAndroid().getVersionForDoctor()) {
                    String path = applicationConfig.getAndroid().getVersionPathForDoctor();
                    verificationInfo.setNewVersionPath(path);
                }
            }
        }
        else if(typeId == 3){//药师端广告图
            info = serviceSystemParametersService.getParameter(SystemParameterKey.APP_PHARMACIST_AD_IMAGES_JSON);
            if (StringUtils.equalsIgnoreCase(osType, "Android")) {
                if (versionLong < applicationConfig.getAndroid().getVersionForPharmacist()) {
                    String path = applicationConfig.getAndroid().getVersionPathForPharmacist();
                    verificationInfo.setNewVersionPath(path);
                }
            }
        }
        if (StringUtils.isNotBlank(info)) {
            ImageList list = JsonUtils.fromJson(info, ImageList.class);
            /////verificationInfo.setImagesList(list.getImagesList());
        }
        if (StringUtils.isBlank(appKey) || StringUtils.isBlank(nonce) || StringUtils.isBlank(timestamp) || StringUtils.isBlank(signature)) {
            verificationInfo.setLoginInfo("loginFail");
        } else {
            verificationInfo.setLoginInfo("loginSuccess");
        }
        result.setData(verificationInfo);
        log.info("验证并获取应用端APP信息是：{}",verificationInfo.toJsonString());
        return result;
    }


    @ApiOperation(value = "通过身份证号，获取相关的信息")
    @RequestMapping(value = "/getIdCardNoInfo.json", method = {RequestMethod.GET})
    public CommonResponse<String> getVerifyCode(HttpServletRequest request, HttpServletResponse response,
                                                @ApiParam(value = "身份证号") @RequestParam(value = "idCardNo", required = true) String idCardNo) {
        CommonResponse<String> result = new CommonResponse<String>();
        Assert.isTrue(idCardNo.length() == 15 || idCardNo.length() == 18, "身份证号长度无效");
        Map<String, String> paramsWithAppKey = new HashMap<>();
        String url = "http://apis.juhe.cn/idcard/index?key=" + JuHe_CarId_App + "&cardno=" + idCardNo;
        String getResult = HttpUtils.get(url, paramsWithAppKey);

        log.info("身份证类URL结果------------->" + getResult);
        IdCardClass idCardClass = JsonUtils.fromJson(getResult, IdCardClass.class);
        log.info("身份证类转换类------------->" + idCardClass.toJsonString());

        if (idCardClass.getResult() != null) {
            String sex = idCardClass.getResult().getSex();
            String birthday = idCardClass.getResult().getBirthday();
            Date birthdayDate = Utils.parseDate(birthday, "yyyy年MM月dd日");
        }

        log.info(getResult);
        result.setData(getResult);
        return result;
    }

    @ApiCheck(check = false)
    @RequestMapping(value = "/getImageList.json", method = {RequestMethod.GET})
    public CommonResponse<ImageList> getImageList(HttpServletRequest request, HttpServletResponse response) {
        CommonResponse<ImageList> result = new CommonResponse<ImageList>();
        ImageList listImages = new ImageList();
        List<Image> list = Lists.newArrayList();
        listImages.setImagesList(list);
        Image image = new Image("名字", "描述", "http://img.zcool.cn/community/0142135541fe180000019ae9b8cf86.jpg@1280w_1l_2o_100sh.png", "https://www.baidu.com/index.php?tn=monline_3_dg");
        list.add(image);
        image = new Image("介绍", "具体描述", "http://img.zcool.cn/community/01690955496f930000019ae92f3a4e.jpg@2o.jpg", "http://search.maven.org/#artifactdetails%7Ccom.fasterxml.jackson.core%7Cjackson-core%7C2.9.6%7Cbundle");
        list.add(image);
        image = new Image("介绍1", "具体描述2", "http://img.zcool.cn/community/01be0e5901d210a801214550082175.jpg@1280w_1l_2o_100sh.png", "https://tieba.baidu.com/index.html");
        list.add(image);
        String jsonImage = image.toJsonString();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            Image tt = objectMapper.readValue(jsonImage, Image.class);
            log.info("成功转换--->" + tt.getUrl());
        } catch (Exception ex) {
            log.info("error!!!", ex);
        }
        Image in1 = JsonUtils.fromJson(jsonImage, Image.class);
        result.setData(listImages);
        return result;
    }

    @ApiCheck(check = false)
    @RequestMapping(value = "/redisTest.json", method = {RequestMethod.GET})
    public CommonResponse<String> redisTest(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "id", required = true) Long id) {
        CommonResponse<String> result = new CommonResponse<String>();
        String key = "system_params" + id;
        if (redisService.hasRedisKey(key)) {

        } else {
            result.setData(redisService.getValueRedisByKey(key));
        }
        return result;
    }

    @RequestMapping(value = "/getPracticePointList.json", method = {RequestMethod.GET,RequestMethod.POST})
    public CommonResponse<PracticePointList> getPracticePointList(HttpServletRequest request, HttpServletResponse response) {

        String practicePointInfo = "{\"practicePointList\":[{\"hospitalId\":1,\"hospitalName\":\"交通大学医院\",\"catalogId\":1,\"catalogName\":\"\",\"hospitalDepartmentId\":1,\"hospitalDepartmentName\":\"核磁共振科\"},{\"hospitalId\":1,\"hospitalName\":\"交通大学医院\",\"catalogId\":1,\"catalogName\":\"\",\"hospitalDepartmentId\":1,\"hospitalDepartmentName\":\"核磁共振科\"}]}";
        String practionPointListInfo = "[{\"provinceId\":\"310000\",\"provinceName\":\"上海\",\"cityId\":\"310101\",\"cityName\":\"黄浦区\",\"hospitalId\":1,\"hospitalName\":\"上海市第一人民医院\",\"catalogId\":1,\"catalogName\":\"特色科室\",\"hospitalDepartmentId\":2,\"hospitalDepartmentName\":\"手外科\"},{\"provinceId\":\"310000\",\"provinceName\":\"上海\",\"cityId\":\"310107\",\"cityName\":\"普陀区\",\"hospitalId\":1,\"hospitalName\":\"上海第二妇幼保健医院\",\"catalogId\":2,\"catalogName\":\"特色科室\",\"hospitalDepartmentId\":4,\"hospitalDepartmentName\":\"骨科\"}]";
        List<PracticePoint> practicePointList = JsonUtils.fromJson(practionPointListInfo, new TypeReference<List<PracticePoint>>() {
        });
        log.info("------------->"+JsonUtils.toJsonString(practicePointList));
        List<PracticePoint> practicePointList2 = JsonUtils.fromJson("", new TypeReference<List<PracticePoint>>() {
        });
        log.info("------------->"+JsonUtils.toJsonString(practicePointList2));


        CommonResponse< PracticePointList > result = new CommonResponse<>();
        PracticePointList list = new PracticePointList();
        List<PracticePoint> listPracticePoint = Lists.newArrayList();
        PracticePoint p = new PracticePoint();
        p.setProvinceId("310000");
        p.setProvinceName("上海");
        p.setCityId("310101");
        p.setCityName("黄浦区");
        p.setHospitalId(1L);
        p.setHospitalName("上海市第一人民医院");
        p.setCatalogId(1L);
        p.setCatalogName("特色科室");
        p.setHospitalDepartmentId(2L);
        p.setHospitalDepartmentName("手外科");
        listPracticePoint.add(p);


        p = new PracticePoint();
        p.setProvinceId("310000");
        p.setProvinceName("上海");
        p.setCityId("310107");
        p.setCityName("普陀区");
        p.setHospitalId(1L);
        p.setHospitalName("上海第二妇幼保健医院");
        p.setCatalogId(2L);
        p.setCatalogName("特色科室");
        p.setHospitalDepartmentId(4L);
        p.setHospitalDepartmentName("骨科");
        listPracticePoint.add(p);
        list.setPracticePointList(listPracticePoint);
        result.setData(list);
        return result;
    }


    @ApiOperation(value = "测试短信发送情况")
    @ApiCheck(check = false)
    @RequestMapping(value = "/testSms.json", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse<String> testSms(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "phoneNumber", required = true) String phoneNumber,
                                          @RequestParam(value = "code", required = true) String code,
                                          @RequestParam(value = "templateCode", required = false) String templateCode) {
        CommonResponse<String> res = new CommonResponse<String>();
        SendSmsResponse resp = smsService.sendSmsResponse(phoneNumber,code,templateCode);
        res.setData(resp.getMessage());
        log.info(res.toJsonString());
        return res;
    }

    @ApiOperation(value = "测试短信发送情况")
    @ApiCheck(check = false)
    @RequestMapping(value = "/testCheckSms.json", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse<String> testCheckSms(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "phoneNumber", required = true) String phoneNumber,
                                               @RequestParam(value = "code", required = true) String code,
                                               @RequestParam(value = "typeId", required = false,defaultValue = "2") Long typeId) {
        CommonResponse<String> res = new CommonResponse<String>();
        smsService.checkCode(phoneNumber,typeId,code);
        log.info(res.toJsonString());
        return res;
    }


    @ApiOperation(value = "测试路径参数情况")
    @ApiCheck(check = false)
    @RequestMapping(value = "/testPath/{managerInfo}", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse<String> testPath(HttpServletRequest request, HttpServletResponse response, @PathVariable("managerInfo") String managerInfo, @RequestParam(value = "typeId", required = true) AppType typeId) {
        CommonResponse<String> res = new CommonResponse<String>();
        log.info("------->目前转换成了："+typeId);
        AppType appType = AppType.valueOf("4");
        log.info("------->目前转换成了："+appType);

        res.setData(managerInfo);
        log.info(res.toJsonString());
        return res;
    }



    @ApiOperation(value = "测试redisCach 信息值情况")
    @ApiCheck(check = false)
    @RequestMapping(value = "/testRedisValue.json", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse<SystemParameters> testRedisValue(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "phoneNumber", required = false) String phoneNumber) {
        CommonResponse<SystemParameters> res = new CommonResponse<SystemParameters>();
        SystemParameters info = this.redisSystemParameterCache.getByKey(SystemParameterKey.APP_DOCTOR_AD_IMAGES_JSON.getCode());
        res.setData(info);
        log.debug(res.toJsonString());
        return res;
    }
    @ApiOperation(value = "测试eventPublisher信息值情况,将参数从数据库中加载到redis缓存")
    @ApiCheck(check = false)
    @RequestMapping(value = "/eventPublisher.json", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse<String> eventPublisher(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "phoneNumber", required = false) String phoneNumber) {
        CommonResponse<String> res = new CommonResponse<String>();
//        RefreshRedisCacheEvent event = new RefreshRedisCacheEvent("****************");
//        this.context.publishEvent(event);
        log.info("触发事件发布机制-----------》》》》");
        RefreshRedisCacheEvent event = new RefreshRedisCacheEvent("发布刷新redis缓存指令",EnumSet.of(RefreshRedisFlag.REFRESH_SYSTEM_PARAMETER),10,TimeUnit.SECONDS);
        this.eventPublisher.publishEven(event);
        //this.eventPublisher.sendRefreshRedisCacheEvent(EnumSet.of(RefreshRedisFlag.REFRESH_SYSTEM_PARAMETER),10,TimeUnit.SECONDS);
        //this.eventPublisher.sendRefreshRedisCacheEvent(EnumSet.allOf(RefreshRedisFlag.class));
        res.setData("已经将参数从数据库中加载到redis缓存");
        log.info(res.toJsonString());
        return res;
    }
    @ApiOperation(value = "测试eventAppPush信息值情况,推送消息")
    @ApiCheck(check = false)
    @RequestMapping(value = "/eventAppPush.json", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse<String> eventAppPush(HttpServletRequest request, HttpServletResponse response) {
        CommonResponse<String> res = new CommonResponse<String>();
//        RefreshRedisCacheEvent event = new RefreshRedisCacheEvent("****************");
//        this.context.publishEvent(event);
        log.info("触发事件发布机制-----------》》》》");
        AppPushEvent event = new AppPushEvent("app push");
        this.eventPublisher.publishEven(event);
        //this.eventPublisher.sendRefreshRedisCacheEvent(EnumSet.of(RefreshRedisFlag.REFRESH_SYSTEM_PARAMETER),10,TimeUnit.SECONDS);
        //this.eventPublisher.sendRefreshRedisCacheEvent(EnumSet.allOf(RefreshRedisFlag.class));
        res.setData("已经推送消息");
        log.info(res.toJsonString());
        return res;
    }


    @ApiOperation(value = "通过查询获取省份信息列表")
    //@ApiCheck(check = true)
    @RequestMapping(value = "/getChatStateList.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ListResponse<ChatState> getChatStateList(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "通用请求接口类") @RequestBody(required = false) ChatStateRequest params) {
        if (params == null) {
            params = new ChatStateRequest();
        }
        params.doWithSortOrStart();
        log.info("目前分页条件如下：" + params.toJsonString());
        ListResponse<ChatState> res  = new ListResponse<ChatState>();
        List<ChatState> list = this.serviceChatRecordService.listChatState(params);
        res.setData(list);
        res.setTotal(this.serviceChatRecordService.listChatStateCount(params));
        log.debug(res.toJsonString());
        scheduleTask(list);


        return res;
    }

    public void scheduleTask(List<ChatState> list) {
        // 时间类
        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        cal.add(Calendar.SECOND,10);
        Date nextDay = cal.getTime();
        //设置开始执行的时间为 某年-某月-某月 00:00:00
        //startDate.set(startDate.get(Calendar.YEAR), startDate.get(Calendar.MONTH), startDate.get(Calendar.DATE), 16, 10, 0);

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                log.info("{}------------------------------------计划实际执行时间：",Utils.getDateNow());
                //log.info(res.toJsonString());
                log.info("当时查询到的条目是："+list.size());
            }
        };
        Timer timer = new Timer();
        log.info("{}------------------------------------计划将要要执行时时间：",Utils.getDateNow());
        timer.schedule(task,nextDay);
        // 1小时的毫秒设定
//        long period = 60 * 60 * 1000;
//        timer.schedule(task,nextDay,period);
    }

    @ApiOperation(value = "测试医生开关，批量影响updateChatStateBatch")
    @ApiCheck(check = false)
    @RequestMapping(value = "/updateChatStateBatch.json", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse<String> updateChatStateBatch(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "通用请求接口类") @RequestBody(required = false) ChatStateRequest params) {
        CommonResponse<String> res = new CommonResponse<String>();
        res.setData(this.serviceChatRecordService.updateChatState(params));
        log.info(res.toJsonString());
        return res;
    }


    @ApiOperation(value = "jwtTokenService")
    @ApiCheck(check = false)
    @RequestMapping(value = "/jwtTokenService.json", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse<String> jwtTokenService(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "typeId", required = false,defaultValue = "1")String typeId
            , @RequestParam(value = "userId", required = false,defaultValue = "888")String userId
            , @RequestParam(value = "osType", required = false,defaultValue = "ios")String osType
            , @RequestParam(value = "phoneNumber", required = false,defaultValue = "13811111111")String phoneNumber) {
        CommonResponse<String> res = new CommonResponse<String>();
        String tokent = this.jwtTokenService.createToken(typeId,userId,osType,phoneNumber);
        res.setData(tokent);
        log.info(res.toJsonString());
        return res;
    }
    @ApiOperation(value = "loginVeriferToken")
    @ApiCheck(check = false)
    @RequestMapping(value = "/loginVeriferToken.json", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse<String> loginVeriferToken(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "token", required = false) String token,
                                                    @RequestParam(value = "typeId", required = false,defaultValue = "1")String typeId
            , @RequestParam(value = "userId", required = false,defaultValue = "888")String userId
            , @RequestParam(value = "osType", required = false,defaultValue = "ios")String osType
            , @RequestParam(value = "phoneNumber", required = false,defaultValue = "13811111111")String phoneNumber) {
        CommonResponse<String> res = new CommonResponse<String>();
        this.jwtTokenService.loginVeriferToken(token,typeId,userId);
        res.setData("OK");
        log.info(res.toJsonString());
        return res;
    }


    @ApiOperation(value = "testSetRedies")
    @ApiCheck(check = false)
    @RequestMapping(value = "/testSetRedies.json", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse<String> testRedies(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "value", required = false,defaultValue = "")String value) {
        CommonResponse<String> res = new CommonResponse<String>();
        String key = "keyTest";
        if(StringUtils.isNotBlank(value)){
            if(StringUtils.equalsIgnoreCase("value",value)){//改变值，继续延续
                long expire = redisService.getExpirRedis(key,TimeUnit.SECONDS);
                redisService.setValueRedisByKey(key,value,expire,TimeUnit.SECONDS);
            }
            else{
                redisService.setValueRedisByKey(key,value);
            }
        }
        else{//设定过期日期
            value = "valuTest";
            redisService.setValueRedisByKey(key,value,1L,TimeUnit.MINUTES);
        }
        long expire = redisService.getExpirRedis(key,TimeUnit.SECONDS);
        res.setData(value +"---------->到期秒数："+expire);
        log.info(res.toJsonString());
        return res;
    }
    @ApiOperation(value = "testGetRedies")
    @ApiCheck(check = false)
    @RequestMapping(value = "/testGetRedies.json", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse<String> testGetRedies(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "typeId", required = false,defaultValue = "1")String typeId) {
        CommonResponse<String> res = new CommonResponse<String>();
        String key = "keyTest";
        long expire = redisService.getExpirRedis(key,TimeUnit.SECONDS);
        res.setData(redisService.getValueRedisByKey(key)+" ---->到期秒数："+expire);
        log.info(res.toJsonString());
        return res;
    }


    @ApiCheck(check = false)
    @RequestMapping(value = "/redisAdd.json", method = {RequestMethod.GET})
    public CommonResponse<String> redisAdd(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "id", required = true) String id) {
        CommonResponse<String> result = new CommonResponse<String>();
        String key = "redisAdd_" + id;
        redisService.setValueRedisByKey(key,key);
        result.setData(redisService.getValueRedisByKey(key));
        return result;
    }

    @ApiCheck(check = false)
    @RequestMapping(value = "/deleteRedisByPattern.json", method = {RequestMethod.GET})
    public CommonResponse<String> deleteRedisByPattern(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "pattern", required = true) String pattern, @RequestParam(value = "id", required = false) String id) {
        CommonResponse<String> result = new CommonResponse<String>();
        redisService.deleteRedisByPattern(pattern);
        result.setData(redisService.getValueRedisByKey("redisAdd_" + id));
        return result;
    }

    @ApiCheck(check = false)
    @RequestMapping(value = "/getKeysByPattern.json", method = {RequestMethod.GET})
    public CommonResponse<String> getKeysByPattern(HttpServletRequest request11, HttpServletResponse response111, @RequestParam(value = "pattern", required = true) String pattern) {
        CommonResponse<String> result = new CommonResponse<String>();
        Set<String>  stringSet = redisService.getRedisKeysByPattern(pattern);
        stringSet.forEach(t->{
            log.info("缓存情况如此 key:{}  value:{}",t,redisService.getValueRedisByKey(t,false));
        });

        String keyPattern = ConstantAll.REDIS_DOCTOR_APPOINT_LOCK_ORDER+1+ConstantAll.REDIS_DOCTOR_APPOINT_LOCK_ORDER_PART_ORDER+"*";
        log.info(" key--->{}",keyPattern);
        Set<String>  stringSet1 = redisService.getRedisKeysByPattern(keyPattern);
        stringSet1.forEach(key->{
            log.info("缓存情况如此 key:{}  value:{}",key,redisService.getValueRedisByKey(key,false));
        });
/*
        try {
            //https://docs.open.alipay.com/54/103419/  官方帮助文档 见此
            String URL = "https://openapi.alipay.com/gateway.do";//支付宝网关（固定）
            String APP_ID = "2018010301555535";
            String APP_PRIVATE_KEY = "";//开发者应用私钥，由开发者自己生成
            String ok                = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAkUj3XcfLJyMf5Y92L7mI7jbKvdh/b0OrADem0DnLU9eSkz2RViz/fDIHiTiMmQv1hTXWtyFbULW5H3tOecKsPL33GEc6NBwRG15OkCMLu0AnHNYbYvf4AO4qTV5qvps6wQtoLuiympFk7+xeCKHyxHiGcghcsCNtraOoAzwSHZ6x10UWoGGYPLYelpmeDfgjmYKDGCmK/f1A80rux54aufpfh1c2UmNM+xV/J8rWihPWVzDYspDZxJQuOs+ZD4hy6ZQNqQSp/QohB5Z9MduCyqwlwWrCzpEr2vsrGT87kuZ0UHLiQcsrcGyHrpvfTHRSOPkp3+xe9fvJsTj0gNDU3wIDAQAB";
            String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAkUj3XcfLJyMf5Y92L7mI7jbKvdh/b0OrADem0DnLU9eSkz2RViz/fDIHiTiMmQv1hTXWtyFbULW5H3tOecKsPL33GEc6NBwRG15OkCMLu0AnHNYbYvf4AO4qTV5qvps6wQtoLuiympFk7+xeCKHyxHiGcghcsCNtraOoAzwSHZ6x10UWoGGYPLYelpmeDfgjmYKDGCmK/f1A80rux54aufpfh1c2UmNM+xV/J8rWihPWVzDYspDZxJQuOs+ZD4hy6ZQNqQSp/QohB5Z9MduCyqwlwWrCzpEr2vsrGT87kuZ0UHLiQcsrcGyHrpvfTHRSOPkp3+xe9fvJsTj0gNDU3wIDAQAB";//支付宝公钥，由支付宝生成
//            String CHARSET = "UTF-8";
//            String SIGN_TYPE = "RSA2";//商户生成签名字符串所使用的签名算法类型，目前支持RSA2和RSA
//实例化客户端
            AlipayClient alipayClient = new DefaultAlipayClient(ALIPAY_GW_URL, ALIPAY_H5_APPID,  ALIPAY_H5_PRIVATE_KEY, PACKAGE_FORMAT, CHARSET, ALIPAY_H5_ALIPAY_PUBLIC_KEY,  SIGN_TYPE);

                    //new DefaultAlipayClient(URL, APP_ID, APP_PRIVATE_KEY, "json", CHARSET, ALIPAY_PUBLIC_KEY, SIGN_TYPE);
//实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.open.public.template.message.industry.modify
            AlipayOpenPublicTemplateMessageIndustryModifyRequest request = new AlipayOpenPublicTemplateMessageIndustryModifyRequest();
//SDK已经封装掉了公共参数，这里只需要传入业务参数
//此次只是参数展示，未进行字符串转义，实际情况下请转义
            request.setBizContent("  {" +
                    "    \"primary_industry_name\":\"IT科技/IT软件与服务\"," +
                    "    \"primary_industry_code\":\"10001/20102\"," +
                    "    \"secondary_industry_code\":\"10001/20102\"," +
                    "    \"secondary_industry_name\":\"IT科技/IT软件与服务\"" +
                    " }");
            log.info("发送请求:{}",request.getBizContent());
            AlipayOpenPublicTemplateMessageIndustryModifyResponse response = alipayClient.execute(request);
            //调用成功，则处理业务逻辑
            if(response.isSuccess()){
                log.info("调用成功，则处理业务逻辑:message->{}  body-->{}",response.getMsg(),response.getBody());
            }
        } catch (AlipayApiException e) {
            log.info("支付宝支付问题:"+e.getMessage(),e);
        } finally {
        }
*/

        return result;
    }


    public static final String ALIPAY_GW_URL = "https://openapi.alipay.com/gateway.do";
    /**
     * 商户号. https://b.alipay.com/order/signManage.htm?channel=ent
     */
    public static final String PID = "2088521637163181";

    /**
     * 支付宝手机网页支付,AppId
     */
    public static final String ALIPAY_H5_APPID = "2017012305374101";
    /**
     * 支付宝手机网页支付,商户私钥
     */
    public static final String ALIPAY_H5_PRIVATE_KEY = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQDZDHX+px+MWG6Jkex+cpTf0JgijuQn1K9HVNurH2vjTZ5CQenUQdbRqG2X8cbENppAt/i9BQWzHs8BiHAhgmHbdOHfubZK1A1ArqWPBWEsQsM4iLXgcDO4i4vVqfQXDFj7PeAl3Lxb9CHxbOy5/lueFb8h7WbO4gdSEKhXodFWjleeV1jAMQYccyBFEdgjyR7NpI2cDnnEu2BMZ1stK1/RrZFBv+CbZKcC2rdeAd5Ip5hPSeBUpI8XQ1+9BjnpiR7WBKEwfFNMSVPOUfcY/E3x9JDOXlKKMRfObPBrW0VdSOZvrG+9cGCcFBV2hzm6cSv2q8Ys02VflqepWw0xkqjhAgMBAAECggEAO7S90KtQdl7tKVLG55HFejv7XaZJBNvwy4KAWvZtyz1Sx48yL31dBpd6bk3IC7mpfRwehICZLMjQ0O8jSVSAbsocSFrh1nu2mZBxYoJsVKyY3zhcL70tvRpGASSbTKvM3nAK9N9fwTvLCoqYJCHgOgBpb3/KxCZRNTX32QNefpxvpLSIxcI6rQ6KBTngGvpg72/ktHMihumO64RgjpcmH1Z3LzG3rPx+VGDi7DBDWuUWShom8GD1b5Z962/9yu0f4AmFkgPraZD+rmQSuld9h1fzFXvUoz3iPOtvCRJFBxz+kIKQxYn1sD7J/zWNYDXIJE7WHHuTCI7juOBBSd/SQQKBgQDzco8gCPTf16TeK9ZNNPk9eNlSlgVEo3qKu7L/EU5IB0UmXTWxbVYw/rFmAlR7q3Cmo+QySZAPRG8cR9k74Qq/1BESiubr36wDSuO+21EgiJbGqW7VIvCj9oINN3sSxnQCAKBqtdapF1JTorzgZZECoA6tV3ehWXrOD6ZQ8tYDxQKBgQDkPXHOlG4vQN6EKTHsrKkzRMOskB7OZ8xd/B/TSoBFTzL9Pn+/wXOTXqfhGuEjEPfD188WPH98WdhU7MVBz8PpeIf0RQBlW70tFB6DPXd9oWhR7xqgzaCYRQHy/Gt/4ATO4pAtIR81hxQzHbG++r7B/Q3BQbW+oDz18nxNOgG2bQKBgE8Vxh0YC8Sz3yE1iHaoYNdxbNgBWFDF8sci1rE5w/5uLi6aVRkAdeGQu7xxwVQzi3L3Bui3MQ9kodvEE3P6VW2fzLQDX6T8knq3V2jnAmHZPXHoFNM0eIwrRnBYt6iPw5vq7DH+RG/F3b7BvS4AkK0TpfD12y0VTmmmd9cQLCopAoGAb20b8Yl/XWxi9cySxgPpOtFYYheeIpCEMmITO3kEtFGdCAuKHJiaDxlIBlq0DQWMoDzsqeNxLclDFvrctmtzLrkcToGdVt55tdc7qkDI5Dl3j4CW1Ghu/ce60uCurqg0ULhFQPMJpH1EvEvD6gPKOzSKkY2958mc2Um/q4glK+UCgYBJ4uLsslWx5l7tGpyMyJM+YGrplgu2IzD88v+tRrzEQVHaScHHh2ZMchsb49z3+bPjFmcZ2FRNjMsGUrS8kbWJ7lAyUAYWGpsf3fEw0HIw9nkXAKWW+cJ8EcStGmj9LbPmTpRz19W11u3GAvIBoanMs41Me2tLRf4xg526Gtz+Jg==";
    /**
     * 支付宝手机网页支付,商户公钥
     */
    public static final String ALIPAY_H5_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA2Qx1/qcfjFhuiZHsfnKU39CYIo7kJ9SvR1Tbqx9r402eQkHp1EHW0ahtl/HGxDaaQLf4vQUFsx7PAYhwIYJh23Th37m2StQNQK6ljwVhLELDOIi14HAzuIuL1an0FwxY+z3gJdy8W/Qh8Wzsuf5bnhW/Ie1mzuIHUhCoV6HRVo5XnldYwDEGHHMgRRHYI8kezaSNnA55xLtgTGdbLStf0a2RQb/gm2SnAtq3XgHeSKeYT0ngVKSPF0NfvQY56Yke1gShMHxTTElTzlH3GPxN8fSQzl5SijEXzmzwa1tFXUjmb6xvvXBgnBQVdoc5unEr9qvGLNNlX5anqVsNMZKo4QIDAQAB";
    /**
     * 支付宝手机网页支付,支付宝公钥
     */
    public static final String ALIPAY_H5_ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmc3gPjfgw7vRP/bZXDo2DGVsPuaP9V+MKaf2sJ6xOE9IxBQxKnfaa5mBsHW5ocnaWCjORpNaq1KlNepYW4VAvA7Zbd96k+bdZ/rkdGJ2s//Me8dn7pTA982/8TNlGEwUOi6UC1q64+aj91FcspUV0plL8Nr/JteLRfXve57wSUgJOiQq6D6wX6ZUAwA1Wh97+eI+mLTNLWD4OhUxcl97OJYSH8O3Q3cKouJTvE/Q9bgzUCHqbkypgvJjva/NwrUr/grgT/TfNDuF2dvPy98O2vCgTBO8vb/Z7Pc1xMtxVf+1x2fmaVho2eRv47B47ttTAft9H+SZKfgshUymm5VF/wIDAQAB";
    public static final String SIGN_TYPE = "RSA2";
    // 字符编码格式 目前支持 utf-8
    public static final String CHARSET = "UTF-8";
    public static final String PACKAGE_FORMAT = "json";
    public static final String CALLBACK_URL = "/alipay/gw/payResultNotify.html";
}
