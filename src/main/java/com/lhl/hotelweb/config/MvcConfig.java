package com.lhl.hotelweb.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer{

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
//        registry.addViewController("/login01.html").setViewName("login01");
//        registry.addViewController("/bbb.html").setViewName("index");
       // registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }




   /* @Bean
    public WebMvcConfigurer webMvcConfigurer() {

        return new WebMvcConfigurer() {

            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                registry.addViewController("/").setViewName("index");
                registry.addViewController("/index.html").setViewName("index");
                registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
            }

            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                registry.addResourceHandler("/templates/**").addResourceLocations("classpath:/templates/");

            }

        };
    }*/

   //添加静态资源映射
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        所有的静态资源访问路径/**映射到如下路径
        registry.addResourceHandler("static/**")
                .addResourceLocations("classpath:/static/");
    }
}
