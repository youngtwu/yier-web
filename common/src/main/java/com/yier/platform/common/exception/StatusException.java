package com.yier.platform.common.exception;

/***
 * 状态错误, 客户端需要执行刷新操作
 */
public class StatusException extends CommonException {

    private static final long serialVersionUID = 2836587347971427753L;
    public static final int CODE = Constants.RESPONSE_CODE_STATUS_ERROR_WITH_REFRESH;
    public static final String MSG = "状态错误, 客户端需要执行刷新操作";

    public String other = "其他信息";

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public StatusException(Throwable e) {
        super(CODE, MSG, e);
    }
    public StatusException(String msg, Throwable e) {
        super(CODE, msg, e);
    }

    public StatusException(int code, String msg, Throwable e) {
        super(code, msg, e);
    }

    public StatusException(String msg) {
        super(CODE, msg);
    }

    public StatusException(int code, String msg) {
        super(code, msg);
    }
    public StatusException(int code, String msg, String other) {
        super(code, msg);
        this.other = other;
    }
}