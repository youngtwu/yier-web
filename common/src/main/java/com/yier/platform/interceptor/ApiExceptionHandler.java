package com.yier.platform.interceptor;


import com.yier.platform.common.exception.*;
import com.yier.platform.common.jsonResponse.JsonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.NativeWebRequest;

//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

@ControllerAdvice
public class ApiExceptionHandler {//配置全局异常捕获器:一方面可以捕获到整个项目中的Exception及其子类（包含RuntimeException等），另一方面可以对异常进行统一处理并返回统一的数据格式，为前端提供友好的数据交互。
    private static final Logger logger = LoggerFactory.getLogger(ApiExceptionHandler.class);

    @ExceptionHandler
    @ResponseBody
    public JsonResponse errorHandler(NativeWebRequest request, Throwable exception) {
        //logger.info("进入异常监控处理");
//        logger.debug("进入异常监控处理：request {} exception {}", request, exception);
        logger.warn(exception.getMessage(), exception);
        String errorMsg = "系统错误";
        Integer status = 3000;
        if(exception instanceof MissingServletRequestParameterException) {
            MissingServletRequestParameterException rtE = (MissingServletRequestParameterException) exception;
            status = Constants.ERROR_PARAMETER_EXCEPTION;
            errorMsg = rtE.getMessage();
            logger.info("请求参数错误: " + exception.getMessage());
        }
        else if (exception instanceof TokenException) {
            TokenException rtE = (TokenException) exception;
            status = rtE.getCode();
            errorMsg = rtE.getMessage();
            logger.info("token失效: " + exception.getMessage());
        } else if (exception instanceof IllegalArgumentException) {
            ArgumentException bizE = new ArgumentException(exception.getMessage(), exception);
            status = bizE.getCode();
            errorMsg = bizE.getMessage();
            logger.info("参数异常: " + exception.getMessage(), exception);
        } else if (exception instanceof ArgumentException) {
            ArgumentException argumentException = (ArgumentException) exception;
            status = argumentException.getCode();
            errorMsg = argumentException.getMessage();
            logger.info("参数异常: " + argumentException.getMessage(), argumentException);
        } else if (exception instanceof StatusException) {
            StatusException parkStatusException = (StatusException) exception;
            status = parkStatusException.getCode();
            errorMsg = parkStatusException.getMessage();
            logger.info("状态异常: " + parkStatusException.getMessage(), parkStatusException);
        } else if (exception instanceof ServiceException) {
            ServiceException exp = (ServiceException) exception;
            status = exp.getCode();
            errorMsg = exp.getMessage();
            logger.info("业务异常: " + exception.getMessage(), exp);
        } else {
            logger.info("未定义异常: " + exception.getMessage(), exception);
            errorMsg = "系统异常, 请联系系统管理员("+exception.getMessage()+")";
            status = Constants.RESPONSE_CODE_SERVICE_ERROR;
        }
        logger.warn("status = {}, message = {}", status, errorMsg);

        JsonResponse res = new JsonResponse();
        res.setStatus(status);
        res.setError(errorMsg);
        return res;

    }

}
