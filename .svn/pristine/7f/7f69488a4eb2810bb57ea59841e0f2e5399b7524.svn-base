package com.yier.platform.common.exception;

//import org.apache.logging.log4j.Level;
import org.slf4j.event.Level;

public class ServiceException  extends CommonException {
    private static final long serialVersionUID = -8958002631092994278L;

    public static final int CODE = Constants.RESPONSE_CODE_APPLICATION_ERROR;
    public static final String MSG = "应用业务逻辑错误";
    public ServiceException(Throwable e) {
        super(CODE, MSG, e);
    }

    public ServiceException(String msg, Throwable e) {
        super(CODE, msg, e);
    }

    public ServiceException(int code, String msg, Throwable e) {
        super(code, msg, e);
    }

    public ServiceException(int code, String msg, Level logLevel) {
        super(code, msg, logLevel);
    }
    public ServiceException(String msg) {
        super(CODE, msg);
    }

    public ServiceException(String msg, Level logLevel) {
        super(CODE, msg, logLevel);
    }

    public ServiceException(int code, String msg) {
        super(code, msg);
    }
}
