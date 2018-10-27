package com.yier.platform.dao;

import com.yier.platform.common.model.Provinces;
import com.yier.platform.common.requestParam.BaseRequest;
import com.yier.platform.common.requestParam.DistrictRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProvincesMapper {
    // 根据条件查询列表
    List<Provinces> listProvinces(BaseRequest params);
    // 根据条件查询列表 总数
    int listProvincesCount(BaseRequest params);

    // 根据条件查询列表
    List<Provinces> getProvincesList(DistrictRequest params);
    // 根据条件查询列表 总数
    int getProvincesListCount(DistrictRequest params);


    // 根据条件查询省份
    Provinces selectProvinceById(Integer provinceId);

    // 根据条件查询省份
    List<Provinces> selectProvincesByCondition(DistrictRequest params);

    int updateProvinceByProvinceId(Provinces record);

    int deleteProvinceByProvinceId(String provinceId);





    int deleteByPrimaryKey(Integer id);

    int insert(Provinces record);

    int insertSelective(Provinces record);

    Provinces selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Provinces record);

    int updateByPrimaryKey(Provinces record);
}