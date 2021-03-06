package com.yier.platform.common.model;

import com.yier.platform.common.jsonResponse.BaseJsonObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

@ApiModel(value = "客户预约送药订单表")
public class DrugSendOrder extends BaseJsonObject {
    private Long id;

    private String sendOrderNo;

    private String orderNo;

    private String prescriptionId;
    @ApiModelProperty(value = "处方编号id列表 方便批量删除或查询关联修改 格式是: 1,2,333,")
    private String medicalPrescriptionId;

    private Long patientId;

    private String patientIdCardNo;

    private String patientName;

    private String patientContact;

    private String patientAddressSummary;

    private String patientAddress;

    private String patientSex;

    private String patientYearOld;

    private Date planTime;

    private Integer payment;

    private Integer shipPayment;

    private String remind;

    private String other;

    private Date overTime;

    private String overType;

    private String sendStatus;

    private String status;

    private Date gmtCreate;

    private Date gmtModified;

    private String remarks;
    @ApiModelProperty(value = "处方编号列表")
    private List<String > prescriptionIdList;
    @ApiModelProperty(value = "查询对应信息,比如含有处方2,的处方id订单")
    private String searchKey;
    @ApiModelProperty(value = "查询搜索 状态信息,符合条件的将变成有效信息")
    private String sendStatusChanging;

    public String getSendStatusChanging() {
        return sendStatusChanging;
    }

    public void setSendStatusChanging(String sendStatusChanging) {
        this.sendStatusChanging = sendStatusChanging;
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }
    public List<String> getPrescriptionIdList() {
        return prescriptionIdList;
    }

    public void setPrescriptionIdList(List<String> prescriptionIdList) {
        this.prescriptionIdList = prescriptionIdList;
    }

    public DrugSendOrder(Long id, String sendOrderNo, String orderNo, String prescriptionId, String medicalPrescriptionId, Long patientId, String patientIdCardNo, String patientName, String patientContact, String patientAddressSummary, String patientAddress, String patientSex, String patientYearOld, Date planTime, Integer payment, Integer shipPayment, String remind, String other, Date overTime, String overType, String sendStatus, String status, Date gmtCreate, Date gmtModified, String remarks) {
        this.id = id;
        this.sendOrderNo = sendOrderNo;
        this.orderNo = orderNo;
        this.prescriptionId = prescriptionId;
        this.medicalPrescriptionId = medicalPrescriptionId;
        this.patientId = patientId;
        this.patientIdCardNo = patientIdCardNo;
        this.patientName = patientName;
        this.patientContact = patientContact;
        this.patientAddressSummary = patientAddressSummary;
        this.patientAddress = patientAddress;
        this.patientSex = patientSex;
        this.patientYearOld = patientYearOld;
        this.planTime = planTime;
        this.payment = payment;
        this.shipPayment = shipPayment;
        this.remind = remind;
        this.other = other;
        this.overTime = overTime;
        this.overType = overType;
        this.sendStatus = sendStatus;
        this.status = status;
        this.gmtCreate = gmtCreate;
        this.gmtModified = gmtModified;
        this.remarks = remarks;
    }

    public DrugSendOrder() {
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

    public String getMedicalPrescriptionId() {
        return medicalPrescriptionId;
    }

    public void setMedicalPrescriptionId(String medicalPrescriptionId) {
        this.medicalPrescriptionId = medicalPrescriptionId;
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

    public String getPatientContact() {
        return patientContact;
    }

    public void setPatientContact(String patientContact) {
        this.patientContact = patientContact;
    }

    public String getPatientAddressSummary() {
        return patientAddressSummary;
    }

    public void setPatientAddressSummary(String patientAddressSummary) {
        this.patientAddressSummary = patientAddressSummary;
    }

    public String getPatientAddress() {
        return patientAddress;
    }

    public void setPatientAddress(String patientAddress) {
        this.patientAddress = patientAddress;
    }

    public String getPatientSex() {
        return patientSex;
    }

    public void setPatientSex(String patientSex) {
        this.patientSex = patientSex;
    }

    public String getPatientYearOld() {
        return patientYearOld;
    }

    public void setPatientYearOld(String patientYearOld) {
        this.patientYearOld = patientYearOld;
    }

    public Date getPlanTime() {
        return planTime;
    }

    public void setPlanTime(Date planTime) {
        this.planTime = planTime;
    }

    public Integer getPayment() {
        return payment;
    }

    public void setPayment(Integer payment) {
        this.payment = payment;
    }

    public Integer getShipPayment() {
        return shipPayment;
    }

    public void setShipPayment(Integer shipPayment) {
        this.shipPayment = shipPayment;
    }

    public String getRemind() {
        return remind;
    }

    public void setRemind(String remind) {
        this.remind = remind;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public Date getOverTime() {
        return overTime;
    }

    public void setOverTime(Date overTime) {
        this.overTime = overTime;
    }

    public String getOverType() {
        return overType;
    }

    public void setOverType(String overType) {
        this.overType = overType;
    }

    public String getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(String sendStatus) {
        this.sendStatus = sendStatus;
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