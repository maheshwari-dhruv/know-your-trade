package org.blog.knowyourtrade.aop;

import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.blog.knowyourtrade.domain.enums.ErrorCode;
import org.blog.knowyourtrade.domain.exception.ServiceException;
import org.blog.knowyourtrade.util.LogUtils;
import org.blog.knowyourtrade.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

@Slf4j
@Aspect
@Component
public class MonitorAspect {

    private static final String REQUEST_NAME = "know-your-trades-request";
    private static final String TIMER_NAME = "know-your-trades-timers";

    @Autowired
    private MeterRegistry meterRegistry;

    @Around("@annotation(org.blog.knowyourtrade.aop.Monitor)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String simpleName = joinPoint.getTarget().getClass().getSimpleName();
        if (Proxy.isProxyClass(joinPoint.getTarget().getClass())) {
            InvocationHandler invocationHandler = Proxy.getInvocationHandler(joinPoint.getTarget());
            simpleName = invocationHandler.getClass().getSimpleName();
        }

        String methodName = joinPoint.getSignature().getName();
        Monitor monitorClass = joinPoint.getTarget().getClass().getAnnotation(Monitor.class);
        Monitor monitorMethod = methodSignature.getMethod().getAnnotation(Monitor.class);
        String name = StringUtils.isNotBlank(monitorMethod.value()) ? monitorMethod.value() : (simpleName + "." + methodName);
        String monitorType = monitorMethod.monitor().getCode();
        if (monitorClass != null) {
            monitorType = monitorClass.monitor().getCode();
        }

        Object result = null;
        Instant startTime = Instant.now();

        Throwable throwable = null;
        try {
            result = joinPoint.proceed();
        } catch (Throwable t) {
            throwable = t;
            throw t;
        } finally {
            Instant endTime = Instant.now();
            Duration between = Duration.between(startTime, endTime);
            Object[] args = joinPoint.getArgs();

            String status = null;
            String reason = null;
            if (throwable != null) {
                if (throwable instanceof ServiceException) {
                    ErrorCode errorCode = ((ServiceException) throwable).getErrorCode();
                    if (errorCode != null) {
                        status = errorCode.getErrorCode();
                        reason = errorCode.getErrorMessage();
                    }
                } else {
                    status = ErrorCode.UNKNOWN_EXCEPTION.getErrorCode();
                    reason = ErrorCode.UNKNOWN_EXCEPTION.getErrorMessage();
                }
            } else {
                status = ErrorCode.SUCCESS.getErrorCode();
                reason = ErrorCode.SUCCESS.getErrorMessage();
            }

            if (monitorMethod.requests()) {
                meterRegistry.counter(REQUEST_NAME, "name", name, "monitor", monitorType, "status", status, "reason", reason).increment();
            }

            if (monitorMethod.timer()) {
                meterRegistry.timer(TIMER_NAME, "name", name, "monitor", monitorType, "status", status, "reason", reason).record(between);
            }

            StringBuilder argsBuilder = new StringBuilder();
            StringBuilder resBuilder = new StringBuilder();
            if (monitorMethod.parameterOutput()) {
                if (args != null) {
                    if (args.length > 1) {
                        argsBuilder.append("[");
                        for (int i = 0; i < args.length; i++) {
                            if (i != 0) {
                                argsBuilder.append(", ");
                            }
                            argsBuilder.append(args[i] != null ? args[i].toString() : "");
                        }
                        argsBuilder.append("]");
                    } else {
                        argsBuilder.append(args[0] != null ? args[0].toString() : "");
                    }
                }
            } else {
                argsBuilder.append("...");
            }

            if (monitorMethod.resultOutput()) {
                resBuilder.append(result != null ? result.toString() : "");
            } else {
                resBuilder.append("...");
            }

            if (resBuilder.length() > 500) {
                String substring = resBuilder.substring(0, 500);
                resBuilder = new StringBuilder(substring + " ...");
            }

            log.info("{} Monitor: {} | args: {} | result: {} | reason: {} | time: {}ms", monitorType,
                    name, argsBuilder, resBuilder, reason, between.toMillis());
        }

        return result;
    }
}
