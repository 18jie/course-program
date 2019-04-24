package com.fengjie.courseprogram.handler;

import com.fengjie.courseprogram.util.RestResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author fengjie
 * @date 2019/4/22 13:36
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public RestResponse exceptionHandler(Exception e){
        e.printStackTrace();
        return RestResponse.fail("系统内部错误");
    }

}
