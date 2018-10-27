package com.yier.platform.common.model;

import com.yier.platform.common.jsonResponse.BaseJsonObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(value = "角色表")
public class Role extends BaseJsonObject {
    @ApiModelProperty(value = "主键标识Id")
    private Long id;
    @ApiModelProperty(value = "端口类型 4 亿尔APP后台管理 6 医药信息后台管理")
    private Long type;
    @ApiModelProperty(value = "归属机构")
    private Long officeId;
    @ApiModelProperty(value = "角色名称")
    private String name;
    @ApiModelProperty(value = "英文名称")
    private String enname;
    @ApiModelProperty(value = "角色类型")
    private String roleType;
    @ApiModelProperty(value = "数据范围")
    private String dataScope;
    @ApiModelProperty(value = "是否系统数据")
    private String isSys;
    @ApiModelProperty(value = "创建者")
    private Long createBy;
    @ApiModelProperty(value = "状态（0正常 1已经处理 2用户点击取消 3 其他）")
    private String status;
    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;
    @ApiModelProperty(value = "修改时间")
    private Date gmtModified;
    @ApiModelProperty(value = "备注信息")
    private String remarks;

    public Role(Long id, Long type, Long officeId, String name, String enname, String roleType, String dataScope, String isSys, Long createBy, String status, Date gmtCreate, Date gmtModified, String remarks) {
        this.id = id;
        this.type = type;
        this.officeId = officeId;
        this.name = name;
        this.enname = enname;
        this.roleType = roleType;
        this.dataScope = dataScope;
        this.isSys = isSys;
        this.createBy = createBy;
        this.status = status;
        this.gmtCreate = gmtCreate;
        this.gmtModified = gmtModified;
        this.remarks = remarks;
    }

    public Role() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public Long getOfficeId() {
        return officeId;
    }

    public void setOfficeId(Long officeId) {
        this.officeId = officeId;
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

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public String getDataScope() {
        return dataScope;
    }

    public void setDataScope(String dataScope) {
        this.dataScope = dataScope;
    }

    public String getIsSys() {
        return isSys;
    }

    public void setIsSys(String isSys) {
        this.isSys = isSys;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}