package com.yier.platform.controller.feign;

import cc.ccae.yry.service.drug.query.sdk.model.DrugFeedback;
import com.yier.platform.common.jsonResponse.CommonResponse;
import com.yier.platform.controller.DrugDiseaseController;
import com.yier.platform.service.feign.DrugDiseaseService;
import org.apache.commons.lang3.tuple.MutablePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component  //是一个泛化的概念，仅仅表示一个组件 (Bean)
public class TestTask {
    private static final Logger logger = LoggerFactory.getLogger(DrugDiseaseController.class);
    @Autowired
    private DrugDiseaseService drugDiseaseService;

    /**
     * 9小时执行一次
     */
//    @Scheduled(initialDelay = 1000, fixedRate = 5 * 60 * 1000) //初始化 延时5秒执行，每6小时执行一次
//    @Scheduled(initialDelay = 5000, fixedRate = 1 * 20 * 1000)
//    @Scheduled(initialDelay = 3000, fixedRate =  1000)
    public void taskDrugFeedback() {
        logger.info("测试用药反馈,每隔5分钟......................");
        DrugFeedback record = new DrugFeedback();
        record.setCountPreDay(1);
        record.setDescription("测试插入用药反馈1描述");
        record.setDiseaseId(1L);
        record.setEachHave(1);
        record.setEachHaveUnit("盒");
        record.setEndDate(new Date());
        record.setPatientId(1L);
        record.setScore(8);
        record.setStartDate(new Date());
        record.setDrugId(2759071310430207L);
        CommonResponse<MutablePair<String,DrugFeedback>> result= null;
        try {
            result = drugDiseaseService.createDrugFeedback(record);
        } catch (Exception e) {
            logger.info("插入用药反馈重现异常,检验是否再次插入会把单位'盒'也同时插入",e);
            DrugFeedback record2 = new DrugFeedback();
            record2.setCountPreDay(2);
            record2.setDescription("插入用药反馈重现异常,检验是否再次插入会把单位'盒'也同时插入,目前是颗");
            record2.setDiseaseId(10L);
            record2.setEachHave(2);
            record2.setEachHaveUnit("颗");
            record2.setEndDate(new Date());
            record2.setPatientId(10L);
            record2.setScore(2);
            record2.setStartDate(new Date());
            record2.setDrugId(2759071310430208L);
            try {
                result = drugDiseaseService.createDrugFeedback(record2);
            } catch (Exception e1) {
                logger.info("插入用药反馈在异常中有异常",e1);
            }
        } finally {
            logger.info("测试用药反馈,完成一个流程......................");
        }

    }

}
