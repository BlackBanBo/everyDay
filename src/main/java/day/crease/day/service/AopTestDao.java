package day.crease.day.service;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
//@Aspect
public class AopTestDao {

    private static final Logger log = LoggerFactory.getLogger(AopTestDao.class);

    /**
     * 定义切点1
     */
    @Pointcut(value = "execution(public String testAop (..) )")
    public void cutOne(){

    }

    @Pointcut(value = "execution(public * day.crease.day..*.* (..))")
    public void cutTwo(){

    }

    /**
     * 前置通知，方法执行前被调用
     */
    @Before("cutOne()")
    public void beforeTest(){
        log.info("--------方法执行前被调用----------");
    }

    /**
     * 后置通知，方法执行后被调用
     */
    @After("cutTwo()")
    public void afterTest(){
        log.info("---------------------方法执行后被调用-----------");
    }

}
