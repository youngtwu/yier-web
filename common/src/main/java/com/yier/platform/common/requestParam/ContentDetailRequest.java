package com.yier.platform.common.requestParam;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "内容详情请求类")
public class ContentDetailRequest extends BaseRequest {
    @ApiModelProperty(value = "内容详情Id")
    private Long contentId;
    @ApiModelProperty(value = "辅助类型标识：1表示New，2表示message，3表示personMessage")
    private Long markId;

    public Long getContentId() {
        return contentId;
    }

    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }

    public Long getMarkId() {
        return markId;
    }

    public void setMarkId(Long markId) {
        this.markId = markId;
    }
}
