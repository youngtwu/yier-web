package com.yier.platform.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.yier.platform.common.model.AppConfig;
import com.yier.platform.common.model.AppConfigPo;
import com.yier.platform.common.model.Images;
import com.yier.platform.common.requestParam.AppConfigRequest;
import com.yier.platform.common.requestParam.ImagesRequest;
import com.yier.platform.dao.ImagesMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 图片展示信息 service
 */
@ApiModel(value = "图片展示信息 service")
@Service
public class ImagesService {
    private static final Logger logger = LoggerFactory.getLogger(ImagesService.class);
    @Autowired
    private ImagesMapper daoImagesMapper;
    @Autowired
    private RedisService redisService;
    @Autowired
    private Uploader uploader;
    @Autowired
    private AppConfigService appConfigService;

    // 根据条件key查询列表
    public List<Images> getRedisListImagesByKey(String key, ImagesRequest params) {
        List<Images> result = redisService.getJsonObjectByKey(key, new TypeReference<List<Images>>() {
        });
        if (result == null) {
            result = daoImagesMapper.listImages(params);
            redisService.setJsonObjectBy(key, result, 12L, TimeUnit.HOURS);
        }
        return result;
    }

    // 根据条件查询列表
    public List<Images> listImages(ImagesRequest params) {
        return daoImagesMapper.listImages(params);
    }

    // 根据条件查询列表 总数
    public int listImagesCount(ImagesRequest params) {
        return daoImagesMapper.listImagesCount(params);
    }

    @ApiOperation(value = "根据id获取app图片信息")
    public Images getAppImagesById(Long id) {
        return this.selectByPrimaryKey(id);
    }

    @ApiOperation(value = "根据typeId和markId联合查询APP信息信息")
    public List<Images> getAppImagesByTypeIdAndMarkId(ImagesRequest params) {
        return this.daoImagesMapper.getAppImagesByTypeIdAndMarkId(params);
    }

    @ApiOperation(value = "根据typeId和markId联合查询APP信息信息")
    public List<Images> getAppImagesByTypeId(ImagesRequest params) {
        return this.daoImagesMapper.getAppImagesByTypeId(params);
    }

    @ApiOperation(value = "根据typeId和markId联合查询APP信息信息")
    public List<Images> getAppImagesByMarkId(ImagesRequest params) {
        return this.daoImagesMapper.getAppImagesByMarkId(params);
    }

    @ApiOperation(value = "新增APP启动页图片")
    @Transactional
    public int insertAppStartupHomepageImage(Images images, MultipartFile[] files) {
        Assert.isTrue(images.getTypeId() != null, "typeId不能为空");
        Assert.isTrue(images.getMarkId() != null, "markId不能为空");
        Assert.isTrue(files != null && files.length > 0, "上传图片不能为空！");
        Assert.isTrue(StringUtils.isNotBlank(images.getName()), "名称不能为空！");
        Assert.isTrue(StringUtils.isNotBlank(images.getUrl()), "跳转URL不能为空！");
        int result = 0;
        if (files != null && files.length > 0) {
            for (int i = 0; i < files.length; i++) {
                MultipartFile file = files[i];
                try {
                    BufferedImage image = ImageIO.read(file.getInputStream());
                    if (image != null) {//如果image=null 表示上传的不是图片格式
                        //查询app基本信息表，获取图片宽和高
                        AppConfigRequest params = new AppConfigRequest();
                        params.setTypeId(images.getTypeId());
                        params.setConfigType("1");
                        List<AppConfigPo> appConfigList = this.appConfigService.getAppConfigList(params);
                        if(appConfigList != null && appConfigList.size() > 0){
                            AppConfigPo appConfigPo = appConfigList.get(0);
                            if(appConfigPo != null){
                                Assert.isTrue(image.getHeight() < Integer.parseInt(appConfigPo.getStartupHeight()), "启动页图片高度过大！");
                                Assert.isTrue(image.getWidth() < Integer.parseInt(appConfigPo.getStartupWidth()), "启动页图片宽度过大！");
                            }

                            //上传APP启动页图片到服务器
                            String imagesUrl = this.uploader.upload(file);
                            //新增APP启动页图片
                            images.setUrl(imagesUrl);
                            images.setWidth(image.getWidth());
                            images.setHeight(image.getHeight());
                            if(appConfigPo.getDisplayOrder() != null){
                                images.setDisplayOrder(appConfigPo.getDisplayOrder() + 1);
                            }
                            images.setRemarks("新增APP启动页图片");
                            int count = this.insertSelective(images);
                            result += count;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        logger.info("新增APP启动页图片个数:{}", result);
        return result;
    }

    @ApiOperation(value = "更新APP启动页图片")
    @Transactional
    public int updateAppStartupHomepageImage(Images images, MultipartFile[] files) {
        Assert.isTrue(images.getId() != null, "typeId不能为空");
        Assert.isTrue(images.getTypeId() != null, "typeId不能为空");
        Assert.isTrue(images.getMarkId() != null, "markId不能为空");
        Assert.isTrue(files != null, "上图片不能为空！");
        Assert.isTrue(StringUtils.isNotBlank(images.getName()), "名称不能为空！");
        Assert.isTrue(StringUtils.isNotBlank(images.getUrl()), "跳转URL不能为空！");
        int result = 0;
        if (files != null && files.length > 0) {
            for (int i = 0; i < files.length; i++) {
                MultipartFile file = files[i];
                try {
                    BufferedImage image = ImageIO.read(file.getInputStream());
                    if (image != null) {//如果image=null 表示上传的不是图片格式
                        //查询app基本信息表，获取图片宽和高
                        AppConfigRequest params = new AppConfigRequest();
                        params.setTypeId(images.getTypeId());
                        params.setConfigType("1");
                        List<AppConfigPo> appConfigList = this.appConfigService.getAppConfigList(params);
                        if(appConfigList != null && appConfigList.size() > 0){
                            AppConfigPo appConfigPo = appConfigList.get(0);
                            if(appConfigPo != null){
                                Assert.isTrue(image.getHeight() < Integer.parseInt(appConfigPo.getStartupHeight()), "启动页图片高度过大！");
                                Assert.isTrue(image.getWidth() < Integer.parseInt(appConfigPo.getStartupWidth()), "启动页图片宽度过大！");
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //上传APP启动页图片到服务器
                String imagesUrl = this.uploader.upload(file);
                //更新APP启动页图片
                images.setUrl(imagesUrl);
                images.setRemarks("更新APP启动页图片");
                int count = this.updateByPrimaryKeySelective(images);
                result += count;
            }
        }
        logger.info("更新APP启动页图片个数:{}", result);
        return result;
    }

    @ApiOperation(value = "新增APP首页图片")
    @Transactional
    public int insertAppIndexImage(Images images, MultipartFile[] files) {
        Assert.isTrue(images.getTypeId() != null, "typeId不能为空");
        Assert.isTrue(images.getMarkId() != null, "markId不能为空");
        Assert.isTrue(files != null && files.length > 0, "上传图片不能为空！");
        Assert.isTrue(StringUtils.isNotBlank(images.getName()), "名称不能为空！");
        Assert.isTrue(StringUtils.isNotBlank(images.getUrl()), "跳转URL不能为空！");

        int result = 0;
        if (files != null && files.length > 0) {
            for (int i = 0; i < files.length; i++) {
                MultipartFile file = files[i];
                try {
                    BufferedImage image = ImageIO.read(file.getInputStream());
                    if (image != null) {//如果image=null 表示上传的不是图片格式
                        //查询app基本信息表，获取图片宽和高
                        AppConfigRequest params = new AppConfigRequest();
                        params.setTypeId(images.getTypeId());
                        params.setConfigType("1");
                        List<AppConfigPo> appConfigList = this.appConfigService.getAppConfigList(params);
                        if(appConfigList != null && appConfigList.size() > 0){
                            AppConfigPo appConfigPo = appConfigList.get(0);
                            if(appConfigPo != null){
                                Assert.isTrue(image.getHeight() < Integer.parseInt(appConfigPo.getBannerHeight()), "首页图片高度过大！");
                                Assert.isTrue(image.getWidth() < Integer.parseInt(appConfigPo.getBannerWidth()), "首页图片宽度过大！");
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //上传APP启动页图片到服务器
                String imagesUrl = this.uploader.upload(file);
                //新增APP首页图片
                images.setUrl(imagesUrl);
                images.setRemarks("新增APP首页图片");
                int count = this.insertSelective(images);
                result += count;
            }
        }
        logger.info("新增APP首页图片个数:{}", result);
        return result;
    }

    @ApiOperation(value = "更新APP首页图片")
    @Transactional
    public int updateAppIndexImage(Images images, MultipartFile[] files) {
        Assert.isTrue(images.getTypeId() != null, "typeId不能为空");
        Assert.isTrue(images.getMarkId() != null, "markId不能为空");
        Assert.isTrue(files != null && files.length > 0, "上传图片不能为空！");
        Assert.isTrue(StringUtils.isNotBlank(images.getName()), "名称不能为空！");
        Assert.isTrue(StringUtils.isNotBlank(images.getUrl()), "跳转URL不能为空！");

        int result = 0;
        if (files != null && files.length > 0) {
            for (int i = 0; i < files.length; i++) {
                MultipartFile file = files[i];
                try {
                    BufferedImage image = ImageIO.read(file.getInputStream());
                    if (image != null) {//如果image=null 表示上传的不是图片格式
                        //查询app基本信息表，获取图片宽和高
                        AppConfigRequest params = new AppConfigRequest();
                        params.setTypeId(images.getTypeId());
                        params.setConfigType("1");
                        List<AppConfigPo> appConfigList = this.appConfigService.getAppConfigList(params);
                        if(appConfigList != null && appConfigList.size() > 0){
                            AppConfigPo appConfigPo = appConfigList.get(0);
                            if(appConfigPo != null){
                                Assert.isTrue(image.getHeight() < Integer.parseInt(appConfigPo.getBannerHeight()), "首页图片高度过大！");
                                Assert.isTrue(image.getWidth() < Integer.parseInt(appConfigPo.getBannerWidth()), "首页图片宽度过大！");
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //上传APP启动页图片到服务器
                String imagesUrl = this.uploader.upload(file);
                //更新APP首页图片
                images.setUrl(imagesUrl);
                images.setGmtModified(new Date());
                images.setRemarks("更新APP首页图片");
                int count = this.updateByPrimaryKeySelective(images);
                result += count;
            }
        }
        logger.info("更新APP首页图片个数:{}", result);
        return result;
    }

    @ApiOperation(value = "更新APP首页图片")
    @Transactional
    public List<Images> updateAppImageOrder(List<Images> images) {
        Assert.isTrue(images != null, "图片不能为空");
        this.daoImagesMapper.updateAppImageOrderForBatch(images);
        return images;
    }




    public Images selectByPrimaryKey(Long id) {
        return this.daoImagesMapper.selectByPrimaryKey(id);
    }

    public int insertSelective(Images images) {
        return this.daoImagesMapper.insertSelective(images);
    }

    public int updateByPrimaryKeySelective(Images images) {
        return this.daoImagesMapper.updateByPrimaryKeySelective(images);
    }
}
