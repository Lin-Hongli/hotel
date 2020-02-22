package com.lhl.hotelweb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

import javax.annotation.Resources;

@PropertySource(value = {"classpath:application-druid.properties",
        "classpath:application-mybatis.properties"})
@MapperScan("com.lhl.hotelweb.dao")
@SpringBootApplication
public class HotelwebApplication {

    public static void main(String[] args) {
        SpringApplication.run(HotelwebApplication.class, args);
    }

}
