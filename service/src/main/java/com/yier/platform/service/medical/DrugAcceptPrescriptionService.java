package com.yier.platform.service.medical;

import com.yier.platform.common.model.DrugAcceptPrescription;
import com.yier.platform.common.requestParam.DrugSendOrderRequest;
import com.yier.platform.dao.DrugAcceptPrescriptionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 药师接受订单与处方关系 service
 */
@Service
public class DrugAcceptPrescriptionService {
    private final Logger logger = LoggerFactory.getLogger(DrugAcceptPrescriptionService.class);
    @Autowired
    private DrugAcceptPrescriptionMapper daoDrugAcceptPrescriptionMapper;

    public DrugAcceptPrescription getDrugAcceptPrescriptionById(DrugSendOrderRequest params){
        return daoDrugAcceptPrescriptionMapper.getDrugAcceptPrescriptionById(params);
    }
    // 根据条件查询列表
    public List<DrugAcceptPrescription> listDrugAcceptPrescription(DrugSendOrderRequest params){
        return daoDrugAcceptPrescriptionMapper.listDrugAcceptPrescription(params);
    }
    // 根据条件查询列表 总数
    public int listDrugAcceptPrescriptionCount(DrugSendOrderRequest params){
        return daoDrugAcceptPrescriptionMapper.listDrugAcceptPrescriptionCount(params);
    }

    //可能批量更新
    public int updateBatchByCondition(DrugAcceptPrescription record){
        return daoDrugAcceptPrescriptionMapper.updateBatchByCondition(record);
    }


    public int deleteByPrimaryKey(Long id){
        return daoDrugAcceptPrescriptionMapper.deleteByPrimaryKey(id);
    }

    public int insertSelective(DrugAcceptPrescription record){
        return daoDrugAcceptPrescriptionMapper.insertSelective(record);
    }

    public DrugAcceptPrescription selectByPrimaryKey(Long id){
        return daoDrugAcceptPrescriptionMapper.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(DrugAcceptPrescription record){
        return daoDrugAcceptPrescriptionMapper.updateByPrimaryKeySelective(record);
    }
}
