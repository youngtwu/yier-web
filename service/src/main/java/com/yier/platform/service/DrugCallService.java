package com.yier.platform.service;

import com.yier.platform.common.model.DrugCall;
import com.yier.platform.common.model.DrugCallTask;
import com.yier.platform.common.requestParam.DrugCallRequest;
import com.yier.platform.dao.DrugCallMapper;
import com.yier.platform.dao.DrugCallTaskMapper;
import io.swagger.annotations.ApiModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 药品提醒 service
 */
@ApiModel(value = "药品提醒 service")
@Service
public class DrugCallService {
    private static final Logger logger = LoggerFactory.getLogger(DrugCallService.class);
    @Autowired
    private DrugCallMapper daoDrugCallMapper;
    @Autowired
    private DrugCallTaskMapper daoDrugCallTaskMapper;

    // 根据条件查询列表
    public List<DrugCall> listDrugCall(DrugCallRequest params) {
        return this.daoDrugCallMapper.listDrugCall(params);
    }

    // 根据条件查询列表 总数
    public int listDrugCallCount(DrugCallRequest params) {
        return this.daoDrugCallMapper.listDrugCallCount(params);
    }

    // 根据条件查询列表
    public List<DrugCallTask> listDrugCallTask(DrugCallRequest params) {
        return this.daoDrugCallTaskMapper.listDrugCallTask(params);
    }

    // 根据条件查询列表 总数
    public int listDrugCallTaskCount(DrugCallRequest params) {
        return this.daoDrugCallTaskMapper.listDrugCallTaskCount(params);
    }

}
