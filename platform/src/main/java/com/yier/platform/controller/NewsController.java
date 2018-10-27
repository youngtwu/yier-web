package com.yier.platform.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Lists;
import com.yier.platform.annotation.ApiCheck;
import com.yier.platform.common.jsonResponse.ListResponse;
import com.yier.platform.common.model.News;
import com.yier.platform.common.po.Image;
import com.yier.platform.common.requestParam.NewsRequest;
import com.yier.platform.common.util.JsonUtils;
import com.yier.platform.service.NewsService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@ApiModel(value = "消息新闻接口")
@RestController
@RequestMapping("/news")
public class NewsController {

    private static final Logger logger = LoggerFactory.getLogger(NewsController.class);
    @Autowired
    private NewsService serviceNewsService;

    @ApiOperation(value = "通过页码、长度获取对应的新闻分页列表")
    @ApiCheck(check = true)
    @RequestMapping(value = "/listNewsForPage.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ListResponse<News> listNewsForPage(HttpServletRequest request, HttpServletResponse response, @RequestBody NewsRequest params) {
        String typeId = request.getHeader("typeId");
        if(StringUtils.isBlank(typeId)){
            typeId = request.getParameter("typeId");//端口
        }
        logger.info("目前的端口id是：" + typeId);
        long typeIdLong = Long.parseLong(typeId);
        params.setTypeId(typeIdLong);
        ListResponse<News> res = new ListResponse<News>();
        params.doWithSortOrStart();
        logger.info("目前分页条件如下：" + params.toJsonString());
        List<News> list = serviceNewsService.listNews(params);
        for (News item : list) {
            String images = item.getImages();//json存储的图片信息，转换成列表对象 ，如果对象Image存在构造函数，一定要有可
            List<Image> lists = JsonUtils.fromJson(images, new TypeReference<List<Image>>() {
            });
            if (lists == null) {
                item.setImagesList(Lists.newArrayList());
            } else {
                item.setImagesList(lists);
            }
        }
        int count = serviceNewsService.listNewsCount(params);
        res.setData(list);
        res.setTotal(count);
        logger.debug("-结果为->" + res.toJsonString());
        return res;
    }
}
