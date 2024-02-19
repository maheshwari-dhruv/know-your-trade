package org.blog.knowyourtrade.web.handlers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.blog.knowyourtrade.domain.dto.base.GenericResponse;
import org.blog.knowyourtrade.domain.enums.ErrorCode;
import org.blog.knowyourtrade.domain.exception.ApiException;
import org.blog.knowyourtrade.domain.exception.ParameterException;
import org.blog.knowyourtrade.domain.exception.ServiceException;
import org.blog.knowyourtrade.domain.exception.ValidatedException;
import org.blog.knowyourtrade.util.TraceIdProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Objects;

@Slf4j
@ControllerAdvice
public class AppExceptionHandler {
  public static final String HEADER_NAME = "X-traceId";

  @ExceptionHandler({HttpMessageNotReadableException.class})
  public ResponseEntity<Object> handleExceptions(HttpServletRequest request, HttpMessageNotReadableException e) {
    log.error("HttpMessageNotReadableException: {} | url: {}", e.getMessage(), request.getRequestURL());

    HttpHeaders headers = new HttpHeaders();
    headers.add(HEADER_NAME, TraceIdProvider.getTraceId());

    return new ResponseEntity<>(new GenericResponse<>(HttpStatus.BAD_REQUEST.getReasonPhrase(), ErrorCode.BAD_REQUEST),
            headers, HttpStatus.BAD_REQUEST
    );
  }

  @ExceptionHandler({HttpMessageNotWritableException.class})
  public ResponseEntity<Object> handleExceptions(HttpServletRequest request, HttpMessageNotWritableException e) {
    log.error("HttpMessageNotWritableException: {} | url: {}", e.getMessage(), request.getRequestURL());

    HttpHeaders headers = new HttpHeaders();
    headers.add(HEADER_NAME, TraceIdProvider.getTraceId());

    return new ResponseEntity<>(new GenericResponse<>(HttpStatus.BAD_REQUEST.getReasonPhrase(), ErrorCode.BAD_REQUEST),
            headers, HttpStatus.BAD_REQUEST
    );
  }

  @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
  public ResponseEntity<Object> handleExceptions(HttpServletRequest request, HttpRequestMethodNotSupportedException e) {
    log.error("HttpRequestMethodNotSupportedException: {} | url: {}", e.getMessage(), request.getRequestURL());

    HttpHeaders headers = new HttpHeaders();
    headers.add(HEADER_NAME, TraceIdProvider.getTraceId());

    return new ResponseEntity<>(new GenericResponse<>(e.getMessage(), ErrorCode.BAD_REQUEST),
            headers, HttpStatus.BAD_REQUEST
    );
  }

  @ExceptionHandler(value = {ServiceException.class, ValidatedException.class, ParameterException.class})
  public ResponseEntity<Object> handleServiceExceptions(HttpServletRequest request, ServiceException e) {
    log.error("ServiceException: {} | url: {}", e.getMessage(), request.getRequestURL());

    HttpHeaders headers = new HttpHeaders();
    headers.add(HEADER_NAME, TraceIdProvider.getTraceId());

    return new ResponseEntity<>(
            new GenericResponse<>(e.getMessage(), e.getErrorCode()),
            headers,
            e.getErrorCode().getHttpCode()
    );
  }

  @ExceptionHandler({ApiException.class})
  public ResponseEntity<Object> handleApiExceptions(ApiException e) {
    log.error("ApiException: {} | api-name: {} | api-url: {} | api-code: {} | api-result: {}",
            e.getMessage(), e.getApiName(), e.getApiUrl(), e.getErrorCode(), e.getResponseBody());

    HttpHeaders headers = new HttpHeaders();
    headers.add(HEADER_NAME, TraceIdProvider.getTraceId());

    return new ResponseEntity<>(
            new GenericResponse<>("request " + e.getApiName() + " api failed, message: " + e.getMessage(), ErrorCode.API_ERROR),
            headers,
            ErrorCode.API_ERROR.getHttpCode()
    );
  }

  @ExceptionHandler({Exception.class})
  public ResponseEntity<Object> handleExceptions(HttpServletRequest request, Exception e) {
    log.error("Exception Handler: {} | url: {}", e.getMessage(), request.getRequestURL());

    HttpHeaders headers = new HttpHeaders();
    headers.add(HEADER_NAME, TraceIdProvider.getTraceId());

    if (e instanceof MethodArgumentNotValidException) {
      return new ResponseEntity<>(
              new GenericResponse<>(Objects.requireNonNull(((MethodArgumentNotValidException) e).getFieldError()).getDefaultMessage(), ErrorCode.PARAM_ILLEGAL),
              headers,
              ErrorCode.PARAM_ILLEGAL.getHttpCode()
      );
    }
    return new ResponseEntity<>(
            new GenericResponse<>(ErrorCode.INTERNAL_SERVER_ERROR.getErrorMessage(), ErrorCode.INTERNAL_SERVER_ERROR),
            headers,
            HttpStatus.INTERNAL_SERVER_ERROR
    );
  }
}