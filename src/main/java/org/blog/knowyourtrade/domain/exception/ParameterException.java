package org.blog.knowyourtrade.domain.exception;

import org.blog.knowyourtrade.domain.enums.ErrorCode;

public class ParameterException extends ServiceException {

    private static final long serialVersionUID = -9205856733189831061L;

    private Object value;

    public ParameterException(String errorMessage, ErrorCode e) {
        super(errorMessage, e);
    }

    public ParameterException(Object value, String errorMessage, ErrorCode e) {
        super(errorMessage, e);
        this.value = value;
    }

}