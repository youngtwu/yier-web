package com.yier.platform.common.exception;

//import org.apache.logging.log4j.Level;
import org.slf4j.event.Level;

/**
 * 统一的service层业务逻辑异常。需要在web层捕获转换成对应的编码。
 */
public class CommonException  extends RuntimeException{
    private static final long serialVersionUID = -8958002631092994278L;

    private final int code;

    private final Level logLevel;

    public CommonException(int code, String msg, Throwable e) {
        super(msg, e);
        this.code = code;
        logLevel = Level.ERROR;
    }

    public CommonException(int code, String msg) {
        super(msg);
        this.code = code;
        logLevel = Level.ERROR;
    }

    public CommonException(int code, String msg, Level logLevel) {
        super(msg);
        this.code = code;
        this.logLevel = logLevel;
    }

    public CommonException(int code, String msg, Level logLevel, Throwable e) {
        super(msg, e);
        this.code = code;
        this.logLevel = logLevel;
    }

    public int getCode() {
        return code;
    }

    public Level getLogLevel() {
        return logLevel;
    }
}
