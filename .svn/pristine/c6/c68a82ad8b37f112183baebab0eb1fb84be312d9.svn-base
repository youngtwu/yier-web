package com.yier.platform.common.requestParam;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "用户地址任务请求类")
public class UserAddressRequest extends BaseRequest {
    @ApiModelProperty(value = "主键ID")
    private Long userAddressId;
    @ApiModelProperty(value = "统一编号")
    private String addressCodeNo;
    @ApiModelProperty(value = "客户端标识。1-患者端，2-医生端，3-药师端")
    private Long typeId;
    @ApiModelProperty(value = "用户表ID,关联t_doctor.id，t_patient.id,t_pharmacy.id")
    private Long userTableId;
    @ApiModelProperty(value = "用户名")
    private String userName;
    @ApiModelProperty(value = "联系电话")
    private String contact;
    @ApiModelProperty(value = "客户端标识。1-患者端，2-医生端，3-药师端")
    private String defaultValue;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Long getUserAddressId() {
        return userAddressId;
    }

    public void setUserAddressId(Long userAddressId) {
        this.userAddressId = userAddressId;
    }

    public String getAddressCodeNo() {
        return addressCodeNo;
    }

    public void setAddressCodeNo(String addressCodeNo) {
        this.addressCodeNo = addressCodeNo;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public Long getUserTableId() {
        return userTableId;
    }

    public void setUserTableId(Long userTableId) {
        this.userTableId = userTableId;
    }
}
