package com.lhl.hotelweb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@PropertySource(value = {"classpath:application-druid.properties"})
@MapperScan("com.lhl.hotelweb.dao")
@SpringBootApplication
public class HotelwebApplication {


    public static void main(String[] args) {
        //try{
            SpringApplication.run(HotelwebApplication.class, args);
        /*}catch(Exception e){
            e.printStackTrace();
        }*/

    }

}
