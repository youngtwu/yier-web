package com.yier.platform.dao;

import com.yier.platform.common.model.DoctorPo;
import com.yier.platform.common.model.Hospital;
import com.yier.platform.common.model.HospitalPo;
import com.yier.platform.common.model.PharmacistPo;
import com.yier.platform.common.requestParam.HospitalRequest;
import com.yier.platform.common.requestParam.PracticeRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HospitalMapper {
    // 根据条件查询列表
    List<HospitalPo> listHospitalForDoctor(HospitalRequest params);//@Param("params")//错误无法实现
    // 根据条件查询列表 总数
    int listHospitalForDoctorCount(HospitalRequest params);

    // 根据条件查询列表
    List<HospitalPo> listHospitalForPharmacist(HospitalRequest params);//@Param("params")//错误无法实现
    // 根据条件查询列表 总数
    int listHospitalForPharmacistCount(HospitalRequest params);

    // 根据条件查询列表
    List<HospitalPo> listHospital(HospitalRequest params);//@Param("params")//错误无法实现
    // 根据条件查询列表 总数
    int listHospitalCount(HospitalRequest params);
    // 根据医院省份区域条件查询列表
    List<DoctorPo> listDoctorPoByArea(HospitalRequest params);
    // 根据医院省份区域条件查询列表 总数
    int listDoctorPoByAreaCount(HospitalRequest params);
    // 根据医院省份区域条件查询列表
    List<PharmacistPo> listPharmacistPoByArea(HospitalRequest params);
    // 根据医院省份区域条件查询列表 总数
    int listPharmacistPoByAreaCount(HospitalRequest params);


    //根据IP获取医院综合详情信息
    HospitalPo getHospitalWithHotById(Long id);

    HospitalPo selectHospitalByCondition(HospitalRequest params);

    //根据IP获取医院综合详情信息，包括省市
    HospitalPo getHospitalPoWithNameByIds(PracticeRequest params);

    int deleteByPrimaryKey(Long id);

    int insert(Hospital record);

    int insertSelective(Hospital record);

    Hospital selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Hospital record);

    int updateByPrimaryKeyWithBLOBs(Hospital record);

    int updateByPrimaryKey(Hospital record);
}