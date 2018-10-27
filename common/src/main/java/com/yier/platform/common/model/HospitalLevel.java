package com.yier.platform.common.model;

import com.yier.platform.common.jsonResponse.BaseJsonObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(value = "医院等级")
public class HospitalLevel extends BaseJsonObject {
    @ApiModelProperty(value = "主键标识Id")
    private Integer id;
    @ApiModelProperty(value = "等级名称")
    private String name;
    @ApiModelProperty(value = "显示顺序 ")
    private Integer displayOrder;
    @ApiModelProperty(value = "记录创建时间")
    private Date gmtCreate;
    @ApiModelProperty(value = "记录修改时间")
    private Date gmtModified;

    public HospitalLevel(Integer id, String name, Integer displayOrder, Date gmtCreate, Date gmtModified) {
        this.id = id;
        this.name = name;
        this.displayOrder = displayOrder;
        this.gmtCreate = gmtCreate;
        this.gmtModified = gmtModified;
    }

    public HospitalLevel() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
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
}