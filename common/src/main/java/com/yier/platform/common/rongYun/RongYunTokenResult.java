package com.yier.platform.common.rongYun;

import io.rong.models.response.TokenResult;

import java.io.Serializable;

public class RongYunTokenResult extends TokenResult  implements Serializable {
    public RongYunTokenResult(Integer code, String token, String userId, String errorMessage) {
        super(code, token, userId, errorMessage);
    }
    public RongYunTokenResult(TokenResult result){
        this(result.getCode(),result.getToken(),result.getUserId(),result.getMsg());
    }
}
