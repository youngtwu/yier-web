package com.yier.platform.common.requestParam;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
@ApiModel(value = "图片请求类")
public class ImagesRequest extends BaseRequest {
    @ApiModelProperty(value = "图片id")
    private Long imageId;
    @ApiModelProperty(value = "所属APP 端口类型 1亿尔医生 2亿尔药师 3亿尔患者")
    private Long typeId;
    @ApiModelProperty(value = "表示的操作类型 android/ios")
    private String osType;
    @ApiModelProperty(value = "表的标识类型： 1表示New，2表示message，3表示personMessage 4.表示来自于banner 没有库中id，5.广告表的类型，无Id 6.启动页, 无id")
    private Long markId;
    @ApiModelProperty(value = "表的标识类型集合")
    private List<Long> markIds;
    @ApiModelProperty(value = "数据库中的Id")
    private Long databaseId;

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public String getOsType() {
        return osType;
    }

    public void setOsType(String osType) {
        this.osType = osType;
    }

    public Long getMarkId() {
        return markId;
    }

    public void setMarkId(Long markId) {
        this.markId = markId;
    }

    public List<Long> getMarkIds() {
        return markIds;
    }

    public void setMarkIds(List<Long> markIds) {
        this.markIds = markIds;
    }

    public Long getDatabaseId() {
        return databaseId;
    }

    public void setDatabaseId(Long databaseId) {
        this.databaseId = databaseId;
    }
}
