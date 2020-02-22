package com.lhl.hotelweb.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfig {

    // 加载YML格式自定义配置文件
  /*  @Bean
    public static PropertySourcesPlaceholderConfigurer properties() {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        // yaml.setResources(new FileSystemResource("application-cat.yml"));// File引入
        yaml.setResources(new ClassPathResource("application-db.yml"));// class引入
        configurer.setProperties(yaml.getObject());
        return configurer;
    }*/

}