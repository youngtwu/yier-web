package com.yier.platform.dao;

import com.yier.platform.common.model.DrugCallTask;
import com.yier.platform.common.requestParam.DrugCallRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DrugCallTaskMapper {
    // 根据条件查询列表
    List<DrugCallTask> listDrugCallTask(DrugCallRequest params);
    // 根据条件查询列表 总数
    int listDrugCallTaskCount(DrugCallRequest params);


    int deleteByPrimaryKey(Long id);

    int insert(DrugCallTask record);

    int insertSelective(DrugCallTask record);

    DrugCallTask selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DrugCallTask record);

    int updateByPrimaryKey(DrugCallTask record);
}