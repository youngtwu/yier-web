package com.yier.platform.service;

import com.yier.platform.common.exception.TokenException;
import com.yier.platform.common.util.Utils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@ApiModel(value = "签名验证服务 service")
@Service
public class SignatureService {
    private static final Logger logger = LoggerFactory.getLogger(SignatureService.class);
    @Autowired
    private ToolShareService toolShareService;//集成通用或公用的分享处理

    @ApiOperation(value = "通过随机数获取对应的app可以，有些类似于App Secret")
    public String getAppKeyBy(Long typeId, String random) {
        String userType = toolShareService.getAppInfoByTypeId(typeId);
        return Utils.aesEncrypt(userType, random);
    }

    @ApiOperation(value = "获取对应的时间戳")
    public String getTimestamp() {
        long currentTime = System.currentTimeMillis();//时间转化为时间戳（13位） 精确到毫秒
//        long currentTime = new Date().getTime();//时间转化为时间戳（10位） 精确到秒
        return currentTime + "";
    }

    public String getSign(String appKey, String nonce, String timestamp) {
        StringBuilder sb = new StringBuilder();
        sb.append(appKey).append("_")
                .append(nonce).append("_")
                .append(timestamp);
        String result = DigestUtils.md5Hex(sb.toString());
        logger.info("目前的sign：{} 原文为：{}", result, sb.toString());
        return result;
    }

    @ApiOperation(value = "传递token及对应的key组成，验证是否是登录，否则抛出异常")
    public void veriferSign(String typeId, String appKey, String nonce, String timestamp, String sign) {
        String userType = Utils.aesDecrypt(appKey, nonce);
        logger.debug("【签名验证】情况是：userType：{} ，typeId:{}, appKey:{},nonce:{},sign:{},timestamp:{},相差时长:{}", userType, typeId, appKey, nonce, sign, timestamp, Math.abs(System.currentTimeMillis() - Long.parseLong(timestamp)));
        if (StringUtils.equals(toolShareService.getAppInfoByTypeId(typeId), userType)) {
            StringBuilder sb = new StringBuilder();
            sb.append(appKey).append("_")
                    .append(nonce).append("_")
                    .append(timestamp);
            if (StringUtils.equals(DigestUtils.md5Hex(sb.toString()), sign)) {
                if(!Utils.isValidTimestamp(timestamp)){
                    throw new TokenException("请重新登录! 请提供有效时间戳timestamp");
                }
                Long sendTime = Long.parseLong(timestamp);//发送时间
//                if (Math.abs(System.currentTimeMillis() - sendTime) > 60 * 1000){//时间戳绝对值大于60秒相应的都是超时的，在目前时间
//                    logger.info("有效区间是目前60秒内跟距离目是60秒内的时间，绝对值的区间是一个点的两侧距离  currentTime:{}  sendTime:{} 小于60秒",System.currentTimeMillis(),sendTime);
//                }
                //if (Math.abs(System.currentTimeMillis() - sendTime) > 24 * 3600 * 1000) {//时间戳绝对值大于1小时相应的都是超时的，在目前时间
                if (System.currentTimeMillis() - sendTime > 24 * 3600 * 1000) {//时间戳绝对值大于1小时相应的都是超时的，在目前时间
                    logger.info("有效区间是目前1小时内内跟距离目是24小时内的时间，绝对值的区间是一个点的两侧距离  currentTime:{}  sendTime:{} 小于1小时", System.currentTimeMillis(), sendTime);
                }
            } else {
                throw new TokenException("请重新登录! 目前Signature 数据签名不匹配");
            }
        } else {
            throw new TokenException("请重新登录! 目前appKey非有些数据");
        }
    }


}
