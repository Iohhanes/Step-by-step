package com.stepByStep.core.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;


@Configuration
@EnableWebMvc
@PropertySource("classpath:application.properties")
@ComponentScan("com.stepByStep.core.controller")
public class WebMvcConfig implements WebMvcConfigurer {

    private Environment environment;

    @Autowired
    public WebMvcConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public FreeMarkerViewResolver viewResolver(){
        FreeMarkerViewResolver viewResolver=new FreeMarkerViewResolver();
        viewResolver.setSuffix("/.ftl");
        viewResolver.setContentType("text/html;charset=UTF-8");
        viewResolver.setCache(false);
        viewResolver.setExposeRequestAttributes(true);
        viewResolver.setExposeSessionAttributes(true);
        return viewResolver;
    }

    @Bean
    public FreeMarkerConfigurer freeMarkerConfigurer(){
        FreeMarkerConfigurer freeMarkerConfigurer=new FreeMarkerConfigurer();
        freeMarkerConfigurer.setTemplateLoaderPath("/WEB-INF/templates");
        freeMarkerConfigurer.setDefaultEncoding("UTF-8");
//        freeMarkerConfigurer.setFreemarkerSettings(new Properties() {{
//            put("default_encoding", "UTF-8");
//        }});
        return freeMarkerConfigurer;
    }

//    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/login").setViewName("login");
//    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file://" + environment.getRequiredProperty("upload.path") + "/");
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }
}
