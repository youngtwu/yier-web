package com.yier.platform.service.feign;

import cc.ccae.yry.service.drug.query.sdk.json.DrugQuerySdkModule;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignPageConfigurtion {
    private static final Logger logger = LoggerFactory.getLogger(FeignPageConfigurtion.class);
    @Autowired
    public void cnf(ObjectMapper objectMapper) {
        objectMapper.registerModule(new DrugQuerySdkModule());
        logger.info("org.springframework.data.domain.Page 为抽象类型无法使用 jackson 框架反序列列化Json，注册Jackson补丁");
    }
}
