package com.yier.platform.service;

import com.alibaba.fastjson.JSONObject;
import com.yier.platform.TestBase;
import com.yier.platform.common.jsonResponse.CommonResponse;
import com.yier.platform.common.jsonResponse.ListResponse;
import com.yier.platform.common.model.Areas;
import com.yier.platform.common.model.Cities;
import com.yier.platform.common.model.Provinces;
import com.yier.platform.common.requestParam.DistrictRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@ApiModel(value = "地址区域管理请求接口测试类")
public class DistrictServiceTest extends TestBase {
    private static final Logger logger = LoggerFactory.getLogger(DistrictServiceTest.class);

    @Autowired
    private DistrictService districtService;

    @ApiOperation(value = "通过查询获取省份信息列表")
    @Test
    public void getProvincesList() {
        DistrictRequest params = new DistrictRequest();
        params.doWithSortOrStart();
        logger.info("目前分页条件如下：" + params.toJsonString());
        ListResponse<Provinces> res = new ListResponse<Provinces>();
        List<Provinces> list = this.districtService.getProvincesList(params);
        int count = this.districtService.getProvincesListCount(params);
        res.setData(list);
        res.setTotal(count);
        logger.info("测试结果：res==={}", res.toJsonString());
    }

    @ApiOperation(value = "通过查询获取省份信息")
    @Test
    public void selectProvincesByCondition() {
        DistrictRequest params = new DistrictRequest();
        params.setProvinceId("110000");
        logger.info("通过查询获取城市信息，请求参数：" + params.toJsonString());
        ListResponse<Provinces> res = new ListResponse<Provinces>();
        List<Provinces> list = this.districtService.selectProvincesByCondition(params);
        res.setData(list);
        logger.info("测试结果：res==={}", res.toJsonString());
    }

    @ApiOperation(value = "通过查询获取城市信息列表")
    @Test
    public void getCitiesList() {
        DistrictRequest params = new DistrictRequest();
        params.doWithSortOrStart();
        logger.info("目前分页条件如下：" + params.toJsonString());
        ListResponse<Cities> res = new ListResponse<Cities>();
        List<Cities> list = this.districtService.getCitiesList(params);
        int count = this.districtService.getCitiesListCount(params);
        res.setData(list);
        res.setTotal(count);
        logger.info("测试结果：res==={}", res.toJsonString());
    }

    @ApiOperation(value = "通过查询获取城市信息")
    @Test
    public void selectCitiesByCondition() {
        DistrictRequest params = new DistrictRequest();
        params.setProvinceId("110000");
        logger.info("通过查询获取城市信息，请求参数：" + params.toJsonString());
        ListResponse<Cities> res = new ListResponse<Cities>();
        List<Cities> cities = this.districtService.selectCitiesByCondition(params);
        res.setData(cities);
        logger.info("测试结果：res==={}", res.toJsonString());
    }

    @ApiOperation(value = "通过查询获取地区信息列表")
    @Test
    public void getAreasList() {
        DistrictRequest params = new DistrictRequest();
        params.doWithSortOrStart();
        logger.info("目前分页条件如下：" + params.toJsonString());
        ListResponse<Areas> res = new ListResponse<Areas>();
        List<Areas> list = this.districtService.getAreasList(params);
        int count = this.districtService.getAreasListCount(params);
        res.setData(list);
        res.setTotal(count);
        logger.info("测试结果：res==={}", res.toJsonString());
    }

    @ApiOperation(value = "通过查询获取地区信息")
    @Test
    public void selectAreasByCondition() {
        DistrictRequest params = new DistrictRequest();
        params.setCityId("110100");
        params.setAreaId("110102");
        logger.info("通过查询获取地区信息：请求参数：" + params.toJsonString());
        ListResponse<Areas> res = new ListResponse<Areas>();
        List<Areas> areas = this.districtService.selectAreasByCondition(params);
        res.setData(areas);
        logger.info("测试结果：res==={}", res.toJsonString());
    }

    @ApiOperation(value = "新增省份信息")
    @Test
    public void insertProvince() {
        //Provinces info = new Provinces();
        String json = "{\"provinceId\": \"666888\", \"province\": \"测试新增城市\" }";
        Provinces info = JSONObject.parseObject(json, Provinces.class);
        logger.info("新增省份信息, 请求参数：json==={}", json);
        CommonResponse<Provinces> res = new CommonResponse<Provinces>();
        Provinces provinces = this.districtService.insertProvince(info);
        res.setData(provinces);
        logger.info("新增省份信息, 测试结果：res==={}", res.toJsonString());
    }

    @ApiOperation(value = "更新省份信息")
    @Test
    public void updateProvince() {
        String json = "{\"provinceId\": \"666888\", \"province\": \"测试更新城市\" }";
        Provinces info = JSONObject.parseObject(json, Provinces.class);
        logger.info("更新省份信息, 请求参数：json==={}", json);
        CommonResponse<Provinces> res = new CommonResponse<Provinces>();
        Provinces provinces = this.districtService.updateProvinceByProvinceId(info);
        res.setData(provinces);
        logger.info("更新省份信息, 测试结果：res==={}", res.toJsonString());
    }

    @ApiOperation(value = "新增城市信息")
    @Test
    public void insertCity() {
        String json = "{\"cityId\":\"666888\",\"city\":\"测试新增城市\",\"provinceId\":\"666888\",\"grade\":2}";
        Cities info = JSONObject.parseObject(json, Cities.class);
        logger.info("新增城市信息, 请求参数：json==={}", json);
        CommonResponse<Cities> res = new CommonResponse<Cities>();
        Cities cities = this.districtService.insertCity(info);
        res.setData(cities);
        logger.info("新增城市信息, 测试结果：res==={}", res.toJsonString());
    }

    @ApiOperation(value = "更新城市信息")
    @Test
    public void updateCity() {
        String json = "{\"cityId\":\"110101\",\"city\":\"测试更新城市\",\"provinceId\":\"110000\"}";
        Cities info = JSONObject.parseObject(json, Cities.class);
        logger.info("更新城市信息, 请求参数：json==={}", json);
        CommonResponse<Cities> res = new CommonResponse<Cities>();
        Cities cities = this.districtService.updateCityByCityId(info);
        res.setData(cities);
        logger.info("更新城市信息, 测试结果：res==={}", res.toJsonString());
    }

    @ApiOperation(value = "新增区县信息")
    @Test
    public void insertAreas() {
        String json = "{\"areaId\":\"666888\",\"area\":\"测试新增区域信息\",\"cityId\":\"666888\"}";
        Areas info = JSONObject.parseObject(json, Areas.class);
        logger.info("新增区县信息, 请求参数：json==={}", json);
        CommonResponse<Areas> res = new CommonResponse<Areas>();
        Areas areas = this.districtService.insertArea(info);
        res.setData(areas);
        logger.info("新增区县信息, 测试结果：res==={}", res.toJsonString());
    }

    @ApiOperation(value = "更新区县信息")
    @Test
    public void updateArea() {
        String json = "{\"areaId\":\"666888\",\"area\":\"测试更新区域信息\",\"cityId\":\"666888\"}";
        Areas info = JSONObject.parseObject(json, Areas.class);
        logger.info("更新区县信息, 请求参数：json==={}", json);
        CommonResponse<Areas> res = new CommonResponse<Areas>();
        Areas areas = this.districtService.updateAreaByAreaId(info);
        res.setData(areas);
        logger.info("更新区县信息, 测试结果：res==={}", res.toJsonString());
    }

    @ApiOperation(value = "百度普通IP定位")
    @Test
    public void getLocationForIPAddress() {
        CommonResponse<String> res = new CommonResponse<String>();
        String location = this.districtService.getLocationForIPAddress();
        res.setData(location);
        logger.info("百度普通IP定位, 测试结果：res==={}", res.toJsonString());
    }
}
