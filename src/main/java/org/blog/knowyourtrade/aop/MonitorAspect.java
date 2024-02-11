package org.blog.knowyourtrade.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.blog.knowyourtrade.domain.enums.ErrorCode;
import org.blog.knowyourtrade.domain.exception.ServiceException;
import org.blog.knowyourtrade.util.StringUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.time.Duration;
import java.time.Instant;

@Slf4j
@Aspect
@Component
public class MonitorAspect {

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

            String reason = null;
            if (throwable != null) {
                if (throwable instanceof ServiceException) {
                    ErrorCode errorCode = ((ServiceException) throwable).getErrorCode();
                    if (errorCode != null) {
                        reason = errorCode.getErrorMessage();
                    }
                } else {
                    reason = ErrorCode.UNKNOWN_EXCEPTION.getErrorMessage();
                }
            } else {
                reason = ErrorCode.SUCCESS.getErrorMessage();
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
