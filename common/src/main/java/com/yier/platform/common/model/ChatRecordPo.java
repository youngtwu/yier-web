package com.yier.platform.common.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "聊天记录扩展表")
public class ChatRecordPo extends ChatRecord {
    @ApiModelProperty(value = "信息发送人姓名")
    private String sendUserName;
    @ApiModelProperty(value = "信息发接受人姓名")
    private String receiveUserName;
    @ApiModelProperty(value = "信息发送端")
    private String sendTypeIdDisplay;
    @ApiModelProperty(value = "信息接收端")
    private String receiveTypeIdDisplay;

    public String getSendUserName() {
        return sendUserName;
    }

    public void setSendUserName(String sendUserName) {
        this.sendUserName = sendUserName;
    }

    public String getReceiveUserName() {
        return receiveUserName;
    }

    public void setReceiveUserName(String receiveUserName) {
        this.receiveUserName = receiveUserName;
    }

    public String getSendTypeIdDisplay() {
        if (super.getSendTypeId().longValue() == 1) {
            sendTypeIdDisplay = "患者";
        } else if (super.getSendTypeId().longValue() == 2) {
            sendTypeIdDisplay = "医生";
        } else if (super.getSendTypeId().longValue() == 3) {
            sendTypeIdDisplay = "药师";
        } else if (super.getSendTypeId().longValue() == 4) {
            sendTypeIdDisplay = "客服";
        }
        return sendTypeIdDisplay;
    }

    public void setSendTypeIdDisplay(String sendTypeIdDisplay) {
        this.sendTypeIdDisplay = sendTypeIdDisplay;
    }

    public String getReceiveTypeIdDisplay() {
        if (super.getReceiveTypeId().longValue() == 1) {
            receiveTypeIdDisplay = "患者";
        } else if (super.getReceiveTypeId().longValue() == 2) {
            receiveTypeIdDisplay = "医生";
        } else if (super.getReceiveTypeId().longValue() == 3) {
            receiveTypeIdDisplay = "药师";
        } else if (super.getReceiveTypeId().longValue() == 4) {
            receiveTypeIdDisplay = "客服";
        }
        return receiveTypeIdDisplay;
    }

    public void setReceiveTypeIdDisplay(String receiveTypeIdDisplay) {
        this.receiveTypeIdDisplay = receiveTypeIdDisplay;
    }
}
