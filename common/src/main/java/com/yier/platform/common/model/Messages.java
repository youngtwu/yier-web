package com.yier.platform.common.model;

import com.yier.platform.common.jsonResponse.BaseJsonObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(value = "公告消息")
public class Messages extends BaseJsonObject {
    @ApiModelProperty(value = "主键标识Id")
    private Long id;
    @ApiModelProperty(value = "类型")
    private Long typeId;
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
    @ApiModelProperty(value = "2表示无详情未查看状态对应1表示查看过，4表示有详情未查看3表示查看过 0备用，5表示删除")
    private Long detail;
    @ApiModelProperty(value = "状态（0正常 1删除 2停用 3冻结）")
    private String status;
    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;
    @ApiModelProperty(value = "修改时间")
    private Date gmtModified;
    @ApiModelProperty(value = "备注信息")
    private String remarks;

    public Messages(Messages messages){
        this.id = messages.id;
        this.typeId = messages.typeId;
        this.otherTypeId = messages.otherTypeId;
        this.title = messages.title;
        this.content = messages.content;
        this.contentUrl = messages.contentUrl;
        this.images = messages.images;
        this.detail = messages.detail;
        this.status = messages.status;
        this.gmtCreate = messages.gmtCreate;
        this.gmtModified = messages.gmtModified;
        this.remarks = messages.remarks;
    }

    public Messages(Long id, Long typeId, Long otherTypeId, String title, String content, String contentUrl, String images, Long detail, String status, Date gmtCreate, Date gmtModified, String remarks) {
        this.id = id;
        this.typeId = typeId;
        this.otherTypeId = otherTypeId;
        this.title = title;
        this.content = content;
        this.contentUrl = contentUrl;
        this.images = images;
        this.detail = detail;
        this.status = status;
        this.gmtCreate = gmtCreate;
        this.gmtModified = gmtModified;
        this.remarks = remarks;
    }

    public Messages() {
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

    public Long getDetail() {
        return detail;
    }

    public void setDetail(Long detail) {
        this.detail = detail;
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