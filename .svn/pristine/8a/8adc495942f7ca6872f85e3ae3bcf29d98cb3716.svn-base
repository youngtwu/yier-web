package com.yier.platform.common.typeEnum;

import org.apache.commons.lang3.StringUtils;

/**
 * @author bob
 */
public enum SystemParameterKey {

	APP_PATIENT_USER_RULER("APP_PATIENT_USER_RULER", "患者端用户手册"),

	APP_PATIENT_BANNER_IMAGES_JSON("APP_PATIENT_BANNER_IMAGES_JSON", "患者端通栏头号标题信息列表"),

	APP_PATIENT_AD_IMAGES_JSON("APP_PATIENT_AD_IMAGES_JSON", "APP患者端广告信息列表"),

	APP_DOCTOR_USER_RULER("APP_DOCTOR_USER_RULER", "医生端用户手册"),

	APP_DOCTOR_BANNER_IMAGES_JSON("APP_DOCTOR_BANNER_IMAGES_JSON", "医生端通栏头号标题信息列表"),

	APP_DOCTOR_AD_IMAGES_JSON("APP_DOCTOR_AD_IMAGES_JSON", "APP医生端广告信息列表"),

	APP_PHARMACIST_USER_RULER("APP_PHARMACIST_USER_RULER", "药师端用户手册"),

	APP_PHARMACIST_BANNER_IMAGES_JSON("APP_PHARMACIST_BANNER_IMAGES_JSON", "药师端通栏头号标题信息列表"),

	APP_PHARMACIST_AD_IMAGES_JSON("APP_PHARMACIST_AD_IMAGES_JSON", "APP药师端广告信息列表"),


	APP_API_AROUND_MIN_CHECK("APP_API_AROUND_MIN_CHECK", "接口调用检测最小阀门值"),

	//患者端
	APP_CONFIG_TYPE_ID_1_NAME("APP_CONFIG_TYPE_Id_1_BANNER_NAME", "名称"),
	APP_CONFIG_TYPE_ID_1_DISCRIPTION("APP_CONFIG_TYPE_Id_1_BANNER_DISCRIPTION", "描述"),
	APP_CONFIG_TYPE_ID_1_BANNER_WIDTH("APP_CONFIG_TYPE_Id_1_BANNER_WIDTH", "首页图片宽度（像素）"),
	APP_CONFIG_TYPE_ID_1_BANNER_HEIGHT("APP_CONFIG_TYPE_Id_1_BANNER_HEIGHT", "首页图片高度（像素）"),
	APP_CONFIG_TYPE_ID_1_STARTUP_WIDTH("APP_CONFIG_TYPE_Id_1_STARTUP_WIDTH", "启动页图片宽度（像素）"),
	APP_CONFIG_TYPE_ID_1_STARTUP_HEIGHT("APP_CONFIG_TYPE_Id_1_STARTUP_HEIGHT", "启动页图片高度度（像素）"),

	//医生端
	APP_CONFIG_TYPE_ID_2_NAME("APP_CONFIG_TYPE_Id_2_BANNER_NAME", "名称"),
	APP_CONFIG_TYPE_ID_2_DISCRIPTION("APP_CONFIG_TYPE_Id_1_BANNER_DISCRIPTION", "描述"),
	APP_CONFIG_TYPE_ID_2_BANNER_WIDTH("APP_CONFIG_TYPE_Id_2_BANNER_WIDTH", "首页图片宽度（像素）"),
	APP_CONFIG_TYPE_ID_2_BANNER_HEIGHT("APP_CONFIG_TYPE_Id_2_BANNER_HEIGHT", "首页图片高度（像素）"),
	APP_CONFIG_TYPE_ID_2_STARTUP_WIDTH("APP_CONFIG_TYPE_Id_2_STARTUP_WIDTH", "启动页图片宽度（像素）"),
	APP_CONFIG_TYPE_ID_2_STARTUP_HEIGHT("APP_CONFIG_TYPE_Id_2_STARTUP_HEIGHT", "启动页图片高度度（像素）"),

	//药师端
	APP_CONFIG_TYPE_ID_3_NAME("APP_CONFIG_TYPE_Id_3_BANNER_NAME", "名称"),
	APP_CONFIG_TYPE_ID_3_DISCRIPTION("APP_CONFIG_TYPE_Id_3_BANNER_DISCRIPTION", "描述"),
	APP_CONFIG_TYPE_ID_3_BANNER_WIDTH("APP_CONFIG_TYPE_Id_3_BANNER_WIDTH", "首页图片宽度（像素）"),
	APP_CONFIG_TYPE_ID_3_BANNER_HEIGHT("APP_CONFIG_TYPE_Id_3_BANNER_HEIGHT", "首页图片高度（像素）"),
	APP_CONFIG_TYPE_ID_3_STARTUP_WIDTH("APP_CONFIG_TYPE_Id_3_STARTUP_WIDTH", "启动页图片宽度（像素）"),
	APP_CONFIG_TYPE_ID_3_STARTUP_HEIGHT("APP_CONFIG_TYPE_Id_3_STARTUP_HEIGHT", "启动页图片高度度（像素）"),
	;

	private String code;
	private String desc;

	SystemParameterKey(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public static SystemParameterKey getFromCode(String code) {
		for (SystemParameterKey key : values()) {
			if (StringUtils.equals(code, key.code)) {
				return key;
			}
		}
		return null;
	}

	/**
	 * Getter method for property <tt>desc</tt>.
	 *
	 * @return property value of desc
	 */
	public String getDesc() {
		return desc;
	}



	public String getCode() {
		return code;
	}


}
