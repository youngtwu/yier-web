package com.yier.platform.common.requestParam;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "药品请求类")
public class DrugRequest extends BaseRequest {
    @ApiModelProperty(value = "药品Id")
    private Long drupId;
    @ApiModelProperty(value = "药品名称")
    private String drugName;
    @ApiModelProperty(value = "疾病Id")
    private Long diseaseId;
    @ApiModelProperty(value = "药品过滤")
    private String attr;
    @ApiModelProperty(value = "药品产地")
    private String placeOfOrigin;

    public String getAttr() {
        return attr;
    }

    public void setAttr(String attr) {
        this.attr = attr;
    }

    public String getPlaceOfOrigin() {
        return placeOfOrigin;
    }

    public void setPlaceOfOrigin(String placeOfOrigin) {
        this.placeOfOrigin = placeOfOrigin;
    }

    public Long getDrupId() {
        return drupId;
    }

    public void setDrupId(Long drupId) {
        this.drupId = drupId;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public Long getDiseaseId() {
        return diseaseId;
    }

    public void setDiseaseId(Long diseaseId) {
        this.diseaseId = diseaseId;
    }
}
