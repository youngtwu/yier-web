package com.yier.platform.common.jsonResponse;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.yier.platform.common.exception.Constants;

public class JsonResponse extends BaseJsonObject implements IJsonResponse {
    private static final long serialVersionUID = -1077882582233786918L;

    protected int status = Constants.RESPONSE_CODE_SUCCESS; //标志状态

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    protected String error = "";//错误信息
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    protected String otherInfo = "";//其他信息
    @Override
    public int getStatus() {
        return status;
    }

    @Override
    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String getError() {
        return error;
    }

    @Override
    public void setError(String error) {
        this.error = error;
    }
    public String getOtherInfo() {
        return otherInfo;
    }

    public void setOtherInfo(String otherInfo) {
        this.otherInfo = otherInfo;
    }
}
