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
    @Autowired
    private AliyunOssService aliyunOssService;
    private static final Logger logger = LoggerFactory.getLogger(UploaderTest.class);
    @Test
    public void testUploaderFile() throws Exception {
        File file = new File("E:\\projectOut\\banner\\lunch\\p2.jpg");//image.jpg 可以上传app.apk 文件类型，fastFile 只是文件管理工具
        InputStream inputStream = FileUtils.openInputStream(file);
        long size = FileUtils.sizeOf(file);
        String url = uploader.upload(inputStream, size, "png");
        logger.info("上传图片对应的url为---------------->:"+url);
    }
    @Test
    //阿里云目前有个问题 转换成 InputStream inputStrea 反而无法有效上传，待之后验证？？？？
    public void testUploaderOSS() throws Exception {
        File file = new File("E:\\projectOut\\banner\\lunch\\patientLunchNew2.jpg");//image.jpg 可以上传app.apk 文件类型，fastFile 只是文件管理工具

        logger.info("开始上传图片到库里面去：");

        String uploadKey = "patientLunchNew2";
        aliyunOssService.deleteKey(uploadKey);
        aliyunOssService.uploadFile(uploadKey,file);

        aliyunOssService.viewBucket();
        logger.info("{} 上传图片的URL是:{}",uploadKey,aliyunOssService.getUrl(uploadKey,3600* 24*365*10));//10年//30));//
//        logger.info("p2.jpg 上传图片的URL是："+aliyunOssService.getUrl("p2.jpg"));

    }

    //svn commit test1111222233333

/*
    @Test
    public void testUploaderOSSd10() throws Exception {
        File file = new File("D:\\temp\\banner\\ios\\banneriosdoctor1.png");//image.jpg 可以上传app.apk 文件类型，fastFile 只是文件管理工具

        logger.info("开始上传图片到库里面去：");

        String uploadKey = "banneriosdoctor1";
        aliyunOssService.deleteKey(uploadKey);
        aliyunOssService.uploadFile(uploadKey,file);

        aliyunOssService.viewBucket();
        logger.info("{} 1上传图片的URL是:{}",uploadKey,aliyunOssService.getUrl(uploadKey,3600* 24*365*10));//10年//30));//
//        logger.info("p2.jpg 上传图片的URL是："+aliyunOssService.getUrl("p2.jpg"));

    }
    @Test
    public void testUploaderOSSd20() throws Exception {
        File file = new File("D:\\temp\\banner\\ios\\banneriosdoctor2.png");//image.jpg 可以上传app.apk 文件类型，fastFile 只是文件管理工具

        logger.info("开始上传图片到库里面去：");

        String uploadKey = "banneriosdoctor12";
        aliyunOssService.deleteKey(uploadKey);
        aliyunOssService.uploadFile(uploadKey,file);

        aliyunOssService.viewBucket();
        logger.info("{} 1上传图片的URL是:{}",uploadKey,aliyunOssService.getUrl(uploadKey,3600* 24*365*10));//10年//30));//
//        logger.info("p2.jpg 上传图片的URL是："+aliyunOssService.getUrl("p2.jpg"));

    }
    @Test
    public void testUploaderOSSd30() throws Exception {
        File file = new File("D:\\temp\\banner\\ios\\banneriosdoctor3.png");//image.jpg 可以上传app.apk 文件类型，fastFile 只是文件管理工具

        logger.info("开始上传图片到库里面去：");

        String uploadKey = "banneriosdoctor3";
        aliyunOssService.deleteKey(uploadKey);
        aliyunOssService.uploadFile(uploadKey,file);

        aliyunOssService.viewBucket();
        logger.info("{} 1上传图片的URL是:{}",uploadKey,aliyunOssService.getUrl(uploadKey,3600* 24*365*10));//10年//30));//
//        logger.info("p2.jpg 上传图片的URL是："+aliyunOssService.getUrl("p2.jpg"));

    }


    @Test
    public void testUploaderOSSp10() throws Exception {
        File file = new File("D:\\temp\\banner\\ios\\banneriospatient1.png");//image.jpg 可以上传app.apk 文件类型，fastFile 只是文件管理工具

        logger.info("开始上传图片到库里面去：");

        String uploadKey = "banneriospatient1";
        aliyunOssService.deleteKey(uploadKey);
        aliyunOssService.uploadFile(uploadKey,file);

        aliyunOssService.viewBucket();
        logger.info("{} 1上传图片的URL是:{}",uploadKey,aliyunOssService.getUrl(uploadKey,3600* 24*365*10));//10年//30));//
//        logger.info("p2.jpg 上传图片的URL是："+aliyunOssService.getUrl("p2.jpg"));

    }

    @Test
    public void testUploaderOSSp20() throws Exception {
        File file = new File("D:\\temp\\banner\\ios\\banneriospatient2.png");//image.jpg 可以上传app.apk 文件类型，fastFile 只是文件管理工具

        logger.info("开始上传图片到库里面去：");

        String uploadKey = "banneriospatient2";
        aliyunOssService.deleteKey(uploadKey);
        aliyunOssService.uploadFile(uploadKey,file);

        aliyunOssService.viewBucket();
        logger.info("{} 1上传图片的URL是:{}",uploadKey,aliyunOssService.getUrl(uploadKey,3600* 24*365*10));//10年//30));//
//        logger.info("p2.jpg 上传图片的URL是："+aliyunOssService.getUrl("p2.jpg"));

    }

    @Test
    public void testUploaderOSSp30() throws Exception {
        File file = new File("D:\\temp\\banner\\ios\\banneriospatient3.png");//image.jpg 可以上传app.apk 文件类型，fastFile 只是文件管理工具

        logger.info("开始上传图片到库里面去：");

        String uploadKey = "banneriospatient3";
        aliyunOssService.deleteKey(uploadKey);
        aliyunOssService.uploadFile(uploadKey,file);

        aliyunOssService.viewBucket();
        logger.info("{} 1上传图片的URL是:{}",uploadKey,aliyunOssService.getUrl(uploadKey,3600* 24*365*10));//10年//30));//
//        logger.info("p2.jpg 上传图片的URL是："+aliyunOssService.getUrl("p2.jpg"));

    }


    @Test
    public void testUploaderOSSph10() throws Exception {
        File file = new File("D:\\temp\\banner\\ios\\banneriospharmacist1.png");//image.jpg 可以上传app.apk 文件类型，fastFile 只是文件管理工具

        logger.info("开始上传图片到库里面去：");

        String uploadKey = "banneriospharmacist1";
        aliyunOssService.deleteKey(uploadKey);
        aliyunOssService.uploadFile(uploadKey,file);

        aliyunOssService.viewBucket();
        logger.info("{} 1上传图片的URL是:{}",uploadKey,aliyunOssService.getUrl(uploadKey,3600* 24*365*10));//10年//30));//
//        logger.info("p2.jpg 上传图片的URL是："+aliyunOssService.getUrl("p2.jpg"));

    }

    @Test
    public void testUploaderOSSph20() throws Exception {
        File file = new File("D:\\temp\\banner\\ios\\banneriospharmacist2.png");//image.jpg 可以上传app.apk 文件类型，fastFile 只是文件管理工具

        logger.info("开始上传图片到库里面去：");

        String uploadKey = "banneriospharmacist2";
        aliyunOssService.deleteKey(uploadKey);
        aliyunOssService.uploadFile(uploadKey,file);

        aliyunOssService.viewBucket();
        logger.info("{} 1上传图片的URL是:{}",uploadKey,aliyunOssService.getUrl(uploadKey,3600* 24*365*10));//10年//30));//
//        logger.info("p2.jpg 上传图片的URL是："+aliyunOssService.getUrl("p2.jpg"));

    }

    @Test
    public void testUploaderOSSph30() throws Exception {
        File file = new File("D:\\temp\\banner\\ios\\banneriospharmacist3.png");//image.jpg 可以上传app.apk 文件类型，fastFile 只是文件管理工具

        logger.info("开始上传图片到库里面去：");

        String uploadKey = "banneriospharmacist3";
        aliyunOssService.deleteKey(uploadKey);
        aliyunOssService.uploadFile(uploadKey,file);

        aliyunOssService.viewBucket();
        logger.info("{} 1上传图片的URL是:{}",uploadKey,aliyunOssService.getUrl(uploadKey,3600* 24*365*10));//10年//30));//
//        logger.info("p2.jpg 上传图片的URL是："+aliyunOssService.getUrl("p2.jpg"));

    }*/
}
