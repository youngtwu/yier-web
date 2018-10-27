package com.yier.platform.dao;

import com.yier.platform.common.model.HospitalPo;
import com.yier.platform.common.model.Pharmacy;
import com.yier.platform.common.model.PharmacyPo;
import com.yier.platform.common.requestParam.HospitalRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PharmacyMapper {
    // 根据条件查询列表
    List<PharmacyPo> listPharmacyPo(HospitalRequest params);
    // 根据条件查询列表 总数
    int listPharmacyPoCount(HospitalRequest params);

    // 根据条件查询列表
    List<Pharmacy> listPharmacy(HospitalRequest params);
    // 根据条件查询列表 总数
    int listPharmacyCount(HospitalRequest params);



    Pharmacy selectPharmacyByHospitalId(Long hospitalId);



    int deleteByPrimaryKey(Long id);

    int insert(Pharmacy record);

    int insertSelective(Pharmacy record);

    Pharmacy selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Pharmacy record);

    int updateByPrimaryKeyWithBLOBs(Pharmacy record);

    int updateByPrimaryKey(Pharmacy record);
}