package com.yier.platform.event;

import com.yier.platform.common.util.JsonUtils;
import org.springframework.context.ApplicationEvent;

public class AppPushEvent extends ApplicationEvent {
    public AppPushEvent(Object source) {
        super(source);
    }


    public String toJsonString() {
        return JsonUtils.toJsonString(this);
    }
    @Override
    public String toString() {
        return this.toJsonString();
    }
}
