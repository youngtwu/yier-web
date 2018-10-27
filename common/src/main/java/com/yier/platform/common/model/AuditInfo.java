package com.yier.platform.common.model;

import com.yier.platform.common.jsonResponse.BaseJsonObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(value = "审核信息表")
public class AuditInfo extends BaseJsonObject {
    @ApiModelProperty(value = "主键标识Id")
    private Long id;
    @ApiModelProperty(value = "审核类型,1医院, 2医生，3药师，4咨询，5通知，6手机号停用")
    private Integer auditType;
    @ApiModelProperty(value = "审核表主键")
    private Long tableId;
    @ApiModelProperty(value = "操作ID")
    private Long userId;
    @ApiModelProperty(value = "操作人")
    private String userName;
    @ApiModelProperty(value = "审核意见")
    private String content;
    @ApiModelProperty(value = "操作状态 A未审核  B未复审 C未通过 D通过")
    private String operate;
    @ApiModelProperty(value = "状态（0正常有效 1删除 4无效）")
    private String status;
    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;
    @ApiModelProperty(value = "修改时间")
    private Date gmtModified;
    @ApiModelProperty(value = "备注")
    private String remarks;

    public AuditInfo(Long id, Integer auditType, Long tableId, Long userId, String userName, String content, String operate, String status, Date gmtCreate, Date gmtModified, String remarks) {
        this.id = id;
        this.auditType = auditType;
        this.tableId = tableId;
        this.userId = userId;
        this.userName = userName;
        this.content = content;
        this.operate = operate;
        this.status = status;
        this.gmtCreate = gmtCreate;
        this.gmtModified = gmtModified;
        this.remarks = remarks;
    }

    public AuditInfo() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAuditType() {
        return auditType;
    }

    public void setAuditType(Integer auditType) {
        this.auditType = auditType;
    }

    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOperate() {
        return operate;
    }

    public void setOperate(String operate) {
        this.operate = operate;
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