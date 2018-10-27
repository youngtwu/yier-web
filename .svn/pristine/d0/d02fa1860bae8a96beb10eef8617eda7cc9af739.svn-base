package com.yier.platform.common.model;

import com.yier.platform.common.jsonResponse.BaseJsonObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(value = "聊天记录")
public class ChatRecord extends BaseJsonObject {
    @ApiModelProperty(value = "主键标识Id")
    private Long id;
    @ApiModelProperty(value = "信息发送端")
    private Long sendTypeId;//发送端
    @ApiModelProperty(value = "信息发送端Id")
    private Long sendUserId;//发送端ID
    @ApiModelProperty(value = "信息接收端")
    private Long receiveTypeId;//接收端
    @ApiModelProperty(value = "信息接收端Id")
    private Long receiveUserId;//接收id
    @ApiModelProperty(value = "会话类型信息")
    private String chatSpace;//会话类型
    @ApiModelProperty(value = "会话类型:0表示文本，1表示图片")
    private String chatType;
    @ApiModelProperty(value = "文本内容")
    private String textContent;
    @ApiModelProperty(value = "图片Url")
    private String imageUrl;
    @ApiModelProperty(value = "状态（0正常 1删除 2停用 3冻结）")
    private String status;
    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;
    @ApiModelProperty(value = "修改时间")
    private Date gmtModified;
    @ApiModelProperty(value = "备注信息")
    private String remarks;

    public ChatRecord(Long sendTypeId, Long sendUserId, Long receiveTypeId, Long receiveUserId, String chatSpace, String chatType, String textContent, String imageUrl) {
        this.sendTypeId = sendTypeId;
        this.sendUserId = sendUserId;
        this.receiveTypeId = receiveTypeId;
        this.receiveUserId = receiveUserId;
        this.chatSpace = chatSpace;
        this.chatType = chatType;
        this.textContent = textContent;
        this.imageUrl = imageUrl;
    }

    public ChatRecord() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSendTypeId() {
        return sendTypeId;
    }

    public void setSendTypeId(Long sendTypeId) {
        this.sendTypeId = sendTypeId;
    }

    public Long getSendUserId() {
        return sendUserId;
    }

    public void setSendUserId(Long sendUserId) {
        this.sendUserId = sendUserId;
    }

    public Long getReceiveTypeId() {
        return receiveTypeId;
    }

    public void setReceiveTypeId(Long receiveTypeId) {
        this.receiveTypeId = receiveTypeId;
    }

    public Long getReceiveUserId() {
        return receiveUserId;
    }

    public void setReceiveUserId(Long receiveUserId) {
        this.receiveUserId = receiveUserId;
    }

    public String getChatSpace() {
        return chatSpace;
    }

    public void setChatSpace(String chatSpace) {
        this.chatSpace = chatSpace;
    }

    public String getChatType() {
        return chatType;
    }

    public void setChatType(String chatType) {
        this.chatType = chatType;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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