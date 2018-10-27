package com.yier.platform.service;

import com.yier.platform.common.model.ContentDetail;
import com.yier.platform.common.requestParam.ContentDetailRequest;
import com.yier.platform.dao.ContentDetailMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 新闻消息具体内容信息 service
 */
@ApiModel(value = "新闻消息具体内容信息 service")
@Service
public class ContentDetailService {
    private static final Logger logger = LoggerFactory.getLogger(ContentDetailService.class);
    @Autowired
    private ContentDetailMapper daoContentDetailMapper;
    @ApiOperation(value = "根据条件对象params查询列表")
    public List<ContentDetail> listContentDetail(ContentDetailRequest params){
        return this.daoContentDetailMapper.listContentDetail(params);
    }
    @ApiOperation(value = "根据条件查询列表 总数")
    public int listContentDetailCount(ContentDetailRequest params){
      return this.daoContentDetailMapper.listContentDetailCount(params);
    }
    @ApiOperation(value = "根据条件查询一个")
    public ContentDetail getContentDetailById(ContentDetailRequest params){
        return this.daoContentDetailMapper.getContentDetailById(params);
    }
}
