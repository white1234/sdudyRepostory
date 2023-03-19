package com.studydemo.demo.handler;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.studydemo.demo.annotation.OperationLog;
import com.studydemo.demo.em.BaseErrorEnum;
import com.studydemo.demo.em.OperTypeEnum;
import com.studydemo.demo.exp.BaseException;
import com.studydemo.demo.model.LogQueue;
import com.studydemo.demo.model.entity.SysLog;
import com.studydemo.demo.service.ISysLogService;
import com.studydemo.demo.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @Author teronb
 * @Date 2023/2/3 22:55
 */
@Slf4j
@Aspect
@Component
@EnableAsync
public class SystemLogAspect {

    @Autowired
    ISysLogService sysLogService;

    @Autowired
    JwtUtils jwtUtils;

    @Resource
    LogQueue logQueue;

    private String requestPath;
    private long startTimeMillis = 0;
    private long endTimeMillis = 0;
    private String user;
    private HttpServletRequest request = null;

    /**
     * @Description 设置切面切入点为注解位置
     * @Param
     * @Return
     * @Author teronb
     * @Date 2023/2/3 23:03
     */
    @Pointcut("@annotation(com.studydemo.demo.annotation.OperationLog)")
    //@Pointcut("execution( * com.studydemo.demo.service..*.*(..))")
    public void logPointCut(){}

    @Before(value = "logPointCut()")
    public void before(JoinPoint joinPoint){
        startTimeMillis = System.currentTimeMillis();
        System.out.println(startTimeMillis);
    }

    /**
     * @Description 获取方法传入参数，int和字符类型
     * @Param joinPoint
     * @Return {@link Map< String, Object>}
     * @Author teronb
     * @Date 2023/2/3 23:09
     */
    public Map<String,Object> getNameAndValue(JoinPoint joinPoint){
        Map<String,Object> param = new HashMap<>();
        Object[] paramValue = joinPoint.getArgs();
        String[] paramNames = ((CodeSignature)joinPoint.getSignature()).getParameterNames();

        for (int i=0;i < paramNames.length;i++){
            if(paramValue[i] instanceof Integer || paramValue[i] instanceof String){
                param.put(paramNames[i],paramValue[i]);
            }
        }
        return param;
    }
    /**
     * @param joinPoint
     * @Description 后置通知    方法调用后触发   记录结束时间 ,操作人 ,入参等
     */
    @AfterReturning(value="logPointCut()")
    public void after(JoinPoint joinPoint) {
        request = getHttpServletRequest();
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class<?> targetClass = null;
        try {
            targetClass = Class.forName(targetName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Method[] methods = targetClass.getMethods();
        String title;
        String action;
        OperationLog.SysType sysType;
        OperTypeEnum opType;
        Class<?>[] clazzs;
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                clazzs = method.getParameterTypes();
                if (clazzs!=null&&clazzs.length == arguments.length
                        &&method.getAnnotation(OperationLog.class)!=null) {
                    request = getHttpServletRequest();
                    requestPath=request.getServletPath();
                    /*HttpSession session = request.getSession();
                    user = session.getAttribute("userName").toString();*/
                    String jwt =  request.getHeader(jwtUtils.getHeader());
                    if(StrUtil.isEmpty(jwt)){
                        user = "test";
                        /*if(requestPath.contains("/login/")){
                           user = "/login";
                        }else {
                            throw new BaseException(BaseErrorEnum.USER_INVALID);
                        }*/
                    }else {
                        Claims claim = jwtUtils.getClaimsByToken(jwt);
                        if (claim == null) {
                            throw new JwtException("token 异常");
                        }
                        if (jwtUtils.isTokenExpired(claim)) {
                            throw new JwtException("token 已过期");
                        }
                        user = claim.getSubject();
                    }
                    title = method.getAnnotation(OperationLog.class).content();
                    action = method.getAnnotation(OperationLog.class).action();
                    sysType = method.getAnnotation(OperationLog.class).sysType();
                    opType = method.getAnnotation(OperationLog.class).opType();
                    endTimeMillis = System.currentTimeMillis();

                    SysLog sysLog=new SysLog(user, requestPath,
                            DateUtil.formatDateTime(new Date()),(endTimeMillis-startTimeMillis)+"ms",
                            getNameAndValue(joinPoint).toString(), title, action,sysType.getCode(),opType.getCode());
                   // System.out.println("增加参数："+log);
                    logQueue.add(sysLog);
                    log.info(DateUtil.formatDateTime(new Date())+"调用接口"+title+"插入一条日志到logQueue,当前队列长度"+logQueue.getSize());
                   // sysLogService.saveLogAsync(log);
//                  break;
                }
            }
        }
    }
    /**
     * @Description: 获取request
     */
    public HttpServletRequest getHttpServletRequest(){
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes)ra;
        HttpServletRequest request = sra.getRequest();
        return request;
    }
    /**
     * @param joinPoint
     * @return 环绕通知
     * @throws Throwable
     */
    public Object around(ProceedingJoinPoint joinPoint) {
        return null;
    }
    /**
     * @param joinPoint
     * @Description 异常通知
     */
    public void throwing(JoinPoint joinPoint) {
        System.out.println("异常通知");
    }

}
