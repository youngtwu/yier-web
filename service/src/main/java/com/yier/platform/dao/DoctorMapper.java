package com.yier.platform.dao;

import com.yier.platform.common.model.Doctor;
import com.yier.platform.common.model.DoctorPo;
import com.yier.platform.common.requestParam.BaseRequest;
import com.yier.platform.common.requestParam.DoctorRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorMapper {
    // 根据条件获取医生对应医院,科室信息 列表,主要是可以批量查询
    List<DoctorPo> listDoctorHospitalPo(DoctorRequest params);

    // 根据条件查询出门就诊列表
    List<DoctorPo> listMedicalDoctorPo(DoctorRequest params);
    // 根据条件查询出门就诊列表 总数
    int listMedicalDoctorPoCount(DoctorRequest params);

    //根据IP获取医生关于医院及科室的信息详情信息
    DoctorPo getDoctorHospitalPoById(Long id);
    //搜索对应的模糊名字
    List<String> getSimilarNameList(BaseRequest params);
    // 根据条件查询列表
    List<DoctorPo> listDoctorPo(DoctorRequest params);
    // 根据条件查询列表 总数
    int listDoctorPoCount(DoctorRequest params);

    // 根据条件查询原始列表
    List<Doctor> listDoctor(DoctorRequest params);
    // 根据条件查询原始列表 总数
    int listDoctorCount(DoctorRequest params);

    //根据IP获取医生详情信息
    DoctorPo getDoctorPoById(DoctorRequest params);

    int deleteByPrimaryKey(Long id);

    int insert(Doctor record);
    //插入所有活跃的字段域
    int insertSelective(Doctor record);

    Doctor selectByPrimaryKey(Long id);
    //更新所有活跃的字段域
    int updateByPrimaryKeySelective(Doctor record);

    int updateByPrimaryKeyWithBLOBs(Doctor record);

    int updateByPrimaryKey(Doctor record);
}