package com.yier.platform.web;


import com.github.yingzhuo.carnival.restful.security.Logical;
import com.github.yingzhuo.carnival.restful.security.RequiresAuthentication;
import com.github.yingzhuo.carnival.restful.security.RequiresPermissions;
import com.github.yingzhuo.carnival.restful.security.RequiresRoles;
import com.github.yingzhuo.carnival.restful.security.annotation.UserDetailsProperty;
import com.github.yingzhuo.carnival.restful.security.userdetails.UserDetails;
import com.github.yingzhuo.carnival.restful.security.util.UserDetailsUtils;
import com.yier.platform.common.exception.ServiceException;
import com.yier.platform.common.jsonResponse.CommonResponse;
import com.yier.platform.common.jsonResponse.ListResponse;
import com.yier.platform.common.model.User;
import com.yier.platform.common.requestParam.NewsRequest;
import com.yier.platform.common.requestParam.UserRequest;
import com.yier.platform.service.JwtTokenShiroService;
import com.yier.platform.service.NewsService;
import com.yier.platform.service.Uploader;
import com.yier.platform.service.UserService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.MutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
@ApiModel(value = "页面测试相关请求接口")
@Controller
//如果只是使用@RestController注解Controller，则Controller中的方法无法返回jsp页面，配置的视图解析器InternalResourceViewResolver不起作用，返回的内容就是Return 里的内容。例如：本来应该到success.jsp页面的，则其显示success.
@RequestMapping("")
@Slf4j
public class IndexController {
    @Autowired
    private NewsService serviceNewsService;

    @Autowired
    private UserService serviceUserService;

    @Autowired
    private JwtTokenShiroService jwtTokenShiroService;

    @Autowired
    private Uploader uploader;

    @ResponseBody
    @ApiOperation(value = "看这个结果就是json测试")
    @RequestMapping(value = "/createJwt.json", method = {RequestMethod.POST, RequestMethod.GET})
    public CommonResponse<String> jqueryJson0(HttpServletRequest request, HttpServletResponse response) {
        log.info("进入---------》看这个结果就是json测试");
        CommonResponse<String> result = new CommonResponse<String>();
        String[] roles = new String[]{"r1", "r2", "r3", "r4"};
        User user = new User();
        user.setUserName("ceshi");
        try {
            //result.setData(jwtTokenShiroService.createToken());
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("目前的结果是：" + result.toJsonString());
        return result;
    }

    @RequiresAuthentication
    @ResponseBody
    @ApiOperation(value = "看这个结果就是json测试")
    @RequestMapping(value = "/getJwtInfo.json", method = {RequestMethod.POST, RequestMethod.GET})
    public CommonResponse<MutablePair<String, UserDetails>> jqueryJson1(HttpServletRequest request, HttpServletResponse response) {
        // 报文头例子header--->Authorization:Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJyb2xlcyI6WyJyMSIsInIyIiwicjMiXSwibmFtZSI6IuW6lOWNkyIsImV4cCI6MTUzNzg3MTIzM30.90Io5CEar3uO2CsbZlf3eX8i4OTyYTrgZlHvTLaNNwW31YTXnWHlZA9jfNem-BYcY5YCZJOUXtJ0A_QeDVaWQw
        log.info("进入---------》看这个结果就是json测试");
        CommonResponse<MutablePair<String, UserDetails>> result = new CommonResponse<MutablePair<String, UserDetails>>();
        MutablePair<String, UserDetails> mutablePair = new MutablePair<>();
        mutablePair.left = "tokenObject";
        mutablePair.right = UserDetailsUtils.get();
        result.setData(mutablePair);
        log.info("目前的结果是：" + result.toJsonString());
        return result;
    }

//    @RequiresAuthentication
////    @RequiresGuest
//    @RequiresPermissions(value = {"p5", "r6"} , logical = Logical.AND)
    @RequiresRoles(value = {"r5", "r6"}, logical = Logical.AND)
    @ResponseBody
    @RequestMapping(value = "/version.json", method = {RequestMethod.POST, RequestMethod.GET})
//    @GetMapping("version")
    public CommonResponse<String> version(HttpServletRequest request, HttpServletResponse response, @UserDetailsProperty("payload['xxx']") String xxx, UserDetails userDetails) {//@UserDetailsProperty("payload['xxx']") String xxx) {
        log.info("xxx={} userDetailsRealm = {} rolse:{}", xxx, userDetails, UserDetailsUtils.getRoleNames());
        CommonResponse<String> result = new CommonResponse<String>();
        result.setData("1.1");
        return result;
    }


    @RequestMapping(value = {"/vueTest"}, method = {RequestMethod.POST, RequestMethod.GET})
    public String vueTest(HttpServletRequest request, ModelMap model) {
        log.info("进入到vueTest.html");
        model.addAttribute("nameInfo", "nameInfo");
        model.addAttribute("newList", this.serviceNewsService.listNews(new NewsRequest()));
        return "vueTest";
    }

    @RequestMapping(value = {"/userTest"}, method = {RequestMethod.POST, RequestMethod.GET})
    public String userTest(HttpServletRequest request, ModelMap model) {
        log.info("进入到userTest.html");
        model.addAttribute("nameInfo", "nameInfo");
        model.addAttribute("newList", this.serviceNewsService.listNews(new NewsRequest()));
        return "userTest";
    }


    @RequestMapping(value = {"/jquery1"}, method = {RequestMethod.POST, RequestMethod.GET})
    public String jquery1(HttpServletRequest request, ModelMap model) {
        log.info("进入到freemarker");
        model.addAttribute("nameInfo", "nameInfo");
        model.addAttribute("newList", this.serviceNewsService.listNews(new NewsRequest()));
        return "jquery1";
    }

    @ResponseBody
    @ApiOperation(value = "通过条件查询获取用户信息列表")
    @RequestMapping(value = "/getUserList.json", method = {RequestMethod.POST, RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ListResponse<User> getUserList(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "基本请求接口类") @RequestBody UserRequest params) {
        log.info("进入---------》通过条件查询获取用户信息列表");
        params.doWithSortOrStart();
        log.info("目前分页条件如下：" + params.toJsonString());
//        UserRequest params = new UserRequest();
        ListResponse<User> res = new ListResponse<User>();
        List<User> list = serviceUserService.listUser(params);
        int count = serviceUserService.listUserCount(params);
        res.setData(list);
        res.setTotal(count);
        log.debug(res.toJsonString());
        return res;
    }

    @ResponseBody
    @ApiOperation(value = "看这个结果就是json测试")
    @RequestMapping(value = "/jquery.json", method = {RequestMethod.POST, RequestMethod.GET})
    public CommonResponse<String> jqueryJson(HttpServletRequest request, HttpServletResponse response) {
        log.info("进入---------》看这个结果就是json测试");
        CommonResponse<String> result = new CommonResponse<String>();
        result.setData("-------------看这个结果就是json测试--------");
        log.info("目前的结果是：" + result.toJsonString());
        return result;
    }

//    @RequestMapping(value = {"/jquery.json"},method = {RequestMethod.POST,RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    public String jqueryJson(HttpServletRequest request){
//        log.info("访问接口");
//        return "jquery.json 信息";
//    }


    @RequestMapping(value = {"/vue0"}, method = {RequestMethod.POST, RequestMethod.GET})
    public String vue0(HttpServletRequest request, ModelMap model) {
        log.info("进入到freemarker");
        model.addAttribute("nameInfo", "nameInfo");
        model.addAttribute("newList", this.serviceNewsService.listNews(new NewsRequest()));
        return "vue";
    }

    @RequestMapping(value = {"/vueT"}, method = {RequestMethod.POST, RequestMethod.GET})
    public String vueT(HttpServletRequest request, Model model) {
        log.info("进入到thymeleaf");
        model.addAttribute("name", "名称信息等等");
        List<String> list = new ArrayList<>();
        list.add("sam a");
        list.add("sam b");
        list.add("sam c");
        list.add("sam d");
        model.addAttribute("list", list);
        return "vueT";
    }


    @RequestMapping(value = {"/freemarker"}, method = {RequestMethod.POST, RequestMethod.GET})
    public String freemarker(HttpServletRequest request, ModelMap model) {
        log.info("进入到freemarker");

        int i = 10;
        int j = 0;
        int k = i / j;
        model.addAttribute("nameInfo", "nameInfo");
        model.addAttribute("newList", this.serviceNewsService.listNews(new NewsRequest()));
        return "freemarkerInfo";
    }

    @RequestMapping(value = {"/freemarker0"}, method = {RequestMethod.POST, RequestMethod.GET})
    public String freemarker0(HttpServletRequest request, Model model) {
        log.info("进入到freemarker");
        model.addAttribute("nameInfo", "nameInfo");
        model.addAttribute("newList", this.serviceNewsService.listNews(new NewsRequest()));
        return "freemarkerInfo";
    }

    @RequestMapping(value = {"/thymeleaf0"}, method = {RequestMethod.POST, RequestMethod.GET})
    public String thymeleaf0(HttpServletRequest request, Model model) {
        log.info("进入到thymeleaf");
        model.addAttribute("name", "名称信息等等");
        List<String> list = new ArrayList<>();
        list.add("sam a");
        list.add("sam b");
        list.add("sam c");
        list.add("sam d");
        model.addAttribute("list", list);
        return "thymeleaf";
    }

    @RequestMapping(value = {"/thymeleaf"}, method = {RequestMethod.POST, RequestMethod.GET})
    public String thymeleaf(HttpServletRequest request, ModelMap model) {
        log.info("进入到thymeleaf");
        int i = 10;
        if (i == 10) {
            throw new ServiceException("出现服务端异常！！！");
        }
        model.put("name", "名称信息等等");
        List<String> list = new ArrayList<>();
        list.add("sam a");
        list.add("sam b");
        list.add("sam c");
        list.add("sam d");
        model.put("list", list);
        return "thymeleaf";
    }

    @RequestMapping(value = {"/helloInfo"}, method = {RequestMethod.POST, RequestMethod.GET})
    public String index(HttpServletRequest request) {
        log.info("进入到helloInfo");
        return "testUploadFiles";
    }

    @ResponseBody
    @RequestMapping(value = {"/hello"}, method = {RequestMethod.POST, RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String hello(HttpServletRequest request) {
        return "hello word 信息0";
    }

    @ResponseBody
    @RequestMapping(value = {"/hello1"}, method = {RequestMethod.POST, RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String hello1() {
        return "hello word1  信息1";
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String home() {
        return "Hello，Spring Boot  信息1 信息1";
    }

    @RequestMapping(value = "/testUploadFiles", method = {RequestMethod.POST, RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String testUploadFiles(HttpServletRequest request) {
        log.info("进入到testUploadFiles.html");
        return "testUploadFiles";
    }

    @RequestMapping(value = "/testDeleteFiles", method = {RequestMethod.POST, RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String testDeleteFiles(HttpServletRequest request) {
        log.info("进入到testUploadFiles");
        String urlPath = "http://192.168.0.215:7070/group1/M00/00/07/wKgA11utxKyAd1hwAACd5D95MTc998.jpg";
        this.uploader.deleteFile(urlPath);
        return "testUploadFiles";
    }
}
