package org.blog.knowyourtrade.domain.exception;

import lombok.Getter;
import lombok.Setter;
import org.blog.knowyourtrade.domain.enums.ErrorCode;

@Setter
@Getter
public class ServiceException extends RuntimeException {

  private static final long serialVersionUID = 5720954041763348333L;

  private ErrorCode errorCode;

  public ServiceException(ErrorCode e) {
    super(e.getErrorMessage());
    this.errorCode = e;
  }

  public ServiceException(String errorMessage, ErrorCode e) {
    super(errorMessage);
    this.errorCode = e;
  }

  public String getErrorCodeId() {
    if (errorCode != null) {
      return errorCode.name();
    }
    return null;
  }
}