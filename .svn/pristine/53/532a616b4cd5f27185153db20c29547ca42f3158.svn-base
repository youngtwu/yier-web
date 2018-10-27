package com.yier.platform.service;

import com.yier.platform.common.model.RedisLog;
import com.yier.platform.dao.RedisLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RedisLogService {
    @Autowired
    private RedisLogMapper daoRedisLogMapper;


    public int deleteByPrimaryKey(Long id){
        return daoRedisLogMapper.deleteByPrimaryKey(id);
    }

    public int insertSelective(RedisLog record){
        return daoRedisLogMapper.insertSelective(record);
    }

    public RedisLog selectByPrimaryKey(Long id){
        return daoRedisLogMapper.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(RedisLog record){
        return daoRedisLogMapper.updateByPrimaryKeySelective(record);
    }


}
