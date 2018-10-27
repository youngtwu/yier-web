package com.yier.platform.dao;

import com.yier.platform.common.model.Images;
import com.yier.platform.common.requestParam.ImagesRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImagesMapper {
    // 根据条件查询列表
    List<Images> listImages(ImagesRequest params);

    // 根据条件查询列表 总数
    int listImagesCount(ImagesRequest params);

    //根据typeId和markId联合查询APP信息信息
    List<Images> getAppImagesByTypeIdAndMarkId(ImagesRequest params);

    //根据typeId查询APP信息信息
    List<Images> getAppImagesByTypeId(ImagesRequest params);

    //根据markId查询APP信息信息
    List<Images> getAppImagesByMarkId(ImagesRequest params);

    int updateAppImageOrderForBatch(List<Images> images);




    int deleteByPrimaryKey(Long id);

    int insert(Images record);

    int insertSelective(Images record);

    Images selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Images record);

    int updateByPrimaryKey(Images record);
}