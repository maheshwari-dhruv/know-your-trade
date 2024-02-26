package org.blog.knowyourtrade.domain.exception;

import lombok.Getter;
import lombok.Setter;
import org.blog.knowyourtrade.domain.enums.ErrorCode;

@Setter
@Getter
public class ClientException extends RuntimeException {

    private static final long serialVersionUID = 5720954041763348333L;

    private String clientName;
    private String errorMessage;
    private ErrorCode errorCode;

    public ClientException(String clientName, String errorMessage) {
        super(errorMessage);
        this.clientName = clientName;
    }

    public ClientException(String clientName, ErrorCode errorCode, String errorMessage) {
        super(errorMessage);
        this.clientName = clientName;
        this.errorCode = errorCode;
    }
}