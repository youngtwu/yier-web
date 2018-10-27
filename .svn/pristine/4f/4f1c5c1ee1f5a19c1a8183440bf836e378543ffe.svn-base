package com.yier.platform.dao;

import com.yier.platform.common.model.DoctorEvaluation;
import com.yier.platform.common.model.DoctorEvaluationPo;
import com.yier.platform.common.model.DoctorPo;
import com.yier.platform.common.model.HospitalDepartmentPo;
import com.yier.platform.common.requestParam.DoctorRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorEvaluationMapper {
    //根据条件Id查询评价医生及科室关联的医院及主分类信息
    DoctorEvaluationPo getDoctorEvaluationPoWithDoctorById(Long id);
    //查询医生信息关联1对1类)
    DoctorPo getDoctorPoById(Long id);
    //根据条件Id查询科室关联的医院及主分类信息
    HospitalDepartmentPo getHospitalDepartmentPoForDoctorById(Long id);

    // 根据条件分组查询列表(关联查询1对1类)
    List<DoctorEvaluationPo> listEvaluationWithGroupDoctorContent(DoctorRequest params);
    // 根据条件分组查询列表 总数
    int listEvaluationWithGroupDoctorContentCount(DoctorRequest params);
    // 根据条件查询列表(关联查询1对1类)
    List<DoctorEvaluationPo> listEvaluationWithDoctorContent(DoctorRequest params);
    // 根据条件查询列表 总数
    int listEvaluationWithDoctorContentCount(DoctorRequest params);


    // 根据条件查询列表
    List<DoctorEvaluationPo> listDoctorEvaluationPo(DoctorRequest params);
    // 根据条件查询列表 总数
    int listDoctorEvaluationPoCount(DoctorRequest params);
    //获取医生评价列表
    String getDoctorEvaluationList(DoctorRequest params);

    int deleteByPrimaryKey(Long id);

    int insert(DoctorEvaluation record);

    int insertSelective(DoctorEvaluation record);

    DoctorEvaluation selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DoctorEvaluation record);

    int updateByPrimaryKey(DoctorEvaluation record);
}