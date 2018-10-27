package com.yier.platform.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.yier.platform.common.exception.TokenException;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * jwt token service
 */
@ApiModel(value = "jwt token service")
@Service
public class JwtTokenService {
    private static final Logger log = LoggerFactory.getLogger(JwtTokenService.class);
    @Autowired
    private RedisService redisService;
    /** token秘钥，请勿泄露，请勿随便修改 backups:JKKLJOoasdlfj */
    public static final String SECRET = "123456";
    public static final int calendarField = Calendar.HOUR;//Calendar.MINUTE;// Calendar.HOUR;// Calendar.SECOND;// Calendar.DATE;
    public static final int calendarInterval = 36;//redis 的周期
    /**
     * JWT生成Token.<br/>
     *
     * JWT构成: header, payload, signature
     *
     * @param userId 登录成功后用户userId, 参数userId不可传空
     */
    @ApiOperation(value = "根据条件生成token并储存在redies中")
    public String createToken(String typeId,String userId,String osType,String phoneNumber){
        Assert.isTrue(StringUtils.isNotBlank(typeId),"创建token中，端口号typeId需要提供");
        Assert.isTrue(StringUtils.isNotBlank(userId),"创建token中，用户userId需要提供");
        Assert.isTrue(StringUtils.isNotBlank(osType),"创建token中，操作端osType需要提供");
        Assert.isTrue(StringUtils.isNotBlank(phoneNumber),"创建token中，手机phoneNumber需要提供");
        StringBuilder sb = new StringBuilder();
        sb.append("token_")
                .append(typeId)
                .append("_")
                .append(userId);
        // 不设定过期日期，完全靠redis来进行维护
        Date signTime = new Date();
        /*
        // expire time
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(calendarField, calendarInterval);
        Date expiresDate = nowTime.getTime();
        */

        // header Map
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");
        // build token
        // param backups {iss:Service, aud:APP}
        String token = JWT.create().withHeader(map) // header
                .withClaim("typeId", typeId) // payload
                .withClaim("userId", userId)
                .withClaim("osType", osType)
                .withClaim("phoneNumber", phoneNumber)
                .withIssuedAt(signTime) // 设定每次签名时间保证了每次分配的不相同 ，否则每次分配的会是一样 sign time
//                .withExpiresAt(expiresDate) // expire time
                .sign(Algorithm.HMAC256(SECRET)); // signature
        this.redisService.setTokenInfoByKey(sb.toString(),token,calendarInterval,calendarField);
        return token;
    }

    @ApiOperation(value = "传递token及对应的key组成，验证是否是登录，否则抛出异常")
    public void loginVeriferToken(String token,String typeId,String userId){
        String key = this.getAppTokenKey(token);//让JWT进行个简单的验证，并生成对应的Key
        StringBuilder sb = new StringBuilder();
        sb.append("token_").append(typeId).append("_").append(userId);
        if(StringUtils.equalsIgnoreCase(key,sb.toString())){
            String currentToken = this.redisService.getJwtTokenValueByKey(sb.toString());
            if(StringUtils.equalsIgnoreCase(token,currentToken)){
                long expire = this.redisService.getExpirRedis(sb.toString(),TimeUnit.SECONDS);
                if(expire < 3600*12){ //60*60*12  最后12小时重新延期 36小时
                    log.info("typeId:{} userId:{} ",typeId,userId);
                    this.redisService.setTokenInfoByKey(sb.toString(),token,calendarInterval,calendarField);
                }
            }
            else{
                log.info("对登录用户的token验证情况:传入token:{} --  比对的有效token:{}  --  key:{}  ",token,currentToken,key);
                String message = currentToken==null?"目前Token已经失效":"目前该用户在其他端口登录";
                throw new TokenException("请重新登录! "+message);
            }
        }
        else{
            throw new TokenException("请重新登录! 目前Token与实际用户不相符");
        }
    }

    /**
     * 解密Token
     *
     * @param token
     * @return
     * @throws Exception
     */
    public Map<String, Claim> verifyToken(String token) {
        DecodedJWT jwt = null;
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
            jwt = verifier.verify(token);
        } catch (Exception e) {
            log.warn(e.getMessage(),e);
            throw new TokenException("请重新登录! Token问题："+e.getMessage());
            // token 校验失败, 抛出Token验证非法异常
        }
        return jwt.getClaims();
    }
    /**
     * 根据Token获取user_id
     *
     * @param token
     * @return user_id
     */
    public String getAppUserId(String token) {
        Map<String, Claim> claims = verifyToken(token);
        Claim user_id_claim = claims.get("userId");
        if (null == user_id_claim || StringUtils.isEmpty(user_id_claim.asString())) {
            // token 校验失败, 抛出Token验证非法异常
            log.warn("token 校验失败, 抛出Token验证非法异常");
        }
        return user_id_claim.asString();
    }
    public String getAppPhoneNumber(String token) {
        Map<String, Claim> claims = verifyToken(token);
        Claim phoneNumber_claim = claims.get("phoneNumber");
        if (null == phoneNumber_claim || StringUtils.isEmpty(phoneNumber_claim.asString())) {
            throw new TokenException("通过token无法获取对应信息");
        }
        return phoneNumber_claim.asString();
    }
    public String getAppTokenKey(String token) {
        Map<String, Claim> claims = verifyToken(token);
        Claim user_id_claim = claims.get("userId");
        if (null == user_id_claim || StringUtils.isEmpty(user_id_claim.asString())) {
            // token 校验失败, 抛出Token验证非法异常
            throw new TokenException("通过token无法获取对应信息 user_id_claim");
        }
        Claim typeId_claim = claims.get("typeId");
        if (null == typeId_claim || StringUtils.isEmpty(typeId_claim.asString())) {
            throw new TokenException("通过token无法获取对应信息 typeId_claim");
        }
        StringBuilder sb = new StringBuilder();
        sb.append("token_")
                .append(typeId_claim.asString())
                .append("_")
                .append(user_id_claim.asString());
        return sb.toString();
    }
}
