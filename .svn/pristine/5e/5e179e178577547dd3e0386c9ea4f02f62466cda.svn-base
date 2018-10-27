package com.yier.platform.service.medical;

import com.yier.platform.common.costant.ConstantAll;
import com.yier.platform.common.model.DrugAcceptOrder;
import com.yier.platform.common.model.DrugAcceptOrderPo;
import com.yier.platform.common.model.DrugSendOrder;
import com.yier.platform.common.requestParam.DrugSendOrderRequest;
import com.yier.platform.dao.DrugAcceptOrderMapper;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;

/**
 * 药师接受订单 service
 */
@Service
public class DrugAcceptOrderService {
    private final Logger logger = LoggerFactory.getLogger(DrugAcceptOrderService.class);
    @Autowired
    private DrugAcceptOrderMapper daoDrugAcceptOrderMapper;
    @Autowired
    private DrugSendOrderService drugSendOrderService;
    @ApiOperation(value = "药师点击接受成功")
    @Transactional
    public DrugAcceptOrder createAcceptOrderToSendOK(DrugAcceptOrder record){
        Assert.isTrue(record.getId() != null , "请传递参数送药接受订单 id");
        DrugAcceptOrder currentOrder = this.selectByPrimaryKey(record.getId());
        Assert.isTrue(currentOrder!=null && StringUtils.equalsIgnoreCase(currentOrder.getAcceptStatus(),ConstantAll.ACCEPT_ORDER_STATUS_SENDING),"只有[派送中]的订单方可操作! 目前状态是:"+ currentOrder.getAcceptStatus());
        record.setGmtModified(new Date());
        record.setAcceptStatus(ConstantAll.ACCEPT_ORDER_STATUS_SEND_OK);
        this.updateByPrimaryKeySelective(record);

        DrugSendOrderRequest params = new DrugSendOrderRequest();
        params.setSendOrderNo(currentOrder.getSendOrderNo());
        params.setStatus(ConstantAll.STATUS_0);
        List<DrugAcceptOrder> drugAcceptOrderList = this.listDrugAcceptOrder(params);
        if(drugAcceptOrderList.stream().filter(t->!StringUtils.equalsIgnoreCase(t.getAcceptStatus(),ConstantAll.ACCEPT_ORDER_STATUS_SEND_OK)).count() == 0 ){
            DrugSendOrder drugSendOrder = new DrugSendOrder();
            drugSendOrder.setSendStatusChanging(ConstantAll.SEND_ORDER_STATUS_SENDING);
            drugSendOrder.setSendStatus(ConstantAll.SEND_ORDER_STATUS_SEND_OK);
            drugSendOrder.setSendOrderNo(currentOrder.getSendOrderNo());
            drugSendOrder.setRemarks("药师已经完成处方药品送达"+currentOrder.getPrescriptionId()+"目前药师:"+currentOrder.getPharmacyId() +" 名字:"+currentOrder.getPharmacyName());
            this.drugSendOrderService.updateBatchByCondition(drugSendOrder);
        }
        else{
            logger.info("目前还尚有没有确认成功送达的,派单列表:{}",drugAcceptOrderList);
        }
        return record;
    }
    @ApiOperation(value = "药师点击派送")
    @Transactional
    public DrugAcceptOrder createAcceptOrderToSending(DrugAcceptOrder record){
        Assert.isTrue(record.getId() != null , "请传递参数送药接受订单 id");
        DrugAcceptOrder currentOrder = this.selectByPrimaryKey(record.getId());
        Assert.isTrue(currentOrder!=null && StringUtils.equalsIgnoreCase(currentOrder.getAcceptStatus(),ConstantAll.ACCEPT_ORDER_STATUS_READY),"只有[待送]的订单方可操作! 目前状态是:"+ currentOrder.getAcceptStatus());
        record.setGmtModified(new Date());
        record.setAcceptStatus(ConstantAll.ACCEPT_ORDER_STATUS_SENDING);
        this.updateByPrimaryKeySelective(record);
        DrugSendOrder drugSendOrder = new DrugSendOrder();
        drugSendOrder.setSendStatusChanging(ConstantAll.SEND_ORDER_STATUS_PAYMENT);
        drugSendOrder.setSendStatus(ConstantAll.SEND_ORDER_STATUS_SENDING);
        drugSendOrder.setSendOrderNo(currentOrder.getSendOrderNo());
        drugSendOrder.setRemarks("目前药师已经开始派送处方中的"+currentOrder.getPrescriptionId()+"目前药师:"+currentOrder.getPharmacyId() +" 名字:"+currentOrder.getPharmacyName());
        this.drugSendOrderService.updateBatchByCondition(drugSendOrder);


        return record;
    }
    @ApiOperation(value = "创建药师接单")
    @Transactional
    public DrugAcceptOrder createAcceptOrder(DrugAcceptOrder record){
        Assert.isTrue(record.getId() != null , "请传递参数送药接受订单 id");
        Assert.isTrue(record.getPharmacistId() != null , "请传递参数药师ID pharmacistId");
        Assert.isTrue(record.getPharmacistName() != null , "请传递参数药师Name pharmacistName");
        DrugAcceptOrder currentOrder = this.selectByPrimaryKey(record.getId());
        Assert.isTrue(currentOrder!=null && StringUtils.equalsIgnoreCase(currentOrder.getAcceptStatus(),ConstantAll.ACCEPT_ORDER_STATUS_PAYMENT),"只有[未接收]的订单方可操作! 目前接单药师是:"+currentOrder.getPharmacyName()+" 状态是:"+ currentOrder.getAcceptStatus());
        record.setGmtModified(new Date());
        record.setRemarks("药师"+record.getPharmacistId()+" 名字:"+record.getPharmacyName()+"已经接单");
        record.setAcceptStatus(ConstantAll.ACCEPT_ORDER_STATUS_READY);
        this.updateByPrimaryKeySelective(record);
        return record;
    }

    // 根据条件查询送药的列表
    public List<DrugAcceptOrderPo> listDrugAcceptOrderPo(DrugSendOrderRequest params){
        return daoDrugAcceptOrderMapper.listDrugAcceptOrderPo(params);
    }
    // 根据条件查询送药的列表 总数
    public int listDrugAcceptOrderPoCount(DrugSendOrderRequest params){
        return daoDrugAcceptOrderMapper.listDrugAcceptOrderPoCount(params);
    }

    public DrugAcceptOrderPo getDrugAcceptOrderPoById(DrugSendOrderRequest params){
        return daoDrugAcceptOrderMapper.getDrugAcceptOrderPoById(params);
    }
    // 根据条件查询列表
    public List<DrugAcceptOrder> listDrugAcceptOrder(DrugSendOrderRequest params){
        return daoDrugAcceptOrderMapper.listDrugAcceptOrder(params);
    }
    // 根据条件查询列表 总数
    public int listDrugAcceptOrderCount(DrugSendOrderRequest params){
        return daoDrugAcceptOrderMapper.listDrugAcceptOrderCount(params);
    }
    //可能批量更新
    public int updateBatchByCondition(DrugAcceptOrder recordParams){
        return daoDrugAcceptOrderMapper.updateBatchByCondition(recordParams);
    }

    public int deleteByPrimaryKey(Long id){
        return daoDrugAcceptOrderMapper.deleteByPrimaryKey(id);
    }

    public int insertSelective(DrugAcceptOrder record){
        return daoDrugAcceptOrderMapper.insertSelective(record);
    }

    public DrugAcceptOrder selectByPrimaryKey(Long id){
        return daoDrugAcceptOrderMapper.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(DrugAcceptOrder record){
        return daoDrugAcceptOrderMapper.updateByPrimaryKeySelective(record);
    }
}
