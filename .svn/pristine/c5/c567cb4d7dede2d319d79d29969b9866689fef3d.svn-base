package com.yier.platform.service;

import com.yier.platform.common.model.ApiAround;
import com.yier.platform.dao.ApiAroundMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 应用接口执行记录表
 *
 */
@Service
public class ApiAroundService {
    private static final Logger logger = LoggerFactory.getLogger(ApiAroundService.class);
    @Autowired
    private ApiAroundMapper daoApiAroundMapper;

    public int insertSelective(ApiAround record){
        return this.daoApiAroundMapper.insertSelective(record);
    }

}
