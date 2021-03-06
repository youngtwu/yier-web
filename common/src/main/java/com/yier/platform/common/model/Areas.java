package com.yier.platform.common.model;

import com.yier.platform.common.jsonResponse.BaseJsonObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * 区县信息
 */
@ApiModel(value = "区县信息")
public class Areas extends BaseJsonObject {
    @ApiModelProperty(value = "主键标识Id")
    private Integer id;
    @ApiModelProperty(value = "区县ID")
    private String areaId;
    @ApiModelProperty(value = "区县")
    private String area;
    @ApiModelProperty(value = "城市ID")
    private String cityId;
    @ApiModelProperty(value = "状态（0正常 1删除 2停用 3冻结 4 审核中 5审核未通过）")
    private String status;
    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;
    @ApiModelProperty(value = "修改时间")
    private Date gmtModified;
    @ApiModelProperty(value = "备注信息")
    private String remarks;

    public Areas(Integer id, String areaId, String area, String cityId) {
        this.id = id;
        this.areaId = areaId;
        this.area = area;
        this.cityId = cityId;
    }

    public Areas(Integer id, String areaId, String area, String cityId, String status, Date gmtCreate, Date gmtModified, String remarks) {
        this.id = id;
        this.areaId = areaId;
        this.area = area;
        this.cityId = cityId;
        this.status = status;
        this.gmtCreate = gmtCreate;
        this.gmtModified = gmtModified;
        this.remarks = remarks;
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

    public Areas() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }
}