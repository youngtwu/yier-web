package com.yier.platform.common.requestParam;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(value = "疾病编码请求类")
public class DiseaseCodeRequest extends BaseRequest {
    @ApiModelProperty(value = "疾病Id")
    private Long doctorDiseaseId;
    @ApiModelProperty(value = "用户Id")
    private Long userId;
    @ApiModelProperty(value = "疾病编号Id")
    private Long diseaseCodeId;
    @ApiModelProperty(value = "疾病编号")
    private String diseaseCode;
    @ApiModelProperty(value = "疾病 编号 补充")
    private String codeAppend;
    @ApiModelProperty(value = "疾病名称")
    private String diseaseName;
    @ApiModelProperty(value = "疾病拼音")
    private String pinyin;
    @ApiModelProperty(value = "组前缀")
    private String groupPrefix;
    @ApiModelProperty(value = "简称")
    private String shortName;
    private List<Long> doctorDiseaseIdList;

    public List<Long> getDoctorDiseaseIdList() {
        return doctorDiseaseIdList;
    }

    public void setDoctorDiseaseIdList(List<Long> doctorDiseaseIdList) {
        this.doctorDiseaseIdList = doctorDiseaseIdList;
    }

    public Long getDoctorDiseaseId() {
        return doctorDiseaseId;
    }

    public void setDoctorDiseaseId(Long doctorDiseaseId) {
        this.doctorDiseaseId = doctorDiseaseId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getDiseaseCodeId() {
        return diseaseCodeId;
    }

    public void setDiseaseCodeId(Long diseaseCodeId) {
        this.diseaseCodeId = diseaseCodeId;
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
}
