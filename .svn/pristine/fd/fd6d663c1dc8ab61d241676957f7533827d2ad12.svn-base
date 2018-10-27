package com.yier.platform.common.requestParam;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "审核信息请求类")
public class AuditInfoRequest extends BaseRequest{
    @ApiModelProperty(value = "审核主键id")
    private Long auditInfoId;
    @ApiModelProperty(value = "审核类型（1医院, 2医生，3药师，4咨询，5通知，6手机号停用）")
    private Integer auditType;
    @ApiModelProperty(value = "审核表主键")
    private Long tableId;
    @ApiModelProperty(value = "提交人id")
    private Long presenterId;
    @ApiModelProperty(value = "提交人姓名")
    private String presenterName;
    @ApiModelProperty(value = "提交人手机号")
    private String presenterPhone;
    @ApiModelProperty(value = "提交人身份证号码")
    private String presenterIdCardNo;
    @ApiModelProperty(value = "审核状态（1初审 2复审 3通过 4未通过）")
    private String auditStatus;
    @ApiModelProperty(value = "处理状态（1已处理 2未处理）")
    private String dealStatus;

    public Long getAuditInfoId() {
        return auditInfoId;
    }

    public void setAuditInfoId(Long auditInfoId) {
        this.auditInfoId = auditInfoId;
    }

    public Integer getAuditType() {
        return auditType;
    }

    public void setAuditType(Integer auditType) {
        this.auditType = auditType;
    }

    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }

    public Long getPresenterId() {
        return presenterId;
    }

    public void setPresenterId(Long presenterId) {
        this.presenterId = presenterId;
    }

    public String getPresenterName() {
        return presenterName;
    }

    public void setPresenterName(String presenterName) {
        this.presenterName = presenterName;
    }

    public String getPresenterPhone() {
        return presenterPhone;
    }

    public void setPresenterPhone(String presenterPhone) {
        this.presenterPhone = presenterPhone;
    }

    public String getPresenterIdCardNo() {
        return presenterIdCardNo;
    }

    public void setPresenterIdCardNo(String presenterIdCardNo) {
        this.presenterIdCardNo = presenterIdCardNo;
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getDealStatus() {
        return dealStatus;
    }

    public void setDealStatus(String dealStatus) {
        this.dealStatus = dealStatus;
    }
}
