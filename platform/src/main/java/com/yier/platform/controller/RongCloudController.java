package com.yier.platform.controller;

import com.yier.platform.annotation.ApiCheck;
import com.yier.platform.common.jsonResponse.CommonResponse;
import com.yier.platform.common.rongYun.RongYunTokenResult;
import com.yier.platform.service.RongCloudService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/rongCloud")
public class RongCloudController {
    private static final Logger logger = LoggerFactory.getLogger(RongCloudController.class);
    @Autowired
    private RongCloudService serviceRongCloudService;

    /**
     * 获取 Token 方法
     *
     * @param request
     * @param response
     * @param typeId      :patient,doctor,other
     * @param id          用户 Id，支持大小写英文字母、数字、部分特殊符号 + | = - _ 的组合方式，最大长度 64 字节。是用户在 App 中的唯一标识码，必须保证在同一个 App 内不重复，重复的用户 Id 将被当作是同一用户。（必传）
     * @param displayName 用户名称，最大长度 128 字节。用来在 Push 推送时显示用户的名称。（必传）
     * @param portraitUri 用户头像 URI，最大长度 1024 字节。（必传）
     * @return
     */
    @ApiOperation(value = "注册融云并获取对应的token")
    @ApiCheck(check = true)
    @RequestMapping(value = "/user/getToken.json", method = RequestMethod.GET)
    public CommonResponse<RongYunTokenResult> getToken(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "typeId", required = true) Long typeId,
                                                       @RequestParam(value = "id", required = true) Long id,
                                                       @RequestParam(value = "displayName", required = true) String displayName, @RequestParam(value = "portraitUri", required = true) String portraitUri) {
        CommonResponse<RongYunTokenResult> res = new CommonResponse<RongYunTokenResult>();
        res.setData(serviceRongCloudService.getToken(typeId, id, displayName, portraitUri));
        return res;
    }


    @ApiOperation(value = "根据融云的用户Id获取对应的用户名")
    @RequestMapping(value = "/getNameByUserId.json", method = RequestMethod.GET)
    public CommonResponse<String> getNameByUserId(HttpServletRequest request, HttpServletResponse response,
                                                  @RequestParam(value = "userId", required = true) String userId) {
        CommonResponse<String> res = new CommonResponse<String>();
        res.setData(serviceRongCloudService.getUserInfoByUserId(userId));
        return res;
    }

}
