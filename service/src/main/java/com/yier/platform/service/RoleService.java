package com.yier.platform.service;

import com.yier.platform.common.model.Role;
import com.yier.platform.common.model.UserRole;
import com.yier.platform.common.requestParam.RoleRequest;
import com.yier.platform.dao.RoleMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;

@ApiModel(value = "角色 service")
@Service
public class RoleService {
    private static final Logger logger = LoggerFactory.getLogger(RoleService.class);
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserRoleService userRoleService;

    @ApiOperation(value = "根据条件查询用户角色关联列表")
    public List<Role> selectRoles(RoleRequest params) {
        return this.roleMapper.selectRoles(params);
    }

    @ApiOperation(value = "根据条件查询用户角色关联列表 总数")
    public int selectRolesCount(RoleRequest params) {
        return this.roleMapper.selectRolesCount(params);
    }

    @ApiOperation(value = "根据id查询用户角色")
    public Role getRoleById(RoleRequest params) {
        return this.roleMapper.selectByPrimaryKey(params.getRoleId());
    }

    @ApiOperation(value = "新增用户角色")
    public Role insertRole(Role info) {
        Assert.isTrue(info.getName() != null, "角色名称不能为空！");
        this.roleMapper.insertSelective(info);
        return info;
    }

    @ApiOperation(value = "修改用户角色")
    public Role updateRole(Role info) {
        info.setGmtModified(new Date());
        this.roleMapper.updateByPrimaryKeySelective(info);
        return info;
    }

    @ApiOperation(value = "根据用户id修改用户角色")
    public List<UserRole> assignmentRoleByUserId(List<UserRole> info) {
        List<UserRole> userRoles = this.userRoleService.assignmentRoleByUserId(info);
        //同时重新更新缓存
        this.userRoleService.updateListAllUserRolePo();
        return userRoles;
    }
}
