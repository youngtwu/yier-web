package com.yier.platform.service;

import com.yier.platform.common.model.AttentionUser;
import com.yier.platform.common.model.AttentionUserPo;
import com.yier.platform.common.requestParam.AttentionUserRequest;
import com.yier.platform.dao.AttentionUserMapper;
import io.swagger.annotations.ApiModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户关注其他用户记录 service
 */
@ApiModel(value = " 用户关注其他用户记录 service")
@Service
public class AttentionUserService {
    private static final Logger logger = LoggerFactory.getLogger(AttentionUserService.class);
    @Autowired
    private AttentionUserMapper daoAttentionUserMapper;

    // 根据条件查询列表  详细信息
    public List<AttentionUserPo> listAttentionUserPo(AttentionUserRequest params) {
        return this.daoAttentionUserMapper.listAttentionUserPo(params);
    }

    // 根据条件查询列表 总数
    public int listAttentionUserPoCount(AttentionUserRequest params) {
        return this.daoAttentionUserMapper.listAttentionUserPoCount(params);
    }

    public String attention(AttentionUserRequest params) {
        if (this.daoAttentionUserMapper.listAttentionUserCount(params) > 0) {
            this.daoAttentionUserMapper.deleteAttentionUserByUnique(params);//删除联合Id 信息
            return "cancel";
        } else {
            AttentionUser record = new AttentionUser();
            record.setTypeId(params.getTypeId());
            record.setUserId(params.getUserId());
            record.setAttentionTypeId(params.getAttentionTypeId());
            record.setAttentionUserId(params.getAttentionUserId());
            this.daoAttentionUserMapper.insertSelective(record);
            return "attention";
        }
    }
}
