package com.yier.platform.service;


import com.yier.platform.common.exception.Constants;
import com.yier.platform.common.exception.EhcAssert;
import com.yier.platform.common.exception.ServiceException;
import com.yier.platform.common.model.SystemParameters;
import com.yier.platform.common.redis.RedisSystemParameterCache;
import com.yier.platform.common.requestParam.BaseRequest;
import com.yier.platform.common.typeEnum.SystemParameterKey;
import com.yier.platform.dao.SystemParametersMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 系统信息 service
 */
@Service
public class SystemParametersService {
    private static final Logger logger = LoggerFactory.getLogger(SystemParametersService.class);
    @Autowired
    private SystemParametersMapper systemParameterDao;
    @Autowired
    private RedisSystemParameterCache redisSystemParameterCache;

    // 根据条件查询基本数据列表
    public List<SystemParameters> listSystemParameters(BaseRequest params){
        return this.systemParameterDao.listSystemParameters(params);
    }
    // 根据条件查询基本数据列表 总数
    public int listSystemParametersCount(BaseRequest params){
        return this.systemParameterDao.listSystemParametersCount(params);
    }


    public String getParameterByKey(String parkey){
        SystemParameters systemParameters = redisSystemParameterCache.getByKey(parkey);
        if(systemParameters != null){
            return systemParameters.getPvalue();
        }
        String value = this.systemParameterDao.getParameter(parkey);
        if(StringUtils.isBlank(value)){
            throw new ServiceException("系统参数不存在. parkey = " + parkey);
        }
        return value;
    }

    public String getParameter(SystemParameterKey parkey) {
        return this.getParameterByKey(parkey.name());
    }

    @Transactional
    public boolean modifySystemParameter(String key, String value) {
        logger.info(">> 键值key = {},值 value = {}", key, value);
        boolean ret = false;
        SystemParameters systemParam = this.getSystemParameterByKey(key, false);
        EhcAssert.notNull(systemParam, Constants.RESPONSE_CODE_PARAMETER_ERROR, "系统参数 " + key + " 不存在");
        if (!StringUtils.equals(systemParam.getPvalue(), value)) {
            systemParam.setPvalue(value);
            ret = this.updateSystemParameter(systemParam);
        }
        logger.info("<< ret = {}", ret);
        return ret;
    }

    public boolean updateSystemParameter(SystemParameters systemParam) {
        logger.info(">>更新参数 systemParam = {}", systemParam);
        boolean ret = this.systemParameterDao.updateSystemParameter(systemParam) > 0 ? true : false;
        logger.info("<< ret = {}");
        return ret;
    }

    public SystemParameters getSystemParameterByKey(String parkey, boolean useCache) {
        SystemParameters systemParameter = null;
        if (useCache) {
            systemParameter = redisSystemParameterCache.getByKey(parkey);
            if (systemParameter != null) {
                // 从缓存获取成功
                return systemParameter;
            }
        }
        systemParameter = this.systemParameterDao.getSystemParameterByKey(parkey);
        return systemParameter;
    }

    public SystemParameters selectByPrimaryKey(Long id) {
        return systemParameterDao.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKeyWithBLOBs(SystemParameters record){
        return this.systemParameterDao.updateByPrimaryKeyWithBLOBs(record);
    }
}
