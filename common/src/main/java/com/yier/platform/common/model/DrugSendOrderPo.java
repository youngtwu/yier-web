package com.yier.platform.common.model;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(value = "客户送药订单表扩展类")
public class DrugSendOrderPo extends DrugSendOrder {

    @ApiModelProperty(value = "商品处方药品集合")
    private List<OrderDrugs> drugList = Lists.newArrayList();


    public List<OrderDrugs> getDrugList() {
        return drugList;
    }

    public void setDrugList(List<OrderDrugs> drugList) {
        this.drugList = drugList;
    }

}
