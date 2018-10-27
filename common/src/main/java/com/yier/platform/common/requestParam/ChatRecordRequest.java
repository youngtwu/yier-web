package com.yier.platform.common.requestParam;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "聊天记录请求接口")
public class ChatRecordRequest extends BaseRequest {
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
    private Long hospitalId;//医院ID
    private Long catalogId;//科目大分类ID
    private Long hospitalDepartmentId ;//科室ID
    private Long doctorId ;//医生ID
    @ApiModelProperty(value = "聊天状态W:wait等待 R:replay回复 其他表示未知")
    private String chatState;

    public String getChatState() {
        return chatState;
    }

    public void setChatState(String chatState) {
        this.chatState = chatState;
    }

    public String getChatType() {
        return chatType;
    }

    public void setChatType(String chatType) {
        this.chatType = chatType;
    }

    public Long getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(Long hospitalId) {
        this.hospitalId = hospitalId;
    }

    public Long getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(Long catalogId) {
        this.catalogId = catalogId;
    }

    public Long getHospitalDepartmentId() {
        return hospitalDepartmentId;
    }

    public void setHospitalDepartmentId(Long hospitalDepartmentId) {
        this.hospitalDepartmentId = hospitalDepartmentId;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
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
}
