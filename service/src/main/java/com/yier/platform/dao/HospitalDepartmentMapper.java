package com.yier.platform.dao;

import com.yier.platform.common.model.HospitalDepartment;
import com.yier.platform.common.model.HospitalDepartmentPo;
import com.yier.platform.common.requestParam.HospitalRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 每个医院科室大分类下的具体科室信息
 */
@Repository
public interface HospitalDepartmentMapper {
    //根据条件查询关联的医院及主分类列表
    List<HospitalDepartmentPo> listHospitalDepartmentPo(HospitalRequest params);


    // 根据条件查询列表
    List<HospitalDepartment> listHospitalDepartmentForDoctor(HospitalRequest params);

    // 根据条件查询列表 总数
    int listHospitalDepartmentForDoctorCount(HospitalRequest params);

    // 根据条件查询列表
    List<HospitalDepartment> listHospitalDepartmentForPharmacist(HospitalRequest params);

    // 根据条件查询列表 总数
    int listHospitalDepartmentForPharmacistCount(HospitalRequest params);

    // 根据条件查询列表
    List<HospitalDepartment> listHospitalDepartment(HospitalRequest params);

    // 根据条件查询列表 总数
    int listHospitalDepartmentCount(HospitalRequest params);

    /**
     * 根据医院id和大科室删除对应小科室
     * @param hospitalId
     * @return
     */
    int deleteHospitalDepartmentByHospitalId(Long hospitalId);

    /**
     * 根据医院id和大科室删除对应小科室
     * @param params
     * @return
     */
    HospitalDepartment selectHospitalDepartment(HospitalRequest params);


    int deleteByPrimaryKey(Long id);

    int insert(HospitalDepartment record);

    int insertSelective(HospitalDepartment record);

    HospitalDepartment selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(HospitalDepartment record);

    int updateByPrimaryKey(HospitalDepartment record);
}