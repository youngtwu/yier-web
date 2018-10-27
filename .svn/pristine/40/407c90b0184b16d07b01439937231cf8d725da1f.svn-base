package com.yier.platform.common.requestParam;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(value = "聊天状态记录请求类")
public class ChatStateRequest extends BaseRequest {
    @ApiModelProperty(value = "patient1 doctor2 pharmacist3 user4 两个拼接的完整会话")
    private String chatSpace;
    @ApiModelProperty(value = "最后一条记录")
    private Long lastId;
    @ApiModelProperty(value = "聊天状态（0开通 1不开通））")
    private String chat;
    @ApiModelProperty(value = "修改时间")
    private Date gmtModified;
    @ApiModelProperty(value = "备注信息")
    private String remarks;
    //@ApiModelProperty(value = "")
    private Integer field;
    //@ApiModelProperty(value = "")
    private Integer amount;
    @ApiModelProperty(value = "发送端")
    private Long typeId;//发送端
    @ApiModelProperty(value = "发送端ID")
    private Long userId;//发送端ID
    @ApiModelProperty(value = "定时任务ID")
    private Long taskScheduleId;//定时任务ID

    public Long getTaskScheduleId() {
        return taskScheduleId;
    }

    public void setTaskScheduleId(Long taskScheduleId) {
        this.taskScheduleId = taskScheduleId;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getField() {
        return field;
    }

    public void setField(Integer field) {
        this.field = field;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getChatSpace() {
        return chatSpace;
    }

    public void setChatSpace(String chatSpace) {
        this.chatSpace = chatSpace;
    }

    public Long getLastId() {
        return lastId;
    }

    public void setLastId(Long lastId) {
        this.lastId = lastId;
    }

    public String getChat() {
        return chat;
    }

    public void setChat(String chat) {
        this.chat = chat;
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
