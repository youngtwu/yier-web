package com.yier.platform.service.feign;

public interface PrescriptionChainConfig {
    //处方状态
    //新开处方
    int STATUS_NEW = 1;
    //未通过审核处方
    int STATUS_REJECT = 2;
    //已更新处方
    int STATUS_UPDATE = 3;
    //待支付处方
    int STATUS_WAIT_PAY = 4;
    //待取药处方
    int STATUS_PAID = 5;
    //已取药处方
    int STATUS_USED = 6;
    //已逾期处方
    int STATUS_TIMEOUT = 7;
}
