package com.yier.platform.common.model;

import com.yier.platform.common.jsonResponse.BaseJsonObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(value = "用户意见反馈")
public class Feedback extends BaseJsonObject {
    @ApiModelProperty(value = "主键Id")
    private Long id;
    @ApiModelProperty(value = "用户ID，根据客户端标识关联到t_patient,t_doctor,t_pharmacist")
    private Long userId;
    @ApiModelProperty(value = "客户端标识。1-患者端，2-医生端，3-药师端")
    private Long clientTypeId;
    @ApiModelProperty(value = "反馈途径。0-APP，1-预留")
    private Long wayId;
    @ApiModelProperty(value = "标题")
    private String title;
    @ApiModelProperty(value = "反馈内容")
    private String content;
    @ApiModelProperty(value = "相关的节点ID，父节点")
    private Long parentId;
    @ApiModelProperty(value = "父节点id列表，使用逗号作为分割")
    private String parentIds;
    @ApiModelProperty(value = "主键Id")
    private Integer visit;
    @ApiModelProperty(value = "状态（0正常 1删除 2停用 3冻结）")
    private String status;
    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;
    @ApiModelProperty(value = "修改时间")
    private Date gmtModified;
    @ApiModelProperty(value = "备注信息")
    private String remarks;

    public Feedback(Long id, Long userId, Long clientTypeId, Long wayId, String title, String content, Long parentId, String parentIds, Integer visit, String status, Date gmtCreate, Date gmtModified, String remarks) {
        this.id = id;
        this.userId = userId;
        this.clientTypeId = clientTypeId;
        this.wayId = wayId;
        this.title = title;
        this.content = content;
        this.parentId = parentId;
        this.parentIds = parentIds;
        this.visit = visit;
        this.status = status;
        this.gmtCreate = gmtCreate;
        this.gmtModified = gmtModified;
        this.remarks = remarks;
    }

    public Feedback() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getClientTypeId() {
        return clientTypeId;
    }

    public void setClientTypeId(Long clientTypeId) {
        this.clientTypeId = clientTypeId;
    }

    public Long getWayId() {
        return wayId;
    }

    public void setWayId(Long wayId) {
        this.wayId = wayId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

    public Integer getVisit() {
        return visit;
    }

    public void setVisit(Integer visit) {
        this.visit = visit;
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