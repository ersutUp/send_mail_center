package top.ersut.mail.send.web.controller;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import top.ersut.mail.send.model.common.result.Result;

@Component
@Aspect
public class ControllerReturnNullAop {

    @Around("execution(* top.ersut.mail.send.web.controller..*Controller.*(..))")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
//        Transactional transactional = ((MethodSignature)proceedingJoinPoint.getSignature()).getMethod().getAnnotation(Transactional.class);
        Object obj = proceedingJoinPoint.proceed();
        if(obj == null){
            return Result.SUCCESS;
        }
        return obj;
    }

}
