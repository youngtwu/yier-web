package com.yier.platform.service;

import com.yier.platform.TestBase;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

import java.io.File;
import java.io.InputStream;

@ActiveProfiles({"dev"}) //获取服务端连接资源
public class UploaderTest extends TestBase {
    @Autowired
    private Uploader uploader;
    private static final Logger logger = LoggerFactory.getLogger(UploaderTest.class);
    @Test
    public void testUploaderFile() throws Exception {
        File file = new File("E:\\study\\tempImage\\link.jpg");//image.jpg 可以上传app.apk 文件类型，fastFile 只是文件管理工具
        InputStream inputStream = FileUtils.openInputStream(file);
        long size = FileUtils.sizeOf(file);
        String url = uploader.upload(inputStream, size, "jpg");
        logger.info("上传图片对应的url为---------------->:"+url);
    }
}
