package com.yier.platform.web;

import io.swagger.annotations.ApiModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@ApiModel(value = "护士相关的请求接口")
@RestController
@RequestMapping("/nurseManager")
@Slf4j
public class NurseManagerController {
}
