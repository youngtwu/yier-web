//package com.yier.platform.controller;
//
//import com.yier.platform.TestBase;
//import com.yier.platform.common.jsonResponse.ListResponse;
//import com.yier.platform.common.model.AppConfig;
//import com.yier.platform.common.model.AppConfigPo;
//import com.yier.platform.common.model.Images;
//import com.yier.platform.common.requestParam.AppConfigRequest;
//import com.yier.platform.common.requestParam.ImagesRequest;
//import com.yier.platform.service.AppConfigService;
//import com.yier.platform.service.ImagesService;
//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiOperation;
//import org.apache.http.entity.ContentType;
//import org.junit.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.util.Assert;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@ApiModel(value = "APP信息管理相关的请求接口测试类")
//public class AppConfigManagerControllerTest extends TestBase {
//    private static final Logger logger = LoggerFactory.getLogger(AppConfigManagerControllerTest.class);
//
//    @Autowired
//    private ImagesService imagesService;
//    @Autowired
//    private AppConfigService appConfigService;
//
//    @ApiOperation(value = "获取APP配置信息列表")
//    @Test
//    public void getAppConfigList() {
//        AppConfigRequest params = new AppConfigRequest();
//        params.doWithSortOrStart();
//        logger.info("获取APP配置信息列表, 请求参数：{}", params);
//        ListResponse<AppConfigPo> res = new ListResponse<AppConfigPo>();
//        List<AppConfigPo> appConfigList = this.appConfigService.getAppConfigList(params);
//        int count = this.appConfigService.getAppConfigListCount(params);
//        res.setData(appConfigList);
//        res.setTotal(count);
//        logger.info("获取APP配置信息列表, 测试结果：{}", res.toJsonString());
//    }
//
//    @ApiOperation(value = "新增APP图片配置信息")
//    @Test
//    public void insertAppConfig() {
//        String configType = "1";
//        Long typeId = 4L;//表的标识类型： 1表示New，2表示message，3表示personMessage 4.表示来自于banner 没有库中id，5.广告表的类型，无Id 6.启动页 无id
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("APP_CONFIG_TYPE_ID_REG_NAME", "测试新增APP页面图片配置");
//        map.put("APP_CONFIG_TYPE_ID_REG_DISCRIPTION", "测试新增APP页面图片配置");
//        map.put("APP_CONFIG_TYPE_ID_REG_BANNER_WIDTH", "666");
//        map.put("APP_CONFIG_TYPE_ID_REG_BANNER_HEIGHT", "888");
//        map.put("APP_CONFIG_TYPE_ID_REG_STARTUP_WIDTH", "666");
//        map.put("APP_CONFIG_TYPE_ID_REG_STARTUP_HEIGHT", "888");
//        logger.info("新增APP配置信息, 请求参数：map==={}, configType==={}, typeId==={}", map, configType, typeId);
//        ListResponse<AppConfig> res = new ListResponse<AppConfig>();
//        List<AppConfig> appConfigList = this.appConfigService.insertAppConfig(map, configType, typeId, null);
//        res.setData(appConfigList);
//        logger.info("新增APP配置信息, 测试结果：{}", res.toJsonString());
//    }
//
//    @ApiOperation(value = "新增APP版本控制信息")
//    @Test
//    public void insertAppConfig2() {
//        String configType = "2";
//        Long typeId = 5L;//表的标识类型： 1表示New，2表示message，3表示personMessage 4.表示来自于banner 没有库中id，5.广告表的类型，无Id 6.启动页 无id
//        String fileName = "E:\\guangukeji\\demo.rar";
//        File file = null;
//        try {
//            file = new File(fileName);
//            FileInputStream fileInputStream = new FileInputStream(file);
//            MultipartFile multipartFile = new MockMultipartFile(file.getName(), file.getName(),
//                    ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream);
//
//            Map<String, String> map = new HashMap<String, String>();
//            map.put("APP_CONFIG_TYPE_ID_REG_FILE_NAME", "测试新增APP版本控制信息");
//            map.put("APP_CONFIG_TYPE_ID_REG_VERSION_NUMBER", "1.4");
//            map.put("APP_CONFIG_TYPE_ID_REG_DISCRIPTION", "测试新增APP版本控制信息");
//            logger.info("新增APP配置信息, 请求参数：map==={}, configType==={}, typeId==={}, multipartFile==={}", map, configType, typeId, multipartFile);
//            ListResponse<AppConfig> res = new ListResponse<AppConfig>();
//            List<AppConfig> appConfigList = this.appConfigService.insertAppConfig(map, configType, typeId, multipartFile);
//            res.setData(appConfigList);
//            logger.info("新增APP配置信息, 测试结果：{}", res.toJsonString());
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @ApiOperation(value = "获取APP图片信息列表")
//    @Test
//    public void getAppImagesList() {
//        ImagesRequest params = new ImagesRequest();
//        params.setTypeId(1L);//所属APP （端口类型 1患者端 2医生端 3药师端）
//        params.setMarkId(4L);//表的标识类型： 1表示New，2表示message，3表示personMessage 4.表示来自于banner 没有库中id，5.广告表的类型，无Id 6.启动页 无id
//        params.doWithSortOrStart();
//        logger.info("获取APP图片信息列表, 请求参数：{}", params);
//        ListResponse<Images> res = new ListResponse<Images>();
//        Assert.isTrue(params.getTypeId() != null, "typeId不能为空");
//        Assert.isTrue(params.getMarkId() != null, "markId不能为空");
//        List<Images> appConfigList = this.imagesService.listImages(params);
//        int count = this.imagesService.listImagesCount(params);
//        res.setData(appConfigList);
//        res.setTotal(count);
//        logger.info("获取APP图片信息列表, 测试结果：{}", res.toJsonString());
//    }
//}
