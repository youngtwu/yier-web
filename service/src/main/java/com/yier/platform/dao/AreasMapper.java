package com.yier.platform.dao;

import com.yier.platform.common.model.Areas;
import com.yier.platform.common.model.Provinces;
import com.yier.platform.common.requestParam.BaseRequest;
import com.yier.platform.common.requestParam.DistrictRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 区县信息
 */
@Repository
public interface AreasMapper {
    // 根据条件查询列表
    List<Areas> listAreas(BaseRequest params);
    // 根据条件查询列表 总数
    int listAreasCount(BaseRequest params);

    Areas selectAreaById(String areaId);

    List<Areas> selectAreasByCondition(DistrictRequest params);

    // 根据条件查询区域列表
    List<Areas> getAreasList(DistrictRequest params);

    // 根据条件查询区域列表总数
    int getAreasListCount(DistrictRequest params);

    int updateAreaByAreaId(Areas areas);

    int deleteAreaByAreaId(String areaId);

    int deleteAreaByCityId(String cityId);




    
    int deleteByPrimaryKey(Integer id);

    int insert(Areas record);

    int insertSelective(Areas record);

    Areas selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Areas record);

    int updateByPrimaryKey(Areas record);
}