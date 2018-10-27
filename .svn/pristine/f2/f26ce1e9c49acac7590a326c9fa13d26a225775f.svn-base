package com.yier.platform.conf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;

@Configuration
public class CommonCfg {
    private static final Logger logger = LoggerFactory.getLogger(CommonCfg.class);
    @Autowired
    private ApplicationConfig applicationConfig;
    @Autowired
    private Environment env;
    private static final String KEY_DEFAULT_PORTRAIT_URL = "common.application.config.rongcloud.defaultPortraitUri";
    private static final String KEY_ENVIRONMENT = "environment";
    private static final String KEY_DEBUG = "debug";
    private static final String KEY_PREFIX = "prefix";
    private static final String KEY_SUFFIX = "suffix";

    @PostConstruct  //@PostConstruct 或 @PreDestroy，这些方法就会在 Bean 初始化后或销毁之前被 Spring 容器执行了。
    public  void init(){
        String[] profiles = env.getActiveProfiles();
        for (int i = 0; i < profiles.length; i++) {
            logger.info("+++++++++++++++目前活跃配置:{}+++++++++++++++++++++++++++",profiles[i]);
        }
        logger.info("目前读取活跃文件是{} 是否是正式发布环境:{} 日志显示是否在debug状态:{} redis前缀:{} 后缀标识:{}", this.getEnvironment(),this.isProductEnv(),this.isDebug(),this.getKeyPrefix(),this.getKeySuffix());
        logger.info("------------------------------------------>系统配置文件上传前缀："+applicationConfig.getUploadImageUrlPrefix());
        logger.info("------------------------------------------>短信有效单位 {}{}",applicationConfig.getSms().getTimeout(),applicationConfig.getSms().getUnit());
    }

    //获取默认头像图片
    public String getKeyDefaultPortraitUrl() {
        return this.env.getProperty(KEY_DEFAULT_PORTRAIT_URL, "没有配置默认头像图片");
    }

    //获取是否为调试环境
    public boolean isDebug(){
        return this.env.getProperty(KEY_DEBUG,Boolean.class,true);
    }
    //获取环境是开发还是发布
    public String getEnvironment(){
        return this.env.getProperty(KEY_ENVIRONMENT,"dev");
    }
    /**
     * 检测是否为生产发布环境
     *
     * @return
     */
    public boolean isProductEnv() {
        String env = this.getEnvironment();
        return "prod".equals(env);
    }

    /**
     * 获取项目前缀
     * @return
     */
    public String getKeyPrefix(){
        return this.env.getProperty(KEY_PREFIX,"");
    }
    /**
     * 获取项目后缀
     * @return
     */
    public String getKeySuffix(){
        return this.env.getProperty(KEY_SUFFIX,"");
    }
}
