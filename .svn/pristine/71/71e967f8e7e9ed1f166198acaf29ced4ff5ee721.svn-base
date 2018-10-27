package com.yier.platform.common.util;


import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.yier.platform.common.exception.ServiceException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

public class JsonUtils {
    private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);
    //private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);// LoggerFactory.getLogger(JsonUtils.class);

    private static ObjectMapper objectMapper = new ObjectMapper();

    private JsonUtils() {
    }

    public static <T> T fromJson(String jsonString, Class<T> clazz) {
        ObjectMapper mapper = getObjectMapper();
        try {
            //logger.info(">>>>目前数据 Json:{}",jsonString);
            return mapper.readValue(jsonString, clazz);
        } catch (IOException e) {
            //e.printStackTrace();
            logger.error(e.getMessage(), e);
            throw new ServiceException(" result can not converto to Object");
        }


    }

    public static <T> T fromJson(String json, TypeReference<T> typereference) {
        logger.debug(">>>>目前数据 Json{}",json);
        if (StringUtils.isBlank(json)) {
            return null;
        }
        ObjectMapper mapper = getObjectMapper();
        try {
            return mapper.readValue(json, typereference);
        } catch (IOException e) {
            //e.printStackTrace();
            logger.error(e.getMessage(), e);
            throw new ServiceException(" result can not converto to Object");
        }
    }

    private static ObjectMapper getObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        //mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);//这种方法的好处是不用改变要转化的类，即本例的YourClass。（如果YourClass不是你维护的，或者不可修改的，可以用这个方法）
        //@JsonIgnoreProperties(ignoreUnknown = true)  jackson库还提供了注解方法，用在class级别上
        return mapper;
    }

    public static String toJsonString(Object obj) {
        ObjectMapper mapper = getObjectMapper();

        String jsonString = "";
        try {
            jsonString = mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            //logger.err
            logger.error(e.getMessage(), e);
        }
        return jsonString;
    }

    /**
     * 将对象序列化为JSON字符串
     *
     * @param object
     * @return JSON字符串
     */
    public static String serialize(Object object) {
        Writer write = new StringWriter();
        try {
            objectMapper.writeValue(write, object);
        } catch (JsonGenerationException e) {
            logger.error("JsonGenerationException when serialize object to json", e);
        } catch (JsonMappingException e) {
            logger.error("JsonMappingException when serialize object to json", e);
        } catch (IOException e) {
            logger.error("IOException when serialize object to json", e);
        }
        return write.toString();
    }

    /**
     * 将JSON字符串反序列化为对象
     *
     * @param
     * @return JSON字符串
     */
    public static <T> T deserialize(String json, Class<T> clazz) {
        Object object = null;
        try {
            object = objectMapper.readValue(json, TypeFactory.rawClass(clazz));
        } catch (JsonParseException e) {
            logger.error("JsonParseException when serialize object to json", e);
        } catch (JsonMappingException e) {
            logger.error("JsonMappingException when serialize object to json", e);
        } catch (IOException e) {
            logger.error("IOException when serialize object to json", e);
        }
        return (T) object;
    }

    /**
     * 将JSON字符串反序列化为对象
     *
     * @param
     * @return JSON字符串
     */
    public static <T> T deserialize(String json, TypeReference<T> typeRef) {
        try {
            return (T) objectMapper.readValue(json, typeRef);
        } catch (JsonParseException e) {
            logger.error("JsonParseException when deserialize json", e);
        } catch (JsonMappingException e) {
            logger.error("JsonMappingException when deserialize json", e);
        } catch (IOException e) {
            logger.error("IOException when deserialize json", e);
        }
        return null;
    }
}
