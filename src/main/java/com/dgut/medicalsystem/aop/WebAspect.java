package com.dgut.medicalsystem.aop;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dgut.medicalsystem.config.WebSocketServer;
import com.dgut.medicalsystem.entity.Appointment;
import com.dgut.medicalsystem.entity.MedicalHistory;
import com.dgut.medicalsystem.entity.Notice;
import com.dgut.medicalsystem.service.MedicalHistoryService;
import com.dgut.medicalsystem.service.NoticeService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Aspect
@Component
public class WebAspect {


    @Autowired
    MedicalHistoryService medicalHistoryService;
    @Autowired
    NoticeService noticeService;
    @Autowired
    WebSocketServer webSocketServer;

    /**
     * 切入点
     * 匹配top.alanlee.template.controller包及其子包下的所有类的所有方法
     */
    @Pointcut("execution(* finish*(..))")
    public void pointCut() {

    }

    /**
     * 前置通知，目标方法调用前被调用
     */
    @Before("pointCut()")
    public void beforeAdvice(JoinPoint joinPoint) {
        System.out.println("----------- 前置通知 -----------");
        Signature signature = joinPoint.getSignature();
        System.out.println("返回目标方法的签名：" + signature);
        System.out.println("代理的是哪一个方法：" + signature.getName());
        Object[] args = joinPoint.getArgs();
        System.out.println("获取目标方法的参数信息：" + Arrays.asList(args));

    }

    /**
     * 最终通知，目标方法执行完之后执行
     */
    @AfterReturning("pointCut()")
    public void afterAdvice(JoinPoint joinPoint) throws Exception {
        try {
            Signature signature = joinPoint.getSignature();
            String methodName = signature.getName();
            Notice notice = new Notice();
            notice.setDate(LocalDateTime.now());
            notice.setType(1);
            notice.setOwner(Integer.parseInt(StpUtil.getLoginId().toString().substring(1)));
            if (methodName.equals("finishPayBill")) {
                notice.setDescription("缴费成功,请取药");
            } else if (methodName.equals("finishAppoint")) {
                notice.setDescription("挂号成功,请前往进度查询中心查看等待叫号");
            } else {
                Integer userid = (Integer) Arrays.asList(joinPoint.getArgs()).get(0);
                notice.setDescription("取药完成,本次治疗已完成");
                notice.setOwner(userid);
            }
            //塞到消息队列中
            if (noticeService.save(notice))
                WebSocketServer.sendInfo(notice.getDescription(), notice.getOwner().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 后置返回通知
     * 如果参数中的第一个参数为JoinPoint，则第二个参数为返回值的信息
     * 如果参数中的第一个参数不为JoinPoint，则第一个参数为returning中对应的参数
     * returning 只有目标方法返回值与通知方法相应参数类型时才能执行后置返回通知，否则不执行
     *
     * @param joinPoint
     * @param keys
     */
//    @AfterReturning(value = "execution(* finish*(..))", returning = "keys")
//    public void afterReturningAdvice(JoinPoint joinPoint, String keys) {
//        System.out.println("----------- 后置返回通知 -----------");
//        System.out.println("后置返回通知的返回值：" + keys);
//    }

    /**
     * 后置异常通知
     * 定义一个名字，该名字用于匹配通知实现方法的一个参数名，当目标方法抛出异常返回后，将把目标方法抛出的异常传给通知方法；
     * throwing 只有目标方法抛出的异常与通知方法相应参数异常类型时才能执行后置异常通知，否则不执行，
     *
     * @param joinPoint
     * @param e
     */
    @AfterThrowing(value = "pointCut()", throwing = "e")
    public void afterThrowingAdvice(JoinPoint joinPoint, NullPointerException e) {
        System.out.println("----------- 后置异常通知 -----------");
    }

}
