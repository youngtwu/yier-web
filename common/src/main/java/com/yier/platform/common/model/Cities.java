package com.yier.platform.common.model;

import com.yier.platform.common.jsonResponse.BaseJsonObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * 地级市信息
 */
@ApiModel(value = "地级市信息")
public class Cities extends BaseJsonObject {
    @ApiModelProperty(value = "主键标识Id")
    private Integer id;
    @ApiModelProperty(value = "城市Id")
    private String cityId;
    @ApiModelProperty(value = "城市名称")
    private String city;
    @ApiModelProperty(value = "省份Id")
    private String provinceId;
    @ApiModelProperty(value = "省份等级 一级省份，二级城市")
    private Integer grade = 2;//一级省份，二级城市
    @ApiModelProperty(value = "状态（0正常 1删除 2停用 3冻结 4 审核中 5审核未通过）")
    private String status;
    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;
    @ApiModelProperty(value = "修改时间")
    private Date gmtModified;
    @ApiModelProperty(value = "备注信息")
    private String remarks;

    public Cities(Integer id, String cityId, String city, String provinceId, String status) {
        this.id = id;
        this.cityId = cityId;
        this.city = city;
        this.provinceId = provinceId;
        this.status = status;
    }

    public Cities(Integer id, String cityId, String city, String provinceId, String status, Date gmtCreate, Date gmtModified, String remarks) {
        this.id = id;
        this.cityId = cityId;
        this.city = city;
        this.provinceId = provinceId;
        this.status = status;
        this.gmtCreate = gmtCreate;
        this.gmtModified = gmtModified;
        this.remarks = remarks;
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

    public Cities() {
        super();
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}