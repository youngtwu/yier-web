package com.yier.platform.common.util;

import java.math.BigDecimal;

public class LocationUtil {
    /**
     * 对double类型数据保留小数点后多少位
     *  高德地图转码返回的就是 小数点后6位，为了统一封装一下
     * @param digit 位数
     * @param in 输入
     * @return 保留小数位后的数
     */
    static double dataDigit(int digit,double in){
        return new   BigDecimal(in).setScale(6,   BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 百度地图坐标转高德地图 GCJ-02(火星坐标) 和 BD-09 （百度坐标）
     * @param bd_lon 经度（值较大）
     * @param bd_lat 纬度（值较小）
     */

    public static String baiduToGaode(double bd_lon, double bd_lat)
    {
        double x_pi = 3.14159265358979324 * 3000.0 / 180.0;
        double x = bd_lon - 0.0065, y = bd_lat - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);
        System.out.println("百度-->高德 经度："+dataDigit(6,z * Math.cos(theta)));
        System.out.println("百度-->高德 维度："+dataDigit(6,z * Math.sin(theta)));
        return z * Math.cos(theta)+","+z * Math.sin(theta);
    }
    public static double[] baiduToGaodeArray(double bd_lon, double bd_lat)
    {
        double x_pi = 3.14159265358979324 * 3000.0 / 180.0;
        double x = bd_lon - 0.0065, y = bd_lat - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);
        double[] gps = {dataDigit(6,z * Math.cos(theta)),dataDigit(6,z * Math.sin(theta))};
//        System.out.println("百度-->高德 经度："+dataDigit(6,z * Math.cos(theta)));
//        System.out.println("百度-->高德 维度："+dataDigit(6,z * Math.sin(theta)));
        return gps;
    }
    public static double[] gaodeToBaiduArray(double bd_lon, double bd_lat)
    {
        double x_pi = 3.14159265358979324 * 3000.0 / 180.0;
        double x = bd_lon, y = bd_lat;
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * x_pi);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * x_pi);
        double[] gps = {dataDigit(6,(z * Math.cos(theta) + 0.0065)),dataDigit(6,(z * Math.sin(theta) + 0.006))};
//        System.out.println("高德-->百度 经度："+dataDigit(6,(z * Math.cos(theta) + 0.0065)));
//        System.out.println("高德-->百度 维度："+dataDigit(6,(z * Math.sin(theta) + 0.006)));
        return gps;
    }



    /**
     * 高德地图坐标转百度地图

     * @param bd_lon 经度（值较大）
     * @param bd_lat 纬度（值较小）
     */

    public static String  gaodeToBaidu(double bd_lon, double bd_lat)
    {
        double x_pi = 3.14159265358979324 * 3000.0 / 180.0;
        double x = bd_lon, y = bd_lat;
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * x_pi);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * x_pi);
        System.out.println("高德-->百度 经度："+dataDigit(6,(z * Math.cos(theta) + 0.0065)));
        System.out.println("高德-->百度 维度："+dataDigit(6,(z * Math.sin(theta) + 0.006)));
        return (z * Math.cos(theta) + 0.0065) + "," + (z * Math.sin(theta) + 0.006);
    }
/*    public static void main(String[] args) {
//        System.out.println(gaodeToBaiduArray(106.592239D, 29.568807D));
//        System.out.println(baiduToGaodeArray(106.59876003477D, 29.57477255411D));
//        System.out.println(baiduToGaodeArray(31.1952109039D, 121.2707931908D));
        System.out.println("上海第一人民医院");
        System.out.println(baiduToGaodeArray(121.495991D, 31.259099D)[0]);
        System.out.println(gaodeToBaiduArray(121.489393D, 31.253389D)[1]);
        //121.495991,31.259099 --> 121.489393,31.253389
    }*/

}
