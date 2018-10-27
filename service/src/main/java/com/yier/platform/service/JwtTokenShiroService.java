package com.yier.platform.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.github.yingzhuo.carnival.jwt.EnableJwtTokenFactory;
import com.github.yingzhuo.carnival.jwt.factory.JwtTokenFactory;
import com.github.yingzhuo.carnival.jwt.factory.JwtTokenInfo;
import com.github.yingzhuo.carnival.jwt.realm.AbstractJwtUserDetailsRealm;
import com.github.yingzhuo.carnival.jwt.token.JwtTokenParser;
import com.github.yingzhuo.carnival.restful.security.EnableRestfulSecurity;
import com.github.yingzhuo.carnival.restful.security.parser.TokenParser;
import com.github.yingzhuo.carnival.restful.security.realm.UserDetailsRealm;
import com.github.yingzhuo.carnival.restful.security.userdetails.UserDetails;
import com.yier.platform.common.model.User;
import io.swagger.annotations.ApiModel;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@ApiModel(value = "carnival-spring-boot-starter-jwt")
@Service
@Configuration
@EnableJwtTokenFactory
@EnableRestfulSecurity
public class JwtTokenShiroService {
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenShiroService.class);
    @Autowired
    private JwtTokenFactory factory;

    @Bean
    @Order(1)
    public TokenParser tokenParser1() {
        logger.info("解析token 开始......");
        return new JwtTokenParser();
    }

//    @Bean
//    @Order(1)
//    public UserDetailsRealm userDetailsRealm1() {
//        return new AbstractJwtUserDetailsRealm() {
//            @Override
//            protected UserDetails getUserDetails(DecodedJWT jwt) {
//                logger.info("获取 UserDetailsRealm......");
//                return UserDetails.builder()
//                        .roles(jwt.getClaim("roles").asArray(String.class))
//                        .build();
//            }
//
//        };
//    }
//
//    public String createToken() throws Exception{
//        val token = factory.create(
//                JwtTokenInfo.builder()
//                        .expiresAtFuture(2, TimeUnit.HOURS)
//                        .putPrivateClaim("roles", new String[]{"r1", "r2", "r3"})
//                        .putPrivateClaim("name", "应卓")
//                        .build()
//        );
//        logger.info("create Token:{}", token);
//        return token;
//    }

    @Bean
    @Order(1)
    public UserDetailsRealm userDetailsRealm1() {
        return new AbstractJwtUserDetailsRealm() {
            @Override
            protected UserDetails getUserDetails(DecodedJWT jwt) {
                logger.info("获取 UserDetailsRealm......");
                return UserDetails.builder()
                        .roles(jwt.getClaim("roles").asArray(String.class))
                        .id(jwt.getClaim("userId").asLong())
                        .username(jwt.getClaim("userName").asString())
                        //.payload("userInfo", "白")
                        .build();
            }

        };
    }

    public String createToken(User user, String[] roles) {
        val token = factory.create(
                JwtTokenInfo.builder()
                        .expiresAtFuture(2, TimeUnit.HOURS)
                        .putPrivateClaim("roles", roles)
                        .putPrivateClaim("userId", user.getId())
                        .putPrivateClaim("userName", user.getUserName())
                        .build()
        );
        logger.info("create Token:{}", token);
        return token;
    }
}
