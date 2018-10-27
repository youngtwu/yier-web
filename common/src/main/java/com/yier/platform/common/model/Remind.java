package com.yier.platform.common.model;

import com.yier.platform.common.jsonResponse.BaseJsonObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(value = "用药提醒")
public class Remind extends BaseJsonObject {
    @ApiModelProperty(value = "主键标识Id")
    private Long id;
    @ApiModelProperty(value = "患者ID。关联到t_patient")
    private Long patientId;
    @ApiModelProperty(value = "药品ID。关联到t_drug")
    private Long drugId;
    @ApiModelProperty(value = "服用剂量")
    private Float dosage;
    @ApiModelProperty(value = "提醒备注")
    private String content;
    @ApiModelProperty(value = "状态（0正常 1删除 2停用 3冻结）")
    private String status;
    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;
    @ApiModelProperty(value = "修改时间")
    private Date gmtModified;
    @ApiModelProperty(value = "备注信息")
    private String remarks;

    public Remind(Long id, Long patientId, Long drugId, Float dosage, String content, String status, Date gmtCreate, Date gmtModified, String remarks) {
        this.id = id;
        this.patientId = patientId;
        this.drugId = drugId;
        this.dosage = dosage;
        this.content = content;
        this.status = status;
        this.gmtCreate = gmtCreate;
        this.gmtModified = gmtModified;
        this.remarks = remarks;
    }

    public Remind() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Long getDrugId() {
        return drugId;
    }

    public void setDrugId(Long drugId) {
        this.drugId = drugId;
    }

    public Float getDosage() {
        return dosage;
    }

    public void setDosage(Float dosage) {
        this.dosage = dosage;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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