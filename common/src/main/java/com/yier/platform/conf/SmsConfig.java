package com.yier.platform.conf;

import java.util.concurrent.TimeUnit;

public class SmsConfig {
    private String product = "Dysmsapi";
    private String domain = "dysmsapi.aliyuncs.com";
    private String accessKeyId = "LTAIvYuVOwMPVYsL";
    private String accessKeySecret = "iardVx5SV3Bug9fUiQQiBh0e9TRi4e";
    //限制同一号码发送间隔
    private String smsMobilePrefix = "SMS_PHONE_NUMBER";
    //限制同一IP访问次数
    private String smsClientPrefix = "SMS_CLIENT_IP";
    private String templateCodeForRegister =  "SMS_136675023";
    private String templateCodeForVerify = "SMS_136675026";
    //短信发送间隔，单位分钟
    private int smsInterval = 1;
    //同一号码每日发送上限
    private int maxsendOfDday = 10;
    //同个手机有效发送次数及同个IP有效积累发送次数
    private long ipTotal = 50;
    //过期时间单位：HOURS，MINUTES，SECONDS，MILLISECONDS （小时，分，秒，毫秒）及长度
    private TimeUnit unit;
    private long timeout;

    public long getIpTotal() {
        return ipTotal;
    }

    public void setIpTotal(long ipTotal) {
        this.ipTotal = ipTotal;
    }

    public TimeUnit getUnit() {
        return unit;
    }

    public void setUnit(TimeUnit unit) {
        this.unit = unit;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getSmsMobilePrefix() {
        return smsMobilePrefix;
    }

    public void setSmsMobilePrefix(String smsMobilePrefix) {
        this.smsMobilePrefix = smsMobilePrefix;
    }

    public String getSmsClientPrefix() {
        return smsClientPrefix;
    }

    public void setSmsClientPrefix(String smsClientPrefix) {
        this.smsClientPrefix = smsClientPrefix;
    }

    public String getTemplateCodeForRegister() {
        return templateCodeForRegister;
    }

    public void setTemplateCodeForRegister(String templateCodeForRegister) {
        this.templateCodeForRegister = templateCodeForRegister;
    }

    public String getTemplateCodeForVerify() {
        return templateCodeForVerify;
    }

    public void setTemplateCodeForVerify(String templateCodeForVerify) {
        this.templateCodeForVerify = templateCodeForVerify;
    }

    public int getSmsInterval() {
        return smsInterval;
    }

    public void setSmsInterval(int smsInterval) {
        this.smsInterval = smsInterval;
    }

    public int getMaxsendOfDday() {
        return maxsendOfDday;
    }

    public void setMaxsendOfDday(int maxsendOfDday) {
        this.maxsendOfDday = maxsendOfDday;
    }
}
