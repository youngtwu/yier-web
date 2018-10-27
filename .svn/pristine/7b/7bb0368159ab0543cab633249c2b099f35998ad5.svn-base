package com.yier.platform.common.model;

import com.yier.platform.common.jsonResponse.BaseJsonObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
@ApiModel(value = "关注用户信息表")
public class AttentionUser extends BaseJsonObject {
    @ApiModelProperty(value = "主键标识Id")
    private Long id;
    @ApiModelProperty(value = "关注端")
    private Long typeId;
    @ApiModelProperty(value = "关注Id")
    private Long userId;
    @ApiModelProperty(value = "被关注端")
    private Long attentionTypeId;
    @ApiModelProperty(value = "被关注端Id")
    private Long attentionUserId;
    @ApiModelProperty(value = "关注次数")
    private Integer attention;
    @ApiModelProperty(value = "状态（0正常 1删除 2停用 3冻结）")
    private String status;
    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;
    @ApiModelProperty(value = "修改时间")
    private Date gmtModified;
    @ApiModelProperty(value = "备注信息")
    private String remarks;

    public AttentionUser(Long id, Long typeId, Long userId, Long attentionTypeId, Long attentionUserId, Integer attention, String status, Date gmtCreate, Date gmtModified, String remarks) {
        this.id = id;
        this.typeId = typeId;
        this.userId = userId;
        this.attentionTypeId = attentionTypeId;
        this.attentionUserId = attentionUserId;
        this.attention = attention;
        this.status = status;
        this.gmtCreate = gmtCreate;
        this.gmtModified = gmtModified;
        this.remarks = remarks;
    }

    public AttentionUser() {
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

    public Long getAttentionTypeId() {
        return attentionTypeId;
    }

    public void setAttentionTypeId(Long attentionTypeId) {
        this.attentionTypeId = attentionTypeId;
    }

    public Long getAttentionUserId() {
        return attentionUserId;
    }

    public void setAttentionUserId(Long attentionUserId) {
        this.attentionUserId = attentionUserId;
    }

    public Integer getAttention() {
        return attention;
    }

    public void setAttention(Integer attention) {
        this.attention = attention;
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