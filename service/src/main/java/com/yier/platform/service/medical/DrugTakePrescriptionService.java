package com.yier.platform.service.medical;

import com.yier.platform.common.model.DrugTakePrescription;
import com.yier.platform.common.requestParam.DrugTakeOrderRequest;
import com.yier.platform.dao.DrugSendOrderMapper;
import com.yier.platform.dao.DrugTakePrescriptionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DrugTakePrescriptionService {
    private final Logger logger = LoggerFactory.getLogger(DrugSendOrderService.class);
    @Autowired
    private DrugTakePrescriptionMapper daoDrugTakePrescriptionMapper;

    public DrugTakePrescription getDrugTakePrescriptionById(DrugTakeOrderRequest params){
        return daoDrugTakePrescriptionMapper.getDrugTakePrescriptionById(params);
    }
    // 根据条件查询列表
    public List<DrugTakePrescription> listDrugTakePrescription(DrugTakeOrderRequest params){
        return daoDrugTakePrescriptionMapper.listDrugTakePrescription(params);
    }
    // 根据条件查询列表 总数
    public int listDrugTakePrescriptionCount(DrugTakeOrderRequest params){
        return daoDrugTakePrescriptionMapper.listDrugTakePrescriptionCount(params);
    }
    //可能批量更新
    public int updateBatchByCondition(DrugTakePrescription record){
        return daoDrugTakePrescriptionMapper.updateBatchByCondition(record);
    }
    public int deleteByPrimaryKey(Long id){
        return daoDrugTakePrescriptionMapper.deleteByPrimaryKey(id);
    }

    public int insertSelective(DrugTakePrescription record){
        return daoDrugTakePrescriptionMapper.insertSelective(record);
    }

    public DrugTakePrescription selectByPrimaryKey(Long id){
        return daoDrugTakePrescriptionMapper.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(DrugTakePrescription record){
        return daoDrugTakePrescriptionMapper.updateByPrimaryKeySelective(record);
    }
}
