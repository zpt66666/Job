package com.baiyun.javaee.common.exception;

import lombok.Getter;

/**
 * 业务异常类
 *
 * @author Craft
 */
@Getter
public class BusinessException extends RuntimeException {

    private final Integer code;

    public BusinessException(String message) {
        super(message);
        this.code = 500;
    }

    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public static BusinessException badRequest(String message) {
        return new BusinessException(400, message);
    }

    /**
     * 未授权异常
     */
    public static BusinessException unauthorized(String message) {
        return new BusinessException(401, message);
    }
}
