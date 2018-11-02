package com.yier.platform.service;

import com.alibaba.fastjson.JSONObject;
import com.yier.platform.TestBase;
import com.yier.platform.common.jsonResponse.CommonResponse;
import com.yier.platform.common.jsonResponse.ListResponse;
import com.yier.platform.common.model.Role;
import com.yier.platform.common.model.UserRole;
import com.yier.platform.common.requestParam.RoleRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@ApiModel(value = "用户角色请求接口")
public class UserRoleServiceTest extends TestBase {
    private static final Logger logger = LoggerFactory.getLogger(UserRoleServiceTest.class);

    @Autowired
    private RoleService roleService;

    @ApiOperation(value = "获取用户角色列表")
    @Test
    public void selectRolesList() {
        RoleRequest params = new RoleRequest();
        params.doWithSortOrStart();
        logger.info("获取用户角色列表, 请求参数：{}", params.toJsonString());
        ListResponse<Role> res = new ListResponse<Role>();
        List<Role> list = this.roleService.selectRoles(params);
        int count = this.roleService.selectRolesCount(params);
        res.setData(list);
        res.setTotal(count);
        logger.info("获取用户角色列表, 返回结果：{}", res.toJsonString());
    }

    @ApiOperation(value = "根据id获取用户角色信息")
    @Test
    public void getUserRoleById() {
        String json = "{\"roleId\":3}";
        RoleRequest params = JSONObject.parseObject(json, RoleRequest.class);
        logger.info("根据id获取用户角色信息, 请求参数：{}", params);
        CommonResponse<Role> res = new CommonResponse<Role>();
        Role role = this.roleService.getRoleById(params);
        res.setData(role);
        logger.info("根据id获取用户角色信息, 返回结果：{}", res.toJsonString());
    }

    @ApiOperation(value = "新增用户角色信息")
    @Test
    public void insertUserRole() {
        String json = "{\"type\":4,\"officeId\":1,\"name\":\"审核员\",\"enname\":\"Audit\",\"roleType\":\"\",\"dataScope\":\"\",\"isSys\":\"\",\"createBy\":4,\"remarks\":\"更新用户角色信息\"}";
        Role record = JSONObject.parseObject(json, Role.class);
        logger.info("新增用户角色信息, 请求参数：{}", record.toJsonString());
        CommonResponse<Role> res = new CommonResponse<Role>();
        this.roleService.insertRole(record);
        res.setData(record);
        logger.info("新增用户角色信息, 返回结果：{}", res.toJsonString());
    }

    @ApiOperation(value = "更新用户角色信息")
    @Test
    public void updateUserRole() {
        String json = "{\"id\":5,\"type\":4,\"officeId\":1,\"name\":\"审核员\",\"enname\":\"Audit\",\"roleType\":\"\",\"dataScope\":\"\",\"isSys\":\"\",\"createBy\":4,\"remarks\":\"更新用户角色信息\"}";
        Role record = JSONObject.parseObject(json, Role.class);
        logger.info("更新用户角色信息, 请求参数：{}", record.toJsonString());
        CommonResponse<Role> res = new CommonResponse<Role>();
        this.roleService.updateRole(record);
        res.setData(record);
        logger.info("更新用户角色信息, 返回结果：{}", res.toJsonString());
    }

    @ApiOperation(value = "停用/启用用户角色")
    @Test
    public void enableOrDisableUserRole() {
        String json = "{\"id\":5,\"status\":\"0\"}";
        Role record = JSONObject.parseObject(json, Role.class);
        logger.info("停用/启用用户角色, 请求参数：{}", record.toJsonString());
        CommonResponse<Role> res = new CommonResponse<Role>();
        Role role = this.roleService.updateRole(record);
        res.setData(role);
        logger.info("停用/启用用户角色, 返回结果：{}", res.toJsonString());
    }

    @ApiOperation(value = "根据用户id分配用户角色")
    @Test
    public void assignmentRoleByUserId() {
        String json = "[{\"userId\":5,\"roleId\":\"3\"},{\"userId\":6,\"roleId\":\"4\"}]";
        List<UserRole> userRole = JSONObject.parseArray(json, UserRole.class);
        logger.info("根据用户id分配用户角色, 请求参数：{}", userRole);
        ListResponse<UserRole> res = new ListResponse<UserRole>();
        List<UserRole> userRoles = this.roleService.assignmentRoleByUserId(userRole);
        res.setData(userRoles);
        logger.info("根据用户id分配用户角色, 返回结果：{}", res.toJsonString());
    }

}
