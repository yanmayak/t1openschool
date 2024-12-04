package ru.yanmayak.t1openschool.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {
    @Before("@annotation(LogExecution)")
    public void logBefore(JoinPoint joinPoint) {
        log.info("Вызван метод {}", joinPoint.getSignature().getName());
    }

    @After("@annotation(LogExecution)")
    public void logAfter(JoinPoint joinPoint) {
        log.info("Метод {} завершен", joinPoint.getSignature().getName());
    }

    @AfterThrowing(pointcut = "@annotation(LogException)", throwing = "throwable")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable throwable) {
        log.info("Возникло исключение {} : {} в методе: {}",
                throwable.getClass().getName(),
                throwable.getMessage(),
                joinPoint.getSignature().getName()
        );
    }

    @AfterReturning(value = "@annotation(LogExecution)", returning = "returnValue")
    public void logAfterReturning(JoinPoint joinPoint, Object returnValue) {
        log.info("Метод {} вернул {}",
                joinPoint.getSignature().getName(),
                returnValue
        );
    }

    @Around("execution(void ru.yanmayak.t1openschool.controller..*(..))")
    public void logAroundVoidMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Метод {} вызван", joinPoint.getSignature().getName());
        try {
            joinPoint.proceed();
            log.info("Метод {} выполнен успешно", joinPoint.getSignature().getName());
        }
        catch (Throwable throwable) {
            log.error("Метод {} завершен с ошибкой: {} ",
                    joinPoint.getSignature().getName(),
                    throwable.getMessage()
            );
            throw throwable;
        }
    }
}
