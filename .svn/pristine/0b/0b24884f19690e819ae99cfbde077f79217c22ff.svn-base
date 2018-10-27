package com.yier.platform.dao;

import com.yier.platform.common.model.HospitalImages;
import org.springframework.stereotype.Repository;

@Repository
public interface HospitalImagesMapper {
    int deleteByPrimaryKey(Long id);

    int insert(HospitalImages record);

    int insertSelective(HospitalImages record);

    HospitalImages selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(HospitalImages record);

    int updateByPrimaryKey(HospitalImages record);

    /**
     * 根据医院id删除医院相关图片
     * @param hospitalId
     * @return
     */
    int deleteHospitalImagesByHospitalId(Long hospitalId);
}