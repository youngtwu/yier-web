package com.yier.platform.dao;

import com.yier.platform.common.model.DrugTakeOrder;
import com.yier.platform.common.model.DrugTakeOrder;
import com.yier.platform.common.model.DrugTakeOrderPo;
import com.yier.platform.common.requestParam.DrugTakeOrderRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DrugTakeOrderMapper {
    // 根据条件查询处方带有药品的列表
    List<DrugTakeOrderPo> listDrugTakeOrderPo(DrugTakeOrderRequest params);
    // 根据条件查询处方带有药品的列表 总数
    int listDrugTakeOrderPoCount(DrugTakeOrderRequest params);

    DrugTakeOrderPo getDrugTakeOrderPoById(DrugTakeOrderRequest params);
    // 根据条件查询列表
    List<DrugTakeOrder> listDrugTakeOrder(DrugTakeOrderRequest params);
    // 根据条件查询列表 总数
    int listDrugTakeOrderCount(DrugTakeOrderRequest params);
    //可能批量更新
    int updateBatchByCondition(DrugTakeOrder record);

    int deleteByPrimaryKey(Long id);

    int insert(DrugTakeOrder record);

    int insertSelective(DrugTakeOrder record);

    DrugTakeOrder selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DrugTakeOrder record);

    int updateByPrimaryKey(DrugTakeOrder record);
}