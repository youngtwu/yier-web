package com.yier.platform.dao;

import com.yier.platform.common.model.Drug;
import org.springframework.stereotype.Repository;

@Repository
public interface DrugMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Drug record);

    int insertSelective(Drug record);
}