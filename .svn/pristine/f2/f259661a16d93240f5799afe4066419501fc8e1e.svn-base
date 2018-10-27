package com.yier.platform.common.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Lists;
import com.yier.platform.common.po.Image;
import com.yier.platform.common.util.JsonUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@ApiModel(value = "消息公告的扩张信息")
public class MessagesPo extends Messages {
    @ApiModelProperty(value = "消息的等级，0 表示空缺   1查看无详情，2表示未查看无详情，3 表示已经查看有详情,4 表有详情未读 ，5 表示删除 [2,4]表示未查看无详情")
    private Long grade;
    @ApiModelProperty(value = "消息公告总数")
    private int total;
    @ApiModelProperty(value = "审核信息")
    List<AuditInfo> auditInfos;

    public MessagesPo() {

    }

    public MessagesPo(Messages messages) {
        super(messages);
    }

    public List<AuditInfo> getAuditInfos() {
        return auditInfos;
    }

    public void setAuditInfos(List<AuditInfo> auditInfos) {
        this.auditInfos = auditInfos;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    private List<Image> imagesList = Lists.newArrayList();

    public List<Image> getImagesList() {
        String images = this.getImages();//json存储的图片信息，转换成列表对象 ，如果对象Image存在构造函数，一定要有可
        if (StringUtils.isNotBlank(images)) {
            List<Image> lists = JsonUtils.fromJson(images, new TypeReference<List<Image>>() {
            });
            if (lists == null) {
            } else {
                imagesList = lists;
            }
        }
        return imagesList;
    }

    public void setImagesList(List<Image> imagesList) {
        this.imagesList = imagesList;
    }

    public Long getGrade() {
        return grade;
    }

    public void setGrade(Long grade) {
        this.grade = grade;
    }
}
