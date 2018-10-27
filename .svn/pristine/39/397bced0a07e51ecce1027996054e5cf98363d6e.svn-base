package com.yier.platform.common.model;

import com.yier.platform.common.jsonResponse.BaseJsonObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(value = "用户钱包")
public class UserWallet extends BaseJsonObject {
    @ApiModelProperty(value = "钱包id")
    private Long id;
    @ApiModelProperty(value = "客户端标识。1-患者端，2-医生端，3-药师端")
    private Long typeId;
    @ApiModelProperty(value = "用户ID,关联t_doctor.id，t_patient.id,t_pharmacy.id")
    private Long userId;
    @ApiModelProperty(value = "目前可以使用的金额")
    private Integer amount;
    @ApiModelProperty(value = "总获得充值金额")
    private Integer obtianAmount;
    @ApiModelProperty(value = "总消费使用金额")
    private Integer deductAmount;
    @ApiModelProperty(value = "总冻结保证金额")
    private Integer frozenAmount;
    @ApiModelProperty(value = "总解冻金额")
    private Integer thawyAmount;
    @ApiModelProperty(value = "状态")
    private String status;
    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;
    @ApiModelProperty(value = "修改时间")
    private Date gmtModified;
    @ApiModelProperty(value = "备注信息 (w.obtianAmount - w.deductAmount) - (w.frozenAmount - w.thawyAmount) AS amount 实际可用金额")
    private String remarks;

    public UserWallet(Long id, Long typeId, Long userId, Integer amount, Integer obtianAmount, Integer deductAmount, Integer frozenAmount, Integer thawyAmount, String status, Date gmtCreate, Date gmtModified, String remarks) {
        this.id = id;
        this.typeId = typeId;
        this.userId = userId;
        this.amount = amount;
        this.obtianAmount = obtianAmount;
        this.deductAmount = deductAmount;
        this.frozenAmount = frozenAmount;
        this.thawyAmount = thawyAmount;
        this.status = status;
        this.gmtCreate = gmtCreate;
        this.gmtModified = gmtModified;
        this.remarks = remarks;
    }

    public UserWallet() {
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

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getObtianAmount() {
        return obtianAmount;
    }

    public void setObtianAmount(Integer obtianAmount) {
        this.obtianAmount = obtianAmount;
    }

    public Integer getDeductAmount() {
        return deductAmount;
    }

    public void setDeductAmount(Integer deductAmount) {
        this.deductAmount = deductAmount;
    }

    public Integer getFrozenAmount() {
        return frozenAmount;
    }

    public void setFrozenAmount(Integer frozenAmount) {
        this.frozenAmount = frozenAmount;
    }

    public Integer getThawyAmount() {
        return thawyAmount;
    }

    public void setThawyAmount(Integer thawyAmount) {
        this.thawyAmount = thawyAmount;
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