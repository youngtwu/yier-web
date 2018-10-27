package com.yier.platform.dao;

import com.yier.platform.common.model.AuditInfo;
import com.yier.platform.common.requestParam.AuditInfoRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 审核日志
 */
@Repository
public interface AuditInfoMapper {

    /**
     * 根据表主键和表类型查询审核信息
     *
     * @param params
     * @return
     */
    List<AuditInfo> selectAuditInfoByTableId(AuditInfoRequest params);


    int deleteByPrimaryKey(Long id);

    int insert(AuditInfo record);

    int insertSelective(AuditInfo record);

    AuditInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AuditInfo record);

    int updateByPrimaryKey(AuditInfo record);
}