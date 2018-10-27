package com.yier.platform.web;

import com.github.yingzhuo.carnival.restful.security.Logical;
import com.github.yingzhuo.carnival.restful.security.RequiresAuthentication;
import com.github.yingzhuo.carnival.restful.security.RequiresRoles;
import com.yier.platform.annotation.ApiCheck;
import com.yier.platform.common.jsonResponse.CommonResponse;
import com.yier.platform.common.jsonResponse.ListResponse;
import com.yier.platform.common.model.Areas;
import com.yier.platform.common.model.Cities;
import com.yier.platform.common.model.Provinces;
import com.yier.platform.common.requestParam.DistrictRequest;
import com.yier.platform.service.DistrictService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@ApiModel(value = "地址区域管理请求接口")
@RestController
@RequestMapping("/districtManagement")
@Slf4j
public class DistrictManagementController {
    private static final Logger logger = LoggerFactory.getLogger(DistrictManagementController.class);
    @Autowired
    private DistrictService districtService;

    @ApiOperation(value = "通过查询获取省份信息列表(不包含热门城市)")
    @ApiCheck(check = false)
    @RequiresAuthentication
    @RequestMapping(value = "/getProvincesList.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ListResponse<Provinces> getProvincesList(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "通用请求接口类") @RequestBody(required = false) DistrictRequest params) {
        params.doWithSortOrStart();
        logger.info("目前分页条件如下：" + params.toJsonString());
        ListResponse<Provinces> res = new ListResponse<Provinces>();
        List<Provinces> list = this.districtService.getProvincesList(params);
        int count = this.districtService.getProvincesListCount(params);
        res.setData(list);
        res.setTotal(count);
        logger.debug(res.toJsonString());
        return res;
    }

    @ApiOperation(value = "通过查询获取省份信息")
    @ApiCheck(check = false)
    @RequiresAuthentication
    @RequestMapping(value = "/selectProvincesByCondition.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ListResponse<Provinces> selectProvincesByCondition(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "通用请求接口类") @RequestBody(required = false) DistrictRequest params) {
        logger.info("通过查询获取省份信息列表，请求参数：" + params.toJsonString());
        ListResponse<Provinces> res = new ListResponse<Provinces>();
        List<Provinces> list = this.districtService.selectProvincesByCondition(params);
        res.setData(list);
        logger.debug(res.toJsonString());
        return res;
    }

    @ApiOperation(value = "通过查询获取城市信息列表, 例如：{\"searchKey\":130000}")
    @ApiCheck(check = false)
    @RequiresAuthentication
    @RequestMapping(value = "/getCitiesList.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ListResponse<Cities> getCitiesList(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "通用请求接口类") @RequestBody(required = false) DistrictRequest params) {
        params.doWithSortOrStart();
        logger.info("目前分页条件如下：" + params.toJsonString());
        ListResponse<Cities> res = new ListResponse<Cities>();
        List<Cities> list = this.districtService.getCitiesList(params);
        int count = this.districtService.getCitiesListCount(params);
        res.setData(list);
        res.setTotal(count);
        logger.debug(res.toJsonString());
        return res;
    }

    @ApiOperation(value = "通过查询获取城市信息，例如：{\"searchKey\":130000}")
    @ApiCheck(check = false)
    @RequiresAuthentication
    @RequestMapping(value = "/selectCitiesByCondition.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ListResponse<Cities> selectCitiesByCondition(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "通用请求接口类") @RequestBody(required = false) DistrictRequest params) {
        logger.info("通过查询获取城市信息，请求参数：" + params.toJsonString());
        ListResponse<Cities> res = new ListResponse<Cities>();
        List<Cities> cities = this.districtService.selectCitiesByCondition(params);
        res.setData(cities);
        logger.debug(res.toJsonString());
        return res;
    }

    @ApiOperation(value = "通过查询获取地区信息列表, 例如：{\"searchKey\":130100}")
    @ApiCheck(check = false)
    @RequiresAuthentication
    @RequestMapping(value = "/getAreasList.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ListResponse<Areas> getAreasList(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "通用请求接口类") @RequestBody(required = false) DistrictRequest params) {
        params.doWithSortOrStart();
        logger.info("目前分页条件如下：" + params.toJsonString());
        ListResponse<Areas> res = new ListResponse<Areas>();
        List<Areas> list = this.districtService.getAreasList(params);
        int count = this.districtService.getAreasListCount(params);
        res.setData(list);
        res.setTotal(count);
        logger.debug(res.toJsonString());
        return res;
    }

    @ApiOperation(value = "通过查询获取地区信息")
    @ApiCheck(check = false)
    @RequiresAuthentication
    @RequestMapping(value = "/selectAreasByCondition.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ListResponse<Areas> selectAreasByCondition(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "通用请求接口类") @RequestBody(required = false) DistrictRequest params) {
        logger.info("通过查询获取地区信息：请求参数：" + params.toJsonString());
        ListResponse<Areas> res = new ListResponse<Areas>();
        List<Areas> areas = this.districtService.selectAreasByCondition(params);
        res.setData(areas);
        logger.debug(res.toJsonString());
        return res;
    }

    @ApiOperation(value = "新增省份信息")
    @ApiCheck(check = false)
    @RequiresAuthentication
    @RequiresRoles(value = {"Super"}, logical = Logical.AND)
    @RequestMapping(value = "/insertProvince.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse<Provinces> insertProvince(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "通用请求接口类") @RequestBody(required = false) Provinces info) {
        logger.info("新增省份信息, 请求参数：{}" + info.toJsonString());
        CommonResponse<Provinces> res = new CommonResponse<Provinces>();
        Provinces provinces = this.districtService.insertProvince(info);
        res.setData(provinces);
        logger.debug(res.toJsonString());
        return res;
    }

    @ApiOperation(value = "更新省份信息")
    @ApiCheck(check = false)
    @RequiresAuthentication
    @RequiresRoles(value = {"Super"}, logical = Logical.AND)
    @RequestMapping(value = "/updateProvince.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse<Provinces> updateProvince(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "通用请求接口类") @RequestBody(required = false) Provinces info) {
        logger.info("更新省份信息, 请求参数：{}" + info.toJsonString());
        CommonResponse<Provinces> res = new CommonResponse<Provinces>();
        Provinces provinces = this.districtService.updateProvinceByProvinceId(info);
        res.setData(provinces);
        logger.debug(res.toJsonString());
        return res;
    }

    @ApiOperation(value = "新增城市信息")
    @ApiCheck(check = false)
    @RequiresAuthentication
    @RequiresRoles(value = {"Super"}, logical = Logical.AND)
    @RequestMapping(value = "/insertCity.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse<Cities> insertCity(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "通用请求接口类") @RequestBody(required = false) Cities info) {
        logger.info("新增城市信息, 请求参数：{}" + info.toJsonString());
        CommonResponse<Cities> res = new CommonResponse<Cities>();
        Cities cities = this.districtService.insertCity(info);
        res.setData(cities);
        logger.debug(res.toJsonString());
        return res;
    }

    @ApiOperation(value = "更新城市信息")
    @ApiCheck(check = false)
    @RequiresAuthentication
    @RequiresRoles(value = {"Super"}, logical = Logical.AND)
    @RequestMapping(value = "/updateCity.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse<Cities> updateCity(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "通用请求接口类") @RequestBody(required = false) Cities info) {
        logger.info("更新城市信息, 请求参数：{}" + info.toJsonString());
        CommonResponse<Cities> res = new CommonResponse<Cities>();
        Cities cities = districtService.updateCityByCityId(info);
        res.setData(cities);
        logger.debug(res.toJsonString());
        return res;
    }

    @ApiOperation(value = "新增区县信息")
    @ApiCheck(check = false)
    @RequiresAuthentication
    @RequiresRoles(value = {"Super"}, logical = Logical.AND)
    @RequestMapping(value = "/insertArea.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse<Areas> insertAreas(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "通用请求接口类") @RequestBody(required = false) Areas info) {
        logger.info("新增区县信息, 请求参数：{}" + info.toJsonString());
        CommonResponse<Areas> res = new CommonResponse<Areas>();
        Areas areas = this.districtService.insertArea(info);
        res.setData(areas);
        log.debug(res.toJsonString());
        return res;
    }

    @ApiOperation(value = "更新区县信息")
    @ApiCheck(check = false)
    @RequiresAuthentication
    @RequiresRoles(value = {"Super"}, logical = Logical.AND)
    @RequestMapping(value = "/updateArea.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse<Areas> updateArea(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "通用请求接口类") @RequestBody(required = false) Areas info) {
        logger.info("更新区县信息, 请求参数：{}" + info.toJsonString());
        CommonResponse<Areas> res = new CommonResponse<Areas>();
        Areas areas = districtService.updateAreaByAreaId(info);
        res.setData(areas);
        logger.debug(res.toJsonString());
        return res;
    }

    @ApiOperation(value = "百度普通IP定位")
    @ApiCheck(check = false)
    @RequiresAuthentication
    //@RequiresRoles(value = {"Super"}, logical = Logical.AND)
    @RequestMapping(value = "/getLocationForIPAddress.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse<String> getLocationForIPAddress(HttpServletRequest request) {
        logger.info("百度普通IP定位, 请求参数：request==={}" + request);
        CommonResponse<String> res = new CommonResponse<String>();
        String location = this.districtService.getLocationForIPAddress();
        res.setData(location);
        logger.debug(res.toJsonString());
        return res;
    }
}
