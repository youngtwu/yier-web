package com.yier.platform.common.model;

import com.yier.platform.common.jsonResponse.BaseJsonObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(value = "Redis 记录情况")
public class RedisLog extends BaseJsonObject {
    @ApiModelProperty(value = "主键标识Id")
    private Long id;
    @ApiModelProperty(value = "redis_业务类型")
    private Long redisType;
    @ApiModelProperty(value = "redis_业务子类型描述")
    private String redisSubType;
    @ApiModelProperty(value = "redis_key")
    private String redisKey;
    @ApiModelProperty(value = "redis_value")
    private String redisValue;
    @ApiModelProperty(value = "超时时长")
    private Long timeout;
    @ApiModelProperty(value = "单位")
    private String timeunit;
    @ApiModelProperty(value = "状态")
    private String status;
    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;
    @ApiModelProperty(value = "修改时间")
    private Date gmtModified;
    @ApiModelProperty(value = "备注信息")
    private String remarks;

    public RedisLog(Long id, Long redisType, String redisSubType, String redisKey, String redisValue, Long timeout, String timeunit, String status, Date gmtCreate, Date gmtModified, String remarks) {
        this.id = id;
        this.redisType = redisType;
        this.redisSubType = redisSubType;
        this.redisKey = redisKey;
        this.redisValue = redisValue;
        this.timeout = timeout;
        this.timeunit = timeunit;
        this.status = status;
        this.gmtCreate = gmtCreate;
        this.gmtModified = gmtModified;
        this.remarks = remarks;
    }
    public RedisLog(Long redisType, String redisSubType, String redisKey, String redisValue, Long timeout, String timeunit, String remarks) {
        this.redisType = redisType;
        this.redisSubType = redisSubType;
        this.redisKey = redisKey;
        this.redisValue = redisValue;
        this.timeout = timeout;
        this.timeunit = timeunit;
        this.remarks = remarks;
    }

    public RedisLog() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRedisType() {
        return redisType;
    }

    public void setRedisType(Long redisType) {
        this.redisType = redisType;
    }

    public String getRedisSubType() {
        return redisSubType;
    }

    public void setRedisSubType(String redisSubType) {
        this.redisSubType = redisSubType;
    }

    public String getRedisKey() {
        return redisKey;
    }

    public void setRedisKey(String redisKey) {
        this.redisKey = redisKey;
    }

    public String getRedisValue() {
        return redisValue;
    }

    public void setRedisValue(String redisValue) {
        this.redisValue = redisValue;
    }

    public Long getTimeout() {
        return timeout;
    }

    public void setTimeout(Long timeout) {
        this.timeout = timeout;
    }

    public String getTimeunit() {
        return timeunit;
    }

    public void setTimeunit(String timeunit) {
        this.timeunit = timeunit;
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