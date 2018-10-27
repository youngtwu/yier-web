package com.yier.platform.common.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 新闻信息扩展类 model
 */
@ApiModel(value = "新闻信息扩展类")
public class NewsPo extends News {

    @ApiModelProperty(value = "审核信息")
    List<AuditInfo> auditInfos;

    public NewsPo() {

    }

    public NewsPo(News news) {
        super(news);
    }

    public List<AuditInfo> getAuditInfos() {
        return auditInfos;
    }

    public void setAuditInfos(List<AuditInfo> auditInfos) {
        this.auditInfos = auditInfos;
    }
}