package com.yier.platform.common.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApiModel(value = "用户信息扩展类")
public class UserPo extends User {
    private static final Logger logger = LoggerFactory.getLogger(UserPo.class);
    @ApiModelProperty(value = "亿尔云平台分配的Access Key")
    private String appKey ="" ;

    @ApiModelProperty(value = "Nonce 随机数，长度8位")
    private String nonce ="" ;

    @ApiModelProperty(value = "Timestamp 时间戳，从1970年1月1日0点0分0秒开始到现在的毫秒数")
    private String timestamp ="" ;

    @ApiModelProperty(value = "Signature 数据签名")
    private String signature ="" ;

    @ApiModelProperty(value = "登录token")
    private String token = "";

    @ApiModelProperty(value = "用户角色信息")
    private String[] roles;

    @ApiModelProperty(value = "用户所属端口类型:1.医生，2.药师，3.患者")
    private String clientType;

    public UserPo(){}
    public UserPo(User info){
        super(info);
        thinkField();
    }
    public void thinkField(){
        super.thinkField();
        this.setPassSalt("秘钥不暴露");//秘钥使命完成
        logger.debug("用户信息思考完毕，秘钥清空不暴露");
    }


    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
