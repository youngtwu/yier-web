package com.yier.platform.common.model;

import com.yier.platform.common.jsonResponse.BaseJsonObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(value = "显示图片信息")
public class Images extends BaseJsonObject {
    @ApiModelProperty(value = "主键标识Id")
    private Long id;
    @ApiModelProperty(value = "所属APP （端口类型 1亿尔医生 2亿尔药师 3亿尔患者）")
    private Long typeId;
    @ApiModelProperty(value = "表的标识类型： 1表示New，2表示message，3表示personMessage 4.表示来自于banner 没有库中id，5.广告表的类型，无Id 6.启动页 无id")
    private Long markId;
    @ApiModelProperty(value = "数据库中的Id")
    private Long databaseId;
    @ApiModelProperty(value = "表示的操作类型 android/ios")
    private String osType;
    @ApiModelProperty(value = "标题")
    private String title;
    @ApiModelProperty(value = "标题名称")
    private String name;
    @ApiModelProperty(value = "图片宽")
    private Integer width;
    @ApiModelProperty(value = "图片高")
    private Integer height;
    @ApiModelProperty(value = "摘要内容")
    private String description;
    @ApiModelProperty(value = "相关图片链接URL链接")
    private String url;
    @ApiModelProperty(value = "其他相关图片链接URL链接")
    private String otherUrl;
    @ApiModelProperty(value = "相关内容链接URL链接")
    private String contentUrl;
    @ApiModelProperty(value = "显示顺序")
    private Integer displayOrder;
    @ApiModelProperty(value = "状态（0正常 1删除 2停用）")
    private String status;
    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;
    @ApiModelProperty(value = "修改时间")
    private Date gmtModified;
    @ApiModelProperty(value = "备注信息")
    private String remarks;

    public Images(Long id, Long typeId, Long markId, Long databaseId, String osType, String title, String name, Integer width, Integer height, String description, String url, String otherUrl, String contentUrl, Integer displayOrder, String status, Date gmtCreate, Date gmtModified, String remarks) {
        this.id = id;
        this.typeId = typeId;
        this.markId = markId;
        this.databaseId = databaseId;
        this.osType = osType;
        this.title = title;
        this.name = name;
        this.width = width;
        this.height = height;
        this.description = description;
        this.url = url;
        this.otherUrl = otherUrl;
        this.contentUrl = contentUrl;
        this.displayOrder = displayOrder;
        this.status = status;
        this.gmtCreate = gmtCreate;
        this.gmtModified = gmtModified;
        this.remarks = remarks;
    }

    public Images() {
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

    public Long getMarkId() {
        return markId;
    }

    public void setMarkId(Long markId) {
        this.markId = markId;
    }

    public Long getDatabaseId() {
        return databaseId;
    }

    public void setDatabaseId(Long databaseId) {
        this.databaseId = databaseId;
    }

    public String getOsType() {
        return osType;
    }

    public void setOsType(String osType) {
        this.osType = osType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getOtherUrl() {
        return otherUrl;
    }

    public void setOtherUrl(String otherUrl) {
        this.otherUrl = otherUrl;
    }

    public String getContentUrl() {
        return contentUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
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