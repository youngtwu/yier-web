package com.yier.platform.common.costant;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public final class ConstantAll {
    private ConstantAll(){}
    /** 图片缓存功能处理  */
    public static final String REDIS_IMAGE_UPLOAD_KEY = "FUN_I_U_K_";//+图片Id
    /** 医生预约锁定半小时支付功能处理  */
    public static final String REDIS_DOCTOR_APPOINT_LOCK_ORDER = "FUN_DALO_";// +患者ID + 订单ID :预约信息,订单Id   //+出诊日期预约ID    //+医生ID+"_L_P_"+患者ID
    public static final String REDIS_DOCTOR_APPOINT_LOCK_ORDER_PART_ORDER = "_L_ID_";
    /** 缓存上门就诊订单信息  */
    public static final String REDIS_APPOINT_ORDER_ID = "FUN_A_OI_";// + 订单ID  订单详情
    /** 医生预约开始服务功能处理  */
    public static final String REDIS_DOCTOR_APPOINT_START_SERVICE_WAIT_TIME = "FUN_D_A_SS_WT_";// + 订单ID  订单详情


    /** 医生锁定24小时聊天设定  */
    public static final String REDIS_DOCTOR_CHAT_SET = "FUN_D_C_S_";//+医生ID
    /** 患者刚刚下定新预约订单  */
//    public static final String REDIS_APPOINT_ORDER_PATIENT = "FUN_A_O_P_";//+患者ID  可以设定有效期为1个小时考虑期
    /** 有处方新订单  */
    public static final String REDIS_PRESCRIPTION_ORDER = "FUN_P_O_";//+处方ID  放置整个处方信息  可以设定有效期为2个小时
    /** 处方审核超时计时器  */
    public static final String REDIS_PRESCRIPTION_CHECK_A_ORDER_ID= "FUN_P_C_A_OID_";//+处方ID  放置整个处方信息  可以设定有效期为2个小时 跟VALUE_TIME_OUT_CHECK_A呼应
    public static final String REDIS_PRESCRIPTION_CHECK_B_ORDER_ID= "FUN_P_C_B_OID_";//+处方ID  放置整个处方信息  可以设定有效期为2个小时 跟VALUE_TIME_OUT_CHECK_B呼应
    public static final String REDIS_PRESCRIPTION_CHECK_C_ORDER_ID= "FUN_P_C_C_OID_";//+处方ID  放置整个处方信息  可以设定有效期为2个小时 跟VALUE_TIME_OUT_CHECK_C呼应
    public static final String REDIS_PRESCRIPTION_CHECK_D_ORDER_ID= "FUN_P_C_D_OID_";//+处方ID  放置整个处方信息  可以设定有效期为2个小时 跟VALUE_TIME_OUT_CHECK_D呼应
    public static final String REDIS_PRESCRIPTION_CHECK_E_ORDER_ID= "FUN_P_C_E_OID_";//+处方ID  放置整个处方信息  可以设定有效期为2个小时 跟VALUE_TIME_OUT_CHECK_E呼应


    /** 送药取药  */
    public static final String REDIS_SEND_TAKE_ORDER_PAYMENT_NEW_ID= "F_TAKEORDER_NEW_ID_";//+取药ID  放置整个送药或取药时间 跟 VALUE_TIME_OUT_SEND_TAKE_ORDER 呼应
    public static final String REDIS_SEND_ORDER_CHECK_A= "F_TAKEORDER_CHECK_A_";//+取药ID  放置整个送药或取药 跟 VALUE_TIME_OUT_SEND_ORDER 呼应





    public static final Long LOG_MEDICAL_ORDER = 1L;// "上门就诊";
    public static final String LOG_MEDICAL_ORDER_SUB_PAYMENT_LOCK = "患者正在付款锁定医生预约日期";
    public static final String LOG_MEDICAL_ORDER_SUB_START_SERVICE = "医生开始上门服务";
    public static final Long LOG_MEDICAL_PRES = 2L;//"处方审核";
    public static final String LOG_MEDICAL_PRES_SUB_CURRENT_PRESCRIPTION = "考虑处方审核";
    public static final String LOG_MEDICAL_PRES_SUB_TIME_OUT_CHECK_A = "A环节阶段审核";
    public static final String LOG_MEDICAL_PRES_SUB_TIME_OUT_CHECK_B = "B环节阶段审核";
    public static final String LOG_MEDICAL_PRES_SUB_TIME_OUT_CHECK_C = "C环节阶段审核";
    public static final String LOG_MEDICAL_PRES_SUB_TIME_OUT_CHECK_D = "D环节阶段审核";
    public static final String LOG_MEDICAL_PRES_SUB_TIME_OUT_CHECK_E = "E环节阶段审核";
    public static final String LOG_REMARK_SET = "设定操作 ";
    public static final String LOG_REMARK_UPDATE = "更新操作 ";
    public static final String LOG_REMARK_DELETE = "删除操作 ";
    public static final Long  LOG_TAKE_SEND_PRES = 4L;//"取药 送药 阶段";
    public static final String LOG_TAKE_SEND_PRES_ORDER = "取药送药下单流程限定";
    public static final String LOG_SEND_PRES_ORDER_TIME_OUT_CHECK_A = "送药药师A环节没接单";


    /** 药师 抢单3分钟锁定  */
    public static final String REDIS_PHARMACIST_GRAB_ORDER = "FUN_P_G_O_";//+药师ID+处方ID 药师关键信息（惩罚机制，抢单时间）
    public static final String REDIS_PHARMACIST_GRAB_ORDER_PART_ORDERID  = "_P_ID_";
    /** 平台药师 2分钟再转移问题  */
    public static final String REDIS_PHARMACIST_PLAT = "FUN_P_P_";//+平台药师ID 药师关键信息（惩罚机制，抢单时间）

    /** 缓存所有的用户角色  */
    public static final String REDIS_ALL_USER_ROLE_PO = "FUN_A_U_R_P_";//+所有的用户角色表
    /** 缓存所有的推送药师  */
    public static final String REDIS_ALL_CHECK_PHARMACIST = "FUN_A_C_P_";//+所有的药师列表
    /** 缓存所有的平台药师  */
    public static final String REDIS_ALL_PLAT_PHARMACIST = "FUN_A_P_P_";//+所有的平台药师列表,要有目前分配索引index
    /** 缓存所有的药店药房  */
    public static final String REDIS_ALL_PHARMACY = "F_A_PHARMACY_";//+所有的药店药房列表

    /** 特别是医生看到的处方订单状态  初始是"" 不限时排除 is not ""，患者总是有值，不限 is not ""或null 同样适用 */
    public static final String ORDER_STATUS_ALL = "";//  不限
    /** 上门就诊订单状态 患者预约 待支付  */
    public static final String ORDER_STATUS_APPPOINT = "1";//只有患者可见
    /** 上门就诊订单状态 患者预约付款  预约待定*/
    public static final String ORDER_STATUS_APPPOINT_PAYMENT = "2";
    /** 上门就诊订单状态 医生确认  预约成功*/
    public static final String ORDER_STATUS_APPPOINT_CONFIIRM = "3";
    /** 上门就诊订单状态 医生确认 上门确认  到达地点*/
    public static final String ORDER_STATUS_START_SERVICE = "4";
    /** 上门就诊订单状态 患者确认  开始服务*/
    public static final String ORDER_STATUS_START_SERVICE_CONFIRM = "5";
    /** 上门就诊订单状态 医生刚出处方 处方审核中  处理中  */
    public static final String ORDER_STATUS_PRESCRIPTION_CHECK = "6";
    /** 上门就诊订单状态 审核通过医生处方 或者没有处方 完结此上门就诊 处方通过  完成  */
    public static final String ORDER_STATUS_OK = "7";
    /** 上门就诊订单状态 审核不通过医生处方    患者预约过(超)期  超时未在*/
    public static final String ORDER_STATUS_APPPOINT_PATIENT_PAST_DUE = "U";
    /** 上门就诊订单状态 审核不通过医生处方   医生预约过期  超时未到*/
    public static final String ORDER_STATUS_APPPOINT_DOCTOR_PAST_DUE = "V";
    /** 上门就诊订单状态 审核不通过医生处方  处方未通过 */
    public static final String ORDER_STATUS_PRESCRIPTION_CHECK_FAIL = "W";//只有医生可见
    /** 上门就诊订单状态 订单 患者取消  */
    public static final String ORDER_STATUS_PATIENT_CANCEL = "X";
    /** 上门就诊订单状态 订单患者 取消支付  */
    public static final String ORDER_STATUS_PATIENT_PAYMENT_CANCEL = "S";
    /** 上门就诊订单状态 医生取消  */
    public static final String ORDER_STATUS_DOCTOR_CANCEL = "Y";
    /** 上门就诊订单状态 支付超期  */
    public static final String ORDER_STATUS_PATIENT_PAST_DUE = "Z";//只有患者可见

    /** 药师处方订单状态    初始是"" 一旦分配药师总有值 不限是 null*/
    public static final String MEDICAL_PRESCRIPTION_STATUS_INIT = "";//初始状态
    public static final String MEDICAL_PRESCRIPTION_STATUS_VIEW = "A";//  审核中 未审核  (在查看 思考中)
    public static final String MEDICAL_PRESCRIPTION_STATUS_REVIEW  = "B";//未复审 复审中 未通过的提交 (重新查看思考中)
    public static final String MEDICAL_PRESCRIPTION_STATUS_ADVICE = "C";//已审 [给出通过或不通过意见]
    public static final String MEDICAL_PRESCRIPTION_STATUS_OK = "0";//完成

    /** 患者看到的处方订单状态  初始是"" 不限时排除 is not ""*/
    public static final String MEDICAL_PRESCRIPTION_STATUS_PATIENT_ALL = "";//  不限
    public static final String MEDICAL_PRESCRIPTION_STATUS_PATIENT_NO_DRUG = "0";//  无药品
    public static final String MEDICAL_PRESCRIPTION_STATUS_PATIENT_NO_ORDER = "1";//  未支付
    public static final String MEDICAL_PRESCRIPTION_STATUS_PATIENT_NEW_ORDER = "2";//  待支付
    public static final String MEDICAL_PRESCRIPTION_STATUS_PATIENT_PAYMENT_OK = "3";//  已支付
    public static final String MEDICAL_PRESCRIPTION_STATUS_PATIENT_GET_DRUG = "4";//  已取药
    public static final String MEDICAL_PRESCRIPTION_STATUS_PATIENT_DRUG_PAST_DUE = "U";//超期未取药
    public static final String MEDICAL_PRESCRIPTION_STATUS_PATIENT_PAYMENT_PAST_DUE = "Z";//超期未支付

    //处方阶段
    public static final int TAKE_SEND_PRESCRIPTION_LOCK_NEW_ORDER = 1;//新建取药或送药订单
    public static final int TAKE_SEND_PRESCRIPTION_LOCK_CANCEL_ORDER = 2;//取消取药或送药订单
    public static final int TAKE_SEND_PRESCRIPTION_LOCK_PAYMENT_ORDER = 3;//支付成功 取药或送药订单
    public static final int TAKE_SEND_PRESCRIPTION_LOCK_TASK_TIME_OUT = 4;//超时
    public static final int TAKE_SEND_PRESCRIPTION_GET_DRUG = 5;//已经取药

    /**  患者预约取药 不限传递的是 ""*/
    public static final String TAKE_ORDER_STATUS_APPPOINT = "1";//待支付
    public static final String TAKE_ORDER_STATUS_APPPOINT_OK = "2";//预约成功
    public static final String TAKE_ORDER_STATUS_OK  = "3";//完成
    public static final String TAKE_ORDER_STATUS_APPPOINT_PATIENT_PAST_DUE = "U";//预约过期/预约超期
    public static final String TAKE_ORDER_STATUS_PATIENT_PAST_DUE = "Z";//支付超期/过期
    public static final String TAKE_ORDER_STATUS_PATIENT_PAYMENT_CANCEL = "S";//取消支付
    /**  患者预约送药 */
    public static final String SEND_ORDER_STATUS_APPPOINT = "1";//待支付
    public static final String SEND_ORDER_STATUS_PAYMENT = "2";//预约成功
    public static final String SEND_ORDER_STATUS_SENDING  = "3";//派送中
    public static final String SEND_ORDER_STATUS_SEND_OK  = "4";//完成派送
    public static final String SEND_ORDER_STATUS_OK  = "5";//完成
    public static final String SEND_ORDER_STATUS_APPPOINT_PATIENT_PAST_DUE = "U";//预约过期/预约超期
    public static final String SEND_ORDER_STATUS_PATIENT_PAST_DUE = "Z";//支付超期/过期
    public static final String SEND_ORDER_STATUS_PATIENT_PAYMENT_CANCEL = "S";//取消支付


    /** 药师接受预约送药 */
    public static final String ACCEPT_ORDER_STATUS_PAYMENT = "2";//未接收
    public static final String ACCEPT_ORDER_STATUS_READY  = "3";//待送
    public static final String ACCEPT_ORDER_STATUS_SENDING  = "4";//派送中
    public static final String ACCEPT_ORDER_STATUS_SEND_OK  = "5";//已送
    public static final String ACCEPT_ORDER_STATUS_OK  = "6";//完成



    public static final String ORDER_END_TYPE_PAST_DUE = "支付超期";//Z
    public static final String ORDER_END_TYPE_PATIENT_PAYMENT_CANCEL = "患者取消未支付订单";//S
    public static final String ORDER_END_TYPE_PATIENT_CACEL_NO_COFNIRM = "患者取消未确认预约";//X
    public static final String ORDER_END_TYPE_PATIENT_CACEL_MORE_48_HOUR = "患者取消>48小时";//X
    public static final String ORDER_END_TYPE_PATIENT_CACEL_24_48_HOUR = "患者取消24-48";//X
    public static final String ORDER_END_TYPE_PATIENT_CACEL_LESS_24 = "患者取消<24";//X
    public static final String ORDER_END_TYPE_DOCTOR_CACEL_MORE_48_HOUR = "医生取消>48小时";//Y
    public static final String ORDER_END_TYPE_PATIENT_APPPOINT_PAST_DUE = "患者预约过期";//V
    public static final String ORDER_END_TYPE_DOCTOR_APPPOINT_PAST_DUE = "医生预约过期";//V
    public static final String ORDER_END_TYPE_OK_NO_PRESCRIPTION = "没有处方生成终止";//7
    public static final String ORDER_END_TYPE_OK_TIME_OUT_PLAT_1 = "普通药师审核通过";//7
    public static final String ORDER_END_TYPE_OK_TIME_OUT_PLAT_2 = "平台药师审核通过";//7
    public static final String ORDER_END_TYPE_OK_TIME_OUT_C = "审核C环节超时通过";//7
    public static final String ORDER_END_TYPE_OK_TIME_OUT_E = "审核E环节超时通过";//7



    public static final String DOCTOR_SIGNATURE = "医生担责";//
    public static final String STRING_EMPTY = "";//


    /** 应用端口类型 */
    public static final long TYPE_ID_PATIENT = 1L;//患者
    public static final long TYPE_ID_DOCTOR = 2L;//医生
    public static final long TYPE_ID_PHARMACIST = 3L;//药师
    public static final long TYPE_ID_USER = 4L;//亿尔APP后台管理端
    public static final long TYPE_ID_NURSE = 5L;//护士
    public static final long TYPE_ID_DRUG = 6L;//医药信息后台管理端


    /** 流水账类型  */
    public static final String USER_TYPE_PATIENT = "patient";//患者
    public static final String USER_TYPE_DOCTOR = "doctor";//医生
    public static final String USER_TYPE_PHARMACIST = "pharmacist";//药师
    public static final String USER_TYPE_USER = "user";//亿尔APP后台管理端
    public static final String USER_TYPE_NURSE = "nurse";//护士
    public static final String USER_TYPE_DRUG = "drug";//医药信息后台管理端

    /** 流水账类型  */
    public static final String SERIAL_TYPE_OUTFLOW = "OUTFLOW";//流出
    public static final String SERIAL_TYPE_INFLOW = "INFLOW";//流入
    public static final String SERIAL_TYPE_FROZEN = "FROZEN";//冻结
    public static final String SERIAL_TYPE_THAWY = "THAWY";//解冻

    /** 状态类型  */
    public static final String STATUS_0 = "0";
    public static final String STATUS_1 = "1";
    public static final String STATUS_2 = "2";
    public static final String STATUS_ADJUST = "adjust";//调整记录

    public static final String STATUS_OPEN = "0";
    public static final String STATUS_CLOSE = "1";


    /** 药师审方性质 平台类型 0 普通 1 系统平台  */
    public static final int PLAT_TYPE_0 = 0;
    public static final int PLAT_TYPE_1 = 1;
    /** 药师审方通过状态  */
    public static final String CHECK_VIEW_STATUS_OK = "通过";
    public static final String CHECK_VIEW_STATUS_OK_TIMEOUT = "超时通过";
    public static final String CHECK_VIEW_STATUS_KO = "不通过";

    /** 常量值  */
    public static final TimeUnit UNIT_CONSIDER = TimeUnit.HOURS;//考虑的单元,1小时
    public static final long VALUE_MICROSECOND_1000 = 1000L; //单位是微秒
    public static final long VALUE_PAYMENT_LOCK = 120L; //单位是秒
    public static final long VALUE_CHECK_GRAP = 180L; //单位是秒
    public static final long VALUE_CHECK_PLAT = 120L; //单位是秒
    public static final long VALUE_CHECK_LIMIT_TIME= 1800L; //单位是秒
    public static final long VALUE_CONSIDER_DAY_TIME= 1L;// 5L; //考虑时间单位是天
    public static final long VALUE_TIME_OUT_CHECK_A= 300L; //单位是秒
    public static final long VALUE_TIME_OUT_CHECK_B= 300L; //单位是秒
    public static final long VALUE_TIME_OUT_CHECK_C= 300L; //单位是秒
    public static final long VALUE_TIME_OUT_CHECK_D= 300L; //单位是秒
    public static final long VALUE_TIME_OUT_CHECK_E= 300L; //单位是秒
    public static final long VALUE_TIME_OUT_SEND_TAKE_ORDER= 600L; //送药取药 单位是秒 10分钟
    public static final long VALUE_TIME_OUT_SEND_ORDER= 600L; //送药接单催促 单位是秒 10分钟


    public static final long VALUE_TIME_48_HOUR= 60*60*48L; //单位是秒,48小时
    public static final long VALUE_TIME_24_HOUR= 60*60*24L; //单位是秒,48小时
    public static final long VALUE_TIME_1_HOUR= 60*60*1L; //单位是秒,1小时

    public static final long VALUE_MEDICAL_ORDER_START_SERVICE_WAIT_TIME = 3600L; //医生开始服务等待时间 单位是秒

    public static final int AM_BEGIN = 8; //上午,8小时
    public static final int AM_END = 12; //上午,12小时
    public static final int FM_BEGIN = 13; //下午,13小时
    public static final int FM_END = 18; //下午,12小时
}
