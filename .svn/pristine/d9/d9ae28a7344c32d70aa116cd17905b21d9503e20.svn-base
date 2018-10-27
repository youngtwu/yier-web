package com.yier.platform.web;

import com.github.yingzhuo.carnival.restful.security.Logical;
import com.github.yingzhuo.carnival.restful.security.RequiresAuthentication;
import com.github.yingzhuo.carnival.restful.security.RequiresRoles;
import com.yier.platform.annotation.ApiCheck;
import com.yier.platform.common.jsonResponse.CommonResponse;
import com.yier.platform.common.jsonResponse.ListResponse;
import com.yier.platform.common.model.Messages;
import com.yier.platform.common.model.MessagesPo;
import com.yier.platform.common.requestParam.MessagesRequest;
import com.yier.platform.service.MessageService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ApiModel(value = "公告消息相关的请求接口")
@RestController
@RequestMapping("/messagesManager")
@Slf4j
public class MessagesManagerController {
    private static final Logger logger = LoggerFactory.getLogger(MessagesManagerController.class);
    @Autowired
    private MessageService messageService;

    @ApiOperation(value = "查询公告消息列表")
    @ApiCheck(check = false)
    @RequiresAuthentication
    @RequiresRoles(value = {"Super"}, logical = Logical.AND)
    @RequestMapping(value = "/getMessagesPoList.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ListResponse<MessagesPo> getMessagesList(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "公告消息请求接口类") @RequestBody MessagesRequest params) {
        params.doWithSortOrStart();
        logger.info("查询公告消息列表, 请求参数：{}", params);
        ListResponse<MessagesPo> res = new ListResponse<MessagesPo>();
        res.setData(this.messageService.listMessagesPo(params));
        res.setTotal(this.messageService.listMessagesPoCount(params));
        logger.info(res.toJsonString());
        return res;
    }

    @ApiOperation(value = "根据id查询公告消息")
    @ApiCheck(check = false)
    @RequiresAuthentication
    @RequiresRoles(value = {"Super"}, logical = Logical.AND)
    @RequestMapping(value = "/getMessagesById.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse<MessagesPo> getMessagesById(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "公告消息请求接口类") @RequestBody MessagesRequest params) {
        logger.info("根据id查询公告消息, 请求参数：{}", params);
        CommonResponse<MessagesPo> res = new CommonResponse<MessagesPo>();
        MessagesPo messages = this.messageService.getMessagesById(params);
        res.setData(messages);
        logger.info(res.toJsonString());
        return res;
    }

    @ApiOperation(value = "新增公告消息")
    @ApiCheck(check = false)
    @RequiresAuthentication
    @RequiresRoles(value = {"Super"}, logical = Logical.AND)
    @RequestMapping(value = "/insertMessages.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse<MessagesPo> insertMessages(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "公告消息请求接口类") @RequestBody Messages info) {
        logger.info("新增公告消息, 请求参数：{}", info);
        CommonResponse<MessagesPo> res = new CommonResponse<MessagesPo>();
        String userIdStr = request.getHeader("userId");
        Assert.isTrue(StringUtils.isNotBlank(userIdStr), "用户名不能为空！");
        Long userId = Long.parseLong(userIdStr);
        MessagesPo messagesPo = this.messageService.insertMessages(info, userId);
        res.setData(messagesPo);
        logger.info(res.toJsonString());
        return res;
    }

    @ApiOperation(value = "更新公告消息")
    @ApiCheck(check = false)
    @RequiresAuthentication
    @RequiresRoles(value = {"Super"}, logical = Logical.AND)
    @RequestMapping(value = "/updateMessages.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse<MessagesPo> updateMessages(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "公告消息请求接口类") @RequestBody Messages info) {
        logger.info("更新公告消息, 请求参数：{}", info);
        CommonResponse<MessagesPo> res = new CommonResponse<MessagesPo>();
        String userIdStr = request.getHeader("userId");
        Assert.isTrue(StringUtils.isNotBlank(userIdStr), "用户名不能为空！");
        Long userId = Long.parseLong(userIdStr);
        MessagesPo messagesPo = this.messageService.updateMessages(info, userId);
        res.setData(messagesPo);
        logger.info(res.toJsonString());
        return res;
    }

    @ApiOperation(value = "删除公告消息")
    @ApiCheck(check = false)
    @RequiresAuthentication
    @RequiresRoles(value = {"Super"}, logical = Logical.AND)
    @RequestMapping(value = "/deleteMessages.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse<MessagesPo> deleteMessages(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "公告消息请求接口类") @RequestBody MessagesRequest params) {
        logger.info("删除公告消息, 请求参数：{}", params);
        CommonResponse<MessagesPo> res = new CommonResponse<MessagesPo>();
        this.messageService.deleteByPrimaryKey(params);
        MessagesPo messagesPo = new MessagesPo();
        messagesPo.setId(params.getMessageId());
        res.setData(messagesPo);
        logger.info(res.toJsonString());
        return res;
    }

    @ApiOperation(value = "启用/停用公告消息")
    @ApiCheck(check = false)
    @RequiresAuthentication
    @RequiresRoles(value = {"Super"}, logical = Logical.AND)
    @RequestMapping(value = "/enableOrDisableMessages.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse<MessagesPo> enableOrDisableMessages(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "公告消息请求接口类") @RequestBody Messages info) {
        logger.info("启用/停用公告消息, 请求参数：{}", info);
        CommonResponse<MessagesPo> res = new CommonResponse<MessagesPo>();
        this.messageService.enableOrDisableMessages(info);
        MessagesPo messagesPo = new MessagesPo();
        messagesPo.setId(info.getId());
        res.setData(messagesPo);
        logger.info(res.toJsonString());
        return res;
    }
}
