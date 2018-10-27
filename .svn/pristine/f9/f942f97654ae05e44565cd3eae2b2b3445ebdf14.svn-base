package com.yier.platform.service;

import com.yier.platform.TestBase;
import com.yier.platform.common.model.DoctorPracticePoint;
import com.yier.platform.common.requestParam.PracticeRequest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class PracticePointTimeServiceTest extends TestBase {
    private static final Logger logger = LoggerFactory.getLogger(PracticePointTimeServiceTest.class);
    @Autowired
    private PracticePointTimeService servicePracticePointTimeService;
    @Test
    public void insertPracticePoint() {
        DoctorPracticePoint record = new DoctorPracticePoint();
        record.setTypeId(2L);
        record.setDoctorId(1L);
        record.setHospitalId(1L);
        record.setCatalogId(4L);
        record.setDepartmentId(1L);
        record.setDefaultPoint("0");
        record.setRemarks("测试插入");
        boolean isFirstDefaultAdd = false;
        this.servicePracticePointTimeService.insertPracticePoint(record,isFirstDefaultAdd);
    }

    @Test
    public void getWorkTimeCaseList() {
        PracticeRequest params = new PracticeRequest();
        params.setPracticePointId(3L);
        params.setTypeId(2L);
        params.setDoctorId(46L);
        logger.info("通过条件查询获取医生或医师执业点时间情况[5_11:星期五 全天;5_10:星期五 上午;5_01:星期五 下午; 说明：left表示别人选中的列表，right表示自己选中的类别 ] 目前：{}",
                this.servicePracticePointTimeService.getWorkTimeCaseList(params));
    }

    @Test
    public void listPracticePointPo() {
        PracticeRequest params = new PracticeRequest();
        params.setTypeId(2L);
        params.setDoctorId(46L);
        logger.info("查询的总数是：{}返回的结果集是:{}",this.servicePracticePointTimeService.listPracticePointPoCount(params),this.servicePracticePointTimeService.listPracticePointPo(params));
    }
}