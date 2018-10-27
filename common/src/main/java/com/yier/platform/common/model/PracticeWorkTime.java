package com.yier.platform.common.model;

import com.yier.platform.common.jsonResponse.BaseJsonObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(value = "医生或药师执业点工作时间信息")
public class PracticeWorkTime extends BaseJsonObject {
    @ApiModelProperty(value = "主键标识Id")
    private Long id;
    @ApiModelProperty(value = "省市ID。关联到t_practice_point")
    private Long practicePointId;
    @ApiModelProperty(value = "关注客户端标识。1-患者端，2-医生端，3-药师端")
    private Long typeId;
    @ApiModelProperty(value = "医生ID，关联到t_doctor")
    private Long doctorId;
    @ApiModelProperty(value = "1表示星期一，7表示星期日")
    private String workDay;
    @ApiModelProperty(value = "11表示全天，10表示上午，01表示下午")
    private String workTime;
    @ApiModelProperty(value = "状态（0正常 1删除 2停用 3冻结）")
    private String status;
    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;
    @ApiModelProperty(value = "修改时间")
    private Date gmtModified;
    @ApiModelProperty(value = "备注信息")
    private String remarks;

    public PracticeWorkTime(Long id, Long practicePointId, Long typeId, Long doctorId, String workDay, String workTime, String status, Date gmtCreate, Date gmtModified, String remarks) {
        this.id = id;
        this.practicePointId = practicePointId;
        this.typeId = typeId;
        this.doctorId = doctorId;
        this.workDay = workDay;
        this.workTime = workTime;
        this.status = status;
        this.gmtCreate = gmtCreate;
        this.gmtModified = gmtModified;
        this.remarks = remarks;
    }

    public PracticeWorkTime() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPracticePointId() {
        return practicePointId;
    }

    public void setPracticePointId(Long practicePointId) {
        this.practicePointId = practicePointId;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public String getWorkDay() {
        return workDay;
    }

    public void setWorkDay(String workDay) {
        this.workDay = workDay;
    }

    public String getWorkTime() {
        return workTime;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
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