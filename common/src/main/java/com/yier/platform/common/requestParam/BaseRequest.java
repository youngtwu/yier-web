package com.yier.platform.common.requestParam;


import com.yier.platform.common.jsonResponse.BaseJsonObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 分页查询请求参数
 */
@ApiModel(value = "分页查询请求参数类，参数为空null或者不传递，不考虑此过滤条件")
public class BaseRequest extends BaseJsonObject {
    private static final Logger logger = LoggerFactory.getLogger(BaseRequest.class);
    private static final long serialVersionUID = 1245593588276488150L;
    @ApiModelProperty(value = "搜索关键词")
    private String searchKey = "";
    @ApiModelProperty(value = "状态有效等")
    private String status;
    @ApiModelProperty(value = "开始页面，存在关系 start=(page-1)*size ")
    private Integer page;
    @ApiModelProperty(value = "开始  起始点")
    private Integer start;
    @ApiModelProperty(value = "查询最大数量")
    private Integer size;
    @ApiModelProperty(value = "排序的字段")
    private String sort;
    @ApiModelProperty(value = "升序或降序 asc ,or desc")
    private String order;

    public BaseRequest(){

    }
    public BaseRequest(BaseRequest br){
        this.searchKey = br.searchKey;
        this.status = br.status;
        this.page = br.page;
        this.start = br.start;
        this.size = br.size;
        this.sort = br.sort;
        this.order = br.order;
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getSort() {
        return sort;
    }

    public void doWithSortOrStart(){
        //logger.info("sort______________前_______________>"+sort);
        if(StringUtils.isNotBlank(this.sort) && !StringUtils.startsWith(this.sort,"t.")){
            this.sort = "t." +  this.sort;
        }
        //logger.info("sort_______________后______________>"+sort);
        //logger.info("start______________前_______________>"+start);
        if(this.start == null && this.page !=null){
            if(this.size !=null){
                this.start = (this.page.intValue() - 1) * this.size.intValue();
            }
        }
        //logger.info("start______________后_______________>"+start);
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
