package com.yier.platform.common.model;

import com.yier.platform.common.jsonResponse.BaseJsonObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(value = "APP配置信息类")
public class AppConfig extends BaseJsonObject {
    @ApiModelProperty(value = "主键标识Id")
    private Long id;
    @ApiModelProperty(value = "区分app配置管理类型(1.app基本信息配置 2.app版本管理)")
    private String configType;
    @ApiModelProperty(value = "所属APP （端口类型 1患者端 2医生端 3药师端）")
    private Long typeId;
    @ApiModelProperty(value = "参数key")
    private String pkey;
    @ApiModelProperty(value = "参数描述")
    private String description;
    @ApiModelProperty(value = "参数值")
    private String pvalue;
    @ApiModelProperty(value = "前缀")
    private String prefix;
    @ApiModelProperty(value = "顺序")
    private Integer displayOrder;
    @ApiModelProperty(value = "状态")
    private String status;
    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;
    @ApiModelProperty(value = "更新时间")
    private Date gmtModified;
    @ApiModelProperty(value = "备注信息")
    private String remarks;

    public AppConfig(Long id, String configType, Long typeId, String pkey, String description, String pvalue, String prefix, Integer displayOrder, String status, Date gmtCreate, Date gmtModified, String remarks) {
        this.id = id;
        this.typeId = typeId;
        this.configType = configType;
        this.pkey = pkey;
        this.description = description;
        this.pvalue = pvalue;
        this.prefix = prefix;
        this.displayOrder = displayOrder;
        this.status = status;
        this.gmtCreate = gmtCreate;
        this.gmtModified = gmtModified;
        this.remarks = remarks;
    }

    public String getConfigType() {
        return configType;
    }

    public void setConfigType(String configType) {
        this.configType = configType;
    }

    public AppConfig() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPvalue() {
        return pvalue;
    }

    public void setPvalue(String pvalue) {
        this.pvalue = pvalue;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}