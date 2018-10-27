package com.yier.platform.dao;

import com.yier.platform.common.model.AttentionUser;
import com.yier.platform.common.model.AttentionUserPo;
import com.yier.platform.common.requestParam.AttentionUserRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户关注其他用户记录
 */
@Repository
public interface AttentionUserMapper {
    //删除联合Id 信息
    int deleteAttentionUserByUnique(AttentionUserRequest params);
    //关联查询对应的信息列表
    List<AttentionUser> listAttentionUser(AttentionUserRequest params);
    //关联查询对应的信息列表 总数
    int listAttentionUserCount(AttentionUserRequest params);

    //关联查询对应的信息列表
    List<AttentionUserPo> listAttentionUserPo(AttentionUserRequest params);
    //关联查询对应的信息列表 总数
    int listAttentionUserPoCount(AttentionUserRequest params);

    int deleteByPrimaryKey(Long id);

    int insert(AttentionUser record);

    int insertSelective(AttentionUser record);

    AttentionUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AttentionUser record);

    int updateByPrimaryKey(AttentionUser record);
}