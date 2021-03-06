package com.yier.platform.common.model;

import com.yier.platform.common.jsonResponse.BaseJsonObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

@ApiModel(value = "个人消息")
public class MessagesPerson extends BaseJsonObject {
    @ApiModelProperty(value = "主键标识Id")
    private Long id;
    @ApiModelProperty(value = "类型")
    private Long typeId;
    @ApiModelProperty(value = "用户Id")
    private Long userId;
    @ApiModelProperty(value = "其他辅助类型标识")
    private Long otherTypeId;
    @ApiModelProperty(value = "标题")
    private String title;
    @ApiModelProperty(value = "摘要内容")
    private String content;
    @ApiModelProperty(value = "相关URL链接")
    private String contentUrl;
    @ApiModelProperty(value = "相关图片")
    private String images;
    @ApiModelProperty(value = "默认等级，grade 消息的等级，1 表示查看过无详情，2表示未查看过无详情,3表示查看过有详情,4 表未读过有详情 ，5 表示删除")
    private Long grade;
    @ApiModelProperty(value = "状态（0正常 1删除 2停用）")
    private String status;
    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;
    @ApiModelProperty(value = "修改时间")
    private Date gmtModified;
    @ApiModelProperty(value = "备注信息")
    private String remarks;
    @ApiModelProperty(value = "图片集合")
    private List<Long> messageIdList;
    @ApiModelProperty(value = "信息公告集合")
    public List<Long> getMessageIdList() {
        return messageIdList;
    }

    public void setMessageIdList(List<Long> messageIdList) {
        this.messageIdList = messageIdList;
    }

    public MessagesPerson(Long id, Long typeId, Long userId, Long otherTypeId, String title, String content, String contentUrl, String images, Long grade, String status, Date gmtCreate, Date gmtModified, String remarks) {
        this.id = id;
        this.typeId = typeId;
        this.userId = userId;
        this.otherTypeId = otherTypeId;
        this.title = title;
        this.content = content;
        this.contentUrl = contentUrl;
        this.images = images;
        this.grade = grade;
        this.status = status;
        this.gmtCreate = gmtCreate;
        this.gmtModified = gmtModified;
        this.remarks = remarks;
    }

    public MessagesPerson() {
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

    public Long getOtherTypeId() {
        return otherTypeId;
    }

    public void setOtherTypeId(Long otherTypeId) {
        this.otherTypeId = otherTypeId;
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

    public String getContentUrl() {
        return contentUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public Long getGrade() {
        return grade;
    }

    public void setGrade(Long grade) {
        this.grade = grade;
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