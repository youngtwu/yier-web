package com.yier.platform.common.requestParam;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "取药查询请求类")
public class DrugTakeOrderRequest extends BaseRequest {
    @ApiModelProperty(value = "取药订单号Id")
    private Long drugTakeOrderId;
    @ApiModelProperty(value = "处方编号,可能会是多个")
    private String prescriptionId;
    @ApiModelProperty(value = "取药订单号")
    private String takeOrderNo;
    @ApiModelProperty(value = "处方ID")
    private Long medicalPrescriptionId;
    @ApiModelProperty(value = "订单号ID")
    private Long medicalOrderId;
    @ApiModelProperty(value = "订单号")
    private String orderNo;
    @ApiModelProperty(value = "药房 药库ID,关联t_pharmacy.id可能是第三方")
    private String pharmacyId;

    @ApiModelProperty(value = "患者ID,关联t_patient.id")
    private Long patientId;
    @ApiModelProperty(value = "取药状态")
    private String takeStatus;

    public String getTakeStatus() {
        return takeStatus;
    }

    public void setTakeStatus(String takeStatus) {
        this.takeStatus = takeStatus;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Long getDrugTakeOrderId() {
        return drugTakeOrderId;
    }

    public void setDrugTakeOrderId(Long drugTakeOrderId) {
        this.drugTakeOrderId = drugTakeOrderId;
    }

    public String getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(String prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public String getTakeOrderNo() {
        return takeOrderNo;
    }

    public void setTakeOrderNo(String takeOrderNo) {
        this.takeOrderNo = takeOrderNo;
    }

    public Long getMedicalPrescriptionId() {
        return medicalPrescriptionId;
    }

    public void setMedicalPrescriptionId(Long medicalPrescriptionId) {
        this.medicalPrescriptionId = medicalPrescriptionId;
    }

    public Long getMedicalOrderId() {
        return medicalOrderId;
    }

    public void setMedicalOrderId(Long medicalOrderId) {
        this.medicalOrderId = medicalOrderId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getPharmacyId() {
        return pharmacyId;
    }

    public void setPharmacyId(String pharmacyId) {
        this.pharmacyId = pharmacyId;
    }
}
