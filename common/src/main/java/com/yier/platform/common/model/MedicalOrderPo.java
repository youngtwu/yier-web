package com.yier.platform.common.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "上门就诊扩展类")
public class MedicalOrderPo extends MedicalOrder {
//    private PatientPo patientPo;
    @ApiModelProperty(value = "医生信息扩展类")
    private DoctorPo doctorPo;
    @ApiModelProperty(value = "就诊预约人数")
    private String appointmentInfo;

    public String getAppointmentInfo() {
        return appointmentInfo;
    }

    public void setAppointmentInfo(String appointmentInfo) {
        this.appointmentInfo = appointmentInfo;
    }

/*
    public PatientPo getPatientPo() {
        return patientPo;
    }

    public void setPatientPo(PatientPo patientPo) {
        this.patientPo = patientPo;
    }
*/

    public DoctorPo getDoctorPo() {
        return doctorPo;
    }

    public void setDoctorPo(DoctorPo doctorPo) {
        this.doctorPo = doctorPo;
    }
}
