package com.example.watermark_demo.WebResource;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfigurer implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 添加自定义的资源映射，例如将路径 "/custom-resources/**" 映射到本地目录 "/path/to/custom-resources/"
        registry.addResourceHandler("/images-local/**")
                .addResourceLocations("file:D:/desktop/images/");

//        registry.addResourceHandler("/images/*").addResourceLocations("classpath:watermark_demo/target/classes/");
    }
}
