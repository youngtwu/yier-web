package com.yier.platform.dao;

import com.yier.platform.common.model.OrderDrugs;
import com.yier.platform.common.requestParam.OrderDrugsRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDrugsMapper {
    // 根据条件删除
    int deleteByRequestKey(OrderDrugsRequest params);
    // 根据条件查询列表
    List<OrderDrugs> listOrderDrugs(OrderDrugsRequest params);
    // 根据条件查询列表 总数
    int listOrderDrugsCount(OrderDrugsRequest params);



    int deleteByPrimaryKey(Long id);

    int insert(OrderDrugs record);

    int insertSelective(OrderDrugs record);

    OrderDrugs selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderDrugs record);

    int updateByPrimaryKey(OrderDrugs record);
}