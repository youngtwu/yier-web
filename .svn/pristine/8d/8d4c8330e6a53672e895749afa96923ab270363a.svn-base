package com.yier.platform.controller;

import com.yier.platform.annotation.ApiCheck;
import com.yier.platform.common.jsonResponse.CommonResponse;
import com.yier.platform.common.jsonResponse.ListResponse;
import com.yier.platform.common.model.User;
import com.yier.platform.common.model.UserPo;
import com.yier.platform.common.requestParam.UserRequest;
import com.yier.platform.service.UserService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.List;

@ApiModel(value = "用户请求接口")
@RestController
@RequestMapping("/user")
@Slf4j
//@CrossOrigin
//响应头 origins:Access-Control-Allow-Origin: http://192.168.0.199:8806 "*"代表所有域的请求都支持
//@CrossOrigin(origins = {"http://192.168.0.199:8806","http://192.168.0.215:8801","https://vimsky.com"})
public class UserController {
    @Autowired
    private UserService serviceUserService;

    @ApiOperation(value = "用户注册接口[插入数据时的实名认证]")
    @ApiCheck(check = false)
    @RequestMapping(value = "/register.json", method = {RequestMethod.POST})
    public CommonResponse<UserPo> register(@ApiParam(value = "患者信息body类") @RequestBody User record, HttpServletRequest request, HttpServletResponse response) {
        CommonResponse<UserPo> result = new CommonResponse<UserPo>();
        result.setData(serviceUserService.insertUserSelective(record));
        return result;
    }

    @ApiOperation(value = "通过手机号及密码登录返回用户信息")
    @ApiCheck(check = false)
    @RequestMapping(value = "/loginByPassword.json", method = {RequestMethod.POST})
    public CommonResponse<UserPo> loginByPassword(HttpServletRequest request, HttpServletResponse response,
                                                  @ApiParam(value = "手机号码") @RequestParam(value = "phoneNumber", required = true) String phoneNumber,
                                                  @ApiParam(value = "密码") @RequestParam(value = "password", required = true) String password) {
        CommonResponse<UserPo> result = new CommonResponse<UserPo>();
        UserRequest params = new UserRequest();
        params.setPhoneNumber(phoneNumber);
        result.setData(serviceUserService.loginByPassword(params, password));
        return result;
    }

    @ApiOperation(value = "修改更新密码")
    @ApiCheck(check = false)
    @RequestMapping(value = "/updateUser.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse<User> updateUser(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "患者信息body类") @RequestBody User record) {
        CommonResponse<User> result = new CommonResponse<User>();
        if (StringUtils.isNotBlank(record.getPassword())) {
            record.setPassword(DigestUtils.md5Hex(record.getPassword()));
        }
        serviceUserService.updateByPrimaryKeySelective(record);
        result.setData(record);
        return result;
    }

    @ApiOperation(value = "通过条件查询获取用户信息列表")
    @ApiCheck(check = false)
    @RequestMapping(value = "/getUserList.json", method = {RequestMethod.POST,RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ListResponse<User> getUserList(HttpServletRequest request, HttpServletResponse response,  @RequestParam(value = "use", required = false) String use,
                                         @RequestBody(required = false) UserRequest params) {

        log.info("-----报文头");
        for(Enumeration e = request.getHeaderNames(); e.hasMoreElements();){
            String header = e.nextElement().toString();
            log.info("header:{} value:{} ",header,request.getHeader(header));
        }
        log.info("-----参数体");
        for(Enumeration e = request.getParameterNames() ; e.hasMoreElements();){
            String parameter = e.nextElement().toString();
            log.info("parameter:{} value:{} ",parameter,request.getParameter(parameter));
        }
        log.info("-----属性");
        for(Enumeration e = request.getAttributeNames() ; e.hasMoreElements();){
            String attributeNames = e.nextElement().toString();
            log.info("attributeNames:{} value:{} ",attributeNames,request.getAttribute(attributeNames));
        }
        String requestMethod = request.getMethod();
        log.info("requestMethod:"+requestMethod);
        params.doWithSortOrStart();


        log.info("目前分页条件如下：" + params.toJsonString());
        ListResponse<User> res = new ListResponse<User>();
        List<User> list = serviceUserService.listUser(params);
        int count = serviceUserService.listUserCount(params);
        res.setData(list);
        res.setTotal(count);
        log.debug(res.toJsonString());
        return res;
    }
    @ApiOperation(value = "通过条件查询获取用户信息列表")
    @ApiCheck(check = false)
    @RequestMapping(value = "/getUserListParam.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ListResponse<User> getUserListParam(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "use", required = true) String use, @ApiParam(value = "基本请求接口类") @RequestBody UserRequest params) {
        //UserRequest params = new UserRequest();
        params.doWithSortOrStart();
        log.info("查询条件如此目前分页条件如下：{} user:{}" ,params.toJsonString(),use);
        ListResponse<User> res = new ListResponse<User>();
        List<User> list = serviceUserService.listUser(params);
        int count = serviceUserService.listUserCount(params);
        res.setData(list);
        res.setTotal(count);
        log.debug(res.toJsonString());
        return res;
    }



    @ApiOperation(value = "通过条件查询获取用户信息列表")
    @ApiCheck(check = false)
    @RequestMapping(value = "/getUserListParamClass.json", method = {RequestMethod.POST,RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ListResponse<User> getUserListParamClass(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "use", required = true) String use,
                                                    @RequestParam(value = "params", required = true) UserRequest params) {
        params.doWithSortOrStart();
        log.info("目前分页条件如下：" + params.toJsonString());
        ListResponse<User> res = new ListResponse<User>();
        List<User> list = serviceUserService.listUser(params);
        int count = serviceUserService.listUserCount(params);
        res.setData(list);
        res.setTotal(count);
        log.debug(res.toJsonString());
        return res;
    }




    @ApiOperation(value = "通过条件查询获取用户信息列表")
    @ApiCheck(check = false)
    @RequestMapping(value = "/getUserListGetInfo.json", method = {RequestMethod.POST,RequestMethod.GET}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ListResponse<User> getUserListGetInfo(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "use", required = false) String use) {
        UserRequest params = new UserRequest();
        params.doWithSortOrStart();
        log.info("目前分页条件如下：{} use：{}" , params.toJsonString(),use);
        ListResponse<User> res = new ListResponse<User>();
        List<User> list = serviceUserService.listUser(params);
        int count = serviceUserService.listUserCount(params);
        res.setData(list);
        res.setTotal(count);
        log.debug(res.toJsonString());
        return res;
    }




    //**Jsonp(JSON with Padding)是资料格式 json 的一种“使用模式”
    @ApiOperation(value = "通过条件跨域请求查询获取用户信息列表")
    @ApiCheck(check = false)
    @RequestMapping(value = "/getJsonpUserList.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ListResponse<User> getJsonpUserList(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "基本请求接口类") @RequestBody UserRequest params) {
        params.doWithSortOrStart();
        log.info("目前分页条件如下：" + params.toJsonString());
        ListResponse<User> res = new ListResponse<User>();
        List<User> list = serviceUserService.listUser(params);
        int count = serviceUserService.listUserCount(params);
        res.setData(list);
        res.setTotal(count);
        log.debug(res.toJsonString());
        return res;
    }
}
