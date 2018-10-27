package com.yier.platform.dao;

import com.yier.platform.common.model.AppConfig;
import com.yier.platform.common.requestParam.AppConfigRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppConfigMapper {

    //根据typeId查询APP配置信息
    List<AppConfig> selectAppConfigByType(AppConfigRequest record);

    //根据pkey查询APP配置信息
    AppConfig getAppConfigByPkey(AppConfigRequest record);

    //根据typeId查询APP配置信息
    List<AppConfig> getAppConfigList(AppConfigRequest record);

    //根据typeId查询APP配置信息数量
    int getAppConfigListCount(AppConfigRequest record);

    //根据typeId删除APP配置信息
    int deleteAppConfigByTypeId(Long typeId);





    int deleteByPrimaryKey(Long id);

    int insert(AppConfig record);

    int insertSelective(AppConfig record);

    AppConfig selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AppConfig record);

    int updateByPrimaryKey(AppConfig record);
}