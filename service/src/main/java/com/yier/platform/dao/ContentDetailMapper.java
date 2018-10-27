package com.yier.platform.dao;

import com.yier.platform.common.model.ContentDetail;
import com.yier.platform.common.requestParam.ContentDetailRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 详细内容描述信息
 */
@Repository
public interface ContentDetailMapper {
    // 根据条件对象params查询列表
    List<ContentDetail> listContentDetail(ContentDetailRequest params);
    // 根据条件查询列表 总数
    int listContentDetailCount(ContentDetailRequest params);
    // 根据条件查询一个
    ContentDetail getContentDetailById(ContentDetailRequest params);

    int deleteByPrimaryKey(Long id);

    int insert(ContentDetail record);

    int insertSelective(ContentDetail record);

    ContentDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ContentDetail record);

    int updateByPrimaryKey(ContentDetail record);
}