//package com.yier.platform.interceptor;
//
//
//import com.auth0.jwt.exceptions.JWTCreationException;
//import com.auth0.jwt.exceptions.JWTDecodeException;
//import com.auth0.jwt.exceptions.JWTVerificationException;
//import com.auth0.jwt.exceptions.SignatureGenerationException;
//import com.github.yingzhuo.carnival.jwt.exception.InvalidClaimException;
//import com.github.yingzhuo.carnival.jwt.exception.SignatureVerificationException;
//import com.github.yingzhuo.carnival.jwt.exception.TokenExpiredException;
//import com.github.yingzhuo.carnival.restful.security.exception.*;
//import com.yier.platform.common.exception.*;
//import com.yier.platform.common.jsonResponse.JsonResponse;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.web.bind.MissingServletRequestParameterException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.context.request.NativeWebRequest;
//
////import org.apache.logging.log4j.LogManager;
////import org.apache.logging.log4j.Logger;
//
//@ControllerAdvice
//public class ApiExceptionHandler {//配置全局异常捕获器:一方面可以捕获到整个项目中的Exception及其子类（包含RuntimeException等），另一方面可以对异常进行统一处理并返回统一的数据格式，为前端提供友好的数据交互。
//    private static final Logger logger = LoggerFactory.getLogger(ApiExceptionHandler.class);
//
//    @ExceptionHandler
//    @ResponseBody
//    public JsonResponse errorHandler(NativeWebRequest request, Throwable exception) {
//        JsonResponse res = new JsonResponse();
//        //logger.info("进入异常监控处理");
////        logger.debug("进入异常监控处理：request {} exception {}", request, exception);
////        logger.warn(exception.getMessage(), exception);
//        String errorMsg = "系统错误";
//        Integer status = 3000;
//        if (exception instanceof MissingServletRequestParameterException) {
//            MissingServletRequestParameterException rtE = (MissingServletRequestParameterException) exception;
//            status = Constants.ERROR_PARAMETER_EXCEPTION;
//            errorMsg = rtE.getMessage();
//            logger.info("[异常监控处理]请求参数错误: " + exception.getMessage());
//        } else if (exception instanceof TokenException) {
//            TokenException rtE = (TokenException) exception;
//            status = rtE.getCode();
//            errorMsg = rtE.getMessage();
//            logger.info("[异常监控处理]token登录无效: " + exception.getMessage());
//        } else if (exception instanceof IllegalArgumentException) {
//            ArgumentException bizE = new ArgumentException(exception.getMessage(), exception);
//            status = bizE.getCode();
//            errorMsg = bizE.getMessage();
//            logger.info("[异常监控处理]参数异常: " + exception.getMessage(), exception);
//        } else if (exception instanceof ArgumentException) {
//            ArgumentException argumentException = (ArgumentException) exception;
//            status = argumentException.getCode();
//            errorMsg = argumentException.getMessage();
//            logger.info("[异常监控处理]参数异常: " + argumentException.getMessage(), argumentException);
//        } else if (exception instanceof StatusException) {
//            StatusException parkStatusException = (StatusException) exception;
//            status = parkStatusException.getCode();
//            errorMsg = parkStatusException.getMessage();
//            res.setOtherInfo(parkStatusException.getOther());
//            logger.info("[异常监控处理]状态异常: " + parkStatusException.getMessage(), parkStatusException);
//        } else if (exception instanceof ServiceException) {
//            ServiceException exp = (ServiceException) exception;
//            status = exp.getCode();
//            errorMsg = exp.getMessage();
//            logger.info("[异常监控处理]业务异常: " + exception.getMessage(), exp);
//        }
//
//
//        else if (exception instanceof TokenExpiredException) {
//            TokenExpiredException exp = (TokenExpiredException) exception;
//            //status = exp.getCode();
//            status = Constants.RESPONSE_CODE_TOKEN_ERROR;
//            errorMsg = exp.getMessage();
//            logger.info("[异常监控处理]登录令牌过期: " + exception.getMessage(), exp);
//        } else if (exception instanceof JWTVerificationException) {
//            JWTVerificationException exp = (JWTVerificationException) exception;
//            //status = exp.getCode();
//            status = Constants.RESPONSE_CODE_TOKEN_ERROR;
//            errorMsg = exp.getMessage();
//            logger.info("[异常监控处理]登录令牌验证无效: " + exception.getMessage(), exp);
//        } else if (exception instanceof SignatureVerificationException) {
//            SignatureVerificationException exp = (SignatureVerificationException) exception;
////            status = exp.getCode();
//            status = Constants.RESPONSE_CODE_TOKEN_ERROR;
//            errorMsg = exp.getMessage();
//            logger.info("[异常监控处理]登录令牌签名无效: " + exception.getMessage(), exp);
//        } else if (exception instanceof SignatureGenerationException) {
//            SignatureGenerationException exp = (SignatureGenerationException) exception;
////            status = exp.getCode();
//            status = Constants.RESPONSE_CODE_SERVICE_ERROR;
//            errorMsg = exp.getMessage();
//            logger.info("[异常监控处理]令牌签名生成异常: " + exception.getMessage(), exp);
//        } else if (exception instanceof JWTDecodeException) {
//            JWTDecodeException exp = (JWTDecodeException) exception;
////            status = exp.getCode();
//            status = Constants.RESPONSE_CODE_SERVICE_ERROR;
//            errorMsg = exp.getMessage();
//            logger.info("[异常监控处理]令牌解析出现异常: " + exception.getMessage(), exp);
//        } else if (exception instanceof JWTCreationException) {
//            JWTCreationException exp = (JWTCreationException) exception;
////            status = exp.getCode();
//            status = Constants.RESPONSE_CODE_SERVICE_ERROR;
//            errorMsg = exp.getMessage();
//            logger.info("[异常监控处理]登录令牌创建时异常: " + exception.getMessage(), exp);
//        } else if (exception instanceof InvalidClaimException) {
//            InvalidClaimException exp = (InvalidClaimException) exception;
////            status = exp.getCode();
//            status = Constants.RESPONSE_CODE_SERVICE_ERROR;
//            errorMsg = exp.getMessage();
//            logger.info("[异常监控处理]令牌解析无效的声明异常: " + exception.getMessage(), exp);
//        } else if (exception instanceof AuthenticationException) {
//            AuthenticationException exp = (AuthenticationException) exception;
////            status = exp.getCode();
//            status = Constants.RESPONSE_CODE_PRIVILEGE_ERROR;
//            errorMsg = exp.getMessage();
//            logger.info("[异常监控处理]认证异常: " + exception.getMessage(), exp);
//        } else if (exception instanceof AuthorizationException) {
//            AuthorizationException exp = (AuthorizationException) exception;
////            status = exp.getCode();
//            status = Constants.RESPONSE_CODE_PRIVILEGE_ERROR;
//            errorMsg = exp.getMessage();
//            logger.info("[异常监控处理]授权异常: " + exception.getMessage(), exp);
//        } else if (exception instanceof RestfulSecurityException) {
//            RestfulSecurityException exp = (RestfulSecurityException) exception;
////            status = exp.getCode();
//            status = Constants.RESPONSE_CODE_PRIVILEGE_ERROR;
//            errorMsg = exp.getMessage();
//            logger.info("[异常监控处理]安全方面异常: " + exception.getMessage(), exp);
//        } else if (exception instanceof UnsupportedTokenTypeException) {
//            UnsupportedTokenTypeException exp = (UnsupportedTokenTypeException) exception;
////            status = exp.getCode();
//            status = Constants.RESPONSE_CODE_PRIVILEGE_ERROR;
//            errorMsg = exp.getMessage();
//            logger.info("[异常监控处理]安全方面不支持令牌异常: " + exception.getMessage(), exp);
//        } else if (exception instanceof LimitedAdultContentException) {
//            LimitedAdultContentException exp = (LimitedAdultContentException) exception;
////            status = exp.getCode();
//            status = Constants.RESPONSE_CODE_PRIVILEGE_ERROR;
//            errorMsg = exp.getMessage();
//            logger.info("[异常监控处理]安全方面非成人异常: " + exception.getMessage(), exp);
//        } else if (exception instanceof UserDetailsExpiredException) {
//            UserDetailsExpiredException exp = (UserDetailsExpiredException) exception;
////            status = exp.getCode();
//            status = Constants.RESPONSE_CODE_PRIVILEGE_ERROR;
//            errorMsg = exp.getMessage();
//            logger.info("[异常监控处理]安全方面账号过期异常: " + exception.getMessage(), exp);
//        } else if (exception instanceof UserDetailsLockedException) {
//            UserDetailsLockedException exp = (UserDetailsLockedException) exception;
////            status = exp.getCode();
//            status = Constants.RESPONSE_CODE_PRIVILEGE_ERROR;
//            errorMsg = exp.getMessage();
//            logger.info("[异常监控处理]安全方面账号被锁定异常: " + exception.getMessage(), exp);
//        } else {
//            errorMsg = "系统异常, 请联系系统管理员([调试阶段显示提示内容]" + exception.getMessage() + ")";
//            status = Constants.RESPONSE_CODE_SERVICE_ERROR;
//            logger.info("[异常监控处理]未定义异常: " + exception.getMessage(), exception);
//        }
//        logger.warn("status = {}, message = {}", status, errorMsg);
//
//        res.setStatus(status);
//        res.setError(errorMsg);
//        return res;
//
//    }
//
//}
