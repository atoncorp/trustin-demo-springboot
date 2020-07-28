package com.atoncorp.trustin.demo.audit;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Aspect
@Component
public class AuditLogAspect {

    @Autowired
    ObjectMapper objectMapper;

    @AfterReturning(pointcut = "execution(* *..*Controller.*(..))", returning = "returning")
    public void logResponseBody(JoinPoint joinPoint, Object returning) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        try {

            log.debug("---------------------Start of Trustin API Response---------------------");
            log.debug("Response Body : " + objectMapper.writeValueAsString(returning));
            log.debug("---------------------End of Trustin API Response---------------------");

        } catch (Exception e) {}
    }
}
