package com.yier.platform.common.util;

import com.yier.platform.common.exception.Constants;
import com.yier.platform.common.jsonResponse.JsonResponse;
import org.apache.http.concurrent.FutureCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.async.DeferredResult;

//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;


/**
 * 用于异步http请求访问外部资源
 * @author bob
 *
 * @param <T>
 */
public abstract class HttpAsyncCallback<T> implements FutureCallback<T> {
	private final Logger logger = LoggerFactory.getLogger(HttpAsyncCallback.class);

	private DeferredResult<JsonResponse> deferredResult;
	private Object userDate;

	public void setDeferredResult(DeferredResult<JsonResponse> deferredResult) {
		this.deferredResult = deferredResult;
	}
	
	public DeferredResult<JsonResponse> getDeferredResult() {
		return this.deferredResult;
	}
	

	public Object getUserDate() {
		return userDate;
	}



	public void setUserDate(Object userDate) {
		this.userDate = userDate;
	}



	@Override
	public void cancelled() {
		logger.warn("请求取消", new Exception());
		if (this.deferredResult != null) {
			JsonResponse res = new JsonResponse();
			res.setStatus(Constants.ERROR_CANCEL);
			res.setError("请求取消");
			this.deferredResult.setResult(res);
		}
	}

	@Override
	public void failed(Exception e) {
		logger.error(e.getMessage(), e);
		if (this.deferredResult != null) {
			JsonResponse res = new JsonResponse();
			res.setStatus(Constants.TASK_PROCESS_ERROR);
			res.setError("请求失败");
			this.deferredResult.setResult(res);
		}

	}



}
