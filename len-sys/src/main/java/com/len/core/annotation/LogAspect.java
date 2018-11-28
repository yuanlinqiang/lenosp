package com.len.core.annotation;

import com.alibaba.fastjson.JSON;
import com.len.base.CurrentUser;
import com.len.core.shiro.ShiroUtil;
import com.len.entity.SysLog;
import com.len.mapper.SysLogMapper;
import com.len.util.IpUtil;

import java.lang.reflect.Method;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author zhuxiaomeng
 * @date 2017/12/28.
 * @email 154040976@qq.com
 * <p>
 * 为增删改添加监控
 */
@Aspect
@Component
public class LogAspect {

    @Autowired
    private SysLogMapper logMapper;

    @Pointcut("@annotation(com.len.core.annotation.Log)")
    private void pointcut() {

    }

    @After("pointcut()")
    public void insertLogSuccess(JoinPoint jp) {
        addLog(jp, getDesc(jp));
    }

    private void addLog(JoinPoint jp, String text) {
        Log.LOG_TYPE type = getType(jp);
        SysLog log = new SysLog();
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        //一些系统监控
        if (requestAttributes != null) {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String ip = IpUtil.getIp(request);
            log.setIp(ip);
        }
        log.setCreateTime(new Date());
        log.setType(type.toString());
        log.setText(text);

        Object[] obj = jp.getArgs();
        StringBuffer buffer = new StringBuffer();
        if (obj != null) {
            for (int i = 0; i < obj.length; i++) {
                buffer.append("[参数" + (i + 1) + ":");
                Object o = obj[i];
                if(o instanceof Model){
                    continue;
                }
                String parameter=null;
                try {
                    parameter=JSON.toJSONString(o);
                }catch (Exception e){
                    continue;
                }
                buffer.append(parameter);
                buffer.append("]");
            }
        }
        log.setParam(buffer.toString());
        try {
            CurrentUser currentUser = ShiroUtil.getCurrentUse();
            log.setUserName(currentUser.getUsername());
        } catch (UnavailableSecurityManagerException e) {

        }
        logMapper.insert(log);
    }

    /**
     * 记录异常
     *
     * @param joinPoint
     * @param e
     */
    @AfterThrowing(value = "pointcut()", throwing = "e")
    public void afterException(JoinPoint joinPoint, Exception e) {
        System.out.print("-----------afterException:" + e.getMessage());
        addLog(joinPoint, getDesc(joinPoint) + e.getMessage());
    }


    private String getDesc(JoinPoint joinPoint) {
        MethodSignature methodName = (MethodSignature) joinPoint.getSignature();
        Method method = methodName.getMethod();
        return method.getAnnotation(Log.class).desc();
    }

    private Log.LOG_TYPE getType(JoinPoint joinPoint) {
        MethodSignature methodName = (MethodSignature) joinPoint.getSignature();
        Method method = methodName.getMethod();
        return method.getAnnotation(Log.class).type();
    }
}

