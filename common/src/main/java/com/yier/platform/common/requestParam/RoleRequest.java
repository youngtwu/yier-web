package com.yier.platform.common.requestParam;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "角色请求类")
public class RoleRequest extends BaseRequest {
    @ApiModelProperty(value = "角色id")
    private Long roleId;
    @ApiModelProperty(value = "用户id")
    private Long userId;
    @ApiModelProperty(value = "角色名称")
    private String name;
    @ApiModelProperty(value = "英文名称")
    private String enname;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnname() {
        return enname;
    }

    public void setEnname(String enname) {
        this.enname = enname;
    }
}