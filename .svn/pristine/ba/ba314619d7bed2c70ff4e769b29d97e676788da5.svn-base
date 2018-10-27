package com.yier.platform.dao;

import com.yier.platform.common.model.PracticeWorkTime;
import com.yier.platform.common.requestParam.PracticeRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PracticeWorkTimeMapper {
    // 根据条件查询列表
    List<PracticeWorkTime> listPracticeWorkTime(PracticeRequest params);
    // 根据条件查询列表 总数
    int listPracticeWorkTimeCount(PracticeRequest params);
    // 根据条件批量删除
    int deleteByCondition(PracticeRequest params);








    int deleteByPrimaryKey(Long id);

    int insert(PracticeWorkTime record);

    int insertSelective(PracticeWorkTime record);

    PracticeWorkTime selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PracticeWorkTime record);

    int updateByPrimaryKey(PracticeWorkTime record);
}