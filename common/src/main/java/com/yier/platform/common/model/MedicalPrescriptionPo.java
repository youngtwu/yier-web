package com.yier.platform.common.model;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(value = "处方表扩展类")
public class MedicalPrescriptionPo extends MedicalPrescription {
    @ApiModelProperty(value = "上门就诊扩展类")
    private MedicalOrderPo medicalOrder ;

    public MedicalOrderPo getMedicalOrder() {
        return medicalOrder;
    }

    public void setMedicalOrder(MedicalOrderPo medicalOrder) {
        this.medicalOrder = medicalOrder;
    }
}
