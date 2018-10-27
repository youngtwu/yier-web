package com.yier.platform.service;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.yier.platform.TestBase;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;

public class GetLocationForIPAddressTest extends TestBase {
    private static final Logger logger = LoggerFactory.getLogger(GetLocationForIPAddressTest.class);

    @Test
    public void test() {
        //这里调用百度的ip定位api服务 详见 http://api.map.baidu.com/lbsapi/cloud/ip-location-api.htm
        JSONObject json = null;
        try {
            json = readJsonFromUrl("http://api.map.baidu.com/location/ip?ak=lhoU6vS5Beh2x0gUUtXc0KFIZuBoHieQ&ip=114.95.120.154");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(json.toString());
        System.out.println(((JSONObject) json.get("content")).get("address"));
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = JSONObject.parseObject(jsonText);
            //JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
            // System.out.println("同时 从这里也能看出 即便return了，仍然会执行finally的！");
        }
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }
}
