package org.blog.knowyourtrade.domain.dto.base;

import lombok.Data;
import lombok.ToString;
import org.blog.knowyourtrade.domain.enums.ErrorCode;

import java.io.Serializable;

@Data
@ToString
public class GenericResponse<T> implements Serializable {

    private boolean success;
    private String resultCode;
    private String resultCodeId;
    private String resultStatus;
    private String resultMsg;

    private T data;

    public GenericResponse() {}

    public GenericResponse(String errorMsg, ErrorCode e) {
        this.setErrorCode(e);
        this.resultMsg = errorMsg;
    }

    public GenericResponse(ErrorCode e) {
        this.setErrorCode(e);
    }

    public GenericResponse(ErrorCode e, T data) {
        this.setErrorCode(e, data);
    }

    public void setErrorCode(ErrorCode e) {
        this.success = e.isSuccess();
        this.resultCode = e.getErrorCode();
        this.resultCodeId = e.name();
        this.resultStatus = e.name();
        this.resultMsg = e.getErrorMessage();
    }

    public void setErrorCode(ErrorCode e, T data) {
        this.success = e.isSuccess();
        this.resultCode = e.getErrorCode();
        this.resultCodeId = e.name();
        this.resultStatus = e.name();
        this.resultMsg = e.getErrorMessage();
        this.data = data;
    }

    public static <T> GenericResponse success(T data) {
        return new GenericResponse(ErrorCode.SUCCESS, data);
    }

    public static <T> GenericResponse success() {
        return new GenericResponse(ErrorCode.SUCCESS, null);
    }
}