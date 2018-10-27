package com.yier.platform.dao;

import com.yier.platform.common.model.DoctorTitle;
import com.yier.platform.common.requestParam.BaseRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorTitleMapper {
    // 根据条件查询列表
    List<DoctorTitle> listDoctorTitle(BaseRequest params);
    // 根据条件查询列表 总数
    int listDoctorTitleCount(BaseRequest params);



    int deleteByPrimaryKey(Long id);

    int insert(DoctorTitle record);

    int insertSelective(DoctorTitle record);

    DoctorTitle selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DoctorTitle record);

    int updateByPrimaryKey(DoctorTitle record);
}