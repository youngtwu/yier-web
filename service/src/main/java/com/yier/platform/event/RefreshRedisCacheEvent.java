package com.yier.platform.event;

import com.yier.platform.common.typeEnum.RefreshRedisFlag;
import com.yier.platform.common.util.JsonUtils;
import org.springframework.context.ApplicationEvent;

import java.util.Set;
import java.util.concurrent.TimeUnit;

public class RefreshRedisCacheEvent extends ApplicationEvent {
    private Set<RefreshRedisFlag> flags;
    private long timeout;
    private TimeUnit unit;

    public RefreshRedisCacheEvent(Object source) {
        super(source);
    }

    public RefreshRedisCacheEvent(Object source, Set<RefreshRedisFlag> flags, long timeout, TimeUnit unit) {
        super(source);
        this.flags = flags;
        this.timeout = timeout;
        this.unit = unit;
    }

    public Set<RefreshRedisFlag> getFlags() {
        return flags;
    }

    public void setFlags(Set<RefreshRedisFlag> flags) {
        this.flags = flags;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public TimeUnit getUnit() {
        return unit;
    }

    public void setUnit(TimeUnit unit) {
        this.unit = unit;
    }

    public String toJsonString() {
        return JsonUtils.toJsonString(this);
    }

    @Override
    public String toString() {
        return this.toJsonString();
    }
}
