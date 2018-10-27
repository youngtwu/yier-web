package com.yier.platform.common.requestParam;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Map;

@ApiModel(value = "APP配置信息请求类")
public class AppConfigRequest extends BaseRequest {
    @ApiModelProperty(value = "APP配置信息主键id")
    private Long appConfigId;
    @ApiModelProperty(value = "所属APP （端口类型 1患者端 2医生端 3药师端）")
    private Long typeId;
    @ApiModelProperty(value = "参数key")
    private String pkey;
    @ApiModelProperty(value = "区分app配置管理类型(1.app基本信息配置 2.app版本管理)")
    private String configType;
    @ApiModelProperty(value = "参数map")
    private Map<String, String> map;

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    public String getConfigType() {
        return configType;
    }

    public void setConfigType(String configType) {
        this.configType = configType;
    }

    public Long getAppConfigId() {
        return appConfigId;
    }

    public void setAppConfigId(Long appConfigId) {
        this.appConfigId = appConfigId;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public String getPkey() {
        return pkey;
    }

    public void setPkey(String pkey) {
        this.pkey = pkey;
    }
}
