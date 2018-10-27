package com.yier.platform.common.po;

import com.yier.platform.common.jsonResponse.BaseJsonObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "通用图片信息")
public class Image  extends BaseJsonObject {
    private static final long serialVersionUID = -8861338234582066878L;
    /**图片名称*/
    @ApiModelProperty(value = "图片名称")
    private String name = "";
    /**图片名称*/
    @ApiModelProperty(value = "描述")
    private String description = "";
    /**图片url*/
    @ApiModelProperty(value = "图片url")
    private String url = "";
    /**图片内容链接*/
    @ApiModelProperty(value = "图片内容跳转url")
    private String contentUrl = "";
    /**图片高度*/
    @ApiModelProperty(value = "图片高度")
    private int height = 0;
    /**图片宽度*/
    @ApiModelProperty(value = "图片宽度")
    private int width = 0;
    /** 图片类型 */
    @ApiModelProperty(value = "图片类型")
    private Integer type;

    public Image(){}//一定要有默认构造函数，这样json才能够对象话
    public Image(String name,String description,String url,String contentUrl){
        this.name = name;
        this.description = description;
        this.contentUrl = contentUrl;
        this.url = url;
        this.type = 1;
        this.height = 500;
        this.width = 800;
    }

    public String getContentUrl() {
        return contentUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
