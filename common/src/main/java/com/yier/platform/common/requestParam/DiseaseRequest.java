package com.yier.platform.common.requestParam;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@ApiModel(value = "疾病请求类")
public class DiseaseRequest extends BaseRequest {
    @ApiModelProperty(value = "疾病Id")
    private Long diseaseId;
    @ApiModelProperty(value = "疾病大科室ID")
    private Long departmentId;
    @ApiModelProperty(value = "疾病名称")
    private String diseaseName;
    @ApiModelProperty(value = "疾病名称首字母")
    private String diseaseInitial;

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public Long getDiseaseId() {
        return diseaseId;
    }

    public void setDiseaseId(Long diseaseId) {
        this.diseaseId = diseaseId;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    public String getDiseaseInitial() {
        return diseaseInitial;
    }

    public void setDiseaseInitial(String diseaseInitial) {
        this.diseaseInitial = diseaseInitial;
    }
}
