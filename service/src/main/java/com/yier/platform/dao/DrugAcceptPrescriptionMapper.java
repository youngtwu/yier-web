package com.yier.platform.dao;

import com.yier.platform.common.model.DrugAcceptPrescription;
import com.yier.platform.common.requestParam.DrugSendOrderRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DrugAcceptPrescriptionMapper {
    DrugAcceptPrescription getDrugAcceptPrescriptionById(DrugSendOrderRequest params);
    // 根据条件查询列表
    List<DrugAcceptPrescription> listDrugAcceptPrescription(DrugSendOrderRequest params);
    // 根据条件查询列表 总数
    int listDrugAcceptPrescriptionCount(DrugSendOrderRequest params);

    //可能批量更新
    int updateBatchByCondition(DrugAcceptPrescription record);


    int deleteByPrimaryKey(Long id);

    int insert(DrugAcceptPrescription record);

    int insertSelective(DrugAcceptPrescription record);

    DrugAcceptPrescription selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DrugAcceptPrescription record);

    int updateByPrimaryKey(DrugAcceptPrescription record);
}