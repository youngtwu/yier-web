package com.yier.platform.dao;

import com.yier.platform.common.model.DoctorDisease;
import com.yier.platform.common.requestParam.DiseaseCodeRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorDiseaseMapper {
    // 根据条件查询列表
    List<DoctorDisease> listDoctorDisease(DiseaseCodeRequest params);
    // 根据条件查询列表 总数
    int listDoctorDiseaseCount(DiseaseCodeRequest params);




    int deleteByPrimaryKey(Long id);

    int insert(DoctorDisease record);

    int insertSelective(DoctorDisease record);

    DoctorDisease selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DoctorDisease record);

    int updateByPrimaryKey(DoctorDisease record);
}