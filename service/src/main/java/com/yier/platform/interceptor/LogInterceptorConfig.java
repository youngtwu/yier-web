/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.yier.platform.interceptor;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;



/**
 * 后台管理日志记录拦截器
 * @author ThinkGem
 * @version 2018年1月10日
 */
@Configuration
//@EnableWebMvc //添加此配置，swagger无法访问
@ConditionalOnProperty(name="interceptor.enabled", havingValue="true", matchIfMissing=true)
public class LogInterceptorConfig implements WebMvcConfigurer {
	private static final Logger logger = LoggerFactory.getLogger(LogInterceptorConfig.class);
	@Autowired
	private Environment env;


	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		InterceptorRegistration registration = registry.addInterceptor(new LogInterceptor());
		//通过项目属性配置,决定访问路径哪些拦截,哪些被排除在外
		String apps = this.env.getProperty("interceptor.addPathPatterns");
		String epps = this.env.getProperty("interceptor.excludePathPatterns");
		logger.info("***************************日志记录拦截器配置********************************");
		logger.info("拦截路径：{}",apps);
		logger.info("排除拦截路径:{}",epps);
		for (String uri : StringUtils.split(apps, ",")){
			if (StringUtils.isNotBlank(uri)){
				registration.addPathPatterns(StringUtils.trim(uri));
			}
		}
		for (String uri : StringUtils.split(epps, ",")){
			if (StringUtils.isNotBlank(uri)){
				registration.excludePathPatterns(StringUtils.trim(uri));
			}
		}
		//registration.addPathPatterns("/**");
	}

}