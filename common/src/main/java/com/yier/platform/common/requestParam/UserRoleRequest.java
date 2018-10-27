package com.yier.platform.common.requestParam;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "用户角色请求类")
public class UserRoleRequest extends BaseRequest {
    @ApiModelProperty(value = "用户id")
    private Long userId;
    @ApiModelProperty(value = "角色id")
    private Long roleId;


    public UserRoleRequest() {
        super();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}