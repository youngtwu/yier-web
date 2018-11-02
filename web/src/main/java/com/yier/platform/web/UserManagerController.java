package com.yier.platform.web;

import com.github.yingzhuo.carnival.restful.security.Logical;
import com.github.yingzhuo.carnival.restful.security.RequiresAuthentication;
import com.github.yingzhuo.carnival.restful.security.RequiresRoles;
import com.yier.platform.annotation.ApiCheck;
import com.yier.platform.common.jsonResponse.CommonResponse;
import com.yier.platform.common.jsonResponse.ListResponse;
import com.yier.platform.common.model.User;
import com.yier.platform.common.model.UserPo;
import com.yier.platform.common.requestParam.UserRequest;
import com.yier.platform.common.util.IPUtils;
import com.yier.platform.service.UserService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@ApiModel(value = "用户请求接口")
@RestController
@RequestMapping("/user")
@Slf4j
public class UserManagerController {
    private Logger logger = LoggerFactory.getLogger(UserManagerController.class);

    @Autowired
    private UserService serviceUserService;

    @ApiOperation(value = "获取用户信息列表（包含角色信息）")
    @ApiCheck(check = false)
    @RequiresAuthentication
    @RequiresRoles(value = {"Admin", "Super"}, logical = Logical.OR)
    @RequestMapping(value = "/getUserList.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ListResponse<User> getUserList(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "用户请求接口类") @RequestBody UserRequest params) {
        params.doWithSortOrStart();
        logger.info("获取用户信息列表, 请求参数：{}", params.toJsonString());
        ListResponse<User> res = new ListResponse<User>();
        List<User> list = this.serviceUserService.getUserList(params);
        int count = this.serviceUserService.getUserListCount(params);
        res.setData(list);
        res.setTotal(count);
        logger.debug("获取用户信息列表, 返回结果：{}", res.toJsonString());
        return res;
    }

    @ApiOperation(value = "根据id获取用户信息http://192.168.0.200:8807/user/getUserById.json")
    @ApiCheck(check = false)
    @RequiresAuthentication
    @RequiresRoles(value = {"Admin", "Super"}, logical = Logical.OR)
    @RequestMapping(value = "/getUserById.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse<UserPo> getUserById(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "用户请求接口类") @RequestBody UserRequest params) {
        logger.info("根据id获取用户信息, 请求参数：{}", params);
        CommonResponse<UserPo> res = new CommonResponse<UserPo>();
        UserPo user = this.serviceUserService.getUserById(params);
        res.setData(user);
        logger.debug("根据id获取用户信息, 返回结果：{}", res.toJsonString());
        return res;
    }

    @ApiOperation(value = "新增用户信息{\"type\":0,\"userName\":\"小张\",\"trueName\":\"张三\",\"phoneNumber\":\"17612166890\",\"idCardNo\":\"42252619950915452x\",\"email\":\"123@163.com\"}")
    @ApiCheck(check = false)
    @RequiresAuthentication
    @RequiresRoles(value = {"Admin", "Super"}, logical = Logical.OR)
    @RequestMapping(value = "/insertUser.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse<User> insertUser(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "用户请求接口类") @RequestBody User record) {
        log.info("新增用户信息, 请求参数：{}", record.toJsonString());
        CommonResponse<User> res = new CommonResponse<User>();
        this.serviceUserService.insertUser(record);
        res.setData(record);
        log.debug("新增用户信息, 返回结果：{}", res.toJsonString());
        return res;
    }

    @ApiOperation(value = "更新用户信息{\"userId\":1,\"userName\":\"小李\",\"trueName\":\"李四\",\"phoneNumber\":\"17612166888\",\"idCardNo\":\"42252619960909453x\",\"email\":\"lisi@163.com\"}")
    @ApiCheck(check = false)
    @RequiresAuthentication
    @RequiresRoles(value = {"Admin", "Super"}, logical = Logical.OR)
    @RequestMapping(value = "/updateUser.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse<User> updateUser(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "用户请求接口类") @RequestBody User record) {
        logger.info("更新用户信息, 请求参数：{}", record.toJsonString());
        CommonResponse<User> res = new CommonResponse<User>();
        this.serviceUserService.updateUser(record);
        res.setData(record);
        logger.debug("更新用户信息, 返回结果：{}", res.toJsonString());
        return res;
    }

    @ApiOperation(value = "停用/启用用户信息 状态（0正常 1删除 2停用 3冻结 4 审核中 5审核未通过）{\"id\":1,\"status\":\"0\"}")
    @ApiCheck(check = false)
    @RequiresAuthentication
    @RequiresRoles(value = {"Admin", "Super"}, logical = Logical.OR)
    @RequestMapping(value = "/enableOrDisableUser.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse<User> enableOrDisableUser(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "用户请求接口类") @RequestBody User record) {
        logger.info("停用/启用用户信息, 请求参数：{}", record.toJsonString());
        CommonResponse<User> res = new CommonResponse<User>();
        User user = this.serviceUserService.enableOrDisableUser(record);
        res.setData(user);
        logger.debug("停用/启用用户信息, 返回结果：{}", res.toJsonString());
        return res;
    }

    @ApiOperation(value = "通过手机号及密码登录返回用户信息")
    @ApiCheck(check = false)
    //@RequiresRoles(value = {"Admin"}, logical = Logical.AND)
    @RequestMapping(value = "/userLoginByPassword.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse<UserPo> userLoginByPassword(HttpServletRequest request, HttpServletResponse response,
                                                      @ApiParam(value = "手机号码") @RequestParam(value = "phoneNumber", required = true) String phoneNumber,
                                                      @ApiParam(value = "密码") @RequestParam(value = "password", required = true) String password) {
        String osType = IPUtils.getHeaderValueByKey(request, "osType", "web");
        CommonResponse<UserPo> result = new CommonResponse<UserPo>();
        UserRequest params = new UserRequest();
        params.setPhoneNumber(phoneNumber);
        result.setData(this.serviceUserService.userLoginByPassword(params, password, osType));
        return result;
    }

    @ApiOperation(value = "通过手机号及验证码登录返回用户信息")
    @ApiCheck(check = false)
    //@RequiresRoles(value = {"Admin"}, logical = Logical.AND)
    @RequestMapping(value = "/userLoginByVerifyCode.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse<UserPo> userLoginByVerifyCode(HttpServletRequest request, HttpServletResponse response,
                                                        @ApiParam(value = "手机号码") @RequestParam(value = "phoneNumber", required = true) String phoneNumber,
                                                        @ApiParam(value = "验证码") @RequestParam(value = "verifyCode", required = true) String verifyCode) {
        String osType = IPUtils.getHeaderValueByKey(request, "osType", "web");
        CommonResponse<UserPo> result = new CommonResponse<UserPo>();
        UserRequest params = new UserRequest();
        params.setPhoneNumber(phoneNumber);
        result.setData(this.serviceUserService.userLoginByVerifyCode(params, verifyCode, osType));
        return result;
    }

    @ApiOperation(value = "更新密码{\"id\":2,\"password\":\"1234\"}")
    @ApiCheck(check = false)
    @RequiresAuthentication
    @RequestMapping(value = "/updateUserPassword.json", method = {RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CommonResponse<User> updateUserPassword(HttpServletRequest request, HttpServletResponse response, @ApiParam(value = "用户实体类") @RequestBody UserRequest record) {
        logger.info("更新密码, 请求参数：{}", record.toJsonString());
        CommonResponse<User> res = new CommonResponse<User>();
        User user = this.serviceUserService.updateUserPassword(record);
        res.setData(user);
        logger.info("更新密码, 返回结果：{}", res.toJsonString());
        return res;
    }
}
