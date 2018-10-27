package com.yier.platform.common.requestParam;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@ApiModel(value = "取药查询请求类")
public class FeedbackRequest extends BaseRequest {
    @ApiModelProperty(value = "反馈内容ID")
    private Long feedbackId;//反馈内容ID
    @ApiModelProperty(value = "用户id")
    private Long userId;//用户id
    @ApiModelProperty(value = "客户端标识。1-患者端，2-医生端，3-药师端")
    private Long clientTypeId;//客户端标识。1-患者端，2-医生端，3-药师端
    @ApiModelProperty(value = "是否恢复 0,1 ,其他")
    private Integer visit;//是否恢复 0,1 ,其他
    @ApiModelProperty(value = "问题节点")
    private Long parentId;//问题节点
    @ApiModelProperty(value = "查询日期")
    //    date类型字段的序列化和反序列化
    //@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ", locale = "zh", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd") // 出参
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8") // 界面入参
    private Date queryDate;//查询日期

    public Long getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(Long feedbackId) {
        this.feedbackId = feedbackId;
    }

    public Date getQueryDate() {
        return queryDate;
    }

    public void setQueryDate(Date queryDate) {
        this.queryDate = queryDate;
    }

    public Long getClientTypeId() {
        return clientTypeId;
    }

    public void setClientTypeId(Long clientTypeId) {
        this.clientTypeId = clientTypeId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getVisit() {
        return visit;
    }

    public void setVisit(Integer visit) {
        this.visit = visit;
    }
}
