package com.yier.platform.common.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "医生或药师执业点信息扩展类")
public class PracticePointPo extends DoctorPracticePoint {
    @ApiModelProperty(value = "省份名字")
    private String provinceName = "";
    @ApiModelProperty(value = "城市名字")
    private String cityName = "";
    @ApiModelProperty(value = "县市名字")
    private String areaName = "";
    @ApiModelProperty(value = "医院名字")
    private String hospitalName = "";
    @ApiModelProperty(value = "科室大分类")
    private String catalogName ="";
    @ApiModelProperty(value = "科室小分类")
    private String hospitalDepartmentName = "";
    @ApiModelProperty(value = "医生名字")
    private String doctorName = "";

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public String getHospitalDepartmentName() {
        return hospitalDepartmentName;
    }

    public void setHospitalDepartmentName(String hospitalDepartmentName) {
        this.hospitalDepartmentName = hospitalDepartmentName;
    }
}
