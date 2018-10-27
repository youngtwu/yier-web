package com.yier.platform.service;

import com.google.common.collect.Lists;
import com.yier.platform.common.costant.ConstantAll;
import com.yier.platform.common.model.*;
import com.yier.platform.common.po.IdCardClass;
import com.yier.platform.common.requestParam.BaseRequest;
import com.yier.platform.common.requestParam.ChatStateRequest;
import com.yier.platform.common.requestParam.DoctorRequest;
import com.yier.platform.common.requestParam.PracticeRequest;
import com.yier.platform.common.rongYun.RongYunTokenResult;
import com.yier.platform.common.typeEnum.EvaluationType;
import com.yier.platform.common.util.RandomUtils;
import com.yier.platform.common.util.Utils;
import com.yier.platform.dao.DoctorEvaluationMapper;
import com.yier.platform.dao.DoctorMapper;
import com.yier.platform.dao.DoctorTitleMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.MutableTriple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 医生信息 service
 */
@ApiModel(value = "医生信息 service")
@Service
public class DoctorService {
    private static final Logger logger = LoggerFactory.getLogger(DoctorService.class);
    @Autowired
    private DoctorMapper daoDoctorMapper;
    @Autowired
    private DoctorEvaluationMapper daoDoctorEvaluationMapper;
    @Autowired
    private RongCloudService serviceRongCloudService;
    @Autowired
    private CommonService commonService;
    @Autowired
    private Uploader uploader;
    @Autowired
    private SmsService smsService;
    @Autowired
    private ChatRecordService serviceChatRecordService;
    @Autowired
    private DoctorTitleMapper daoDoctorTitleMapper;
    @Autowired
    private JwtTokenService jwtTokenService;
    @Autowired
    private PracticePointTimeService practicePointTimeService;
    @Autowired
    private SignatureService signatureService;//签名方面的生成
    @Autowired
    private UserService serviceUserService;
    @Autowired
    private AuditInfoService auditInfoService;

    @ApiOperation(value = "开通或关闭医生聊天设定对应的影响：推送，延迟，提醒等")
    public String updateChatState(ChatStateRequest params) {
        return serviceChatRecordService.updateChatState(params);
    }

    @ApiOperation(value = "根据条件查询评价携带医生职称列表")
    public List<DoctorTitle> listDoctorTitle(BaseRequest params) {
        return this.daoDoctorTitleMapper.listDoctorTitle(params);
    }

    @ApiOperation(value = "根据条件查询评价携带医生职称列表总数")
    public int listDoctorTitleCount(BaseRequest params) {
        return this.daoDoctorTitleMapper.listDoctorTitleCount(params);
    }

    @ApiOperation(value = "根据条件Id查询评价医生及科室关联的医院及主分类信息")
    public DoctorEvaluationPo getDoctorEvaluationPoWithDoctorById(Long id) {
        return daoDoctorEvaluationMapper.getDoctorEvaluationPoWithDoctorById(id);
    }

    @ApiOperation(value = "根据条件按照医生或患者分组查询评价携带医生信息列表")
    public List<DoctorEvaluationPo> listEvaluationWithGroupDoctorContent(DoctorRequest params) {
        return daoDoctorEvaluationMapper.listEvaluationWithGroupDoctorContent(params);
    }

    @ApiOperation(value = "根据条件按照医生或患者分组查询评价携带医生信息列表总数")
    public int listEvaluationWithGroupDoctorContentCount(DoctorRequest params) {
        return daoDoctorEvaluationMapper.listEvaluationWithGroupDoctorContentCount(params);
    }

    @ApiOperation(value = "根据条件查询评价携带医生信息列表")
    public List<DoctorEvaluationPo> listEvaluationWithDoctorContent(DoctorRequest params) {
        return daoDoctorEvaluationMapper.listEvaluationWithDoctorContent(params);
    }

    @ApiOperation(value = "根据条件查询评价携带医生信息列表总数")
    public int listEvaluationWithDoctorContentCount(DoctorRequest params) {
        return daoDoctorEvaluationMapper.listEvaluationWithDoctorContentCount(params);
    }

    @ApiOperation(value = "根据条件查询评价列表")
    public List<DoctorEvaluationPo> listDoctorEvaluationPo(DoctorRequest params) {
        return daoDoctorEvaluationMapper.listDoctorEvaluationPo(params);
    }

    @ApiOperation(value = "根据条件查询评价列表总数")
    public int listDoctorEvaluationPoCount(DoctorRequest params) {
        return daoDoctorEvaluationMapper.listDoctorEvaluationPoCount(params);
    }

    @ApiOperation(value = "获取包含医院科室信息患者关注执业点 的医生信息 ")
    public DoctorPo getDoctorPoWithHospitalDepartmentAndEvaluationList(DoctorRequest params) {
        DoctorPo result = this.getDoctorPoById(params);
        //和患者关联的信息不存在了DoctorPo result = daoDoctorEvaluationMapper.getDoctorPoById(params.getId());
        //DoctorPo result = daoDoctorMapper.getDoctorPoById(params.getId());
        Assert.isTrue(result != null, "不存在该医生");
        if (StringUtils.equals("list", params.getPath())) {
            DoctorRequest paramsDE = new DoctorRequest();
            paramsDE.setDoctorId(result.getId());
            paramsDE.setStatus("0");
            String evaluations = daoDoctorEvaluationMapper.getDoctorEvaluationList(paramsDE);
            if (evaluations == null) {
                evaluations = "";//如果为空的话返回个空数据
                logger.info("评价描述:{}", params.toJsonString());
            }
            List<String> evaluationsList = Arrays.asList(StringUtils.split(evaluations, ','));
            long total = this.listDoctorEvaluationPoCount(paramsDE);
            List<MutableTriple<String, String, String>> list = Lists.newArrayList();
            for (EvaluationType item : EvaluationType.values()) {
                if (item.getType() == "-1") {
                    continue;
                }
                long currentCount = evaluationsList.stream().filter(r -> r.equals(item.getType())).count();
                if (currentCount > 0) {
                    //total += currentCount;
                    list.add(new MutableTriple(item.getType(), item.getDesc(), "" + currentCount));
                }
            }
            list.add(0, new MutableTriple("", "全部", "" + total));
            for (String item : evaluationsList) {
                logger.info("------>" + item);
            }
            result.setEvaluationTypeList(list);
        } else if (StringUtils.equals("insert", params.getPath())) {
            List<MutableTriple<String, String, String>> list = Lists.newArrayList();
            for (EvaluationType item : EvaluationType.values()) {
                if (item.getType() == "-1") {
                    continue;
                }
                list.add(new MutableTriple(item.getType(), item.getDesc(), "支持多选"));
            }
            result.setEvaluationTypeList(list);
        }
        return result;
    }

    @ApiOperation(value = "获取医生评价列表")
    public String getDoctorEvaluationList(DoctorRequest params) {
        return daoDoctorEvaluationMapper.getDoctorEvaluationList(params);
    }

    @ApiOperation(value = "插入评价")
    public int insertDoctorEvaluation(DoctorEvaluation record) {
        Assert.isTrue(record.getDoctorId() != null && record.getDoctorId().longValue() > 0, "请传递正确的医生ID");
        Assert.isTrue(record.getPatientId() != null && record.getPatientId().longValue() > 0, "请传递正确的患者ID");
        Assert.isTrue(StringUtils.isNotBlank(record.getContent()), "请输入有效的反馈内容");
        if (record.getTypeIds() != null) {
            record.setTypeIds(record.getTypeIds().replaceAll(" ", ""));//过滤掉无效空格 输入内容如：1,2
        }
        return daoDoctorEvaluationMapper.insertSelective(record);
    }

    @ApiOperation(value = "搜索对应的模糊名字")
    public List<String> getSimilarNameList(BaseRequest params) {
        return daoDoctorMapper.getSimilarNameList(params);
    }

    // 根据条件查询出门就诊列表
    public List<DoctorPo> listMedicalDoctorPo(DoctorRequest params) {
        return daoDoctorMapper.listMedicalDoctorPo(params);
    }

    // 根据条件查询出门就诊列表 总数
    public int listMedicalDoctorPoCount(DoctorRequest params) {
        return daoDoctorMapper.listMedicalDoctorPoCount(params);
    }

    // 根据条件查询聊天设定等列表
    public List<DoctorPo> listDoctorPo(DoctorRequest params) {
        return daoDoctorMapper.listDoctorPo(params);
    }

    // 根据条件查询列表 总数
    public int listDoctorPoCount(DoctorRequest params) {
        return daoDoctorMapper.listDoctorPoCount(params);
    }


    // 根据条件查询列表
    public List<Doctor> listDoctor(DoctorRequest params) {
        return daoDoctorMapper.listDoctor(params);
    }

    // 根据条件查询列表 总数
    public int listDoctorCount(DoctorRequest params) {
        return daoDoctorMapper.listDoctorPoCount(params);
    }

    @ApiOperation(value = "根据IP获取医生关于医院及科室的信息详情信息")
    public DoctorPo getDoctorHospitalPoById(Long id) {
        return daoDoctorMapper.getDoctorHospitalPoById(id);
    }

    //根据IP获取医生详情信息
    public DoctorPo getDoctorPoById(DoctorRequest params) {
        DoctorPo p = daoDoctorMapper.getDoctorPoById(params);
        if (p != null) {
            p.thinkField();
        } else {
            return p;
        }
        Assert.isTrue(p != null, "不存在该医生");
        PracticeRequest paramsPr = new PracticeRequest();
        paramsPr.setTypeId(ConstantAll.TYPE_ID_DOCTOR);
        paramsPr.setDoctorId(p.getId());
        List<PracticePointPo> list = this.practicePointTimeService.listPracticePointPo(paramsPr);
        if (list != null) {
            p.setPracticePointList(list);
        }

/*        if (StringUtils.isNotBlank(p.getPracticePoint())) {
            List<PracticePoint> practicePointList = JsonUtils.fromJson(p.getPracticePoint(), new TypeReference<List<PracticePoint>>() {
            });
            p.setPracticePointList(practicePointList);
//            PracticePointList list = JsonUtils.fromJson(p.getPracticePoint(), PracticePointList.class);
//            p.setPracticePointList(list.getPracticePointList());
        }*/
        return p;
    }

    //根据IP获取医生详情信息
    public Doctor selectByPrimaryKey(Long id) {
        return daoDoctorMapper.selectByPrimaryKey(id);
    }

    @ApiOperation(value = "修改用户头像或近照图片")
    public Doctor adjustDoctorInfo(MultipartFile file, Long id, String use) {
        Assert.isTrue(file != null, "头像无效，请重新传递");
        Doctor record = this.selectByPrimaryKey(id);
        Assert.isTrue(record != null, "传递的ID无效，无法查询到对应的医生");
        String url = uploader.upload(file);
        if (StringUtils.equalsIgnoreCase("avatar", use)) {
            logger.info("头像显示：{}", url);
            record.setAvatarUrl(url);
//            RongYunTokenResult ry = this.serviceRongCloudService.getToken(2L, record.getId(), record.getTrueName(), record.getAvatarUrl());
//            if (ry == null) {
//                Assert.isTrue(false, "获取第三方即时通讯工具token错误");
//            } else {
//                Assert.isTrue(ry.getCode() == 200, ry.getMsg());
//            }
//            record.setImtoken(ry.getToken());
        } else {
            record.setPhotoUrl(url);
        }
        record.setGmtModified(new Date());
        this.updateDoctorSelective(record);
        DoctorPo po = new DoctorPo(record);
        return po;
    }

    @ApiOperation(value = "通过身份证号，验证证件是否有效")
    public List<Doctor> verifyIdCardNo(String idCardNo, String useType) {
        Assert.isTrue(StringUtils.isNotBlank(idCardNo), "身份证号无效");
        Assert.isTrue(idCardNo.trim().length() == 15 || idCardNo.trim().length() == 18, "身份证号长度无效");
        DoctorRequest params = new DoctorRequest();
        params.setIdCardNo(idCardNo);
        List<Doctor> list = this.listDoctor(params);
        if (list.size() > 1) {
            Assert.isTrue(false, "重复存在该用户，请联系管理员处理");
        }
        if (StringUtils.equals("0", useType)) {
            if (list.size() == 1) {
                Assert.isTrue(false, "该身份证号码已经注册，无法重新绑定");//系统存在该用户，无法注册
            }
        } else if (StringUtils.equals("1", useType)) {
            if (list.size() == 0) {
                Assert.isTrue(false, "该身份证对应的用户不存在");
            }
        }
        return list;
    }

    @ApiOperation(value = "通过使用类型，验证手机号码是否有效")
    public List<DoctorPo> verifyPhoneNumber(String phoneNumber, String useType) {
        Assert.isTrue(Utils.isValidMobileNumber(phoneNumber), "手机号格式错误");
        DoctorRequest params = new DoctorRequest();
        params.setPhoneNumber(phoneNumber);
        List<DoctorPo> list = this.listDoctorPo(params);
        if (list.size() > 1) {
            Assert.isTrue(false, "重复存在该用户，请联系管理员处理");
        }
        if (StringUtils.equals("0", useType)) {
            if (list.size() == 1) {
                Assert.isTrue(false, "该手机号码已经存在，无法重新绑定");//系统存在该用户，无法注册
            }
        } else if (StringUtils.equals("1", useType)) {
            if (list.size() == 0) {
                Assert.isTrue(false, "该手机用户不存在");
            }
        }
        return list;
    }

    @ApiOperation(value = "新增对应条目入库，并思考附加及特殊字段，默认处理医师的执业点")
    @Transactional
    public DoctorPo insertDoctorSelective(Doctor record, String osType) {
        Assert.isTrue(Utils.isValidMobileNumber(record.getPhoneNumber()), "手机号格式错误");
        Assert.isTrue(StringUtils.isNotBlank(record.getPassHash()), "用户密码无效");
        Assert.isTrue(record.getPassHash().length() > 2 && record.getPassHash().length() < 17, "密码长度无效");
        this.verifyPhoneNumber(record.getPhoneNumber(), "0");
        //临时去掉
        this.verifyIdCardNo(record.getIdCardNo(), "0");
        //保证必须进行实名认证
        Assert.isTrue(StringUtils.isNotBlank(record.getTrueName()), "没有输入真实姓名");
        String randomSalt = RandomUtils.randomAlphanumeric(8);//随机字母及数字
        record.setPassSalt(randomSalt);//加盐数不加密，反向解密比较麻烦
        record.setPassHash(Utils.aesEncrypt(record.getPassHash(), randomSalt));
        IdCardClass idCardClass = this.commonService.getIdCardClass(record.getIdCardNo());
        String sex = idCardClass.getResult().getSex();
        String area = idCardClass.getResult().getArea();
        String birthday = idCardClass.getResult().getBirthday();
        Date birthdayDate = Utils.parseDate(birthday, "yyyy年MM月dd日");
        record.setSex(sex);
        record.setRemarks(area);
        record.setBirthday(birthdayDate);

        if (record.getProfile() == null) {
            record.setProfile("");
            logger.info("设定个空值插入：{}------>Field 'profile' doesn't have a default value", record.getProfile());
        }
        logger.info("目前传递的医生数据是：{}", record.toJsonString());
        this.daoDoctorMapper.insertSelective(record);
        updateImtoken(record, true);//分配融云token
        DoctorPo model = new DoctorPo(record);//继承new(m)会自动思考，目前直接映射手动处理特殊字段
        model.setToken(this.jwtTokenService.createToken(""+ConstantAll.TYPE_ID_DOCTOR, model.getId().toString(), osType, model.getPhoneNumber()));//分配系统钥匙token
        model.setNonce(RandomUtils.randomNumeric(4));
        model.setAppKey(this.signatureService.getAppKeyBy(ConstantAll.TYPE_ID_DOCTOR, model.getNonce()));
        model.setTimestamp(this.signatureService.getTimestamp());
        model.setSignature(this.signatureService.getSign(model.getAppKey(), model.getNonce(), model.getTimestamp()));
        DoctorPracticePoint doctorPracticePoint = new DoctorPracticePoint();
        doctorPracticePoint.setHospitalId(model.getHospitalId());
        doctorPracticePoint.setCatalogId(model.getCatalogId());
        doctorPracticePoint.setDepartmentId(model.getDepartmentId());
        doctorPracticePoint.setDefaultPoint("1");
        doctorPracticePoint.setTypeId(ConstantAll.TYPE_ID_DOCTOR);
        doctorPracticePoint.setDoctorId(model.getId());
        doctorPracticePoint.setSelectWorkTimeList(record.getSelectWorkTimeList());
        this.practicePointTimeService.insertPracticePoint(doctorPracticePoint, true);//创建时的执业地点及工作时间分配
        return model;
    }

    @ApiOperation(value = "如果没有token，将会获取token,isNeedToken 表示必须获取")
    public String updateImtoken(Doctor record, boolean isNeedToken) {
        String token = null;
        if (StringUtils.isBlank(record.getImtoken()) || isNeedToken) {
            RongYunTokenResult ry = this.serviceRongCloudService.getToken(ConstantAll.TYPE_ID_DOCTOR, record.getId(), record.getTrueName(), record.getAvatarUrl());
            if (ry == null) {
                Assert.isTrue(false, "获取第三方即时通讯工具token错误");
            } else {
                Assert.isTrue(ry.getCode() == 200, ry.getMsg());
            }
            record.setImtoken(ry.getToken());
            record.setGmtModified(new Date());
            this.updateDoctorSelective(record);
            token = record.getImtoken();
        }
        return token;
    }

    @ApiOperation(value = "验证手机号码跟验证码是否正确，然后直接更新手机号码")
    public Doctor updatePhoneNumberByVerifyCodeId(String phoneNumber, String verifyCode, long id) {
        smsService.checkCode(phoneNumber, ConstantAll.TYPE_ID_DOCTOR, verifyCode);//验证码验证
        this.verifyPhoneNumber(phoneNumber, "0");//保证手机没有被使用
        Doctor record = new Doctor();
        record.setId(id);
        record.setPhoneNumber(phoneNumber);
        record.setGmtModified(new Date());
        record.setRemarks("用户更换手机号码");
        this.updateDoctorSelective(record);
        return record;
    }

    @ApiOperation(value = "用户通过Id进行调整信息的任何修改接口")
    public Doctor updatePasswordById(String passwordOld, String password, long id) {
        Assert.isTrue(StringUtils.isNotBlank(passwordOld), "请输入旧的密码");
        Assert.isTrue(StringUtils.isNotBlank(password) && password.length() > 5 && password.length() < 17, "新登录密码长度无效");
        Doctor record = this.selectByPrimaryKey(id);
        logger.error("这里有个时区漂移问题尚待解决！！！");
        logger.info("【修改密码前】record:{} gmtCreate：{} gmtModified：{}", record, Utils.formatDate(record.getGmtCreate(), "yyyy-MM-dd HH:mm:ss"), Utils.formatDate(record.getGmtModified(), "yyyy-MM-dd HH:mm:ss"));
        Assert.isTrue(record != null, "指定用户Id没有找到对应的用户");
        Assert.isTrue(StringUtils.equals(record.getPassHash(), Utils.aesEncrypt(passwordOld, record.getPassSalt())), "原登录密码无效");
        record.setPassHash(Utils.aesEncrypt(password, record.getPassSalt()));
        Date date = new Date();
        record.setGmtModified(date);
        logger.info("【修改密码时】date:{}", Utils.formatDate(date, "yyyy-MM-dd hh:mm:ss"));
        record.setRemarks("用户修改密码");
        this.updateDoctorSelective(record);
        record.thinkField();//完事后的思考及隐蔽
        logger.info("【修改密码完】record:{} gmtCreate：{} gmtModified：{}", record, Utils.formatDate(record.getGmtCreate(), "yyyy-MM-dd HH:mm:ss"), Utils.formatDate(record.getGmtModified(), "yyyy-MM-dd HH:mm:ss"));
        return record;
    }

    @ApiOperation(value = "用户通过Id进行调整信息的任何修改接口")
    public int updateAnyInfoById(Doctor record) {
        Doctor doctor = this.selectByPrimaryKey(record.getId());
        Assert.isTrue(doctor != null, "指定用户Id没有找到对应的用户");
        if (StringUtils.isNotBlank(record.getPassHash())) {
            record.setPassHash(Utils.aesEncrypt(record.getPassHash(), doctor.getPassSalt()));// record.setPassword(DigestUtils.md5Hex(record.getPassword()));
        }
        return this.updateDoctorSelective(record);
    }

    public int updateDoctorSelective(Doctor record) {
        return this.daoDoctorMapper.updateByPrimaryKeySelective(record);
    }

    @ApiOperation(value = "输入实名信息，获取对应的ID")
    public Long getIdByTrueInfo(String phoneNumber, String trueName, String idCardNo) {
        Assert.isTrue(Utils.isValidMobileNumber(phoneNumber), "手机号格式错误");
        Assert.isTrue(StringUtils.isNotBlank(trueName), "真实名字有误");
        Assert.isTrue(idCardNo.trim().length() == 15 || idCardNo.trim().length() == 18, "身份证号长度无效");
        DoctorRequest params = new DoctorRequest();
        params.setPhoneNumber(phoneNumber);
        params.setTrueName(trueName);
        params.setIdCardNo(idCardNo);
        List<Doctor> list = this.listDoctor(params);
        Assert.isTrue(list.size() == 1, "该用户并不合法存在");
        return list.get(0).getId();
    }

    public DoctorPo loginByVerifyCode(DoctorRequest params, String verifyCode, String osType) {
        List<DoctorPo> list = this.verifyPhoneNumber(params.getPhoneNumber(), "1");
        DoctorPo model = list.get(0);
        smsService.checkCode(model.getPhoneNumber(), ConstantAll.TYPE_ID_DOCTOR, verifyCode);
        ToolShareService.statusException(model.getStatus(), model.getId().toString());//验证状态是否异常
        updateImtoken(model, false);
        model.thinkField();//继承new(m)会自动思考，目前直接映射手动处理特殊字段
        PracticeRequest paramsPr = new PracticeRequest();
        paramsPr.setTypeId(ConstantAll.TYPE_ID_DOCTOR);
        paramsPr.setDoctorId(model.getId());
        List<PracticePointPo> listP = this.practicePointTimeService.listPracticePointPo(paramsPr);
        if (listP != null) {
            model.setPracticePointList(listP);
        }

        model.setToken(this.jwtTokenService.createToken(""+ConstantAll.TYPE_ID_DOCTOR, model.getId().toString(), osType, model.getPhoneNumber()));
        model.setNonce(RandomUtils.randomNumeric(4));
        model.setAppKey(this.signatureService.getAppKeyBy(ConstantAll.TYPE_ID_DOCTOR, model.getNonce()));
        model.setTimestamp(this.signatureService.getTimestamp());
        model.setSignature(this.signatureService.getSign(model.getAppKey(), model.getNonce(), model.getTimestamp()));
        return model;
    }

    public DoctorPo loginByPassword(DoctorRequest params, String password, String osType, boolean onlyVerify) {
        List<DoctorPo> list = this.verifyPhoneNumber(params.getPhoneNumber(), "1");
        DoctorPo model = list.get(0);
        Assert.isTrue(StringUtils.equals(Utils.aesEncrypt(password, model.getPassSalt()), model.getPassHash()), "密码无效，请重新输入");
        ToolShareService.statusException(model.getStatus(), model.getId().toString());//验证状态是否异常
        if (!onlyVerify) updateImtoken(model, false);
        model.thinkField();//继承new(m)会自动思考，目前直接映射手动处理特殊字段
        if (!onlyVerify) {
            PracticeRequest paramsPr = new PracticeRequest();
            paramsPr.setTypeId(ConstantAll.TYPE_ID_DOCTOR);
            paramsPr.setDoctorId(model.getId());
            List<PracticePointPo> listP = this.practicePointTimeService.listPracticePointPo(paramsPr);
            if (listP != null) {
                model.setPracticePointList(listP);
            }
            model.setToken(this.jwtTokenService.createToken(""+ConstantAll.TYPE_ID_DOCTOR, model.getId().toString(), osType, model.getPhoneNumber()));
            model.setNonce(RandomUtils.randomNumeric(4));
            model.setAppKey(this.signatureService.getAppKeyBy(ConstantAll.TYPE_ID_DOCTOR, model.getNonce()));
            model.setTimestamp(this.signatureService.getTimestamp());
            model.setSignature(this.signatureService.getSign(model.getAppKey(), model.getNonce(), model.getTimestamp()));
        }
        return model;
    }

    @ApiOperation(value = "修改用户手机号码，并上传审核图片")
    public Doctor adjustRegisterInfo(MultipartFile file, Long id, String phoneNumber, String password, boolean check) {
        Assert.isTrue(file != null, "上传审核图片无效");
        Doctor record = this.selectByPrimaryKey(id);
        Assert.isTrue(record != null, "传递的ID无效，无法查询到对应的医生");
        if (StringUtils.isNotBlank(phoneNumber)) {
            this.verifyPhoneNumber(phoneNumber, "0");//新的手机号码不存在才更新 实际类似于库中无0
            record.setPhoneNumber(phoneNumber);
        }
        if (StringUtils.isNotBlank(password)) {
            record.setPassHash(Utils.aesEncrypt(password, record.getPassSalt()));
        }
        String url = uploader.upload(file);
        record.setCheckUrl(url);
        record.setGmtModified(new Date());
        if (check) {
            record.setStatus("A");//状态（0正常有效 审核通过 1删除 2未提交 点击保存 4无效 5未通过 A审核中 未审核  B未复审 复审中 未通过的提交
        }
        this.updateDoctorSelective(record);
        DoctorPo modelPo = new DoctorPo(record);
        return modelPo;
    }

    @ApiOperation(value = "后台管理系统新增医生信息")
    @Transactional
    public DoctorPo insertDoctorWithPic(Doctor record, MultipartFile avatarPic, MultipartFile photoPic, MultipartFile idCardPic, Long userId) {
        String avatarPicImagesUrl = "";
        String photoPicImagesUrl = "";
        String idCardPicImagesUrl = "";
        if (avatarPic != null) {
            avatarPicImagesUrl = this.uploader.upload(avatarPic);//上传头像图片到服务器
        }
        if (photoPic != null) {
            photoPicImagesUrl = this.uploader.upload(photoPic);//上传近照图片到服务器
        }
        if (idCardPic != null) {
            idCardPicImagesUrl = this.uploader.upload(idCardPic);//上传手持身份证图片到服务器
        }

        Assert.isTrue(Utils.isValidMobileNumber(record.getPhoneNumber()), "手机号格式错误");
        this.verifyPhoneNumber(record.getPhoneNumber(), "0");
        //临时去掉
        this.verifyIdCardNo(record.getIdCardNo(), "0");
        //保证必须进行实名认证
        Assert.isTrue(StringUtils.isNotBlank(record.getTrueName()), "没有输入真实姓名");
        IdCardClass idCardClass = this.commonService.getIdCardClass(record.getIdCardNo());
        String sex = idCardClass.getResult().getSex();
        String area = idCardClass.getResult().getArea();
        String birthday = idCardClass.getResult().getBirthday();
        Date birthdayDate = Utils.parseDate(birthday, "yyyy年MM月dd日");
        record.setSex(sex);
        record.setRemarks(area);
        record.setBirthday(birthdayDate);

        if (record.getProfile() == null) {
            record.setProfile("");
            logger.info("设定个空值插入：{}------>Field 'profile' doesn't have a default value", record.getProfile());
        }
        record.setAvatarUrl(avatarPicImagesUrl);
        record.setPhotoUrl(photoPicImagesUrl);
        record.setCheckUrl(idCardPicImagesUrl);
        logger.info("目前传递的医生数据是：{}", record.toJsonString());
        this.insertSelective(record);
        DoctorPo model = new DoctorPo(record);//继承new(m)会自动思考，目前直接映射手动处理特殊字段
        DoctorPracticePoint doctorPracticePoint = new DoctorPracticePoint();
        doctorPracticePoint.setHospitalId(model.getHospitalId());
        doctorPracticePoint.setCatalogId(model.getCatalogId());
        doctorPracticePoint.setDepartmentId(model.getDepartmentId());
        doctorPracticePoint.setDefaultPoint("1");
        doctorPracticePoint.setTypeId(ConstantAll.TYPE_ID_DOCTOR);
        doctorPracticePoint.setDoctorId(model.getId());
        doctorPracticePoint.setSelectWorkTimeList(record.getSelectWorkTimeList());
        this.practicePointTimeService.insertPracticePoint(doctorPracticePoint, true);//创建时的执业地点及工作时间分配

        AuditInfo auditInfo = new AuditInfo();
        auditInfo.setAuditType((int)ConstantAll.TYPE_ID_DOCTOR);
        auditInfo.setTableId(model.getId());
        auditInfo.setOperate(model.getStatus());
        auditInfo.setRemarks(model.getRemarks());
        User user = this.serviceUserService.selectByPrimaryKey(userId);//根据userid获取当前操作人的信息
        if (user != null) {
            auditInfo.setUserId(user.getId());
            auditInfo.setUserName(user.getTrueName());
        }
        this.auditInfoService.insertAuditInfo(auditInfo);//新增审核信息表
        return model;
    }

    @ApiOperation(value = "后台管理系统更新医生信息")
    @Transactional
    public DoctorPo updateDoctorWithPic(Doctor record, MultipartFile avatarPic, MultipartFile photoPic, MultipartFile idCardPic, Long userId) {
        String avatarPicImagesUrl = "";
        String photoPicImagesUrl = "";
        String idCardPicImagesUrl = "";
        if (avatarPic != null) {
            avatarPicImagesUrl = this.uploader.upload(avatarPic);//上传头像图片到服务器
        }
        if (photoPic != null) {
            photoPicImagesUrl = this.uploader.upload(photoPic);//上传近照图片到服务器
        }
        if (idCardPic != null) {
            idCardPicImagesUrl = this.uploader.upload(idCardPic);//上传手持身份证图片到服务器
        }

        //更新医生基本信息
        Assert.isTrue(Utils.isValidMobileNumber(record.getPhoneNumber()), "手机号格式错误");
        this.verifyPhoneNumber(record.getPhoneNumber(), "0");
        //临时去掉
        this.verifyIdCardNo(record.getIdCardNo(), "0");
        //保证必须进行实名认证
        Assert.isTrue(StringUtils.isNotBlank(record.getTrueName()), "没有输入真实姓名");
        IdCardClass idCardClass = this.commonService.getIdCardClass(record.getIdCardNo());
        String sex = idCardClass.getResult().getSex();
        String area = idCardClass.getResult().getArea();
        String birthday = idCardClass.getResult().getBirthday();
        Date birthdayDate = Utils.parseDate(birthday, "yyyy年MM月dd日");
        record.setSex(sex);
        record.setRemarks(area);
        record.setBirthday(birthdayDate);

        if (record.getProfile() == null) {
            record.setProfile("");
            logger.info("设定个空值插入：{}------>Field 'profile' doesn't have a default value", record.getProfile());
        }
        record.setAvatarUrl(avatarPicImagesUrl);
        record.setPhotoUrl(photoPicImagesUrl);
        record.setCheckUrl(idCardPicImagesUrl);
        record.setGmtModified(new Date());
        logger.info("目前传递的医生数据是：{}", record.toJsonString());
        int count = this.updateDoctorSelective(record);
        DoctorPo model = null;
        if (count > 0) {
            model = new DoctorPo(record);//继承new(m)会自动思考，目前直接映射手动处理特殊字段
            DoctorPracticePoint doctorPracticePoint = new DoctorPracticePoint();
            doctorPracticePoint.setHospitalId(model.getHospitalId());
            doctorPracticePoint.setCatalogId(model.getCatalogId());
            doctorPracticePoint.setDepartmentId(model.getDepartmentId());
            doctorPracticePoint.setDefaultPoint("1");
            doctorPracticePoint.setTypeId(ConstantAll.TYPE_ID_DOCTOR);
            doctorPracticePoint.setDoctorId(model.getId());
            doctorPracticePoint.setSelectWorkTimeList(record.getSelectWorkTimeList());
            doctorPracticePoint.setGmtModified(new Date());
            this.practicePointTimeService.updatePracticePoint(doctorPracticePoint);//创建时的执业地点及工作时间分配

            AuditInfo auditInfo = new AuditInfo();
            auditInfo.setAuditType((int)ConstantAll.TYPE_ID_DOCTOR);
            auditInfo.setTableId(model.getId());
            auditInfo.setOperate(model.getStatus());
            auditInfo.setRemarks(model.getRemarks());
            User user = this.serviceUserService.selectByPrimaryKey(userId);//根据userid获取当前操作人的信息
            if (user != null) {
                auditInfo.setUserId(user.getId());
                auditInfo.setUserName(user.getTrueName());
            }
            this.auditInfoService.insertAuditInfo(auditInfo);//新增审核信息表
        }
        return model;
    }

    @ApiOperation(value = "后台管理系统启用/停用医生信息")
    public Long enableOrDisableDoctor(Doctor record) {
        logger.info("目前传递的医生数据是：{}", record.toJsonString());
        //启用/停用医生
        record.setGmtModified(new Date());
        this.updateDoctorSelective(record);
        return record.getId();
    }

    @ApiOperation(value = "后台管理系统删除医生信息")
    @Transactional
    public Long deleteDoctor(Doctor record, Long typeId) {
        logger.info("目前传递的医生数据是：{}", record.toJsonString());
        //删除医生对应执业点信息和执业时间信息
        PracticeRequest params = new PracticeRequest();
        params.setDoctorId(record.getId());
        params.setTypeId(typeId);
        this.practicePointTimeService.deletePracticePointByDoctorId(params);
        //删除医生信息 逻辑删除
        record.setStatus("1");
        record.setGmtModified(new Date());
        this.updateDoctorSelective(record);
        return record.getId();
    }

    @ApiOperation(value = "新增医生职称信息")
    public int insertDoctorTitle(DoctorTitle record) {
        logger.info("目前传递的医生职称信息是：{}", record.toJsonString());
        return this.daoDoctorTitleMapper.insertSelective(record);
    }

    @ApiOperation(value = "修改医生职称信息")
    public int updateDoctorTitle(DoctorTitle record) {
        logger.info("目前传递的医生职称信息是：{}", record.toJsonString());
        record.setGmtModified(new Date());
        return this.daoDoctorTitleMapper.updateByPrimaryKeySelective(record);
    }

    @ApiOperation(value = "停用/启用医生职称信息")
    public int enabelOrDisableDoctorTitle(DoctorTitle record) {
        logger.info("目前传递的医生职称信息是：{}", record.toJsonString());
        record.setGmtModified(new Date());
        return this.daoDoctorTitleMapper.updateByPrimaryKeySelective(record);
    }

    @ApiOperation(value = "通过id查询获取医生职称信息")
    public DoctorTitle getDoctorTitleById(Long doctorTitleId) {
        logger.info("目前传递的医生职称信息是：{}", doctorTitleId);
        return this.daoDoctorTitleMapper.selectByPrimaryKey(doctorTitleId);
    }

    @ApiOperation(value = "新增医生职称信息")
    public int insertSelective(Doctor doctor) {
        logger.info("目前传递的医生信息是：{}", doctor);
        return this.daoDoctorMapper.insertSelective(doctor);
    }

    // 根据条件查询出门就诊列表
    @ApiOperation(value = "根据条件获取医生对应医院,科室信息 列表,主要是可以批量查询")
    public List<DoctorPo> listDoctorHospitalPo(DoctorRequest params) {
        return this.daoDoctorMapper.listDoctorHospitalPo(params);
    }

}
