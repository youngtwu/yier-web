package com.yier.platform.dao;

import com.yier.platform.common.model.MedicalDate;
import com.yier.platform.common.model.MedicalDatePo;
import com.yier.platform.common.requestParam.MedicalDateRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicalDateMapper {

    //获取并锁定查询就诊日期
    MedicalDatePo getLockMedicalDatePoByIdRequest(MedicalDateRequest params);
    //更新锁定数据
    int updateLockNumSelective(MedicalDate record);
    //查询到全天的信息
    int listMedicalDateGroupCount(MedicalDateRequest params);
    List<MedicalDatePo> listMedicalDatePo(MedicalDateRequest params);
    int listMedicalDatePoCount(MedicalDateRequest params);
    List<MedicalDate> listMedicalDate(MedicalDateRequest params);
    int listMedicalDateCount(MedicalDateRequest params);
	
    int deleteByPrimaryKey(Long id);

    int insert(MedicalDate record);

    int insertSelective(MedicalDate record);

    MedicalDate selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MedicalDate record);

    int updateByPrimaryKey(MedicalDate record);
}