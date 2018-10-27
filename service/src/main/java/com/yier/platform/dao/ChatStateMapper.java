package com.yier.platform.dao;

import com.yier.platform.common.model.ChatState;
import com.yier.platform.common.requestParam.ChatStateRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 聊天空间记录
 */
@Repository
public interface ChatStateMapper {
    // 根据条件查询列表
    List<ChatState> listChatState(ChatStateRequest params);
    // 根据条件查询列表 总数
    int listChatStateCount(ChatStateRequest params);
    //批量更新
    int updateChatStateBatch(ChatStateRequest params);

    int deleteByPrimaryKey(Long id);

    int insert(ChatState record);

    int insertSelective(ChatState record);

    ChatState selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ChatState record);

    int updateByPrimaryKey(ChatState record);
}