package com.yier.platform.common.model;

import com.yier.platform.common.jsonResponse.BaseJsonObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(value = "客户取药-处方关系表")
public class DrugTakePrescription extends BaseJsonObject {
    @ApiModelProperty(value = "主键标识Id")
    private Long id;
    @ApiModelProperty(value = "处方编号")
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
    @ApiModelProperty(value = "药房名字 t_patient.true_name")
    private String pharmacyName;
    @ApiModelProperty(value = "药房名字 地址")
    private String pharmacyAddress;
    @ApiModelProperty(value = "联系电话")
    private String pharmacyContact;
    @ApiModelProperty(value = "状态 1表示变更")
    private String status;
    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;
    @ApiModelProperty(value = "修改时间")
    private Date gmtModified;
    @ApiModelProperty(value = "备注信息")
    private String remarks;

    public DrugTakePrescription(Long id, String prescriptionId, String takeOrderNo, Long medicalPrescriptionId, Long medicalOrderId, String orderNo, String pharmacyId, String pharmacyName, String pharmacyAddress, String pharmacyContact, String status, Date gmtCreate, Date gmtModified, String remarks) {
        this.id = id;
        this.prescriptionId = prescriptionId;
        this.takeOrderNo = takeOrderNo;
        this.medicalPrescriptionId = medicalPrescriptionId;
        this.medicalOrderId = medicalOrderId;
        this.orderNo = orderNo;
        this.pharmacyId = pharmacyId;
        this.pharmacyName = pharmacyName;
        this.pharmacyAddress = pharmacyAddress;
        this.pharmacyContact = pharmacyContact;
        this.status = status;
        this.gmtCreate = gmtCreate;
        this.gmtModified = gmtModified;
        this.remarks = remarks;
    }

    public DrugTakePrescription() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getPharmacyName() {
        return pharmacyName;
    }

    public void setPharmacyName(String pharmacyName) {
        this.pharmacyName = pharmacyName;
    }

    public String getPharmacyAddress() {
        return pharmacyAddress;
    }

    public void setPharmacyAddress(String pharmacyAddress) {
        this.pharmacyAddress = pharmacyAddress;
    }

    public String getPharmacyContact() {
        return pharmacyContact;
    }

    public void setPharmacyContact(String pharmacyContact) {
        this.pharmacyContact = pharmacyContact;
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