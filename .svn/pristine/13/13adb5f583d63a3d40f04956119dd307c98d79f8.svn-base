package com.yier.platform.controller;

import com.yier.platform.annotation.ApiCheck;
import com.yier.platform.common.jsonResponse.CommonResponse;
import com.yier.platform.common.model.Pharmacy;
import com.yier.platform.service.PharmacyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/pharmacy")
public class PharmacyController {
    private static final Logger logger = LoggerFactory.getLogger(PharmacyController.class);
    @Autowired
    private PharmacyService servicePharmacyService;

    @ApiCheck(check = false)
    @RequestMapping(value = "/selectById.json", method = {RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse<Pharmacy> selectById(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "id", required = true) Long id) {
        CommonResponse<Pharmacy> res = new CommonResponse<Pharmacy>();
        Pharmacy p = servicePharmacyService.selectByPrimaryKey(id);
        res.setData(p);
        logger.info(res.toJsonString());
        return res;
    }


}
