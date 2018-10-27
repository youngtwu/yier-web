package com.yier.platform.common.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Lists;
import com.yier.platform.common.po.Image;
import com.yier.platform.common.util.JsonUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@ApiModel(value = "个人消息扩展类")
public class MessagesPersonPo extends MessagesPerson {
    @ApiModelProperty(value = "图片集合")
    private List<Image> imagesList = Lists.newArrayList() ;
    @ApiModelProperty(value = "图片总数")
    private int total ;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Image> getImagesList() {
        String images = this.getImages();//json存储的图片信息，转换成列表对象 ，如果对象Image存在构造函数，一定要有可
        if(StringUtils.isNotBlank(images)){
            List<Image> lists = JsonUtils.fromJson(images, new TypeReference<List<Image>>() {
            });
            if (lists == null){
            }
            else{
                imagesList = lists;
            }
        }
        return imagesList;
    }

    public void setImagesList(List<Image> imagesList) {
        this.imagesList = imagesList;
    }

}
