package com.yier.platform.service.medical;

import com.yier.platform.common.model.OrderDrugs;
import com.yier.platform.common.requestParam.OrderDrugsRequest;
import com.yier.platform.dao.OrderDrugsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 上门问诊订单商品列表 service
 */
@Service
public class OrderDrugsService {
    private final Logger logger = LoggerFactory.getLogger(OrderDrugsService.class);
    @Autowired
    private OrderDrugsMapper daoOrderDrugsMapper;


    public int deleteByRequestKey(OrderDrugsRequest params){
        return daoOrderDrugsMapper.deleteByRequestKey(params);
    }

    public List<OrderDrugs> listOrderDrugs(OrderDrugsRequest params){
        return daoOrderDrugsMapper.listOrderDrugs(params);
    }

    public int listOrderDrugsCount(OrderDrugsRequest params){
        return daoOrderDrugsMapper.listOrderDrugsCount(params);
    }

    public int insertSelective(OrderDrugs record){
        return daoOrderDrugsMapper.insertSelective(record);
    }

    public OrderDrugs selectByPrimaryKey(Long id){
        return daoOrderDrugsMapper.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(OrderDrugs record){
        return daoOrderDrugsMapper.updateByPrimaryKeySelective(record);
    }
}
