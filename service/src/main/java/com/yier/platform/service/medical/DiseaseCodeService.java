package com.yier.platform.service.medical;

import com.yier.platform.common.model.DiseaseCode;
import com.yier.platform.common.requestParam.DiseaseCodeRequest;
import com.yier.platform.dao.DiseaseCodeMapper;
import com.yier.platform.dao.MedicalDateMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 疾病编码 service
 */
@Service
public class DiseaseCodeService {
    private final Logger logger = LoggerFactory.getLogger(MedicalDateService.class);
    @Autowired
    private DiseaseCodeMapper daoDiseaseCodeMapper;
    // 根据条件查询列表 根据id列表查询到医生没有收藏的疾病代码
    public List<DiseaseCode> listDoctorWithoutDiseaseCode(DiseaseCodeRequest params){
        return daoDiseaseCodeMapper.listDoctorWithoutDiseaseCode(params);
    }
    // 根据条件查询列表
    public List<DiseaseCode> listDiseaseCode(DiseaseCodeRequest params){
        return daoDiseaseCodeMapper.listDiseaseCode(params);
    }
    // 根据条件查询列表 总数
    public int listDiseaseCodeCount(DiseaseCodeRequest params){
        return daoDiseaseCodeMapper.listDiseaseCodeCount(params);
    }
    public DiseaseCode selectByPrimaryKey(Long id){
        return daoDiseaseCodeMapper.selectByPrimaryKey(id);
    }
}
