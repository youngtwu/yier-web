package com.yier.platform.dao;

import com.yier.platform.common.model.DrugCall;
import com.yier.platform.common.requestParam.DrugCallRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DrugCallMapper {
    // 根据条件查询列表
    List<DrugCall> listDrugCall(DrugCallRequest params);
    // 根据条件查询列表 总数
    int listDrugCallCount(DrugCallRequest params);



    int deleteByPrimaryKey(Long id);

    int insert(DrugCall record);

    int insertSelective(DrugCall record);

    DrugCall selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DrugCall record);

    int updateByPrimaryKey(DrugCall record);
}