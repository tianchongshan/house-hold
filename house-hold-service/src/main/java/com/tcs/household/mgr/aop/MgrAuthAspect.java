package com.tcs.household.mgr.aop;

import com.tcs.household.mgr.security.model.UserAuthInfo;
import com.tcs.household.model.request.AbstractBaseRequest;
import com.tcs.household.model.vo.MgrUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Created by chongshan.tian01.
 */

@Aspect
@Component
@Slf4j
@Order(2)
public class MgrAuthAspect {

    @Pointcut(value = "execution(public * com.tcs.household.mgr.controller.*.*(..)) && !execution(public * com.tcs.household.mgr.controller.SysController.addUser(..))")
    public void controllerCut(){}

    @Around(value = "controllerCut()")
    public Object around(ProceedingJoinPoint jpg) throws Throwable {
        UserAuthInfo userAuthInfo=null;
        if(SecurityContextHolder.getContext()==null
        ||SecurityContextHolder.getContext().getAuthentication()==null
        ||SecurityContextHolder.getContext().getAuthentication().getPrincipal()==null){
            return jpg.proceed();
        }
        Object obj=SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(obj instanceof UserAuthInfo){
            userAuthInfo= (UserAuthInfo) obj;
            for(Object o : jpg.getArgs()){
                if(o instanceof AbstractBaseRequest){
                    AbstractBaseRequest request= (AbstractBaseRequest) o;
                    MgrUserInfo mgrUserInfo=new MgrUserInfo();
                    mgrUserInfo.setLoginName(userAuthInfo.getLoginName());
                    mgrUserInfo.setUserName(userAuthInfo.getUsername());
                    mgrUserInfo.setId(userAuthInfo.getId());
                    request.setLoginUserInfo(mgrUserInfo);
                }
            }
        }
        return jpg.proceed();
    }


}
