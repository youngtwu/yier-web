package com.yier.platform.dao;

import com.yier.platform.common.model.DiseaseCode;
import com.yier.platform.common.requestParam.DiseaseCodeRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiseaseCodeMapper {
    // 根据条件查询列表 根据id列表查询到医生没有收藏的疾病代码
    List<DiseaseCode> listDoctorWithoutDiseaseCode(DiseaseCodeRequest params);

    // 根据条件查询列表
    List<DiseaseCode> listDiseaseCode(DiseaseCodeRequest params);
    // 根据条件查询列表 总数
    int listDiseaseCodeCount(DiseaseCodeRequest params);



    int deleteByPrimaryKey(Long id);

    int insert(DiseaseCode record);

    int insertSelective(DiseaseCode record);

    DiseaseCode selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DiseaseCode record);

    int updateByPrimaryKey(DiseaseCode record);
}