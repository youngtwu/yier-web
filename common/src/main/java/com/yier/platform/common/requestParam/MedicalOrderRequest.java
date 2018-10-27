package com.yier.platform.common.requestParam;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

@ApiModel(value = "上门就诊订单查询请求类")
public class MedicalOrderRequest extends BaseRequest {
    @ApiModelProperty(value = "上门就诊订单ID")
    private Long medicalOrderId;
    @ApiModelProperty(value = "订单号")
    private String orderNo;
    @ApiModelProperty(value = "医院ID")
    private Long hospitalId;
    @ApiModelProperty(value = "患者ID")
    private Long patientId;
    @ApiModelProperty(value = "医生ID")
    private Long doctorId;
    @ApiModelProperty(value = "医生看到的订单状态")
    private String statusDetail;
    @ApiModelProperty(value = "状态列表")
    private List<String> statusList;
    @ApiModelProperty(value = "查询工作时间过期")
    private Date queryWorkDate;

    public Date getQueryWorkDate() {
        return queryWorkDate;
    }

    public void setQueryWorkDate(Date queryWorkDate) {
        this.queryWorkDate = queryWorkDate;
    }

    public List<String> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<String> statusList) {
        this.statusList = statusList;
    }

    public String getStatusDetail() {
        return statusDetail;
    }

    public void setStatusDetail(String statusDetail) {
        this.statusDetail = statusDetail;
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

    public Long getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(Long hospitalId) {
        this.hospitalId = hospitalId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }
}
