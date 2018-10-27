package com.yier.platform.event;

import com.yier.platform.common.typeEnum.RefreshRedisFlag;
import com.yier.platform.service.AppPushService;
import com.yier.platform.service.RefreshRedisCacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.EnumSet;
import java.util.concurrent.TimeUnit;

/**
 * 监听消息队列
 */
@Service
public class EventBusListener {// implements ApplicationListener {  //事件监听器,此类的目前是方便集中执行
    private final Logger logger = LoggerFactory.getLogger(EventBusListener.class);
    @Autowired
    private RefreshRedisCacheService refreshRedisCacheService;
    @Autowired
    private AppPushService appPushService;



    /**
     * 监听更新推送消息队列
     *
     * @param event
     */
    @EventListener(value = AppPushEvent.class) //监控的事件类型
    public void refreshAppPushEventListener(AppPushEvent event) {//针对的监听事件，对应的处理是
        logger.info("-->进入推送 AppPushEvent 监控方法");
        logger.info("监听到需要执行的事件-------------------------------->{}", event);
        appPushService.pushAppTest();

    }
    /**
     * 监听更新redis缓存的队列消息
     *
     * @param event
     */
    @EventListener(value = RefreshRedisCacheEvent.class) //监控的事件类型
    public void refreshRedisCacheEventListener(RefreshRedisCacheEvent event) {//针对的监听事件，对应的处理是
        logger.info("-->进入 EventBusListener 监控方法");
        logger.info("监听到需要执行的事件-------------------------------->{}", event);
        this.refreshRedisCacheService.updateRedisCache(EnumSet.allOf(RefreshRedisFlag.class), 24, TimeUnit.HOURS);
        //////---this.refreshRedisCacheService.updateRedisCache(EnumSet.allOf(RefreshRedisFlag.class),10,TimeUnit.SECONDS);
    }
// 继承事件监听器稍有风吹草动就会进行如此方法，@EventListener注解任意的Spring组件
//    @Override
//    public void onApplicationEvent(ApplicationEvent applicationEvent) {
//        logger.info("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
//        if(applicationEvent instanceof RefreshRedisCacheEvent){
//            RefreshRedisCacheEvent event = (RefreshRedisCacheEvent)applicationEvent;
//            logger.info("监听消息队列：发送刷新命令------------------------->"+event);
//        }
//    }




}
