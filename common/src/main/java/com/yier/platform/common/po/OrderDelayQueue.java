package com.yier.platform.common.po;

import com.yier.platform.common.jsonResponse.BaseJsonObject;

import java.util.Date;

public class OrderDelayQueue  extends BaseJsonObject {
    private String caseInfo;
    private Long orderId;
    private String redisKey;
    private Date currentTime;

    public String getCaseInfo() {
        return caseInfo;
    }

    public void setCaseInfo(String caseInfo) {
        this.caseInfo = caseInfo;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getRedisKey() {
        return redisKey;
    }

    public void setRedisKey(String redisKey) {
        this.redisKey = redisKey;
    }

    public Date getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(Date currentTime) {
        this.currentTime = currentTime;
    }
}
