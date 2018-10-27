package com.yier.platform.dao;

import com.yier.platform.common.model.MedicalOrder;
import com.yier.platform.common.model.MedicalPrescription;
import com.yier.platform.common.model.MedicalPrescriptionPo;
import com.yier.platform.common.requestParam.MedicalOrderRequest;
import com.yier.platform.common.requestParam.MedicalPrescriptionRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicalPrescriptionMapper {
    // 根据条件查询处方带有药品的列表
    List<MedicalPrescriptionPo> listMedicalPrescriptionPo(MedicalPrescriptionRequest params);
    // 根据条件查询处方带有药品的列表 总数
    int listMedicalPrescriptionPoCount(MedicalPrescriptionRequest params);

    MedicalPrescriptionPo getMedicalPrescriptionPoById(MedicalPrescriptionRequest params);
    // 根据条件查询列表
    List<MedicalPrescription> listMedicalPrescription(MedicalPrescriptionRequest params);
    // 根据条件查询列表 总数
    int listMedicalPrescriptionCount(MedicalPrescriptionRequest params);
    //可能批量更新
    int updateBatchByCondition(MedicalPrescription record);

    int deleteByPrimaryKey(Long id);

    int insert(MedicalPrescription record);

    int insertSelective(MedicalPrescription record);

    MedicalPrescription selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MedicalPrescription record);

    int updateByPrimaryKey(MedicalPrescription record);
}