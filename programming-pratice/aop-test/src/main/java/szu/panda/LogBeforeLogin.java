package szu.panda;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @Author: Winnie
 * @Date: 2019-07-26
 * @Description:
 */
@Aspect
public class LogBeforeLogin {

    @Pointcut("execution(* szu.panda.*.login(..))")
    public void loginMethod(){}

    @Before("loginMethod()")
    public void beforeLogin(){
        System.out.println("准备登陆....");
    }
}