package com.mall.goods.exception;

import com.common.exception.BizCodeEnum;
import com.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.util.HashMap;
import java.util.Map;

/**
 * 集中处理所有异常
 */
@Slf4j
@RestControllerAdvice(basePackages = "com.mall.goods.controller")
public class ExceptionControllerAdvice {

    @ExceptionHandler(value = WebExchangeBindException.class)
    public R handleValidException(WebExchangeBindException e){
        log.error("数据校验出现问题：{}，异常类型：{}", e.getMessage(), e.getClass());
        BindingResult result = e.getBindingResult();

        Map<String, String> errorMap = new HashMap<>();
        result.getFieldErrors().forEach( fieldError -> {
            errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
        });
        return R.error(BizCodeEnum.VALID_EXCEPTION.getCode(), BizCodeEnum.VALID_EXCEPTION.getMsg()).put("data", errorMap);
    }

//    @ExceptionHandler(value = Throwable.class)
//    public R handleUnknownException(Throwable e){
//        return R.error(BizCodeEnum.UNKNOWN_EXCEPTION.getCode(), BizCodeEnum.UNKNOWN_EXCEPTION.getMsg());
//    }
}
