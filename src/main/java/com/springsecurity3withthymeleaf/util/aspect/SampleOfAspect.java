package com.springsecurity3withthymeleaf.util.aspect;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SampleOfAspect {
  protected final Log logger = LogFactory.getLog(this.getClass());

  @Before("execution(public * com.springsecurity3withthymeleaf.*Service.*(..))")
  public void log(JoinPoint joinPoint) {
    System.out.printf("Appel de %s avec %d paramètres%n",
                      joinPoint.toShortString(),
                      joinPoint.getArgs().length);
    logger.info(" babbababa"+ joinPoint);
  }
}