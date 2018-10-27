package com.yier.platform.common.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

@ApiModel(value = "医师评价扩展类")
public class PharmacistEvaluationPo extends PharmacistEvaluation {
    @ApiModelProperty(value = "患者姓名")
    private String patientName = "";
    @ApiModelProperty(value = "患者相关图片")
    private String patientAvatarUrl = "";
    @ApiModelProperty(value = "药师信息")
    private PharmacistPo doctorPo;

    public String getPatientName() {
        if(StringUtils.isNotBlank(this.patientName)){
            String nameInfo = this.patientName.trim();
            this.patientName = nameInfo.replaceAll("([\\u4e00-\\u9fa5]{1})(.*)", "$1" + createAsterisk(nameInfo.length() - 1));
        }
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientAvatarUrl() {
        return patientAvatarUrl;
    }

    public void setPatientAvatarUrl(String patientAvatarUrl) {
        this.patientAvatarUrl = patientAvatarUrl;
    }

    public PharmacistPo getDoctorPo() {
        return doctorPo;
    }

    public void setDoctorPo(PharmacistPo doctorPo) {
        this.doctorPo = doctorPo;
    }

    //生成很多个*号
    public String createAsterisk(int length) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < length; i++) {
            stringBuffer.append("*");
        }
        return stringBuffer.toString();
    }
}
