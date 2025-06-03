package com.baiyun.javaee.common.exception;

import com.baiyun.javaee.common.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 全局异常处理器
 *
 * @author Craft
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public Object handleBusinessException(BusinessException e, HttpServletRequest request) {
        log.warn("业务异常: {}", e.getMessage());
        if (isAjaxRequest(request)) {
            return ApiResponse.error(e.getCode(), e.getMessage());
        }
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("error", new Error(e.getMessage()));
        return mav;
    }

    /**
     * 处理参数校验异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object handleValidationException(MethodArgumentNotValidException e, HttpServletRequest request) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        String message = fieldErrors.stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));
        log.warn("参数校验失败: {}", message);
        if (isAjaxRequest(request)) {
            return ApiResponse.badRequest(message);
        }
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("error", new Error(message));
        return mav;
    }

    /**
     * 处理绑定异常
     */
    @ExceptionHandler(BindException.class)
    public Object handleBindException(BindException e, HttpServletRequest request) {
        String message = e.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));
        log.warn("参数绑定失败: {}", message);
        if (isAjaxRequest(request)) {
            return ApiResponse.badRequest(message);
        }
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("error", new Error(message));
        return mav;
    }

    /**
     * 处理请求参数缺失异常
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Object handleMissingParamException(MissingServletRequestParameterException e, HttpServletRequest request) {
        log.warn("请求参数缺失: {}", e.getMessage());
        String message = "缺少必需的请求参数: " + e.getParameterName();
        if (isAjaxRequest(request)) {
            return ApiResponse.badRequest(message);
        }
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("error", new Error(message));
        return mav;
    }

    /**
     * 处理JSON解析异常
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Object handleHttpMessageNotReadableException(HttpMessageNotReadableException e, HttpServletRequest request) {
        log.warn("JSON解析失败: {}", e.getMessage());
        if (isAjaxRequest(request)) {
            return ApiResponse.badRequest("请求数据格式错误");
        }
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("error", new Error("请求数据格式错误"));
        return mav;
    }

    /**
     * 处理其他未知异常
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Object handleException(Exception e, HttpServletRequest request) {
        log.error("系统异常", e);
        if (isAjaxRequest(request)) {
            return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage() != null ? e.getMessage() : "服务器内部错误，请稍后重试");
        }
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("error", new Error("服务器内部错误，请稍后重试"));
        return mav;
    }

    /**
     * 判断是否为AJAX请求
     */
    private boolean isAjaxRequest(HttpServletRequest request) {
        String requestedWith = request.getHeader("X-Requested-With");
        return "XMLHttpRequest".equals(requestedWith);
    }
}
