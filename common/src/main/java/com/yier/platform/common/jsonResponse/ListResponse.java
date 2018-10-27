package com.yier.platform.common.jsonResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ListResponse <T extends Serializable> extends JsonResponse{
    private static final long serialVersionUID = -3589086597069231542L;
    private long total;//总数据条数
    private List<T> data;//返回结果的数据部分

    public ListResponse() {
        this.data = new ArrayList<>();
        this.total = 0L;
    }

    public ListResponse(List<T> data) {
        this.data = data;
        this.total = this.data.size();
    }

    public ListResponse(List<T> data, long total) {
        this.data = data;
        this.total = total;
    }

    public long getTotal() {
        return total;
    }

    public ListResponse<T> setTotal(long total) {
        this.total = total;
        return this;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
