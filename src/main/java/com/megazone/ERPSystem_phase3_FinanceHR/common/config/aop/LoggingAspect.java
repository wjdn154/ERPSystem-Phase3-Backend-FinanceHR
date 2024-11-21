package com.megazone.ERPSystem_phase3_FinanceHR.common.config.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    // API 요청 전후로 로깅
    @Around("execution(* com.megazone.ERPSystem_phase3_FinanceHR.financial.controller..*(..)) || " +
            "execution(* com.megazone.ERPSystem_phase3_FinanceHR.hr.controller..*(..)) || " +
            "execution(* com.megazone.ERPSystem_phase3_FinanceHR.logistics.controller..*(..)) || " +
            "execution(* com.megazone.ERPSystem_phase3_FinanceHR.production.controller..*(..))")
    public Object logApiRequest(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        logger.info("메소드 시작: " + methodName);
        Object result;
        try {
            result = joinPoint.proceed(); // 메소드 실행
            logger.info("메소드 종료: " + methodName);
        } catch (Exception e) {
            logger.error("메소드 실행 중 예외 발생: " + methodName, e);
            throw e;
        }
        return result;
    }

    // 서비스 Layer 메소드 호출 전후로 로깅
    @Around("execution(* com.megazone.ERPSystem_phase3_FinanceHR.financial.service..*(..)) || " +
            "execution(* com.megazone.ERPSystem_phase3_FinanceHR.hr.service..*(..)) || " +
            "execution(* com.megazone.ERPSystem_phase3_FinanceHR.logistics.service..*(..)) || " +
            "execution(* com.megazone.ERPSystem_phase3_FinanceHR.production.service..*(..))")
    public Object logServiceMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        logger.info("서비스 메소드 시작: " + methodName);

        Object result;
        try {
            result = joinPoint.proceed();
            logger.info("서비스 메소드 종료: " + methodName);
        } catch (Exception e) {
            logger.error("서비스 메소드 실행 중 예외 발생: " + methodName, e);
            throw e;
        }
        return result;
    }

    // 리포지토리 Layer 메소드 호출 전후로 로깅
    @Around("execution(* com.megazone.ERPSystem_phase3_FinanceHR.financial.repository..*(..)) || " +
            "execution(* com.megazone.ERPSystem_phase3_FinanceHR.hr.repository..*(..)) || " +
            "execution(* com.megazone.ERPSystem_phase3_FinanceHR.logistics.repository..*(..)) || " +
            "execution(* com.megazone.ERPSystem_phase3_FinanceHR.production.repository..*(..))")
    public Object logRepositoryMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        logger.info("리포지토리 메소드 시작: " + methodName);

        Object result;
        try {
            result = joinPoint.proceed();
            logger.info("리포지토리 메소드 종료: " + methodName);
        } catch (Exception e) {
            logger.error("리포지토리 메소드 실행 중 예외 발생: " + methodName, e);
            throw e;
        }
        return result;
    }
}
