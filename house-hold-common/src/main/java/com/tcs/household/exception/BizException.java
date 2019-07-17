package com.tcs.household.exception;

import cn.hutool.core.util.StrUtil;
import com.tcs.household.enums.MessageCode;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class BizException extends RuntimeException {
    private static final long serialVersionUID = -1260864127580709459L;

    /**
     * 错误枚举
     */
    private String errorCode;

    /**
     * 错误描述
     */
    private String errorMsg;

    /**
     * 通用业务异常数据
     */
    private Map<String, String> bizDataMap;

    private List<MessageCode> groupErrorCode;

    public BizException() {
        super();
    }

    public BizException(List<MessageCode> groupErrorCode, MessageCode grouponErrorCode) {
        this.groupErrorCode = groupErrorCode;
        this.errorCode = grouponErrorCode.getCode();
        this.errorMsg = grouponErrorCode.getMessage();

    }

    public BizException(MessageCode grouponErrorCode, Map<String, String> bizDataMap) {
        this.errorCode = grouponErrorCode.getCode();
        this.errorMsg = grouponErrorCode.getMessage();
        this.bizDataMap = bizDataMap;
    }

    public BizException(MessageCode grouponErrorCode) {
        this.errorCode = grouponErrorCode.getCode();
        this.errorMsg = grouponErrorCode.getMessage();
    }

    public BizException(MessageCode grouponErrorCode, Throwable throwable) {
        super(throwable);
        this.errorCode = grouponErrorCode.getCode();
        this.errorMsg = grouponErrorCode.getMessage();
    }

    public BizException(MessageCode grouponErrorCode, String errorInfo, Throwable throwable) {
        super(throwable);
        this.errorCode = grouponErrorCode.getCode();
        this.errorMsg = grouponErrorCode.getMessage() + ":" + errorInfo;

    }

    public BizException(MessageCode grouponErrorCode, Object... errorInfo) {
        this.errorCode = grouponErrorCode.getCode();
        this.errorMsg = StrUtil.format(grouponErrorCode.getMessage(), errorInfo);

    }

    public BizException(List<MessageCode> groupErrorCode) {
        this.groupErrorCode = groupErrorCode;
    }

    public BizException(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public String getUserDefinedExceptionName() {
        return this.getClass().getName() + "-" + this.getErrorCode();
    }
}
