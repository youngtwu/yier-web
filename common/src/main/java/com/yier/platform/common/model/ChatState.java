package com.yier.platform.common.model;

import com.yier.platform.common.jsonResponse.BaseJsonObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(value = "聊天状态记录表")
public class ChatState extends BaseJsonObject {
    @ApiModelProperty(value = "主键标识Id")
    private Long id;
    @ApiModelProperty(value = "patient1 doctor2 pharmacist3 user4 两个拼接的完整会话")
    private String chatSpace;
    @ApiModelProperty(value = "发送次数")
    private Long senderCount;
    @ApiModelProperty(value = "接收次数")
    private Long receiverCount;
    @ApiModelProperty(value = "最后一条记录")
    private Long lastId;
    @ApiModelProperty(value = "聊天状态（0开通 1不开通））")
    private String chat;
    @ApiModelProperty(value = "状态（W发送端等待回复  R接收端回复）")
    private String status;
    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;
    @ApiModelProperty(value = "修改时间")
    private Date gmtModified;
    @ApiModelProperty(value = "备注信息")
    private String remarks;

    public ChatState(Long id, String chatSpace, Long senderCount, Long receiverCount, Long lastId, String chat, String status, Date gmtCreate, Date gmtModified, String remarks) {
        this.id = id;
        this.chatSpace = chatSpace;
        this.senderCount = senderCount;
        this.receiverCount = receiverCount;
        this.lastId = lastId;
        this.chat = chat;
        this.status = status;
        this.gmtCreate = gmtCreate;
        this.gmtModified = gmtModified;
        this.remarks = remarks;
    }

    public ChatState() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChatSpace() {
        return chatSpace;
    }

    public void setChatSpace(String chatSpace) {
        this.chatSpace = chatSpace;
    }

    public Long getSenderCount() {
        return senderCount;
    }

    public void setSenderCount(Long senderCount) {
        this.senderCount = senderCount;
    }

    public Long getReceiverCount() {
        return receiverCount;
    }

    public void setReceiverCount(Long receiverCount) {
        this.receiverCount = receiverCount;
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