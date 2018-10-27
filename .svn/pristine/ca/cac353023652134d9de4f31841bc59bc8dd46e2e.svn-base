package com.yier.platform.dao;

import com.yier.platform.common.model.HospitalLevel;
import com.yier.platform.common.requestParam.HospitalRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HospitalLevelMapper {
    // 根据条件查询列表
    List<HospitalLevel> listHospitalLevel(HospitalRequest params);
    // 根据条件查询列表 总数
    int listHospitalLevelCount(HospitalRequest params);
    
    int deleteByPrimaryKey(Integer id);

    int insert(HospitalLevel record);

    int insertSelective(HospitalLevel record);

    HospitalLevel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(HospitalLevel record);

    int updateByPrimaryKey(HospitalLevel record);
}