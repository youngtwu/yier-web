package com.yier.platform.dao;

import com.yier.platform.common.model.DrugTakeOrder;
import com.yier.platform.common.model.DrugTakeOrderPo;
import com.yier.platform.common.model.DrugTakePrescription;
import com.yier.platform.common.requestParam.DrugTakeOrderRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DrugTakePrescriptionMapper {

    DrugTakePrescription getDrugTakePrescriptionById(DrugTakeOrderRequest params);
    // 根据条件查询列表
    List<DrugTakePrescription> listDrugTakePrescription(DrugTakeOrderRequest params);
    // 根据条件查询列表 总数
    int listDrugTakePrescriptionCount(DrugTakeOrderRequest params);

    //可能批量更新
    int updateBatchByCondition(DrugTakePrescription record);

    int deleteByPrimaryKey(Long id);

    int insert(DrugTakePrescription record);

    int insertSelective(DrugTakePrescription record);

    DrugTakePrescription selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DrugTakePrescription record);

    int updateByPrimaryKey(DrugTakePrescription record);
}