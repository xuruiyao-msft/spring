package com.springinaction.exceptions;

import org.springframework.web.bind.annotation.ExceptionHandler;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

    //注解标注的方法要像处理请求一样，来处理异常，然后结果返回给视图解析器
    @ExceptionHandler(SpittrNotFound.class)
    //这里只要是加上该注解，页面跳转就跑到了异常一面，这个注解能设置返回的状态码，以后在仔细研究吧
    //@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "spittr not found")
    public String handleSpittrNotFound() {
        return "errorpage";
    }
}
