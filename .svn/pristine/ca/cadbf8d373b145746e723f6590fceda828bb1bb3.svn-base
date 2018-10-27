package com.yier.platform.dao;

import com.yier.platform.common.model.DrugAcceptOrder;
import com.yier.platform.common.model.DrugAcceptOrderPo;
import com.yier.platform.common.model.DrugSendOrder;
import com.yier.platform.common.model.DrugSendOrderPo;
import com.yier.platform.common.requestParam.DrugSendOrderRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DrugAcceptOrderMapper {
    // 根据条件查询送药的列表
    List<DrugAcceptOrderPo> listDrugAcceptOrderPo(DrugSendOrderRequest params);
    // 根据条件查询送药的列表 总数
    int listDrugAcceptOrderPoCount(DrugSendOrderRequest params);

    DrugAcceptOrderPo getDrugAcceptOrderPoById(DrugSendOrderRequest params);
    // 根据条件查询列表
    List<DrugAcceptOrder> listDrugAcceptOrder(DrugSendOrderRequest params);
    // 根据条件查询列表 总数
    int listDrugAcceptOrderCount(DrugSendOrderRequest params);
    //可能批量更新
    int updateBatchByCondition(DrugAcceptOrder record);

    int deleteByPrimaryKey(Long id);

    int insert(DrugAcceptOrder record);

    int insertSelective(DrugAcceptOrder record);

    DrugAcceptOrder selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DrugAcceptOrder record);

    int updateByPrimaryKey(DrugAcceptOrder record);
}