package com.yier.platform.dao;

import com.yier.platform.common.model.MessagesPerson;
import com.yier.platform.common.model.MessagesPersonPo;
import com.yier.platform.common.requestParam.MessagesRequest;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MessagesPersonMapper {
    //关联查询个人消息对应的信息列表
    List<MessagesPersonPo> listMessagesPersonsPo(MessagesRequest params);
    //关联查询个人消息对应的信息列表 总数
    int listMessagesPersonPoCount(MessagesRequest params);
    //批量更新2的未读信息为1
    int updateMessagesPersonReadBatch(MessagesRequest params);
    int deleteMessagesPersonBatch(MessagesPerson record);

    int deleteByPrimaryKey(Long id);

    int insert(MessagesPerson record);

    int insertSelective(MessagesPerson record);

    MessagesPerson selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MessagesPerson record);

    int updateByPrimaryKey(MessagesPerson record);
}