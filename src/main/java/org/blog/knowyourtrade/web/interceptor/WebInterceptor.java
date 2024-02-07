package org.blog.knowyourtrade.web.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.blog.knowyourtrade.domain.constant.LogConstants;
import org.blog.knowyourtrade.domain.enums.IstioTraceStatus;
import org.blog.knowyourtrade.util.StringUtils;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.UUID;

@Slf4j
@Component
public class WebInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String traceId = MDC.get(LogConstants.ISTIO_REQUEST_HEADER);
        String spanId = MDC.get(LogConstants.SPAN_REQUEST_HEADER);
        if (StringUtils.isBlank(traceId)) {
            traceId = UUID.randomUUID().toString();
        }
        if (StringUtils.isBlank(spanId)) {
            spanId = UUID.randomUUID().toString();
        }
        MDC.put(LogConstants.ISTIO_TRACE_ID_KEY, traceId);
        MDC.put(LogConstants.ISTIO_SPAN_ID_KEY, spanId);
        MDC.put(LogConstants.ISTIO_TRACE_ID_KEY_STATUS, String.valueOf(IstioTraceStatus.CREATED));
        return true;
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        MDC.remove(LogConstants.ISTIO_TRACE_ID_KEY);
        MDC.remove(LogConstants.ISTIO_SPAN_ID_KEY);
        MDC.remove(LogConstants.ISTIO_TRACE_ID_KEY_STATUS);
    }
}
