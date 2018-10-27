package com.yier.platform.common.typeEnum;

/*
评价类型
 */
public enum EvaluationType {
    UNKNOWN("-1", "未知"),

    JIU_ZHEN("1", "就诊"),

    WEN_ZHEN("2", "问诊"),

    JINGY_YAN("3", "经验"),

    AI_GANG("4", "爱岗敬业"),

    TAI_DU("5", "态度"),

    QI_TA("0", "其他"),

    //QUAN_BU("", "全部"),
    ;
    private String type;
    private String desc;

    private EvaluationType(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}
