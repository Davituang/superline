package com.heiyo.superline.web.apo;

import org.aopalliance.intercept.Joinpoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author txz
 * @date
 */
@Component
@Aspect
public class LogAspect {

    // 切入点表达式
    @Pointcut("execution(public * com.heiyo.superline.web.controller.*.*(..))")
    public void webLog(){

    }

    // 前置通知
    @Before("webLog()")
    public void doBefore(/*Joinpoint joinpoint*/){
        // 接收请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 打印请求内容
        System.out.println("url:"+request.getRequestURL().toString());
        System.out.println("method:"+request.getMethod().toString());
        System.out.println("ip:"+request.getRemoteAddr());


    }

    // 后置通知
    @AfterReturning(returning = "ret",pointcut = "webLog()")
    public void doAfterReturning(Object ret){
        // 处理返回值
        if(ret==null){
            System.out.println("该方法无返回值...");
        }else {
            System.out.println("请求方法的返回值为："+ret.toString());
        }


    }

    //后置异常通知
    @AfterThrowing(throwing = "ex",pointcut = "webLog()")
    public void doAfterThrowing(Exception ex){
        System.out.println("调用方法出现异常,异常信息："+ex.getMessage());

    }

    //后置最终通知,final增强，不管是抛出异常或者正常退出都会执行
    @After("webLog()")
    public void doAfter(){
        System.out.println("最后必须执行的方法...");
    }

    //环绕通知,环绕增强，相当于MethodInterceptor
//    @Around("webLog()")
    public Object around(ProceedingJoinPoint pjp){
        System.out.println("方法环绕start");
        try {
            Object o = pjp.proceed();
            System.out.println("方法环绕proceed,结果是："+o);
            return o;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return null;
        }

    }





}
