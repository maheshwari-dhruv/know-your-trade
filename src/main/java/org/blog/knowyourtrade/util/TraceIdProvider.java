package org.blog.knowyourtrade.util;

import org.slf4j.MDC;

public abstract class TraceIdProvider {

  public static String getTraceId() {
    return MDC.get("traceId");
  }

}