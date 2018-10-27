package com.yier.platform.dao;

import com.yier.platform.common.model.MessageUser;
import com.yier.platform.common.model.MessagesPo;
import com.yier.platform.common.requestParam.MessagesRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageUserMapper {
    //关联查询对应的信息列表
    List<MessagesPo> listMessagesPo(MessagesRequest params);
    //关联查询对应的信息列表 总数
    int listMessagesPoCount(MessagesRequest params);
    //删除联合Id 信息
    int deleteMessageUserByUnique(MessagesRequest params);

    //关联查询对应的信息列表
    List<MessagesPo> listMessageUser(MessagesRequest params);
    //关联查询对应的信息列表 总数
    int listMessageUserCount(MessagesRequest params);



    int deleteByPrimaryKey(Long id);

    int insert(MessageUser record);

    int insertSelective(MessageUser record);

    MessageUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MessageUser record);

    int updateByPrimaryKey(MessageUser record);
}