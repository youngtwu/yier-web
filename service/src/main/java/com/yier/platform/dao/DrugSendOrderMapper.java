package com.yier.platform.dao;

import com.yier.platform.common.model.DrugSendOrder;
import com.yier.platform.common.model.DrugSendOrderPo;
import com.yier.platform.common.requestParam.DrugSendOrderRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DrugSendOrderMapper {

    // 根据条件查询送药的列表
    List<DrugSendOrderPo> listDrugSendOrderPo(DrugSendOrderRequest params);
    // 根据条件查询送药的列表 总数
    int listDrugSendOrderPoCount(DrugSendOrderRequest params);

    DrugSendOrderPo getDrugSendOrderPoById(DrugSendOrderRequest params);
    // 根据条件查询列表
    List<DrugSendOrder> listDrugSendOrder(DrugSendOrderRequest params);
    // 根据条件查询列表 总数
    int listDrugSendOrderCount(DrugSendOrderRequest params);
    //可能批量更新
    int updateBatchByCondition(DrugSendOrder record);

    int deleteByPrimaryKey(Long id);

    int insert(DrugSendOrder record);

    int insertSelective(DrugSendOrder record);

    DrugSendOrder selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DrugSendOrder record);

    int updateByPrimaryKey(DrugSendOrder record);
}