package com.yier.platform.common.po;

import com.yier.platform.common.jsonResponse.BaseJsonObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "职业点信息")
public class PracticePoint   extends BaseJsonObject {
    @ApiModelProperty(value = "省份Id")
    private String provinceId;
    @ApiModelProperty(value = "省份名字")
    private String provinceName = "";
    @ApiModelProperty(value = "城市ID")
    private String cityId;
    @ApiModelProperty(value = "城市名字")
    private String cityName = "";
    @ApiModelProperty(value = "医院ID")
    private Long hospitalId = 0L;
    @ApiModelProperty(value = "医院名字")
    private String hospitalName = "";
    @ApiModelProperty(value = "科室大分类ID")
    private Long catalogId = 0L;
    @ApiModelProperty(value = "科室大分类")
    private String catalogName ="";
    @ApiModelProperty(value = "科室小分类id")
    private Long hospitalDepartmentId = 0L;
    @ApiModelProperty(value = "科室小分类")
    private String hospitalDepartmentName = "";

    public PracticePoint(){}

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Long getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(Long hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public Long getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(Long catalogId) {
        this.catalogId = catalogId;
    }

    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public Long getHospitalDepartmentId() {
        return hospitalDepartmentId;
    }

    public void setHospitalDepartmentId(Long hospitalDepartmentId) {
        this.hospitalDepartmentId = hospitalDepartmentId;
    }

    public String getHospitalDepartmentName() {
        return hospitalDepartmentName;
    }

    public void setHospitalDepartmentName(String hospitalDepartmentName) {
        this.hospitalDepartmentName = hospitalDepartmentName;
    }
}
