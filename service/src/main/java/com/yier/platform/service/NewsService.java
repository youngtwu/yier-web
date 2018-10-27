package com.yier.platform.service;

import com.yier.platform.common.model.AuditInfo;
import com.yier.platform.common.model.News;
import com.yier.platform.common.model.NewsPo;
import com.yier.platform.common.model.User;
import com.yier.platform.common.requestParam.NewsRequest;
import com.yier.platform.dao.NewsMapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 消息新闻信息 service
 */
@Service
public class NewsService {
    @Autowired
    private NewsMapper daoNewsMapper;
    @Autowired
    private UserService serviceUserService;
    @Autowired
    private AuditInfoService auditInfoService;

    /**
     * 通过条件可分页查询患者
     *
     * @param params
     * @return
     */
    public List<News> listNews(NewsRequest params) {
        return daoNewsMapper.listNews(params);
    }

    /**
     * 查询对应的条目数
     *
     * @param params
     * @return
     */
    public int listNewsCount(NewsRequest params) {
        return daoNewsMapper.listNewsCount(params);
    }

    public News selectByPrimaryKey(Long id) {
        return daoNewsMapper.selectByPrimaryKey(id);
    }

    @ApiOperation(value = "新增新闻资讯")
    public int insertSelective(News news) {
        return this.daoNewsMapper.insertSelective(news);
    }

    @ApiOperation(value = "新增新闻资讯")
    public News insertNews(News news, Long userId) {
        news.setRemarks("新增新闻资讯");
        this.insertSelective(news);//新增新闻资讯

        AuditInfo auditInfo = new AuditInfo();
        auditInfo.setAuditType(5);
        auditInfo.setTableId(news.getId());
        auditInfo.setOperate(news.getStatus());
        auditInfo.setRemarks(news.getRemarks());
        User user = this.serviceUserService.selectByPrimaryKey(userId);//根据userid获取当前操作人的信息
        if (user != null) {
            auditInfo.setUserId(user.getId());
            auditInfo.setUserName(user.getTrueName());
        }
        this.auditInfoService.insertAuditInfo(auditInfo);//新增审核信息表
        return news;
    }

    @ApiOperation(value = "更新新闻资讯")
    public News updateNews(News news, Long userId) {
        news.setGmtModified(new Date());
        news.setRemarks("更新新闻资讯");
        this.updateByPrimaryKeySelective(news);

        AuditInfo auditInfo = new AuditInfo();
        auditInfo.setAuditType(5);
        auditInfo.setTableId(news.getId());
        auditInfo.setOperate(news.getStatus());
        auditInfo.setRemarks(news.getRemarks());
        User user = this.serviceUserService.selectByPrimaryKey(userId);//根据userid获取当前操作人的信息
        if (user != null) {
            auditInfo.setUserId(user.getId());
            auditInfo.setUserName(user.getTrueName());
        }
        this.auditInfoService.insertAuditInfo(auditInfo);//新增审核信息表
        return news;
    }

    @ApiOperation(value = "更新新闻资讯")
    public int updateByPrimaryKeySelective(News news) {
        return this.daoNewsMapper.updateByPrimaryKeySelective(news);
    }

    @ApiOperation(value = "删除新闻资讯")
    public int deleteByPrimaryKey(NewsRequest params) {
        return this.daoNewsMapper.deleteByPrimaryKey(params.getNewId());
    }

    @ApiOperation(value = "启用/停用新闻资讯")
    public int enableOrDisableNews(News news) {
        news.setGmtModified(new Date());
        if (news.getStatus() != null && "0".equals(news.getStatus())) {
            news.setRemarks("启用公告消息");
        } else {
            news.setRemarks("停用公告消息");
        }
        return this.daoNewsMapper.updateByPrimaryKeySelective(news);
    }

    @ApiOperation(value = "根据id查询新闻资讯")
    public NewsPo getNewsById(NewsRequest params) {
        News news = this.selectByPrimaryKey(params.getNewId());
        NewsPo newsPo = null;
        if(news != null){
            newsPo = new NewsPo(news);
        } else{
            newsPo = new NewsPo();
        }
        return newsPo;
    }
}
