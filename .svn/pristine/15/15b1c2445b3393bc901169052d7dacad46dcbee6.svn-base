package com.yier.platform.conf;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 用于生成swagger文档
 * 
 * @author bob
 *
 */
@Configuration
@EnableSwagger2
//@EnableWebMvc
public class SwaggerConfig //implements WebMvcConfigurer 此处用于MVC方面
{
	@Bean
	public Docket petApi() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				//.apis(RequestHandlerSelectors.basePackage("com.yier.platform.common.model")) // 仅显示 com.yier.platform.common.model 目录下的接口
				.build();
	}


}
