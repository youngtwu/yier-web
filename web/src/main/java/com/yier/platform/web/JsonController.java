package com.yier.platform.web;

import com.yier.platform.common.jsonResponse.CommonResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ApiModel(value = "json测试相关的请求接口")
@RestController
@RequestMapping("")
@Slf4j
public class JsonController {


    @ApiOperation(value = "纯粹就是json测试")
    @RequestMapping(value = "/restControllerJson.json", method = {RequestMethod.POST,RequestMethod.GET})
    public CommonResponse<String> restControllerJson(HttpServletRequest request, HttpServletResponse response) {
        log.info("进入---------》纯粹就是json测试");
        CommonResponse<String> result = new CommonResponse<String>();
        result.setData("-------------纯粹就是json测试--------");
        log.info("目前的结果是："+result.toJsonString());
        return result;
    }
}
