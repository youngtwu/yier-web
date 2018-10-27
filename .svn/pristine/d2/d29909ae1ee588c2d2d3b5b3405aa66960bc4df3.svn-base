package com.yier.platform.dao;

import com.yier.platform.common.model.DoctorPracticePoint;
import com.yier.platform.common.model.PracticePointPo;
import com.yier.platform.common.requestParam.PracticeRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorPracticePointMapper {
    // 根据条件查询执业点附近名字详情列表
    List<PracticePointPo> listPracticePointPo(PracticeRequest params);
    // 根据条件查询执业点附近名字详情列表 总数
    int listPracticePointPoCount(PracticeRequest params);
    // 根据条件查询列表
    List<DoctorPracticePoint> listPracticePoint(PracticeRequest params);
    // 根据条件查询列表 总数
    int listPracticePointCount(PracticeRequest params);

    /**
     * 根据条件删除执业点
     * @param params
     * @return
     */
    int deletePracticePointByCondition(PracticeRequest params);







    int deleteByPrimaryKey(Long id);

    int insert(DoctorPracticePoint record);

    int insertSelective(DoctorPracticePoint record);

    DoctorPracticePoint selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DoctorPracticePoint record);

    int updateByPrimaryKey(DoctorPracticePoint record);
}