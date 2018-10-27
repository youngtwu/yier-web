package com.yier.platform.service;

import com.alibaba.fastjson.JSONObject;
import com.yier.platform.TestBase;
import com.yier.platform.common.jsonResponse.CommonResponse;
import com.yier.platform.common.jsonResponse.ListResponse;
import com.yier.platform.common.model.Messages;
import com.yier.platform.common.model.MessagesPo;
import com.yier.platform.common.requestParam.MessagesRequest;
import io.swagger.annotations.ApiOperation;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class MessagesServiceTest extends TestBase {
    private static final Logger logger = LoggerFactory.getLogger(MessagesServiceTest.class);

    @Autowired
    private MessageService messageService;

    @ApiOperation(value = "查询公告消息列表")
    @Test
    public void getMessagesList() {
        MessagesRequest params = new MessagesRequest();
        params.doWithSortOrStart();
        logger.info("查询公告消息列表, 请求参数：{}", params);
        ListResponse<MessagesPo> res = new ListResponse<MessagesPo>();
        res.setData(this.messageService.listMessagesPo(params));
        res.setTotal(this.messageService.listMessagesPoCount(params));
        logger.info("查询公告消息列表, 测试结果：{}", res.toJsonString());
    }

    @ApiOperation(value = "根据id查询公告消息")
    @Test
    public void getMessagesById() {
        MessagesRequest params = new MessagesRequest();
        params.setMessageId(6L);
        logger.info("根据id查询公告消息, 请求参数：{}", params);
        CommonResponse<MessagesPo> res = new CommonResponse<MessagesPo>();
        MessagesPo messages = this.messageService.getMessagesById(params);
        res.setData(messages);
        logger.info("根据id查询公告消息, 测试结果：{}", res.toJsonString());
    }

    @ApiOperation(value = "根据id查询公告消息")
    @Test
    public void insertMessages() {
        String json = "{\"typeId\":3,\"otherTypeId\":0,\"title\":\"观谷科技亿尔药师APP，您的智慧药房管家\",\"content\":\"自2008年创立以来，积极进取，不断创新，凭借良好的企业信誉，独特的经营风格及较强的市场开拓能力，取得了一个又一个骄人的业绩。随着公司新产品不断地推向应用，必将有效提高医院的医疗水平、服务水平和管理水平，推进医院整体向着智能数字化、服务人性化的方向发展！\",\"contentUrl\":\"\",\"images\":\"\",\"detail\":4,\"status\":\"0\",\"remarks\":\"单元测试\",\"grade\":null,\"total\":0,\"auditInfos\":null,\"imagesList\":[]}";
        Messages messages = JSONObject.parseObject(json, Messages.class);
        logger.info("新增公告消息, 请求参数：{}", messages);
        CommonResponse<MessagesPo> res = new CommonResponse<MessagesPo>();
        MessagesPo messagesPo = this.messageService.insertMessages(messages, 8L);
        res.setData(messagesPo);
        logger.info("根据id查询公告消息, 测试结果：{}", res.toJsonString());
    }

    @ApiOperation(value = "更新公告消息")
    @Test
    public void updateMessages() {
        String json = "{\"id\":6,\"typeId\":3,\"otherTypeId\":0,\"title\":\"观谷科技亿尔药师APP，您的智慧药房管家\",\"content\":\"自2008年创立以来，积极进取，不断创新，凭借良好的企业信誉，独特的经营风格及较强的市场开拓能力，取得了一个又一个骄人的业绩。随着公司新产品不断地推向应用，必将有效提高医院的医疗水平、服务水平和管理水平，推进医院整体向着智能数字化、服务人性化的方向发展！\",\"contentUrl\":\"\",\"images\":\"\",\"detail\":4,\"status\":\"0\",\"remarks\":\"单元测试\",\"grade\":null,\"total\":0,\"auditInfos\":null,\"imagesList\":[]}";
        Messages messages = JSONObject.parseObject(json, Messages.class);
        logger.info("更新公告消息, 请求参数：{}", messages);
        CommonResponse<MessagesPo> res = new CommonResponse<MessagesPo>();
        MessagesPo messagesPo = this.messageService.updateMessages(messages, 8L);
        res.setData(messagesPo);
        logger.info("更新公告消息, 测试结果：{}", res.toJsonString());
    }

    @ApiOperation(value = "删除公告消息")
    @Test
    public void deleteMessages() {
        String json = "{\"messageId\":7,\"typeId\":3}";
        MessagesRequest params = JSONObject.parseObject(json, MessagesRequest.class);
        logger.info("删除公告消息, 请求参数：{}", params);
        CommonResponse<MessagesPo> res = new CommonResponse<MessagesPo>();
        this.messageService.deleteByPrimaryKey(params);
        MessagesPo messagesPo = new MessagesPo();
        messagesPo.setId(params.getMessageId());
        res.setData(messagesPo);
        logger.info("删除公告消息, 测试结果：{}", res.toJsonString());
    }

    @ApiOperation(value = "启用/停用公告消息")
    @Test
    public void enableOrDisableMessages() {
        String json = "{\"id\":7,\"typeId\":3,\"status\":\"0\"}";
        Messages info = JSONObject.parseObject(json, Messages.class);
        logger.info("启用/停用公告消息, 请求参数：{}", info);
        CommonResponse<MessagesPo> res = new CommonResponse<MessagesPo>();
        this.messageService.enableOrDisableMessages(info);
        MessagesPo messagesPo = new MessagesPo();
        messagesPo.setId(info.getId());
        res.setData(messagesPo);
        logger.info("启用/停用公告消息, 测试结果：{}", res.toJsonString());
    }
}
