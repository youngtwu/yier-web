package com.yier.platform.common.requestParam;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(value = "计划任务请求类")
public class TaskScheduleRequest extends BaseRequest {
    @ApiModelProperty(value = "发送端")
    private Long typeId;
    @ApiModelProperty(value = "发送端Id")
    private Long userId;
    @ApiModelProperty(value = "任务类型")
    private Long taskType;
    @ApiModelProperty(value = "任务子类型")
    private Long subTaskType;
    @ApiModelProperty(value = "任务时间")
    private Date schedule;
    @ApiModelProperty(value = "任务时间")
    private Date scheduleBegin;
    @ApiModelProperty(value = "任务时间")
    private Date scheduleEnd;
    @ApiModelProperty(value = "考虑附加字段信息")
    private String considerAttach;

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

    public Long getTaskType() {
        return taskType;
    }

    public void setTaskType(Long taskType) {
        this.taskType = taskType;
    }

    public Long getSubTaskType() {
        return subTaskType;
    }

    public void setSubTaskType(Long subTaskType) {
        this.subTaskType = subTaskType;
    }

    public Date getSchedule() {
        return schedule;
    }

    public void setSchedule(Date schedule) {
        this.schedule = schedule;
    }

    public Date getScheduleBegin() {
        return scheduleBegin;
    }

    public void setScheduleBegin(Date scheduleBegin) {
        this.scheduleBegin = scheduleBegin;
    }

    public Date getScheduleEnd() {
        return scheduleEnd;
    }

    public void setScheduleEnd(Date scheduleEnd) {
        this.scheduleEnd = scheduleEnd;
    }

    public String getConsiderAttach() {
        return considerAttach;
    }

    public void setConsiderAttach(String considerAttach) {
        this.considerAttach = considerAttach;
    }
}
