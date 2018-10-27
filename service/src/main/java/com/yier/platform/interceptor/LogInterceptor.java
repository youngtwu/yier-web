/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.yier.platform.interceptor;

import com.yier.platform.common.util.IPUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.NamedThreadLocal;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Map;


/**
 * 日志拦截器
 */
public class LogInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(LogInterceptor.class);
    private static final ThreadLocal<Long> startTimeThreadLocal = new NamedThreadLocal<Long>("ThreadLocal StartTime");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        long beginTime = System.currentTimeMillis();// 1、开始时间
        startTimeThreadLocal.set(beginTime);        // 线程绑定变量（该数据只有当前请求的线程可见）
        if (logger.isDebugEnabled()) {
            logger.debug("日志记录拦截器调试打开");
        } else {
            logger.debug("日志记录拦截器调试关闭");
        }
        logger.debug("日志记录拦截器开始计时: {}  URI: {}", new SimpleDateFormat("hh:mm:ss.SSS")
                .format(beginTime), request.getRequestURI());
        //此时如果返回false 将会停止访问Url
        //logger.info("commonCfg.isDebug():{}",commonCfg.isDebug());//拦截器无法注入，目前是普通类不是service的原因
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        if (modelAndView != null) {
            logger.info("ViewName: " + modelAndView.getViewName() + " <<<<<<<<< " + request.getRequestURI() + " >>>>>>>>> " + handler);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {
        long beginTime = startTimeThreadLocal.get();// 得到线程绑定的局部变量（开始时间）
        long endTime = System.currentTimeMillis();    // 2、结束时间
        long executeTime = endTime - beginTime;    // 3、获取执行时间
        startTimeThreadLocal.remove(); // 用完之后销毁线程变量数据

        String serverAddress = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        String remoteAddress = IPUtils.getIpAddress(request);

        Map paramsMap = request.getParameterMap();
        String methodName = request.getMethod();
        logger.debug("服务地址：{} - 远程访问地址:{}- - 方法类型:{} ",
                serverAddress, remoteAddress, methodName);
        // 保存日志
        //LogUtils.saveLog(UserUtils.getUser(), request, handler, ex, null, null, executeTime);
    }


}
