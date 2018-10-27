package com.yier.platform.controller;

import com.yier.platform.annotation.ApiCheck;
import com.yier.platform.common.jsonResponse.CommonResponse;
import com.yier.platform.common.jsonResponse.ListResponse;
import com.yier.platform.common.model.MessageUser;
import com.yier.platform.common.model.MessagesPerson;
import com.yier.platform.common.model.MessagesPersonPo;
import com.yier.platform.common.model.MessagesPo;
import com.yier.platform.common.requestParam.MessagesRequest;
import com.yier.platform.service.MessageService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.MutableTriple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@ApiModel(value = "公告消息相关的请求接口")
@RestController
@RequestMapping("/message")
@Slf4j
public class MessageController {
    @Autowired
    private MessageService serviceMessageService;

    @ApiOperation(value = "通过条件查询获取未读消息情况")
    @ApiCheck(check = true)
    @RequestMapping(value = "/getNoReadMessages.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse<MutableTriple<String, MessagesPersonPo, MessagesPo>> getNoReadMessages(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "意见反馈请求接口类") @RequestBody MessagesRequest params) {
        params.setSize(1);
        log.info("获取未读消息情况 条件如下：{}" , params);
        CommonResponse<MutableTriple<String, MessagesPersonPo, MessagesPo>> res = new CommonResponse<MutableTriple<String, MessagesPersonPo, MessagesPo>>();
        MutableTriple<String, MessagesPersonPo, MessagesPo> mutable = new MutableTriple<String, MessagesPersonPo, MessagesPo>();
        mutable.left = "描述信息：middle表示个人互动信息 right表示公告通知信息";
        int middle = serviceMessageService.listMessagesPersonPoCount(params);
        int right = serviceMessageService.listMessagesPoCount(params);
        List<MessagesPo> listMessagePo = serviceMessageService.listMessagesPoByRequest(params);
        MessagesPo gongGao ;
        if(listMessagePo==null||listMessagePo.size()==0){
            gongGao = new MessagesPo();
            gongGao.setTotal(right);
        }
        else{
            gongGao = listMessagePo.get(0);
            gongGao.setTotal(right);
        }
        List<MessagesPersonPo> messagesPersonPoList = serviceMessageService.listMessagesPersonsPo(params);
        MessagesPersonPo personPo ;
        if(messagesPersonPoList==null||messagesPersonPoList.size()==0){
            personPo = new MessagesPersonPo();
            personPo.setTotal(middle);
        }
        else{
            personPo = messagesPersonPoList.get(0);
            personPo.setTotal(middle);
        }
        mutable.middle = personPo;
        mutable.right = gongGao;
        res.setData(mutable);
        log.info(res.toJsonString());
        return res;
    }

    @ApiOperation(value = "通过条件查询获取所能看到的个人消息类别列表")
    @ApiCheck(check = true)
    @RequestMapping(value = "/getMessagesPersonPoList.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ListResponse<MessagesPersonPo> getMessagesPersonPoList(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "意见反馈请求接口类") @RequestBody MessagesRequest params) {
        String typeId = request.getHeader("typeId");
        if(StringUtils.isBlank(typeId)){
            typeId = request.getParameter("typeId");//端口
        }
        log.info("目前的端口id是："+ typeId);
        long typeIdLong = Long.parseLong(typeId);
        params.setTypeId(typeIdLong);
        params.doWithSortOrStart();
        log.info("目前分页条件如下：" + params.toJsonString());
        ListResponse<MessagesPersonPo> res = new ListResponse<MessagesPersonPo>();
        res.setData(serviceMessageService.listMessagesPersonsPo(params));
        res.setTotal(serviceMessageService.listMessagesPersonPoCount(params));
        log.info(res.toJsonString());
        return res;
    }


    @ApiOperation(value = "调整个人用户的信息显示")
    @ApiCheck(check = true)
    @RequestMapping(value = "/adjustMessagePersonUser.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse<MessagesPerson> adjustMessageUser(HttpServletRequest request, HttpServletResponse response, @RequestBody MessagesPerson record) {
        log.info("目前调整处理的数据情况是：" + record.toJsonString());
        CommonResponse<MessagesPerson> res = new CommonResponse<MessagesPerson>();
        int result = serviceMessageService.adjustMessagesPerson(record);
        record.setRemarks("影响到的条目数是:" + result);
        res.setData(record);
        log.info(res.toJsonString());
        return res;
    }

    @ApiOperation(value = "通过条件查询获取所能看到的公告类别列表")
    @ApiCheck(check = true)
    @RequestMapping(value = "/getMessagesPoList.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ListResponse<MessagesPo> getMessagesPoList(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "意见反馈请求接口类") @RequestBody MessagesRequest params) {
        params.doWithSortOrStart();
        log.info("目前查询获取所能看到的公告类别列表分页条件如下：" + params.toJsonString());
        ListResponse<MessagesPo> res = new ListResponse<MessagesPo>();
        res.setData(serviceMessageService.listMessagesPo(params));
        res.setTotal(serviceMessageService.listMessagesPoCount(params));
        log.info(res.toJsonString());
        return res;
    }

    @ApiOperation(value = "调整个人用户的公告显示")
    @ApiCheck(check = true)
    @RequestMapping(value = "/adjustMessageUser.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse<MessageUser> adjustMessageUser(HttpServletRequest request, HttpServletResponse response, @RequestBody MessageUser record) {
        log.info("目前调整处理的数据情况是：" + record.toJsonString());
        CommonResponse<MessageUser> res = new CommonResponse<MessageUser>();
        int result = serviceMessageService.adjustMessageUser(record);
        record.setRemarks("影响到的条目数是:" + result);
        res.setData(record);
        log.info(res.toJsonString());
        return res;
    }

}
