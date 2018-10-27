package com.yier.platform.common.jsonResponse;

import java.io.Serializable;

public class CommonResponse<T extends Serializable> extends JsonResponse {
    private static final long serialVersionUID = -6422802191790000850L;
    protected T data;

    public CommonResponse() {
        this.data = null;
    }

    public CommonResponse(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
