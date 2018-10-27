package com.yier.platform.common.model;

import com.yier.platform.common.jsonResponse.BaseJsonObject;
import java.util.Date;

public class DrugAcceptPrescription extends BaseJsonObject {
    private Long id;

    private String sendOrderNo;

    private String acceptOrderNo;

    private String prescriptionId;

    private Long medicalPrescriptionId;

    private Long medicalOrderId;

    private String orderNo;

    private Long patientId;

    private String patientIdCardNo;

    private String patientName;

    private String pharmacyId;

    private String pharmacyName;

    private String pharmacyAddress;

    private String pharmacyContact;

    private String status;

    private Date gmtCreate;

    private Date gmtModified;

    private String remarks;

    public DrugAcceptPrescription(Long id, String sendOrderNo, String acceptOrderNo, String prescriptionId, Long medicalPrescriptionId, Long medicalOrderId, String orderNo, Long patientId, String patientIdCardNo, String patientName, String pharmacyId, String pharmacyName, String pharmacyAddress, String pharmacyContact, String status, Date gmtCreate, Date gmtModified, String remarks) {
        this.id = id;
        this.sendOrderNo = sendOrderNo;
        this.acceptOrderNo = acceptOrderNo;
        this.prescriptionId = prescriptionId;
        this.medicalPrescriptionId = medicalPrescriptionId;
        this.medicalOrderId = medicalOrderId;
        this.orderNo = orderNo;
        this.patientId = patientId;
        this.patientIdCardNo = patientIdCardNo;
        this.patientName = patientName;
        this.pharmacyId = pharmacyId;
        this.pharmacyName = pharmacyName;
        this.pharmacyAddress = pharmacyAddress;
        this.pharmacyContact = pharmacyContact;
        this.status = status;
        this.gmtCreate = gmtCreate;
        this.gmtModified = gmtModified;
        this.remarks = remarks;
    }

    public DrugAcceptPrescription() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSendOrderNo() {
        return sendOrderNo;
    }

    public void setSendOrderNo(String sendOrderNo) {
        this.sendOrderNo = sendOrderNo;
    }

    public String getAcceptOrderNo() {
        return acceptOrderNo;
    }

    public void setAcceptOrderNo(String acceptOrderNo) {
        this.acceptOrderNo = acceptOrderNo;
    }

    public String getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(String prescriptionId) {
        this.prescriptionId = prescriptionId;
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

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public String getPatientIdCardNo() {
        return patientIdCardNo;
    }

    public void setPatientIdCardNo(String patientIdCardNo) {
        this.patientIdCardNo = patientIdCardNo;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
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