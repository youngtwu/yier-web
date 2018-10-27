package com.yier.platform.dao;

import com.yier.platform.common.model.RemindTime;
import org.springframework.stereotype.Repository;

@Repository
public interface RemindTimeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RemindTime record);

    int insertSelective(RemindTime record);

    RemindTime selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RemindTime record);

    int updateByPrimaryKey(RemindTime record);
}