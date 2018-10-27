/*
 * Copyright (c) 2018 上海观谷科技有限公司 All Rights Reserved.
 * @author 李文喆
 * @date 18-6-12 下午2:07
 * @version 1.0.0
 */

package com.yier.platform.service;


import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yier.platform.common.exception.ServiceException;
import com.yier.platform.common.util.Utils;
import com.yier.platform.conf.ApplicationConfig;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class SmsService {
    private static final Logger logger = LoggerFactory.getLogger(SmsService.class);
    @Autowired
    private ApplicationConfig applicationConfig;//来自于可配置的外部属性文件
    @Autowired
    private RedisService redisService;


    @ApiOperation(value = "发送短信：phoneNumber 手机号码，useType 短信类别，clientIP 客户端IP地址，code 验证码，使用端typeId")
    public SendSmsResponse sendSms(String phoneNumber, Long typeId, String code, String useType, String clientIP) {
        String templateCode = null;
        switch (useType) {
            case "0"://register-->影响到短信模板为注册
                templateCode = applicationConfig.getSms().getTemplateCodeForRegister();// "SMS_136675023";
                break;
            case "1"://verify-->影响到短信模板为验证
                templateCode = applicationConfig.getSms().getTemplateCodeForVerify();// "SMS_136675026";
                break;
            default:
                Assert.isTrue(false, "使用类型非法，目前只支持注册跟验证。");
        }
        //限制同一号码发送间隔
        String mobileKey = applicationConfig.getSms().getSmsMobilePrefix() + typeId + phoneNumber;
        Map<String, String> mapMobile = (Map<String, String>)redisService.getValueByKeyRedisTemplate(mobileKey);
        long expireMobile = -1;//标志处理说明：若最后结果-1表示第一次（然后也是设定过期时间了），不是-1是表示在固有上面累加继续
        if (mapMobile == null || mapMobile.entrySet().size() != 3) {
            mapMobile = new HashMap<String, String>();
            mapMobile.put("time", System.currentTimeMillis() + "");//放置目前系统时间戳，防止一分钟误操作
            mapMobile.put("count", "1");//次数
            mapMobile.put("code", code.toString());//验证码信息
         } else {
            Long sendTime = Long.parseLong(mapMobile.get("time"));
            if (Math.abs(System.currentTimeMillis() - sendTime) < this.applicationConfig.getSms().getSmsInterval() * 60 * 1000) {//1分钟内不能够重新点击发送验证码
                Assert.isTrue(false, "操作过于频繁，请稍后再试。");
            }
            Integer sendCount = Integer.parseInt(mapMobile.get("count"));
            if (sendCount >= this.applicationConfig.getSms().getMaxsendOfDday()) {// 每日不能超过限制次数10
                Assert.isTrue(false, "发送短信数量超出上限。");
            }
            sendCount++;
            mapMobile.put("time", System.currentTimeMillis() + "");//放置目前系统时间戳，防止一分钟误操作
            mapMobile.put("count", sendCount.toString());//次数
            mapMobile.put("code", code.toString());//验证码信息
            //续接对应的过期时间，以秒为单位续接
            expireMobile = redisService.getExpireRedisTemplate(mobileKey, TimeUnit.SECONDS);
            logger.debug("目前Mobile电话的过期情况为：mobileKey:{} expire:{}", mobileKey, expireMobile);
         }

        //限制同一IP访问次数
        String clientKey = applicationConfig.getSms().getSmsClientPrefix() + typeId + clientIP;
        Map<String, String> mapClient = (Map<String, String>) redisService.getValueByKeyRedisTemplate(clientKey);
        long expireIp = -1;//标志处理说明：若最后结果-1表示第一次（然后也是设定过期时间了），不是-1是表示在固有上面累加继续
        if (mapClient == null || mapClient.entrySet().size() != 3) {
            mapClient = new HashMap<String, String>();
            mapClient.put("count", "1");
            //初始发送时间间隔1秒钟
            mapClient.put("interval", "5");
            mapClient.put("time", System.currentTimeMillis() + "");
         } else {
            String intervalString = mapClient.get("interval");
            if (intervalString == null) {
                logger.info("目前很奇怪，竟然intervalString为空：{}", mapClient);
                redisService.deleteByKeyRedisTemplate(clientKey);
                mapClient = new HashMap<String, String>();
                mapClient.put("count", "1");
                //初始发送时间间隔1秒钟
                mapClient.put("interval", "5");
                mapClient.put("time", System.currentTimeMillis() + "");
                redisService.setValueByKeyRedisTemplate(clientKey, mapClient, this.applicationConfig.getSms().getTimeout(), this.applicationConfig.getSms().getUnit());//动态配置 手机号码,redis 从开始创建时要执行的失效时间
            }
            Integer interval = Integer.parseInt(intervalString);//时间间隔
            Long sendTime = Long.parseLong(mapClient.get("time"));//发送时间
            if (Math.abs(System.currentTimeMillis() - sendTime) < interval * 1000) {//初始发送时间间隔1秒钟
//                mapClient.put("interval", (interval + 5) + "");
//                mapClient.put("time", System.currentTimeMillis() + "");
//                redisTemplate.opsForValue().set(clientKey, mapClient);
                Assert.isTrue(false, "操作过于频繁，请稍后再试。[限制同一IP访问]");
            }
            Integer sendCount = Integer.parseInt(mapClient.get("count"));//发送次数
            if (sendCount >= this.applicationConfig.getSms().getIpTotal()) {//  * 5
                Assert.isTrue(false, "发送短信数量超出上限。[限制同一IP访问]");
            }
            sendCount++;
            mapClient.put("time", System.currentTimeMillis() + "");
            mapClient.put("count", sendCount.toString());
            //续接对应的过期时间，以秒为单位续接
            expireIp = redisService.getExpireRedisTemplate(clientKey, TimeUnit.SECONDS);
            logger.debug("目前IP的过期情况为：clientKey:{} expire:{}", clientKey, expireIp);
        }
        /* 数据库DB保存，目前完全依赖redis来进行保存处理
        saveSmsCodeToDatabase(phoneNumber, typeId, code, clientIP);
        */
        SendSmsResponse result = null;
        result = sendSmsResponse(phoneNumber, code, templateCode);
        if(result!=null && result.getCode()!=null && StringUtils.equalsIgnoreCase("OK",result.getCode())){//保证确实成功才发送消息
            if(expireMobile==-1){//标志处理说明：若最后结果-1表示第一次（然后也是设定过期时间了），不是-1是表示在固有上面累加继续
                redisService.setValueByKeyRedisTemplate(mobileKey, mapMobile, this.applicationConfig.getSms().getTimeout(), this.applicationConfig.getSms().getUnit());//动态配置 手机号码,redis 从开始创建时要执行的失效时间
            }
            else{
                redisService.setValueByKeyRedisTemplate(mobileKey, mapMobile, expireMobile, TimeUnit.SECONDS);//在固有失效时长中设定
            }
            if (expireIp == -1) {//对于之前没有设定的，一定要设定个到期时间
                redisService.setValueByKeyRedisTemplate(clientKey, mapClient, this.applicationConfig.getSms().getTimeout(), this.applicationConfig.getSms().getUnit());//动态配置 手机号码,redis 从开始创建时要执行的失效时间
            }
            else{
                redisService.setValueByKeyRedisTemplate(clientKey, mapClient, expireIp, TimeUnit.SECONDS);
            }
            logger.info("短信缓存情况汇报：IP端口放置{}-->{} 过期秒数{}[-1表示第一次]  手机号码放置{}-->{} 过期秒数{}[-1表示第一次]", clientKey, mapClient, expireIp, mobileKey, mapMobile,expireMobile);
        }
        else{
            throw new ServiceException("请稍后再试,目前短信服务发送失败!"+(result==null?"":"原因在于:"+result.getMessage()));
        }
        return result;
    }



    @ApiOperation(value = "直接发送编码到某手机上")
    public SendSmsResponse sendSmsResponse(String phoneNumber, String code, String templateCode) {
        SendSmsResponse result;
        try {
            logger.info("开始发送短信前的情况手机号:{} 验证码:{}  所用模板:{}",phoneNumber, code, templateCode);
            if (templateCode == null) {
                templateCode = applicationConfig.getSms().getTemplateCodeForVerify();
            }
            IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", applicationConfig.getSms().getAccessKeyId(), applicationConfig.getSms().getAccessKeySecret());
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", applicationConfig.getSms().getProduct(), applicationConfig.getSms().getDomain());
            IAcsClient acsClient = new DefaultAcsClient(profile);

            SendSmsRequest request = new SendSmsRequest();
            request.setPhoneNumbers(phoneNumber);
            request.setSignName("亿尔云");

            request.setTemplateCode(templateCode);

            HashMap<String, String> param = new HashMap<>();
            param.put("code", code);
            ObjectMapper objectMapper = new ObjectMapper();
            request.setTemplateParam(objectMapper.writeValueAsString(param));
            result = acsClient.getAcsResponse(request);
            logger.info("短信结果发送结束情况 code:{} message:{} RequestId:{} BizId:{} ",result.getCode(), result.getMessage(),result.getRequestId(),result.getBizId());
        } catch (ClientException e) {
            throw new ServiceException("短信发送出现客户端异常:"+e.getMessage(), e);
        } catch (Exception e) {
            throw new ServiceException("短信发送出现错误异常:"+e.getMessage(), e);
        }
        return result;
    }

    @ApiOperation(value = "通过手机、用户类型来看验证码是否有效")
    public void checkCode(String phoneNumber, Long typeId, String code) {
        Assert.isTrue(Utils.isValidMobileNumber(phoneNumber), "手机号格式错误");
        Assert.isTrue(StringUtils.isNotBlank(code), "请输入验证码");
        //限制同一号码发送间隔
        String mobileKey = applicationConfig.getSms().getSmsMobilePrefix() + typeId + phoneNumber;
        Map<String, String> mapMobile = (Map<String, String>)redisService.getValueByKeyRedisTemplate(mobileKey);
        if (mapMobile == null || mapMobile.entrySet().size() != 3) {
            Assert.isTrue(false, "目前手机号验证码无效");
        } else {
            Long getExpire = redisService.getExpireRedisTemplate(mobileKey, TimeUnit.SECONDS);//获取以秒为计算单位，对应的到期时间
            logger.info("模板情况:将{}秒失效 系统默认设置的失效时长:{}{} ", getExpire, this.applicationConfig.getSms().getTimeout(), this.applicationConfig.getSms().getUnit(), this.applicationConfig.getSms());
            Long sendTime = Long.parseLong(mapMobile.get("time"));
            Integer sendCount = Integer.parseInt(mapMobile.get("count"));
            String redisCode = mapMobile.get("code");
            Date date = new Date(sendTime);
            String dateFormt = Utils.formatDate(date, "yyyy-MM-dd HH:mm:ss");
            logger.info("目前验证信息如此：时间dateFormt:{} sendTime:{} sendCount:{} redisCode:{} code:{}", dateFormt, sendTime, sendCount, redisCode, code);
            Assert.isTrue(StringUtils.equalsIgnoreCase(redisCode, code), "验证码不正确!");
        }
    }

}
