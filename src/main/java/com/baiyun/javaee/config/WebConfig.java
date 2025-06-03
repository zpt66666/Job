package com.baiyun.javaee.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
// import org.springframework.core.io.Resource;
// import org.springframework.web.servlet.resource.ResourceResolverChain;
// import org.springframework.web.servlet.resource.AbstractResourceResolver;
// import jakarta.servlet.http.HttpServletRequest;
// import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // 我将暂时注释掉 addResourceHandlers 方法，让 Spring Boot 使用默认静态资源处理
    
    /*
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 配置静态资源映射并排除 /career/api/** 路径
        registry.addResourceHandler("/career/**")
                .addResourceLocations("classpath:/static/career/")
                .resourceChain(true)
                .addResolver(new ExcludeApiPathResourceResolver("/career/api/")); // 添加自定义的排除解析器

         // 添加其他可能存在的静态资源路径，例如 /js, /css
        registry.addResourceHandler("/js/**")
                .addResourceLocations("classpath:/static/js/");
        registry.addResourceHandler("/css/**")
                .addResourceLocations("classpath:/static/css/");
         // registry.addResourceHandler("/") // 首页的特殊处理，如果使用PageController映射，则不需要这里
         //       .addResourceLocations("classpath:/static/index.html");
    }
    */
    

    // 我将暂时注释掉 addViewControllers 方法，因为它可能与 Thymeleaf 的模板解析冲突
    /*
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/register").setViewName("register");
        // 添加其他视图控制器映射
        registry.addViewController("/personal-info").setViewName("personal-info");
        registry.addViewController("/skill-assessment").setViewName("skill-assessment");
        registry.addViewController("/resume-tool").setViewName("resume-tool");
        registry.addViewController("/mock-interview").setViewName("mock-interview");
        registry.addViewController("/action-plan").setViewName("action-plan");
        registry.addViewController("/resume/fill").setViewName("resume-form");
    }
    */

    // 自定义 ResourceResolver 用于排除 /career/api/ 路径
    /*
    private static class ExcludeApiPathResourceResolver extends AbstractResourceResolver {
        private final String apiPathPrefix;

        public ExcludeApiPathResourceResolver(String apiPathPrefix) {
            this.apiPathPrefix = apiPathPrefix;
        }

        @Override
        protected Resource resolveResourceInternal(HttpServletRequest request, String requestPath, List<? extends Resource> locations, ResourceResolverChain chain) {
            // 如果请求路径以 apiPathPrefix 开头，则不解析为静态资源
            if (requestPath.startsWith(apiPathPrefix)) {
                return null;
            }
            // 否则，交给链中的下一个解析器处理
            return chain.resolveResource(request, requestPath, locations);
        }

        @Override
        protected String resolveUrlPathInternal(String resourcePath, List<? extends Resource> locations, ResourceResolverChain chain) {
            return chain.resolveUrlPath(resourcePath, locations);
        }
    }
    */
} 