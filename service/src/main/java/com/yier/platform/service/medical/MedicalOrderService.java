package com.yier.platform.service.medical;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.yingzhuo.carnival.id.IdGenerator;
import com.google.common.collect.Lists;
import com.yier.platform.common.costant.ConstantAll;
import com.yier.platform.common.model.*;
import com.yier.platform.common.requestParam.DoctorRequest;
import com.yier.platform.common.requestParam.MedicalDateRequest;
import com.yier.platform.common.requestParam.MedicalOrderRequest;
import com.yier.platform.common.requestParam.UserAddressRequest;
import com.yier.platform.common.util.JsonUtils;
import com.yier.platform.common.util.Utils;
import com.yier.platform.dao.MedicalOrderMapper;
import com.yier.platform.service.*;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 上门问诊订单 service
 */
@Service
public class MedicalOrderService {
    private final Logger logger = LoggerFactory.getLogger(MedicalOrderService.class);
    @Autowired
    private MedicalOrderMapper daoMedicalOrderMapper;
    @Autowired
    private  MedicalDateService medicalDateService;
    @Autowired
    private OrderDrugsService orderDrugsService;
    @Autowired
    private PatientService patientService;
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private PharmacyService pharmacyService;
    @Autowired
    private Uploader uploader;
    @Autowired
    private AppPushService appPushService;
    @Autowired
    private ToolShareService toolShareService;
    @Autowired
    private UserAddressService serviceUserAddressService;
    @Autowired
    private IdGenerator<Long> idGenerator;
    @Autowired
    private RedisService redisService;
    @Autowired
    private RedisLogService redisLogService;//缓存log记录情况
    @Autowired
    private RedissonClient redissonClient;//锁及解锁


    @ApiOperation(value = "创建患者[取消支付]")
    @Transactional
    public MedicalOrder createPatientPaymentCancel(MedicalOrder record){
        String key = ConstantAll.REDIS_DOCTOR_APPOINT_LOCK_ORDER+record.getPatientId()+ConstantAll.REDIS_DOCTOR_APPOINT_LOCK_ORDER_PART_ORDER+record.getId();
        if(this.redisService.hasRedisKey(key)){
            this.redisService.deleteRedisByKey(key);
            redisLogService.insertSelective(new RedisLog(ConstantAll.LOG_MEDICAL_ORDER,ConstantAll.LOG_MEDICAL_ORDER_SUB_PAYMENT_LOCK,key,"弱化对应的内容",0L,ConstantAll.STRING_EMPTY,ConstantAll.LOG_REMARK_DELETE+"--患者点击取消支付"));

        }
        MedicalOrder currentOrder = this.selectByPrimaryKey(record.getId());
        Assert.isTrue(StringUtils.equalsIgnoreCase(currentOrder.getStatus(),ConstantAll.ORDER_STATUS_APPPOINT),"只能取消[就诊订单待支付]的订单! (目前状态是："+currentOrder.getStatus()+")");

        Date currentDate = new Date();
        MedicalOrder medicalOrder = new MedicalOrder();
        medicalOrder.setId(record.getId());
        medicalOrder.setStatus(ConstantAll.ORDER_STATUS_PATIENT_PAYMENT_CANCEL);//预约确认
        medicalOrder.setOverType(ConstantAll.ORDER_END_TYPE_PATIENT_PAYMENT_CANCEL);
        medicalOrder.setOverTime(currentDate);
        medicalOrder.setRemarks("患者[取消支付]");
        medicalOrder.setGmtModified(currentDate);
        this.updateByPrimaryKeySelective(medicalOrder);

        MedicalDate medicalDate = new MedicalDate();
        medicalDate.setId(currentOrder.getMedicalDateId());
        medicalDate.setLockNum(-1);//添加预约量 appointment = appointment - #{appointment} 作用  此时要统筹考虑 订单锁定超时的问题
        medicalDate.setGmtModified(currentDate);
        medicalDate.setRemarks("患者[取消支付] 预约锁定-1");
        this.medicalDateService.updateLockNumSelective(medicalDate);
        return record;
    }
    @ApiOperation(value = "创建患者取消")
    @Transactional
    public MedicalOrder createPatientCancel(MedicalOrder record){
        MedicalOrderRequest medicalOrderRequest = new MedicalOrderRequest();
        medicalOrderRequest.setMedicalOrderId(record.getId());
        MedicalOrder currentOrder = this.selectMedicalOrderByIdKey(medicalOrderRequest);
        List<String> considerStatus = Lists.newArrayList();
        considerStatus.add(ConstantAll.ORDER_STATUS_APPPOINT_PAYMENT);
        considerStatus.add(ConstantAll.ORDER_STATUS_APPPOINT_CONFIIRM);
        considerStatus.add(ConstantAll.ORDER_STATUS_VISIT_DOOR);
        Assert.isTrue(considerStatus.contains(currentOrder.getStatus()),"目前状态,您无法进行有效操作!");


        Date currentDate = new Date();
//        Utils.formatDate()
        if(StringUtils.equalsIgnoreCase(currentOrder.getStatus(),ConstantAll.ORDER_STATUS_APPPOINT_PAYMENT)  ){
            MedicalOrder medicalOrder = new MedicalOrder();
            medicalOrder.setId(record.getId());
            medicalOrder.setStatus(ConstantAll.ORDER_STATUS_PATIENT_CANCEL);//预约确认
            medicalOrder.setStatusDetail(ConstantAll.ORDER_STATUS_PATIENT_CANCEL);//预约确认
            medicalOrder.setOverType(ConstantAll.ORDER_END_TYPE_PATIENT_CACEL_NO_COFNIRM);
            medicalOrder.setOverTime(currentDate);
            medicalOrder.setRemarks("患者取消");
            medicalOrder.setGmtModified(new Date());
            this.updateByPrimaryKeySelective(medicalOrder);

        }
        else if(currentOrder.getWorkFrom().getTime() - currentDate.getTime() > ConstantAll.VALUE_TIME_48_HOUR*ConstantAll.VALUE_MICROSECOND_1000){
            MedicalOrder medicalOrder = new MedicalOrder();
            medicalOrder.setId(record.getId());
            medicalOrder.setStatus(ConstantAll.ORDER_STATUS_PATIENT_CANCEL);//[超时未在]
            medicalOrder.setStatusDetail(ConstantAll.ORDER_STATUS_PATIENT_CANCEL);//[超时未在]
            medicalOrder.setOverType(ConstantAll.ORDER_END_TYPE_PATIENT_CACEL_MORE_48_HOUR);
            medicalOrder.setOverTime(currentDate);
            medicalOrder.setRemarks("患者取消");
            medicalOrder.setGmtModified(new Date());
            this.updateByPrimaryKeySelective(medicalOrder);
        }
        else if(currentOrder.getWorkFrom().getTime() - currentDate.getTime() > ConstantAll.VALUE_TIME_24_HOUR*ConstantAll.VALUE_MICROSECOND_1000){
            MedicalOrder medicalOrder = new MedicalOrder();
            medicalOrder.setId(record.getId());
            medicalOrder.setStatus(ConstantAll.ORDER_STATUS_PATIENT_CANCEL);//预约确认
            medicalOrder.setStatusDetail(ConstantAll.ORDER_STATUS_PATIENT_CANCEL);//预约确认
            medicalOrder.setOverType(ConstantAll.ORDER_END_TYPE_PATIENT_CACEL_24_48_HOUR);
            medicalOrder.setOverTime(currentDate);
            medicalOrder.setRemarks("患者取消");
            medicalOrder.setGmtModified(new Date());
            this.updateByPrimaryKeySelective(medicalOrder);
        }
        else if(currentOrder.getWorkFrom().getTime() - currentDate.getTime() > 0){
            MedicalOrder medicalOrder = new MedicalOrder();
            medicalOrder.setId(record.getId());
            medicalOrder.setStatus(ConstantAll.ORDER_STATUS_PATIENT_CANCEL);//预约确认
            medicalOrder.setStatusDetail(ConstantAll.ORDER_STATUS_PATIENT_CANCEL);//预约确认
            medicalOrder.setOverType(ConstantAll.ORDER_END_TYPE_PATIENT_CACEL_LESS_24);
            medicalOrder.setOverTime(currentDate);
            medicalOrder.setRemarks("患者取消");
            medicalOrder.setGmtModified(new Date());
            this.updateByPrimaryKeySelective(medicalOrder);
        }
        else if(currentOrder.getWorkFrom().getTime() - currentDate.getTime() <= 0){
            MedicalOrder medicalOrder = new MedicalOrder();
            medicalOrder.setId(record.getId());
            medicalOrder.setStatus(ConstantAll.ORDER_STATUS_APPPOINT_PATIENT_PAST_DUE);//预约过期
            medicalOrder.setStatusDetail(ConstantAll.ORDER_STATUS_APPPOINT_PATIENT_PAST_DUE);//预约过期
            medicalOrder.setOverType(ConstantAll.ORDER_END_TYPE_PATIENT_APPPOINT_PAST_DUE);
            medicalOrder.setOverTime(currentDate);
            medicalOrder.setRemarks("此时患者取消--未在规定时间等待医生上门就诊");
            medicalOrder.setGmtModified(new Date());
            this.updateByPrimaryKeySelective(medicalOrder);
        }
        else{
            Assert.isTrue(false,"已经到了约定的就诊日期是:"+Utils.formatDateToyyyyMMdd(currentOrder.getPlanTime())+"无法取消,若有特殊情况,请联系医生,并说明情况." );
        }
        if(currentOrder.getDoctorId()!=null && currentOrder.getDoctorId().longValue()>0){
            //推送给医生
            List<String> listPushUserIdOld = Lists.newArrayList();
            listPushUserIdOld.add(toolShareService.getAppInfoByTypeId(ConstantAll.TYPE_ID_DOCTOR) + currentOrder.getDoctorId());
            appPushService.pushListUserIdApp(ConstantAll.TYPE_ID_DOCTOR, "[患者取消]"+Utils.formatDateToyyyyMMdd(currentOrder.getPlanTime())+currentOrder.getWorkTime()+"预约" , "请您及时调整自己的安排", listPushUserIdOld);

        }

        MedicalDate medicalDate = new MedicalDate();
        medicalDate.setId(currentOrder.getMedicalDateId());
        medicalDate.setAppointment(-1);//添加预约量 appointment = appointment - #{appointment} 作用  此时要统筹考虑 订单锁定超时的问题
        medicalDate.setGmtModified(currentOrder.getGmtModified());
        medicalDate.setRemarks("患者取消 预约-1");
        this.medicalDateService.updateLockNumSelective(medicalDate);
        return record;
    }



    @ApiOperation(value = "创建医生取消")
    @Transactional
    public MedicalOrder createDoctorCancel(MedicalOrder record){
        Assert.isTrue( StringUtils.isNotBlank(record.getRemind()),"一定要填写理由![参数remind]");
        MedicalOrderRequest medicalOrderRequest = new MedicalOrderRequest();
        medicalOrderRequest.setMedicalOrderId(record.getId());
        MedicalOrder currentOrder = this.selectMedicalOrderByIdKey(medicalOrderRequest);
        Date currentDate = new Date();
        List<String> considerStatus = Lists.newArrayList();
        considerStatus.add(ConstantAll.ORDER_STATUS_APPPOINT_PAYMENT);
        considerStatus.add(ConstantAll.ORDER_STATUS_APPPOINT_CONFIIRM);
        Assert.isTrue(considerStatus.contains(currentOrder.getStatus()),"目前状态,您无法进行有效操作!");
        //Assert.isTrue(StringUtils.equalsIgnoreCase(currentMedicalOrder.getStatusDetail(),ConstantAll.ORDER_STATUS_APPPOINT_CONFIIRM),"只有[确认预约]的订单方可点击!");
        if(currentOrder.getWorkFrom().getTime() - currentDate.getTime() > ConstantAll.VALUE_TIME_48_HOUR*ConstantAll.VALUE_MICROSECOND_1000){
            MedicalOrder medicalOrder = new MedicalOrder();
            medicalOrder.setId(record.getId());
            medicalOrder.setStatus(ConstantAll.ORDER_STATUS_DOCTOR_CANCEL);//预约确认
            medicalOrder.setStatusDetail(ConstantAll.ORDER_STATUS_DOCTOR_CANCEL);//预约确认
            medicalOrder.setOverType(ConstantAll.ORDER_END_TYPE_DOCTOR_CACEL_MORE_48_HOUR);
            medicalOrder.setOverTime(currentDate);
            medicalOrder.setRemind(record.getRemind());
            medicalOrder.setRemarks("医生取消");
            medicalOrder.setGmtModified(new Date());
            this.updateByPrimaryKeySelective(medicalOrder);
        }
        else{
            Assert.isTrue(false,"已经到了约定的就诊日期是:"+Utils.formatDateToyyyyMMdd(currentOrder.getPlanTime())+",离就诊日期在两天内不能取消" );
        }
        //推送给患者
        List<String> listPushUserIdOld = Lists.newArrayList();
        listPushUserIdOld.add(toolShareService.getAppInfoByTypeId(ConstantAll.TYPE_ID_PATIENT) + currentOrder.getPatientId());
        appPushService.pushListUserIdApp(ConstantAll.TYPE_ID_PATIENT,  "[医生取消]"+Utils.formatDateToyyyyMMdd(currentOrder.getPlanTime())+currentOrder.getWorkTime()+"预约" , "请您及时调整自己的安排", listPushUserIdOld);



        MedicalDate medicalDate = new MedicalDate();
        medicalDate.setId(record.getMedicalDateId());
        medicalDate.setAppointment(-1);//添加预约量 appointment = appointment - #{appointment} 作用  此时要统筹考虑 订单锁定超时的问题
        medicalDate.setGmtModified(record.getGmtModified());
        medicalDate.setRemarks("医生取消 预约-1");
        this.medicalDateService.updateLockNumSelective(medicalDate);
        return record;
    }


    @ApiOperation(value = "预约订单 医生 患者未在家")
    @Transactional
    public MedicalOrder createPatientDisappearConfirm(MedicalOrder record){
        Assert.isTrue(record.getId()!=null,"请给出您要处理的出诊订单ID");
        Assert.isTrue(record.getDoctorLat()!=null,"请提供目前医生的经度坐标!");
        Assert.isTrue(record.getDoctorLng()!=null,"请提供目前医生的纬度坐标!");

        MedicalOrder currentOrder = this.selectByPrimaryKey(record.getId());
        Assert.isTrue(StringUtils.equalsIgnoreCase(currentOrder.getStatusDetail(),ConstantAll.ORDER_STATUS_VISIT_DOOR),"只有[到达地点]的订单方可点击[患者未在家]!");

        String keyStartServiceRcordStartTime = ConstantAll.REDIS_DOCTOR_APPOINT_START_SERVICE_WAIT_TIME+record.getId();
        MedicalOrder result = JsonUtils.fromJson(redisService.getValueRedisByKey(keyStartServiceRcordStartTime), new TypeReference<MedicalOrder>() {
        });
        Assert.isTrue(result != null,"已经操作过,请勿再重复点击患者未在家操作!");

        Date currentDate = new Date();
        long differMicroSecond = result.getGmtModified().getTime() - currentDate.getTime();//result.getGmtModified().getTime() 缓存中的数据对象,跟DB中的模型修改没有直接关系,DB就是后台悄悄更改也不影响缓存中的数据
        long remain = ConstantAll.VALUE_MEDICAL_ORDER_START_SERVICE_WAIT_TIME + differMicroSecond /ConstantAll.VALUE_MICROSECOND_1000;
        MedicalOrder medicalOrder = new MedicalOrder();
        String remark = "";
        if(remain>0){
            remark = "患者未在家 医生没有等够"+ConstantAll.VALUE_MEDICAL_ORDER_START_SERVICE_WAIT_TIME+"秒,提前离开,责任在医生";
            medicalOrder.setOverTime(currentDate);
            medicalOrder.setOverType(ConstantAll.ORDER_END_TYPE_DOCTOR_APPPOINT_PAST_DUE);

            medicalOrder.setStatus(ConstantAll.ORDER_STATUS_APPPOINT_DOCTOR_PAST_DUE);//  预约过期
            medicalOrder.setStatusDetail(ConstantAll.ORDER_STATUS_APPPOINT_DOCTOR_PAST_DUE);// 预约过期
        }
        else{
            remark = "患者未在家 医生苦苦等够"+ConstantAll.VALUE_MEDICAL_ORDER_START_SERVICE_WAIT_TIME+"秒,目前离开,责任在患者";
            medicalOrder.setOverTime(currentDate);
            medicalOrder.setOverType(ConstantAll.ORDER_END_TYPE_PATIENT_APPPOINT_PAST_DUE);

            medicalOrder.setStatus(ConstantAll.ORDER_STATUS_APPPOINT_PATIENT_PAST_DUE);//  预约过期
            medicalOrder.setStatusDetail(ConstantAll.ORDER_STATUS_APPPOINT_PATIENT_PAST_DUE);// 预约过期
        }
        medicalOrder.setId(record.getId());
        medicalOrder.setDoctorLeaveLat(record.getDoctorLat());
        medicalOrder.setDoctorLeaveLng(record.getDoctorLng());
        medicalOrder.setRemarks(remark);
        medicalOrder.setGmtModified(currentDate);
        this.updateByPrimaryKeySelective(medicalOrder);
        redisService.deleteRedisByKey(keyStartServiceRcordStartTime);//不用考虑 医生开始服务 记录时间问题
        redisLogService.insertSelective(new RedisLog(ConstantAll.LOG_MEDICAL_ORDER,ConstantAll.LOG_MEDICAL_ORDER_SUB_START_SERVICE,keyStartServiceRcordStartTime,"弱化对应的内容",0L,ConstantAll.STRING_EMPTY,ConstantAll.LOG_REMARK_DELETE+"--医生点击患者未在家"+remark));

        if(remain>0){
            //推送给医生
            List<String> listPushUserIdDoctor = Lists.newArrayList();
            listPushUserIdDoctor.add(toolShareService.getAppInfoByTypeId(ConstantAll.TYPE_ID_DOCTOR) + currentOrder.getPatientId());
            appPushService.pushListUserIdApp(ConstantAll.TYPE_ID_DOCTOR,  "[超时未到] 医生提前"+remain+"秒离开患者驻地" , "[重要提醒:医生已经离开就诊点,责任在医生]", listPushUserIdDoctor);

            //推送给患者
            List<String> listPushUserIdOld = Lists.newArrayList();
            listPushUserIdOld.add(toolShareService.getAppInfoByTypeId(ConstantAll.TYPE_ID_PATIENT) + currentOrder.getPatientId());
            appPushService.pushListUserIdApp(ConstantAll.TYPE_ID_PATIENT,  "[超时未到] 医生提前"+remain+"秒离开患者驻地" , "[重要提醒:医生已经离开就诊点,责任在医生]", listPushUserIdOld);

        }
        else{
            //推送给患者
            List<String> listPushUserIdOld = Lists.newArrayList();
            listPushUserIdOld.add(toolShareService.getAppInfoByTypeId(ConstantAll.TYPE_ID_PATIENT) + currentOrder.getPatientId());
            appPushService.pushListUserIdApp(ConstantAll.TYPE_ID_PATIENT,  "[超时未在] 医生名字:"+currentOrder.getDoctorName() , "[重要提醒:医生已经离开就诊点,责任在您]", listPushUserIdOld);
        }
        return record;
    }

    @ApiOperation(value = "预约订单 患者 确认上门")
    @Transactional
    public MedicalOrder createStartServiceConfirm(MedicalOrder record){
        Assert.isTrue(record.getId()!=null,"请给出您要处理的出诊订单ID");
        MedicalOrder currentOrder = this.selectByPrimaryKey(record.getId());
        Assert.isTrue(StringUtils.equalsIgnoreCase(currentOrder.getStatusDetail(),ConstantAll.ORDER_STATUS_VISIT_DOOR),"只有[到达地点]的订单方可进行[开始服务]!");

        String keyStartServiceRcordStartTime = ConstantAll.REDIS_DOCTOR_APPOINT_START_SERVICE_WAIT_TIME+record.getId();
        Set<String> stringSet = redisService.getRedisKeysByPattern(keyStartServiceRcordStartTime);
        Assert.isTrue(redisService.hasRedisKey(keyStartServiceRcordStartTime)==true,"已经操作过,请勿再重复点击[开始服务]!");
        redisService.deleteRedisByKey(keyStartServiceRcordStartTime);//不用考虑 医生开始服务 记录时间问题
        redisLogService.insertSelective(new RedisLog(ConstantAll.LOG_MEDICAL_ORDER,ConstantAll.LOG_MEDICAL_ORDER_SUB_START_SERVICE,keyStartServiceRcordStartTime,"弱化对应的内容",0L,ConstantAll.STRING_EMPTY,ConstantAll.LOG_REMARK_DELETE+"--患者点击确认预约"));
        MedicalOrder medicalOrder = new MedicalOrder();
        medicalOrder.setId(record.getId());
        medicalOrder.setStatus(ConstantAll.ORDER_STATUS_START_SERVICE_CONFIRM);//患者确认开始服务
        medicalOrder.setStatusDetail(ConstantAll.ORDER_STATUS_START_SERVICE_CONFIRM);//患者确认开始服务
        medicalOrder.setRemarks("患者点击[开始服务]");
        medicalOrder.setGmtModified(new Date());
        this.updateByPrimaryKeySelective(medicalOrder);
        //推送给医生
        List<String> listPushUserIdOld = Lists.newArrayList();
        listPushUserIdOld.add(toolShareService.getAppInfoByTypeId(ConstantAll.TYPE_ID_DOCTOR) + currentOrder.getDoctorId());
        appPushService.pushListUserIdApp(ConstantAll.TYPE_ID_DOCTOR, "[开始服务] 患者:"+currentOrder.getPatientName() +"已经就位" , "请您及时诊断", listPushUserIdOld);

        return record;
    }


    @ApiOperation(value = "预约订单 医生[到达地点]")
    @Transactional
    public MedicalOrder createStartService(MedicalOrder record){
        Assert.isTrue(record.getId()!=null,"请给出您要处理的出诊订单ID");
        Assert.isTrue(record.getDoctorLat()!=null,"请提供目前医生的经度坐标!");
        Assert.isTrue(record.getDoctorLng()!=null,"请提供目前医生的纬度坐标!");
        MedicalOrder currentOrder = this.selectByPrimaryKey(record.getId());
        Assert.isTrue(StringUtils.equalsIgnoreCase(currentOrder.getStatusDetail(),ConstantAll.ORDER_STATUS_APPPOINT_CONFIIRM),"只有[预约成功]的订单方可开始服务!");
        Date currentDate = new Date();
//todo: 预约开始时间暂时放开        Assert.isTrue(Math.abs(redisMedicalOrder.getWorkFrom().getTime() - currentDate.getTime() )<ConstantAll.VALUE_TIME_1_HOUR *ConstantAll.VALUE_MICROSECOND_1000,Utils.formatDate(redisMedicalOrder.getWorkFrom(),"yyyy年MM月dd日HH时mm分ss秒") + "这个时间左右您才能开始服务，限定提醒！");

        String keyStartServiceRcordStartTime = ConstantAll.REDIS_DOCTOR_APPOINT_START_SERVICE_WAIT_TIME+record.getId();
        Assert.isTrue(redisService.hasRedisKey(keyStartServiceRcordStartTime)==false,"已经操作过,请勿再重复点击到达地点!");
        MedicalOrder medicalOrder = new MedicalOrder();
        medicalOrder.setId(record.getId());
        medicalOrder.setDoctorLat(record.getDoctorLat());
        medicalOrder.setDoctorLng(record.getDoctorLng());
        medicalOrder.setStatus(ConstantAll.ORDER_STATUS_VISIT_DOOR);//到达地点
        medicalOrder.setStatusDetail(ConstantAll.ORDER_STATUS_VISIT_DOOR);//到达地点
        medicalOrder.setRemarks("医生点击[到达地点]");
        medicalOrder.setGmtModified(currentDate);

        redisService.setJsonObjectBy(keyStartServiceRcordStartTime,medicalOrder,ConstantAll.VALUE_CONSIDER_DAY_TIME,ConstantAll.UNIT_CONSIDER);//考虑一整天,redis 都是副本,如果取出修改,并不直接影响DB库的值
        redisLogService.insertSelective(new RedisLog(ConstantAll.LOG_MEDICAL_ORDER,ConstantAll.LOG_MEDICAL_ORDER_SUB_START_SERVICE,keyStartServiceRcordStartTime,JsonUtils.toJsonString(medicalOrder),ConstantAll.VALUE_CONSIDER_DAY_TIME,ConstantAll.UNIT_CONSIDER.toString(),ConstantAll.LOG_REMARK_SET+"--  医生点击开始服务"));
        this.updateByPrimaryKeySelective(medicalOrder);


        //推送给患者
        List<String> listPushUserIdOld = Lists.newArrayList();
        listPushUserIdOld.add(toolShareService.getAppInfoByTypeId(ConstantAll.TYPE_ID_PATIENT) + currentOrder.getPatientId());
        appPushService.pushListUserIdApp(ConstantAll.TYPE_ID_PATIENT,  "[到达地点] 医生名字:"+currentOrder.getDoctorName() , "请您确认[开始服务]", listPushUserIdOld);

        return record;
    }



    @ApiOperation(value = "预约订单确定")
    @Transactional
    public MedicalOrder createAppointConfirm(MedicalOrder record){
        MedicalOrder currentOrder = this.selectByPrimaryKey(record.getId());
        Assert.isTrue(StringUtils.equalsIgnoreCase(currentOrder.getStatusDetail(),ConstantAll.ORDER_STATUS_APPPOINT_PAYMENT),"只有[预约待定]的订单方可进行[预约成功]确认!");

        List<Pharmacy> listAllPharmacy  = pharmacyService.getListAllPharmacy();
        Pharmacy pharmacy = listAllPharmacy.stream().filter(t->t.getHospitalId().longValue()==currentOrder.getHospitalId().longValue()).findFirst().orElse(null);
        Assert.isTrue(pharmacy!=null,"请及时配置您的医院对应的药房,目前并不匹配!");

        MedicalOrder medicalOrder = new MedicalOrder();
        medicalOrder.setId(record.getId());
        medicalOrder.setStatus(ConstantAll.ORDER_STATUS_APPPOINT_CONFIIRM);//预约确认
        medicalOrder.setStatusDetail(ConstantAll.ORDER_STATUS_APPPOINT_CONFIIRM);//预约确认
        medicalOrder.setPharmacyId(pharmacy.getId().toString());
        medicalOrder.setPharmacyName(pharmacy.getName());
        medicalOrder.setPharmacyAddress(pharmacy.getAddress());
        medicalOrder.setPharmacyContact(pharmacy.getContact());
        medicalOrder.setRemarks("医生确认[预约成功]");
        medicalOrder.setGmtModified(new Date());
        this.updateByPrimaryKeySelective(medicalOrder);

        //推送给患者
        List<String> listPushUserIdOld = Lists.newArrayList();
        listPushUserIdOld.add(toolShareService.getAppInfoByTypeId(ConstantAll.TYPE_ID_PATIENT) + currentOrder.getPatientId());
        appPushService.pushListUserIdApp(ConstantAll.TYPE_ID_PATIENT, "[预约成功]"+ Utils.formatDateToyyyyMMdd(currentOrder.getPlanTime())+currentOrder.getWorkTime() , "请您到时在家候诊", listPushUserIdOld);

        return record;
    }

    @ApiOperation(value = "创建预约订单")
    @Transactional
    public MedicalOrder createAppointment(MedicalOrder record, MultipartFile[] file){
        Set<String> stringSet = redisService.getRedisKeysByPattern(ConstantAll.REDIS_DOCTOR_APPOINT_LOCK_ORDER+record.getPatientId()+ConstantAll.REDIS_DOCTOR_APPOINT_LOCK_ORDER_PART_ORDER+"*");
        stringSet.forEach(t->{
            logger.info("缓存情况如此 key:{}  value:{}",t,redisService.getValueRedisByKey(t,false));
            //锁死 redisService.deleteRedisByKey(t,false);
        });

        Assert.isTrue(stringSet.size()<1,"您需要付好款或取消订单才能再预约!");
        Assert.isTrue(record.getPlanTime()!=null,"请传递预约日期!");
        Assert.isTrue(StringUtils.isNotBlank(record.getWorkTime()),"请传递预约上午还是下午!");
        MedicalDateRequest medicalDateRequest = new MedicalDateRequest();
//        medicalDateRequest.setMedicalDateId(record.getMedicalDateId());
        medicalDateRequest.setPlanTime(record.getPlanTime());
        medicalDateRequest.setWorkTime(record.getWorkTime());
        medicalDateRequest.setUserId(record.getDoctorId().toString());
        medicalDateRequest.setTypeId(ConstantAll.TYPE_ID_DOCTOR+"");

        String lockKey = ConstantAll.LOCK_MEDICAL_ORDER + record.getDoctorId()+ Utils.formatDateToyyyyMMdd(record.getPlanTime()) + record.getWorkTime();
        RLock lock = redissonClient.getLock(lockKey);
        logger.info("[预约当天门诊 判定]key名字值为:{} 数量:{} 目前锁的情况：{}",lock.getName(),lock.getHoldCount(),lock.isLocked());
        try{
            boolean isCanlock = lock.tryLock(40,10,TimeUnit.SECONDS);
            if(isCanlock){
//                Assert.isTrue(false,"故意抛出个异常,看下次结果");



                MedicalDatePo medicalDatePo = medicalDateService.getLockMedicalDatePoByIdRequest(medicalDateRequest);
                Assert.isTrue(medicalDatePo!=null, Utils.formatDateToyyyyMMdd(record.getPlanTime())+record.getWorkTime()+" 医生没有就诊安排,无法预约!");
                Assert.isTrue(medicalDatePo.getAppointmentNum() >= 1,Utils.formatDateToyyyyMMdd(record.getPlanTime())+record.getWorkTime()+"医生该日期的上门就诊已经饱和,无法预约!");

                DoctorPo doctor = doctorService.getDoctorHospitalPoById(record.getDoctorId());
                Assert.isTrue(doctor!=null,"医生信息无效");
                Assert.isTrue(doctor.getHospitalDepartmentPo()!=null, " 医生对应的科室无效,请调整!");
                Assert.isTrue(StringUtils.equalsIgnoreCase(doctor.getMedical(),ConstantAll.STATUS_OPEN),"该医生上门就诊已经关闭,无法预约!");

                Patient patient = patientService.selectByPrimaryKey(record.getPatientId());
                Assert.isTrue(patient!=null,"患者信息无效");
                UserAddressRequest userAddressRequest = new UserAddressRequest();
                userAddressRequest.setUserTableId(patient.getId());
                userAddressRequest.setTypeId(ConstantAll.TYPE_ID_PATIENT);
                userAddressRequest.setContact(patient.getPhoneNumber());
                userAddressRequest.setUserName(patient.getTrueName());
                userAddressRequest.setStatus(ConstantAll.STATUS_0);
                UserAddress userAddress = serviceUserAddressService.getFirstUserAddress(userAddressRequest);
                Assert.isTrue(userAddress!=null,"您没有设定有效的地址（电话及名字需要跟您注册信息内容一致）！");
                record.setPatientAddress(userAddress.getAddressJoin());
                record.setPatientLng(userAddress.getLng());
                record.setPatientLat(userAddress.getLat());
                record.setPatientAddressSummary(userAddress.getProvinceName()+userAddress.getCityName()+userAddress.getAreaName());

                if(file!=null && file.length >0){
                    StringBuilder stringBuilder = new StringBuilder();
                    for(int i=0;i<file.length;i++){
                        String url = uploader.upload(file[i]);
                        stringBuilder.append(url).append(",");
                    }
                    record.setMedicalPictures(stringBuilder.toString());
                    record.setMedicalPicturesList(null);//不考虑 图片上传
                }

                if(record.getMedicalPicturesList()!=null && record.getMedicalPicturesList().size()>0){
                    StringBuilder sb = new StringBuilder();
                    record.getMedicalPicturesList().forEach(t->{
                        sb.append(t).append(",");
                    });
                    record.setMedicalPictures(sb.toString());
                }
                String orderNo = idGenerator.nextId().toString();
                record.setPatientIdCardNo(patient.getIdCardNo());
                record.setPatientName(patient.getTrueName());
//        record.setPatientAddress(record.getPatientAddress());
                record.setPatientContact(patient.getPhoneNumber());
                record.setPatientSex(patient.getSex());
                String patientYearOld = "";
                if(patient.getBirthday()!=null){
                    Calendar cal = Calendar.getInstance();
                    int iCurrYear = cal.get(Calendar.YEAR);
                    cal.setTime(patient.getBirthday());
                    int userYear = cal.get(Calendar.YEAR);
                    int yearInfo = iCurrYear - userYear;
                    patientYearOld = yearInfo==0?"":yearInfo+"岁";
                }
                record.setPatientYearOld(patientYearOld);
                record.setPatientAvatarUrl(patient.getAvatarUrl());

                record.setDoctorName(doctor.getTrueName());
                record.setHospitalId(doctor.getHospitalId());
                record.setHospitalName(doctor.getHospitalDepartmentPo().getHospitalName());
                record.setHospitalAddress(doctor.getHospitalDepartmentPo().getHospitalAddress());
                record.setHospitalContact("医院电话号码");
                record.setCatalogId(doctor.getCatalogId());
                record.setDepartmentId(doctor.getDepartmentId());
                record.setDepartmentName(doctor.getHospitalDepartmentPo().getCatalogName() + " " + doctor.getHospitalDepartmentPo().getName());
                record.setDoctorTitle(doctor.getDoctorTitle());
                record.setStatus(ConstantAll.ORDER_STATUS_APPPOINT);
                record.setOrderNo(orderNo);

                record.setMedicalDateId(medicalDatePo.getId());
                Date appointDateFrom =  Utils.getDayTimeHHmmss(medicalDatePo.getPlanTime(),medicalDatePo.getWorkFrom());
                record.setWorkFrom(appointDateFrom);
                Date appointDateTo =  Utils.getDayTimeHHmmss(medicalDatePo.getPlanTime(),medicalDatePo.getWorkTo());
                record.setWorkTo(appointDateTo);

                Date currentDate = new Date();
                record.setGmtCreate(currentDate);
                record.setGmtModified(currentDate);
                record.setRemarks("用户下单[就诊订单待支付]准备付款");
                this.daoMedicalOrderMapper.insertSelective(record);
                medicalDateService.lockForCondition(record);



            }
            else{
                logger.info("目前扑空了，只有等待");
            }
            //Thread.sleep(500L);
        } catch (InterruptedException e) {
            logger.info(e.getMessage(),e);
        } finally {
            if(lock.isHeldByCurrentThread()){
                logger.info("解锁");
                lock.unlock();
            }
        }




//        RLock lock = redissonClient.getLock(lockKey);
//        logger.info("[预约当天门诊 判定]key名字值为:{} 数量:{} 目前锁的情况：{}",lock.getName(),lock.getHoldCount(),lock.isLocked());
//        if(lock.isLocked()){
//            lock.unlock();
//            logger.info("解锁");
//        }
//        lock.tryLock(5,20,TimeUnit.SECONDS);
//        lock.lock();//加锁
//        logger.info("[预约当天门诊 判定]key名字值为:{} 数量:{} 目前锁的情况：{}",lock.getName(),lock.getHoldCount(),lock.isLocked());
//
//


//        lock.unlock();//解锁
        return record;
    }

    //患者端 根据条件查询显示医生信息及详细预约时间信息列表
    public List<MedicalOrderPo> listMedicalOrderPoWithDoctorappointmentInfo(MedicalOrderRequest params){
        logger.info("目前查询条件是:{}",params);
        List<MedicalOrderPo> medicalOrderPoList = daoMedicalOrderMapper.listMedicalOrderPo(params);
        List<Long> doctorIdList = Lists.newArrayList();
        medicalOrderPoList.forEach(t->{
//            t.getPatientPo().thinkField();
            StringBuilder sb = new StringBuilder();
            sb.append(Utils.formatDateToyyyyMMdd(t.getPlanTime()))
                    .append(" ").append(Utils.getWeekInfo(t.getPlanTime())).append(" ").append(t.getWorkTime());
            t.setAppointmentInfo(sb.toString());
            if(StringUtils.isNotBlank(t.getMedicalPictures())){
                t.setMedicalPicturesList(Arrays.asList(t.getMedicalPictures().split(",")));
            }
            else{
                t.setMedicalPicturesList(Lists.newArrayList());
            }
            doctorIdList.add(t.getDoctorId());
        });
        DoctorRequest doctorRequest = new DoctorRequest();
        doctorRequest.setDoctorIdList(doctorIdList);
        List<DoctorPo>  doctorPoList = this.doctorService.listDoctorHospitalPo(doctorRequest);
        medicalOrderPoList.forEach(t->{
            t.setDoctorPo(doctorPoList.stream().filter(doctor->doctor.getId().longValue()==t.getDoctorId().longValue()).findFirst().orElse(null));
        });
        return medicalOrderPoList;
    }
    // 医生端 根据条件查询显示患者信息及详细预约时间信息列表
    public List<MedicalOrderPo> listMedicalOrderPoWithPatienOldappointmentInfo(MedicalOrderRequest params){
        logger.info("目前查询条件是:{}",params);
        List<MedicalOrderPo> medicalOrderPoList = daoMedicalOrderMapper.listMedicalOrderPo(params);
        medicalOrderPoList.forEach(t->{
//            t.getPatientPo().thinkField();
            StringBuilder sb = new StringBuilder();
            sb.append(Utils.formatDateToyyyyMMdd(t.getPlanTime()))
            .append(" ").append(Utils.getWeekInfo(t.getPlanTime())).append(" ").append(t.getWorkTime());
            t.setAppointmentInfo(sb.toString());
            if(StringUtils.isNotBlank(t.getMedicalPictures())){
                t.setMedicalPicturesList(Arrays.asList(t.getMedicalPictures().split(",")));
            }
            else{
                t.setMedicalPicturesList(Lists.newArrayList());
            }
        });
        return medicalOrderPoList;
    }

    public void taskConsiderOrderPastDue(Date currentDate){
        MedicalOrderRequest params = new MedicalOrderRequest();
        List<String> considerStatus = Lists.newArrayList();
        considerStatus.add(ConstantAll.ORDER_STATUS_APPPOINT_PAYMENT);//2
        considerStatus.add(ConstantAll.ORDER_STATUS_APPPOINT_CONFIIRM);//3
        considerStatus.add(ConstantAll.ORDER_STATUS_VISIT_DOOR);//4
        considerStatus.add(ConstantAll.ORDER_STATUS_START_SERVICE_CONFIRM);//5
        considerStatus.add(ConstantAll.ORDER_STATUS_PRESCRIPTION_CHECK);//6
        params.setStatusList(considerStatus);
        params.setQueryWorkDate(currentDate);
        List<MedicalOrder> medicalOrderList = this.listMedicalOrder(params);
        logger.info("查询到的数据是：{}",medicalOrderList);
        medicalOrderList.forEach(order->{
            MedicalOrder medicalOrder = new MedicalOrder();
            medicalOrder.setId(order.getId());
            medicalOrder.setStatusDetail(ConstantAll.ORDER_STATUS_APPPOINT_DOCTOR_PAST_DUE);
            medicalOrder.setStatus(ConstantAll.ORDER_STATUS_APPPOINT_DOCTOR_PAST_DUE);
            medicalOrder.setRemarks(Utils.formatDateToyyyyMMddHHmmss(currentDate)+ "医生预约过期  [超时未到]");
            medicalOrder.setOverType(ConstantAll.ORDER_END_TYPE_DOCTOR_APPPOINT_PAST_DUE);
            medicalOrder.setOverTime(currentDate);
            this.updateByPrimaryKeySelective(medicalOrder);
        });
    }



    //通过条件（可能是多个的查询）可能批量更新列表
    int updateBatchByCondition(MedicalOrder recordParams){
        return daoMedicalOrderMapper.updateBatchByCondition(recordParams);
    }
    // 根据条件查询列表
    public List<MedicalOrderPo> listMedicalOrderPo(MedicalOrderRequest params){
        return daoMedicalOrderMapper.listMedicalOrderPo(params);
    }
    // 根据条件查询列表 总数
    public int listMedicalOrderPoCount(MedicalOrderRequest params){
        return daoMedicalOrderMapper.listMedicalOrderPoCount(params);
    }
    //根据ID Key 获取一个订单
    public MedicalOrder selectMedicalOrderByIdKey(MedicalOrderRequest params){
        return daoMedicalOrderMapper.selectMedicalOrderByIdKey(params);
    }
    //根据ID Key 获取一个订单
    public MedicalOrder getRedisMedicalOrderById(Long id){
        MedicalOrder redisMedicalOrder = redisService.getJsonObjectByKey(ConstantAll.REDIS_APPOINT_ORDER_ID+id,new TypeReference<MedicalOrder>(){});
        if(redisMedicalOrder==null){
            redisMedicalOrder = this.selectByPrimaryKey(id);
            Assert.isTrue(redisMedicalOrder!=null,"目前 id:"+id+" 上门就诊订单信息异常，目前并不存在");
            redisService.setJsonObjectBy(ConstantAll.REDIS_APPOINT_ORDER_ID+id,redisMedicalOrder,ConstantAll.VALUE_CONSIDER_DAY_TIME,TimeUnit.HOURS);//目前考虑1个小时
        }
        return redisMedicalOrder;
    }
    @ApiOperation(value = "更新数据库中DB中所有的平台药师列表")
    public MedicalOrder getRedisMedicalOrderFirstUpdateById(Long id){
        this.redisService.deleteRedisByKey(ConstantAll.REDIS_APPOINT_ORDER_ID+id);
        return this.getRedisMedicalOrderById(id);
    }


    public List<MedicalOrder> listMedicalOrder(MedicalOrderRequest params){
        return daoMedicalOrderMapper.listMedicalOrder(params);
    }

    public int listMedicalOrderCount(MedicalOrderRequest params){
        return daoMedicalOrderMapper.listMedicalOrderCount(params);
    }

    public int insertSelective(MedicalOrder record){
        return daoMedicalOrderMapper.insertSelective(record);
    }

    public MedicalOrder selectByPrimaryKey(Long id){
        return daoMedicalOrderMapper.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(MedicalOrder record){
        return daoMedicalOrderMapper.updateByPrimaryKeySelective(record);
    }

}
