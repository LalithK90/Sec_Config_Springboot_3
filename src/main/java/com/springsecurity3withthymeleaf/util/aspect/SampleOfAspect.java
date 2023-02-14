package com.springsecurity3withthymeleaf.util.aspect;

import lombok.extern.java.Log;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Log
public class SampleOfAspect {

  @Before("execution( * com.springsecurity3withthymeleaf.*.service.*(..))")
  public void log(JoinPoint joinPoint) {
    System.out.printf("Appel de %s avec %d paramètres%n",
                      joinPoint.toShortString(),
                      joinPoint.getArgs().length);
    log.info("Executing method: {}" +
                    joinPoint);

  }
  @AfterReturning("execution(* com.springsecurity3withthymeleaf.*.service.*(..))")
  public void logAfterReturning(JoinPoint joinPoint) {
    log.info("Finished executing method:after "+ joinPoint.getSignature().toShortString());
  }
}