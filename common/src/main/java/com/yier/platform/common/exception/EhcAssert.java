package com.yier.platform.common.exception;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.event.Level;
import org.springframework.util.CollectionUtils;

import java.util.Collection;

//import org.apache.logging.log4j.Level;

public class EhcAssert {

	private EhcAssert() {
		// default construct
	}

	/**
	 * 用于判断状态. 不匹配时抛出 ParkStatusException
	 */
	public static void isTrueStatus(boolean expression, String message) {
		if (!expression) {
			throw new StatusException(message);
		}
	}

	public static void isTrue(boolean expression, int code, String message) {
		if (!expression) {
			throw new ServiceException(code, message, Level.WARN);
		}
	}

	/**
	 * 判断字符段 expression 是否为空. 默认抛出参数错误(status = 10000 的错误)
	 * 
	 * @param expression
	 * @param message
	 */
	public static void notBlank(String expression, String message) {
		EhcAssert.notBlank(expression, Constants.RESPONSE_CODE_PARAMETER_ERROR, message);
	}

	/**
	 * 判断输入的expression是否为空字串
	 * 
	 * @param expression
	 * @param code
	 * @param message
	 */
	public static void notBlank(String expression, int code, String message) {
		if (!StringUtils.isNotBlank(expression)) {
			throw new ServiceException(code, message);
		}
	}

	public static void isNull(Object obj, String message) {
		if (obj != null) {
			throw new DuplicateException(message);
		}
	}

	public static void isNull(Object obj, int code, String message) {
		if (obj != null) {
			throw new DuplicateException(code, message);
		}
	}

	/**
	 * 判断对象obj是否为null. 默认抛出参数错误(status = 10000 的错误)
	 * 
	 * @param obj
	 * @param message
	 */
	public static void notNull(Object obj, String message) {
		EhcAssert.notNull(obj, Constants.RESPONSE_CODE_PARAMETER_ERROR, message);
	}

	/**
	 * 判断对象obj是否为null.
	 * 
	 * @param obj
	 * @param code
	 * @param message
	 */
	public static void notNull(Object obj, int code, String message) {
		if (obj == null) {
			throw new ServiceException(code, message, Level.WARN);
		}
	}

	/**
	 * 判断Collection对象是否为null或空
	 * 
	 * @param obj
	 * @param message
	 */
	public static void notEmpty(Collection<?> obj, String message) {
		if (CollectionUtils.isEmpty(obj)) {
			throw new ServiceException(Constants.RESPONSE_CODE_PARAMETER_ERROR, message);
		}
	}

}
