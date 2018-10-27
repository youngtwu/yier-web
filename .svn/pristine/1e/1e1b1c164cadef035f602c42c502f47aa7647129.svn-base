package com.yier.platform.service;

import com.yier.platform.common.model.Feedback;
import com.yier.platform.common.requestParam.FeedbackRequest;
import com.yier.platform.dao.FeedbackMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;

@ApiModel(value = "意见反馈 信息 service")
@Service
public class FeedbackService {
    private static final Logger logger = LoggerFactory.getLogger(DoctorService.class);
    @Autowired
    private FeedbackMapper daoFeedbackMapper;

    @ApiOperation(value = "一般根据ID条件查询问答列表")
    public List<Feedback> listAskAnswerFeedback(FeedbackRequest params) {
        return daoFeedbackMapper.listAskAnswerFeedback(params);
    }

    @ApiOperation(value = "一般根据ID条件查询问答列表 总数")
    public int listAskAnswerFeedbackCount(FeedbackRequest params) {
        return daoFeedbackMapper.listAskAnswerFeedbackCount(params);
    }

    @ApiOperation(value = "根据Id条件查询详情信息")
    public Feedback getFeedbackById(Long id) {
        return daoFeedbackMapper.selectByPrimaryKey(id);
    }

    @ApiOperation(value = "根据条件查询列表")
    public List<Feedback> listFeedback(FeedbackRequest params) {
        return daoFeedbackMapper.listFeedback(params);
    }

    @ApiOperation(value = "根据条件查询列表 总数")
    public int listFeedbackCount(FeedbackRequest params) {
        return daoFeedbackMapper.listFeedbackCount(params);
    }


    @ApiOperation(value = "新增意见反馈信息")
    @Transactional
    public int insertFeedback(Feedback record) {
        Assert.isTrue(record.getUserId() != null && record.getUserId().longValue() > 0, "请传递正确的用户ID");
        Assert.isTrue(record.getClientTypeId() != null && record.getClientTypeId().longValue() > 0, "请传递正确的客户端标识");
        Assert.isTrue(StringUtils.isNotBlank(record.getContent()), "请输入有效的反馈内容");
        Assert.isTrue(record.getParentId() != null && record.getParentId().longValue() >= 0, "请传递正确的节点");
        Assert.isTrue(record.getVisit() != null && record.getVisit().longValue() >= 0, "请输入反馈次数");
        if (record.getParentId().longValue() > 0) {
            Feedback parent = this.getFeedbackById(record.getParentId());
            record.setParentId(parent.getId());//父节点
            String parentIds = parent.getParentIds()+parent.getId()+",";
            record.setParentIds(parentIds);//父节点层次串
            parent.setVisit(parent.getVisit()+1);
            parent.setGmtModified(new Date());
            StringBuilder sb = new StringBuilder();
            sb.append("目前端口：").append(record.getClientTypeId()).append("用户Id：").append(record.getUserId()).append("对应父节点：").append(record.getParentId()).append("的一次回复");
            parent.setRemarks(sb.toString());
            logger.debug("目前父节点parent:{}",parent);
            daoFeedbackMapper.updateByPrimaryKeySelective(parent);
        }
        else {
            record.setParentIds("0,");//父节点层次串
        }
        logger.debug("目前节点record:{}",record);
        return daoFeedbackMapper.insertSelective(record);
    }
}
