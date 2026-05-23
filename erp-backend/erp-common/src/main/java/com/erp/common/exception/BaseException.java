package com.erp.common.exception;

import com.erp.common.result.ResultCode;
import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {
    private final ResultCode resultCode;

    public BaseException(ResultCode resultCode) {
        super(resultCode.getMessage());
        this.resultCode = resultCode;
    }

    public BaseException(ResultCode resultCode, String message) {
        super(message);
        this.resultCode = resultCode;
    }

    public BaseException(String message) {
        super(message);
        this.resultCode = ResultCode.FAILED;
    }
}
