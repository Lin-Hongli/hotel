package com.lhl.hotelweb.advice;

import com.lhl.hotelweb.exception.CustomizeErrorCode;
import com.lhl.hotelweb.exception.CustomizeException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice//等待了解底层实现？？
public class CustomizeExceptionHandler {

        @ExceptionHandler(Exception.class)
        ModelAndView handle(Throwable e, Model model){
            //接收到异常时，若是自定义异常，取出自定义异常信息并返回给前端
            if(e instanceof CustomizeException){
                model.addAttribute("message",e.getMessage());
            }/*else if(e.getMessage().equals(CustomizeErrorCode.LOGIN_FAIL)){
                model.addAttribute("message",e.getMessage());
            }*/else{
                //若不是自定义异常，则返回通用的异常信息
                model.addAttribute("message","服务器冒烟了，小伙子，别乱搞！");
            }
            return new ModelAndView("error");
        }


}
