package com.yier.platform.dao;

import com.yier.platform.common.model.Feedback;
import com.yier.platform.common.requestParam.FeedbackRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackMapper {
    // 一般根据ID条件查询问答列表
    List<Feedback> listAskAnswerFeedback(FeedbackRequest params);
    // 根据条件查询列表 总数
    int listAskAnswerFeedbackCount(FeedbackRequest params);
    // 根据条件查询列表
    List<Feedback> listFeedback(FeedbackRequest params);
    // 根据条件查询列表 总数
    int listFeedbackCount(FeedbackRequest params);



    int deleteByPrimaryKey(Long id);

    int insert(Feedback record);

    int insertSelective(Feedback record);

    Feedback selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Feedback record);

    int updateByPrimaryKey(Feedback record);
}