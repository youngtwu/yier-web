package com.yier.platform.common.typeEnum;

public enum AppType {//枚举类型定义常量方法,enum 不能使用 extends 关键字继承其他类
    patient(1,"患者端"),
    doctor(2,"医生端"),
    pharmacist(3,"药师端"),
    server(4,"服务端");
    private int port ;
    private String app;
    AppType(int port,String app){
        this.port = port;
        this.app = app;
    }
    public int getPort() {
        return port;
    }

    public String getApp() {
        return app;
    }
}
