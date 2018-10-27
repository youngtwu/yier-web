package com.yier.platform.common.redis;

import com.yier.platform.common.model.SystemParameters;
import com.yier.platform.conf.CommonCfg;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;


@Component
public class RedisSystemParameterCache {
	private final Logger logger = LoggerFactory.getLogger(RedisSystemParameterCache.class);
	@Autowired
	private CommonCfg commonCfg;
	/**
	 * 缓存key前缀,从map整体储存中获取对应的key的value
	 */
	private static final String SYSTEM_PARAMETER = "system_params";
	@Autowired //idea Could not autowire:打开设置setting 在左侧找到Editor ，然后选择 Inspections,spring core 找到code ，选中Autowiring for Bean Class ,然后选择右侧的Severity，将ERROR级别调制Warning
	@Resource(name="redisTemplate") //新造的类需要注入对应的Bean资源
	private RedisTemplate<String,Map<String, String>> redisTemplate;

	@ApiOperation(value = "缓存中放置的是Map储存，目前通过Key来获取Value")
	public SystemParameters getByKey(String key) {
		SystemParameters systemParameter = null;
		try {
			if (StringUtils.isBlank(key)) {
				return systemParameter;
			}
			Map<String, String> parameterMap = this.redisTemplate.opsForValue().get(StringUtils.join(this.commonCfg.getKeyPrefix(),SYSTEM_PARAMETER));//获取整个map储存
			if(parameterMap == null){
				logger.info("目前缓存里面没有查获对应的储存信息及内容");
				return systemParameter;
			}
			String value = parameterMap.get(key);
			//logger.info("_key = {} , _value = {}", key, value);
			if (StringUtils.isNotBlank(value)) {
				systemParameter = new SystemParameters();
				systemParameter.setParkey(key);
				systemParameter.setPvalue(value);
			}
		} catch (Exception e) {
			logger.warn("can't get systemParameter from redis. _key = {}, error = {}", key, e.getMessage());
			logger.trace(e.getMessage(), e);
		}
		return systemParameter;
	}

	@ApiOperation(value = "把列表对象转换成Map储存，根据提供的过期时间，然后放置到缓存中")
	public void reset(List<SystemParameters> systemParameterList, long timeout, TimeUnit unit) {
		if (CollectionUtils.isEmpty(systemParameterList)) {
			logger.warn("systemParameterList is empty. store nothing to redis");
			return;
		}
		Map<String, String> parameterMap = systemParameterList.stream()
				.collect(Collectors.toMap(SystemParameters::getParkey, ch -> ch.getPvalue()));//只从对象流中提取key-value有用的东西
		if(timeout==0){
			redisTemplate.opsForValue().set(StringUtils.join(this.commonCfg.getKeyPrefix(),SYSTEM_PARAMETER),parameterMap);
		}
		else{
			redisTemplate.opsForValue().set(StringUtils.join(this.commonCfg.getKeyPrefix(),SYSTEM_PARAMETER),parameterMap,timeout,unit);
		}
	}

}