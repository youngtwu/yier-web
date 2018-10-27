package com.yier.platform.dao;

import com.yier.platform.common.model.PharmacistTitle;
import com.yier.platform.common.requestParam.BaseRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PharmacistTitleMapper {

    // 根据条件查询列表
    List<PharmacistTitle> listPharmacistTitle(BaseRequest params);
    // 根据条件查询列表 总数
    int listPharmacistTitleCount(BaseRequest params);


    int deleteByPrimaryKey(Long id);

    int insert(PharmacistTitle record);

    int insertSelective(PharmacistTitle record);

    PharmacistTitle selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PharmacistTitle record);

    int updateByPrimaryKey(PharmacistTitle record);
}