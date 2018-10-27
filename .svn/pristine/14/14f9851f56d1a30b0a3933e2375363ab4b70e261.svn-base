package com.yier.platform.web;

import com.yier.platform.common.requestParam.DoctorRequest;
import com.yier.platform.service.DoctorService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ApiModel(value = "分享相关的web入口")
@Controller
//如果只是使用@RestController注解Controller，则Controller中的方法无法返回jsp页面，配置的视图解析器InternalResourceViewResolver不起作用，返回的内容就是Return 里的内容。例如：本来应该到success.jsp页面的，则其显示success.
@RequestMapping("/share")
@Slf4j
public class ShareController {
    @Autowired
    private DoctorService doctorService;

    @ApiOperation(value = "通过条件查询获取所能看到的公告类别列表")
    @RequestMapping(value = {"/info"},method = {RequestMethod.POST,RequestMethod.GET})
    public String vueTest(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                          @ApiParam(value = "操作来源") @RequestParam(value = "osType", required = true) String osType,
                          @ApiParam(value = "端口类型") @RequestParam(value = "typeId",defaultValue = "0",required = true) long typeId,
                          @ApiParam(value = "用户ID") @RequestParam(value = "userId", required = true) Long userId,
                          @ApiParam(value = "端口类型") @RequestParam(value = "shareTypeId",defaultValue = "0",required = true) long shareTypeId,
                          @ApiParam(value = "用户ID") @RequestParam(value = "shareUserId", required = true) Long shareUserId
                          ){
        log.info("进入到share/shareInf.html");
        Assert.isTrue(StringUtils.isNotBlank(osType),"操作来源不存在！");
        Assert.isTrue(typeId>0,"端口类型无效！");
        Assert.isTrue(userId!=null,"用户ID不存在！");
        Assert.isTrue(shareTypeId>0,"分享端口类型无效！");
        Assert.isTrue(shareUserId!=null,"分享用户ID不存在！");
        model.addAttribute("nameInfo","nameInfo");
        DoctorRequest doctorRequest = new DoctorRequest();
        doctorRequest.setDoctorId(shareUserId);
        doctorRequest.setPatientId(userId);
        model.addAttribute("userPo",doctorService.getDoctorPoById(doctorRequest));
        model.addAttribute("userList",doctorService.listDoctorPo(new DoctorRequest()));
        return "share/shareInfo";
    }
}
