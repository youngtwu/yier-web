package com.yier.platform.dao;

import com.yier.platform.common.model.DeviceInfoException;
import org.springframework.stereotype.Repository;

/**
 * 设备异常信息
 */
@Repository
public interface DeviceInfoExceptionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DeviceInfoException record);

    int insertSelective(DeviceInfoException record);

    DeviceInfoException selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DeviceInfoException record);

    int updateByPrimaryKey(DeviceInfoException record);
}