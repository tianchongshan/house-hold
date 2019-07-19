package com.tcs.household.aop;

import com.tcs.household.enums.MessageCode;
import com.tcs.household.exception.BizException;
import com.tcs.household.model.response.JsonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.AccessDeniedException;

/**
 * Created by chongshan.tian01.
 */


@Slf4j
@RestController
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public JsonResponse<Void> handleException(Exception ex){
        JsonResponse<Void> reps=JsonResponse.error();
        log.error("Unknow Error",ex);
        return reps;
    }

    /**
     * 业务错误不正确
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(BizException.class)
    public JsonResponse<Void> handleBusinessException(BizException ex){
        log.error(ex.getUserDefinedExceptionName(),ex);
        return JsonResponse.error(ex.getErrorCode(),ex.getErrorMsg());
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public JsonResponse<Void> handlerMethodArgumentNotValidException(MethodArgumentNotValidException e){
        BindingResult br=e.getBindingResult();
        StringBuilder sb=new StringBuilder();
        for(FieldError error :br.getFieldErrors()){
            sb.append(error.getDefaultMessage());
            sb.append("\n");
        }
        return JsonResponse.error(MessageCode.REQUEST_PARAM_ERROR.getCode(),sb.toString());
    }

    /**
     * 用户名密码不正确
     * @param ex
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(BadCredentialsException.class)
    public JsonResponse<Void> handleBadCredentialsException(BadCredentialsException ex){
        log.warn("BadCredentialsException Error: {}",ex.getMessage());
        return JsonResponse.error(ex.getMessage());
    }

    /**
     * 访问拒绝/未授权
     *
     * @param ex
     * @return
     */
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public JsonResponse<Void> handleUnauthorizedException(AccessDeniedException ex){
        log.error("AccessDeniedException",ex);
        return JsonResponse.error(ex);
    }








}
