package com.yier.platform.common.model;

import com.yier.platform.common.jsonResponse.BaseJsonObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(value = "商品处方药品表")
public class OrderDrugs extends BaseJsonObject {
    @ApiModelProperty(value = "主键标识Id")
    private Long id;
    @ApiModelProperty(value = "订单号")
    private String orderNo;
    @ApiModelProperty(value = "处方编号")
    private String prescriptionId;
    @ApiModelProperty(value = "药品ID或编号")
    private String drugId;
    @ApiModelProperty(value = "药品编码")
    private String drugCode;
    @ApiModelProperty(value = "药品名称")
    private String drugName;
    @ApiModelProperty(value = "药品图片")
    private String drugImage;
    @ApiModelProperty(value = "药品数量")
    private Integer drugCount;
    @ApiModelProperty(value = "药品数量单位")
    private String drugUnit;
    @ApiModelProperty(value = "售卖价格")
    private Integer costPrice;
    @ApiModelProperty(value = "药品生产厂家....药品选择会提供")
    private String productionCompanyName;
    @ApiModelProperty(value = "药品规格....药品选择会提供")
    private String drugSpec;
    @ApiModelProperty(value = "剂型代码....药品选择会提供")
    private String formCode;
    @ApiModelProperty(value = "总剂量....药品选择会提供")
    private String totalDose;
    @ApiModelProperty(value = "用药途径代码  == 服药类型方式  口服")
    private String routeCode;
    @ApiModelProperty(value = "用药剂量   === 每次数量 each_have")
    private String dose;
    @ApiModelProperty(value = "剂量单位 === 每次单位 each_have_unit")
    private String doseUnit;
    @ApiModelProperty(value = "用药频率 === 每日几次 count_pre_day 每日几")
    private String rate;
    @ApiModelProperty(value = "频率单位 每日3次 ~~ 中的 次/日 ")
    private String rateUnit;
    @ApiModelProperty(value = "超额说明")
    private String exceedQuota;
    @ApiModelProperty(value = "状态")
    private String status;
    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;
    @ApiModelProperty(value = "修改时间")
    private Date gmtModified;
    @ApiModelProperty(value = "备注信息")
    private String remarks;
    @ApiModelProperty(value = "药品库存量")
    private Integer drugStock;//药品库存量
    @ApiModelProperty(value = "药房Id")
    private String pharmacyId = "";

    public String getPharmacyId() {
        return pharmacyId;
    }

    public void setPharmacyId(String pharmacyId) {
        this.pharmacyId = pharmacyId;
    }

    public OrderDrugs(Long id, String orderNo, String prescriptionId, String drugId, String drugCode, String drugName, String drugImage, Integer drugCount, String drugUnit, Integer costPrice, String productionCompanyName, String drugSpec, String formCode, String totalDose, String routeCode, String dose, String doseUnit, String rate, String rateUnit, String exceedQuota, String status, Date gmtCreate, Date gmtModified, String remarks) {
        this.id = id;
        this.orderNo = orderNo;
        this.prescriptionId = prescriptionId;
        this.drugId = drugId;
        this.drugCode = drugCode;
        this.drugName = drugName;
        this.drugImage = drugImage;
        this.drugCount = drugCount;
        this.drugUnit = drugUnit;
        this.costPrice = costPrice;
        this.productionCompanyName = productionCompanyName;
        this.drugSpec = drugSpec;
        this.formCode = formCode;
        this.totalDose = totalDose;
        this.routeCode = routeCode;
        this.dose = dose;
        this.doseUnit = doseUnit;
        this.rate = rate;
        this.rateUnit = rateUnit;
        this.exceedQuota = exceedQuota;
        this.status = status;
        this.gmtCreate = gmtCreate;
        this.gmtModified = gmtModified;
        this.remarks = remarks;
    }

    public OrderDrugs() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(String prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public String getDrugId() {
        return drugId;
    }

    public void setDrugId(String drugId) {
        this.drugId = drugId;
    }

    public String getDrugCode() {
        return drugCode;
    }

    public void setDrugCode(String drugCode) {
        this.drugCode = drugCode;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getDrugImage() {
        return drugImage;
    }

    public void setDrugImage(String drugImage) {
        this.drugImage = drugImage;
    }

    public Integer getDrugCount() {
        return drugCount;
    }

    public void setDrugCount(Integer drugCount) {
        this.drugCount = drugCount;
    }

    public String getDrugUnit() {
        return drugUnit;
    }

    public void setDrugUnit(String drugUnit) {
        this.drugUnit = drugUnit;
    }

    public Integer getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(Integer costPrice) {
        this.costPrice = costPrice;
    }

    public String getProductionCompanyName() {
        return productionCompanyName;
    }

    public void setProductionCompanyName(String productionCompanyName) {
        this.productionCompanyName = productionCompanyName;
    }

    public String getDrugSpec() {
        return drugSpec;
    }

    public void setDrugSpec(String drugSpec) {
        this.drugSpec = drugSpec;
    }

    public String getFormCode() {
        return formCode;
    }

    public void setFormCode(String formCode) {
        this.formCode = formCode;
    }

    public String getTotalDose() {
        return totalDose;
    }

    public void setTotalDose(String totalDose) {
        this.totalDose = totalDose;
    }

    public String getRouteCode() {
        return routeCode;
    }

    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode;
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    public String getDoseUnit() {
        return doseUnit;
    }

    public void setDoseUnit(String doseUnit) {
        this.doseUnit = doseUnit;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getRateUnit() {
        return rateUnit;
    }

    public void setRateUnit(String rateUnit) {
        this.rateUnit = rateUnit;
    }

    public String getExceedQuota() {
        return exceedQuota;
    }

    public void setExceedQuota(String exceedQuota) {
        this.exceedQuota = exceedQuota;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getDrugStock() {
        return drugStock;
    }

    public void setDrugStock(Integer drugStock) {
        this.drugStock = drugStock;
    }
}