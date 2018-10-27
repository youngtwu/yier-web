package com.yier.platform.web;

import com.github.yingzhuo.carnival.restful.security.Logical;
import com.github.yingzhuo.carnival.restful.security.RequiresAuthentication;
import com.github.yingzhuo.carnival.restful.security.RequiresRoles;
import com.yier.platform.annotation.ApiCheck;
import com.yier.platform.common.jsonResponse.CommonResponse;
import com.yier.platform.common.jsonResponse.ListResponse;
import com.yier.platform.common.model.PharmacistTitle;
import com.yier.platform.common.requestParam.PharmacistRequest;
import com.yier.platform.service.PharmacistService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@ApiModel(value = "药师职称相关的请求接口")
@RestController
@RequestMapping("/pharmacistTtile")
@Slf4j
public class PharmacistTitleController {
    private static final Logger logger = LoggerFactory.getLogger(PharmacistTitleController.class);
    @Autowired
    private PharmacistService pharmacistService;

    @ApiOperation(value = "通过条件查询获取药师职称信息列表")
    @ApiCheck(check = false)
    @RequiresAuthentication
    @RequiresRoles(value = {"Super", "Hospital"}, logical = Logical.OR)
    @RequestMapping(value = "/getPharmacistTitleList.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ListResponse<PharmacistTitle> getPharmacistTitleList(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "医生职称请求接口类") @RequestBody PharmacistRequest params) {
        params.doWithSortOrStart();
        logger.info("目前分页条件如下：" + params.toJsonString());
        ListResponse<PharmacistTitle> res = new ListResponse<PharmacistTitle>();
        List<PharmacistTitle> list = this.pharmacistService.listPharmacistTitle(params);
        int count = this.pharmacistService.listPharmacistTitleCount(params);
        res.setData(list);
        res.setTotal(count);
        logger.debug("通过条件查询获取药师职称信息列表：{}", res.toJsonString());
        return res;
    }

    @ApiOperation(value = "通过id查询获取药师职称信息")
    @ApiCheck(check = false)
    @RequiresAuthentication
    @RequiresRoles(value = {"Super", "Hospital"}, logical = Logical.OR)
    @RequestMapping(value = "/getPharmacistTitleById.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse<PharmacistTitle> getDoctorTitleById(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "医生职称id") @RequestParam Long pharmacistTitleId) {
        logger.info("通过id查询获取药师职称信息：{}", pharmacistTitleId);
        CommonResponse<PharmacistTitle> res = new CommonResponse<PharmacistTitle>();
        PharmacistTitle pharmacistTitle = this.pharmacistService.getPharmacistTitleById(pharmacistTitleId);
        res.setData(pharmacistTitle);
        logger.debug("通过id查询获取药师职称信息：{}", res.toJsonString());
        return res;
    }

    @ApiOperation(value = "新增药师职称信息")
    @ApiCheck(check = false)
    @RequiresAuthentication
    @RequiresRoles(value = {"Super", "Hospital"}, logical = Logical.OR)
    @RequestMapping(value = "/insertPharmacistTitle.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse<String> insertPharmacistTitle(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "药师职称请求接口类") @RequestBody PharmacistTitle record) {
        logger.info("新增药师职称信息, 请求参数：{}", record);
        CommonResponse<String> res = new CommonResponse<String>();
        this.pharmacistService.insertPharmacistTitle(record);
        res.setOtherInfo("新增成功");
        res.setStatus(0);
        logger.debug("新增药师职称信息：{}", res.toJsonString());
        return res;
    }

    @ApiOperation(value = "修改药师职称信息")
    @ApiCheck(check = false)
    @RequiresAuthentication
    @RequiresRoles(value = {"Super", "Hospital"}, logical = Logical.OR)
    @RequestMapping(value = "/updateDoctorTitle.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse<PharmacistTitle> updateDoctorTitle(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "药师职称请求接口类") @RequestBody PharmacistTitle record) {
        logger.info("修改药师职称信息, 请求参数：{}", record);
        CommonResponse<PharmacistTitle> res = new CommonResponse<PharmacistTitle>();
        this.pharmacistService.updatePharmacistTitle(record);
        res.setData(record);
        logger.debug("修改药师职称信息：{}", res.toJsonString());
        return res;
    }

    @ApiOperation(value = "停用/启用药师职称信息")
    @ApiCheck(check = false)
    @RequiresAuthentication
    @RequiresRoles(value = {"Super", "Hospital"}, logical = Logical.OR)
    @RequestMapping(value = "/enableOrDisablePharmacistTitle.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse<PharmacistTitle> enableOrDisablePharmacistTitle(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "药师职称请求接口类") @RequestBody PharmacistTitle record) {
        logger.info("停用/启用药师职称信息, 请求参数：{}", record);
        CommonResponse<PharmacistTitle> res = new CommonResponse<PharmacistTitle>();
        this.pharmacistService.enableOrDisablePharmacistTitle(record);
        res.setData(record);
        logger.debug("停用/启用药师职称信息：{}", res.toJsonString());
        return res;
    }
}
