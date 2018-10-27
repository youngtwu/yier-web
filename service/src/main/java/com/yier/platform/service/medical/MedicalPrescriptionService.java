package com.yier.platform.service.medical;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.yingzhuo.carnival.id.IdGenerator;
import com.google.common.collect.Lists;
import com.yier.platform.common.costant.ConstantAll;
import com.yier.platform.common.model.*;
import com.yier.platform.common.requestParam.DoctorRequest;
import com.yier.platform.common.requestParam.MedicalPrescriptionRequest;
import com.yier.platform.common.requestParam.OrderDrugsRequest;
import com.yier.platform.common.util.JsonUtils;
import com.yier.platform.common.util.OrderIdUtils;
import com.yier.platform.common.util.Utils;
import com.yier.platform.dao.MedicalPrescriptionMapper;
import com.yier.platform.service.*;
import com.yier.platform.service.feign.LockDrug;
import com.yier.platform.service.feign.RemoteService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.MutableTriple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 上门问诊医生时间编排 service
 */
@Service
public class MedicalPrescriptionService {
    private final Logger logger = LoggerFactory.getLogger(MedicalPrescriptionService.class);
    @Autowired
    private MedicalPrescriptionMapper daoMedicalPrescriptionMapper;
    @Autowired
    private MedicalOrderService medicalOrderService;
    @Autowired
    private OrderDrugsService orderDrugsService;
    @Autowired
    private PharmacistService pharmacistService;
    @Autowired
    private AppPushService appPushService;
    @Autowired
    private ToolShareService toolShareService;
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private IdGenerator<Long> idGenerator;
    @Autowired
    private RedisService redisService;
    @Autowired
    private RedisLogService redisLogService;//缓存log记录情况
    @Autowired
    private RemoteService remoteService;
    @Autowired
    private DrugSendOrderService drugSendOrderService;
    @Autowired
    private DrugTakeOrderService drugTakeOrderService;

    @ApiOperation(value = "查询送药的药品列表的处方")
    public MutableTriple<List<MedicalPrescriptionPo>,Integer,Integer> drugSendOrderShow(MedicalPrescriptionRequest params){
        MutableTriple<List<MedicalPrescriptionPo>,Integer,Integer> mutableTriple = this.prescriptionList(params,2000);
        return mutableTriple;
    }

    @ApiOperation(value = "查询取药选择某个药房的的处方")
    public MutableTriple<List<MedicalPrescriptionPo>,List<String>,Integer> drugTakeOrderShow(MedicalPrescriptionRequest params){
        Assert.isTrue(params.getPharmacyId()!=null,"需要指定药房ID pharmacyId");
        logger.info("查询取药选择某个药房的的处方的参数是：{}",params);
        MutableTriple<List<MedicalPrescriptionPo>,Integer,Integer> mutableTriple = this.prescriptionList(params,0);
        //logger.info("处方目前信息：{}",mutableTriple.left);
        //考虑某个处方在某个药库下面，库存有效，同时修正处方对应药品code的价格
        remoteService.considerPrescriptionInPharmacyStockUpdatePrice(mutableTriple.left, params.getPharmacyId());
        //价格调整
        final OrderDrugs orderDrugs =new OrderDrugs();
        orderDrugs.setCostPrice(0);
        mutableTriple.left.forEach(p->{
            final OrderDrugs orderDrugsSmall =new OrderDrugs();//价格小计调整
            orderDrugsSmall.setCostPrice(0);
            p.getDrugList().forEach(d->{
                orderDrugsSmall.setCostPrice(d.getCostPrice().intValue()*d.getDrugCount().intValue()+orderDrugsSmall.getCostPrice().intValue());
                logger.info("目前药品信息：{} 药名：{} 价格是：{}",d.getDrugCode(),d.getDrugName(), d.getCostPrice()*d.getDrugCount().intValue());
            });
            p.setCost(orderDrugsSmall.getCostPrice());//价格小计
            orderDrugs.setCostPrice(orderDrugsSmall.getCostPrice()+orderDrugs.getCostPrice().intValue());
        });
        //logger.info("处方更改药房后的信息：{}",mutableTriple.left);

        MutableTriple<List<MedicalPrescriptionPo>,List<String>,Integer> mutableTripleResult = new MutableTriple<>();
        mutableTripleResult.left = mutableTriple.left;
        mutableTripleResult.middle = Lists.newArrayList();
        mutableTripleResult.right = orderDrugs.getCostPrice().intValue() + mutableTriple.middle.intValue() ;//总价格+ 快递费用
        return mutableTripleResult;
    }
    @ApiOperation(value = "查询取药的药品列表的处方")
    public MutableTriple<List<MedicalPrescriptionPo>,List<String>,Integer> drugTakeOrderTakeShow(MedicalPrescriptionRequest params){
        MutableTriple<List<MedicalPrescriptionPo>,Integer,Integer> mutableTriple = this.prescriptionList(params,0);

        MutableTriple<List<MedicalPrescriptionPo>,List<String>,Integer> mutableTripleResult = new MutableTriple<>();
        mutableTripleResult.left = mutableTriple.left;
        mutableTripleResult.middle = Lists.newArrayList();
        mutableTripleResult.right = mutableTriple.right;
        return mutableTripleResult;
    }

    @ApiOperation(value = "根据处方请求信息查询 带有药品列表的处方[可能是多个处方]")
    public MutableTriple<List<MedicalPrescriptionPo>,Integer,Integer> prescriptionList(MedicalPrescriptionRequest params,int shipCost){
        if(params.getPrescriptionIdList()==null && params.getPrescriptionId()!=null){
            params.setPrescriptionIdList(Arrays.asList(params.getPrescriptionId().split(",")));
        }
        Assert.isTrue(params.getPrescriptionIdList()!=null,"需要指定处方编号数组 prescriptionIdList");
        MutableTriple<List<MedicalPrescriptionPo>,Integer,Integer> mutableTriple = new MutableTriple<List<MedicalPrescriptionPo>,Integer,Integer>();
        MedicalPrescriptionRequest params1 = new MedicalPrescriptionRequest();
        params1.setPrescriptionIdList(params.getPrescriptionIdList());
        List<MedicalPrescriptionPo> medicalPrescriptionPoList = this.listMedicalPrescriptionPo(params1);
        List<String> pharmacyList = Lists.newArrayList();
        final OrderDrugs orderDrugs =new OrderDrugs();
        orderDrugs.setCostPrice(0);
        medicalPrescriptionPoList.forEach(p->{
            p.getDrugList().forEach(d->{
                orderDrugs.setCostPrice(d.getCostPrice().intValue()*d.getDrugCount().intValue()+orderDrugs.getCostPrice().intValue());
                logger.info("目前药品信息：{} 药名：{} 价格是：{}={}*{}",d.getDrugCode(),d.getDrugName(), d.getCostPrice()*d.getDrugCount().intValue(),d.getCostPrice(),d.getDrugCount());
            });
            if(!pharmacyList.contains(p.getPharmacyId())){
                pharmacyList.add(p.getPharmacyId());
            }
        });
        orderDrugs.setCostPrice(shipCost*pharmacyList.size()+orderDrugs.getCostPrice().intValue());
        logger.info("总的药品信息：价格是：{}",orderDrugs.getCostPrice());
        mutableTriple.setLeft(medicalPrescriptionPoList);//放置处方列表
        mutableTriple.setMiddle(shipCost*pharmacyList.size());//快递费用*药房数量
        mutableTriple.setRight(orderDrugs.getCostPrice());//放置总价格
        return mutableTriple;
    }

    @ApiOperation(value = "药师审方 不通过 引发医生修改处方")
    @Transactional
    public MedicalPrescription createPrescriptionModify(MedicalPrescription record) {
        logger.info("传递数据为:{}", record);
        Assert.isTrue(record.getId()!=null,"请传递ID");
        Assert.isTrue(record.getMedicalOrderId()!=null,"请传递MedicalOrderId");
        Assert.isTrue(record.getPrescriptionId()!=null,"请传递PrescriptionId");
        Assert.isTrue(record.getPharmacistId()!=null,"请传递PharmacistId");
        Assert.isTrue(  StringUtils.equalsIgnoreCase(record.getDoctorSignature(),ConstantAll.DOCTOR_SIGNATURE)," 修改请传递医生担责信息");

        MutableTriple<Long, Date, Long> result = JsonUtils.fromJson(redisService.getValueRedisByKey(ConstantAll.REDIS_PRESCRIPTION_CHECK_D_ORDER_ID + record.getId()), new TypeReference<MutableTriple<Long, Date, Long>>() {
        });
        logger.info("D阶段 result:{}",result);
        Assert.isTrue(result!=null,"很抱歉,已经超时或在重复点击操作");


//        Assert.isTrue(  redisService.hasRedisKey(ConstantAll.REDIS_PRESCRIPTION_ORDER + record.getId()),"已经处理完成");//
//        Assert.isTrue(  !redisService.hasRedisKey(ConstantAll.REDIS_PRESCRIPTION_CHECK_E_ORDER_ID + record.getId()),"已经自动进入环节E");
/*
        Assert.isTrue(  redisService.hasRedisKey(ConstantAll.REDIS_PRESCRIPTION_CHECK_D_ORDER_ID + record.getId()),"已经操作过请勿重复提交");
        MutableTriple<Long, Date, Long> result = JsonUtils.fromJson(redisService.getValueRedisByKey(ConstantAll.REDIS_PRESCRIPTION_CHECK_D_ORDER_ID + record.getId()), new TypeReference<MutableTriple<Long, Date, Long>>() {
        });
        Assert.isTrue(  currentDate.getTime() - result.middle.getTime() < ConstantAll.VALUE_TIME_OUT_CHECK_D * ConstantAll.VALUE_MICROSECOND_1000,"D环节超时");
*/
        Date currentDate = new Date();
        redisService.deleteRedisByKey(ConstantAll.REDIS_PRESCRIPTION_CHECK_D_ORDER_ID + record.getId(), false);//去除对应 超时D环节
        redisLogService.insertSelective(new RedisLog(ConstantAll.LOG_MEDICAL_PRES, ConstantAll.LOG_MEDICAL_PRES_SUB_TIME_OUT_CHECK_D, ConstantAll.REDIS_PRESCRIPTION_CHECK_D_ORDER_ID + record.getId(), "弱化对应的内容", 0L, ConstantAll.STRING_EMPTY, ConstantAll.LOG_REMARK_DELETE + "--医生点击修改处方,医生必须担责签名，进入环节E"));

        //todo:此处逻辑需要整理
        if(record.getDrugList()!=null){
            OrderDrugsRequest params = new OrderDrugsRequest();
            params.setPrescriptionId(record.getPrescriptionId());
            this.orderDrugsService.deleteByRequestKey(params);
            record.getDrugList().forEach(drug->{
                drug.setOrderNo(record.getOrderNo());
                drug.setPrescriptionId(record.getPrescriptionId());
                orderDrugsService.insertSelective(drug);
            });
        }


        record.setPrescriptionStatus(ConstantAll.MEDICAL_PRESCRIPTION_STATUS_REVIEW);
        record.setGmtModified(currentDate);
        this.updateByPrimaryKeySelective(record);

        MedicalOrder medicalOrder1 = new MedicalOrder();
        medicalOrder1.setId(record.getMedicalOrderId());
        medicalOrder1.setStatus(ConstantAll.ORDER_STATUS_PRESCRIPTION_CHECK);//审核中
        medicalOrder1.setStatusDetail(ConstantAll.ORDER_STATUS_PRESCRIPTION_CHECK);//审核中
        medicalOrder1.setRemarks("复审再此提交");
        medicalOrder1.setGmtModified(currentDate);
        medicalOrderService.updateByPrimaryKeySelective(medicalOrder1);

        //设定自有平台药师阶段E时间
        String keyTimeOutE = ConstantAll.REDIS_PRESCRIPTION_CHECK_E_ORDER_ID + record.getId();//处方产生订单超时记录
        MutableTriple<Long, Date, Long> mutableTriple = new MutableTriple<Long, Date, Long>();
        mutableTriple.left = result.left;
        mutableTriple.middle = new Date();
        mutableTriple.right = result.right;
        redisService.setJsonObjectBy(keyTimeOutE, mutableTriple, ConstantAll.VALUE_CONSIDER_DAY_TIME, ConstantAll.UNIT_CONSIDER);
        redisLogService.insertSelective(new RedisLog(ConstantAll.LOG_MEDICAL_PRES, ConstantAll.LOG_MEDICAL_PRES_SUB_TIME_OUT_CHECK_E, ConstantAll.REDIS_PRESCRIPTION_CHECK_E_ORDER_ID + result.left, JsonUtils.toJsonString(mutableTriple), ConstantAll.VALUE_CONSIDER_DAY_TIME, ConstantAll.UNIT_CONSIDER.toString(), ConstantAll.LOG_REMARK_SET + "--系统判定E环节设定"));

        return record;
    }
    @ApiOperation(value = "药师审方 不通过")
    @Transactional
    public MedicalPrescription createPrescriptionCheckKO(MedicalPrescription record) {
        logger.info("传递数据为:{}", record);
        Assert.isTrue(record.getId()!=null,"请传递处方ID");
        Assert.isTrue(record.getMedicalOrderId()!=null,"请传递MedicalOrderId");
        Assert.isTrue(record.getPharmacistId()!=null,"请传递PharmacistId");
        Assert.isTrue(record.getDoctorSignature()!=null,"请传递医生担责信息");
        Assert.isTrue(record.getPharmacyPlatType()!=null,"请传递pharmacyPlatType");
        Assert.isTrue(redisService.hasRedisKey(ConstantAll.REDIS_PRESCRIPTION_ORDER+record.getId()),"您已经超时,其他流程审核已经通过");
        if(record.getPharmacyPlatType()==ConstantAll.PLAT_TYPE_0){
            String caseInfo = getCaseInfoForBE(record);
            if(StringUtils.equalsIgnoreCase(ConstantAll.DOCTOR_SIGNATURE,record.getDoctorSignature())){
                redisService.deleteRedisByKey((StringUtils.equalsIgnoreCase(caseInfo,ConstantAll.REDIS_PRESCRIPTION_CHECK_B_ORDER_ID)?ConstantAll.REDIS_PRESCRIPTION_CHECK_B_ORDER_ID:ConstantAll.REDIS_PRESCRIPTION_CHECK_E_ORDER_ID)+record.getId());//去除对应 超时B环节
                redisService.deleteRedisByKey(ConstantAll.REDIS_PRESCRIPTION_ORDER+record.getId());//去除对应处方处理
                if(StringUtils.equalsIgnoreCase(caseInfo,ConstantAll.REDIS_PRESCRIPTION_CHECK_B_ORDER_ID)){
                    redisLogService.insertSelective(new RedisLog(ConstantAll.LOG_MEDICAL_PRES,ConstantAll.LOG_MEDICAL_PRES_SUB_TIME_OUT_CHECK_B,ConstantAll.REDIS_PRESCRIPTION_CHECK_B_ORDER_ID+record.getId(),"弱化对应的内容",0L,ConstantAll.STRING_EMPTY,ConstantAll.LOG_REMARK_DELETE+"--医生点击患者未在家"));
                }else{
                    redisLogService.insertSelective(new RedisLog(ConstantAll.LOG_MEDICAL_PRES,ConstantAll.LOG_MEDICAL_PRES_SUB_TIME_OUT_CHECK_E,ConstantAll.REDIS_PRESCRIPTION_CHECK_E_ORDER_ID+record.getId(),"弱化对应的内容",0L,ConstantAll.STRING_EMPTY,ConstantAll.LOG_REMARK_DELETE+"--医生点击患者未在家"));
                }
                redisLogService.insertSelective(new RedisLog(ConstantAll.LOG_MEDICAL_PRES,ConstantAll.LOG_MEDICAL_PRES_SUB_CURRENT_PRESCRIPTION,ConstantAll.REDIS_PRESCRIPTION_ORDER+record.getId(),"弱化对应的内容",0L,ConstantAll.STRING_EMPTY,ConstantAll.LOG_REMARK_DELETE+"--医生点击患者未在家"));

                Date currentDate = new Date();
                record.setViewTime(currentDate);
                //record.setViewInfo("药师审方不通过");
                record.setViewStatus(ConstantAll.CHECK_VIEW_STATUS_KO);
                record.setPrescriptionStatus(ConstantAll.MEDICAL_PRESCRIPTION_STATUS_OK);// 完成
                record.setStatusDetail(ConstantAll.MEDICAL_PRESCRIPTION_STATUS_PATIENT_NO_ORDER);//未支付
                record.setGmtModified(currentDate);
                record.setRemarks((StringUtils.equalsIgnoreCase(caseInfo,ConstantAll.REDIS_PRESCRIPTION_CHECK_B_ORDER_ID)?"B阶段":"E阶段")+"普通药师审方不通过");
                this.updateByPrimaryKeySelective(record);

                MedicalOrder medicalOrder1 = new MedicalOrder();
                medicalOrder1.setId(record.getMedicalOrderId());
                medicalOrder1.setStatus(ConstantAll.ORDER_STATUS_OK);//患者 完成
                medicalOrder1.setStatusDetail(ConstantAll.ORDER_STATUS_OK);//完成
                medicalOrder1.setRemarks((StringUtils.equalsIgnoreCase(caseInfo,ConstantAll.REDIS_PRESCRIPTION_CHECK_B_ORDER_ID)?"B阶段":"E阶段")+"普通药师审方 完成");
                medicalOrder1.setGmtModified(currentDate);
                medicalOrderService.updateByPrimaryKeySelective(medicalOrder1);

                considerToTakeSendOrder(record.getId(),"--携带医生担责的普通药师不通过处方 整体完成门诊订单，");//考虑取药存药订单计时处理

            }
            else{//只能是B阶段
                redisService.deleteRedisByKey((StringUtils.equalsIgnoreCase(caseInfo,ConstantAll.REDIS_PRESCRIPTION_CHECK_B_ORDER_ID)?ConstantAll.REDIS_PRESCRIPTION_CHECK_B_ORDER_ID:ConstantAll.REDIS_PRESCRIPTION_CHECK_E_ORDER_ID)+record.getId());//去除对应 超时B环节
                if(StringUtils.equalsIgnoreCase(caseInfo,ConstantAll.REDIS_PRESCRIPTION_CHECK_B_ORDER_ID)){
                    redisLogService.insertSelective(new RedisLog(ConstantAll.LOG_MEDICAL_PRES,ConstantAll.LOG_MEDICAL_PRES_SUB_TIME_OUT_CHECK_B,ConstantAll.REDIS_PRESCRIPTION_CHECK_B_ORDER_ID+record.getId(),"弱化对应的内容",0L,ConstantAll.STRING_EMPTY,ConstantAll.LOG_REMARK_DELETE+"--医生点击患者未在家"));
                }else{
                    redisLogService.insertSelective(new RedisLog(ConstantAll.LOG_MEDICAL_PRES,ConstantAll.LOG_MEDICAL_PRES_SUB_TIME_OUT_CHECK_E,ConstantAll.REDIS_PRESCRIPTION_CHECK_E_ORDER_ID+record.getId(),"弱化对应的内容",0L,ConstantAll.STRING_EMPTY,ConstantAll.LOG_REMARK_DELETE+"--医生点击患者未在家"));
                }
                Date currentDate = new Date();
                record.setViewTime(currentDate);
                //record.setViewInfo((StringUtils.equalsIgnoreCase(caseInfo,ConstantAll.REDIS_PRESCRIPTION_CHECK_B_ORDER_ID)?"B阶段":"E阶段")+"药师审方不通过");
                record.setViewStatus(ConstantAll.CHECK_VIEW_STATUS_KO);
                record.setPrescriptionStatus(ConstantAll.MEDICAL_PRESCRIPTION_STATUS_ADVICE);// 已审 [给出通过或不通过意见]
                record.setGmtModified(currentDate);
                record.setRemarks((StringUtils.equalsIgnoreCase(caseInfo,ConstantAll.REDIS_PRESCRIPTION_CHECK_B_ORDER_ID)?"B阶段":"E阶段")+"普通药师审方不通过");
                this.updateByPrimaryKeySelective(record);



                MedicalOrder medicalOrder1 = new MedicalOrder();
                medicalOrder1.setId(record.getMedicalOrderId());
                medicalOrder1.setStatus(ConstantAll.ORDER_STATUS_PRESCRIPTION_CHECK);//患者 处理中
                medicalOrder1.setStatusDetail(ConstantAll.ORDER_STATUS_PRESCRIPTION_CHECK_FAIL);//审核不通过
                medicalOrder1.setRemarks((StringUtils.equalsIgnoreCase(caseInfo,ConstantAll.REDIS_PRESCRIPTION_CHECK_B_ORDER_ID)?"B阶段":"E阶段")+"普通药师审方 不通过");
                medicalOrder1.setGmtModified(new Date());
                medicalOrderService.updateByPrimaryKeySelective(medicalOrder1);

                //药师审核 D阶段 给医生超时处理
                String keyTimeOutD = ConstantAll.REDIS_PRESCRIPTION_CHECK_D_ORDER_ID+record.getId();//不通过处方 迈入D阶段
                MutableTriple<Long, Date, Long> mutableTriple = new MutableTriple<Long, Date, Long>();
                mutableTriple.left = record.getId();
                mutableTriple.middle = new Date();
                mutableTriple.right = record.getPharmacistId();//前台传递过来可能已经过期
                redisService.setJsonObjectBy(keyTimeOutD,mutableTriple,ConstantAll.VALUE_CONSIDER_DAY_TIME,ConstantAll.UNIT_CONSIDER);
                redisLogService.insertSelective(new RedisLog(ConstantAll.LOG_MEDICAL_PRES, ConstantAll.LOG_MEDICAL_PRES_SUB_TIME_OUT_CHECK_D, keyTimeOutD, JsonUtils.toJsonString(mutableTriple), ConstantAll.VALUE_CONSIDER_DAY_TIME, ConstantAll.UNIT_CONSIDER.toString(), ConstantAll.LOG_REMARK_SET + "--审核不通过进入D环节设定"));
            }
        }
        else if(record.getPharmacyPlatType()==ConstantAll.PLAT_TYPE_1){
            String caseInfo = getCaseInfoForCE(record);
            if(StringUtils.equalsIgnoreCase(ConstantAll.DOCTOR_SIGNATURE,record.getDoctorSignature())){
                redisService.deleteRedisByKey((StringUtils.equalsIgnoreCase(caseInfo,ConstantAll.REDIS_PRESCRIPTION_CHECK_C_ORDER_ID)?ConstantAll.REDIS_PRESCRIPTION_CHECK_C_ORDER_ID:ConstantAll.REDIS_PRESCRIPTION_CHECK_E_ORDER_ID)+record.getId());//去除对应 超时B环节
                redisService.deleteRedisByKey(ConstantAll.REDIS_PRESCRIPTION_ORDER+record.getId());//去除对应处方处理
                if(StringUtils.equalsIgnoreCase(caseInfo,ConstantAll.REDIS_PRESCRIPTION_CHECK_C_ORDER_ID)){
                    redisLogService.insertSelective(new RedisLog(ConstantAll.LOG_MEDICAL_PRES,ConstantAll.LOG_MEDICAL_PRES_SUB_TIME_OUT_CHECK_C,ConstantAll.REDIS_PRESCRIPTION_CHECK_C_ORDER_ID+record.getId(),"弱化对应的内容",0L,ConstantAll.STRING_EMPTY,ConstantAll.LOG_REMARK_DELETE+"--医生点击患者未在家"));
                }else{
                    redisLogService.insertSelective(new RedisLog(ConstantAll.LOG_MEDICAL_PRES,ConstantAll.LOG_MEDICAL_PRES_SUB_TIME_OUT_CHECK_E,ConstantAll.REDIS_PRESCRIPTION_CHECK_E_ORDER_ID+record.getId(),"弱化对应的内容",0L,ConstantAll.STRING_EMPTY,ConstantAll.LOG_REMARK_DELETE+"--医生点击患者未在家"));
                }
                redisLogService.insertSelective(new RedisLog(ConstantAll.LOG_MEDICAL_PRES,ConstantAll.LOG_MEDICAL_PRES_SUB_CURRENT_PRESCRIPTION,ConstantAll.REDIS_PRESCRIPTION_ORDER+record.getId(),"弱化对应的内容",0L,ConstantAll.STRING_EMPTY,ConstantAll.LOG_REMARK_DELETE+"--医生点击患者未在家"));

                Date currentDate = new Date();
                //record.setViewTime(currentDate);
                record.setViewInfo("平台药师审方不通过");
                record.setViewStatus(ConstantAll.CHECK_VIEW_STATUS_KO);
                record.setPrescriptionStatus(ConstantAll.MEDICAL_PRESCRIPTION_STATUS_OK);// 完成
                record.setStatusDetail(ConstantAll.MEDICAL_PRESCRIPTION_STATUS_PATIENT_NO_ORDER);//未支付
                record.setGmtModified(currentDate);
                record.setRemarks((StringUtils.equalsIgnoreCase(caseInfo,ConstantAll.REDIS_PRESCRIPTION_CHECK_C_ORDER_ID)?"C阶段":"E阶段")+"平台药师审方不通过");
                this.updateByPrimaryKeySelective(record);

                MedicalOrder medicalOrder1 = new MedicalOrder();
                medicalOrder1.setId(record.getMedicalOrderId());
                medicalOrder1.setStatus(ConstantAll.ORDER_STATUS_OK);//患者 完成
                medicalOrder1.setStatusDetail(ConstantAll.ORDER_STATUS_OK);//完成
                medicalOrder1.setRemarks((StringUtils.equalsIgnoreCase(caseInfo,ConstantAll.REDIS_PRESCRIPTION_CHECK_C_ORDER_ID)?"C阶段":"E阶段")+"平台药师审方 完成");
                medicalOrder1.setGmtModified(new Date());
                medicalOrderService.updateByPrimaryKeySelective(medicalOrder1);

                considerToTakeSendOrder(record.getId(),"--携带医生担责的平台药师不通过处方 整体完成门诊订单，");//考虑取药存药订单计时处理
            }
            else{//只能是C阶段
                //查看redis处方在C环节分配超时
                redisService.deleteRedisByKey(ConstantAll.REDIS_PRESCRIPTION_CHECK_C_ORDER_ID+record.getId());//去除对应 超时C环节
                redisLogService.insertSelective(new RedisLog(ConstantAll.LOG_MEDICAL_PRES,ConstantAll.LOG_MEDICAL_PRES_SUB_TIME_OUT_CHECK_C,ConstantAll.REDIS_PRESCRIPTION_CHECK_C_ORDER_ID+record.getId(),"弱化对应的内容",0L,ConstantAll.STRING_EMPTY,ConstantAll.LOG_REMARK_DELETE+"--医生点击患者未在家"));

                Date currentDate = new Date();
                record.setViewTime(currentDate);
                //record.setViewInfo("平台药师审方不通过");
                record.setViewStatus(ConstantAll.CHECK_VIEW_STATUS_KO);
                record.setPrescriptionStatus(ConstantAll.MEDICAL_PRESCRIPTION_STATUS_ADVICE);// 已审 [给出通过或不通过意见]
                record.setGmtModified(currentDate);
                record.setRemarks((StringUtils.equalsIgnoreCase(caseInfo,ConstantAll.REDIS_PRESCRIPTION_CHECK_C_ORDER_ID)?"C阶段":"E阶段")+"平台药师审方不通过");
                this.updateByPrimaryKeySelective(record);

                MedicalOrder medicalOrder1 = new MedicalOrder();
                medicalOrder1.setId(record.getMedicalOrderId());
                medicalOrder1.setStatus(ConstantAll.ORDER_STATUS_PRESCRIPTION_CHECK);//患者 处理中
                medicalOrder1.setStatusDetail(ConstantAll.ORDER_STATUS_PRESCRIPTION_CHECK_FAIL);//审核不通过
                medicalOrder1.setRemarks((StringUtils.equalsIgnoreCase(caseInfo,ConstantAll.REDIS_PRESCRIPTION_CHECK_C_ORDER_ID)?"C阶段":"E阶段")+"平台药师审方 不通过");
                medicalOrder1.setGmtModified(new Date());
                medicalOrderService.updateByPrimaryKeySelective(medicalOrder1);

                //药师审核 D阶段 给医生超时处理
                String keyTimeOutD = ConstantAll.REDIS_PRESCRIPTION_CHECK_D_ORDER_ID+record.getId();//不通过处方 迈入D阶段
                MutableTriple<Long, Date, Long> mutableTriple = new MutableTriple<Long, Date, Long>();
                mutableTriple.left = record.getId();
                mutableTriple.middle = new Date();
                mutableTriple.right = record.getPharmacistId();
                redisService.setJsonObjectBy(keyTimeOutD,mutableTriple,ConstantAll.VALUE_CONSIDER_DAY_TIME,ConstantAll.UNIT_CONSIDER);
                redisLogService.insertSelective(new RedisLog(ConstantAll.LOG_MEDICAL_PRES, ConstantAll.LOG_MEDICAL_PRES_SUB_TIME_OUT_CHECK_D, keyTimeOutD, JsonUtils.toJsonString(mutableTriple), ConstantAll.VALUE_CONSIDER_DAY_TIME, ConstantAll.UNIT_CONSIDER.toString(), ConstantAll.LOG_REMARK_SET + "--审核不通过进入D环节设定"));
            }
        }
        return record;
    }

    public void updateBatchByCondition(String statusDetial,String remark,List<String> prescriptionIdList,List<Long> medicalPrescriptionIdList,Date currentDate){
        MedicalPrescription medicalPrescription = new MedicalPrescription();
        medicalPrescription.setGmtModified(currentDate);
        medicalPrescription.setStatusDetail(statusDetial);
        medicalPrescription.setRemarks(remark);
        medicalPrescription.setPrescriptionIdList(prescriptionIdList);
        medicalPrescription.setMedicalPrescriptionIdList(medicalPrescriptionIdList);
        this.updateBatchByCondition(medicalPrescription);
    }


    /**
     * 患者通过取药或送药预约更新处方状态
     * @param medicalPrescriptionIdList
     * @param operate
     * @param remarkForMp
     * @param otherInfo
     * @param currentDate
     */
    public  void updateKeyTimeOutTakeOrSendOrder(List<Long> medicalPrescriptionIdList,int operate,String remarkForMp,String otherInfo,Date currentDate){
        switch (operate){
            case ConstantAll.TAKE_SEND_PRESCRIPTION_LOCK_NEW_ORDER://下单成功,进入待支付
                this.updateBatchByCondition(ConstantAll.MEDICAL_PRESCRIPTION_STATUS_PATIENT_NEW_ORDER, remarkForMp,null,medicalPrescriptionIdList,currentDate);//批量更新处方列表状态及描述
                break;
            case ConstantAll.TAKE_SEND_PRESCRIPTION_LOCK_CANCEL_ORDER://取消预约订单,进入未支付
                this.updateBatchByCondition(ConstantAll.MEDICAL_PRESCRIPTION_STATUS_PATIENT_NO_ORDER, remarkForMp,null,medicalPrescriptionIdList,currentDate);//批量更新处方列表状态及描述
                break;
            case ConstantAll.TAKE_SEND_PRESCRIPTION_LOCK_PAYMENT_ORDER://支付成功,删除资源锁,进入已支付
                this.updateBatchByCondition(ConstantAll.MEDICAL_PRESCRIPTION_STATUS_PATIENT_PAYMENT_OK, remarkForMp,null,medicalPrescriptionIdList,currentDate);//批量更新处方列表状态及描述
                break;
            case ConstantAll.TAKE_SEND_PRESCRIPTION_LOCK_TASK_TIME_OUT://检查到超期,目前单独处理
                this.updateBatchByCondition(ConstantAll.MEDICAL_PRESCRIPTION_STATUS_PATIENT_PAYMENT_PAST_DUE, remarkForMp,null,medicalPrescriptionIdList,currentDate);//批量更新处方列表状态及描述
                break;

        }
        medicalPrescriptionIdList.forEach(idMp->{
            String keyTimeOutTakeOrSendOrder = ConstantAll.REDIS_SEND_TAKE_ORDER_PAYMENT_NEW_ID+idMp;//处方产生送药或取药订单计时
            if(operate==ConstantAll.TAKE_SEND_PRESCRIPTION_LOCK_PAYMENT_ORDER){//支付成功,删除资源锁
                Assert.isTrue(redisService.hasRedisKey(keyTimeOutTakeOrSendOrder),"您选择有目前已经过期的处方! [处方Id:"+idMp+"]");
                redisService.deleteRedisByKey(keyTimeOutTakeOrSendOrder);//去除对应 超时取药送药处方
                redisLogService.insertSelective(new RedisLog(ConstantAll.LOG_MEDICAL_PRES, ConstantAll.LOG_TAKE_SEND_PRES_ORDER, keyTimeOutTakeOrSendOrder, "弱化对应的内容", 0L, ConstantAll.STRING_EMPTY, ConstantAll.LOG_REMARK_DELETE + "--取消对于的超时设定"+otherInfo));
            }
            else if(operate == ConstantAll.TAKE_SEND_PRESCRIPTION_LOCK_NEW_ORDER){//绑定合并,新建立送药或取药
                Assert.isTrue(redisService.hasRedisKey(keyTimeOutTakeOrSendOrder),"您选择有目前已经过期的处方! [处方Id:"+idMp+"]");
                MutableTriple<Long, Date,List<Long>> result = JsonUtils.fromJson(redisService.getValueRedisByKey(keyTimeOutTakeOrSendOrder), new TypeReference<MutableTriple<Long, Date,List<Long>>>() {
                });
                result.right = medicalPrescriptionIdList;
                long timeOutLeft = this.redisService.getExpirRedis(keyTimeOutTakeOrSendOrder, TimeUnit.SECONDS);
                redisService.setJsonObjectBy(keyTimeOutTakeOrSendOrder,result,timeOutLeft,TimeUnit.SECONDS);//一个处方最长考虑5整天[无限制不能有效释放资源]
                redisLogService.insertSelective(new RedisLog(ConstantAll.LOG_TAKE_SEND_PRES,ConstantAll.LOG_TAKE_SEND_PRES_ORDER,keyTimeOutTakeOrSendOrder,JsonUtils.toJsonString(result),ConstantAll.VALUE_CONSIDER_DAY_TIME,ConstantAll.UNIT_CONSIDER.toString(),ConstantAll.LOG_REMARK_UPDATE+"合并的处方联合"+otherInfo));

            }
            else if(operate == ConstantAll.TAKE_SEND_PRESCRIPTION_LOCK_CANCEL_ORDER){//绑定断裂,取消支付时
                Assert.isTrue(redisService.hasRedisKey(keyTimeOutTakeOrSendOrder),"您选择有目前已经过期的处方! [处方Id:"+idMp+"]");
                MutableTriple<Long, Date,List<Long>> result = JsonUtils.fromJson(redisService.getValueRedisByKey(keyTimeOutTakeOrSendOrder), new TypeReference<MutableTriple<Long, Date,List<Long>>>() {
                });
                result.right.clear();
                result.right.add(idMp);//分散对应的缓存控制,各回个家
                long timeOutLeft = this.redisService.getExpirRedis(keyTimeOutTakeOrSendOrder, TimeUnit.SECONDS);
                redisService.setJsonObjectBy(keyTimeOutTakeOrSendOrder,result,timeOutLeft,TimeUnit.SECONDS);//一个处方最长考虑5整天[无限制不能有效释放资源]
                redisLogService.insertSelective(new RedisLog(ConstantAll.LOG_TAKE_SEND_PRES,ConstantAll.LOG_TAKE_SEND_PRES_ORDER,keyTimeOutTakeOrSendOrder,JsonUtils.toJsonString(result),ConstantAll.VALUE_CONSIDER_DAY_TIME,ConstantAll.UNIT_CONSIDER.toString(),ConstantAll.LOG_REMARK_UPDATE+"释放被合并的处方联合,免得一个过期一波都遭遇"+otherInfo));
            }
            else if(operate==ConstantAll.TAKE_SEND_PRESCRIPTION_LOCK_TASK_TIME_OUT){//资源过期,目前不株连九族

            }
        });
    }


    public void considerValidTimeOutTakeSendOrder(MedicalPrescriptionPo medicalPrescriptionPo) {
        String keyTimeOutTakeOrSendOrder = ConstantAll.REDIS_SEND_TAKE_ORDER_PAYMENT_NEW_ID+medicalPrescriptionPo.getId();//处方产生送药或取药订单计时
        Assert.isTrue(redisService.hasRedisKey(keyTimeOutTakeOrSendOrder),"您选择的处方目前已经过期! [处方Id:"+medicalPrescriptionPo.getId()+ "编号:"+medicalPrescriptionPo.getPrescriptionId()+"]");
    }
    /**
     * 删除某个处方缓存 同时会关联删除标在成一起,如果考虑DB将会批量更新状态,一个处方取药送药过期删除,其他一波处方并不删除,只是释放更正关联right
     */
    public void considerDeleteTimeOutTakeSendOrder_One_One(MutableTriple<Long, Date,List<Long>> result,Date currentDate) {
        if(result!=null){
            logger.info("{} 一下关联删除缓存中被合并的处方取药送药ID 目前查询结果是:{}",result.right,result);

            this.updateBatchByCondition(ConstantAll.MEDICAL_PRESCRIPTION_STATUS_PATIENT_PAYMENT_PAST_DUE,"删除资源锁定,意味着处方发药的大门已经关闭,目前处方可能同时关闭一波ids是"+result.right,
                    null,result.right,currentDate);//批量更新处方列表状态及描述

            DrugSendOrder drugSendOrder = new DrugSendOrder();
            drugSendOrder.setSendStatus(ConstantAll.SEND_ORDER_STATUS_PATIENT_PAST_DUE);//送药 支付超期
            drugSendOrder.setSearchKey(result.left+",");//查询到跟处方ID相关的列表
            drugSendOrder.setSendStatusChanging(ConstantAll.SEND_ORDER_STATUS_APPPOINT);
            drugSendOrder.setRemarks("检测到资源锁定已经到期,目前更新送药,调整为支付超期");
            drugSendOrder.setGmtModified(currentDate);
            this.drugSendOrderService.updateBatchByCondition(drugSendOrder);

            DrugTakeOrder drugTakeOrder = new DrugTakeOrder();
            drugTakeOrder.setTakeStatus(ConstantAll.TAKE_ORDER_STATUS_PATIENT_PAST_DUE);//取药 支付超期
            drugTakeOrder.setSearchKey(result.left+",");//查询到跟处方ID相关的列表
            drugTakeOrder.setTakeStatusChanging(ConstantAll.TAKE_ORDER_STATUS_APPPOINT);
            drugTakeOrder.setRemarks("检测到资源锁定已经到期,目前更新取药,调整为支付超期");
            drugTakeOrder.setGmtModified(currentDate);
            this.drugTakeOrderService.updateBatchByCondition(drugTakeOrder);


            String keyTimeOutTakeOrSendOrderOneTimeOut = ConstantAll.REDIS_SEND_TAKE_ORDER_PAYMENT_NEW_ID + result.left;
            redisService.deleteRedisByKey(keyTimeOutTakeOrSendOrderOneTimeOut);//去除对应 超时取药送药处方
            redisLogService.insertSelective(new RedisLog(ConstantAll.LOG_MEDICAL_PRES, ConstantAll.LOG_TAKE_SEND_PRES_ORDER, keyTimeOutTakeOrSendOrderOneTimeOut, "弱化对应的内容", 0L, ConstantAll.STRING_EMPTY, ConstantAll.LOG_REMARK_DELETE + "--取消对于的超时设定"));

            result.right.remove(result.left);
            result.right.forEach(idOther->{//释放缓存资源
                String keyTimeOutTakeOrSendOrderOther = ConstantAll.REDIS_SEND_TAKE_ORDER_PAYMENT_NEW_ID + idOther;

                MutableTriple<Long, Date,List<Long>> otherResult = JsonUtils.fromJson(redisService.getValueRedisByKey(keyTimeOutTakeOrSendOrderOther), new TypeReference<MutableTriple<Long, Date,List<Long>>>() {
                });
                otherResult.right.clear();
                otherResult.right.add(idOther);//分散对应的缓存控制,各回个家
                long timeOutLeft = this.redisService.getExpirRedis(keyTimeOutTakeOrSendOrderOther, TimeUnit.SECONDS);
                redisService.setJsonObjectBy(keyTimeOutTakeOrSendOrderOther,result,timeOutLeft,TimeUnit.SECONDS);//一个处方最长考虑5整天[无限制不能有效释放资源]
                redisLogService.insertSelective(new RedisLog(ConstantAll.LOG_TAKE_SEND_PRES,ConstantAll.LOG_TAKE_SEND_PRES_ORDER,keyTimeOutTakeOrSendOrderOther,JsonUtils.toJsonString(result),ConstantAll.VALUE_CONSIDER_DAY_TIME,ConstantAll.UNIT_CONSIDER.toString(),ConstantAll.LOG_REMARK_UPDATE+"重新设定取药释放被合并的处方联合,免得一个过期一波都遭遇"));
            });
            logger.info("一个处方取药送药过期删除,其他一波处方并不删除,只是释放更正关联right");
        }
        else{
            logger.info("  目前缓存已经不存在此处方取药送药ID");
        }
    }
    /**
     * 删除某个处方缓存 同时会关联删除标在成一起,如果考虑DB将会批量更新状态,一个处方删除,一波处方跟着删除,株连九族
     * @param result
     */
    public void considerDeleteTimeOutTakeSendOrder_One_More(MutableTriple<Long, Date,List<Long>> result,Date currentDate) {
        if(result!=null){
            logger.info("{} 一下关联删除缓存中被合并的处方取药送药ID 目前查询结果是:{}",result.right,result);
            this.updateBatchByCondition(ConstantAll.MEDICAL_PRESCRIPTION_STATUS_PATIENT_PAYMENT_PAST_DUE,"删除资源锁定,意味着处方发药的大门已经关闭,目前处方可能同时关闭一波ids是"+result.right,
                    null,result.right,currentDate);//批量更新处方列表状态及描述


            //批量调整对应的取药或存药状态
            DrugSendOrder drugSendOrder = new DrugSendOrder();
            drugSendOrder.setSendStatus(ConstantAll.SEND_ORDER_STATUS_PATIENT_PAST_DUE);//送药 支付超期
            drugSendOrder.setSearchKey(result.left+",");//查询到跟处方ID相关的列表
            drugSendOrder.setSendStatusChanging(ConstantAll.SEND_ORDER_STATUS_APPPOINT);
            drugSendOrder.setRemarks("检测到资源锁定已经到期,目前更新送药,调整为支付超期");
            drugSendOrder.setGmtModified(currentDate);
            this.drugSendOrderService.updateBatchByCondition(drugSendOrder);

            DrugTakeOrder drugTakeOrder = new DrugTakeOrder();
            drugTakeOrder.setTakeStatus(ConstantAll.TAKE_ORDER_STATUS_PATIENT_PAST_DUE);//取药 支付超期
            drugTakeOrder.setSearchKey(result.left+",");//查询到跟处方ID相关的列表
            drugTakeOrder.setTakeStatusChanging(ConstantAll.TAKE_ORDER_STATUS_APPPOINT);
            drugTakeOrder.setRemarks("检测到资源锁定已经到期,目前更新取药,调整为支付超期");
            drugTakeOrder.setGmtModified(currentDate);
            this.drugTakeOrderService.updateBatchByCondition(drugTakeOrder);

            result.right.forEach(idP->{//释放缓存资源
                String keyTimeOutTakeOrSendOrderAllRef = ConstantAll.REDIS_SEND_TAKE_ORDER_PAYMENT_NEW_ID+idP;
                redisService.deleteRedisByKey(keyTimeOutTakeOrSendOrderAllRef);//去除对应 超时取药送药处方
                redisLogService.insertSelective(new RedisLog(ConstantAll.LOG_MEDICAL_PRES, ConstantAll.LOG_TAKE_SEND_PRES_ORDER, keyTimeOutTakeOrSendOrderAllRef, "弱化对应的内容", 0L, ConstantAll.STRING_EMPTY, ConstantAll.LOG_REMARK_DELETE + "--取消对于的超时设定"));
            });
            logger.info("一个处方取药送药过期删除,一波处方跟着删除,株连九族");
        }
        else{
            logger.info("  目前缓存已经不存在此处方取药送药ID");
        }
    }
    /**
     * 设定 送药或取药资源最大锁定时长,超期代表释放资源,直接删除也表示释放资源
     * @param medicalPrescriptionId
     * @param message
     */
    public void considerToTakeSendOrder(Long medicalPrescriptionId,String message) {
        String keyTimeOutTakeOrSendOrder = ConstantAll.REDIS_SEND_TAKE_ORDER_PAYMENT_NEW_ID+medicalPrescriptionId;//处方产生送药或取药订单计时
        MutableTriple<Long, Date,List<Long>> mutableTriple = new MutableTriple<Long, Date, List<Long>>();//<处方ID,放置时的时间,可能关联的处方合并ID列表>
        mutableTriple.left = medicalPrescriptionId;
        mutableTriple.middle = new Date();
        mutableTriple.right = Lists.newArrayList();
        mutableTriple.right.add(medicalPrescriptionId);
        redisService.setJsonObjectBy(keyTimeOutTakeOrSendOrder,mutableTriple,ConstantAll.VALUE_CONSIDER_DAY_TIME,ConstantAll.UNIT_CONSIDER);//一个处方最长考虑5整天[无限制不能有效释放资源]
        redisLogService.insertSelective(new RedisLog(ConstantAll.LOG_TAKE_SEND_PRES,ConstantAll.LOG_TAKE_SEND_PRES_ORDER,keyTimeOutTakeOrSendOrder,JsonUtils.toJsonString(mutableTriple),ConstantAll.VALUE_CONSIDER_DAY_TIME,ConstantAll.UNIT_CONSIDER.toString(),ConstantAll.LOG_REMARK_SET+message+"开始考虑患者取药或送药"));
    }

    /**
     * 定时任务 考虑送药或取药超时问题
     * @param currentDate
     */
    public void taskConsiderToTakeSendOrderTimeOut(Date currentDate) {
        Set<String> keyTimeOutTakeSendSet = redisService.getRedisKeysByPattern(ConstantAll.REDIS_SEND_TAKE_ORDER_PAYMENT_NEW_ID + "*");//查看redis处方在B环节分配超时
        logger.info("目前考虑的处方送药或取药超时问题:{}",keyTimeOutTakeSendSet);
        if (keyTimeOutTakeSendSet.size() > 0) {//存在要考虑的取药送药处方
            keyTimeOutTakeSendSet.forEach(timeOutTakeSend->{
                MutableTriple<Long, Date,List<Long>> result = JsonUtils.fromJson(redisService.getValueRedisByKey(timeOutTakeSend, false), new TypeReference<MutableTriple<Long, Date,List<Long>>>() {
                });
                if(result !=null){
                    if (currentDate.getTime() - result.middle.getTime() > ConstantAll.VALUE_TIME_OUT_SEND_TAKE_ORDER * ConstantAll.VALUE_MICROSECOND_1000) {//送药取药超时
                        this.considerDeleteTimeOutTakeSendOrder_One_One(result,currentDate);//一个处方取药送药过期删除,其他一波处方并不删除,只是释放更正关联right
                        //this.considerDeleteTimeOutTakeSendOrder_One_More(result,currentDate);//一个处方删除,一波处方跟着删除,株连九族
                    }
                }
                else{
                    logger.info("查询到有,目前对应的Key {}资源可能被方法[considerDeleteTimeOutTakeSendOrder_One_More]中删除掉",timeOutTakeSend);
                }
            });

        }

     }

    private String getCaseInfoForCE(MedicalPrescription record) {
        String caseInfo = ConstantAll.REDIS_PRESCRIPTION_CHECK_C_ORDER_ID;
        MutableTriple<Long, Date, Long> resultC = JsonUtils.fromJson(redisService.getValueRedisByKey(ConstantAll.REDIS_PRESCRIPTION_CHECK_C_ORDER_ID+record.getId(), true), new TypeReference<MutableTriple<Long, Date, Long>>() {
        });
        if(resultC!=null){
            Assert.isTrue(resultC.right.longValue()==record.getPharmacistId().longValue(),"您非法审核C阶段操作");
        }
        else{
            MutableTriple<Long, Date, Long> resultE = JsonUtils.fromJson(redisService.getValueRedisByKey(ConstantAll.REDIS_PRESCRIPTION_CHECK_E_ORDER_ID+record.getId(), true), new TypeReference<MutableTriple<Long, Date, Long>>() {
            });
            if(resultE!=null){
                Assert.isTrue(resultE.right.longValue()==record.getPharmacistId().longValue(),"可能在于您没有在规定时间内及时审核造成,当前审核E阶段操作人不是您!");
                caseInfo = ConstantAll.REDIS_PRESCRIPTION_CHECK_E_ORDER_ID;
            }
            else{
                Assert.isTrue(false,"阶段审核已经超时或者您在重复操作");
            }
        }
        return caseInfo;
    }

    private String getCaseInfoForBE(MedicalPrescription record) {
        String caseInfo = ConstantAll.REDIS_PRESCRIPTION_CHECK_B_ORDER_ID;
        MutableTriple<Long, Date, Long> resultB = JsonUtils.fromJson(redisService.getValueRedisByKey(ConstantAll.REDIS_PRESCRIPTION_CHECK_B_ORDER_ID+record.getId(), true), new TypeReference<MutableTriple<Long, Date, Long>>() {
        });
        if(resultB!=null){
            Assert.isTrue(resultB.right.longValue()==record.getPharmacistId().longValue(),"您非法审核B阶段操作");
        }
        else{
            MutableTriple<Long, Date, Long> resultE = JsonUtils.fromJson(redisService.getValueRedisByKey(ConstantAll.REDIS_PRESCRIPTION_CHECK_E_ORDER_ID+record.getId(), true), new TypeReference<MutableTriple<Long, Date, Long>>() {
            });
            if(resultE!=null){
                Assert.isTrue(resultE.right.longValue()==record.getPharmacistId().longValue(),"可能在于您没有在规定时间内及时审核造成,当前审核E阶段操作人不是您!");
                caseInfo = ConstantAll.REDIS_PRESCRIPTION_CHECK_E_ORDER_ID;
            }
            else{
                Assert.isTrue(false,"阶段审核已经超时或者您在重复操作");
            }
        }
        return caseInfo;
    }

    @ApiOperation(value = "药师审方通过")
    @Transactional
    public MedicalPrescription createPrescriptionCheckOk(MedicalPrescription record) {
        logger.info("传递数据为:{}", record);
        Assert.isTrue(record.getId()!=null,"请传递处方ID");
        Assert.isTrue(record.getMedicalOrderId()!=null,"请传递MedicalOrderId");
//        Assert.isTrue(record.getViewStatus()!=null,"请传递审核意向[通过,不通过]");
        Assert.isTrue(record.getPharmacistId()!=null,"请传递PharmacistId");
        Assert.isTrue(record.getPharmacyPlatType()!=null,"请传递pharmacyPlatType");
        Assert.isTrue(redisService.hasRedisKey(ConstantAll.REDIS_PRESCRIPTION_ORDER+record.getId()),"您已经超时,其他流程审核已经通过");
        if(record.getPharmacyPlatType()==ConstantAll.PLAT_TYPE_0) {
            String caseInfo = getCaseInfoForBE(record);
            redisService.deleteRedisByKey((StringUtils.equalsIgnoreCase(caseInfo,ConstantAll.REDIS_PRESCRIPTION_CHECK_B_ORDER_ID)?ConstantAll.REDIS_PRESCRIPTION_CHECK_B_ORDER_ID:ConstantAll.REDIS_PRESCRIPTION_CHECK_E_ORDER_ID)+record.getId());//去除对应 超时B环节
            redisService.deleteRedisByKey(ConstantAll.REDIS_PRESCRIPTION_ORDER+record.getId());//去除对应处方处理
            if(StringUtils.equalsIgnoreCase(caseInfo,ConstantAll.REDIS_PRESCRIPTION_CHECK_B_ORDER_ID)){
                redisLogService.insertSelective(new RedisLog(ConstantAll.LOG_MEDICAL_PRES,ConstantAll.LOG_MEDICAL_PRES_SUB_TIME_OUT_CHECK_B,ConstantAll.REDIS_PRESCRIPTION_CHECK_B_ORDER_ID+record.getId(),"弱化对应的内容",0L,ConstantAll.STRING_EMPTY,ConstantAll.LOG_REMARK_DELETE+"--医生点击患者未在家"));
            }else{
                redisLogService.insertSelective(new RedisLog(ConstantAll.LOG_MEDICAL_PRES,ConstantAll.LOG_MEDICAL_PRES_SUB_TIME_OUT_CHECK_E,ConstantAll.REDIS_PRESCRIPTION_CHECK_E_ORDER_ID+record.getId(),"弱化对应的内容",0L,ConstantAll.STRING_EMPTY,ConstantAll.LOG_REMARK_DELETE+"--医生点击患者未在家"));
            }
            redisLogService.insertSelective(new RedisLog(ConstantAll.LOG_MEDICAL_PRES,ConstantAll.LOG_MEDICAL_PRES_SUB_CURRENT_PRESCRIPTION,ConstantAll.REDIS_PRESCRIPTION_ORDER+record.getId(),"弱化对应的内容",0L,ConstantAll.STRING_EMPTY,ConstantAll.LOG_REMARK_DELETE+"--医生点击患者未在家"));

            Date currentDate = new Date();
            record.setViewTime(currentDate);
            //record.setViewInfo("药师审方通过");
            record.setViewStatus(ConstantAll.CHECK_VIEW_STATUS_OK);
            record.setPrescriptionStatus(ConstantAll.MEDICAL_PRESCRIPTION_STATUS_OK);// 完成
            record.setStatusDetail(ConstantAll.MEDICAL_PRESCRIPTION_STATUS_PATIENT_NO_ORDER);//未支付
            record.setGmtModified(currentDate);
            record.setRemarks((StringUtils.equalsIgnoreCase(caseInfo,ConstantAll.REDIS_PRESCRIPTION_CHECK_B_ORDER_ID)?"B阶段":"E阶段")+"普通药师审方通过");
            this.updateByPrimaryKeySelective(record);

            MedicalOrder medicalOrder1 = new MedicalOrder();
            medicalOrder1.setId(record.getMedicalOrderId());
            medicalOrder1.setStatus(ConstantAll.ORDER_STATUS_OK);//患者 完成
            medicalOrder1.setStatusDetail(ConstantAll.ORDER_STATUS_OK);//完成
            medicalOrder1.setOverTime(currentDate);
            medicalOrder1.setOverType(ConstantAll.ORDER_END_TYPE_OK_TIME_OUT_PLAT_1);
            medicalOrder1.setRemarks((StringUtils.equalsIgnoreCase(caseInfo,ConstantAll.REDIS_PRESCRIPTION_CHECK_B_ORDER_ID)?"B阶段":"E阶段")+"普通药师审方 完成");
            medicalOrder1.setGmtModified(currentDate);
            medicalOrderService.updateByPrimaryKeySelective(medicalOrder1);

            considerToTakeSendOrder(record.getId(),(StringUtils.equalsIgnoreCase(caseInfo,ConstantAll.REDIS_PRESCRIPTION_CHECK_B_ORDER_ID)?"B阶段":"E阶段")+"--普通药师通过处方 整体完成门诊订单，");//考虑取药存药订单计时处理
        }
        else if(record.getPharmacyPlatType()==ConstantAll.PLAT_TYPE_1) {
            String caseInfo = getCaseInfoForCE(record);
            redisService.deleteRedisByKey((StringUtils.equalsIgnoreCase(caseInfo,ConstantAll.REDIS_PRESCRIPTION_CHECK_C_ORDER_ID)?ConstantAll.REDIS_PRESCRIPTION_CHECK_C_ORDER_ID:ConstantAll.REDIS_PRESCRIPTION_CHECK_E_ORDER_ID)+record.getId());//去除对应 超时B环节
            redisService.deleteRedisByKey(ConstantAll.REDIS_PRESCRIPTION_ORDER+record.getId());//去除对应处方处理
            if(StringUtils.equalsIgnoreCase(caseInfo,ConstantAll.REDIS_PRESCRIPTION_CHECK_C_ORDER_ID)){
                redisLogService.insertSelective(new RedisLog(ConstantAll.LOG_MEDICAL_PRES,ConstantAll.LOG_MEDICAL_PRES_SUB_TIME_OUT_CHECK_C,ConstantAll.REDIS_PRESCRIPTION_CHECK_C_ORDER_ID+record.getId(),"弱化对应的内容",0L,ConstantAll.STRING_EMPTY,ConstantAll.LOG_REMARK_DELETE+"--医生点击患者未在家"));
            }else{
                redisLogService.insertSelective(new RedisLog(ConstantAll.LOG_MEDICAL_PRES,ConstantAll.LOG_MEDICAL_PRES_SUB_TIME_OUT_CHECK_E,ConstantAll.REDIS_PRESCRIPTION_CHECK_E_ORDER_ID+record.getId(),"弱化对应的内容",0L,ConstantAll.STRING_EMPTY,ConstantAll.LOG_REMARK_DELETE+"--医生点击患者未在家"));
            }
            redisLogService.insertSelective(new RedisLog(ConstantAll.LOG_MEDICAL_PRES,ConstantAll.LOG_MEDICAL_PRES_SUB_CURRENT_PRESCRIPTION,ConstantAll.REDIS_PRESCRIPTION_ORDER+record.getId(),"弱化对应的内容",0L,ConstantAll.STRING_EMPTY,ConstantAll.LOG_REMARK_DELETE+"--医生点击患者未在家"));

            Date currentDate = new Date();
            record.setViewTime(currentDate);
            //record.setViewInfo("平台药师审方通过");
            record.setViewStatus(ConstantAll.CHECK_VIEW_STATUS_OK);
            record.setPrescriptionStatus(ConstantAll.MEDICAL_PRESCRIPTION_STATUS_OK);// 完成
            record.setStatusDetail(ConstantAll.MEDICAL_PRESCRIPTION_STATUS_PATIENT_NO_ORDER);//未支付
            record.setGmtModified(currentDate);
            record.setRemarks((StringUtils.equalsIgnoreCase(caseInfo,ConstantAll.REDIS_PRESCRIPTION_CHECK_C_ORDER_ID)?"C阶段":"E阶段")+"平台药师审方通过");
            this.updateByPrimaryKeySelective(record);

            MedicalOrder medicalOrder1 = new MedicalOrder();
            medicalOrder1.setId(record.getMedicalOrderId());
            medicalOrder1.setStatus(ConstantAll.ORDER_STATUS_OK);//患者 完成
            medicalOrder1.setStatusDetail(ConstantAll.ORDER_STATUS_OK);//完成
            medicalOrder1.setOverTime(currentDate);
            medicalOrder1.setOverType(ConstantAll.ORDER_END_TYPE_OK_TIME_OUT_PLAT_2);
            medicalOrder1.setRemarks((StringUtils.equalsIgnoreCase(caseInfo,ConstantAll.REDIS_PRESCRIPTION_CHECK_C_ORDER_ID)?"C阶段":"E阶段")+"平台药师审方 完成");
            medicalOrder1.setGmtModified(new Date());
            medicalOrderService.updateByPrimaryKeySelective(medicalOrder1);

            considerToTakeSendOrder(record.getId(),(StringUtils.equalsIgnoreCase(caseInfo,ConstantAll.REDIS_PRESCRIPTION_CHECK_C_ORDER_ID)?"C阶段":"E阶段")+"--平台药师通过处方 整体完成门诊订单，");//考虑取药存药订单计时处理
        }
        return record;
    }
    @ApiOperation(value = "药师抢单审方")
    @Transactional
    public MedicalPrescription createPrescriptionGrapCheck(MedicalPrescription record) {
        logger.info("传递数据为:{}", record);
        Assert.isTrue(record.getId()!=null,"请传递ID");
        Assert.isTrue(record.getPharmacistId()!=null,"请传递PharmacistId");
        Assert.isTrue(record.getPharmacistName()!=null,"请传递PharmacistName");
        Assert.isTrue(record.getMedicalOrderId()!=null,"请传递medicalOrderId");
//        Assert.isTrue(record.getPharmacyPlatType()!=null,"请传递pharmacyPlatType");

//        MutableTriple<Long, Date, Long> resultCheckA = JsonUtils.fromJson(redisService.getValueRedisByKey(ConstantAll.REDIS_PRESCRIPTION_CHECK_A_ORDER_ID+record.getId()), new TypeReference<MutableTriple<Long, Date, Long>>() {
//        });
//        Assert.isTrue(resultCheckA!=null,"很抱歉,已经被其他药师抢单,调试阶段:具体信息-->");

        Assert.isTrue(redisService.hasRedisKey(ConstantAll.REDIS_PRESCRIPTION_CHECK_A_ORDER_ID+record.getId()),"很抱歉,已经被其他药师抢单");
        redisService.deleteRedisByKey(ConstantAll.REDIS_PRESCRIPTION_CHECK_A_ORDER_ID+record.getId());//去除对应 超时A环节
        redisLogService.insertSelective(new RedisLog(ConstantAll.LOG_MEDICAL_PRES,ConstantAll.LOG_MEDICAL_PRES_SUB_TIME_OUT_CHECK_A,ConstantAll.REDIS_PRESCRIPTION_CHECK_A_ORDER_ID+record.getId(),"弱化对应的内容",0L,ConstantAll.STRING_EMPTY,ConstantAll.LOG_REMARK_DELETE+"--药师点击 抢单审方"));
        Date currentDate = new Date();
        MutableTriple<Long, Date, Long> mutableTriple = new MutableTriple<Long, Date, Long>();
        mutableTriple.left = record.getId();
        mutableTriple.middle = currentDate;
        mutableTriple.right = record.getPharmacistId();
        redisService.setJsonObjectBy(ConstantAll.REDIS_PRESCRIPTION_CHECK_B_ORDER_ID+mutableTriple.left,mutableTriple,ConstantAll.VALUE_CONSIDER_DAY_TIME,ConstantAll.UNIT_CONSIDER);
        redisLogService.insertSelective(new RedisLog(ConstantAll.LOG_MEDICAL_PRES,ConstantAll.LOG_MEDICAL_PRES_SUB_TIME_OUT_CHECK_B,ConstantAll.REDIS_PRESCRIPTION_CHECK_B_ORDER_ID+mutableTriple.left,JsonUtils.toJsonString(mutableTriple),ConstantAll.VALUE_CONSIDER_DAY_TIME,ConstantAll.UNIT_CONSIDER.toString(),ConstantAll.LOG_REMARK_SET+"--药师点击 抢单审方"));
        record.setGmtModified(currentDate);
        record.setPharmacistId(record.getPharmacistId());
        record.setPharmacistName(record.getPharmacistName());
        record.setPharmacyPlatType(ConstantAll.PLAT_TYPE_0);//普通药师
        record.setRemarks("药师抢单审方");
        this.updateByPrimaryKeySelective(record);
        return record;
    }
    @ApiOperation(value = "创建处方")
    @Transactional
    public MedicalPrescription createPrescription(MedicalPrescription record){
        logger.info("插入数据为:{}",record);
        MedicalOrder medicalOrder = this.medicalOrderService.getRedisMedicalOrderFirstUpdateById(record.getMedicalOrderId());
//        logger.info("查询结果:{}",medicalOrder);
        Assert.isTrue(medicalOrder!=null,"您传递的上门就诊订单已经无效,请选择有效的订单信息");
        Assert.isTrue(StringUtils.equalsIgnoreCase(medicalOrder.getStatusDetail(),ConstantAll.ORDER_STATUS_START_SERVICE_CONFIRM),"只有患者上门确认的订单方可创建处方!");


        Date currentDate = new Date();
        if(record.getDrugList()==null || record.getDrugList().size()==0){
/*
            MedicalOrderRequest medicalOrderRequest = new MedicalOrderRequest();
            medicalOrderRequest.setOrderNo(record.getOrderNo());
            MedicalOrder medicalOrder = medicalOrderService.selectMedicalOrderByIdKey(medicalOrderRequest);
*/
//        String prescriptionId = "PP"+ RandomUtils.randomNumeric(4) + UUID.randomUUID().toString().replace("-","").toUpperCase();
//            String prescriptionId = OrderIdUtils.genOrderIdWithDateUserId(StringUtils.leftPad(medicalOrder.getDoctorId().toString(),5,"P"));
            String prescriptionId = idGenerator.nextId().toString();
            record.setOrderNo(medicalOrder.getOrderNo());
            record.setPrescriptionTime(new Date());
            record.setPrescriptionId(prescriptionId);
            record.setOrganizationId(medicalOrder.getHospitalId().toString());
            record.setOrganizationName(medicalOrder.getHospitalName());
            record.setHospitalAddress(medicalOrder.getHospitalAddress());
            record.setHospitalContact(medicalOrder.getHospitalContact());
            record.setCatalogId(medicalOrder.getCatalogId());
            record.setDepartmentId(medicalOrder.getDepartmentId());
            record.setPatientId(medicalOrder.getPatientId());
            record.setPatientIdCardNo(medicalOrder.getPatientIdCardNo());
            record.setPatientName(medicalOrder.getPatientName());
            record.setPatientAddressSummary(medicalOrder.getPatientAddressSummary());
            record.setPatientAddress(medicalOrder.getPatientAddress());
            record.setPatientContact(medicalOrder.getPatientContact());
            record.setPatientSex(medicalOrder.getPatientSex());
            record.setPatientYearOld(medicalOrder.getPatientYearOld());
            record.setDoctorId(medicalOrder.getDoctorId());
            record.setDoctorTitle(medicalOrder.getDoctorTitle());
            record.setDoctorName(medicalOrder.getDoctorName());
            record.setPharmacyId(medicalOrder.getPharmacyId());
            record.setPharmacyName(medicalOrder.getPharmacyName());
            record.setPharmacyAddress(medicalOrder.getPharmacyAddress());
            record.setPharmacyContact(medicalOrder.getPharmacyContact());
            record.setServiceFee(1000);
            record.setViewInfo("没有药品处方信息,自动通过");
            record.setViewTime(new Date());
            record.setStatusDetail(ConstantAll.MEDICAL_PRESCRIPTION_STATUS_PATIENT_NO_DRUG);
            record.setPrescriptionStatus(ConstantAll.MEDICAL_PRESCRIPTION_STATUS_OK);
            record.setGmtModified(currentDate);
            record.setCreateTime(currentDate);
            record.setGmtCreate(currentDate);
            record.setRemarks("自动通过");
            daoMedicalPrescriptionMapper.insertSelective(record);

            MedicalOrder medicalOrder1 = new MedicalOrder();
            medicalOrder1.setId(record.getMedicalOrderId());
            medicalOrder1.setOther(ConstantAll.STRING_EMPTY);//患者摆布处方情况
            medicalOrder1.setStatus(ConstantAll.ORDER_STATUS_OK);//患者 完成
            medicalOrder1.setStatusDetail(ConstantAll.ORDER_STATUS_OK);//完成
            medicalOrder1.setOverType(ConstantAll.ORDER_END_TYPE_OK_NO_PRESCRIPTION);
            medicalOrder1.setOverTime(currentDate);
            medicalOrder1.setDoctorSignature(record.getDoctorSignature());
            medicalOrder1.setRemind(record.getAdvice());
            medicalOrder1.setRemarks("完成");
            medicalOrder1.setGmtModified(currentDate);
            medicalOrderService.updateByPrimaryKeySelective(medicalOrder1);
        }
        else{
            List<OrderDrugs> lockOnlyDrugList = Lists.newArrayList();
            record.getDrugList().forEach(drug->{
                OrderDrugs lockDrug = lockOnlyDrugList.stream().filter(t->StringUtils.equalsIgnoreCase(t.getDrugCode(),drug.getDrugCode())).findFirst().orElse(null);
                if(lockDrug==null){
                    lockOnlyDrugList.add(drug);
                }
                else{
                    lockDrug.setDrugCount(lockDrug.getDrugCount()+drug.getDrugCount());
                }
            });
            Assert.isTrue(lockOnlyDrugList.size()==record.getDrugList().size(),"请您确认处方不存在药品重复！");
            String prescriptionId = idGenerator.nextId().toString();
            record.setOrderNo(medicalOrder.getOrderNo());
            record.setPrescriptionTime(new Date());
            record.setPrescriptionId(prescriptionId);
            record.setOrganizationId(medicalOrder.getHospitalId().toString());
            record.setOrganizationName(medicalOrder.getHospitalName());
            record.setHospitalAddress(medicalOrder.getHospitalAddress());
            record.setHospitalContact(medicalOrder.getHospitalContact());
            record.setCatalogId(medicalOrder.getCatalogId());
            record.setDepartmentId(medicalOrder.getDepartmentId());
            record.setPatientId(medicalOrder.getPatientId());
            record.setPatientIdCardNo(medicalOrder.getPatientIdCardNo());
            record.setPatientName(medicalOrder.getPatientName());
            record.setPatientAddressSummary(medicalOrder.getPatientAddressSummary());
            record.setPatientAddress(medicalOrder.getPatientAddress());
            record.setPatientContact(medicalOrder.getPatientContact());
            record.setPatientSex(medicalOrder.getPatientSex());
            record.setPatientYearOld(medicalOrder.getPatientYearOld());
            record.setDoctorId(medicalOrder.getDoctorId());
            record.setDoctorTitle(medicalOrder.getDoctorTitle());
            record.setDoctorName(medicalOrder.getDoctorName());
            record.setPharmacyId(medicalOrder.getPharmacyId());
            record.setPharmacyName(medicalOrder.getPharmacyName());
            record.setPharmacyAddress(medicalOrder.getPharmacyAddress());
            record.setPharmacyContact(medicalOrder.getPharmacyContact());
            record.setServiceFee(1000);
            record.getDrugList().forEach(drug->{
                drug.setOrderNo(medicalOrder.getOrderNo());
                drug.setPrescriptionId(prescriptionId);
                orderDrugsService.insertSelective(drug);
            });
            record.setStatusDetail(ConstantAll.MEDICAL_PRESCRIPTION_STATUS_PATIENT_ALL);
            record.setPrescriptionStatus(ConstantAll.MEDICAL_PRESCRIPTION_STATUS_INIT);
            record.setGmtModified(currentDate);
            record.setCreateTime(currentDate);
            record.setGmtCreate(currentDate);
            record.setRemarks("刚刚创建出来处方,若没有抢单,要考虑在"+ConstantAll.VALUE_TIME_OUT_CHECK_A+"秒后超时分配给自有药师审方");
            daoMedicalPrescriptionMapper.insertSelective(record);

            MedicalOrder medicalOrder1 = new MedicalOrder();
            medicalOrder1.setId(medicalOrder.getId());
            medicalOrder1.setStatus(ConstantAll.ORDER_STATUS_PRESCRIPTION_CHECK);//患者 完成
            medicalOrder1.setStatusDetail(ConstantAll.ORDER_STATUS_PRESCRIPTION_CHECK);//完成
            medicalOrder1.setRemarks("处方审核中");
            medicalOrder1.setGmtModified(currentDate);
            medicalOrder1.setRemind(record.getAdvice());
            medicalOrder1.setDoctorSignature(record.getDoctorSignature());
            medicalOrderService.updateByPrimaryKeySelective(medicalOrder1);


            String keyTimeOutA = ConstantAll.REDIS_PRESCRIPTION_CHECK_A_ORDER_ID+record.getId();//处方产生订单超时记录
            MutableTriple<Long, Date, Long> mutableTriple = new MutableTriple<Long, Date, Long>();
            mutableTriple.left = record.getId();
            mutableTriple.middle = new Date();
            mutableTriple.right = ConstantAll.VALUE_TIME_OUT_CHECK_A;
            redisService.setJsonObjectBy(keyTimeOutA,mutableTriple,ConstantAll.VALUE_CONSIDER_DAY_TIME,ConstantAll.UNIT_CONSIDER);//一个处方最长考虑5整天[无限制不能有效释放资源]
            redisLogService.insertSelective(new RedisLog(ConstantAll.LOG_MEDICAL_PRES,ConstantAll.LOG_MEDICAL_PRES_SUB_TIME_OUT_CHECK_A,keyTimeOutA,JsonUtils.toJsonString(mutableTriple),ConstantAll.VALUE_CONSIDER_DAY_TIME,ConstantAll.UNIT_CONSIDER.toString(),ConstantAll.LOG_REMARK_SET+"--  医生点击提交处方 阶段A"));
            String keyForNewPrescription = ConstantAll.REDIS_PRESCRIPTION_ORDER+record.getId();//有处方产生
            redisService.setJsonObjectBy(keyForNewPrescription,record,ConstantAll.VALUE_CONSIDER_DAY_TIME,ConstantAll.UNIT_CONSIDER);//一个处方最长考虑5整天[无限制不能有效释放资源]
            redisLogService.insertSelective(new RedisLog(ConstantAll.LOG_MEDICAL_PRES,ConstantAll.LOG_MEDICAL_PRES_SUB_CURRENT_PRESCRIPTION,keyTimeOutA,JsonUtils.toJsonString(record),ConstantAll.VALUE_CONSIDER_DAY_TIME,ConstantAll.UNIT_CONSIDER.toString(),ConstantAll.LOG_REMARK_SET+"--  医生点击提交处方 考虑此处方"));

            List<Pharmacist> pharmacistList = pharmacistService.getListAllCheck();
            List<String> listPushUserId = Lists.newArrayList();
            pharmacistList.forEach(t->{
                listPushUserId.add(toolShareService.getAppInfoByTypeId(ConstantAll.TYPE_ID_PHARMACIST)+t.getId());
            });
            appPushService.pushListUserIdApp(ConstantAll.TYPE_ID_PHARMACIST,"医生处方已经产生","请抢单进行审核",listPushUserId);

        }
        return record;
    }


    @ApiOperation(value = "药师审核超期处理 环节ABCDE")
    @Transactional
    public void doWithCheckABCDE(Set<String> stringSet,Date currentDate) {
        if (stringSet.size() > 0) {//存在要考虑的处方
//            logger.info(">>如果redis 有处方新订单列表 或配置强硬要 考虑 才浪费去DB查询");
//            logger.info(">>用于定期查看订单 条件status=0 prescription_status= now()-gmt_create>30 view_time is null");
//            logger.info(">>平台药师列表中，分配个给处方，推送信息给药师；比较如果pharmacist_id 已经一直，相当于反复提醒，没有尽快处理");

            //A 环节超时
            Set<String> keyTimeOutASet = redisService.getRedisKeysByPattern(ConstantAll.REDIS_PRESCRIPTION_CHECK_A_ORDER_ID + "*");//查看redis处方在B环节分配超时
            keyTimeOutASet.forEach(keyTimeOutA -> {
                MutableTriple<Long, Date, Long> result = JsonUtils.fromJson(redisService.getValueRedisByKey(keyTimeOutA, false), new TypeReference<MutableTriple<Long, Date, Long>>() {
                });
                if (currentDate.getTime() - result.middle.getTime() > ConstantAll.VALUE_TIME_OUT_CHECK_A * ConstantAll.VALUE_MICROSECOND_1000) {//超时A
                    redisService.deleteRedisByKey(keyTimeOutA, false);//去除对应 超时A环节
                    redisLogService.insertSelective(new RedisLog(ConstantAll.LOG_MEDICAL_PRES, ConstantAll.LOG_MEDICAL_PRES_SUB_TIME_OUT_CHECK_A, ConstantAll.REDIS_PRESCRIPTION_CHECK_A_ORDER_ID + result.left, "弱化对应的内容", 0L, ConstantAll.STRING_EMPTY, ConstantAll.LOG_REMARK_DELETE + "--系统判定A环节超时,自动跳到C环节并分配平台药师"));
                    logger.info("A 环节有要考虑的 key:{} result:{}",keyTimeOutA,result);

                    if (redisService.hasRedisKey(ConstantAll.REDIS_PRESCRIPTION_ORDER + result.left)) {
                        List<Pharmacist> pharmacistList = pharmacistService.getListAllPlat();
                        int totalPlat = pharmacistList.size();
                        if (totalPlat > 0) {
                            List<String> listPushUserId = Lists.newArrayList();
                            Pharmacist platPharmacist = pharmacistList.get(0);
                            //更新处方信息
                            MedicalPrescription medicalPrescription = new MedicalPrescription();
                            medicalPrescription.setId(result.left);
                            medicalPrescription.setPharmacistId(platPharmacist.getId());
                            medicalPrescription.setPharmacistName(platPharmacist.getTrueName());
                            medicalPrescription.setPharmacyPlatType(ConstantAll.PLAT_TYPE_1);
                            medicalPrescription.setPrescriptionStatus(ConstantAll.MEDICAL_PRESCRIPTION_STATUS_VIEW);//  审核中 未审核  (在查看 思考中)
                            medicalPrescription.setGmtModified(new Date());
                            medicalPrescription.setRemarks("A环节超时,自动分配给平台药师 为:" + platPharmacist.getTrueName());
                            this.updateByPrimaryKeySelective(medicalPrescription);
                            //设定自有平台药师阶段C时间
                            String keyTimeOutC = ConstantAll.REDIS_PRESCRIPTION_CHECK_C_ORDER_ID + result.left;//处方产生订单超时记录
                            MutableTriple<Long, Date, Long> mutableTriple = new MutableTriple<Long, Date, Long>();
                            mutableTriple.left = result.left;
                            mutableTriple.middle = new Date();
                            mutableTriple.right = platPharmacist.getId();
                            redisService.setJsonObjectBy(keyTimeOutC, mutableTriple, ConstantAll.VALUE_CONSIDER_DAY_TIME, ConstantAll.UNIT_CONSIDER);
                            redisLogService.insertSelective(new RedisLog(ConstantAll.LOG_MEDICAL_PRES, ConstantAll.LOG_MEDICAL_PRES_SUB_TIME_OUT_CHECK_C, ConstantAll.REDIS_PRESCRIPTION_CHECK_C_ORDER_ID + result.left, JsonUtils.toJsonString(mutableTriple), ConstantAll.VALUE_CONSIDER_DAY_TIME, ConstantAll.UNIT_CONSIDER.toString(), ConstantAll.LOG_REMARK_SET + "--系统判定A环节超时,自动跳到C环节并分配平台药师"));

                            //推送给新的平台
                            listPushUserId.add(toolShareService.getAppInfoByTypeId(ConstantAll.TYPE_ID_PHARMACIST) + platPharmacist.getId());
                            appPushService.pushListUserIdApp(ConstantAll.TYPE_ID_PHARMACIST, "处方发出A阶段超时 " + ConstantAll.VALUE_TIME_OUT_CHECK_A + "秒", "转向您这个平台人员要是进行审核,您需要在" + ConstantAll.VALUE_TIME_OUT_CHECK_C + "秒内审核完毕,否则将超时通过!", listPushUserId);
                        } else {
                            logger.info("没有找到任何有效的平台药师", new Exception());
                        }
                    }
                    else{
                        logger.info("处方信息消失掉了,环节阶段A", new Exception());
                    }
                }

            });


            //B 环节超时
            Set<String> keyTimeOutBSet = redisService.getRedisKeysByPattern(ConstantAll.REDIS_PRESCRIPTION_CHECK_B_ORDER_ID + "*");//查看redis处方在A环节分配超时
            keyTimeOutBSet.forEach(keyTimeOutB -> {
                MutableTriple<Long, Date, Long> result = JsonUtils.fromJson(redisService.getValueRedisByKey(keyTimeOutB, false), new TypeReference<MutableTriple<Long, Date, Long>>() {
                });
                if (currentDate.getTime() - result.middle.getTime() > ConstantAll.VALUE_TIME_OUT_CHECK_B * ConstantAll.VALUE_MICROSECOND_1000) {//超时B
                    redisService.deleteRedisByKey(keyTimeOutB, false);//去除对应 超时B环节
                    redisLogService.insertSelective(new RedisLog(ConstantAll.LOG_MEDICAL_PRES, ConstantAll.LOG_MEDICAL_PRES_SUB_TIME_OUT_CHECK_B, ConstantAll.REDIS_PRESCRIPTION_CHECK_B_ORDER_ID + result.left, "弱化对应的内容", 0L, ConstantAll.STRING_EMPTY, ConstantAll.LOG_REMARK_DELETE + "--系统判定B环节超时,自动跳到C环节并分配平台药师"));
                    logger.info("B 环节有要考虑的 key:{} result:{}",keyTimeOutB,result);

                    if (redisService.hasRedisKey(ConstantAll.REDIS_PRESCRIPTION_ORDER + result.left)) {
                        List<String> listPushUserIdOld = Lists.newArrayList();
                        listPushUserIdOld.add(toolShareService.getAppInfoByTypeId(ConstantAll.TYPE_ID_PHARMACIST) + result.right);
                        appPushService.pushListUserIdApp(ConstantAll.TYPE_ID_PHARMACIST, "处方审核 B 环节超时," + ConstantAll.VALUE_TIME_OUT_CHECK_B + "秒您竟然还没审核完成", "将有其他平台人员要是进行审核", listPushUserIdOld);


                        List<Pharmacist> pharmacistList = pharmacistService.getListAllPlat();
                        int totalPlat = pharmacistList.size();
                        if (totalPlat > 0) {
                            List<String> listPushUserId = Lists.newArrayList();
                            Pharmacist platPharmacist = pharmacistList.get(0);
                            //更新处方信息
                            MedicalPrescription medicalPrescription = new MedicalPrescription();
                            medicalPrescription.setId(result.left);
                            medicalPrescription.setPharmacistId(platPharmacist.getId());
                            medicalPrescription.setPharmacistName(platPharmacist.getTrueName());
                            medicalPrescription.setPharmacyPlatType(ConstantAll.PLAT_TYPE_1);
                            medicalPrescription.setPrescriptionStatus(ConstantAll.MEDICAL_PRESCRIPTION_STATUS_VIEW);//  审核中 未审核  (在查看 思考中)
                            medicalPrescription.setGmtModified(new Date());
                            medicalPrescription.setRemarks("B环节超时,自动分配给平台药师 为:" + platPharmacist.getTrueName());
                            this.updateByPrimaryKeySelective(medicalPrescription);
                            //设定自有平台药师阶段C时间
                            String keyTimeOutC = ConstantAll.REDIS_PRESCRIPTION_CHECK_C_ORDER_ID + result.left;//处方产生订单超时记录
                            MutableTriple<Long, Date, Long> mutableTriple = new MutableTriple<Long, Date, Long>();
                            mutableTriple.left = result.left;
                            mutableTriple.middle = new Date();
                            mutableTriple.right = platPharmacist.getId();
                            redisService.setJsonObjectBy(keyTimeOutC, mutableTriple, ConstantAll.VALUE_CONSIDER_DAY_TIME, ConstantAll.UNIT_CONSIDER);
                            redisLogService.insertSelective(new RedisLog(ConstantAll.LOG_MEDICAL_PRES, ConstantAll.LOG_MEDICAL_PRES_SUB_TIME_OUT_CHECK_C, ConstantAll.REDIS_PRESCRIPTION_CHECK_C_ORDER_ID + result.left, JsonUtils.toJsonString(mutableTriple), ConstantAll.VALUE_CONSIDER_DAY_TIME, ConstantAll.UNIT_CONSIDER.toString(), ConstantAll.LOG_REMARK_SET + "--系统判定B环节设定"));

                            //推送给新的平台
                            listPushUserId.add(toolShareService.getAppInfoByTypeId(ConstantAll.TYPE_ID_PHARMACIST) + platPharmacist.getId());
                            appPushService.pushListUserIdApp(ConstantAll.TYPE_ID_PHARMACIST, "处方发出B阶段超时 " + ConstantAll.VALUE_TIME_OUT_CHECK_B + "秒", "转向您这个平台人员要是进行审核,您需要在" + ConstantAll.VALUE_TIME_OUT_CHECK_C + "秒内审核完毕,否则将超时通过!", listPushUserId);
                        } else {
                            logger.info("没有找到任何有效的平台药师", new Exception());
                        }
                    }
                    else{
                        logger.info("处方信息消失掉了,环节阶段B", new Exception());
                    }

                }

            });


            //C 环节超时
            Set<String> keyTimeOutCSet = redisService.getRedisKeysByPattern(ConstantAll.REDIS_PRESCRIPTION_CHECK_C_ORDER_ID + "*");//查看redis处方在C环节分配超时
            keyTimeOutCSet.forEach(keyTimeOutC -> {
                MutableTriple<Long, Date, Long> result = JsonUtils.fromJson(redisService.getValueRedisByKey(keyTimeOutC, false), new TypeReference<MutableTriple<Long, Date, Long>>() {
                });
                if (currentDate.getTime() - result.middle.getTime() > ConstantAll.VALUE_TIME_OUT_CHECK_C * ConstantAll.VALUE_MICROSECOND_1000) {//超时c
                    redisService.deleteRedisByKey(keyTimeOutC, false);//去除对应 超时C环节
                    redisLogService.insertSelective(new RedisLog(ConstantAll.LOG_MEDICAL_PRES, ConstantAll.LOG_MEDICAL_PRES_SUB_TIME_OUT_CHECK_C, ConstantAll.REDIS_PRESCRIPTION_CHECK_C_ORDER_ID + result.left, "弱化对应的内容", 0L, ConstantAll.STRING_EMPTY, ConstantAll.LOG_REMARK_DELETE + "--系统判定A环节超时,自动跳让平台药师超时通过"));
                    logger.info("C 环节有要考虑的 key:{} result:{}",keyTimeOutC,result);

                    if (redisService.hasRedisKey(ConstantAll.REDIS_PRESCRIPTION_ORDER + result.left)) {
                        List<String> listPushUserIdOld = Lists.newArrayList();
                        listPushUserIdOld.add(toolShareService.getAppInfoByTypeId(ConstantAll.TYPE_ID_PHARMACIST) + result.right);
                        appPushService.pushListUserIdApp(ConstantAll.TYPE_ID_PHARMACIST, "处方审核 C 环节超时," + ConstantAll.VALUE_TIME_OUT_CHECK_C + "秒您竟然还没审核完成", "将[超时审核通过]", listPushUserIdOld);

                        //通过缓存获取处方及订单信息
                        MedicalPrescription recordNewPrescription = JsonUtils.fromJson(redisService.getValueRedisByKey(ConstantAll.REDIS_PRESCRIPTION_ORDER + result.left), new TypeReference<MedicalPrescription>() {
                        });
                        if (recordNewPrescription == null) {
                            logger.info("处方信息C消失掉了,很奇怪C", new Exception());
                        } else {
                            redisService.deleteRedisByKey(ConstantAll.REDIS_PRESCRIPTION_ORDER + result.left);//通过要去掉订单处理
                            redisLogService.insertSelective(new RedisLog(ConstantAll.LOG_MEDICAL_PRES, ConstantAll.LOG_MEDICAL_PRES_SUB_CURRENT_PRESCRIPTION, ConstantAll.REDIS_PRESCRIPTION_ORDER + result.left, "弱化对应的内容", 0L, ConstantAll.STRING_EMPTY, ConstantAll.LOG_REMARK_DELETE + "--C环节超时通过"));

                            Date nowDate = new Date();
                            MedicalPrescription medicalPrescription = new MedicalPrescription();
                            medicalPrescription.setId(result.left);
                            medicalPrescription.setGmtModified(nowDate);
                            medicalPrescription.setRemarks("C环节超时,超时通过审方");
                            medicalPrescription.setViewInfo("超时通过审方");
                            medicalPrescription.setViewTime(nowDate);
                            medicalPrescription.setViewStatus(ConstantAll.CHECK_VIEW_STATUS_OK_TIMEOUT);
                            medicalPrescription.setPrescriptionStatus(ConstantAll.MEDICAL_PRESCRIPTION_STATUS_OK);
                            medicalPrescription.setStatusDetail(ConstantAll.MEDICAL_PRESCRIPTION_STATUS_PATIENT_NO_ORDER);//未支付
                            this.updateByPrimaryKeySelective(medicalPrescription);

                            MedicalOrder medicalOrder = new MedicalOrder();
                            medicalOrder.setId(recordNewPrescription.getMedicalOrderId());
                            medicalOrder.setStatus(ConstantAll.ORDER_STATUS_OK);//患者 完成
                            medicalOrder.setStatusDetail(ConstantAll.ORDER_STATUS_OK);//患者 完成
                            medicalOrder.setOverTime(nowDate);
                            medicalOrder.setOverType(ConstantAll.ORDER_END_TYPE_OK_TIME_OUT_C);
                            medicalOrder.setRemarks("超时通过审方,门诊完成");
                            medicalOrder.setGmtModified(new Date());
                            this.medicalOrderService.updateByPrimaryKeySelective(medicalOrder);

                            considerToTakeSendOrder(result.left,"--C环节超时审方导致的通过处方 整体完成门诊订单，");//考虑取药存药订单计时处理
                        }
                    }
                    else{
                        logger.info("处方信息消失掉了,环节阶段C", new Exception());
                    }

                }

            });


            //D 环节超时
            Set<String> keyTimeOutDSet = redisService.getRedisKeysByPattern(ConstantAll.REDIS_PRESCRIPTION_CHECK_D_ORDER_ID + "*");//查看redis处方在D环节分配超时
            keyTimeOutDSet.forEach(keyTimeOutD -> {
                MutableTriple<Long, Date, Long> result = JsonUtils.fromJson(redisService.getValueRedisByKey(keyTimeOutD, false), new TypeReference<MutableTriple<Long, Date, Long>>() {
                });
                if (currentDate.getTime() - result.middle.getTime() > ConstantAll.VALUE_TIME_OUT_CHECK_D * ConstantAll.VALUE_MICROSECOND_1000) {//超时D
                    redisService.deleteRedisByKey(keyTimeOutD, false);//去除对应 超时D环节
                    redisLogService.insertSelective(new RedisLog(ConstantAll.LOG_MEDICAL_PRES, ConstantAll.LOG_MEDICAL_PRES_SUB_TIME_OUT_CHECK_D, ConstantAll.REDIS_PRESCRIPTION_CHECK_D_ORDER_ID + result.left, "弱化对应的内容", 0L, ConstantAll.STRING_EMPTY, ConstantAll.LOG_REMARK_DELETE + "--系统判定D环节超时,自动跳让医生担责签名"));
                    logger.info("D 环节有要考虑的 key:{} result:{}",keyTimeOutD,result);

                    if (redisService.hasRedisKey(ConstantAll.REDIS_PRESCRIPTION_ORDER + result.left)) {
                        //通过缓存获取处方及订单信息
                        MedicalPrescription recordNewPrescription = JsonUtils.fromJson(redisService.getValueRedisByKey(ConstantAll.REDIS_PRESCRIPTION_ORDER + result.left), new TypeReference<MedicalPrescription>() {
                        });
                        if (recordNewPrescription == null) {
                            logger.info("处方信息在D环节消失掉了,很奇怪D", new Exception());
                        } else {
                            List<String> listPushUserIdOld = Lists.newArrayList();
                            listPushUserIdOld.add(toolShareService.getAppInfoByTypeId(ConstantAll.TYPE_ID_DOCTOR) + recordNewPrescription.getDoctorId());
                            appPushService.pushListUserIdApp(ConstantAll.TYPE_ID_PHARMACIST, "处方审核 D 环节超时," + ConstantAll.VALUE_TIME_OUT_CHECK_D + "秒您竟然还没完成修改", "将[担责签字 再次发出处方]", listPushUserIdOld);


                            MedicalPrescription medicalPrescription = new MedicalPrescription();
                            medicalPrescription.setId(result.left);
                            medicalPrescription.setGmtModified(new Date());
                            medicalPrescription.setRemarks("D环节超时,医生自动担责签字");
                            medicalPrescription.setPrescriptionStatus(ConstantAll.MEDICAL_PRESCRIPTION_STATUS_REVIEW);//复审
                            this.updateByPrimaryKeySelective(medicalPrescription);

                            MedicalOrder medicalOrder = new MedicalOrder();
                            medicalOrder.setId(recordNewPrescription.getMedicalOrderId());
                            medicalOrder.setStatus(ConstantAll.ORDER_STATUS_PRESCRIPTION_CHECK);//患者 完成
                            medicalOrder.setStatusDetail(ConstantAll.ORDER_STATUS_PRESCRIPTION_CHECK);//患者 完成
                            medicalOrder.setDoctorSignature(ConstantAll.DOCTOR_SIGNATURE);
                            medicalOrder.setRemarks("D环节超时,医生自动担责签字");
                            medicalOrder.setGmtModified(new Date());
                            this.medicalOrderService.updateByPrimaryKeySelective(medicalOrder);

                            //设定阶段E时间
                            String keyTimeOutE = ConstantAll.REDIS_PRESCRIPTION_CHECK_E_ORDER_ID + result.left;//处方产生订单超时记录
                            MutableTriple<Long, Date, Long> mutableTriple = new MutableTriple<Long, Date, Long>();
                            mutableTriple.left = result.left;
                            mutableTriple.middle = new Date();
                            mutableTriple.right = result.right;//取得是D阶段的时间值
                            redisService.setJsonObjectBy(keyTimeOutE, mutableTriple, ConstantAll.VALUE_CONSIDER_DAY_TIME, ConstantAll.UNIT_CONSIDER);
                            redisLogService.insertSelective(new RedisLog(ConstantAll.LOG_MEDICAL_PRES, ConstantAll.LOG_MEDICAL_PRES_SUB_TIME_OUT_CHECK_E, ConstantAll.REDIS_PRESCRIPTION_CHECK_E_ORDER_ID + result.left, JsonUtils.toJsonString(mutableTriple), ConstantAll.VALUE_CONSIDER_DAY_TIME, ConstantAll.UNIT_CONSIDER.toString(), ConstantAll.LOG_REMARK_SET + "--系统判定E环节设定"));

                        }
                    }
                    else{
                        logger.info("处方信息消失掉了,环节阶段D", new Exception());
                    }

                }

            });

            //E 环节超时
            Set<String> keyTimeOutESet = redisService.getRedisKeysByPattern(ConstantAll.REDIS_PRESCRIPTION_CHECK_E_ORDER_ID + "*");//查看redis处方在C环节分配超时
            keyTimeOutESet.forEach(keyTimeOutE -> {
                MutableTriple<Long, Date, Long> result = JsonUtils.fromJson(redisService.getValueRedisByKey(keyTimeOutE, false), new TypeReference<MutableTriple<Long, Date, Long>>() {
                });
                if (currentDate.getTime() - result.middle.getTime() > ConstantAll.VALUE_TIME_OUT_CHECK_E * ConstantAll.VALUE_MICROSECOND_1000) {//超时E
                    redisService.deleteRedisByKey(keyTimeOutE, false);//去除对应 超时A环节
                    redisLogService.insertSelective(new RedisLog(ConstantAll.LOG_MEDICAL_PRES, ConstantAll.LOG_MEDICAL_PRES_SUB_TIME_OUT_CHECK_E, ConstantAll.REDIS_PRESCRIPTION_CHECK_E_ORDER_ID + result.left, "弱化对应的内容", 0L, ConstantAll.STRING_EMPTY, ConstantAll.LOG_REMARK_DELETE + "--系统判定E环节超时,自动超时通过"));
                    logger.info("E 环节有要考虑的 key:{} result:{}",keyTimeOutE,result);

                    if (redisService.hasRedisKey(ConstantAll.REDIS_PRESCRIPTION_ORDER + result.left)) {
                        //通过缓存获取处方及订单信息
                        MedicalPrescription recordNewPrescription = JsonUtils.fromJson(redisService.getValueRedisByKey(ConstantAll.REDIS_PRESCRIPTION_ORDER + result.left), new TypeReference<MedicalPrescription>() {
                        });
                        if (recordNewPrescription == null) {
                            logger.info("处方信息在E环节消失掉了,很奇怪E", new Exception());
                        } else {
                            redisService.deleteRedisByKey(ConstantAll.REDIS_PRESCRIPTION_ORDER + result.left);//通过要去掉订单处理
                            redisLogService.insertSelective(new RedisLog(ConstantAll.LOG_MEDICAL_PRES, ConstantAll.LOG_MEDICAL_PRES_SUB_CURRENT_PRESCRIPTION, ConstantAll.REDIS_PRESCRIPTION_ORDER + result.left, "弱化对应的内容", 0L, ConstantAll.STRING_EMPTY, ConstantAll.LOG_REMARK_DELETE + "--E环节超时通过"));

                            List<String> listPushUserIdOld = Lists.newArrayList();
                            listPushUserIdOld.add(toolShareService.getAppInfoByTypeId(ConstantAll.TYPE_ID_PHARMACIST) + result.right);
                            appPushService.pushListUserIdApp(ConstantAll.TYPE_ID_PHARMACIST, "处方审核 E 环节超时," + ConstantAll.VALUE_TIME_OUT_CHECK_E + "秒您竟然还没审核完成", "将[超时审核通过]", listPushUserIdOld);

                            Date nowDate = new Date();
                            MedicalPrescription medicalPrescription = new MedicalPrescription();
                            medicalPrescription.setId(result.left);
                            medicalPrescription.setGmtModified(nowDate);
                            medicalPrescription.setRemarks("E环节超时,超时通过审方");
                            medicalPrescription.setViewInfo("超时通过审方");
                            medicalPrescription.setViewTime(nowDate);
                            medicalPrescription.setViewStatus(ConstantAll.CHECK_VIEW_STATUS_OK_TIMEOUT);
                            medicalPrescription.setPrescriptionStatus(ConstantAll.MEDICAL_PRESCRIPTION_STATUS_OK);
                            medicalPrescription.setStatusDetail(ConstantAll.MEDICAL_PRESCRIPTION_STATUS_PATIENT_NO_ORDER);//未支付
                            this.updateByPrimaryKeySelective(medicalPrescription);

                            MedicalOrder medicalOrder = new MedicalOrder();
                            medicalOrder.setId(recordNewPrescription.getMedicalOrderId());
                            medicalOrder.setStatus(ConstantAll.ORDER_STATUS_OK);//患者 完成
                            medicalOrder.setStatusDetail(ConstantAll.ORDER_STATUS_OK);//患者 完成
                            medicalOrder.setOverTime(nowDate);
                            medicalOrder.setOverType(ConstantAll.ORDER_END_TYPE_OK_TIME_OUT_E);
                            medicalOrder.setRemarks("超时通过审方,门诊完成");
                            medicalOrder.setGmtModified(new Date());
                            this.medicalOrderService.updateByPrimaryKeySelective(medicalOrder);

                            considerToTakeSendOrder(result.left,"--E环节超时审方导致的通过处方 整体完成门诊订单，");//考虑取药存药订单计时处理
                        }
                    }
                    else{
                         logger.info("处方信息消失掉了,环节阶段E", new Exception());
                    }
                }

            });
        }
    }



    @ApiOperation(value = "抓单3分钟内并没有处理,需要强制给平台人员处理")
    @Transactional
    public void doWithPharmacistExtent3Minute(Set<String> stringSet,Date currentDate) {
        if(stringSet.size()>0) {//存在要考虑的处方
            Set<String> pharmacistGrapSet = redisService.getRedisKeysByPattern(ConstantAll.REDIS_PHARMACIST_GRAB_ORDER+"*");//查看redis 有处方新订单列表
            pharmacistGrapSet.forEach(grapKey->{
                MutableTriple<Long, Date, Long> result = JsonUtils.fromJson(redisService.getValueRedisByKey(grapKey,false), new TypeReference<MutableTriple<Long, Date, Long>>() {
                });
                logger.info(">>如果redis 有处方新订单列表 并且有药师锁定");//REDIS_PHARMACIST_GRAB_ORDER
                if(currentDate.getTime() - result.middle.getTime() > ConstantAll.VALUE_CHECK_GRAP*ConstantAll.VALUE_MICROSECOND_1000){
                    logger.info(">>如果该药师还存在 说明在规定的"+ConstantAll.VALUE_CHECK_GRAP+"秒内没有核定，记录惩罚表，移交并分配给平台药师列表 t_reward_punishment_log");

                    redisService.deleteRedisByKey(grapKey,false);//去除对应的抓单锁定
                    List<String> listPushUserIdOld = Lists.newArrayList();
                    listPushUserIdOld.add(toolShareService.getAppInfoByTypeId(ConstantAll.TYPE_ID_PHARMACIST)+result.right);
                    appPushService.pushListUserIdApp(ConstantAll.TYPE_ID_PHARMACIST,"处方审核时间"+ConstantAll.VALUE_CHECK_GRAP+"秒您竟然还没有审核完成","将有其他平台人员要是进行审核",listPushUserIdOld);


                    List<Pharmacist> pharmacistList = pharmacistService.getListAllPlat();
                    int totalPlat = pharmacistList.size();
                    if(totalPlat>0){
                        List<String> listPushUserId = Lists.newArrayList();
                        Pharmacist platPharmacist = pharmacistList.get(0);
                        //更新处方信息
                        MedicalPrescription medicalPrescription = new MedicalPrescription();
                        medicalPrescription.setId(result.left);
                        medicalPrescription.setPharmacistId(platPharmacist.getId());
                        medicalPrescription.setPharmacistName(platPharmacist.getTrueName());
                        medicalPrescription.setGmtModified(currentDate);
                        medicalPrescription.setRemarks("跟换平台药师加速处理,为:"+platPharmacist.getTrueName()+" 之前为:"+result.right);
                        this.updateByPrimaryKeySelective(medicalPrescription);
                        //todo: 惩罚药师,没有完成处方审核
                        //设定抓期时间
                        String keyPlat = ConstantAll.REDIS_PHARMACIST_PLAT+platPharmacist.getId()+ConstantAll.REDIS_PHARMACIST_GRAB_ORDER_PART_ORDERID+result.left;
                        MutableTriple<Long, Date, Long> mutableTriple = new MutableTriple<Long, Date, Long>();
                        mutableTriple.left = result.left;
                        mutableTriple.middle = currentDate;
                        mutableTriple.right = platPharmacist.getId();
                        redisService.setJsonObjectBy(keyPlat,mutableTriple,ConstantAll.VALUE_CONSIDER_DAY_TIME,ConstantAll.UNIT_CONSIDER);//考虑一整天
                        redisLogService.insertSelective(new RedisLog(ConstantAll.LOG_MEDICAL_PRES, keyPlat, keyPlat, JsonUtils.toJsonString(mutableTriple), ConstantAll.VALUE_CONSIDER_DAY_TIME, ConstantAll.UNIT_CONSIDER.toString(), ConstantAll.LOG_REMARK_SET + "--系统判定ggg"));

                        //推送给新的平台
                        listPushUserId.add(toolShareService.getAppInfoByTypeId(ConstantAll.TYPE_ID_PHARMACIST)+platPharmacist.getId());
                        appPushService.pushListUserIdApp(ConstantAll.TYPE_ID_PHARMACIST,"处方审核时间达到" +ConstantAll.VALUE_CHECK_GRAP,"转向您这个平台人员要是进行审核",listPushUserId);
                    }
                }



            });
        }
    }


    @ApiOperation(value = "A秒内并没有处理,需要强制给平台人员处理")
    @Transactional
    public void doWithDecisionExtent30Minute(Set<String> stringSet,Date currentDate) {
        if (stringSet.size() > 0) {//存在要考虑的处方
            logger.info(">>如果redis 有处方新订单列表 或配置强硬要 考虑 才浪费去DB查询");
            logger.info(">>用于定期查看订单 条件status=0 prescription_status= now()-gmt_create>30 view_time is null");
            logger.info(">>平台药师列表中，分配个给处方，推送信息给药师；比较如果pharmacist_id 已经一直，相当于反复提醒，没有尽快处理");

            if (stringSet.size() > 0) {//存在要考虑的处方
                logger.info(">>如果redis 有处方新订单列表 或配置强硬要 考虑 才浪费去DB查询");
                logger.info(">>用于定期查看订单 条件status=0 prescription_status= now()-gmt_create>30 view_time is null");
                logger.info(">>平台药师列表中，分配个给处方，推送信息给药师；比较如果pharmacist_id 已经一直，相当于反复提醒，没有尽快处理");

                MedicalPrescriptionRequest medicalPrescriptionRequest = new MedicalPrescriptionRequest();
                medicalPrescriptionRequest.setStatus(ConstantAll.STATUS_0);
                medicalPrescriptionRequest.setViewTimeNull("ViewTimeIsNull");
                medicalPrescriptionRequest.setLimitTime(ConstantAll.VALUE_CHECK_LIMIT_TIME);//目前考虑30分钟内需要处理完毕
                //以后可能要考虑处方订单状态 medicalPrescriptionRequest.setPrescriptionStatus(ConstantAll.STATUS_0);
                List<MedicalPrescription> medicalPrescriptionList = this.listMedicalPrescription(medicalPrescriptionRequest);
                medicalPrescriptionList.forEach(prescription -> {

                    List<Pharmacist> pharmacistList = pharmacistService.getListAllPlat();
                    int totalPlat = pharmacistList.size();
                    if (totalPlat > 0) {
                        List<String> listPushUserId = Lists.newArrayList();
                        Pharmacist platPharmacist = pharmacistList.get(0);
                        //更新处方信息
                        prescription.setPharmacistId(platPharmacist.getId());
                        prescription.setPharmacistName(platPharmacist.getTrueName());
                        prescription.setGmtModified(currentDate);
                        prescription.setRemarks("跟换平台药师加速处理,为:" + platPharmacist.getTrueName() + " 之前为:" + prescription.getPharmacistName());
                        this.updateByPrimaryKeySelective(prescription);
                        //设定抓期时间
                        String keyPlat = ConstantAll.REDIS_PHARMACIST_PLAT + platPharmacist.getId() + ConstantAll.REDIS_PHARMACIST_GRAB_ORDER_PART_ORDERID + prescription.getId();
                        MutableTriple<Long, Date, Long> mutableTriple = new MutableTriple<Long, Date, Long>();
                        mutableTriple.left = prescription.getId();
                        mutableTriple.middle = currentDate;
                        mutableTriple.right = platPharmacist.getId();
                        redisService.setJsonObjectBy(keyPlat, mutableTriple, ConstantAll.VALUE_CONSIDER_DAY_TIME, ConstantAll.UNIT_CONSIDER);//考虑一整天
                        redisLogService.insertSelective(new RedisLog(ConstantAll.LOG_MEDICAL_PRES, keyPlat, keyPlat, JsonUtils.toJsonString(mutableTriple), ConstantAll.VALUE_CONSIDER_DAY_TIME, ConstantAll.UNIT_CONSIDER.toString(), ConstantAll.LOG_REMARK_SET + "--系统判定ggg"));

                        //推送给新的平台
                        listPushUserId.add(toolShareService.getAppInfoByTypeId(ConstantAll.TYPE_ID_PHARMACIST) + platPharmacist.getId());
                        appPushService.pushListUserIdApp(ConstantAll.TYPE_ID_DOCTOR, "处方产生到审核时间超期", "将有您这个平台人员要进行审核", listPushUserId);
                    }
                    Long pharmacistId = prescription.getPharmacistId();
                    if (pharmacistId != null) {
                        redisService.deleteRedisByKey(ConstantAll.REDIS_PHARMACIST_GRAB_ORDER + pharmacistId + ConstantAll.REDIS_PHARMACIST_GRAB_ORDER_PART_ORDERID + prescription.getId());//去除对应的抓单锁定
                        List<String> listPushUserId = Lists.newArrayList();
                        listPushUserId.add(toolShareService.getAppInfoByTypeId(ConstantAll.TYPE_ID_PHARMACIST) + pharmacistId);
                        appPushService.pushListUserIdApp(ConstantAll.TYPE_ID_DOCTOR, "处方产生到审核时间超期", "将有其他平台人员进行审核", listPushUserId);
                    }


                });
            }

        }
    }

    public MedicalPrescriptionPo getMedicalPrescriptionPoInfoById(MedicalPrescriptionRequest params){
        MedicalPrescriptionPo medicalPrescriptionPo = this.getMedicalPrescriptionPoById(params);
        if(medicalPrescriptionPo!=null && medicalPrescriptionPo.getMedicalOrder()!=null){
            if(StringUtils.isNotBlank(medicalPrescriptionPo.getMedicalOrder().getMedicalPictures())){
                medicalPrescriptionPo.getMedicalOrder().setMedicalPicturesList(Arrays.asList(medicalPrescriptionPo.getMedicalOrder().getMedicalPictures().split(",")));
            }
            else{
                medicalPrescriptionPo.getMedicalOrder().setMedicalPicturesList(Lists.newArrayList());
            }
        }
        return medicalPrescriptionPo;
    }
    // 根据条件查询处方带有药品的列表
    public List<MedicalPrescriptionPo> listMedicalPrescriptionPoConsiderDoctorPo(MedicalPrescriptionRequest params){
        List<MedicalPrescriptionPo> medicalPrescriptionPoList = this.listMedicalPrescriptionPo(params);
        List<Long> doctorIdList = Lists.newArrayList();
        medicalPrescriptionPoList.forEach(t->{
//            t.getPatientPo().thinkField();
            StringBuilder sb = new StringBuilder();
            sb.append(Utils.formatDate(t.getMedicalOrder().getPlanTime(),"yyyy-MM-dd"))
                    .append(" ").append(Utils.getWeekInfo(t.getMedicalOrder().getPlanTime())).append(" ").append(t.getMedicalOrder().getWorkTime());
            t.getMedicalOrder().setAppointmentInfo(sb.toString());
            if(StringUtils.isNotBlank(t.getMedicalOrder().getMedicalPictures())){
                t.getMedicalOrder().setMedicalPicturesList(Arrays.asList(t.getMedicalOrder().getMedicalPictures().split(",")));
            }
            else{
                t.getMedicalOrder().setMedicalPicturesList(Lists.newArrayList());
            }
            doctorIdList.add(t.getDoctorId());
        });
        if (params.getShowDoctorPo()!=null){
            DoctorRequest doctorRequest = new DoctorRequest();
            doctorRequest.setDoctorIdList(doctorIdList);
            List<DoctorPo>  doctorPoList = this.doctorService.listDoctorHospitalPo(doctorRequest);
            medicalPrescriptionPoList.forEach(t->{
                t.getMedicalOrder().setDoctorPo(doctorPoList.stream().filter(doctor->doctor.getId().longValue()==t.getDoctorId().longValue()).findFirst().orElse(null));
            });
            logger.info("medicalPrescriptionPoList:{}",medicalPrescriptionPoList);
        }
        return medicalPrescriptionPoList;
    }
    // 根据条件查询处方带有药品的列表
    public List<MedicalPrescriptionPo> listMedicalPrescriptionPo(MedicalPrescriptionRequest params){
        return daoMedicalPrescriptionMapper.listMedicalPrescriptionPo(params);
    }
    // 根据条件查询处方带有药品的列表 总数
    public int listMedicalPrescriptionPoCount(MedicalPrescriptionRequest params){
        return daoMedicalPrescriptionMapper.listMedicalPrescriptionPoCount(params);
    }
    // 根据条件查询列表
    public List<MedicalPrescription> listMedicalPrescription(MedicalPrescriptionRequest params){
        return daoMedicalPrescriptionMapper.listMedicalPrescription(params);
    }
    // 根据条件查询列表 总数
    public int listMedicalPrescriptionCount(MedicalPrescriptionRequest params){
        return daoMedicalPrescriptionMapper.listMedicalPrescriptionCount(params);
    }
    //可能批量更新
    public int updateBatchByCondition(MedicalPrescription recordParams){
        return daoMedicalPrescriptionMapper.updateBatchByCondition(recordParams);
    }
    public int insertSelective(MedicalPrescription record){
        return daoMedicalPrescriptionMapper.insertSelective(record);
    }

    public MedicalPrescription selectByPrimaryKey(Long id){
        return daoMedicalPrescriptionMapper.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(MedicalPrescription record){
        return daoMedicalPrescriptionMapper.updateByPrimaryKeySelective(record);
    }
    public MedicalPrescriptionPo getMedicalPrescriptionPoById(MedicalPrescriptionRequest params){
        return daoMedicalPrescriptionMapper.getMedicalPrescriptionPoById(params);
    }
}
