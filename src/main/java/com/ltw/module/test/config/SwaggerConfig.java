package com.ltw.module.test.config;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import com.ltw.common.config.swagger.BaseSwaggerConfig;
import com.ltw.common.model.domain.SwaggerProperties;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger API文档相关配置
 * Created by macro on 2019/4/8.
 */
@Configuration
@EnableSwagger2
@EnableSwaggerBootstrapUI
public class SwaggerConfig extends BaseSwaggerConfig {

    @Override
    public SwaggerProperties swaggerProperties() {
        return SwaggerProperties.builder()
                .apiBasePackage("com.ltw.module.test")
                .title("nettysocket-test系统")
                .description("系统测试")
                .contactName("nettysocket")
                .version("1.0")
                .enableSecurity(false)
                .build();
    }

}
