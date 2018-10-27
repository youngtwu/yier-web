package com.yier.platform.common.model;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(value = "客户取药订单扩展类")
public class DrugTakeOrderPo extends DrugTakeOrder {
    @ApiModelProperty(value = "商品处方药品集合")
    private List<OrderDrugs> drugList = Lists.newArrayList();
    private List<DrugTakePrescription> drugTakePrescriptionList = Lists.newArrayList();

    public List<DrugTakePrescription> getDrugTakePrescriptionList() {
        return drugTakePrescriptionList;
    }

    public void setDrugTakePrescriptionList(List<DrugTakePrescription> drugTakePrescriptionList) {
        this.drugTakePrescriptionList = drugTakePrescriptionList;
    }

    public List<OrderDrugs> getDrugList() {
        return drugList;
    }
    public void setDrugList(List<OrderDrugs> drugList) {
        this.drugList = drugList;
    }

}
