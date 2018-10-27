package com.yier.platform.web;

import com.github.yingzhuo.carnival.restful.security.Logical;
import com.github.yingzhuo.carnival.restful.security.RequiresAuthentication;
import com.github.yingzhuo.carnival.restful.security.RequiresRoles;
import com.yier.platform.annotation.ApiCheck;
import com.yier.platform.common.jsonResponse.CommonResponse;
import com.yier.platform.common.jsonResponse.ListResponse;
import com.yier.platform.common.model.*;
import com.yier.platform.common.requestParam.HospitalRequest;
import com.yier.platform.service.HospitalService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.MutableTriple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@ApiModel(value = "医院信息管理相关的请求接口")
@RestController
@RequestMapping("/hospitalManager")
@Slf4j
public class HospitalManagerController {
    private static final Logger logger = LoggerFactory.getLogger(HospitalManagerController.class);

    @Autowired
    private HospitalService serviceHospitalService;

    @ApiOperation(value = "获取医院信息列表 {\"searchKey\":\"浙江省中医院\",\"stauts\":\"0\"} status状态传对应操作的数值或者字母（0正常有效 审核通过 1删除 2未提交 点击保存 4无效 5未通过 A审核中 未审核  B未复审 复审中 未通过的提交）")
    @ApiCheck(check = false)
    @RequiresAuthentication
    @RequiresRoles(value = {"Super","Hospital"}, logical = Logical.OR)
    @RequestMapping(value = "/hospital/getHospitalList.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ListResponse<HospitalPo> getHospitalList(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "医院请求接口类") @RequestBody HospitalRequest params) {
        params.doWithSortOrStart();
        logger.info("获取医院信息列表, 请求参数：{}", params);
        ListResponse<HospitalPo> hospitalPoListResponse = new ListResponse<HospitalPo>();
        List<HospitalPo> hospitalPoList = serviceHospitalService.listHospital(params);
        int count = serviceHospitalService.listHospitalCount(params);
        hospitalPoListResponse.setData(hospitalPoList);
        hospitalPoListResponse.setTotal(count);
        logger.debug(hospitalPoListResponse.toJsonString());
        return hospitalPoListResponse;
    }

    @ApiOperation(value = "通过id获取医院信息，包括图片信息及热点科目类， 请求参数放在问号之后：/hospital/getHospitalById.json?id=11")
    @ApiCheck(check = false)
    @RequiresAuthentication
    @RequiresRoles(value = {"Super","Hospital"}, logical = Logical.OR)
    @RequestMapping(value = "/hospital/getHospitalById.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse<MutableTriple<HospitalPo, AuditInfo, List<AuditInfo>>> getHospitalById(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "医院ID") @RequestParam(value = "id", defaultValue = "0", required = true) Long id) {
        CommonResponse<MutableTriple<HospitalPo, AuditInfo, List<AuditInfo>>> hospitalPoResponse = new CommonResponse<MutableTriple<HospitalPo, AuditInfo, List<AuditInfo>>>();
        MutableTriple<HospitalPo, AuditInfo, List<AuditInfo>> mutableTriple = serviceHospitalService.getHospitalById(id);
        Assert.isTrue(mutableTriple != null, "不存在该医院！");
        hospitalPoResponse.setData(mutableTriple);
        logger.debug(hospitalPoResponse.toJsonString());
        return hospitalPoResponse;
    }

    @ApiOperation(value = "新增医院信息, 传递两个参数，医院实体类hospital和图片数组files，状态（0正常有效 审核通过 1删除 2未提交 点击保存 4无效 5未通过 A审核中 未审核  B未复审 复审中 未通过的提交）")
    @ApiCheck(check = false)
    @RequiresAuthentication
    @RequiresRoles(value = {"Super","Hospital"}, logical = Logical.OR)
    @RequestMapping(value = "/hospital/insertHospital.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse<HospitalPo> insertHospital(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "医院实体类") Hospital hospital, @ApiParam(value = "上传图片的数组") @RequestParam(value = "files", required = false) MultipartFile[] files) {
        logger.info("[新增医院信息 请求参数：hospital==={}, files==={}", hospital, files);
        String userIdStr = request.getHeader("userId");
        Assert.isTrue(StringUtils.isNotBlank(userIdStr), "用户名不能为空！");
        Long userId = Long.parseLong(userIdStr);
        HospitalPo hospitalPo = this.serviceHospitalService.insertHospital(hospital, files, userId);
        CommonResponse<HospitalPo> result = new CommonResponse<HospitalPo>();
        result.setData(hospitalPo);
        return result;
    }

    @ApiOperation(value = "更新医院信息, 状态（0正常有效 审核通过 1删除 2未提交 点击保存 4无效 5未通过 A审核中 未审核  B未复审 复审中 未通过的提交）")
    @ApiCheck(check = false)
    @RequiresAuthentication
    @RequiresRoles(value = {"Super","Hospital"}, logical = Logical.OR)
    @RequestMapping(value = "/hospital/updateHospital.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse<HospitalPo> updateHospital(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "医院实体类") Hospital hospital, @ApiParam(value = "上传图片的数组") @RequestParam(value = "files", required = false) MultipartFile[] files) {
        logger.info("[更新医院信息 请求参数：hospital==={}, files==={}", hospital, files);
        String userIdStr = request.getHeader("userId");
        Assert.isTrue(StringUtils.isNotBlank(userIdStr), "用户名不能为空！");
        Long userId = Long.parseLong(userIdStr);
        HospitalPo hospitalPo = this.serviceHospitalService.updateHospital(hospital, files, userId);
        CommonResponse<HospitalPo> result = new CommonResponse<HospitalPo>();
        result.setData(hospitalPo);
        return result;
    }

    @ApiOperation(value = "删除医院信息 逻辑删除, 状态（0正常有效 审核通过 1删除 2未提交 点击保存 4无效 5未通过 A审核中 未审核  B未复审 复审中 未通过的提交）传值方式/hospital/deleteHospital.json?id=11")
    @ApiCheck(check = false)
    @RequiresAuthentication
    @RequiresRoles(value = {"Super","Hospital"}, logical = Logical.OR)
    @RequestMapping(value = "/hospital/deleteHospital.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse<HospitalPo> deleteHospital(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "医院ID") @RequestParam(value = "id", defaultValue = "0", required = true) Long id) {
        logger.info("[删除医院信息 请求参数：id==={}", id);
        CommonResponse<HospitalPo> result = new CommonResponse<HospitalPo>();
        Hospital hospital = this.serviceHospitalService.deleteHospital(id);
        result.setData(new HospitalPo(hospital));
        return result;
    }

    @ApiOperation(value = "根据医院id停用/启用医院, 状态（0正常有效 审核通过 1删除 2未提交 点击保存 4无效 5未通过 A审核中 未审核  B未复审 复审中 未通过的提交）")
    @ApiCheck(check = false)
    @RequiresAuthentication
    @RequiresRoles(value = {"Super","Hospital"}, logical = Logical.OR)
    @RequestMapping(value = "/hospital/enableOrDisabelHospital.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse<HospitalPo> enableOrDisabelHospital(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "根据医院id停用/启用医院接口类") @RequestBody Hospital record) {
        logger.info("[根据医院id停用/启用医院 参数如下：record==={}", record);
        CommonResponse<HospitalPo> result = new CommonResponse<HospitalPo>();
        Hospital hospital = this.serviceHospitalService.enableOrDisabelHospital(record);
        result.setData(new HospitalPo(hospital));
        return result;
    }

    @ApiOperation(value = "通过查询获取医院科室大分类信息列表")
    @ApiCheck(check = false)
    @RequiresAuthentication
    @RequiresRoles(value = {"Super"}, logical = Logical.AND)
    @RequestMapping(value = "/departmentCatalog/getDepartmentCatalogList.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ListResponse<DepartmentCatalog> getDepartmentCatalogList(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "医院请求接口类") @RequestBody HospitalRequest params) {
        params.doWithSortOrStart();
        logger.info("[filterShow跟patientId 都有值患者需要过滤满足 开通聊天设置并且是有效数据]查询大分类 目前条件如下：{}", params);
        ListResponse<DepartmentCatalog> departmentCatalogResponse = new ListResponse<DepartmentCatalog>();
        List<DepartmentCatalog> departmentCatalogList = this.serviceHospitalService.listDepartmentCatalog(params);
        int count = this.serviceHospitalService.listDepartmentCatalogCount(params);
        departmentCatalogResponse.setData(departmentCatalogList);
        departmentCatalogResponse.setTotal(count);
        logger.debug(departmentCatalogResponse.toJsonString());
        return departmentCatalogResponse;
    }

    @ApiOperation(value = "新增大科室, 状态（0正常 有效 1删除 3无效）")
    @ApiCheck(check = false)
    @RequiresAuthentication
    @RequiresRoles(value = {"Super"}, logical = Logical.AND)
    @RequestMapping(value = "/departmentCatalog/insertDepartmentCatalog.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse<DepartmentCatalog> insertDepartmentCatalog(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "新增大科室接口类") @RequestBody DepartmentCatalog departmentCatalog) {
        logger.info("[新增大科室 参数如下：departmentCatalog==={}", departmentCatalog);
        CommonResponse<DepartmentCatalog> res = new CommonResponse<DepartmentCatalog>();
        DepartmentCatalog dCatalog = this.serviceHospitalService.insertDepartmentCatalog(departmentCatalog);
        res.setData(dCatalog);
        return res;
    }

    @ApiOperation(value = "根据id更新大科室, 状态（0正常 有效 1删除 3无效）")
    @ApiCheck(check = false)
    @RequiresAuthentication
    @RequiresRoles(value = {"Super"}, logical = Logical.AND)
    @RequestMapping(value = "/departmentCatalog/updateDepartmentCatalog.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse<DepartmentCatalog> updateDepartmentCatalog(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "根据id更新大科室接口类") @RequestBody DepartmentCatalog departmentCatalog) {
        logger.info("[根据id更新大科室 参数如下：departmentCatalog==={}", departmentCatalog);
        CommonResponse<DepartmentCatalog> res = new CommonResponse<DepartmentCatalog>();
        DepartmentCatalog dCatalog = this.serviceHospitalService.updateDepartmentCatalog(departmentCatalog);
        res.setData(dCatalog);
        return res;
    }

    @ApiOperation(value = "根据id删除大科室, 状态（0正常 有效 1删除 3无效）传值方式：/departmentCatalog/deleteDepartmentCatalogById.json?id=1")
    @ApiCheck(check = false)
    @RequiresAuthentication
    @RequiresRoles(value = {"Super"}, logical = Logical.AND)
    @RequestMapping(value = "/departmentCatalog/deleteDepartmentCatalogById.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse<Long> deleteDepartmentCatalogById(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "根据id删除大科室接口类") @RequestParam Long id) {
        logger.info("[根据id删除大科室 参数如下：id==={}", id);
        CommonResponse<Long> res = new CommonResponse<Long>();
        Long delId = this.serviceHospitalService.deleteDepartmentCatalogById(id);
        res.setData(delId);
        res.setOtherInfo("删除大科室");
        return res;
    }

    @ApiOperation(value = "启用/停用大科室, 状态（0正常 有效 1删除 3无效）")
    @ApiCheck(check = false)
    @RequiresAuthentication
    @RequiresRoles(value = {"Super"}, logical = Logical.AND)
    @RequestMapping(value = "/departmentCatalog/enableOrDisableDepartmentCatalog.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse<DepartmentCatalog> enableOrDisableDepartmentCatalog(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "启用/停用大科室接口类") @RequestBody DepartmentCatalog record) {
        logger.info("[启用/停用大科室 参数如下：record==={}", record);
        CommonResponse<DepartmentCatalog> res = new CommonResponse<DepartmentCatalog>();
        DepartmentCatalog departmentCatalog = this.serviceHospitalService.enableOrDisableDepartmentCatalog(record);
        res.setData(departmentCatalog);
        return res;
    }

    @ApiOperation(value = "通过查询获取医院科室小分类信息列表[filterShow:doctor,pharmacist]")
    @ApiCheck(check = false)
    @RequiresAuthentication
    @RequiresRoles(value = {"Super"}, logical = Logical.AND)
    @RequestMapping(value = "/hospitalDepartment/getHospitalDepartmentList.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ListResponse<HospitalDepartment> getHospitalDepartmentList(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "医院请求接口类") @RequestBody HospitalRequest params) {
        params.doWithSortOrStart();
        logger.info("[filterShow跟patientId 都有值患者需要过滤满足 开通聊天设置并且是有效数据]查询小分类 目前条件如下：{}", params);
        ListResponse<HospitalDepartment> hospitalDepartmentResponse = new ListResponse<HospitalDepartment>();
        List<HospitalDepartment> hospitalDepartmentList = this.serviceHospitalService.listHospitalDepartment(params);
        int count = this.serviceHospitalService.listHospitalDepartmentCount(params);
        hospitalDepartmentResponse.setData(hospitalDepartmentList);
        hospitalDepartmentResponse.setTotal(count);
        logger.debug(hospitalDepartmentResponse.toJsonString());
        return hospitalDepartmentResponse;
    }

    @ApiOperation(value = "根据医院id和大科室新增小科室, 状态（0正常 有效 1删除 3无效）")
    @ApiCheck(check = false)
    @RequiresAuthentication
    @RequiresRoles(value = {"Super"}, logical = Logical.AND)
    @RequestMapping(value = "/hospitalDepartment/insertHospitalDepartment.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse<HospitalDepartment> insertHospitalDepartment(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "新增小科室接口类") @RequestBody HospitalDepartment hospitalDepartment) {
        logger.info("根据医院id和大科室新增小科室, 参数如下：hospitalDepartment==={}", hospitalDepartment);
        CommonResponse<HospitalDepartment> result = new CommonResponse<HospitalDepartment>();
        HospitalDepartment hDepartment = this.serviceHospitalService.insertHospitalDepartment(hospitalDepartment);
        result.setData(hDepartment);
        return result;
    }

    @ApiOperation(value = "根据医院id和大科室更新小科室, 状态（0正常 有效 1删除 3无效）")
    @ApiCheck(check = false)
    @RequiresAuthentication
    @RequiresRoles(value = {"Super"}, logical = Logical.AND)
    @RequestMapping(value = "/hospitalDepartment/updateHospitalDepartment.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse<HospitalDepartment> updateHospitalDepartment(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "根据id更新小科室接口类") @RequestBody HospitalDepartment hospitalDepartment) {
        logger.info("根据医院id和大科室更新小科室, 参数如下：hospitalDepartment==={}", hospitalDepartment);
        CommonResponse<HospitalDepartment> res = new CommonResponse<HospitalDepartment>();
        HospitalDepartment hDepartment = this.serviceHospitalService.updateHospitalDepartment(hospitalDepartment);
        res.setData(hDepartment);
        return res;
    }

    @ApiOperation(value = "根据id删除小科室, 状态（0正常 有效 1删除 3无效）传值方式：/hospitalDepartment/deleteHospitalDepartmentById.json?id=1")
    @ApiCheck(check = false)
    @RequiresAuthentication
    @RequiresRoles(value = {"Super"}, logical = Logical.AND)
    @RequestMapping(value = "/hospitalDepartment/deleteHospitalDepartmentById.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse<Long> deleteHospitalDepartmentById(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "根据id删除小科室接口类") @RequestParam Long id) {
        logger.info("[根据id删除小科室, 参数如下：id==={}", id);
        CommonResponse<Long> res = new CommonResponse<Long>();
        Long delId = this.serviceHospitalService.deleteHospitalDepartmentById(id);
        res.setData(delId);
        res.setOtherInfo("删除小科室");
        return res;
    }

    @ApiOperation(value = "启用/停用小科室, 状态（0正常 有效 1删除 3无效）")
    @ApiCheck(check = false)
    @RequiresAuthentication
    @RequiresRoles(value = {"Super"}, logical = Logical.AND)
    @RequestMapping(value = "/hospitalDepartment/enableOrDisableHospitalDepartment.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse<HospitalDepartment> enableOrDisableHospitalDepartment(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "启用/停用小科室接口类") @RequestBody HospitalDepartment record) {
        logger.info("[启用/停用小科室, 参数如下：record==={}", record);
        CommonResponse<HospitalDepartment> result = new CommonResponse<HospitalDepartment>();
        HospitalDepartment hospitalDepartment = this.serviceHospitalService.enableOrDisableHospitalDepartment(record);
        result.setData(hospitalDepartment);
        return result;
    }

}
