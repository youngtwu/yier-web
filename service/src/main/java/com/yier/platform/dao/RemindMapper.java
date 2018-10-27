package com.yier.platform.dao;

import com.yier.platform.common.model.Remind;
import org.springframework.stereotype.Repository;

@Repository
public interface RemindMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Remind record);

    int insertSelective(Remind record);

    Remind selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Remind record);

    int updateByPrimaryKey(Remind record);
}