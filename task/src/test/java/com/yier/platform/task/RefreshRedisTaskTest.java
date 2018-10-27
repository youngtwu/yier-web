package com.yier.platform.task;

import com.yier.platform.TestBase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class RefreshRedisTaskTest extends TestBase {
    private static final Logger logger = LoggerFactory.getLogger(RefreshRedisTaskTest.class);
    @Autowired
    private RefreshRedisTask refreshRedisTask;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void updateRedisCache() {
        refreshRedisTask.updateRedisCache();
    }
}