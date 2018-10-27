package com.yier.platform.controller;

import com.yier.platform.annotation.ApiCheck;
import com.yier.platform.common.jsonResponse.CommonResponse;
import com.yier.platform.common.jsonResponse.ListResponse;
import com.yier.platform.common.model.Feedback;
import com.yier.platform.common.requestParam.FeedbackRequest;
import com.yier.platform.service.FeedbackService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ApiModel(value = "意见反馈相关的请求接口")
@RestController
@RequestMapping("/feedback")
@Slf4j
public class FeedbackController {
    @Autowired
    private FeedbackService serviceFeedbackService;

    @ApiOperation(value = "插入意见反馈")
    @ApiCheck(check = true)
    @RequestMapping(value = "/insertFeedback.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse<Feedback> insertFeedback(HttpServletRequest request, HttpServletResponse response, @RequestBody Feedback record) {
        CommonResponse<Feedback> res = new CommonResponse<Feedback>();
        serviceFeedbackService.insertFeedback(record);
        res.setData(record);
        log.info(res.toJsonString());
        return res;
    }


    @ApiOperation(value = "通过条件查询获取意见反馈列表")
    @ApiCheck(check = false)
    @RequestMapping(value = "/getFeedbackList.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ListResponse<Feedback> getFeedbackoList(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "意见反馈请求接口类") @RequestBody FeedbackRequest params) {
        params.doWithSortOrStart();
        log.info("目前分页条件如下：" + params.toJsonString());
        ListResponse<Feedback> res = new ListResponse<Feedback>();
        res.setData(serviceFeedbackService.listFeedback(params));
        res.setTotal(serviceFeedbackService.listFeedbackCount(params));
        log.info(res.toJsonString());
        return res;
    }


    @ApiOperation(value = "一般根据ID条件查询问答列表")
    @ApiCheck(check = true)
    @RequestMapping(value = "/listAskAnswerFeedback.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ListResponse<Feedback> listAskAnswerFeedback(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "意见反馈请求接口类") @RequestBody FeedbackRequest params) {
        params.doWithSortOrStart();
        log.info("目前分页条件如下：" + params.toJsonString());
        ListResponse<Feedback> res = new ListResponse<Feedback>();
        res.setData(serviceFeedbackService.listAskAnswerFeedback(params));
        res.setTotal(serviceFeedbackService.listAskAnswerFeedbackCount(params));
        log.debug(res.toJsonString());
        return res;
    }


}
