package com.shooter.springboot.common.config;

import io.swagger.annotations.Api;
import org.springframework.http.HttpMethod;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.*;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import java.util.*;
import io.swagger.v3.oas.annotations.Operation;

@EnableOpenApi
@SpringBootConfiguration
public class SwaggerConfig {
    /**
     * 配置文档生成最佳实践
     */
    @Bean
    public Docket createDocket() {
        // OAS_30表示Swagger3
        return new Docket(DocumentationType.OAS_30)
                // 配置基本信息
                .apiInfo(apiInfo())
                //select()函数返回一个ApiSelectorBuilder实例,用来控制哪些接口暴露给Swagger来展现
                .select()
                // 指定扫描的包，包含子包
                .apis(RequestHandlerSelectors.basePackage("com.shooter.springboot"))
                // 指定扫描类上的注解，可以配置@RestController等
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                // 指定扫描方法上的注解，可以配置PostMapping、RequestMapping、GetMapping、ApiOperation等
                .apis(RequestHandlerSelectors.withMethodAnnotation(Operation.class))
                .build()
                // 支持的通讯协议集合
                .protocols(new LinkedHashSet<>(Arrays.asList("https", "http")))
                // 授权信息设置，必要的header token等认证信息
                .securitySchemes(securitySchemes())
                // 授权信息全局应用
                .securityContexts(securityContexts())
                // 通用响应信息
                .globalResponses(HttpMethod.GET, getGlobalResponseMessage())
                .globalResponses(HttpMethod.POST, getGlobalResponseMessage())
                .globalResponses(HttpMethod.DELETE, getGlobalResponseMessage())
                .globalResponses(HttpMethod.PUT, getGlobalResponseMessage());
    }

    /**
     * 创建该API的基本信息
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                // Swagger接口文档标题
                .title("Swagger3接口文档")
                // Swagger接口文档描述
                .description("前后端分离的接口文档")
                .termsOfServiceUrl("TermsOfServiceUrl")
                // 配置作者信息
                .contact(new Contact("管理员","www.admin.com","admin@qq.com"))
                .version("1.0")
                .build();
    }

    /**
     * 设置授权信息
     */
    private List<SecurityScheme> securitySchemes() {
        return Collections.singletonList(new ApiKey("TOKEN", "token", "pass"));
    }

    /**
     * 授权信息全局应用
     */
    private List<SecurityContext> securityContexts() {
        return Collections.singletonList(
                SecurityContext.builder().securityReferences(
                        Collections.singletonList(
                                new SecurityReference("TOKEN",
                                        new AuthorizationScope[]{
                                                new AuthorizationScope("global", "请求Token")})))
                        .build());
    }

    /**
     * 生成通用响应信息
     * */
    private List<Response> getGlobalResponseMessage() {
        List<Response> responseList = new ArrayList<>();
        responseList.add(new ResponseBuilder().code("404").description("找不到资源").build());
        responseList.add(new ResponseBuilder().code("403").description("请求拒绝").build());
        responseList.add(new ResponseBuilder().code("401").description("权限拒绝").build());
        return responseList;
    }
}
