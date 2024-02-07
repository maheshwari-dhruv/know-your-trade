package org.blog.knowyourtrade.domain.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter
public class ApiException extends RuntimeException {
    private static final long serialVersionUID = 5720954041763348333L;

    private String apiName;
    private String apiUrl;
    private String errorCode;
    private String responseBody;

    public ApiException(String apiName, String errorMessage) {
        super(errorMessage);
        this.apiName = apiName;
    }

    public ApiException(String apiName, String apiUrl, String errorMessage) {
        super(errorMessage);
        this.apiName = apiName;
        this.apiUrl = apiUrl;
    }

    public ApiException(String apiName, String apiUrl, String errorMessage, String errorCode) {
        super(errorMessage);
        this.apiName = apiName;
        this.apiUrl = apiUrl;
        this.errorCode = errorCode;
    }

    public ApiException(String apiName, String apiUrl, String errorMessage, HttpStatus httpStatus, String responseBody) {
        super(errorMessage);
        this.apiName = apiName;
        this.apiUrl = apiUrl;
        if (httpStatus != null) {
            this.errorCode = String.valueOf(httpStatus.value());
        }
        this.responseBody = responseBody;
    }

    public ApiException(String apiName, String apiUrl, String errorMessage, String errorCode, String responseBody) {
        super(errorMessage);
        this.apiName = apiName;
        this.apiUrl = apiUrl;
        this.errorCode = errorCode;
        this.responseBody = responseBody;
    }

}