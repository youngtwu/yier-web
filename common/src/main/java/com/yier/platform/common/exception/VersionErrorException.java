package com.yier.platform.common.exception;

public class VersionErrorException   extends CommonException{
    private static final long serialVersionUID = -8769413684211905776L;
    public static final int CODE = Constants.RESPONSE_CODE_VERSION_ERROR;
    public static final String MSG = "版本号太低";

    public VersionErrorException(Throwable e) {
        super(CODE, MSG, e);
    }

    public VersionErrorException(String msg, Throwable e) {
        super(CODE, msg, e);
    }

    public VersionErrorException(int code, String msg, Throwable e) {
        super(code, msg, e);
    }

    public VersionErrorException(String msg) {
        super(CODE, msg);
    }

    public VersionErrorException(int code, String msg) {
        super(code, msg);
    }
}
