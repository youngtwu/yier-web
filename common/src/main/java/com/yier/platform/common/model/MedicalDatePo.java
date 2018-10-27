package com.yier.platform.common.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

@ApiModel(value = "医生出诊时间扩展类")
public class MedicalDatePo extends MedicalDate {
    @ApiModelProperty(value = "是否可以预约")
    private Boolean isShowUpdate;
    @ApiModelProperty(value = "目前可预约数量")
    private int appointmentNum;
    @ApiModelProperty(value = "上午可预约数量")
    private Integer numAm = 0 ;
    @ApiModelProperty(value = "下午可预约数量")
    private Integer numPm = 0;
    @ApiModelProperty(value = "上午预约金额")
    private Integer paymentAm = 0;
    @ApiModelProperty(value = "下午可预约金额")
    private Integer paymentPm = 0;

    @ApiModelProperty(value = "计划任务开始时间")
    private String planTimeBegin;
    @ApiModelProperty(value = "计划任务结束时间")
    private String planTimeEnd;
    @ApiModelProperty(value = "考虑周几")
    private List<String> weekTimeList;
    @ApiModelProperty(value = "除外时间")
    private List<String> exceptTimeList;

    public Integer getNumAm() {
        return numAm;
    }

    public void setNumAm(Integer numAm) {
        this.numAm = numAm;
    }

    public Integer getNumPm() {
        return numPm;
    }

    public void setNumPm(Integer numPm) {
        this.numPm = numPm;
    }

    public Integer getPaymentAm() {
        return paymentAm;
    }

    public void setPaymentAm(Integer paymentAm) {
        this.paymentAm = paymentAm;
    }

    public Integer getPaymentPm() {
        return paymentPm;
    }

    public void setPaymentPm(Integer paymentPm) {
        this.paymentPm = paymentPm;
    }

    public int getAppointmentNum() {
        return appointmentNum;
    }

    public void setAppointmentNum(int appointmentNum) {
        this.appointmentNum = appointmentNum;
    }

    public String getPlanTimeBegin() {
        return planTimeBegin;
    }

    public void setPlanTimeBegin(String planTimeBegin) {
        this.planTimeBegin = planTimeBegin;
    }

    public String getPlanTimeEnd() {
        return planTimeEnd;
    }

    public void setPlanTimeEnd(String planTimeEnd) {
        this.planTimeEnd = planTimeEnd;
    }

    public List<String> getWeekTimeList() {
        return weekTimeList;
    }

    public void setWeekTimeList(List<String> weekTimeList) {
        this.weekTimeList = weekTimeList;
    }

    public List<String> getExceptTimeList() {
        return exceptTimeList;
    }

    public void setExceptTimeList(List<String> exceptTimeList) {
        this.exceptTimeList = exceptTimeList;
    }

    public Boolean getShowUpdate() {
        return isShowUpdate;
    }

    public void setShowUpdate(Boolean showUpdate) {
        isShowUpdate = showUpdate;
    }
}
