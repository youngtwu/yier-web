package com.yier.platform.common.util;

import java.util.Date;
import java.util.UUID;

public class OrderIdUtils {
    public static String genOrderIdWithUser(String userId){//业务上同个用户操作，保证不重复
        //时间戳是13位到毫秒，纳秒10-15位变化明显
        return System.currentTimeMillis() + userId + (System.nanoTime() + "").substring(10, 15);//18-23位
    }
    public static String genOrderIdWithUUID(String sign){//技术上保证多开电脑也不会出现重复
        //int最长是11位加正负标志是12位
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if (hashCodeV < 0) {//有可能是负数
            hashCodeV = -hashCodeV;
        }
        //时间戳是13位到毫秒
        return System.currentTimeMillis() + sign + hashCodeV; //24位
    }
    public static String genOrderIdWithDateUserId(String userId){//技术上保证多开电脑也不会出现重复
        return Utils.formatDate(new Date(), "yyyyMMddHHmmss") + "" + userId;//16-21
    }

}
