package com.yier.platform.controller;

import com.yier.platform.annotation.ApiCheck;
import com.yier.platform.common.jsonResponse.CommonResponse;
import com.yier.platform.common.jsonResponse.ListResponse;
import com.yier.platform.common.model.*;
import com.yier.platform.common.po.HospitalDoctorList;
import com.yier.platform.common.requestParam.BaseRequest;
import com.yier.platform.common.requestParam.HospitalRequest;
import com.yier.platform.service.DoctorService;
import com.yier.platform.service.HospitalService;
import com.yier.platform.service.PharmacistService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@ApiModel(value = "医院相关的请求接口")
@RestController
@RequestMapping("/hospital")
@Slf4j
public class HospitalController {
    @Autowired
    private HospitalService serviceHospitalService;
    @Autowired
    private DoctorService serviceDoctorService;
    @Autowired
    private PharmacistService servicePharmacistService;

    @ApiOperation(value = "模糊查询获取医院-医生-或者-药师名字的列表")
    @ApiCheck(check = true)
    @RequestMapping(value = "/getSimilarNameList.json", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ListResponse<String> getSimilarNameList(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "模糊名字") @RequestParam(value = "searchKey", required = true) String searchKey, @ApiParam(value = "医生2，药师3") @RequestParam(value = "typeId", required = true,defaultValue = "2") long typeId) {
        ListResponse<String> res = new ListResponse<String>();
        BaseRequest params = new BaseRequest();
        params.setSearchKey(searchKey);
        List<String> list = typeId == 2 ? serviceDoctorService.getSimilarNameList(params):servicePharmacistService.getSimilarNameList(params);
        res.setData(list);
        res.setTotal(list.size());
        log.debug(res.toJsonString());
        return res;
    }

    @ApiOperation(value = "通过名字查询获取医院信息列表和医生信息列表 filterShow:null,patientId:1000,chat:0 不过滤有效医生或药师的医院,listDoctorPoByArea 是关联患者,并且是有效可聊天")
    @ApiCheck(check = true)
    @RequestMapping(value = "/getHospitalDoctorList.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse<HospitalDoctorList> getHospitalDoctorList(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "医院请求接口类") @RequestBody HospitalRequest params) {
        params.doWithSortOrStart();
        log.info("通过名字查询获取医院信息列表和医生信息列表 目前分页条件如下：" + params.toJsonString());
        CommonResponse<HospitalDoctorList> res = new CommonResponse<HospitalDoctorList>();
        HospitalDoctorList hdl = new HospitalDoctorList();
        log.info("paramsHr-->目前分页条件如下：" + params.toJsonString());
        List<HospitalPo> listHospital = serviceHospitalService.listHospital(params);
        //int countHr = serviceHospitalService.listHospitalCount(params);
        hdl.setHospitalList(listHospital);
        params.setOrder(null);
        params.setSort(null);
        List<DoctorPo> listDoctor = serviceHospitalService.listDoctorPoByArea(params);
        //int countDr = serviceHospitalService.listHospitalCount(params);
        hdl.setDoctorList(listDoctor);
        res.setData(hdl);
        log.debug(res.toJsonString());
        return res;
    }





    @ApiOperation(value = "通过名字查询获取医院信息列表和药师信息列表 filterShow:null,patientId:1000,chat:0 不过滤有效医生或药师的医院,listDoctorPoByArea 是关联患者,并且是有效可聊天")
    @ApiCheck(check = true)
    @RequestMapping(value = "/getHospitalPharmacistList.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse<HospitalDoctorList> getHospitalPharmacistList(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "医院请求接口类") @RequestBody HospitalRequest params) {
        params.doWithSortOrStart();
        log.info("通过名字查询获取医院信息列表和药师信息列表 目前分页条件如下：" + params.toJsonString());
        CommonResponse<HospitalDoctorList> res = new CommonResponse<HospitalDoctorList>();
        HospitalDoctorList hdl = new HospitalDoctorList();
        List<HospitalPo> listHospital = serviceHospitalService.listHospital(params);
        //int countHr = serviceHospitalService.listHospitalCount(params);
        hdl.setHospitalList(listHospital);
        params.setOrder(null);
        params.setSort(null);
        List<PharmacistPo> listPharmacist = serviceHospitalService.listPharmacistPoByArea(params);
        //int countDr = serviceHospitalService.listPharmacistPoByAreaCount(params);
        hdl.setPharmacistList(listPharmacist);
        res.setData(hdl);
        log.debug(res.toJsonString());
        return res;
    }


    @ApiOperation(value = "通过查询获取医生信息列表")
    @ApiCheck(check = true)
    @RequestMapping(value = "/getDoctorPoByAreaList.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ListResponse<DoctorPo> getDoctorPoByAreaList(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "医院请求接口类") @RequestBody HospitalRequest params) {
        params.doWithSortOrStart();
        log.info("目前分页条件如下：" + params.toJsonString());
        ListResponse<DoctorPo> res = new ListResponse<DoctorPo>();
        List<DoctorPo> list = serviceHospitalService.listDoctorPoByArea(params);
        int count = serviceHospitalService.listDoctorPoByAreaCount(params);
        res.setData(list);
        res.setTotal(count);
        log.debug(res.toJsonString());
        return res;
    }

    @ApiOperation(value = "通过查询获取药师信息列表")
    @ApiCheck(check = true)
    @RequestMapping(value = "/getPharmacistPoByAreaList.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ListResponse<PharmacistPo> getPharmacistPoByAreaList(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "医院请求接口类") @RequestBody HospitalRequest params) {
        params.doWithSortOrStart();
        log.info("目前分页条件如下：" + params.toJsonString());
        ListResponse<PharmacistPo> res = new ListResponse<PharmacistPo>();
        List<PharmacistPo> list = serviceHospitalService.listPharmacistPoByArea(params);
        int count = serviceHospitalService.listPharmacistPoByAreaCount(params);
        res.setData(list);
        res.setTotal(count);
        log.debug(res.toJsonString());
        return res;
    }

    @ApiOperation(value = "通过id获取医院信息，包括图片信息及热点科目类")
    @ApiCheck(check = true)
    @RequestMapping(value = "/getHospitalWithHotById.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse<HospitalPo> getHospitalWithHotById(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "医院ID") @RequestParam(value = "id", required = true) Long id) {
        CommonResponse<HospitalPo> res = new CommonResponse<HospitalPo>();
        HospitalPo p = serviceHospitalService.getHospitalWithHotById(id);
        Assert.isTrue(p != null, "不存在该医院");
        res.setData(p);
        log.debug(res.toJsonString());
        return res;
    }


    @ApiOperation(value = "通过查询获取医院信息列表")
    @ApiCheck(check = false)
    @RequestMapping(value = "/getHospitalList.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ListResponse<HospitalPo> getHospitalList(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "医院请求接口类") @RequestBody HospitalRequest params) {
        params.doWithSortOrStart();
        log.info("查询获取医院信息列表,目前分页条件如下：{}", params);
        ListResponse<HospitalPo> res = new ListResponse<HospitalPo>();
        List<HospitalPo> list = serviceHospitalService.listHospital(params);
        int count = serviceHospitalService.listHospitalCount(params);
        res.setData(list);
        res.setTotal(count);
        log.debug(res.toJsonString());
        return res;
    }

    @ApiOperation(value = "通过查询获取医院大分类信息列表")
    @ApiCheck(check = false)
    @RequestMapping(value = "/getDepartmentCatalogList.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ListResponse<DepartmentCatalog> getDepartmentCatalogList(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "医院请求接口类") @RequestBody HospitalRequest params) {
        params.doWithSortOrStart();
        log.info("[filterShow跟patientId 都有值患者需要过滤满足 开通聊天设置并且是有效数据]查询大分类 目前条件如下：{}" , params);
        ListResponse<DepartmentCatalog> res = new ListResponse<DepartmentCatalog>();
        List<DepartmentCatalog> list = serviceHospitalService.listDepartmentCatalog(params);
        int count = serviceHospitalService.listDepartmentCatalogCount(params);
        res.setData(list);
        res.setTotal(count);
        log.debug(res.toJsonString());
        return res;
    }

    @ApiOperation(value = "通过查询获取医院科室小分类信息列表[filterShow:doctor,pharmacist]")
    @ApiCheck(check = false)
    @RequestMapping(value = "/getHospitalDepartmentList.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ListResponse<HospitalDepartment> getHospitalDepartmentList(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "医院请求接口类") @RequestBody HospitalRequest params) {
        params.doWithSortOrStart();
        log.info("[filterShow跟patientId 都有值患者需要过滤满足 开通聊天设置并且是有效数据]查询小分类 目前条件如下：{}" , params);
        ListResponse<HospitalDepartment> res = new ListResponse<HospitalDepartment>();
        List<HospitalDepartment> list = serviceHospitalService.listHospitalDepartment(params);
        int count = serviceHospitalService.listHospitalDepartmentCount(params);
        res.setData(list);
        res.setTotal(count);
        log.debug(res.toJsonString());
        return res;
    }

    @ApiOperation(value = "通过查询获取医院等级信息列表")
    @ApiCheck(check = true)
    @RequestMapping(value = "/getHospitalLevelList.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ListResponse<HospitalLevel> getHospitalLevelList(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "医院请求接口类") @RequestBody HospitalRequest params) {
        params.doWithSortOrStart();
        log.info("目前分页条件如下：" + params.toJsonString());
        ListResponse<HospitalLevel> res = new ListResponse<HospitalLevel>();
        List<HospitalLevel> list = serviceHospitalService.listHospitalLevel(params);
        list.add(0, new HospitalLevel(null, "不限",null, null, null));
        int count = serviceHospitalService.listHospitalLevelCount(params);
        res.setData(list);
        res.setTotal(count + 1);
        log.debug(res.toJsonString());
        return res;
    }
}
