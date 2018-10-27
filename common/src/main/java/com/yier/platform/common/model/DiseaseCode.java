package com.yier.platform.common.model;

import com.yier.platform.common.jsonResponse.BaseJsonObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(value = "疾病国内统一码")
public class DiseaseCode extends BaseJsonObject {
    @ApiModelProperty(value = "主键ID")
    private Long id;
    @ApiModelProperty(value = "疾病 编号")
    private String diseaseCode;
    @ApiModelProperty(value = "疾病 编号 补充")
    private String codeAppend;
    @ApiModelProperty(value = "疾病名称")
    private String diseaseName;
    @ApiModelProperty(value = "疾病拼音")
    private String pinyin;
    @ApiModelProperty(value = "参数key")
    private String groupPrefix;
    @ApiModelProperty(value = "简称")
    private String shortName;
    @ApiModelProperty(value = "顺序")
    private Integer displayOrder;
    @ApiModelProperty(value = "状态")
    private String status;
    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;
    @ApiModelProperty(value = "修改时间")
    private Date gmtModified;
    @ApiModelProperty(value = "备注信息")
    private String remarks;

    public DiseaseCode(Long id, String diseaseCode, String codeAppend, String diseaseName, String pinyin, String groupPrefix, String shortName, Integer displayOrder, String status, Date gmtCreate, Date gmtModified, String remarks) {
        this.id = id;
        this.diseaseCode = diseaseCode;
        this.codeAppend = codeAppend;
        this.diseaseName = diseaseName;
        this.pinyin = pinyin;
        this.groupPrefix = groupPrefix;
        this.shortName = shortName;
        this.displayOrder = displayOrder;
        this.status = status;
        this.gmtCreate = gmtCreate;
        this.gmtModified = gmtModified;
        this.remarks = remarks;
    }

    public DiseaseCode() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDiseaseCode() {
        return diseaseCode;
    }

    public void setDiseaseCode(String diseaseCode) {
        this.diseaseCode = diseaseCode;
    }

    public String getCodeAppend() {
        return codeAppend;
    }

    public void setCodeAppend(String codeAppend) {
        this.codeAppend = codeAppend;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getGroupPrefix() {
        return groupPrefix;
    }

    public void setGroupPrefix(String groupPrefix) {
        this.groupPrefix = groupPrefix;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
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