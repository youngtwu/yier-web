package com.yier.platform.common.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "用户-角色 关联扩展类")
public class UserRolePo extends UserRole {
    @ApiModelProperty(value = "关联的用户信息")
    private User user;
    @ApiModelProperty(value = "关联的角色信息")
    private Role role;

    public UserRolePo() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
