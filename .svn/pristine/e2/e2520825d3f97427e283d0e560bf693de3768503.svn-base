package com.yier.platform.common.model;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(value = "药房药库信息扩展类")
public class PharmacyPo extends Pharmacy {
    @ApiModelProperty(value = "药房药店的相关图片描述")
    private List<HospitalImages> imagesList = Lists.newArrayList();
    @ApiModelProperty(value = "药房药店与基础点的距离")
    private Double distance;
    @ApiModelProperty(value = "药房药店与基础点的距离显示")
    private String distanceDisplay = "";
    @ApiModelProperty(value = "药房药店访问量显示")
    private String visitDisplay = "";

    public List<HospitalImages> getImagesList() {
        return imagesList;
    }

    public void setImagesList(List<HospitalImages> imagesList) {
        this.imagesList = imagesList;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public String getDistanceDisplay() {
        if (distance == null || distance.doubleValue() == 0) {
            this.distanceDisplay = "";
        } else {
            Double result = distance / 1000;
            this.distanceDisplay = result.longValue() + " 公里";
        }
        return distanceDisplay;
    }

    public void setDistanceDisplay(String distanceDisplay) {
        this.distanceDisplay = distanceDisplay;
    }

    public String getVisitDisplay() {
        if(this.visitDisplay == null || "".equals(this.visitDisplay)){
            this.visitDisplay = "";
        } else{
            this.visitDisplay = this.getVisit().intValue() + "";
        }
        return visitDisplay;
    }

    public void setVisitDisplay(String visitDisplay) {
        this.visitDisplay = visitDisplay;
    }
}
