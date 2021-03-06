package com.yier.platform.service;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.yier.platform.common.model.Areas;
import com.yier.platform.common.model.Cities;
import com.yier.platform.common.model.DeviceInfoException;
import com.yier.platform.common.model.Provinces;
import com.yier.platform.common.po.IdCardClass;
import com.yier.platform.common.requestParam.BaseRequest;
import com.yier.platform.common.util.IdcardUtils;
import com.yier.platform.common.util.Utils;
import com.yier.platform.dao.AreasMapper;
import com.yier.platform.dao.CitiesMapper;
import com.yier.platform.dao.DeviceInfoExceptionMapper;
import com.yier.platform.dao.ProvincesMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

/**
 * 通用处理信息 service
 */
@ApiModel(value = "通用处理信息 service")
@Service
public class CommonService {
    private static final String JuHe_CarId_App = "635b9cbebf4efde2cb14f6b806fd26b7";//身份证
    private final Logger logger = LoggerFactory.getLogger(CommonService.class);
    @Autowired
    private CitiesMapper daoCitiesMapper;
    @Autowired
    private ProvincesMapper daoProvincesMapper;
    @Autowired
    private AreasMapper daoAreasMapper;
    @Autowired
    private SmsService smsService;
    @Autowired
    private Uploader uploader;
    @Autowired
    private DeviceInfoExceptionMapper daoDeviceInfoExceptionMapper;
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private PatientService patientService;
    @Autowired
    private PharmacistService pharmacistService;

    /**
     * 验证身份证是否合法
     */
    @ApiOperation(value = "验证身份证，端口下的用户，真实姓名")
    public boolean validateIdCard(String trueName, String idCard, String typeIdString, String useType) {
        Assert.isTrue(StringUtils.isNotBlank(typeIdString), "没有指明使用端口");
        Assert.isTrue(StringUtils.isNotBlank(trueName), "没有输入真实姓名");
        Assert.isTrue(idCard.trim().length() == 15 || idCard.trim().length() == 18, "身份证号长度无效");
        Assert.isTrue(IdcardUtils.validateCard(idCard), "身份证号码无效");
        long typeId = Long.parseLong(typeIdString);
        if (typeId == 1) {
            patientService.verifyIdCardNo(idCard, useType);
        } else if (typeId == 2) {
            doctorService.verifyIdCardNo(idCard, useType);
        } else if (typeId == 3) {
            pharmacistService.verifyIdCardNo(idCard, useType);
        }
        return true;
    }

    @ApiOperation(value = "将异常信息插入对应的列表")
    public int insertDeviceInfoException(DeviceInfoException record, MultipartFile file) {
        Assert.isTrue(file != null, "文件无效，请重新传递");
        String url = uploader.upload(file);
        record.setExceptionPathUrl(url);
        return daoDeviceInfoExceptionMapper.insertSelective(record);
    }

    @ApiOperation(value = "根据条件查询省市列表")
    public DeviceInfoException selectDeviceInfoExceptionByPrimaryKey(Long id) {
        return this.daoDeviceInfoExceptionMapper.selectByPrimaryKey(id);
    }

    @ApiOperation(value = "根据条件查询省市列表")
    public List<Provinces> listProvinces(BaseRequest params) {
        return daoProvincesMapper.listProvinces(params);
    }

    @ApiOperation(value = "根据条件查询省市列表总数")
    public int listProvincesCount(BaseRequest params) {
        return daoProvincesMapper.listProvincesCount(params);
    }

    @ApiOperation(value = "根据条件查询省市列表")
    public List<Areas> listAreas(BaseRequest params) {
        return daoAreasMapper.listAreas(params);
    }

    @ApiOperation(value = "根据条件查询省市列表总数")
    public int listAreasCount(BaseRequest params) {
        return daoAreasMapper.listAreasCount(params);
    }

    @ApiOperation(value = "根据条件查询省市列表")
    public List<Cities> listCities(BaseRequest params) {
        return daoCitiesMapper.listCities(params);
    }

    @ApiOperation(value = "根据条件查询省市列表总数")
    public int listCitiesCount(BaseRequest params) {
        return daoCitiesMapper.listCitiesCount(params);
    }


    @ApiOperation(value = "发送验证码")
    public void sendCode(String phoneNumber, String code, Long typeId, String useType, String clientIP) {
        SendSmsResponse resp = smsService.sendSms(phoneNumber, typeId, code, useType, clientIP);
    }

    @ApiOperation(value = "通过身份证号码获取对应的身份信息")
    public IdCardClass getIdCardClass(String idCardNo) {
        Assert.isTrue(IdcardUtils.validateCard(idCardNo), "身份证号码无效");
        IdCardClass idCardClass = new IdCardClass();

        String birth = IdcardUtils.getBirthByIdCard(idCardNo);//生日(yyyyMMdd)
        String sex = IdcardUtils.getGenderByIdCard(idCardNo);//性别(M-男，F-女，N-未知)
        Date birthdayDate = Utils.parseDate(birth, "yyyyMMdd");
        birth = Utils.formatDate(birthdayDate, "yyyy年MM月dd日");
        if (StringUtils.equalsIgnoreCase(sex, "M")) {
            sex = "男";
        } else if (StringUtils.equalsIgnoreCase(sex, "F")) {
            sex = "女";
        } else {
            sex = "";
        }
        String province = IdcardUtils.getProvinceByIdCard(idCardNo);//省级编码
        IdCardClass.IdCardInfo result = new IdCardClass.IdCardInfo();
        result.setArea(province);
        result.setSex(sex);
        result.setBirthday(birth);
        idCardClass.setResult(result);
        idCardClass.setResultcode("200");
        return idCardClass;


/*        Assert.isTrue(StringUtils.isNotBlank(idCardNo), "身份证号无效");
        Assert.isTrue(idCardNo.trim().length() == 15 || idCardNo.trim().length() == 18, "身份证号长度无效");
        String url = "http://apis.juhe.cn/idcard/index?key=" + JuHe_CarId_App + "&cardno=" + idCardNo.trim();
        Map<String, String> paramsWithAppKey = new HashMap<>();
        String getResult = HttpUtils.get(url, paramsWithAppKey);
        logger.info("use idCardNo getResult-->{}", getResult);
        IdCardClass idCardClass = JsonUtils.fromJson(getResult, IdCardClass.class);
        logger.info("查询到的返回结果是：{}",idCardClass.toJsonString());
        Assert.isTrue(idCardClass != null, "实名验证失效");
        if (!StringUtils.equals("200", idCardClass.getResultcode())) {
            Assert.isTrue(false, idCardClass.getReason());
        }
        return idCardClass;*/
    }


}
