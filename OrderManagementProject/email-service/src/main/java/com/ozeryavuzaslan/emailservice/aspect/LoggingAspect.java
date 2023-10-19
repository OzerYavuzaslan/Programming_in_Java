package com.ozeryavuzaslan.emailservice.aspect;

import com.ozeryavuzaslan.basedomains.util.ColorConstants;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class LoggingAspect {
    @Pointcut("within(@org.springframework.stereotype.Component *)" +
            " || within(@org.springframework.stereotype.Service *)" +
            " || within(@org.springframework.stereotype.Repository *)" +
            " || within(@org.springframework.web.bind.annotation.RestController *)")
    public void springBeanPointcut() {
    }

    @Pointcut("within(com.ozeryavuzaslan.emailservice..*)" +
            " || within(com.ozeryavuzaslan.emailservice.configuration..*)" +
            " || within(com.ozeryavuzaslan.emailservice.dto..*)" +
            " || within(com.ozeryavuzaslan.emailservice.model..*)" +
            " || within(com.ozeryavuzaslan.emailservice.objectPropertySetter..*)" +
            " || within(com.ozeryavuzaslan.emailservice.repository..*)" +
            " || within(com.ozeryavuzaslan.emailservice.service..*)" +
            " || within(com.ozeryavuzaslan.emailservice.util..*)")
    public void applicationPackagePointcut() {
    }

    @AfterThrowing(pointcut = "applicationPackagePointcut() && springBeanPointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        log.error(ColorConstants.ANSI_RED + "Exception in {}.{}() with cause = {}" + ColorConstants.ANSI_RESET,
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                e.getCause() != null ? e.getCause() : "NULL");
    }

    @Around("applicationPackagePointcut() && springBeanPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        if (log.isDebugEnabled()) {
            String enterMessage = ColorConstants.ANSI_GREEN
                    + String.format("Enter: %s.%s() with argument[s] = %s",
                    joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(),
                    Arrays.toString(joinPoint.getArgs()))
                    + ColorConstants.ANSI_RESET;

            log.debug(enterMessage);
        }

        try {
            Object result = joinPoint.proceed();

            if (log.isDebugEnabled()) {
                String exitMessage = ColorConstants.ANSI_YELLOW
                        + String.format("Exit: %s.%s() with result = %s",
                        joinPoint.getSignature().getDeclaringTypeName(),
                        joinPoint.getSignature().getName(),
                        result)
                        + ColorConstants.ANSI_RESET;

                log.debug(exitMessage);
            }

            return result;
        } catch (IllegalArgumentException e) {
            String errorMessage = ColorConstants.ANSI_RED
                    + String.format("Illegal Argument Exception: %s in %s.%s()",
                    Arrays.toString(joinPoint.getArgs()),
                    joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName())
                    + ColorConstants.ANSI_RESET;
            log.error(errorMessage);
            throw e;
        }
    }
}