package com.yier.platform.dao;

import com.yier.platform.common.model.DepartmentCatalog;
import com.yier.platform.common.requestParam.HospitalRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 每个医院科室大分类信息
 */
@Repository
public interface DepartmentCatalogMapper {
    // 根据条件查询列表
    List<DepartmentCatalog> listDepartmentCatalogForDoctor(HospitalRequest params);
    // 根据条件查询列表 总数
    int listDepartmentCatalogForDoctorCount(HospitalRequest params);

    // 根据条件查询列表
    List<DepartmentCatalog> listDepartmentCatalogForPharmacist(HospitalRequest params);
    // 根据条件查询列表 总数
    int listDepartmentCatalogForPharmacistCount(HospitalRequest params);

    // 根据条件查询列表
    List<DepartmentCatalog> listDepartmentCatalog(HospitalRequest params);
    // 根据条件查询列表 总数
    int listDepartmentCatalogCount(HospitalRequest params);

    /**
     * 根据医院id删除大科室
     * @param hospitalId
     * @return
     */
    int deleteDepartmentCatalogByHospitalId(Long hospitalId);

    /**
     * 根据医院id查询大科室
     * @param params
     * @return
     */
    DepartmentCatalog selectDepartmentCatalog(HospitalRequest params);




    int deleteByPrimaryKey(Long id);

    int insert(DepartmentCatalog record);

    int insertSelective(DepartmentCatalog record);

    DepartmentCatalog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DepartmentCatalog record);

    int updateByPrimaryKey(DepartmentCatalog record);
}