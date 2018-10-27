package com.yier.platform.common.model;

import com.yier.platform.common.jsonResponse.BaseJsonObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(value = "医生出诊时间")
public class MedicalDate extends BaseJsonObject {
    @ApiModelProperty(value = "主键ID")
    private Long id;
    @ApiModelProperty(value = "端口类型")
    private String typeId;
    @ApiModelProperty(value = "用户Id")
    private String userId;
    @ApiModelProperty(value = "预约上门就诊时间")
    private Date planTime;
    @ApiModelProperty(value = "周一 周日 描述")
    private String weekInfo;
    @ApiModelProperty(value = "11表示全天，10表示上午，01表示下午")
    private String workTime;
    @ApiModelProperty(value = "工作区间开始时间点")
    private Date workFrom;
    @ApiModelProperty(value = "工作区间结束时间点")
    private Date workTo;
    @ApiModelProperty(value = "需要付费金额 单位是分")
    private Integer payment;
    @ApiModelProperty(value = "计划出诊人数")
    private Integer num;
    @ApiModelProperty(value = "就诊预约人数")
    private Integer appointment;
    @ApiModelProperty(value = "支付环节时锁定人数")
    private Integer lockNum;
    @ApiModelProperty(value = "状态")
    private String status;
    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;
    @ApiModelProperty(value = "修改时间")
    private Date gmtModified;
    @ApiModelProperty(value = "备注信息")
    private String remarks;

    public MedicalDate(Long id, String typeId, String userId, Date planTime, String weekInfo, String workTime, Date workFrom, Date workTo, Integer payment, Integer num, Integer appointment, Integer lockNum, String status, Date gmtCreate, Date gmtModified, String remarks) {
        this.id = id;
        this.typeId = typeId;
        this.userId = userId;
        this.planTime = planTime;
        this.weekInfo = weekInfo;
        this.workTime = workTime;
        this.workFrom = workFrom;
        this.workTo = workTo;
        this.payment = payment;
        this.num = num;
        this.appointment = appointment;
        this.lockNum = lockNum;
        this.status = status;
        this.gmtCreate = gmtCreate;
        this.gmtModified = gmtModified;
        this.remarks = remarks;
    }
    public MedicalDate(MedicalDate record) {
        this.id = record.id;
        this.typeId = record.typeId;
        this.userId = record.userId;
        this.planTime = record.planTime;
        this.weekInfo = record.weekInfo;
        this.workTime = record.workTime;
        this.workFrom = record.workFrom;
        this.workTo = record.workTo;
        this.payment = record.payment;
        this.num = record.num;
        this.appointment = record.appointment;
        this.lockNum = record.lockNum;
        this.status = record.status;
        this.gmtCreate = record.gmtCreate;
        this.gmtModified = record.gmtModified;
        this.remarks = record.remarks;
    }
    public MedicalDate() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getPayment() {
        return payment;
    }

    public void setPayment(Integer payment) {
        this.payment = payment;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getAppointment() {
        return appointment;
    }

    public void setAppointment(Integer appointment) {
        this.appointment = appointment;
    }

    public Integer getLockNum() {
        return lockNum;
    }

    public void setLockNum(Integer lockNum) {
        this.lockNum = lockNum;
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