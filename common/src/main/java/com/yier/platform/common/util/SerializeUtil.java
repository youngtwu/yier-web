package com.yier.platform.common.util;

import com.yier.platform.common.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class SerializeUtil {
    private static final Logger logger = LoggerFactory.getLogger(SerializeUtil.class);

    private static SerializeUtil instance = null;

    public static SerializeUtil getInstance() {
        if (instance == null) {
            instance = new SerializeUtil();
        }
        return instance;
    }

    public static byte[] serialize(Object object) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            // 序列化
            oos.writeObject(object);
            byte[] bytes = baos.toByteArray();
            return bytes;
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public static Object unserialize(byte[] bytes) {
        try (InputStream bais = new ByteArrayInputStream(bytes)) {
            // 反序列化
            ObjectInputStream ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }


//	public static void main(String[] args) {
//		DepartmentPo dep = new DepartmentPo();
//		dep.setId(123);
//		Map<String, String> map =  SerializeUtil.getInstance().objectToMap(dep);
//		Iterator<String> iter = map.keySet().iterator();
//		while(iter.hasNext()) {
//			String key = iter.next();
//			String obj = map.get(key);
//			System.out.println("key = " + key + ", obj = " + obj);
//		}
//	}
}