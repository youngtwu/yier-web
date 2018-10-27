package com.yier.platform.common.po;

import com.yier.platform.common.jsonResponse.BaseJsonObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "疾病扩展类")
public class DiseasePo extends BaseJsonObject {
    @ApiModelProperty(value = "主键ID")
    private Long id;
    @ApiModelProperty(value = "疾病名称")
    private String name;
    @ApiModelProperty(value = "药品数量")
    private int drugCount;

    public int getDrugCount() {
        return drugCount;
    }

    public void setDrugCount(int drugCount) {
        this.drugCount = drugCount;
    }

    public DiseasePo(){}
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
