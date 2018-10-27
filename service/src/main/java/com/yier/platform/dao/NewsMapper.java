package com.yier.platform.dao;

import com.yier.platform.common.model.News;
import com.yier.platform.common.requestParam.NewsRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 新闻映射服务类
 */
@Repository
public interface NewsMapper {

    // 根据条件对象params查询列表
    List<News> listNews(NewsRequest params);

    // 根据条件查询列表 总数
    int listNewsCount(NewsRequest params);

    int deleteByPrimaryKey(Long id);

    int insert(News record);

    int insertSelective(News record);

    News selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(News record);

    int updateByPrimaryKey(News record);
}