package com.yier.platform.event;

import com.yier.platform.common.typeEnum.RefreshRedisFlag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class EventPublisher {
    private final Logger logger = LoggerFactory.getLogger(EventPublisher.class);
    @Autowired
    private ApplicationEventPublisher publisher;
    @Autowired
    private ApplicationContext context;

    /**
     * 发送更新redis缓存的队列消息
     *
     * @param flags
     */
    public void sendRefreshRedisCacheEvent(Set<RefreshRedisFlag> flags, long timeout, TimeUnit unit) {
        RefreshRedisCacheEvent event = new RefreshRedisCacheEvent("发布刷新redis缓存指令", flags, timeout, unit);
        logger.info("具体针对事件{},群发事件，任何监控者均应捕获处置", event);
        this.context.publishEvent(event); // 这个事件对应监控处理，都在待命中，切面是执行时才切开看，事件监控是群发的
    }

    public void publishEven(ApplicationEvent applicationEvent) {
        logger.info("目前针对事件内容{}触发广播，任何监控者均应捕获处置  事件类是:{}", applicationEvent, applicationEvent.getClass().getTypeName());
        this.context.publishEvent(applicationEvent);
    }
}
