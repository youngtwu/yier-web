package com.yier.platform.dao;


import com.yier.platform.common.model.Patient;
import com.yier.platform.common.requestParam.PatientRequest;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 患者信息dao
 */
@Repository
public interface PatientMapper {

    // 根据条件查询列表
    List<Patient> listPatient(PatientRequest params);//@Param("params")//错误无法实现
    // 根据条件查询列表 总数
    int listPatientCount(PatientRequest params);//@Param("params")



    // 根据条件查询列表
    List<Patient> list(Map<String, Object> params);//@Param("params")
    // 根据条件查询列表 总数
    int listCount(Map<String, Object> params);//@Param("params")

    int deleteByPrimaryKey(@Param("id") Long id);

    int insert(Patient record);

    int insertSelective(Patient record);

    Patient selectByPrimaryKey(@Param("id") Long id);

    int updateByPrimaryKeySelective(Patient record);

    int updateByPrimaryKey(Patient record);
}