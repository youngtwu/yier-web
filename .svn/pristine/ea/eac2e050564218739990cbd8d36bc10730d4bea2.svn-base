package com.yier.platform.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.yier.platform.common.util.JsonUtils;
import com.yier.platform.conf.ApplicationConfig;
import com.yier.platform.conf.CommonCfg;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService {
    private static final Logger logger = LoggerFactory.getLogger(RedisService.class);
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private ApplicationConfig applicationConfig;//来自于可配置的外部属性文件
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ToolShareService toolShareService;//集成通用或公用的分享处理
    @Autowired
    private CommonCfg commonCfg;

    public void deleteChatStateCloseForRedis(long typeId, long userId) {
        String userType = toolShareService.getAppInfoByTypeId(typeId);
        String key = StringUtils.join(userType, userId, "_SetChat");
        String value = this.getValueRedisByKey(key);
        logger.info("删除前value---------->" + value);
        this.deleteRedisByKey(key);
        value = this.getValueRedisByKey(key);
        logger.info("删除后value---------->" + value);
    }

    public void isValidChatStateClose(long typeId, long userId, Integer timerAmount, Integer tiemerField) {
        String userType = toolShareService.getAppInfoByTypeId(typeId);
        String key = StringUtils.join(userType, userId, "_SetChat");
        Assert.isTrue(timerAmount != null, "时长[amount]要有值");
        Assert.isTrue(tiemerField != null && tiemerField.intValue() > 9 && tiemerField.intValue() < 14, "时间单位[field]要有值 10(小时HOUR)，12(分MINUTE),13(秒SECOND)");
        long timeout = timerAmount.longValue();
        TimeUnit unit = TimeUnit.SECONDS;
        String unitString = "小时";
        if (tiemerField.intValue() == 10) {
            unit = TimeUnit.HOURS;
            unitString = "小时";
        } else if (tiemerField.intValue() == 12) {
            unit = TimeUnit.MINUTES;
            unitString = "分";
        } else if (tiemerField.intValue() == 13) {
            unit = TimeUnit.SECONDS;
            unitString = "秒";
        }
        String value = this.getValueRedisByKey(key);
        logger.info("目前设定聊天设置状态更改，redis的储存情况是：{}:{}", key, value);
        String info = StringUtils.join("在您最近设置的",timeout,unitString,"内，无法再次调整");
        Assert.isTrue(StringUtils.isBlank(value), info);//之前的key - value 对等，会出现问题，毕竟后边对应的有 this.commonCfg.getKeyPrefix()
    }

    public void setChatStateCloseForRedis(long typeId, long userId, Integer timerAmount, Integer tiemerField) {
        String userType = toolShareService.getAppInfoByTypeId(typeId);
        String key = StringUtils.join(userType, userId, "_SetChat");
        Assert.isTrue(timerAmount != null, "时长[amount]要有值");
        Assert.isTrue(tiemerField != null && tiemerField.intValue() > 9 && tiemerField.intValue() < 14, "时间单位[field]要有值 10(小时HOUR)，12(分MINUTE),13(秒SECOND)");
        long timeout = timerAmount.longValue();
        TimeUnit unit = TimeUnit.SECONDS;
        String unitString = "小时";
        if (tiemerField.intValue() == 10) {
            unit = TimeUnit.HOURS;
            unitString = "小时";
        } else if (tiemerField.intValue() == 12) {
            unit = TimeUnit.MINUTES;
            unitString = "分";
        } else if (tiemerField.intValue() == 13) {
            unit = TimeUnit.SECONDS;
            unitString = "秒";
        }
        String value = this.getValueRedisByKey(key);
        logger.info("目前设定聊天设置状态更改，redis的储存情况是：{}:{}", key, value);
        String info = StringUtils.join("在您最近设置的",timeout,unitString,"内，无法再次调整");
        Assert.isTrue(StringUtils.isBlank(value), info);//之前的key - value 对等，会出现问题，毕竟后边对应的有 this.commonCfg.getKeyPrefix()
        this.setValueRedisByKey(key, info, timeout, unit);
    }


    public String getJwtTokenValueByKey(String key) {
        String value = this.getValueRedisByKey(key);
        Long getExpire = this.getExpirRedis(key, TimeUnit.SECONDS);
        logger.debug("目前redis情况 还剩{}秒[-2不存在,-1无限]到期 key:{} value:{}", getExpire, key, value);
        return value;
    }

    public void setTokenInfoByKey(String key, String value, Integer timerAmount, Integer tiemerField) {
        if (timerAmount == null || tiemerField == null) {
            this.setValueRedisByKey(key, value);
        } else {
            long timeout = timerAmount.longValue();
            TimeUnit unit = TimeUnit.SECONDS;
            if (tiemerField.intValue() == 10) {
                unit = TimeUnit.HOURS;
            } else if (tiemerField.intValue() == 12) {
                unit = TimeUnit.MINUTES;
            } else if (tiemerField.intValue() == 13) {
                unit = TimeUnit.SECONDS;
            }
            this.setValueRedisByKey(key, value, timeout, unit);
        }
    }

    /**
     * 通过key对象保存到redies缓存中去
     *
     * @param key
     * @param value
     * @param timeout
     * @param unit
     */
    public void setJsonObjectBy(String key, Object value, long timeout, TimeUnit unit) {
        try {
            logger.debug("通过key对象保存到redies缓存中去,目前的key:{}----->value:{}", key, JsonUtils.toJsonString(value));
            this.setValueRedisByKey(key, JsonUtils.toJsonString(value), timeout, unit);
        } catch (Exception e) {
            logger.warn("can't set {} to redis. error = {}", key, e.getMessage());
            logger.trace(e.getMessage(), e);
        }
    }

    /**
     * 通过key对象将redies缓存对象解析出来
     *
     * @param key
     * @param typeReference
     * @param <T>
     * @return
     */
    public <T> T getJsonObjectByKey(String key, final TypeReference<T> typeReference) {
        try {
            String value = this.getValueRedisByKey(key);
//            logger.info("通过key对象将redies缓存对象解析出来,目前的key:{}----->value:{}",key,value);
            return JsonUtils.fromJson(value, typeReference);
        } catch (Exception e) {
            logger.warn("can't get {} to redis. error = {}", key, e.getMessage());
            logger.trace(e.getMessage(), e);
        }
        return null;

    }

    @ApiOperation(value = "设定目前的Key-value 有时效性")
    public void setValueRedisByKey(String key, String value, long timeout, TimeUnit unit) {
        key = StringUtils.join(this.commonCfg.getKeyPrefix(), key);
        logger.debug("设定目前的Key：{} value:{} 有时效性", key, value);
        this.setValueByKey(key, value, timeout, unit);
    }

    @ApiOperation(value = "设定目前的Key-value 无时效性")
    public void setValueRedisByKey(String key, String value) {
        key = StringUtils.join(this.commonCfg.getKeyPrefix(), key);
        logger.debug("设定目前的Key：{} value:{} 无时效性", key, value);
        this.setValueByKey(key, value);
    }

    @ApiOperation(value = "获取过期时间 对应的剩余有效秒数时间")
    public long getExpirRedis(String key, final TimeUnit timeUnit) {
        key = StringUtils.join(this.commonCfg.getKeyPrefix(), key);
        logger.debug("获取过期时间Key：{}", key);
        return this.getExpire(key, timeUnit);
    }

    @ApiOperation(value = "获取值缓存值通过Key")
    public String getValueRedisByKey(String key) {//keys 是包含前缀标识的
        return this.getValueRedisByKey(key,true);
    }

    @ApiOperation(value = "获取值缓存值通过Key addPre=true,表示要系统添加前缀,false表示用自己取得,不添加前缀")
    public String getValueRedisByKey(String key,boolean addPrefix) {//addPrefix 决定 key,查询是否添加前缀
        if(addPrefix) key = StringUtils.join(this.commonCfg.getKeyPrefix(), key);
        logger.debug("获取值通过Key：{}", key);
        return this.getValueByKey(key);
    }

    public Set<String> getRedisKeysByPattern(String pattern){
        pattern = StringUtils.join(this.commonCfg.getKeyPrefix(), pattern);
        logger.debug("目前查询的前缀是 patern:{}",pattern);
        return this.getKeysByPattern(pattern);
    }
    @ApiOperation(value = "删除缓存中的模型 pattern :key_OrderNo_*")
    public Long deleteRedisByPattern(String pattern) {//pattern 是不包含前缀标识的
        pattern = StringUtils.join(this.commonCfg.getKeyPrefix(), pattern);
        logger.debug("删除缓存中的pattern：{}", pattern);
        return this.deleteByPattern(pattern);
    }

    @ApiOperation(value = "删除缓存中的Key")
    public boolean deleteRedisByKey(String key) {//keys 是包含前缀标识的
        return this.deleteRedisByKey(key,true);
    }
    @ApiOperation(value = "删除缓存中的Key")
    public boolean deleteRedisByKey(String key,boolean addPrefix) {//addPrefix 决定 keys 是包含前缀标识的
        if(addPrefix) key = StringUtils.join(this.commonCfg.getKeyPrefix(), key);
        logger.debug("删除缓存中的Key：{}", key);
        return this.deleteByKey(key);
    }


    @ApiOperation(value = "删除缓存中所有的Keys")
    public Long deleteRedisByKeys(List<String> keys,boolean addPrefix) {//keys 是包含前缀标识的
        for (int i = 0; i < keys.size(); i++) {
            keys.set(i, addPrefix?StringUtils.join(this.commonCfg.getKeyPrefix(), keys.get(i)):keys.get(i));
        }
        logger.debug("删除缓存中所有的Keys：{}", keys);
        return this.deleteByKeys(keys);
    }
    @ApiOperation(value = "删除缓存中所有的Keys")
    public Long deleteRedisByKeys(List<String> keys) {//keys 是包含前缀标识的
        return this.deleteRedisByKeys(keys,true);
    }
    @ApiOperation(value = "判断目前Key是否存在操作")
    public boolean hasRedisKey(String key,boolean addPrefix) {//keys 是包含前缀标识的
        if(addPrefix)key = StringUtils.join(this.commonCfg.getKeyPrefix(), key);
        logger.debug("判断目前Key：{}是否存在操作", key);
        return this.hasKey(key);
    }

    @ApiOperation(value = "判断目前Key是否存在操作")
    public boolean hasRedisKey(String key) {//keys 是包含前缀标识的
        return this.hasRedisKey(key,true);
    }

    //------------------------------------------------------
    @ApiOperation(value = "设定目前的Key-value 有时效性")
    public void setValueByKeyRedisTemplate(String key, Object value, long timeout, TimeUnit unit) {
        key = StringUtils.join(this.commonCfg.getKeyPrefix(), key);
        logger.debug("设定目前的Key：{} value:{} 有时效性", key, value);
        this.setValueByKeyTemplate(key, value, timeout, unit);
    }

    @ApiOperation(value = "设定目前的Key-value 无时效性")
    public void setValueByKeyRedisTemplate(String key, String value) {
        key = StringUtils.join(this.commonCfg.getKeyPrefix(), key);
        logger.debug("设定目前的Key：{} value:{} 无时效性", key, value);
        this.setValueByKeyTemplate(key, value);
    }

    @ApiOperation(value = "获取过期时间")
    public long getExpireRedisTemplate(String key, final TimeUnit timeUnit) {
        key = StringUtils.join(this.commonCfg.getKeyPrefix(), key);
        logger.debug("获取过期时间Key：{}", key);
        return this.getExpireTemplate(key, timeUnit);
    }

    @ApiOperation(value = "获取值缓存值通过Key")
    public Object getValueByKeyRedisTemplate(String key) {
        key = StringUtils.join(this.commonCfg.getKeyPrefix(), key);
        logger.debug("获取值通过Key：{}", key);
        return this.getValueByKeyTemplate(key);
    }
    public Set<String> getRedisKeysByPatternTemplate(String pattern){
        pattern = StringUtils.join(this.commonCfg.getKeyPrefix(), pattern);
        return this.getKeysByPatternTemplate(pattern);
    }
    @ApiOperation(value = "删除缓存中的模型 pattern :key_OrderNo_*")
    public Long deleteRedisByPatternTemplate(String pattern) {//pattern 是不包含前缀标识的
        pattern = StringUtils.join(this.commonCfg.getKeyPrefix(), pattern);
        logger.debug("删除缓存中的pattern：{}", pattern);
        return this.deleteByPatternTemplate(pattern);
    }
    @ApiOperation(value = "删除缓存中的Key")
    public boolean deleteByKeyRedisTemplate(String key) {
        key = StringUtils.join(this.commonCfg.getKeyPrefix(), key);
        logger.debug("删除缓存中的Key：{}", key);
        return this.deleteByKeyTemplate(key);
    }

    @ApiOperation(value = "删除缓存中所有的Keys")
    public Long deleteByKeysRedisTemplate(List<String> keys) {
        for (int i = 0; i < keys.size(); i++) {
            keys.set(i, StringUtils.join(this.commonCfg.getKeyPrefix(), keys.get(i)));
        }
        logger.debug("删除缓存中所有的Keys：{}", keys);
        return this.deleteByKeysTemplate(keys);
    }

    @ApiOperation(value = "判断目前Key是否存在操作")
    public boolean hasKeyRedisTemplate(String key) {
        key = StringUtils.join(this.commonCfg.getKeyPrefix(), key);
        logger.debug("判断目前Key：{}是否存在操作", key);
        return this.hasKeyTemplate(key);
    }

    //以下是基础单元，key都是根方法，无需再解析
    private void setValueByKey(String key, String value, long timeout, TimeUnit unit) {
        stringRedisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    private void setValueByKey(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    private long getExpire(String key, final TimeUnit timeUnit) {//keys 是包含前缀标识的
        return stringRedisTemplate.getExpire(key, timeUnit);
    }

    private String getValueByKey(String key) {//keys 是包含前缀标识的
        return stringRedisTemplate.opsForValue().get(key);
    }

    private Set<String> getKeysByPattern(String pattern){//pattern 是包含前缀标识的
        Set<String> keys = stringRedisTemplate.keys(pattern);
        logger.debug("目前找到模糊查询到的有:keys列表有{}",keys);
        return keys;
    }
    private Long deleteByPattern(String pattern) {//pattern 是包含前缀标识的
        Set<String> keys = this.getKeysByPattern(pattern);
        return stringRedisTemplate.delete(keys);
    }

    private boolean deleteByKey(String key) {//keys 是包含前缀标识的
        return stringRedisTemplate.delete(key);
    }

    private Long deleteByKeys(List<String> keys) {//keys 是包含前缀标识的
        return stringRedisTemplate.delete(keys);
    }

    private boolean hasKey(String key) {//keys 是包含前缀标识的
        return stringRedisTemplate.hasKey(key);
    }

    //-------------------redisTemplate.opsForValue().get(mobileKey)-------------------
    private void setValueByKeyTemplate(String key, Object value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    private void setValueByKeyTemplate(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    private long getExpireTemplate(String key, final TimeUnit timeUnit) {//keys 是包含前缀标识的
        return redisTemplate.getExpire(key, timeUnit);
    }

    private Object getValueByKeyTemplate(String key) {//keys 是包含前缀标识的
        return redisTemplate.opsForValue().get(key);
    }
    private Set<String> getKeysByPatternTemplate(String pattern){//pattern 是包含前缀标识的
        Set<String> keys = redisTemplate.keys(pattern);
        logger.info("目前找到模糊查询到的有:keys列表有{}",keys);
        return keys;
    }
    private Long deleteByPatternTemplate(String pattern) {//pattern 是包含前缀标识的
        Set<String> keys = this.getKeysByPatternTemplate(pattern);
        return redisTemplate.delete(keys);
    }
    private boolean deleteByKeyTemplate(String key) {//keys 是包含前缀标识的
        return redisTemplate.delete(key);
    }

    private Long deleteByKeysTemplate(List<String> keys) {//keys 是包含前缀标识的
        return redisTemplate.delete(keys);
    }

    private boolean hasKeyTemplate(String key) {//keys 是包含前缀标识的
        return redisTemplate.hasKey(key);
    }
}
