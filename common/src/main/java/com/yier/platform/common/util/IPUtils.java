package com.yier.platform.common.util;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IPUtils {
	private static final Logger logger = LoggerFactory.getLogger(IPUtils.class);

	private static final List<String> IP_HEADER_LIST = Arrays.asList("X-Forwarded-For", "Proxy-Client-IP",
			"WL-Proxy-Client-IP", "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR");

	private IPUtils() {

	}


	/**
	 * 获得用户远程地址
	 */
	public static String getRemoteAddr(HttpServletRequest request){
		String remoteAddr = request.getHeader("X-Real-IP");
		if (StringUtils.isNotBlank(remoteAddr)) {
			remoteAddr = request.getHeader("X-Forwarded-For");
		}else if (StringUtils.isNotBlank(remoteAddr)) {
			remoteAddr = request.getHeader("Proxy-Client-IP");
		}else if (StringUtils.isNotBlank(remoteAddr)) {
			remoteAddr = request.getHeader("WL-Proxy-Client-IP");
		}
		return remoteAddr != null ? remoteAddr : request.getRemoteAddr();
	}

	/**
	 * 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址;
	 *
	 * @param request
	 * @return
	 */
	public static String getIpAddress(HttpServletRequest request) {
		// 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址
		

		String ip = "";
		Iterator<String> iterHeader = IPUtils.IP_HEADER_LIST.iterator();
		while (iterHeader.hasNext()) {
			String header = iterHeader.next();
			ip = request.getHeader(header);
			if (StringUtils.isNotBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
				logger.debug("{} = {}", header, ip);
				break;
			}
		}
		if (StringUtils.isBlank(ip)) {
			ip = request.getRemoteAddr();
			logger.debug("remote addr = {}", ip);
		} else {
			String[] toks = StringUtils.split(ip, ",");
			for (int index = 0; index < toks.length && toks.length > 1; index++) {
				String strIp = toks[index];
				if (!"unknown".equalsIgnoreCase(strIp)) {
					ip = strIp;
					break;
				}
			}
		}
		if (StringUtils.isBlank(ip)) {
			logger.error("无法获取请求方的IP");
		}
		return ip;
	}

    /**
     * 获取报文头，没有获取的话，默认赋值
	 * @param request
     * @param headerKey
     * @param defaultValue
     * @return
     */
	public static String getHeaderValueByKey(HttpServletRequest request, String headerKey, String defaultValue) {
		String result = request.getHeader(headerKey);
		if(StringUtils.isBlank(result)){
			result = defaultValue;
		}
		return result;
	}


	/**
	 * 百度普通ip定位
	 * @param url
	 * @return
	 * @throws IOException
	 * @throws JSONException
	 */
	public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
		InputStream is = new URL(url).openStream();
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			StringBuilder sb = new StringBuilder();
			int cp;
			while ((cp = rd.read()) != -1) {
				sb.append((char) cp);
			}
			String jsonText = sb.toString();
			JSONObject json = JSONObject.parseObject(jsonText);
			//JSONObject json = new JSONObject(jsonText);
			return json;
		} finally {
			is.close();
			// System.out.println("同时 从这里也能看出 即便return了，仍然会执行finally的！");
		}
	}

	/**
	 *获取外网本机的IP地址的方法
	 * @return
	 */
	public static String getV4IP(){
		String ip = "";
		String chinaz = "http://ip.chinaz.com";

		StringBuilder inputLine = new StringBuilder();
		String read = "";
		URL url = null;
		HttpURLConnection urlConnection = null;
		BufferedReader in = null;
		try {
			url = new URL(chinaz);
			urlConnection = (HttpURLConnection) url.openConnection();
			in = new BufferedReader( new InputStreamReader(urlConnection.getInputStream(),"UTF-8"));
			while((read=in.readLine())!=null){
				inputLine.append(read+"\r\n");
			}
			//System.out.println(inputLine.toString());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(in!=null){
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		Pattern p = Pattern.compile("\\<dd class\\=\"fz24\">(.*?)\\<\\/dd>");
		Matcher m = p.matcher(inputLine.toString());
		if(m.find()){
			String ipstr = m.group(1);
			ip = ipstr;
			//System.out.println(ipstr);
		}
		return ip;
	}
}
