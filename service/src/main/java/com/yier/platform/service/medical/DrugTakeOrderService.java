package com.yier.platform.service.medical;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.yingzhuo.carnival.id.IdGenerator;
import com.google.common.collect.Lists;
import com.yier.platform.common.costant.ConstantAll;
import com.yier.platform.common.model.*;
import com.yier.platform.common.requestParam.DrugSendOrderRequest;
import com.yier.platform.common.requestParam.DrugTakeOrderRequest;
import com.yier.platform.common.requestParam.MedicalOrderRequest;
import com.yier.platform.common.requestParam.MedicalPrescriptionRequest;
import com.yier.platform.common.util.JsonUtils;
import com.yier.platform.common.util.Utils;
import com.yier.platform.dao.DrugSendOrderMapper;
import com.yier.platform.dao.DrugTakeOrderMapper;
import com.yier.platform.service.PharmacyService;
import com.yier.platform.service.RedisLogService;
import com.yier.platform.service.RedisService;
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
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class DrugTakeOrderService {
    private final Logger logger = LoggerFactory.getLogger(DrugSendOrderService.class);
    @Autowired
    private DrugTakeOrderMapper daoDrugTakeOrderMapper;
    @Autowired
    private IdGenerator<Long> idGenerator;
    @Autowired
    private MedicalPrescriptionService medicalPrescriptionService;
    @Autowired
    private DrugTakePrescriptionService drugTakePrescriptionService;
    @Autowired
    private PharmacyService pharmacyService;
    @Autowired
    private MedicalOrderService medicalOrderService;
    @Autowired
    private OrderDrugsService orderDrugsService;
    @Autowired
    private RemoteService remoteService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private RedisLogService redisLogService;//缓存log记录情况

    @ApiOperation(value = "跟当前时间相比,取药单已经过期的列表")
    @Transactional
    public void taskDrugTakeOrderPlanTimeDue(Date planTimeDue){
        DrugTakeOrderRequest params = new DrugTakeOrderRequest();
        params.setStatus(ConstantAll.STATUS_0);
        params.setPlanTimeDue(planTimeDue);
        List<String> statusList = Lists.newArrayList();
        statusList.add(ConstantAll.TAKE_ORDER_STATUS_APPPOINT_OK);
        params.setStatusList(statusList);
        List<DrugTakeOrder> listDrugTakeOrder = this.listDrugTakeOrder(params);
        logger.info("跟当前时间相比,取药单已经过期的列表是:{}",listDrugTakeOrder);
        listDrugTakeOrder.forEach(drugTakeOrder -> {
            drugTakeOrder.setOverType("预约超期");
            drugTakeOrder.setOverTime(planTimeDue);
            drugTakeOrder.setRemarks("预约超期");
            drugTakeOrder.setGmtModified(planTimeDue);
            drugTakeOrder.setTakeStatus(ConstantAll.TAKE_ORDER_STATUS_APPPOINT_PATIENT_PAST_DUE);
            this.updateByPrimaryKeySelective(drugTakeOrder);
        });
    }



    @ApiOperation(value = "创建患者取药OK 确认取药完成")
    @Transactional
    public void drugTakeOrderOk(DrugTakeOrder record){
        Assert.isTrue(record.getId() != null , "请传递参数取药订单 id");
        DrugTakeOrder currentDrugTakeOrder = this.selectByPrimaryKey(record.getId());
        Assert.isTrue(currentDrugTakeOrder!=null && StringUtils.equalsIgnoreCase(currentDrugTakeOrder.getTakeStatus(),ConstantAll.TAKE_ORDER_STATUS_APPPOINT_OK),"只有[预约成功]的订单方可操作! 目前状态是:"+ currentDrugTakeOrder.getTakeStatus());

        Date currentDate = new Date();
        record.setGmtModified(currentDate);
        record.setOverType("患者确认取药完成");
        record.setOverTime(currentDate);
        record.setRemarks("患者确认取药完成");
        record.setTakeStatus(ConstantAll.TAKE_ORDER_STATUS_OK);
        this.updateByPrimaryKeySelective(record);

        List<Long> medicalPrescriptionIdList = Arrays.asList(currentDrugTakeOrder.getMedicalPrescriptionId().split(",")).stream().map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
        this.medicalPrescriptionService.updateBatchByCondition(ConstantAll.MEDICAL_PRESCRIPTION_STATUS_PATIENT_GET_DRUG, "取药药品患者确认完成",null,medicalPrescriptionIdList,currentDate);//批量更新处方列表状态及描述
    }

    @ApiOperation(value = "创建患者取药订单")
    @Transactional
    public void drugTakeOrderCancel(DrugTakeOrder record){
        Assert.isTrue(record.getId() != null , "请传递参数取药订单 id");
        DrugTakeOrder currentDrugTakeOrder = this.selectByPrimaryKey(record.getId());
        Assert.isTrue(currentDrugTakeOrder!=null && StringUtils.equalsIgnoreCase(currentDrugTakeOrder.getTakeStatus(),ConstantAll.TAKE_ORDER_STATUS_APPPOINT),"只有[处方待支付]的订单方可操作! 目前状态是:"+ currentDrugTakeOrder.getTakeStatus());

        Date currentDate = new Date();
        DrugTakePrescription drugTakePrescription = new DrugTakePrescription();
        drugTakePrescription.setTakeOrderNo(record.getTakeOrderNo());
        drugTakePrescription.setGmtModified(currentDate);
        drugTakePrescription.setRemarks("取消支付取药订单");
        drugTakePrescription.setStatus(ConstantAll.STATUS_1);//1表示删除，取消对应的操作
        this.drugTakePrescriptionService.updateBatchByCondition(drugTakePrescription);

        record.setGmtModified(currentDate);
        record.setRemarks("取药取消支付");
        record.setTakeStatus(ConstantAll.TAKE_ORDER_STATUS_PATIENT_PAYMENT_CANCEL);
        this.updateByPrimaryKeySelective(record);

        List<Long> medicalPrescriptionIdList = Arrays.asList(currentDrugTakeOrder.getMedicalPrescriptionId().split(",")).stream().map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
        this.medicalPrescriptionService.updateKeyTimeOutTakeOrSendOrder(medicalPrescriptionIdList,ConstantAll.TAKE_SEND_PRESCRIPTION_LOCK_CANCEL_ORDER,"取药取消支付,多个处方合并情况:"+ medicalPrescriptionIdList," 取药取消支付",currentDate);

    }
    @ApiOperation(value = "患者取药订单支付成功")
    @Transactional
    public void drugTakeOrderPaymentOk(DrugTakeOrder record){
        Assert.isTrue(record.getId() != null , "请传递参数取药订单 id");
        DrugTakeOrder currentDrugTakeOrder = this.selectByPrimaryKey(record.getId());
        Assert.isTrue(currentDrugTakeOrder!=null && StringUtils.equalsIgnoreCase(currentDrugTakeOrder.getTakeStatus(),ConstantAll.TAKE_ORDER_STATUS_APPPOINT),"只有[处方待支付]的订单方可操作! 目前状态是:"+ currentDrugTakeOrder.getTakeStatus());

        Date currentDate = new Date();
        record.setGmtModified(currentDate);
        record.setRemarks("取药付款成功");
        record.setTakeStatus(ConstantAll.TAKE_ORDER_STATUS_APPPOINT_OK);
        this.updateByPrimaryKeySelective(record);

        List<Long> medicalPrescriptionIdList = Arrays.asList(currentDrugTakeOrder.getMedicalPrescriptionId().split(",")).stream().map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
        this.medicalPrescriptionService.updateKeyTimeOutTakeOrSendOrder(medicalPrescriptionIdList,ConstantAll.TAKE_SEND_PRESCRIPTION_LOCK_PAYMENT_ORDER,"取药付款成功,多个处方合并情况:"+ medicalPrescriptionIdList," 取药付款成功",currentDate);
    }
    @ApiOperation(value = "创建患者取药订单")
    @Transactional
    public DrugTakeOrder drugTakeOrderCreate(DrugTakeOrder record){
        Assert.isTrue(record.getPrescriptionIdList()!=null,"请传递药品列表");
        Assert.isTrue(record.getPharmacyId()!=null,"请传递pharmacyId药库Id");
        Assert.isTrue(record.getPlanTime()!=null,"请传递planTime取药日期");
        String pharmacyId = record.getPharmacyId();
        logger.info("目前创建取药订单信息:{}",record);
        MedicalPrescriptionRequest params = new MedicalPrescriptionRequest();
        params.setPrescriptionIdList(record.getPrescriptionIdList());
        List<MedicalPrescriptionPo> medicalPrescriptionPoList =  medicalPrescriptionService.listMedicalPrescriptionPo(params);
        List<Pharmacy> listAllPharmacy  = pharmacyService.getListAllPharmacy();
        Pharmacy pharmacy = listAllPharmacy.stream().filter(t->t.getId().toString().equalsIgnoreCase(pharmacyId)).findFirst().orElse(null);
        Assert.isTrue(pharmacy!=null,"您选择的药房目前并不存在!");
        List<Integer> workFrom = Arrays.asList(pharmacy.getWorkFrom().split(":")).stream().map(s -> Integer.parseInt(s.trim())).collect(Collectors.toList());
        Assert.isTrue(workFrom.size()==3,"您选择的药房"+pharmacy.getName()+"目前开始时间配置无效!"+ pharmacy.getWorkFrom());
        List<Integer> workTo = Arrays.asList(pharmacy.getWorkTo().split(":")).stream().map(s -> Integer.parseInt(s.trim())).collect(Collectors.toList());
        Assert.isTrue(workFrom.size()==3,"您选择的药房"+pharmacy.getName()+"目前结束时间配置无效!"+ pharmacy.getWorkTo());
        record.setWorkStartTime(Utils.getDayTimeHHmmss(record.getPlanTime(),workFrom.get(0),workFrom.get(1),workFrom.get(2)));
        record.setWorkEndTime(Utils.getDayTimeHHmmss(record.getPlanTime(),workTo.get(0),workTo.get(1),workTo.get(2)));
        logger.info("药房信息是：{}",pharmacy);

        //考虑某个处方在某个药库下面，库存有效，同时修正处方对应药品code的价格
        remoteService.considerPrescriptionInPharmacyStockUpdatePrice(medicalPrescriptionPoList, pharmacyId);

        String orderNo = idGenerator.nextId().toString();
        record.setTakeOrderNo(orderNo);

        List<String> orderNoList = Lists.newArrayList();
        List<String> prescriptionIdList = Lists.newArrayList();
        List<Long> medicalPrescriptionIdList = Lists.newArrayList();
        final OrderDrugs orderDrugs =new OrderDrugs();
        orderDrugs.setCostPrice(0);

        medicalPrescriptionPoList.forEach(p->{
            orderNoList.add(p.getOrderNo());
            prescriptionIdList.add(p.getPrescriptionId());
            medicalPrescriptionIdList.add(p.getId());

            final OrderDrugs orderDrugsSmall =new OrderDrugs();//价格小计调整
            orderDrugsSmall.setCostPrice(0);
            p.getDrugList().forEach(d->{
                if(StringUtils.equalsIgnoreCase(d.getStatus(),ConstantAll.STATUS_ADJUST)){
                    OrderDrugs adOrderDrugs = new OrderDrugs();
                    adOrderDrugs.setRemarks(d.getRemarks());
                    adOrderDrugs.setId(d.getId());
                    adOrderDrugs.setCostPrice(d.getCostPrice());
                    adOrderDrugs.setGmtModified(new Date());
                    this.orderDrugsService.updateByPrimaryKeySelective(adOrderDrugs);
                }
                orderDrugsSmall.setCostPrice(d.getCostPrice().intValue()*d.getDrugCount().intValue()+orderDrugsSmall.getCostPrice());
            });
            orderDrugs.setCostPrice(orderDrugsSmall.getCostPrice().intValue()+orderDrugs.getCostPrice());
            MedicalPrescription medicalPrescription = new MedicalPrescription();
            medicalPrescription.setId(p.getId());
            medicalPrescription.setPharmacyId(pharmacy.getId().toString());
            medicalPrescription.setPharmacyName(pharmacy.getName());
            medicalPrescription.setPharmacyAddress(pharmacy.getAddress());
            medicalPrescription.setPharmacyContact(pharmacy.getContact());
            medicalPrescription.setCost(orderDrugsSmall.getCostPrice());//修改处方总计，
            medicalPrescription.setGmtModified(new Date());
            medicalPrescription.setStatusDetail(ConstantAll.MEDICAL_PRESCRIPTION_STATUS_PATIENT_NEW_ORDER);//无论如何都要修改状态
            medicalPrescription.setRemarks("患者预约取药单，等待支付 目前处方价格或药库有调整情况："+p.getStatus()+" 目前总药房价格:"+medicalPrescription.getCost());
            //medicalPrescription.setPrescriptionIdList(record.getPrescriptionIdList());
            this.medicalPrescriptionService.updateByPrimaryKeySelective(medicalPrescription);

            DrugTakePrescription drugTakePrescription = new DrugTakePrescription();
            drugTakePrescription.setPrescriptionId(p.getPrescriptionId());
            drugTakePrescription.setTakeOrderNo(orderNo);
            drugTakePrescription.setMedicalOrderId(p.getMedicalOrderId());
            drugTakePrescription.setMedicalPrescriptionId(p.getId());
            drugTakePrescription.setPharmacyId(pharmacy.getId().toString());
            drugTakePrescription.setPharmacyName(pharmacy.getName());
            drugTakePrescription.setPharmacyAddress(pharmacy.getAddress());
            drugTakePrescription.setPharmacyContact(pharmacy.getContact());
            drugTakePrescription.setRemarks("创建取药订单与处方的关系");
            drugTakePrescriptionService.insertSelective(drugTakePrescription);

            record.setPatientId(p.getPatientId());
            record.setPatientAddress(p.getPatientAddress());
            record.setPatientContact(p.getPatientContact());
            record.setPatientIdCardNo(p.getPatientIdCardNo());
            record.setPatientName(p.getPatientName());
            record.setPatientSex(p.getPatientSex());
            record.setPatientYearOld(p.getPatientYearOld());

        });

        record.setOrderNo(StringUtils.join(orderNoList.toArray(),","));
        record.setPrescriptionId(StringUtils.join(prescriptionIdList.toArray(),","));
        record.setMedicalPrescriptionId(StringUtils.join(medicalPrescriptionIdList.toArray(),",")+",");
        record.setPharmacyId(pharmacy.getId().toString());
        record.setPharmacyName(pharmacy.getName());
        record.setPharmacyAddress(pharmacy.getAddress());
        record.setPharmacyContact(pharmacy.getContact());
        record.setPayment(orderDrugs.getCostPrice());
        record.setRemind(""+ConstantAll.VALUE_PAYMENT_LOCK);
        record.setTakeStatus(ConstantAll.TAKE_ORDER_STATUS_APPPOINT);
        record.setRemarks("取药订单成功");
        Date currentDate = new Date();
        record.setGmtModified(currentDate);
        this.insertSelective(record);

        this.medicalPrescriptionService.updateKeyTimeOutTakeOrSendOrder(medicalPrescriptionIdList,ConstantAll.TAKE_SEND_PRESCRIPTION_LOCK_NEW_ORDER,"取药下单,多个处方合并情况:"+ medicalPrescriptionIdList," 取药下单",currentDate);
        return record;
    }


    // 根据条件查询处方带有药品的列表
    public List<DrugTakeOrderPo> listDrugTakeOrderPo(DrugTakeOrderRequest params){
        return daoDrugTakeOrderMapper.listDrugTakeOrderPo(params);
    }
    // 根据条件查询处方带有药品的列表 总数
    public int listDrugTakeOrderPoCount(DrugTakeOrderRequest params){
        return daoDrugTakeOrderMapper.listDrugTakeOrderPoCount(params);
    }

    public DrugTakeOrderPo getDrugTakeOrderPoById(DrugTakeOrderRequest params){
        return daoDrugTakeOrderMapper.getDrugTakeOrderPoById(params);
    }
    // 根据条件查询列表
    public List<DrugTakeOrder> listDrugTakeOrder(DrugTakeOrderRequest params){
        return daoDrugTakeOrderMapper.listDrugTakeOrder(params);
    }
    // 根据条件查询列表 总数
    public int listDrugTakeOrderCount(DrugTakeOrderRequest params){
        return daoDrugTakeOrderMapper.listDrugTakeOrderCount(params);
    }
    //可能批量更新
    public int updateBatchByCondition(DrugTakeOrder recordParams){
        return daoDrugTakeOrderMapper.updateBatchByCondition(recordParams);
    }

    public int deleteByPrimaryKey(Long id){
        return daoDrugTakeOrderMapper.deleteByPrimaryKey(id);
    }

    public int insertSelective(DrugTakeOrder record){
        return daoDrugTakeOrderMapper.insertSelective(record);
    }

    public DrugTakeOrder selectByPrimaryKey(Long id){
        return daoDrugTakeOrderMapper.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(DrugTakeOrder record){
        return daoDrugTakeOrderMapper.updateByPrimaryKeySelective(record);
    }

}
