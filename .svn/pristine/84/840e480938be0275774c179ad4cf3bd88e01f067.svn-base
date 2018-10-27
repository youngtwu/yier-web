package com.yier.platform.common.requestParam;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(value = "药物服药提醒请求")
public class DrugCallRequest extends BaseRequest {
    @ApiModelProperty(value = "药物提醒ID")
    private Long drugCallId;
    @ApiModelProperty(value = "客户端标识。1-患者端，2-医生端，3-药师端")
    private Long typeId;
    @ApiModelProperty(value = "用户ID")
    private Long userId;
    @ApiModelProperty(value = "药品ID")
    private Long drugId;
    @ApiModelProperty(value = "药品信息")
    private Long drugInfo;
    @ApiModelProperty(value = "服药时间标题")
    private String caption;
    @ApiModelProperty(value = "服药开始时间")
    private Date startDate;
    @ApiModelProperty(value = "服药结束时间")
    private Date endDate;
    @ApiModelProperty(value = "任务时间")
    private Date schedule;
    @ApiModelProperty(value = "提醒类型 0表示每天")
    private Long callType;

    public Date getSchedule() {
        return schedule;
    }

    public void setSchedule(Date schedule) {
        this.schedule = schedule;
    }

    public Long getDrugCallId() {
        return drugCallId;
    }

    public void setDrugCallId(Long drugCallId) {
        this.drugCallId = drugCallId;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getDrugId() {
        return drugId;
    }

    public void setDrugId(Long drugId) {
        this.drugId = drugId;
    }

    public Long getDrugInfo() {
        return drugInfo;
    }

    public void setDrugInfo(Long drugInfo) {
        this.drugInfo = drugInfo;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Long getCallType() {
        return callType;
    }

    public void setCallType(Long callType) {
        this.callType = callType;
    }
}
