package com.yier.platform.common.model;

import com.yier.platform.common.jsonResponse.BaseJsonObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;


@ApiModel(value = "车辆品牌")
public class EhcCarBrand extends BaseJsonObject {


	/**
	 * 
	 */
	private static final long serialVersionUID = 5935897719268246451L;

	@ApiModelProperty(value = "品牌编码")
	private String brandCode = "";
	
	@ApiModelProperty(value = "品牌名称")
	private String brandName = "";
	
	@ApiModelProperty(value = "拼音")
	private String pinyin = "";
	
	@ApiModelProperty(value = "logo图片Url")
	private String logo = "";

	public String getBrandCode() {
		return brandCode;
	}

	public EhcCarBrand setBrandCode(String brandCode) {
		this.brandCode = brandCode;
		return this;
	}

	public String getBrandName() {
		return brandName;
	}

	public EhcCarBrand setBrandName(String brandName) {
		this.brandName = brandName;
		return this;
	}

	public String getPinyin() {
		return pinyin;
	}
	
	/**
	 * 获取拼音首字母
	 * @return
	 */
	public String getFirstPinYin() {
		if(StringUtils.length(this.pinyin) < 1) {
			return "";
		}
		else {
			return this.pinyin.substring(0, 1).toUpperCase();
		}
	}

	public EhcCarBrand setPinyin(String pinyin) {
		this.pinyin = pinyin;
		return this;
	}
	

	public String getLogo() {
		return logo;
	}

	public EhcCarBrand setLogo(String logo) {
		this.logo = logo;
		return this;
	}
}
