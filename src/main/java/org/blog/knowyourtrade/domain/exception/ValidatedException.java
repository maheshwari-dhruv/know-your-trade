package org.blog.knowyourtrade.domain.exception;

import org.blog.knowyourtrade.domain.enums.ErrorCode;

import java.text.MessageFormat;

public class ValidatedException extends ServiceException {

    private static final long serialVersionUID = -9205856733189831061L;

    private Object value;

    public ValidatedException(ErrorCode e) {
        super(e.getErrorMessage(), e);
    }

    public ValidatedException(String errorMessage, ErrorCode e) {
        super(errorMessage, e);
    }

    public ValidatedException(Object value, String errorMessage, ErrorCode e) {
        super(MessageFormat.format(errorMessage, value), e);

        this.value = value;
    }

}