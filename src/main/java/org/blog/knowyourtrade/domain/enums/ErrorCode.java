package org.blog.knowyourtrade.domain.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    SUCCESS(HttpStatus.OK.value(), "200", "Ok", true),
    SYSTEM_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR.value(), "500", "System exception"),
    UNKNOWN_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR.value(),"500", "Unknown exception"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(),"500", "Internal Server Error"),
    PARAM_ILLEGAL(HttpStatus.BAD_REQUEST.value(),"400", "Request param illegal"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST.value(),"400", "Bad Request"),
    API_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(),"500", "Confluent API error"),
    DIFFERENT_MONTHS(HttpStatus.BAD_REQUEST.value(),"400", "Date start and date end can't have different month"),
    INVALID_DATE(HttpStatus.NOT_FOUND.value(), "404", "Invalid year or month"),
    REQUEST_NOT_EXISTS(HttpStatus.BAD_REQUEST.value(),"400", "Request not exist"),
    PAYLOAD_TOO_LARGE(HttpStatus.PAYLOAD_TOO_LARGE.value(),"413", "Payload Too Large"),
    REQUEST_CANCEL(HttpStatus.INTERNAL_SERVER_ERROR.value(),"500", "Request canceled"),
    ;

    private final String errorCode;
    private final String errorMessage;
    private final int httpCode;
    private boolean success;

    ErrorCode(int httpCode, String errorCode, String errorMessage) {
        this.httpCode = httpCode;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    ErrorCode(int httpCode, String errorCode, String errorMessage, boolean success) {
        this.httpCode = httpCode;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.success = success;
    }
}