package com.yier.platform.common.exception;

public interface Constants {
    /** 成功 */
    public static final int RESPONSE_CODE_SUCCESS = 0;

    /** 参数错误 */
    public static final int RESPONSE_CODE_PARAMETER_ERROR = 1000;
    /** 无效参数错误 */
    public static final int ERROR_INVALID_PARAMETER = 1001;
    /** 没有partner 或 channel */
    public static final int RESPONSE_CODE_PARAMETER_INVALID_PARTNER_ID = 1010;

    /** 签名错误 */
    public static final int RESPONSE_CODE_PARAMETER_INVALID_SIGN = 1011;

    /** 应用业务逻辑错误 */
    public static final int RESPONSE_CODE_APPLICATION_ERROR = 2000;

    /** 业务异常 **/
    public static final int USER_EXIST_ERROR = 2001;
    public static final int USER_NOT_EXIST_ERROR = 2002;

    /** 重复数据错误. 在插入数据时, 如果有相同的数据存在, 会返回这个错误 */
    public static final int RESPONSE_CODE_DUPLICATE_ERROR = 2010;


    /**
     * 司机端收到2003错误码强制刷新
     */
    public static final int TASK_PROCESS_ERROR = 2003;

    /** 版本号太低 **/
    public static final int RESPONSE_CODE_VERSION_ERROR = 2100;

    /** 没有数据 */
    public static final int RESPONSE_CODE_NO_DATA = 2200;

    /** 状态错误, 客户端需要执行刷新操作 */
    public static final int RESPONSE_CODE_STATUS_ERROR_WITH_REFRESH = 2300;
    public static final int RESPONSE_CODE_STATUS_ERROR_NO_REFRESH = 2301;

    /**权限错误*/
    public static final int RESPONSE_CODE_PRIVILEGE_ERROR = 2400;

    /** 服务系统错误 */
    public static final int RESPONSE_CODE_SERVICE_ERROR = 3000;

    /** token失效 */
    public static final int RESPONSE_CODE_TOKEN_ERROR = 4000;
    /** 请求取消 */
    public static final int ERROR_CANCEL = 4001;
    /** 远程调用请求错误 */
    public static final int ERROR_REMOTE_ERROR = 4011;
    /** 未知错误 */
    public static final int ERROR_UNKNOW = 9999;

    public static final  int RESPONSE_CODE_NOT_CORRESPONDED_VALETCOUPON_ERROR = 5000;
    public static final int HTTP_CACHE = 5 * 60; // 5分钟

    public static final int ERROR_SERVICE_EXCEPTION = 10000;//10000	服务内部错误	服务端内部逻辑错误,请稍后重试	500
    public static final int ERROR_RESPONSE_OUTTIME = 10001;//10001	服务响应超时	内部服务响应超时	504
    public static final int ERROR_SECRET_EXCEPTION = 10002;//10002	Secret错误	Access Key与 Access Secret 不匹配	401
    public static final int ERROR_SIGNATURE_EXCEPTION = 10003;//10003	验证签名错误	验证签名错误	401
    public static final int ERROR_PARAMETER_EXCEPTION = 10004;//10004	参数错误	参数错误	400
    public static final int ERROR_CODE_ISNOTEXIST = 20001;//20001	处方编号不存在	没有找到匹配的处方编号	200

    public static final int ERROR_STATE_1_EXCEPTION = 11001;//该用户状态是删除
    public static final int ERROR_STATE_2_EXCEPTION = 11002;//该用户状态是停用.
    public static final int ERROR_STATE_3_EXCEPTION = 11003;//该用户状态是冻结
    public static final int ERROR_STATE_4_EXCEPTION = 11004;//审核中，请等待...
    public static final int ERROR_STATE_5_EXCEPTION = 11005;//审核未通过
}
