package com.yier.platform.common.exception;

//import org.apache.logging.log4j.Level;
import org.slf4j.event.Level;

/**
 * 统一的服务器异常。需要在web层捕获转换成对应的编码。
 * 对应编码的范围：4000
 */
public class TokenException  extends CommonException {
    private static final long serialVersionUID = -8958002631092994278L;

    public static final int CODE = Constants.RESPONSE_CODE_TOKEN_ERROR;
    public static final String MSG = "token失效";
    public TokenException(Throwable e){
        super(CODE,MSG,e);
    }

    public TokenException(String msg,Throwable e){
        super(CODE,msg,e);
    }

    public TokenException(String msg){
        super(CODE,msg);
    }


    public TokenException(String msg, Level logLevel){
        super(CODE,msg, logLevel);
    }

    public TokenException(int code ,String msg){
        super(code,msg);
    }

    public TokenException(int code ,String msg, Level logLevel){
        super(code,msg, logLevel);
    }
}
