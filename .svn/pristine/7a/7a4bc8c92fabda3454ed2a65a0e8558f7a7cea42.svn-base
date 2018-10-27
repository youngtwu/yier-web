package com.yier.platform.common.model;

import com.yier.platform.common.jsonResponse.BaseJsonObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(value = "药品服药提醒 分配明确的具体时间包含计划任务")
public class DrugCallTask extends BaseJsonObject {
    @ApiModelProperty(value = "主键标识Id")
    private Long id;
    @ApiModelProperty(value = "客户端标识。1-患者端，2-医生端，3-药师端")
    private Long typeId;
    @ApiModelProperty(value = "关注用户ID,关联t_doctor.id，t_patient.id,t_pharmacy.id")
    private Long userId;
    @ApiModelProperty(value = "任务类型 2表示用药提醒")
    private Long taskType;
    @ApiModelProperty(value = "附加值，比如t_drug_call.id")
    private Long drugCallId;
    @ApiModelProperty(value = "任务子类型 药物ID t_drug_call.drug_id")
    private Long drugId;
    @ApiModelProperty(value = "其他附加信息 药名 t_drug_call.drug_info")
    private String drugInfo;
    @ApiModelProperty(value = "任务描述 服药时间标题 t_drug_call.title")
    private String caption;
    @ApiModelProperty(value = "服药标题具体时间 t_drug_call.type+date+value1,2  t_drug_call.title_time")
    private Date captionTime;
    @ApiModelProperty(value = "计划执行时间  t_drug_call_task.title_time+remind_minutes")
    private Date schedule;
    @ApiModelProperty(value = "目前状态状态（0正常 1已经服药  2没有服药）")
    private String currentStatus;
    @ApiModelProperty(value = "执行结果")
    private String taskResult;
    @ApiModelProperty(value = "状态（0正常 1已经处理 2用户点击取消 3 其他）")
    private String status;
    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;
    @ApiModelProperty(value = "修改时间")
    private Date gmtModified;
    @ApiModelProperty(value = "备注信息")
    private String remarks;

    public DrugCallTask(Long id, Long typeId, Long userId, Long taskType, Long drugCallId, Long drugId, String drugInfo, String caption, Date captionTime, Date schedule, String currentStatus, String taskResult, String status, Date gmtCreate, Date gmtModified, String remarks) {
        this.id = id;
        this.typeId = typeId;
        this.userId = userId;
        this.taskType = taskType;
        this.drugCallId = drugCallId;
        this.drugId = drugId;
        this.drugInfo = drugInfo;
        this.caption = caption;
        this.captionTime = captionTime;
        this.schedule = schedule;
        this.currentStatus = currentStatus;
        this.taskResult = taskResult;
        this.status = status;
        this.gmtCreate = gmtCreate;
        this.gmtModified = gmtModified;
        this.remarks = remarks;
    }

    public DrugCallTask() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getTaskType() {
        return taskType;
    }

    public void setTaskType(Long taskType) {
        this.taskType = taskType;
    }

    public Long getDrugCallId() {
        return drugCallId;
    }

    public void setDrugCallId(Long drugCallId) {
        this.drugCallId = drugCallId;
    }

    public Long getDrugId() {
        return drugId;
    }

    public void setDrugId(Long drugId) {
        this.drugId = drugId;
    }

    public String getDrugInfo() {
        return drugInfo;
    }

    public void setDrugInfo(String drugInfo) {
        this.drugInfo = drugInfo;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public Date getCaptionTime() {
        return captionTime;
    }

    public void setCaptionTime(Date captionTime) {
        this.captionTime = captionTime;
    }

    public Date getSchedule() {
        return schedule;
    }

    public void setSchedule(Date schedule) {
        this.schedule = schedule;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public String getTaskResult() {
        return taskResult;
    }

    public void setTaskResult(String taskResult) {
        this.taskResult = taskResult;
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