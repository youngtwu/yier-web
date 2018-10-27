package com.yier.platform.common.model;

import com.yier.platform.common.jsonResponse.BaseJsonObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(value = "用药提醒时间")
public class RemindTime extends BaseJsonObject {
    @ApiModelProperty(value = "主键标识Id")
    private Long id;
    @ApiModelProperty(value = "提醒事项ID。关联到t_remind")
    private Long remindId;
    @ApiModelProperty(value = "提醒时间")
    private Date remindTime;
    @ApiModelProperty(value = "删除标志。0-正常，1-已删除")
    private Integer idDeleted;
    @ApiModelProperty(value = "记录创建时间")
    private Date gmtCreate;
    @ApiModelProperty(value = "记录修改时间")
    private Date gmtModified;

    public RemindTime(Long id, Long remindId, Date remindTime, Integer idDeleted, Date gmtCreate, Date gmtModified) {
        this.id = id;
        this.remindId = remindId;
        this.remindTime = remindTime;
        this.idDeleted = idDeleted;
        this.gmtCreate = gmtCreate;
        this.gmtModified = gmtModified;
    }

    public RemindTime() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRemindId() {
        return remindId;
    }

    public void setRemindId(Long remindId) {
        this.remindId = remindId;
    }

    public Date getRemindTime() {
        return remindTime;
    }

    public void setRemindTime(Date remindTime) {
        this.remindTime = remindTime;
    }

    public Integer getIdDeleted() {
        return idDeleted;
    }

    public void setIdDeleted(Integer idDeleted) {
        this.idDeleted = idDeleted;
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
}