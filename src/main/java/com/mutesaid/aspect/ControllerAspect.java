package com.mutesaid.aspect;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class ControllerAspect {
    private Logger logger = LogManager.getLogger(ControllerAspect.class);

    private static final Long FIFTY_MS = 50L;

    private static final String POINT_CUT = "execution(* com.mutesaid.service.*.*(..))";

    @Around(POINT_CUT)
    public Object serviceLogger(ProceedingJoinPoint pjp) throws Throwable{
        Long startTime = System.currentTimeMillis();
        Object obj = pjp.proceed();
        Long endTime = System.currentTimeMillis();
        if(endTime - startTime > FIFTY_MS) {
            String className = pjp.getSignature().getDeclaringTypeName();
            String methodName = pjp.getSignature().getName();
            String runName = className + "." + methodName + "()";
            Object[] args = pjp.getArgs();

            logger.info("The runing function is {}", runName);
            logger.info("The args of function are:");
            Arrays.stream(args).map(Object::toString).forEach(logger::info);
            logger.info("return: {}", obj);
            logger.info("Runtime: {}ms", endTime - startTime);
        }
        return obj;
    }

}
