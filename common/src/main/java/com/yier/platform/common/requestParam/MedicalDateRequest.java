package com.yier.platform.common.requestParam;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@ApiModel(value = "上门就诊医生就诊日期查询请求类")
public class MedicalDateRequest extends BaseRequest {
    @ApiModelProperty(value = "医生就诊日期ID")
    private Long medicalDateId;
    @ApiModelProperty(value = "端口ID,医生是2 药师是3")
    private String typeId;
    @ApiModelProperty(value = "用户ID,医生Id 药师Id ")
    private String userId;
    @ApiModelProperty(value = "用户ID,医生Id 药师Id ")
    private Date planTime;
    @ApiModelProperty(value = "计划任务开始时间")
    private Date planTimeBegin;
    @ApiModelProperty(value = "计划任务结束时间")
    private Date planTimeEnd;
    @ApiModelProperty(value = "周几描述")
    private String weekInfo;
    @ApiModelProperty(value = "上下午描述")
    private String workTime;
    @ApiModelProperty(value = "工作区间开始时间点")
    private Date workFrom;
    @ApiModelProperty(value = "工作区间结束时间点")
    private Date workTo;

    @DateTimeFormat(pattern = "yyyy-MM-dd") // 出参
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8") // 界面入参
    private Date queryDate;//查询日期
    @ApiModelProperty(value = "预约量考虑")
    private Integer appointmentNum;

    public Long getMedicalDateId() {
        return medicalDateId;
    }

    public void setMedicalDateId(Long medicalDateId) {
        this.medicalDateId = medicalDateId;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getPlanTime() {
        return planTime;
    }

    public void setPlanTime(Date planTime) {
        this.planTime = planTime;
    }

    public Date getPlanTimeBegin() {
        return planTimeBegin;
    }

    public void setPlanTimeBegin(Date planTimeBegin) {
        this.planTimeBegin = planTimeBegin;
    }

    public Date getPlanTimeEnd() {
        return planTimeEnd;
    }

    public void setPlanTimeEnd(Date planTimeEnd) {
        this.planTimeEnd = planTimeEnd;
    }

    public String getWeekInfo() {
        return weekInfo;
    }

    public void setWeekInfo(String weekInfo) {
        this.weekInfo = weekInfo;
    }

    public String getWorkTime() {
        return workTime;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }

    public Date getWorkFrom() {
        return workFrom;
    }

    public void setWorkFrom(Date workFrom) {
        this.workFrom = workFrom;
    }

    public Date getWorkTo() {
        return workTo;
    }

    public void setWorkTo(Date workTo) {
        this.workTo = workTo;
    }

    public Date getQueryDate() {
        return queryDate;
    }

    public void setQueryDate(Date queryDate) {
        this.queryDate = queryDate;
    }

    public Integer getAppointmentNum() {
        return appointmentNum;
    }

    public void setAppointmentNum(Integer appointmentNum) {
        this.appointmentNum = appointmentNum;
    }
}
