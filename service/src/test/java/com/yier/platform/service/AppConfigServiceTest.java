package com.yier.platform.service;

import com.alibaba.fastjson.JSONObject;
import com.yier.platform.TestBase;
import com.yier.platform.common.jsonResponse.CommonResponse;
import com.yier.platform.common.jsonResponse.ListResponse;
import com.yier.platform.common.model.AppConfig;
import com.yier.platform.common.model.AppConfigPo;
import com.yier.platform.common.model.Images;
import com.yier.platform.common.requestParam.AppConfigRequest;
import com.yier.platform.common.requestParam.ImagesRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.apache.http.entity.ContentType;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApiModel(value = "APP信息管理相关的请求接口测试类")
public class AppConfigServiceTest extends TestBase {
    private static final Logger logger = LoggerFactory.getLogger(AppConfigServiceTest.class);

    @Autowired
    private ImagesService imagesService;
    @Autowired
    private AppConfigService appConfigService;

    @ApiOperation(value = "获取APP配置信息列表")
    @Test
    public void getAppConfigList() {
        AppConfigRequest params = new AppConfigRequest();
        params.doWithSortOrStart();
        logger.info("获取APP配置信息列表, 请求参数：{}", params);
        ListResponse<AppConfigPo> res = new ListResponse<AppConfigPo>();
        List<AppConfigPo> appConfigList = this.appConfigService.getAppConfigList(params);
        int count = this.appConfigService.getAppConfigListCount(params);
        res.setData(appConfigList);
        res.setTotal(count);
        logger.info("获取APP配置信息列表, 测试结果：{}", res.toJsonString());
    }

    @ApiOperation(value = "新增APP图片配置信息")
    @Test
    public void insertAppConfig() {
        String configType = "1";
        Long typeId = 4L;//表的标识类型： 1表示New，2表示message，3表示personMessage 4.表示来自于banner 没有库中id，5.广告表的类型，无Id 6.启动页 无id
        Map<String, String> map = new HashMap<String, String>();
        map.put("APP_CONFIG_TYPE_ID_REG_NAME", "测试新增APP页面图片配置");
        map.put("APP_CONFIG_TYPE_ID_REG_DISCRIPTION", "测试新增APP页面图片配置");
        map.put("APP_CONFIG_TYPE_ID_REG_BANNER_WIDTH", "666");
        map.put("APP_CONFIG_TYPE_ID_REG_BANNER_HEIGHT", "888");
        map.put("APP_CONFIG_TYPE_ID_REG_STARTUP_WIDTH", "666");
        map.put("APP_CONFIG_TYPE_ID_REG_STARTUP_HEIGHT", "888");
        logger.info("新增APP配置信息, 请求参数：map==={}, configType==={}, typeId==={}", map, configType, typeId);
        ListResponse<AppConfig> res = new ListResponse<AppConfig>();
        List<AppConfig> appConfigList = this.appConfigService.insertAppConfig(map, configType, typeId, null);
        res.setData(appConfigList);
        logger.info("新增APP配置信息, 测试结果：{}", res.toJsonString());
    }

    @ApiOperation(value = "新增APP版本控制信息")
    @Test
    public void insertAppConfig2() {
        String configType = "2";
        Long typeId = 5L;//表的标识类型： 1表示New，2表示message，3表示personMessage 4.表示来自于banner 没有库中id，5.广告表的类型，无Id 6.启动页 无id
        String fileName = "E:\\guangukeji\\demo.rar";
        File file = null;
        try {
            file = new File(fileName);
            FileInputStream fileInputStream = new FileInputStream(file);
            MultipartFile multipartFile = new MockMultipartFile(file.getName(), file.getName(),
                    ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream);

            Map<String, String> map = new HashMap<String, String>();
            map.put("APP_CONFIG_TYPE_ID_REG_FILE_NAME", "测试新增APP版本控制信息");
            map.put("APP_CONFIG_TYPE_ID_REG_VERSION_NUMBER", "1.4");
            map.put("APP_CONFIG_TYPE_ID_REG_DISCRIPTION", "测试新增APP版本控制信息");
            logger.info("新增APP配置信息, 请求参数：map==={}, configType==={}, typeId==={}, multipartFile==={}", map, configType, typeId, multipartFile);
            ListResponse<AppConfig> res = new ListResponse<AppConfig>();
            List<AppConfig> appConfigList = this.appConfigService.insertAppConfig(map, configType, typeId, multipartFile);
            res.setData(appConfigList);
            logger.info("新增APP配置信息, 测试结果：{}", res.toJsonString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @ApiOperation(value = "获取APP图片信息列表")
    @Test
    public void getAppImagesList() {
        ImagesRequest params = new ImagesRequest();
        params.setTypeId(1L);//所属APP （端口类型 1患者端 2医生端 3药师端）
        params.setMarkId(4L);//表的标识类型： 1表示New，2表示message，3表示personMessage 4.表示来自于banner 没有库中id，5.广告表的类型，无Id 6.启动页 无id
        params.doWithSortOrStart();
        logger.info("获取APP图片信息列表, 请求参数：{}", params);
        ListResponse<Images> res = new ListResponse<Images>();
        Assert.isTrue(params.getTypeId() != null, "typeId不能为空");
        Assert.isTrue(params.getMarkId() != null, "markId不能为空");
        List<Images> appConfigList = this.imagesService.listImages(params);
        int count = this.imagesService.listImagesCount(params);
        res.setData(appConfigList);
        res.setTotal(count);
        logger.info("获取APP图片信息列表, 测试结果：{}", res.toJsonString());
    }

    @ApiOperation(value = "根据id获取APP配置信息")
    @Test
    public void getAppImagesById() {
        CommonResponse<Images> res = new CommonResponse<Images>();
        Images images = this.imagesService.getAppImagesById(1L);
        res.setData(images);
        logger.info("根据id获取APP配置信息, 测试结果：{}", res.toJsonString());
    }

    @ApiOperation(value = "根据typeId和markId联合查询APP信息信息")
    @Test
    public void getAppImagesByTypeIdAndMarkId() {
        String json = "{\"typeId\":1,\"markId\":4}";
        ImagesRequest params = JSONObject.parseObject(json, ImagesRequest.class);
        logger.info("根据typeId和markId联合查询APP信息信息, 请求参数：{}", params);
        ListResponse<Images> res = new ListResponse<Images>();
        List<Images> images = this.imagesService.getAppImagesByTypeIdAndMarkId(params);
        res.setData(images);
        logger.info("根据typeId和markId联合查询APP信息信息, 测试结果：{}", res.toJsonString());
    }

    @ApiOperation(value = "根据typeId查询APP基本信息")
    @Test
    public void getAppImagesByTypeId() {
        String json = "{\"typeId\":1}";
        ImagesRequest params = JSONObject.parseObject(json, ImagesRequest.class);
        logger.info("根据typeId查询APP基本信息, 请求参数：{}", params);
        ListResponse<Images> res = new ListResponse<Images>();
        List<Images> images = this.imagesService.getAppImagesByTypeId(params);
        res.setData(images);
        logger.info("根据typeId查询APP基本信息, 测试结果：{}", res.toJsonString());
    }

    @ApiOperation(value = "根据markId查询APP基本信息")
    @Test
    public void getAppImagesByMarkId() {
        String json = "{\"markId\":4}";
        ImagesRequest params = JSONObject.parseObject(json, ImagesRequest.class);
        logger.info("根据markId查询APP基本信息, 请求参数：{}", params);
        ListResponse<Images> res = new ListResponse<Images>();
        List<Images> images = this.imagesService.getAppImagesByMarkId(params);
        res.setData(images);
        logger.info("根据markId查询APP基本信息, 测试结果：{}", res.toJsonString());
    }

    @ApiOperation(value = "新增APP启动页图片")
    @Test
    public void insertAppStartupHomepageImage() {
        String json = "{\"typeId\":1,\"markId\":4,\"databaseId\":0,\"title\":\"测试新增首页图片\",\"name\":\"测试新增首页图片\",\"description\":\"banner内容\", \"url\": \"www.123.com\",\"remarks\":\"测试新增首页图片\"}";
        Images images = JSONObject.parseObject(json, Images.class);
        MultipartFile[] files = new MultipartFile[0];
        try {
            String fileName = "F:\\pic\\1.jpg";
            File file = new File(fileName);
            FileInputStream fileInputStream = new FileInputStream(file);
            MultipartFile multipartFile = new MockMultipartFile(file.getName(), file.getName(),
                    ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream);
            files = new MultipartFile[]{multipartFile};
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("新增APP启动页图片, 请求参数：images==={}, {}", images, files);
        CommonResponse<Images> res = new CommonResponse<Images>();
        this.imagesService.insertAppStartupHomepageImage(images, files);
        res.setData(images);
        logger.info("新增APP启动页图片, 测试结果：{}", res.toJsonString());
    }

    @ApiOperation(value = "更新APP启动页图片")
    @Test
    public void updateAppStartupHomepageImage() {
        String json = "{\"id\":32,\"typeId\":1,\"markId\":4,\"databaseId\":0,\"title\":\"测试新增启动页图片\",\"name\":\"测试新增启动页图片\",\"description\":\"banner内容\", \"url\": \"www.123.com\",\"remarks\":\"测试新增启动页图片\"}";
        Images images = JSONObject.parseObject(json, Images.class);
        MultipartFile[] files = new MultipartFile[0];
        try {
            String fileName = "F:\\pic\\2.jpg";
            File file = new File(fileName);
            FileInputStream fileInputStream = new FileInputStream(file);
            MultipartFile multipartFile = new MockMultipartFile(file.getName(), file.getName(),
                    ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream);
            files = new MultipartFile[]{multipartFile};
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("更新APP启动页图片, 请求参数：images==={}, {}", images, files);
        CommonResponse<Images> res = new CommonResponse<Images>();
        this.imagesService.updateAppStartupHomepageImage(images, files);
        res.setData(images);
        logger.info("更新APP启动页图片, 测试结果：{}", res.toJsonString());
    }

    @ApiOperation(value = "新增APP首页图片")
    @Test
    public void insertAppIndexImage() {
        String json = "{\"typeId\":1,\"markId\":5,\"databaseId\":0,\"title\":\"测试新增首页图片\",\"name\":\"测试新增首页图片\",\"description\":\"banner内容\", \"url\": \"www.123.com\",\"remarks\":\"测试新增首页图片\"}";
        Images images = JSONObject.parseObject(json, Images.class);
        MultipartFile[] files = new MultipartFile[0];
        try {
            String fileName = "F:\\pic\\3.jpg";
            File file = new File(fileName);
            FileInputStream fileInputStream = new FileInputStream(file);
            MultipartFile multipartFile = new MockMultipartFile(file.getName(), file.getName(),
                    ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream);
            files = new MultipartFile[]{multipartFile};
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("新增APP首页图片, 请求参数：images==={}, {}", images, files);
        CommonResponse<Images> res = new CommonResponse<Images>();
        this.imagesService.insertAppIndexImage(images, files);
        res.setData(images);
        logger.info("新增APP首页图片, 测试结果：{}", res.toJsonString());
    }

    @ApiOperation(value = "更新APP首页图片")
    @Test
    public void updateAppIndexImage() {
        String json = "{\"id\":33,\"typeId\":1,\"markId\":5,\"databaseId\":0,\"title\":\"测试新增首页图片\",\"name\":\"测试新增首页图片\",\"description\":\"banner内容\", \"url\": \"www.123.com\",\"remarks\":\"测试新增首页图片\"}";
        Images images = JSONObject.parseObject(json, Images.class);
        MultipartFile[] files = new MultipartFile[0];
        try {
            String fileName = "F:\\pic\\4.jpg";
            File file = new File(fileName);
            FileInputStream fileInputStream = new FileInputStream(file);
            MultipartFile multipartFile = new MockMultipartFile(file.getName(), file.getName(),
                    ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream);
            files = new MultipartFile[]{multipartFile};
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("更新APP首页图片, 请求参数：images==={}, {}", images, files);
        CommonResponse<Images> res = new CommonResponse<Images>();
        this.imagesService.updateAppIndexImage(images, files);
        res.setData(images);
        logger.info("更新APP首页图片, 测试结果：{}", res.toJsonString());
    }

    @ApiOperation(value = "修改APP图片显示顺序")
    @Test
    public void updateAppImageOrder() {
        String json = "[{\"id\":1,\"displayOrder\":3,\"remarks\":\"测试调整图片显示顺序\"},{\"id\":2,\"displayOrder\":1,\"remarks\":\"测试调整图片显示顺序\"},{\"id\":3,\"displayOrder\":2,\"remarks\":\"测试调整图片显示顺序\"}]";
        List<Images> images = JSONObject.parseArray(json, Images.class);
        logger.info("修改APP图片显示顺序, 请求参数：images==={}", images);
        ListResponse<Images> res = new ListResponse<Images>();
        List<Images> imagesList = this.imagesService.updateAppImageOrder(images);
        res.setData(imagesList);
        logger.info("修改APP图片显示顺序, 测试结果：{}", res.toJsonString());
    }
}
