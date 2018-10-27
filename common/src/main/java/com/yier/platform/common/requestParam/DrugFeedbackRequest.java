package com.yier.platform.common.requestParam;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "用户使用药品意见反馈请求类")
public class DrugFeedbackRequest extends BaseRequest {
    @ApiModelProperty(value = "药品Id")
    private Long drugId;
    @ApiModelProperty(value = "药品名称")
    private String drugName;
    @ApiModelProperty(value = "疾病Id")
    private Long diseaseId;
    @ApiModelProperty(value = "患者Id")
    private Long patientId;
    @ApiModelProperty(value = "成绩")
    private Integer score;

    public Long getDrugId() {
        return drugId;
    }

    public void setDrugId(Long drugId) {
        this.drugId = drugId;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public Long getDiseaseId() {
        return diseaseId;
    }

    public void setDiseaseId(Long diseaseId) {
        this.diseaseId = diseaseId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
