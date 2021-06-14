package com.epam.liadov;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * LoggingBean - class for logging of methods annotated with {@link Logging}
 *
 * @author Aleksandr Liadov
 */
@Slf4j
@Aspect
@AllArgsConstructor
public class LoggingBean {

    /**
     * Method create JoinPoint on method annotated with @Logging
     */
    @Pointcut("@annotation(Logging)")
    public void invokeLogMethod() {
    }

    /**
     * Method for logging of Method
     *
     * @param joinPoint code connecting point
     */
    @Before("invokeLogMethod()")
    public void logMethod(JoinPoint joinPoint) {
        Signature loggedMethod = joinPoint.getSignature();
        Object[] methodArgs = joinPoint.getArgs();
        int argsLength = methodArgs.length;
        var stringBuilder = new StringBuilder("Input arguments size = ")
                .append(argsLength);
        if (argsLength > 0) {
            for (int i = 0; i < methodArgs.length; i++) {
                stringBuilder.append("; arg(")
                        .append(i)
                        .append(") = ")
                        .append(methodArgs[i]);
            }
        }
        log.info("LOGGING(INFO): Method called: {}; {}", loggedMethod, stringBuilder);
    }
}