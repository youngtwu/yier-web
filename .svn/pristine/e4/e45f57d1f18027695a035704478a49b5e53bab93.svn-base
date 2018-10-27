package com.yier.platform.controller;

import com.yier.platform.annotation.ApiCheck;
import com.yier.platform.common.jsonResponse.CommonResponse;
import com.yier.platform.common.jsonResponse.ListResponse;
import com.yier.platform.common.model.AttentionUserPo;
import com.yier.platform.common.requestParam.AttentionUserRequest;
import com.yier.platform.service.AttentionUserService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ApiModel(value = "相互关注的请求接口")
@RestController
@RequestMapping("/attention")
@Slf4j
public class AttentionUserController {
    @Autowired
    private AttentionUserService serviceAttentionUserService;

    @ApiOperation(value = "通过条件查询获取关注列表")
    @ApiCheck(check = true)
    @RequestMapping(value = "/getAttentionUserPoList.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ListResponse<AttentionUserPo> getAttentionUserPoList(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "意见反馈请求接口类") @RequestBody AttentionUserRequest params) {
        params.doWithSortOrStart();
        log.info("目前分页条件如下：" + params.toJsonString());
        ListResponse<AttentionUserPo> res = new ListResponse<AttentionUserPo>();
        res.setData(serviceAttentionUserService.listAttentionUserPo(params));
        res.setTotal(serviceAttentionUserService.listAttentionUserPoCount(params));
        log.debug(res.toJsonString());
        return res;
    }

    @ApiOperation(value = "关注用户")
    @ApiCheck(check = true)
    @RequestMapping(value = "/attentionUser.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse<String> attentionUser(HttpServletRequest request, HttpServletResponse response, @RequestBody AttentionUserRequest params) {

        log.info("目前调整处理的数据情况是：" + params.toJsonString());
        CommonResponse<String> res = new CommonResponse<String>();
        String result = serviceAttentionUserService.attention(params);
        res.setData(result);
        log.info(res.toJsonString());
        return res;
    }
}
