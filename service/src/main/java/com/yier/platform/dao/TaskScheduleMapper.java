package com.yier.platform.dao;

import com.yier.platform.common.model.TaskSchedule;
import com.yier.platform.common.requestParam.TaskScheduleRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskScheduleMapper {
    // 根据条件查询列表
    List<TaskSchedule> listTaskSchedule(TaskScheduleRequest params);
    // 根据条件查询列表 总数
    int listTaskScheduleCount(TaskScheduleRequest params);



    int deleteByPrimaryKey(Long id);

    int insert(TaskSchedule record);

    int insertSelective(TaskSchedule record);

    TaskSchedule selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TaskSchedule record);

    int updateByPrimaryKey(TaskSchedule record);
}