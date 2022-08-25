package com.geekbrains.spring.web.aspects;

import com.geekbrains.spring.web.utils.ServiceStatistic;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TimeTrackingAspect {

    @Autowired
    private ServiceStatistic serviceStatistic;

    @Around("execution(public * com.geekbrains.spring.web.services.*.*(..))")
    public Object getDuration(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object out = proceedingJoinPoint.proceed();
        long stop = System.currentTimeMillis();
        long duration = stop - start;
        String className = proceedingJoinPoint.getThis().getClass().getSimpleName();
        serviceStatistic.addTimeAndHits(className, duration);
        return out;
    }

}
