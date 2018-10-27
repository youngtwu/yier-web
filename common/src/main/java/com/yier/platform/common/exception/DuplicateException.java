package com.yier.platform.common.exception;

//import org.apache.logging.log4j.Level;
import org.slf4j.event.Level;

public class DuplicateException extends CommonException {
    private static final long serialVersionUID = -5945961735926404641L;

    public static final int CODE = Constants.RESPONSE_CODE_DUPLICATE_ERROR;
    private static final String ERROR_MESSAGE = "数据已存在";
    private final String keyInfo;


    public DuplicateException(long keyInfo) {
        super(CODE, DuplicateException.ERROR_MESSAGE, Level.INFO);
        this.keyInfo = Long.toString(keyInfo);
    }

    public DuplicateException(int code, long keyInfo) {
        super(code, DuplicateException.ERROR_MESSAGE, Level.INFO);
        this.keyInfo = Long.toString(keyInfo);
    }

    public DuplicateException( String keyInfo) {
        super(CODE, DuplicateException.ERROR_MESSAGE, Level.INFO);
        this.keyInfo = keyInfo;
    }
    public DuplicateException(int code, String keyInfo) {
        super(code, DuplicateException.ERROR_MESSAGE, Level.INFO);
        this.keyInfo = keyInfo;
    }


    public String getKeyInfo() {
        return this.keyInfo;
    }
}
