package com.yier.platform.service;

import com.yier.platform.common.model.AppConfig;
import com.yier.platform.common.model.AppConfigPo;
import com.yier.platform.common.requestParam.AppConfigRequest;
import com.yier.platform.dao.AppConfigMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * APP配置信息 service
 */
@ApiModel(value = "APP配置信息 service")
@Service
@Slf4j
public class AppConfigService {
    private static final Logger logger = LoggerFactory.getLogger(AppConfigService.class);

    @Autowired
    private AppConfigMapper appConfigMapper;
    @Autowired
    private Uploader uploader;

    @ApiOperation(value = "获取APP配置信息列表")
    public List<AppConfigPo> getAppConfigList(AppConfigRequest params) {
        Assert.isTrue(params.getConfigType() != null, "configType不能为空");
        //Assert.isTrue(params.getTypeId() != null, "typeId不能为空");
        List<AppConfigPo> appConfigPoList = new ArrayList<AppConfigPo>();
        List<AppConfig> list = this.appConfigMapper.getAppConfigList(params);
        if ("1".equals(params.getConfigType())) {//基本配置信息
            for (int typeId = 1; typeId <= (list.size() / 6 == 0 ? list.size() : list.size() / 6); typeId++) {
                String nameKey = "APP_CONFIG_TYPE_ID_" + (params.getTypeId() == null ? typeId : params.getTypeId()) + "_NAME";
                String descriptionKey = "APP_CONFIG_TYPE_ID_" + (params.getTypeId() == null ? typeId : params.getTypeId()) + "_DISCRIPTION";
                String bannerWidthKey = "APP_CONFIG_TYPE_ID_" + (params.getTypeId() == null ? typeId : params.getTypeId()) + "_BANNER_WIDTH";
                String bannerHeightKey = "APP_CONFIG_TYPE_ID_" + (params.getTypeId() == null ? typeId : params.getTypeId()) + "_BANNER_HEIGHT";
                String startupWidthKey = "APP_CONFIG_TYPE_ID_" + (params.getTypeId() == null ? typeId : params.getTypeId()) + "_STARTUP_WIDTH";
                String startupHeightKey = "APP_CONFIG_TYPE_ID_" + (params.getTypeId() == null ? typeId : params.getTypeId()) + "_STARTUP_HEIGHT";
                if (list.stream().filter(t -> t.getPkey().equals(nameKey)).count() > 0) {
                    AppConfigPo appConfigPo = new AppConfigPo();
                    appConfigPo.setPkey(list.stream().filter(t -> t.getPkey().equals(nameKey)).findFirst().orElse(new AppConfig()).getPvalue());
                    appConfigPo.setStatus(list.stream().filter(t -> t.getPkey().equals(nameKey)).findFirst().orElse(new AppConfig()).getStatus());
                    appConfigPo.setTypeId(list.stream().filter(t -> t.getPkey().equals(nameKey)).findFirst().orElse(new AppConfig()).getTypeId());
                    appConfigPo.setConfigType(list.stream().filter(t -> t.getPkey().equals(nameKey)).findFirst().orElse(new AppConfig()).getConfigType());
                    appConfigPo.setDescription(list.stream().filter(t -> t.getPkey().equals(descriptionKey)).findFirst().orElse(new AppConfig()).getPvalue());
                    appConfigPo.setBannerWidth(list.stream().filter(t -> t.getPkey().equals(bannerWidthKey)).findFirst().orElse(new AppConfig()).getPvalue());
                    appConfigPo.setBannerHeight(list.stream().filter(t -> t.getPkey().equals(bannerHeightKey)).findFirst().orElse(new AppConfig()).getPvalue());
                    appConfigPo.setStartupWidth(list.stream().filter(t -> t.getPkey().equals(startupWidthKey)).findFirst().orElse(new AppConfig()).getPvalue());
                    appConfigPo.setStartupHeight(list.stream().filter(t -> t.getPkey().equals(startupHeightKey)).findFirst().orElse(new AppConfig()).getPvalue());
                    appConfigPo.setRemarks(list.get(typeId).getRemarks());
                    appConfigPoList.add(appConfigPo);
                }
            }
        } else if ("2".equals(params.getConfigType())) {//版本信息管理
            for (int typeId = 1; typeId <= (list.size() / 4 == 0 ? list.size() : list.size() / 4); typeId++) {
                String fileNameKey = "APP_CONFIG_TYPE_ID_" + (params.getTypeId() == null ? typeId : params.getTypeId()) + "_FILE_NAME";
                String versionNumberKey = "APP_CONFIG_TYPE_ID_" + (params.getTypeId() == null ? typeId : params.getTypeId()) + "_VERSION_NUMBER";
                String filePathKey = "APP_CONFIG_TYPE_ID_" + (params.getTypeId() == null ? typeId : params.getTypeId()) + "_FILE_PATH";
                String updateDiscriptionKey = "APP_CONFIG_TYPE_ID_" + (params.getTypeId() == null ? typeId : params.getTypeId()) + "_UPDATE_DISCRIPTION";
                if (list.stream().filter(t -> t.getPkey().equals(fileNameKey)).count() > 0) {
                    AppConfigPo appConfigPo = new AppConfigPo();
                    appConfigPo.setPkey(list.stream().filter(t -> t.getPkey().equals(fileNameKey)).findFirst().orElse(new AppConfig()).getPvalue());
                    appConfigPo.setStatus(list.stream().filter(t -> t.getPkey().equals(fileNameKey)).findFirst().orElse(new AppConfig()).getStatus());
                    appConfigPo.setTypeId(list.stream().filter(t -> t.getPkey().equals(fileNameKey)).findFirst().orElse(new AppConfig()).getTypeId());
                    appConfigPo.setConfigType(list.stream().filter(t -> t.getPkey().equals(fileNameKey)).findFirst().orElse(new AppConfig()).getConfigType());
                    appConfigPo.setGmtModified(list.stream().filter(t -> t.getPkey().equals(fileNameKey)).findFirst().orElse(new AppConfig()).getGmtModified());
                    appConfigPo.setVersionNumber(list.stream().filter(t -> t.getPkey().equals(versionNumberKey)).findFirst().orElse(new AppConfig()).getPvalue());
                    appConfigPo.setFilePath(list.stream().filter(t -> t.getPkey().equals(filePathKey)).findFirst().orElse(new AppConfig()).getPvalue());
                    appConfigPo.setUpdateDiscription(list.stream().filter(t -> t.getPkey().equals(updateDiscriptionKey)).findFirst().orElse(new AppConfig()).getPvalue());
                    appConfigPo.setRemarks(list.get(typeId).getRemarks());
                    appConfigPoList.add(appConfigPo);
                }
            }
        }

        return appConfigPoList;
    }

    @ApiOperation(value = "获取APP配置信息总数")
    public int getAppConfigListCount(AppConfigRequest params) {
        return this.appConfigMapper.getAppConfigListCount(params);
    }

    @ApiOperation(value = "新增APP配置信息")
    @Transactional
    public List<AppConfig> insertAppConfig(Map<String, String> map, String configType, Long typeId, MultipartFile file) {
        Assert.isTrue(configType != null, "configType不能为空");
        Assert.isTrue(typeId != null, "typeId不能为空");
        List<AppConfig> appConfigList = new ArrayList<AppConfig>();
        AppConfig appConfig = null;
        //循环遍历map
        if (map != null && map.size() > 0) {
            map.remove("configType");
            map.remove("typeId");
            for (Map.Entry<String, String> entry : map.entrySet()) {
                logger.info("pkey-pvalue: {}, {}", entry.getKey(), entry.getValue());
                String pkey = entry.getKey();
                String pvalue = entry.getValue();
                pkey = pkey.replace("REG", typeId.toString());

                if ("1".equals(configType)) {//app基本信息配置
                    AppConfigRequest params = new AppConfigRequest();
                    params.setTypeId(typeId);
                    params.setConfigType(configType);
                    params.setPkey(pkey);
                    appConfig = this.appConfigMapper.getAppConfigByPkey(params);
                    if (appConfig != null) {
                        appConfig.setPvalue(pvalue);
                        appConfig.setRemarks("修改APP配置信息");
                        appConfig.setPkey(pkey);
                        appConfig.setGmtModified(new Date());
                        this.updateByPrimaryKeySelective(appConfig);
                        appConfigList.add(appConfig);
                    } else {
                        AppConfig aconfig = new AppConfig();
                        aconfig.setTypeId(typeId);
                        aconfig.setConfigType(configType);
                        aconfig.setPvalue(pvalue);
                        aconfig.setRemarks("新增APP配置信息");
                        aconfig.setPkey(pkey);
                        aconfig.setDescription(pkey);
                        this.insertSelective(aconfig);
                        appConfigList.add(aconfig);
                    }
                } else {//app版本控制管理
                    AppConfig aconfig = new AppConfig();
                    aconfig.setTypeId(typeId);
                    aconfig.setConfigType(configType);
                    aconfig.setPvalue(pvalue);
                    aconfig.setRemarks("新增APP版本控制信息");
                    aconfig.setPkey(pkey);
                    aconfig.setDescription(pkey);
                    this.insertSelective(aconfig);
                    appConfigList.add(aconfig);
                }
            }

            String vsersionUrl = "";
            //将文件上传到服务器
            if (file != null) {
                //上传版本文件到服务器
                vsersionUrl = this.uploader.upload(file);

                AppConfig aconfig = new AppConfig();
                aconfig.setTypeId(typeId);
                aconfig.setConfigType(configType);
                aconfig.setPvalue(vsersionUrl);
                aconfig.setRemarks("新增APP版本控制信息");
                aconfig.setPkey("APP_CONFIG_TYPE_ID_" + typeId + "_FILE_PATH");
                aconfig.setDescription("APP_CONFIG_TYPE_ID_" + typeId + "_FILE_PATH");
                this.insertSelective(aconfig);
                appConfigList.add(aconfig);
            }

        }
        return appConfigList;
    }


    public AppConfig selectByPrimaryKey(Long id) {
        return this.appConfigMapper.selectByPrimaryKey(id);
    }

    public int insertSelective(AppConfig appConfig) {
        return this.appConfigMapper.insertSelective(appConfig);
    }

    public int updateByPrimaryKeySelective(AppConfig appConfig) {
        return this.appConfigMapper.updateByPrimaryKeySelective(appConfig);
    }

    public int deleteAppConfigByTypeId(Long typeId) {
        return this.appConfigMapper.deleteAppConfigByTypeId(typeId);
    }
}
