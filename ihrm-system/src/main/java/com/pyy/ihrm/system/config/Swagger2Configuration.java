package com.pyy.ihrm.system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * ========================
 * swagger 配置
 * Created with IntelliJ IDEA.
 * User：pyy
 * Date：2019/10/12 15:00
 * Version: v1.0
 * ========================
 */
@Configuration
@EnableSwagger2
//@Profile({"local", "dev", "test"})
public class Swagger2Configuration {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.pyy.ihrm"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("系统权限管理后台api文档")
                .description("系统权限管理后台api文档")
                .version("1.0")
                .build();
    }

}
