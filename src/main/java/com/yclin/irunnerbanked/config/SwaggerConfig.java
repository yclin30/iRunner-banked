package com.yclin.irunnerbanked.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("iRunner API 文档")
                        .version("1.0.0")
                        .description("为 iRunner App 提供后端服务的 API 接口文档。"));
    }
}