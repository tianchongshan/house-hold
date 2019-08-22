package com.tcs.household.aop;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Aspect
@Component
@Slf4j
@Order(1)
public class LogAspect {

    @Pointcut(value="execution(public * com.tcs.household.mgr.controller.*.*(..)) && !execution(public * com.tcs.household.mgr.controller.SysController.addUser(..))" +
            "&& !execution(public * com.tcs.household.mgr.controller.CommonController.*.*(..))")
    public void controllerCut() {}

    @Around(value="controllerCut()")
    public Object around(ProceedingJoinPoint jpg) throws Throwable {
        MDC.clear();
        MDC.put("TRACE", UUID.randomUUID().toString());
        MethodSignature signature = (MethodSignature) jpg.getSignature();
        long startTime = System.currentTimeMillis();
        String methodName = signature.getDeclaringTypeName() + "." + signature.getName();
        Object obj = null;
        List<Object> args = new ArrayList<>();
        for (Object o : jpg.getArgs()) {
            if (o instanceof HttpServletRequest
                    || o instanceof MultipartFile
                    || o instanceof HttpServletResponse) {
                continue;
            }
            args.add(o);
        }
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(methodName);
            sb.append(".请求参数:{}");
            String json = JSON.toJSONString(args);
            // 身份证 || 银行卡
            json = json.replaceAll("(\"\\d{3})(?:\\d{12})(\\d{2})(\\d|x|X\")","$1************$2$3");
            json = json.replaceAll("(\"\\d{4})(?:\\d{8,12})(\\d{4}\")","$1**********$2");
            log.info(sb.toString(), json);
            obj = jpg.proceed();
            return obj;
        } finally {
            StringBuilder sb = new StringBuilder();
            sb.append(methodName);
            sb.append(".返回结果:{}");
            String json = JSON.toJSONString(obj);
            // 身份证 || 银行卡
            json = json.replaceAll("(\"\\d{3})(?:\\d{12})(\\d{2})(\\d|x|X\")","$1************$2$3");
            json = json.replaceAll("(\"\\d{4})(?:\\d{8,12})(\\d{4}\")","$1**********$2");
            log.info(sb.toString(), json);

            // 响应时间
            StringBuilder sb1 = new StringBuilder();
            sb1.append(methodName);
            sb1.append(".响应时间:{} ms");
            log.info(sb1.toString(), System.currentTimeMillis() - startTime);
        }
    }

}
