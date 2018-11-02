package com.yier.platform.service.amqp;

import com.yier.platform.common.util.JsonUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SentinelServersConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;


import java.io.IOException;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("spring.redis")
public class RedissonConfig {
  private static final Logger logger = LoggerFactory.getLogger(RedissonConfig.class);

    public static class Sentinel {
        private String nodes;
        private String master;

        public String getNodes() {
            return nodes;
        }

        public void setNodes(String nodes) {
            this.nodes = nodes;
        }

        public String getMaster() {
            return master;
        }

        public void setMaster(String master) {
            this.master = master;
        }
    }

    private Sentinel sentinel;
    private String password;

    @Bean
    public RedissonClient getRedisson(){
        Config config = new Config();
        SentinelServersConfig serversConfig = config.useSentinelServers();
        serversConfig.setMasterName(sentinel.getMaster());

        String[] nodes = sentinel.getNodes().split(",");
        for(String node : nodes) {
            serversConfig.addSentinelAddress("redis://" + node.trim());
        }
        serversConfig.setPassword(password);
        logger.debug("显示配置设定信息:{}",JsonUtils.toJsonString(serversConfig));
        return Redisson.create(config);
    }

    public Sentinel getSentinel() {
        return sentinel;
    }

    public void setSentinel(Sentinel sentinel) {
        this.sentinel = sentinel;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



}
