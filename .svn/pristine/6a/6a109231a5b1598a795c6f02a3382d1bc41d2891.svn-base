package com.yier.platform.service;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Lists;
import com.yier.platform.TestBase;
import com.yier.platform.common.jsonResponse.CommonResponse;
import com.yier.platform.common.jsonResponse.ListResponse;
import com.yier.platform.common.model.Messages;
import com.yier.platform.common.model.News;
import com.yier.platform.common.po.Image;
import com.yier.platform.common.requestParam.NewsRequest;
import com.yier.platform.common.util.JsonUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@ApiModel(value = "新闻资讯相关的请求接口")
public class NewsServiceTest extends TestBase {
    private static final Logger logger = LoggerFactory.getLogger(NewsServiceTest.class);

    @Autowired
    private NewsService newsService;

    @ApiOperation(value = "查询新闻资讯分页列表")
    @Test
    public void getNewsList() {
        NewsRequest params = new NewsRequest();
        params.doWithSortOrStart();
        logger.info("查询新闻资讯分页列表, 请求参数：{}", params);
        List<News> newsList = newsService.listNews(params);
        for (News item : newsList) {
            String images = item.getImages();//json存储的图片信息，转换成列表对象
            List<Image> imageLists = JSONObject.parseArray(images, Image.class);
            if (imageLists == null) {
                item.setImagesList(Lists.newArrayList());
            } else {
                item.setImagesList(imageLists);
            }
            System.out.println(item.toJsonString());
            System.out.println(item.toJsonString().replace("\\", ""));
        }
        int count = newsService.listNewsCount(params);
        ListResponse<News> res = new ListResponse<News>();
        res.setData(newsList);
        res.setTotal(count);
        logger.info("查询新闻资讯分页列表, 测试结果：{}", newsList);
    }

    @ApiOperation(value = "根据id查询新闻资讯")
    @Test
    public void getNewsById() {
        NewsRequest params = new NewsRequest();
        params.setNewId(3L);
        logger.info("根据id查询新闻资讯, 请求参数：{}", params);
        CommonResponse<News> res = new CommonResponse<News>();
        News news = this.newsService.getNewsById(params);
        String images = news.getImages();//json存储的图片信息，转换成列表对象
        List<Image> imageLists = JsonUtils.fromJson(images, new TypeReference<List<Image>>() {
        });
        if (imageLists == null) {
            news.setImagesList(Lists.newArrayList());
        } else {
            news.setImagesList(imageLists);
        }
        res.setData(news);
        logger.info("根据id查询新闻资讯, 测试结果：{}", res.toJsonString());
    }

    @ApiOperation(value = "新增新闻资讯")
    @Test
    public void insertNews() {
        String json = "{\"typeId\":3,\"otherTypeId\":1,\"title\":\"智能药师桐小丫来了 将在药房上岗\",\"content\":\"作为医药行业龙头企业，太极集团近年来紧抓智能制造发展机遇，总投资超5亿元，在人工智能、智能工厂、大数据研发等方面持续发力，加快推进企业智能化升级转型，用智能化全面提升企业竞争力。\",\"contentUrl\":\"http://www.sohu.com/a/248873127_742667\",\"images\":\"[{\\\"name\\\":\\\"名字\\\",\\\"description\\\":\\\"描述\\\",\\\"url\\\":\\\"http://5b0988e595225.cdn.sohucs.com/images/20180820/17f69482bf7148cc9dd5f5b1d6620b82.jpeg\\\",\\\"height\\\":500,\\\"width\\\":800,\\\"type\\\":1},{\\\"name\\\":\\\"介绍\\\",\\\"description\\\":\\\"具体描述\\\",\\\"url\\\":\\\"http://5b0988e595225.cdn.sohucs.com/images/20180820/17f69482bf7148cc9dd5f5b1d6620b82.jpeg\\\",\\\"height\\\":500,\\\"width\\\":800,\\\"type\\\":1},{\\\"name\\\":\\\"介绍1\\\",\\\"description\\\":\\\"具体描述2\\\",\\\"url\\\":\\\"http://www.firsthospital.cn/upload/images/201808/06163536.jpg\\\",\\\"height\\\":500,\\\"width\\\":800,\\\"type\\\":1}]\",\"status\":\"0\",\"gmtCreate\":1536713301000,\"gmtModified\":1536713301000,\"remarks\":\"新创建导入数据\",\"imagesList\":[{\"name\":\"名字\",\"description\":\"描述\",\"url\":\"http://5b0988e595225.cdn.sohucs.com/images/20180820/17f69482bf7148cc9dd5f5b1d6620b82.jpeg\",\"contentUrl\":\"\",\"height\":500,\"width\":800,\"type\":1},{\"name\":\"介绍\",\"description\":\"具体描述\",\"url\":\"http://5b0988e595225.cdn.sohucs.com/images/20180820/17f69482bf7148cc9dd5f5b1d6620b82.jpeg\",\"contentUrl\":\"\",\"height\":500,\"width\":800,\"type\":1},{\"name\":\"介绍1\",\"description\":\"具体描述2\",\"url\":\"http://www.firsthospital.cn/upload/images/201808/06163536.jpg\",\"contentUrl\":\"\",\"height\":500,\"width\":800,\"type\":1}],\"auditInfos\":null}";
        News news = JSONObject.parseObject(json, News.class);
        logger.info("新增新闻资讯, 请求参数：{}", news);
        CommonResponse<News> res = new CommonResponse<News>();
        this.newsService.insertNews(news, 8L);
        res.setData(news);
        logger.info("新增新闻资讯, 测试结果：{}", res.toJsonString());
    }

    @ApiOperation(value = "更新新闻资讯")
    @Test
    public void updateNews() {
        String json = "{\"id\":3,\"typeId\":3,\"otherTypeId\":1,\"title\":\"智能药师桐小丫来了 将在药房上岗\",\"content\":\"作为医药行业龙头企业，太极集团近年来紧抓智能制造发展机遇，总投资超5亿元，在人工智能、智能工厂、大数据研发等方面持续发力，加快推进企业智能化升级转型，用智能化全面提升企业竞争力。\",\"contentUrl\":\"http://www.sohu.com/a/248873127_742667\",\"images\":\"[{\\\"name\\\":\\\"名字\\\",\\\"description\\\":\\\"描述\\\",\\\"url\\\":\\\"http://5b0988e595225.cdn.sohucs.com/images/20180820/17f69482bf7148cc9dd5f5b1d6620b82.jpeg\\\",\\\"height\\\":500,\\\"width\\\":800,\\\"type\\\":1},{\\\"name\\\":\\\"介绍\\\",\\\"description\\\":\\\"具体描述\\\",\\\"url\\\":\\\"http://5b0988e595225.cdn.sohucs.com/images/20180820/17f69482bf7148cc9dd5f5b1d6620b82.jpeg\\\",\\\"height\\\":500,\\\"width\\\":800,\\\"type\\\":1},{\\\"name\\\":\\\"介绍1\\\",\\\"description\\\":\\\"具体描述2\\\",\\\"url\\\":\\\"http://www.firsthospital.cn/upload/images/201808/06163536.jpg\\\",\\\"height\\\":500,\\\"width\\\":800,\\\"type\\\":1}]\",\"status\":\"0\",\"gmtCreate\":1536713301000,\"gmtModified\":1536713301000,\"remarks\":\"新创建导入数据\",\"imagesList\":[{\"name\":\"名字\",\"description\":\"描述\",\"url\":\"http://5b0988e595225.cdn.sohucs.com/images/20180820/17f69482bf7148cc9dd5f5b1d6620b82.jpeg\",\"contentUrl\":\"\",\"height\":500,\"width\":800,\"type\":1},{\"name\":\"介绍\",\"description\":\"具体描述\",\"url\":\"http://5b0988e595225.cdn.sohucs.com/images/20180820/17f69482bf7148cc9dd5f5b1d6620b82.jpeg\",\"contentUrl\":\"\",\"height\":500,\"width\":800,\"type\":1},{\"name\":\"介绍1\",\"description\":\"具体描述2\",\"url\":\"http://www.firsthospital.cn/upload/images/201808/06163536.jpg\",\"contentUrl\":\"\",\"height\":500,\"width\":800,\"type\":1}],\"auditInfos\":null}";
        News news = JSONObject.parseObject(json, News.class);
        logger.info("更新新闻资讯, 请求参数：{}", news);
        CommonResponse<News> res = new CommonResponse<News>();
        this.newsService.updateNews(news, 8L);
        res.setData(news);
        logger.info("更新新闻资讯, 测试结果：{}", res.toJsonString());
    }

    @ApiOperation(value = "删除新闻资讯")
    @Test
    public void deleteNews() {
        String json = "{\"newId\":3}";
        NewsRequest params = JSONObject.parseObject(json, NewsRequest.class);
        logger.info("删除新闻资讯, 请求参数：{}", params);
        CommonResponse<Messages> res = new CommonResponse<Messages>();
        this.newsService.deleteByPrimaryKey(params);
        Messages messages = new Messages();
        messages.setId(params.getNewId());
        res.setData(messages);
        logger.info("删除新闻资讯, 测试结果：{}", res.toJsonString());
    }

    @ApiOperation(value = "启用/停用新闻资讯")
    @Test
    public void enableOrDisableNews() {
        String json = "{\"id\":3,\"status\":0}";
        News news = JSONObject.parseObject(json, News.class);
        logger.info("启用/停用新闻资讯, 请求参数：{}", news);
        CommonResponse<Messages> res = new CommonResponse<Messages>();
        this.newsService.enableOrDisableNews(news);
        Messages messages = new Messages();
        messages.setId(news.getId());
        res.setData(messages);
        logger.info("启用/停用新闻资讯, 测试结果：{}", res.toJsonString());
    }
}
