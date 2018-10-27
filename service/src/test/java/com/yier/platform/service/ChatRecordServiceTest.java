package com.yier.platform.service;

import com.yier.platform.TestBase;
import com.yier.platform.common.model.ChatRecord;
import com.yier.platform.common.requestParam.ChatRecordRequest;
import io.swagger.annotations.ApiModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@ApiModel(value = "聊天记录接口测试")
public class ChatRecordServiceTest extends TestBase {
    private static final Logger logger = LoggerFactory.getLogger(ChatRecordServiceTest.class);
    @Autowired
    private ChatRecordService serviceChatRecordService;

    private ChatRecordRequest params = new ChatRecordRequest();

    @Before
    public void setUp() throws Exception {
        params.setStart(0);
        params.setSize(10);
        logger.info("开始测试>>");
    }

    @After
    public void tearDown() throws Exception {
        logger.info("结束一个测试");
    }

    @Test
    public void listChatRecordPo() {
        serviceChatRecordService.listChatRecordPo(params);
    }

    @Test
    public void listChatRecordPoCount() {
        serviceChatRecordService.listChatRecordPoCount(params);
    }

    @Test
    public void listChatRecordDoctorPo() {
        serviceChatRecordService.listChatRecordDoctorPo(params);
    }

    @Test
    public void listChatRecordDoctorPoCount() {
        serviceChatRecordService.listChatRecordDoctorPoCount(params);
    }

    @Test
    public void insertChatRecord() {
        ChatRecord record = new ChatRecord(1L, 1L, 2L, 2L, "patient1doctor2", "0", "测试内容", "");
        try {
            File file = new File("E:\\study\\tempImage\\link.jpg");
            MultipartFile mfile = new MockMultipartFile("link.jpg", "link.jpg", "jpg", new FileInputStream(file));
            serviceChatRecordService.insertChatRecord(record, mfile);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }
}