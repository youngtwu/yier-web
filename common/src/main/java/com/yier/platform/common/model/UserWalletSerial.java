package com.yier.platform.common.model;

import com.yier.platform.common.jsonResponse.BaseJsonObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(value = "用户钱包流水")
public class UserWalletSerial extends BaseJsonObject {
    @ApiModelProperty(value = "流水id")
    private Long id;
    @ApiModelProperty(value = "客户端标识。1-患者端，2-医生端，3-药师端")
    private Long typeId;
    @ApiModelProperty(value = "用户ID,关联t_doctor.id，t_patient.id,t_pharmacy.id")
    private Long userId;
    @ApiModelProperty(value = "钱包id")
    private Long userWalletId;
    @ApiModelProperty(value = "订单类型：上门就诊 1 ，取药 2，送药 3")
    private Long orderType;
    @ApiModelProperty(value = "订单库中支付ID")
    private Long orderId;
    @ApiModelProperty(value = "订单号:最好有ID顺序")
    private String orderNo;
    @ApiModelProperty(value = "变动金额")
    private Integer amount;
    @ApiModelProperty(value = "资金流向描述")
    private String serialType;
    @ApiModelProperty(value = "资金流可能 的凭证,发票号,支付宝交易号")
    private String serialProof;
    @ApiModelProperty(value = "操作描述")
    private String description;
    @ApiModelProperty(value = "客户端标识。1-患者端，2-医生端，3-药师端")
    private Long creatorType;
    @ApiModelProperty(value = "用户ID,关联t_doctor.id，t_patient.id,t_pharmacy.id")
    private Long creatorId;
    @ApiModelProperty(value = "状态")
    private String status;
    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;
    @ApiModelProperty(value = "修改时间")
    private Date gmtModified;
    @ApiModelProperty(value = "备注信息")
    private String remarks;

    public UserWalletSerial(Long id, Long typeId, Long userId, Long userWalletId, Long orderType, Long orderId, String orderNo, Integer amount, String serialType, String serialProof, String description, Long creatorType, Long creatorId, String status, Date gmtCreate, Date gmtModified, String remarks) {
        this.id = id;
        this.typeId = typeId;
        this.userId = userId;
        this.userWalletId = userWalletId;
        this.orderType = orderType;
        this.orderId = orderId;
        this.orderNo = orderNo;
        this.amount = amount;
        this.serialType = serialType;
        this.serialProof = serialProof;
        this.description = description;
        this.creatorType = creatorType;
        this.creatorId = creatorId;
        this.status = status;
        this.gmtCreate = gmtCreate;
        this.gmtModified = gmtModified;
        this.remarks = remarks;
    }

    public UserWalletSerial() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserWalletId() {
        return userWalletId;
    }

    public void setUserWalletId(Long userWalletId) {
        this.userWalletId = userWalletId;
    }

    public Long getOrderType() {
        return orderType;
    }

    public void setOrderType(Long orderType) {
        this.orderType = orderType;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getSerialType() {
        return serialType;
    }

    public void setSerialType(String serialType) {
        this.serialType = serialType;
    }

    public String getSerialProof() {
        return serialProof;
    }

    public void setSerialProof(String serialProof) {
        this.serialProof = serialProof;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCreatorType() {
        return creatorType;
    }

    public void setCreatorType(Long creatorType) {
        this.creatorType = creatorType;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
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