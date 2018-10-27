package com.yier.platform.common.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;

@ApiModel(value = "患者信息扩展")
public class PatientPo extends Patient {
    private static final Logger logger = LoggerFactory.getLogger(PatientPo.class);
    @ApiModelProperty(value = "AccessKey")
    private String appKey ="" ;

    @ApiModelProperty(value = "Nonce随机数")
    private String nonce ="" ;

    @ApiModelProperty(value = "Timestamp 时间戳，从1970年1月1日0点0分0秒开始到现在的毫秒数")
    private String timestamp ="" ;

    @ApiModelProperty(value = "Signature 数据签名")
    private String signature ="" ;

    @ApiModelProperty(value = "登录token")
    private String token = "";

    @ApiModelProperty(value = "显示年龄")
    private String yearOld = "";

    @ApiModelProperty(value = "显示聊天状态W:wait等待 R:replay回复 其他表示未知")
    private String chatState = "";

    public String getChatState() {
        return chatState;
    }

    public void setChatState(String chatState) {
        this.chatState = chatState;
    }

    public PatientPo(){}
    public PatientPo(Patient patient){
        super(patient);
        thinkField();
    }
    public void thinkField(){
        super.thinkField();
        this.setPassSalt("秘钥不暴露");//秘钥使命完成
        logger.info("------:{}",this);
        if(this.getBirthday()!=null){
            Calendar cal = Calendar.getInstance();
            int iCurrYear = cal.get(Calendar.YEAR);
            cal.setTime(this.getBirthday());
            int userYear = cal.get(Calendar.YEAR);
            int yearInfo = iCurrYear - userYear;
            this.yearOld = yearInfo==0?"":yearInfo+"";
            logger.info("iCurrYear:{} userYear:{} this.yearOld:{}",iCurrYear,userYear,this.yearOld);
        }
        logger.info("患者信息思考完毕，秘钥清空不暴露");
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getYearOld() {
        return yearOld;
    }

    public void setYearOld(String yearOld) {
        this.yearOld = yearOld;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
