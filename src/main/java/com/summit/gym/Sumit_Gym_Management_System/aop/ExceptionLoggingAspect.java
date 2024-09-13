package com.summit.gym.Sumit_Gym_Management_System.aop;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class ExceptionLoggingAspect {

        private final Logger logger = LogManager.getLogger(ExceptionLoggingAspect.class);
//    private final Logger logger = LoggerFactory.getLogger(ExceptionLoggingAspect.class);
    @AfterThrowing(pointcut = "execution(* com.summit.gym.Sumit_Gym_Management_System..*(..))",
            throwing = "e")
    public void logExceptions(Exception e) {

        logger.error("Printing from logger "+ Arrays.toString(e.getStackTrace()));
    }



}
