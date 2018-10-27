package com.yier.platform.controller;

import com.yier.platform.annotation.ApiCheck;
import com.yier.platform.common.jsonResponse.CommonResponse;
import com.yier.platform.common.jsonResponse.ListResponse;
import com.yier.platform.common.model.*;
import com.yier.platform.common.requestParam.ChatRecordRequest;
import com.yier.platform.service.ChatRecordService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@ApiModel(value = "聊天的请求接口")
@RestController
@RequestMapping("/chat")
@Slf4j
public class ChatRecordController {
    @Autowired
    private ChatRecordService serviceChatRecordService;

    @ApiOperation(value = "通过条件获取我的【医生】端的健康咨询列表  对医生的聊天传递样例:{chatSpace:patient46doctor,page:1,size:10,searchKey:拒}")
    @ApiCheck(check = true)
    @RequestMapping(value = "/getChatRecordDoctorPoList.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ListResponse<DoctorPo> getChatRecordDoctorPoList(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "聊天记录请求接口类") @RequestBody ChatRecordRequest params) {
        params.doWithSortOrStart();
        log.info("目前分页条件如下：" + params.toJsonString());
        ListResponse<DoctorPo> res = new ListResponse<DoctorPo>();
        List<DoctorPo> list = serviceChatRecordService.listChatRecordDoctorPo(params);
        int count = serviceChatRecordService.listChatRecordDoctorPoCount(params);
        res.setData(list);
        res.setTotal(count);
        log.debug(res.toJsonString());
        return res;
    }
    @ApiOperation(value = "通过条件获取我的【药师】健康咨询列表  对药师的聊天传递样例:{chatSpace:patient25pharmacist,page:1,size:10,searchKey:拒}")
    @ApiCheck(check = true)
    @RequestMapping(value = "/getChatRecordPharmacistPoList.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ListResponse<PharmacistPo> getChatRecordPharmacistPoList(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "聊天记录请求接口类") @RequestBody ChatRecordRequest params) {
        params.doWithSortOrStart();
        log.info("目前分页条件如下：" + params.toJsonString());
        ListResponse<PharmacistPo> res = new ListResponse<PharmacistPo>();
        List<PharmacistPo> list = serviceChatRecordService.listChatRecordPharmacistPo(params);
        int count = serviceChatRecordService.listChatRecordPharmacistPoCount(params);
        res.setData(list);
        res.setTotal(count);
        log.debug(res.toJsonString());
        return res;
    }

    @ApiOperation(value = "通过条件获取对【患者】我的健康咨询列表  对患者的聊天传递样例:{chatSpace:doctor26|pharmacist1009,page:1,size:10,searchKey:拒}")
    @ApiCheck(check = true)
    @RequestMapping(value = "/getChatRecordPatientPoList.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ListResponse<PatientPo> getChatRecordPatientPoList(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "聊天记录请求接口类") @RequestBody ChatRecordRequest params) {
        params.doWithSortOrStart();
        log.info("目前分页条件如下：" + params.toJsonString());
        ListResponse<PatientPo> res = new ListResponse<PatientPo>();
        List<PatientPo> list = serviceChatRecordService.listChatRecordPatientPo(params);
        int count = serviceChatRecordService.listChatRecordPatientPoCount(params);
        res.setData(list);
        res.setTotal(count);
        log.debug(res.toJsonString());
        return res;
    }



    @ApiOperation(value = "通过条件获取患者我的健康咨询（我的聊天记录列表） 查询彼此聊天记录表：{chatSpace:patient1doctor2,page:1,size:10}")
    @ApiCheck(check = true)
    @RequestMapping(value = "/getChatRecordPoList.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ListResponse<ChatRecordPo> getChatRecordPoList(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "聊天记录请求接口类") @RequestBody ChatRecordRequest params) {
        params.doWithSortOrStart();
        log.info("目前分页条件如下：" + params.toJsonString());
        ListResponse<ChatRecordPo> res = new ListResponse<ChatRecordPo>();
        List<ChatRecordPo> list = serviceChatRecordService.listChatRecordPo(params);
        int count = serviceChatRecordService.listChatRecordPoCount(params);
        res.setData(list);
        res.setTotal(count);
        log.debug(res.toJsonString());
        return res;
    }

    @ApiOperation(value = "上传聊天记录文本或图片")
    @ApiCheck(check = true)
    @RequestMapping(value = "/insertChatRecord.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse<ChatRecord> insertChatRecord(HttpServletRequest request, HttpServletResponse response,
                                                       @ApiParam(value = "发送端Id：1-患者端，2-医生端，3-药师端，4-客服端") @RequestParam(value = "sendTypeId", required = true) Long sendTypeId,
                                                       @ApiParam(value = "发送端的系统DB用户Id") @RequestParam(value = "sendUserId", required = true) Long sendUserId,
                                                       @ApiParam(value = "接收端Id：1-患者端，2-医生端，3-药师端，4-客服端") @RequestParam(value = "receiveTypeId", required = true) Long receiveTypeId,
                                                       @ApiParam(value = "接收端的系统DB用户Id") @RequestParam(value = "receiveUserId", required = true) Long receiveUserId,
                                                       @ApiParam(value = "会话类型信息 如：patient1doctor2pharmacist3user4nurse5 两个拼接的完整会话") @RequestParam(value = "chatSpace", required = true) String chatSpace,
                                                       @ApiParam(value = "会话类型:0表示文本，1表示图片") @RequestParam(value = "chatType", required = true, defaultValue = "0") String chatType,
                                                       @RequestParam(value = "file", required = false) MultipartFile file,
                                                       @RequestParam(value = "textContent", required = false, defaultValue = "") String textContent) {
        ChatRecord record = new ChatRecord();
        record.setSendTypeId(sendTypeId);
        record.setSendUserId(sendUserId);
        record.setReceiveTypeId(receiveTypeId);
        record.setReceiveUserId(receiveUserId);
        record.setTextContent(textContent);
        record.setChatSpace(chatSpace);
        record.setChatType(chatType);
        log.info("目前插入的数据情况是:{}",record);
        CommonResponse<ChatRecord> res = new CommonResponse<ChatRecord>();
        res.setData(serviceChatRecordService.insertChatRecord(record, file));
        log.info(res.toJsonString());
        return res;
    }
}
