package com.yier.platform;

import com.yier.platform.conf.ApplicationConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication   //包含：@EnableAutoConfiguration：开启自动配置；@ComponentScan：开启包扫描，默认扫描同级及当前包下内容
@MapperScan("com.yier.platform.dao")  //自动扫描注册为 Spring Bean
//@EnableTransactionManagement //事务管理
//@Import(FdfsClientConfig.class)  //引入fdfs图片管理
@EnableConfigurationProperties(ApplicationConfig.class)  //引进项目配置类
//@EnableScheduling  //运行进行定时任务，发现注解@Scheduled的任务并后台执行
//@EnableAsync //允许异步执行 注释@EventListener 事件可能以后需要
//@EnableWebMvc
@EnableEurekaClient //作为客户端消费注册中心eureka
//@EnableDiscoveryClient	//其他的注册中心，那么推荐
@EnableFeignClients //作为客户端远程调用对开发者完全透明的Http请求
public class WebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);

    }
}
