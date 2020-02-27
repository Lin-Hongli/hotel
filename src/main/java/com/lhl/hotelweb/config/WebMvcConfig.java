package com.lhl.hotelweb.config;

import com.lhl.hotelweb.interceptor.SessionInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
//@EnableWebMvc全面接管webmvc
public class WebMvcConfig implements WebMvcConfigurer{
    @Autowired
    SessionInterceptor sessionInterceptor;

    //视图控制器
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        //registry.addViewController("/index").setViewName("index");
//        registry.addViewController("/bbb.html").setViewName("index");
       // registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }

    //拦截器
    //拦截请求,通过一个实现了HandlerInterceptor的实现类在请求执行的各个阶段去做请求处理
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        //通过sessionInterceptor在访问/请求时做相应的处理
        //拦截/请求？？，其他页面请求无效？？
        registry.addInterceptor(sessionInterceptor).addPathPatterns("/");
        //拦截所有请求
        //registry.addInterceptor(sessionInterceptor).addPathPatterns("/**");
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
