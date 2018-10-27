package com.yier.platform.service.medical;

import com.google.common.collect.Lists;
import com.yier.platform.common.model.DiseaseCode;
import com.yier.platform.common.model.DoctorDisease;
import com.yier.platform.common.requestParam.DiseaseCodeRequest;
import com.yier.platform.dao.DoctorDiseaseMapper;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

/**
 * 医生疾病列表 service
 */
@Service
public class DoctorDiseaseService {
    private final Logger logger = LoggerFactory.getLogger(MedicalDateService.class);
    @Autowired
    private DoctorDiseaseMapper daoDoctorDiseaseMapper;
    @Autowired
    private DiseaseCodeService diseaseCodeService;

    @ApiOperation(value = "如果医生没有收藏的ID将会添加到收藏列表中来")
    @Transactional
    public List<DiseaseCode> doWithoutDiseaseCode(DiseaseCodeRequest params) {
        Assert.isTrue(params.getUserId()!=null,"必须传递对应的用户Id");
        Assert.isTrue(params.getDoctorDiseaseIdList()!=null,"必须传递对应的编码列表");
        Assert.isTrue(params.getStatus()!=null,"必须传递status");
        List<DiseaseCode> listDoctorWithoutDiseaseCode = diseaseCodeService.listDoctorWithoutDiseaseCode(params);
        listDoctorWithoutDiseaseCode.forEach(disease->{
            DoctorDisease record = new DoctorDisease();
            record.setUserId(params.getUserId());
            record.setCodeAppend(disease.getCodeAppend());
            record.setDiseaseCode(disease.getDiseaseCode());
            record.setDiseaseCodeId(disease.getId());
            record.setDiseaseName(disease.getDiseaseName());
            record.setDisplayOrder(disease.getDisplayOrder());
            record.setGroupPrefix(disease.getGroupPrefix());
            record.setShortName(disease.getShortName());
            this.insertSelective(record);
        });
        return listDoctorWithoutDiseaseCode;
    }

    // 根据条件查询列表
    public List<DoctorDisease> listDoctorDisease(DiseaseCodeRequest params) {
        return daoDoctorDiseaseMapper.listDoctorDisease(params);
    }

    // 根据条件查询列表 总数
    public int listDoctorDiseaseCount(DiseaseCodeRequest params) {
        return daoDoctorDiseaseMapper.listDoctorDiseaseCount(params);
    }

    public int deleteByPrimaryKey(Long id) {
        return daoDoctorDiseaseMapper.deleteByPrimaryKey(id);
    }

    public int insertSelective(DoctorDisease record) {
        return daoDoctorDiseaseMapper.insertSelective(record);
    }

    public DoctorDisease selectByPrimaryKey(Long id) {
        return daoDoctorDiseaseMapper.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(DoctorDisease record) {
        return daoDoctorDiseaseMapper.updateByPrimaryKeySelective(record);
    }

}
