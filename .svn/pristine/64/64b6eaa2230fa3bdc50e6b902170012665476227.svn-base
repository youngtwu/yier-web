package com.yier.platform.common.model;

import com.yier.platform.common.jsonResponse.BaseJsonObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(value = "新闻消息banner广告描述")
public class ContentDetail extends BaseJsonObject {
    @ApiModelProperty(value = "主键标识Id")
    private Long id;
    @ApiModelProperty(value = "消息新闻Id")
    private Long contentId;
    @ApiModelProperty(value = "其他辅助类型标识：1表示New，2表示message，3表示personMessage")
    private Long markId;
    @ApiModelProperty(value = "标题")
    private String title;
    @ApiModelProperty(value = "来源")
    private String source;
    @ApiModelProperty(value = "详细内容")
    private String content;
    @ApiModelProperty(value = "相关URL链接")
    private String contentUrl;
    @ApiModelProperty(value = "相关图片")
    private String images;
    @ApiModelProperty(value = "状态（0正常 1删除 2停用）")
    private String status;
    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;
    @ApiModelProperty(value = "修改时间")
    private Date gmtModified;
    @ApiModelProperty(value = "备注信息")
    private String remarks;

    public ContentDetail(Long id, Long contentId, Long markId, String title, String source, String content, String contentUrl, String images, String status, Date gmtCreate, Date gmtModified, String remarks) {
        this.id = id;
        this.contentId = contentId;
        this.markId = markId;
        this.title = title;
        this.source = source;
        this.content = content;
        this.contentUrl = contentUrl;
        this.images = images;
        this.status = status;
        this.gmtCreate = gmtCreate;
        this.gmtModified = gmtModified;
        this.remarks = remarks;
    }

    public ContentDetail() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getContentId() {
        return contentId;
    }

    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }

    public Long getMarkId() {
        return markId;
    }

    public void setMarkId(Long markId) {
        this.markId = markId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
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