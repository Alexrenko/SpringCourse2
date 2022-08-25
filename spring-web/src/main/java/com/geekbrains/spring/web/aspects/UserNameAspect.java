package com.geekbrains.spring.web.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class UserNameAspect {

    @After("execution(public * com.geekbrains.spring.web.controllers.AuthController.createAuthToken(..))")
    public void printUserName(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        if (args.length == 1) {
            System.out.println(args[0]);
        }
    }

}
