package com.yier.platform.dao;

import com.yier.platform.common.model.*;
import com.yier.platform.common.requestParam.ChatRecordRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 聊天记录
 */
@Repository
public interface ChatRecordMapper {
    // 根据条件查询原始聊天记录列表
    List<ChatRecordPo> listChatRecordPo(ChatRecordRequest params);
    // 根据条件查询原始聊天记录列表 总数
    int listChatRecordPoCount(ChatRecordRequest params);
    // 根据条件查询对药师的我的咨询列表列表
    List<PharmacistPo> listChatRecordPharmacistPo(ChatRecordRequest params);
    // 根据条件查询对药师的我的咨询列表 总数
    int listChatRecordPharmacistPoCount(ChatRecordRequest params);
    // 根据条件查询对医生的我的咨询列表列表
    List<DoctorPo> listChatRecordDoctorPo(ChatRecordRequest params);
    // 根据条件查询对医生的我的咨询列表 总数
    int listChatRecordDoctorPoCount(ChatRecordRequest params);
    // 根据条件查询对患者聊天原始列表
    List<PatientPo> listChatRecordPatientPo(ChatRecordRequest params);
    // 根据条件查询对患者聊天原始列表 总数
    int listChatRecordPatientPoCount(ChatRecordRequest params);


    int deleteByPrimaryKey(Long id);

    int insert(ChatRecord record);

    int insertSelective(ChatRecord record);

    ChatRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ChatRecord record);

    int updateByPrimaryKey(ChatRecord record);
}