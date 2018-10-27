package com.yier.platform.common.model;

import com.yier.platform.common.jsonResponse.BaseJsonObject;
import com.yier.platform.common.po.Image;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

/**
 * 新闻信息 model
 */
@ApiModel(value = "新闻信息")
public class News extends BaseJsonObject {
    @ApiModelProperty(value = "主键ID")
    private Long id;
    @ApiModelProperty(value = "类别分类")
    private Long typeId;
    @ApiModelProperty(value = "其他子类别分类")
    private Long otherTypeId;
    @ApiModelProperty(value = "标题")
    private String title;
    @ApiModelProperty(value = "内容")
    private String content;
    @ApiModelProperty(value = "对应的URL")
    private String contentUrl;
    @ApiModelProperty(value = "对应的图片")
    private String images;
    @ApiModelProperty(value = "状态")
    private String status;
    @ApiModelProperty(value = "创建日期")
    private Date gmtCreate;
    @ApiModelProperty(value = "修改日期")
    private Date gmtModified;
    @ApiModelProperty(value = "备注信息")
    private String remarks;

    private List<Image> imagesList;

    public List<Image> getImagesList() {
        return imagesList;
    }

    public void setImagesList(List<Image> imagesList) {
        this.imagesList = imagesList;
    }

    public News(Long id, Long typeId, Long otherTypeId, String title, String content, String contentUrl, String images, String status, Date gmtCreate, Date gmtModified, String remarks) {
        this.id = id;
        this.typeId = typeId;
        this.otherTypeId = otherTypeId;
        this.title = title;
        this.content = content;
        this.contentUrl = contentUrl;
        this.images = images;
        this.status = status;
        this.gmtCreate = gmtCreate;
        this.gmtModified = gmtModified;
        this.remarks = remarks;
    }

    public News(News news) {
        this.id = news.id;
        this.typeId = news.typeId;
        this.otherTypeId = news.otherTypeId;
        this.title = news.title;
        this.content = news.content;
        this.contentUrl = news.contentUrl;
        this.images = news.images;
        this.status = news.status;
        this.gmtCreate = news.gmtCreate;
        this.gmtModified = news.gmtModified;
        this.remarks = news.remarks;
    }

    public News() {
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