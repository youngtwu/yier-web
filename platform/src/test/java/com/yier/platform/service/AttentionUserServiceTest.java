package com.yier.platform.service;

import com.yier.platform.TestBase;
import com.yier.platform.common.model.AttentionUserPo;
import com.yier.platform.common.requestParam.AttentionUserRequest;
import io.swagger.annotations.ApiModel;
import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.util.List;

@ApiModel(value = "提醒服务接口测试")
public class AttentionUserServiceTest extends TestBase {
    private static final Logger logger = LoggerFactory.getLogger(AttentionUserServiceTest.class);
    @Autowired
    private AttentionUserService serviceAttentionUserService;

    private AttentionUserRequest params = new AttentionUserRequest();

    @Before
    public void setUp() throws Exception {
        params.setStart(0);
        params.setSize(10);
        logger.info("开始测试关注>>");
    }

    @After
    public void tearDown() throws Exception {
        logger.info("结束一个关注测试");
    }

    @Test
    public void attention() {
        params.setTypeId(1L);
        params.setUserId(1L);
        params.setAttentionTypeId(2L);
        params.setAttentionUserId(2L);
        String result = this.serviceAttentionUserService.attention(params);
        Assert.isTrue(StringUtils.isNotBlank(result), "关注有不成功");
    }
}