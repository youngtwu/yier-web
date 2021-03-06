package com.yier.platform.service.feign;

import cc.ccae.yry.service.drug.query.sdk.FeedbackApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "yry-service-drug-query", path = "/feedback")
public interface DrugFeedbackClientFeign extends FeedbackApi {
}
