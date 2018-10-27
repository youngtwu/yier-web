package com.yier.platform.service;


import com.yier.platform.common.model.SystemParameters;
import com.yier.platform.common.redis.RedisSystemParameterCache;
import com.yier.platform.common.requestParam.BaseRequest;
import com.yier.platform.common.typeEnum.RefreshRedisFlag;
import com.yier.platform.common.util.JsonUtils;
import com.yier.platform.event.RefreshRedisCacheEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service //通常作用在业务层，但是目前该功能与 @Component 相同
public class RefreshRedisCacheService {
    private static final Logger logger = LoggerFactory.getLogger(RefreshRedisCacheService.class);
    @Autowired
    private SystemParametersService systemParametersService;
    @Autowired
    private RedisSystemParameterCache redisSystemParameterCache;
    @Autowired
    private StringRedisTemplate redisStringTemplate;

    //为了使工作能够得到异步执行，通常还需在Spring项目的上下文中使用注释@EnableAsync
    @Async
    @EventListener(value = RefreshRedisCacheEvent.class) //监控的事件类型，注解的最大好处在于，一个地方发布此时间，多个地方（方法）可以针对性同时执行，此处的奥秘之处
    public void updateRedisCache(RefreshRedisCacheEvent event) {
        logger.info("-->进入 @EventListener 监控方法");
        this.updateSystemParameter(event.getFlags(), event.getTimeout(), event.getUnit());
    }


    @TransactionalEventListener(value = RefreshRedisCacheEvent.class)
     public void updateTransectionRedisCache(RefreshRedisCacheEvent event) {
        logger.info("-->进入 @TransactionalEventListener 监控方法[验证阶段,目前不十分起作用?]");
        this.updateSystemParameter(event.getFlags(), event.getTimeout(), event.getUnit());
    }

    public void updateRedisCache(Set<RefreshRedisFlag> flags, long timeout, TimeUnit unit) {
        this.updateSystemParameter(flags, timeout, unit);
    }

    /**
     * 更新系统配置参数
     *
     * @param flags
     */
    private void updateSystemParameter(Set<RefreshRedisFlag> flags, long timeout, TimeUnit unit) {
        if (flags.contains(RefreshRedisFlag.REFRESH_SYSTEM_PARAMETER)) {
            logger.info(">>开始更新系统配置参数缓存数据 ，针对更新redis的类型:{},时长:{},单位:{}", flags, timeout, unit);
            BaseRequest params = new BaseRequest();
            params.setStatus("0");
            List<SystemParameters> listAll = this.systemParametersService.listSystemParameters(params);
            logger.debug("统一集中查询系统配置表条件为：{} \r\n结果为：{}", JsonUtils.toJsonString(params), JsonUtils.toJsonString(listAll));
            this.redisSystemParameterCache.reset(listAll, timeout, unit);
            logger.info("<<系统配置参数缓存数据更新完毕.");
        }
    }
}
