package com.yier.platform.conf;


import io.swagger.annotations.ApiModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;

//import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//第一种现象:No 'Access-Control-Allow-Origin' header is present on the requested resource,并且The response had HTTP status code 404
//本次ajax请求是“非简单请求”,所以请求前会发送一次预检请求(OPTIONS)
//解决方案: 后端允许options请求

//第二种现象:No 'Access-Control-Allow-Origin' header is present on the requested resource,并且The response had HTTP status code 405
//后台方法允许OPTIONS请求,但是一些配置文件中(如安全配置)
//解决方案: 后端关闭对应的安全配置

//第三种现象:No 'Access-Control-Allow-Origin' header is present on the requested resource,并且status 200


//同一协议，同一ip，同一端口，三同中有一不同就产生了跨域。跨域是指 不同域名之间相互访问。跨域，指的是浏览器不能执行其他网站的脚本。, 浏览器的同源策略对JavaScript施加的安全限制,不能执行其他网站的脚本。
//前端解决跨域 所以搞一个node 服务器做代理，发出请求到node 服务器，node服务器转发到后端就可以绕过跨域问题。
//后端解决跨域 只用在Controller类上添加一个“@CrossOrigin“注解就可以实现对当前controller 的跨域
//还有就是如下统一过滤配置处理
@ApiModel(value = "ajax 跨域请求问题 过滤的配置，响应头拦截，")
@Configuration
@Slf4j
public class CorsConfig {
/*
//1.编写一个支持跨域请求的 第一种存在一个问题，当服务器抛出 500 的时候依旧存在跨域问题
    @Bean
    public WebMvcConfigurer corsConfigurer(){// 类似扩张extends WebMvcConfigurerAdapter
        log.info("********************************************************ajax 跨域请求问题配置 extends WebMvcConfigurerAdapter");
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowCredentials(true).allowedOrigins("*").allowedHeaders("*").allowedMethods(new String[]{"GET", "POST", "PUT", "DELETE"}).maxAge(1800);
            }
        };
    }
*/

//2. 跨域过滤器
//直接添加注解实现类或方法跨越@CrossOrigin 默认30分钟 60*30=1800
//响应头 origins:Access-Control-Allow-Origin: http://192.168.0.199:8806 "*"代表所有域的请求都支持
    @Bean
    public CorsFilter corsFilter() {
        log.info("********************************************************ajax 跨域请求问题配置");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);//允许cookie跨域
        corsConfiguration.addAllowedOrigin("*");//原始域名 响应头 origins:Access-Control-Allow-Origin: *
        corsConfiguration.addAllowedHeader("*");//允许的头部  响应头中允许访问的header
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));//允许的方法  默认支持RequestMapping中设置的方法
        corsConfiguration.setMaxAge(10L);// 10秒钟缓存时间300L);//缓存时间 单位秒 60*5 = 300,表示5分钟
        // 配置所有请求
        source.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(source);
    }

/*
//3  每次请求都进行过滤处理
    @Bean
    public OncePerRequestFilter doFilter(){
        log.info("********************************************************ajax 跨域请求问题配置 extends OncePerRequestFilter");
        return new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
                log.info("-----报文头");
                for(Enumeration e = request.getHeaderNames() ; e.hasMoreElements();){
                    String header = e.nextElement().toString();
                    log.info("header:{} value:{} ",header,request.getHeader(header));
                }
                log.info("-----参数体");
                for(Enumeration e = request.getParameterNames() ; e.hasMoreElements();){
                    String parameter = e.nextElement().toString();
                    log.info("parameter:{} value:{} ",parameter,request.getParameter(parameter));
                }
                log.info("-----属性");
                for(Enumeration e = request.getAttributeNames() ; e.hasMoreElements();){
                    String attributeNames = e.nextElement().toString();
                    log.info("attributeNames:{} value:{} ",attributeNames,request.getAttribute(attributeNames));
                }
                String requestMethod = request.getMethod();
                log.info("requestMethod:"+requestMethod);
//                String origin = request.getHeader("Origin");
//                response.setHeader("Access-Control-Allow-Origin", "*");//* or origin as u prefer
//                response.setHeader("Access-Control-Allow-Credentials", "true");
//                response.setHeader("Access-Control-Allow-Methods", "PUT, POST, GET, OPTIONS, DELETE");
//                response.setHeader("Access-Control-Max-Age", "3600");
//                response.setHeader("Access-Control-Allow-Headers", "x-requested-with,content-type");
                if (request.getMethod().equals("OPTIONS")) {//预热处理
                    log.info("本次发送一次预检请求(OPTIONS)  终止");
                    response.setStatus(HttpServletResponse.SC_OK);
                }
                else {
                    log.info("本次发送{}请求，继续进行下去",request.getMethod());
                    filterChain.doFilter(request, response);
                }
            }
        };
    }*/



}
