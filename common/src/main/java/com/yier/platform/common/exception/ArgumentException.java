package com.yier.platform.common.exception;

//import org.apache.logging.log4j.Level;
import org.slf4j.event.Level;

public class ArgumentException extends CommonException {
    private static final long serialVersionUID = 7130862567570137310L;
    public static final int CODE = Constants.RESPONSE_CODE_PARAMETER_ERROR;
    public static final String MEG = "参数错误信息";

    public ArgumentException(Throwable e){
        super(CODE,MEG,e);
    }
    public ArgumentException(String msg,Throwable e){
        super(CODE,msg,e);
    }

    public ArgumentException(int code,String msg,Throwable e){
        super(code,msg,e);
    }

    public ArgumentException(String msg){
        super(CODE,msg);
    }


    public ArgumentException(String msg, Level logLevel){
        super(CODE,msg, logLevel);
    }

    public ArgumentException(String msg, Level logLevel, Throwable e){
        super(CODE,msg, logLevel, e);
    }

    public ArgumentException(int code ,String msg){
        super(code,msg);
    }

    public ArgumentException(int code ,String msg, Level logLevel){
        super(code,msg, logLevel);
    }
}
