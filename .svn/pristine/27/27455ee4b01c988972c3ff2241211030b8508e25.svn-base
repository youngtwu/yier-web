package com.yier.platform.common.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "关注用户信息扩展表")
public class AttentionUserPo extends AttentionUser {
    @ApiModelProperty(value = "医生姓名")
    private String trueName;
    @ApiModelProperty(value = "医生领域")
    private String field;
    @ApiModelProperty(value = "医生职称描述")
    private String doctorTitle = "";
    @ApiModelProperty(value = "医生急诊人次")
    private Double visit;
    @ApiModelProperty(value = "医生访问量显示")
    private String visitDisplay = "";
    @ApiModelProperty(value = "医生头像")
    private String avatarUrl;

//    private DoctorPo doctorPo;
//
//    public DoctorPo getDoctorPo() {
//        return doctorPo;
//    }
//
//    public void setDoctorPo(DoctorPo doctorPo) {
//        this.doctorPo = doctorPo;
//    }

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

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getDoctorTitle() {
        return doctorTitle;
    }

    public void setDoctorTitle(String doctorTitle) {
        this.doctorTitle = doctorTitle;
    }

    public Double getVisit() {
        return visit;
    }

    public void setVisit(Double visit) {
        this.visit = visit;
    }

    public String getVisitDisplay() {
        if (this.getVisit() == null || this.getVisit().doubleValue() == 0) {
            this.visitDisplay = "0";
        } else {
            this.visitDisplay = this.getVisit().longValue() + "";
        }
        return visitDisplay;
    }

    public void setVisitDisplay(String visitDisplay) {
        this.visitDisplay = visitDisplay;
    }
}
