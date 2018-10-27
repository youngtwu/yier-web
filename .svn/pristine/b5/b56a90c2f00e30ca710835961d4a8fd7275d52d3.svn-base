package com.yier.platform.service;

import com.alibaba.fastjson.JSONObject;
import com.yier.platform.common.model.Areas;
import com.yier.platform.common.model.Cities;
import com.yier.platform.common.model.Provinces;
import com.yier.platform.common.requestParam.DistrictRequest;
import com.yier.platform.common.util.IPUtils;
import com.yier.platform.dao.AreasMapper;
import com.yier.platform.dao.CitiesMapper;
import com.yier.platform.dao.ProvincesMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * 地址区域管理 service
 */
@ApiModel(value = "地址区域管理 service")
@Service
public class DistrictService {
    private final Logger logger = LoggerFactory.getLogger(DistrictService.class);
    @Autowired
    private CitiesMapper daoCitiesMapper;
    @Autowired
    private ProvincesMapper daoProvincesMapper;
    @Autowired
    private AreasMapper daoAreasMapper;

    @ApiOperation(value = "根据条件查询省份列表")
    public List<Provinces> getProvincesList(DistrictRequest params) {
        return daoProvincesMapper.getProvincesList(params);
    }

    @ApiOperation(value = "根据条件查询省份列表总数")
    public int getProvincesListCount(DistrictRequest params) {
        return daoProvincesMapper.getProvincesListCount(params);
    }

    @ApiOperation(value = "根据条件查询省")
    public Provinces selectProvinceById(Integer provinceId) {
        return daoProvincesMapper.selectProvinceById(provinceId);
    }

    @ApiOperation(value = "根据条件查询省")
    public List<Provinces> selectProvincesByCondition(DistrictRequest params) {
        return daoProvincesMapper.selectProvincesByCondition(params);
    }

    @ApiOperation(value = "根据条件查询城市列表")
    public List<Cities> getCitiesList(DistrictRequest params) {
        return daoCitiesMapper.getCitiesList(params);
    }

    @ApiOperation(value = "根据条件查询城市列表总数")
    public int getCitiesListCount(DistrictRequest params) {
        return daoCitiesMapper.getCitiesListCount(params);
    }

    @ApiOperation(value = "根据条件查询市")
    public Cities selectCityById(String cityId) {
        return daoCitiesMapper.selectCityById(cityId);
    }

    @ApiOperation(value = "根据条件查询市")
    public List<Cities> selectCitiesByCondition(DistrictRequest params) {
        return daoCitiesMapper.selectCitiesByCondition(params);
    }

    @ApiOperation(value = "根据条件查询区域")
    public Areas selectAreaById(String areaId) {
        return daoAreasMapper.selectAreaById(areaId);
    }

    @ApiOperation(value = "根据条件查询区域")
    public List<Areas> selectAreasByCondition(DistrictRequest params) {
        return daoAreasMapper.selectAreasByCondition(params);
    }

    @ApiOperation(value = "根据条件查询区域")
    public List<Areas> getAreasList(DistrictRequest params) {
        List<Areas> areasList = daoAreasMapper.getAreasList(params);
        return areasList;
    }

    @ApiOperation(value = "根据条件查询区域列表总数")
    public int getAreasListCount(DistrictRequest params) {
        return daoAreasMapper.getAreasListCount(params);
    }

    @ApiOperation(value = "新增城市")
    public Cities insertCity(Cities cities) {
        this.daoCitiesMapper.insertSelective(cities);
        return cities;
    }

    @ApiOperation(value = "更新城市")
    public Cities updateCityByCityId(Cities cities) {
//        DistrictRequest params = new DistrictRequest();
//        params.setProvinceId(cities.getProvinceId());
//        params.setCityId(cities.getCityId());
//        List<Cities> citiesList = this.daoCitiesMapper.selectCitiesByCondition(params);
//        if (citiesList != null && citiesList.size() > 0) {
//            cities.setGmtModified(new Date());
//            this.daoCitiesMapper.updateCityByCityId(cities);
//        } else{
//            Areas areas = new Areas();
//            areas.setArea(cities.getCity());
//            areas.setStatus(cities.getStatus());
//            areas.setAreaId(cities.getCityId());
//            areas.setGmtModified(new Date());
//            areas.setRemarks(cities.getRemarks());
//            this.daoAreasMapper.updateAreaByAreaId(areas);
//        }
        cities.setGmtModified(new Date());
        this.daoCitiesMapper.updateCityByCityId(cities);
        return cities;
    }

    @ApiOperation(value = "删除城市")
    public Cities deleteCitiesByPrimaryKey(Cities cities) {
        this.daoCitiesMapper.deleteByPrimaryKey(cities.getId());
        return cities;
    }

    @ApiOperation(value = "删除城市")
    public int deleteCityByCityId(String cityId) {
        return this.daoCitiesMapper.deleteCityByCityId(cityId);
    }

    @ApiOperation(value = "删除城市")
    public int deleteCityByProvinceId(String provinceId) {
        return this.daoCitiesMapper.deleteCityByProvinceId(provinceId);
    }

    @ApiOperation(value = "新增省份")
    public Provinces insertProvince(Provinces provinces) {
        this.daoProvincesMapper.insertSelective(provinces);
        return provinces;
    }

    @ApiOperation(value = "更新省份")
    public Provinces updateProvinceByPrimaryKeySelective(Provinces provinces) {
        provinces.setGmtModified(new Date());
        this.daoProvincesMapper.updateByPrimaryKeySelective(provinces);
        return provinces;
    }

    @ApiOperation(value = "更新省份")
    public Provinces updateProvinceByProvinceId(Provinces provinces) {
        provinces.setGmtModified(new Date());
        this.daoProvincesMapper.updateProvinceByProvinceId(provinces);
        return provinces;
    }

    @ApiOperation(value = "删除省份")
    public int deleteProvincesByPrimaryKey(Provinces provinces) {
        return this.daoProvincesMapper.deleteByPrimaryKey(provinces.getId());
    }

    @ApiOperation(value = "删除省份")
    public int deleteProvinceByProvinceId(Provinces provinces) {
        return this.daoProvincesMapper.deleteProvinceByProvinceId(provinces.getProvinceId());
    }

    @ApiOperation(value = "新增区县")
    public Areas insertArea(Areas areas) {
        this.daoAreasMapper.insertSelective(areas);
        return areas;
    }

    @ApiOperation(value = "更新区县")
    public Areas updateAreaByAreaId(Areas areas) {
        areas.setGmtModified(new Date());
        this.daoAreasMapper.updateAreaByAreaId(areas);
        return areas;
    }

    @ApiOperation(value = "删除区县")
    public int deleteAreaByPrimaryKey(Areas areas) {
        return this.daoAreasMapper.deleteByPrimaryKey(areas.getId());
    }

    @ApiOperation(value = "删除区县")
    public int deleteAreaByAreaId(String areaId) {
        return this.daoAreasMapper.deleteAreaByAreaId(areaId);
    }

    @ApiOperation(value = "百度普通IP定位")
    public String getLocationForIPAddress() {
        //获取当前主机外网ip地址
        String ipAddress = IPUtils.getV4IP();
        logger.info("当前用户电脑所在地的外网ip地址：" + ipAddress);
        //这里调用百度的ip定位api服务 详见 http://api.map.baidu.com/lbsapi/cloud/ip-location-api.htm
        JSONObject json = null;
        try {
            json = IPUtils.readJsonFromUrl("http://api.map.baidu.com/location/ip?coor=bd09ll&ak=lhoU6vS5Beh2x0gUUtXc0KFIZuBoHieQ&ip=" + ipAddress);
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        logger.info(json.toJSONString());
        return json.toJSONString();
    }
}
