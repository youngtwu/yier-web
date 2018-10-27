package com.yier.platform.web;


import com.github.yingzhuo.carnival.restful.security.Logical;
import com.github.yingzhuo.carnival.restful.security.RequiresAuthentication;
import com.github.yingzhuo.carnival.restful.security.RequiresRoles;
import com.yier.platform.annotation.ApiCheck;
import com.yier.platform.common.jsonResponse.CommonResponse;
import com.yier.platform.common.jsonResponse.ListResponse;
import com.yier.platform.common.model.Patient;
import com.yier.platform.common.requestParam.PatientRequest;
import com.yier.platform.service.PatientService;
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

@ApiModel(value = "患者用户请求接口")
@RestController
@RequestMapping("/patientManager")
@Slf4j
public class PatientManagerController {
    private static final Logger logger = LoggerFactory.getLogger(PatientManagerController.class);

    @Autowired
    private PatientService servicePatientService;

    @ApiOperation(value = "获取患者信息列表")
    @ApiCheck(check = false)
    @RequiresAuthentication
    @RequiresRoles(value = {"Super", "Hospital"}, logical = Logical.OR)
    @RequestMapping(value = "/getPatientList.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ListResponse<Patient> getPatientList(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "患者用户请求接口类") @RequestBody PatientRequest params) {
        params.doWithSortOrStart();
        logger.info("获取患者信息列表, 请求参数：{}", params.toJsonString());
        ListResponse<Patient> res = new ListResponse<Patient>();
        List<Patient> patientList = this.servicePatientService.listPatient(params);
        int count = this.servicePatientService.listPatientCount(params);
        res.setData(patientList);
        res.setTotal(count);
        logger.debug("获取患者信息列表, 返回结果：{}", res.toJsonString());
        return res;
    }

    @ApiOperation(value = "根据id获取患者信息")
    @ApiCheck(check = false)
    @RequiresAuthentication
    @RequiresRoles(value = {"Super", "Hospital"}, logical = Logical.OR)
    @RequestMapping(value = "/getPatientById.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse<Patient> getPatientById(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "患者用户请求接口类") @RequestParam Long id) {
        logger.info("根据id获取患者信息, 请求参数：{}", id);
        CommonResponse<Patient> res = new CommonResponse<Patient>();
        Patient patient = this.servicePatientService.selectByPrimaryKey(id);
        res.setData(patient);
        logger.debug("根据id获取患者信息, 返回结果：{}", res.toJsonString());
        return res;
    }

}
