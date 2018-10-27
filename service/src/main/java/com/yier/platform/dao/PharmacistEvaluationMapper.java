package com.yier.platform.dao;

import com.yier.platform.common.model.HospitalDepartmentPo;
import com.yier.platform.common.model.PharmacistEvaluation;
import com.yier.platform.common.model.PharmacistEvaluationPo;
import com.yier.platform.common.model.PharmacistPo;
import com.yier.platform.common.requestParam.PharmacistRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PharmacistEvaluationMapper {
    //根据条件Id查询评价医生及科室关联的医院及主分类信息
    PharmacistEvaluationPo getPharmacistEvaluationPoWithPharmacistById(Long id);
    //查询医生信息关联1对1类)
    PharmacistPo getPharmacistPoById(Long id);
    //根据条件Id查询科室关联的医院及主分类信息
    HospitalDepartmentPo getHospitalDepartmentPoForPharmacistById(Long id);

    // 根据条件查询列表(关联查询1对1类)
    List<PharmacistEvaluationPo> listEvaluationWithGroupPharmacistContent(PharmacistRequest params);
    // 根据条件查询列表 总数
    int listEvaluationWithGroupPharmacistContentCount(PharmacistRequest params);
    // 根据条件查询列表(关联查询1对1类)
    List<PharmacistEvaluationPo> listEvaluationWithPharmacistContent(PharmacistRequest params);
    // 根据条件查询列表 总数
    int listEvaluationWithPharmacistContentCount(PharmacistRequest params);


    // 根据条件查询列表
    List<PharmacistEvaluationPo> listPharmacistEvaluationPo(PharmacistRequest params);
    // 根据条件查询列表 总数
    int listPharmacistEvaluationPoCount(PharmacistRequest params);
    //获取医生评价列表
    String getPharmacistEvaluationList(PharmacistRequest params);




    int deleteByPrimaryKey(Long id);

    int insert(PharmacistEvaluation record);

    int insertSelective(PharmacistEvaluation record);

    PharmacistEvaluation selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PharmacistEvaluation record);

    int updateByPrimaryKey(PharmacistEvaluation record);
}