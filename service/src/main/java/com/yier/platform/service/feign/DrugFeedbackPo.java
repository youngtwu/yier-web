package com.yier.platform.service.feign;

import cc.ccae.yry.service.drug.query.sdk.model.Drug;
import cc.ccae.yry.service.drug.query.sdk.model.DrugFeedback;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class DrugFeedbackPo extends DrugFeedback implements Serializable {
    @ApiModelProperty(value = "患者姓名")
    private String trueName ;//患者姓名
    @ApiModelProperty(value = "头像URL")
    private String avatarUrl ;//头像URL
    @ApiModelProperty(value = "周期")
    private String cycle;
    @ApiModelProperty(value = "药品详情")
    private Drug drug;
    public DrugFeedbackPo(DrugFeedback drugFeedback){
        this.setDiseaseName(drugFeedback.getDiseaseName());
        this.setDescription(drugFeedback.getDescription());
        this.setCountPreDay(drugFeedback.getCountPreDay());
        this.setDiseaseId(drugFeedback.getDiseaseId());
        this.setDrugId(drugFeedback.getDrugId());
        this.setDrugName(drugFeedback.getDrugName());
        this.setEachHave(drugFeedback.getEachHave());
        this.setId(drugFeedback.getId());
        this.setEndDate(drugFeedback.getEndDate());
        this.setPatientId(drugFeedback.getPatientId());
        this.setEachHaveUnit(drugFeedback.getEachHaveUnit());
        this.setScore(drugFeedback.getScore());
        this.setStartDate(drugFeedback.getStartDate());
        this.setCreatedDate(drugFeedback.getCreatedDate());
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getCycle() {
        return cycle;
    }

    public void setCycle(String cycle) {
        this.cycle = cycle;
    }

    public Drug getDrug() {
        return drug;
    }

    public void setDrug(Drug drug) {
        this.drug = drug;
    }
}
