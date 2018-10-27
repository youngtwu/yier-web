package com.yier.platform.common.jsonResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

public abstract class BaseJsonObject implements Serializable {
    private static final long serialVersionUID = 7255183217689124288L;

    public String toJsonString() {
        String ret = "";
        ObjectMapper om = new ObjectMapper();
        try {
            ret = om.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            LoggerFactory.getLogger(BaseJsonObject.class).trace(e.getMessage(), e);

        }
        return ret;
    }


    @Override
    public String toString() {
        return this.toJsonString();
    }

}
