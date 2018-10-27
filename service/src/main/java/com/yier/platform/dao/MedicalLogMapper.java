package com.yier.platform.dao;

import com.yier.platform.common.model.MedicalLog;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicalLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MedicalLog record);

    int insertSelective(MedicalLog record);

    MedicalLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MedicalLog record);

    int updateByPrimaryKey(MedicalLog record);
}