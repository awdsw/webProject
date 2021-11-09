package com.example.webProject.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    public void addViewControllers(ViewControllerRegistry registry){
        registry.addViewController("/403Page").setViewName("403Page");
        registry.addViewController("/_menu").setViewName("_menu");
        registry.addViewController("/userInfoPage").setViewName("userInfoPage");
        registry.addViewController("/employeePage").setViewName("employeePage");
        registry.addViewController("/generalPage").setViewName("generalPage");
    }
}
