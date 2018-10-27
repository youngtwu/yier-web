package com.yier.platform.common.requestParam;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;
@ApiModel(value = "公告消息请求类")
public class MessagesRequest extends BaseRequest {
    @ApiModelProperty(value = "查询日期")
    private Date queryDate;//查询日期
    @ApiModelProperty(value = "消息id")
    private Long messageId;//消息id
    @ApiModelProperty(value = "用户ID")
    private Long userId;//用户ID
    @ApiModelProperty(value = "类型ID")
    private Long typeId;//类型ID
    @ApiModelProperty(value = "其他类型信息 其他辅助类型标识")
    private Long otherTypeId;//其他类型信息 其他辅助类型标识
    @ApiModelProperty(value = "等级信息 0 查看，1 详情 2 删除")
    private Long grade;//等级信息 0 查看，1 详情 2 删除
    @ApiModelProperty(value = "默认等级，grade 消息的等级，1 表示查看过无详情，2表示未查看过无详情,3表示查看过有详情,4 表未读过有详情 ，5 表示删除")
    private List<Long> gradeList;
    @ApiModelProperty(value = "标题")
    private String title;//标题

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Long> getGradeList() {
        return gradeList;
    }

    public void setGradeList(List<Long> gradeList) {
        this.gradeList = gradeList;
    }

    public Date getQueryDate() {
        return queryDate;
    }

    public void setQueryDate(Date queryDate) {
        this.queryDate = queryDate;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public Long getGrade() {
        return grade;
    }

    public void setGrade(Long grade) {
        this.grade = grade;
    }
}
