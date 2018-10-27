package com.yier.platform.common.requestParam;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(value = "上门就诊订单查询请求类")
public class OrderDrugsRequest extends BaseRequest {
    @ApiModelProperty(value = "上门就诊药品ID")
    private Long orderDrugsId;
    @ApiModelProperty(value = "订单号")
    private String orderNo;
    @ApiModelProperty(value = "处方编号")
    private String prescriptionId;
    @ApiModelProperty(value = "药品ID")
    private Long drugId;
    @ApiModelProperty(value = "药品编码")
    private String drugCode;
    @ApiModelProperty(value = "药品通用名字")
    private String drugName;
    @ApiModelProperty(value = "药房药库ID")
    private String pharmacyId;
    @ApiModelProperty(value = "处方编号列表")
    private List<String > prescriptionIdList;

    public List<String> getPrescriptionIdList() {
        return prescriptionIdList;
    }

    public void setPrescriptionIdList(List<String> prescriptionIdList) {
        this.prescriptionIdList = prescriptionIdList;
    }

    public String getPharmacyId() {
        return pharmacyId;
    }

    public void setPharmacyId(String pharmacyId) {
        this.pharmacyId = pharmacyId;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public Long getOrderDrugsId() {
        return orderDrugsId;
    }

    public void setOrderDrugsId(Long orderDrugsId) {
        this.orderDrugsId = orderDrugsId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(String prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public Long getDrugId() {
        return drugId;
    }

    public void setDrugId(Long drugId) {
        this.drugId = drugId;
    }

    public String getDrugCode() {
        return drugCode;
    }

    public void setDrugCode(String drugCode) {
        this.drugCode = drugCode;
    }
}
