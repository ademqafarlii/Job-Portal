package com.example.ingressjobs.aop;

import com.example.ingressjobs.aop.customAnnotation.ConsoleLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class AopLog {

    @Pointcut("execution(* com.example.ingressjobs.controller.*.*(..))")
    public void pointcut() {
    }

    @Around("@annotation(com.example.ingressjobs.aop.customAnnotation.ConsoleLog)")
    public Object authAround(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getMethod().getName();
        ConsoleLog consoleLog = methodSignature.getMethod().getAnnotation(ConsoleLog.class);

        long startTime = System.currentTimeMillis();
        log.info("[{}] {}.{} is started", consoleLog.value(), className, methodName);

        Object proceed = joinPoint.proceed();

        long endTime = System.currentTimeMillis();
        log.info("[{}] {}.{} is ended in {} ms", consoleLog.value(), className, methodName, (endTime - startTime));

        return proceed;
    }
}
