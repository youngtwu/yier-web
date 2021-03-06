package com.yier.platform.service.feign;

import cc.ccae.yry.service.drug.query.sdk.model.Department;
import cc.ccae.yry.service.drug.query.sdk.model.Disease;
import cc.ccae.yry.service.drug.query.sdk.model.Drug;
import cc.ccae.yry.service.drug.query.sdk.model.DrugFeedback;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Lists;
import com.yier.platform.common.jsonResponse.CommonResponse;
import com.yier.platform.common.jsonResponse.ListResponse;
import com.yier.platform.common.model.Patient;
import com.yier.platform.common.po.DiseasePo;
import com.yier.platform.common.requestParam.DiseaseRequest;
import com.yier.platform.common.requestParam.DrugFeedbackRequest;
import com.yier.platform.common.requestParam.DrugRequest;
import com.yier.platform.common.requestParam.PatientRequest;
import com.yier.platform.common.util.JsonUtils;
import com.yier.platform.common.util.Utils;
import com.yier.platform.service.PatientService;
import com.yier.platform.service.RedisService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.MutableTriple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 药品疾病服务 service
 */
@ApiModel(value = "药品疾病服务 service")
@Service
public class DrugDiseaseService {
    private static final Logger logger = LoggerFactory.getLogger(DrugDiseaseService.class);
    @Autowired
    private DiseaseClientFeign diseaseClientFeign;//疾病方面对接
    @Autowired
    private DrugClientFeign drugClientFeign;//药品方面对接
    @Autowired
    private DrugFeedbackClientFeign drugFeedbackClientFeign;//药品反馈方面对接
    @Autowired
    private DepartmentClientFeign departmentClientFeign;//部门方面对接
    @Autowired
    private PatientService patientService;//获取患者信息
    @Autowired
    private RedisService redisService;

    @ApiOperation(value = "查询用药反馈通过患者")
    public ListResponse<DrugFeedbackPo> findByPatientId(DrugFeedbackRequest params){
        Page<DrugFeedback> drugFeedbackPage = drugFeedbackClientFeign.findByPatientId(params.getPatientId(),params.getScore(),params.getDrugName(),params.getPage(),params.getSize());
        ListResponse<DrugFeedbackPo> result = new ListResponse<DrugFeedbackPo>();
        result.setTotal(drugFeedbackPage.getTotalElements());
        List<DrugFeedback> drugFeedbackList = drugFeedbackPage.getContent();
        List<DrugFeedbackPo> listDrugFeedbackPo = generateDrugFeedbackPoList(drugFeedbackList);
        result.setData(listDrugFeedbackPo);
        return result;
    }

    @ApiOperation(value = "查询用药反馈通过药品")
    public ListResponse<DrugFeedbackPo> findByDrugId(DrugFeedbackRequest params){
        Page<DrugFeedback> drugFeedbackPage = drugFeedbackClientFeign.findByDrugId(params.getDrugId(),params.getPage(),params.getSize());
        ListResponse<DrugFeedbackPo> result = new ListResponse<DrugFeedbackPo>();
        result.setTotal(drugFeedbackPage.getTotalElements());
        List<DrugFeedback> drugFeedbackList = drugFeedbackPage.getContent();
        List<DrugFeedbackPo> listDrugFeedbackPo = generateDrugFeedbackPoList(drugFeedbackList);
        result.setData(listDrugFeedbackPo);
        return result;
    }
    @ApiOperation(value = "插入用药反馈")
    public CommonResponse<MutablePair<String,DrugFeedback>> createDrugFeedback(DrugFeedback record){
        CommonResponse<MutablePair<String,DrugFeedback>> result = new CommonResponse<MutablePair<String,DrugFeedback>>();
        drugFeedbackClientFeign.createFeedback(record.getDrugId(),record.getDiseaseId(),record.getPatientId(),record.getCountPreDay(),record.getEachHave(),record.getEachHaveUnit(),record.getStartDate(),record.getEndDate(),record.getScore(),record.getDescription());
        MutablePair<String,DrugFeedback> mutablePair = new MutablePair<>();
        mutablePair.left = "用药反馈结果插入";
        mutablePair.right = record;
        result.setData(mutablePair);
        return result;
    }

    @ApiOperation(value = "通过药名 分页查询药品信息列表")
    public ListResponse<Drug> findDrugsByName(DrugRequest params){
        Page<Drug> drugPage = drugClientFeign.findByName(params.getDrugName(),params.getPage(),params.getSize(),params.getSort(), params.getAttr(),params.getPlaceOfOrigin());
        ListResponse<Drug> res = new ListResponse<Drug>();
        res.setData(drugPage.getContent());
        res.setTotal(drugPage.getTotalElements());
        return res;
    }
    @ApiOperation(value = "通过疾病Id 分页查询药品信息列表")
    public ListResponse<Drug> findDrugsByDiseaseId(DrugRequest params){
        logger.info("params:{}",params);
        Page<Drug> drugPage = drugClientFeign.findByDiseaseId(params.getDiseaseId(),params.getPage(),params.getSize(),params.getSort(), params.getAttr(),params.getPlaceOfOrigin());
        ListResponse<Drug> res = new ListResponse<Drug>();
        res.setData(drugPage.getContent());
        res.setTotal(drugPage.getTotalElements());
        logger.info(res.toJsonString());
        return res;
    }
    @ApiOperation(value = "通过首字母 分页查询疾病信息列表")
    public ListResponse<DiseasePo> findDiseasesByInitial(DiseaseRequest params){
        ListResponse<DiseasePo> res = new ListResponse<DiseasePo>();
        List<MutableTriple<String,Integer, List<DiseasePo>>> queryWholeDiseaseResult = this.queryDiseasesAllByRedis(params);
        List<DiseasePo> diseaseListTotal = queryWholeDiseaseResult.stream().filter(t->t.left.equalsIgnoreCase(params.getDiseaseInitial())).findFirst().orElse(new MutableTriple<>()).right;
        List<DiseasePo> diseaseList = diseaseListTotal.stream().skip(params.getSize()*(params.getPage()-1)).limit(params.getSize()).collect(Collectors.toList());
        res.setData(diseaseList);
        res.setTotal(diseaseListTotal.size());
        logger.info("通过首字母 分页查询疾病信息列表 查询到的总列表是：{}",diseaseListTotal);
        logger.info("通过首字母 分页查询疾病信息列表 查询到的总列表的分页结果：{} 目前的分页条件是：{}",diseaseList,params);
//        Page<Disease> diseasePage = diseaseClientFeign.findDiseasesByInitialPage(params.getDiseaseInitial(),params.getPage(),params.getSize());
//        res.setData(diseasePage.getContent());
//        res.setTotal(diseasePage.getTotalElements());
//        logger.info(res.toJsonString());
        return res;
    }
    @ApiOperation(value = "通过首字母 分页查询疾病信息列表")
    public CommonResponse<MutableTriple<String,Integer, List<DiseasePo>>> findDiseasesAllByInitial(DiseaseRequest params){
        CommonResponse<MutableTriple<String,Integer, List<DiseasePo>>> result = new CommonResponse<MutableTriple<String,Integer, List<DiseasePo>>>();
        List<MutableTriple<String,Integer, List<DiseasePo>>> queryWholeDiseaseResult = this.queryDiseasesAllByRedis(params);
        MutableTriple<String,Integer, List<DiseasePo>> mutablePair = queryWholeDiseaseResult.stream().filter(t->t.left.equalsIgnoreCase(params.getDiseaseInitial())).findFirst().orElse(new MutableTriple<>());
//        MutableTriple<String,Integer, List<DiseasePo>> mutablePair = new MutablePair<>();
//        mutablePair.left = params.getDiseaseInitial();
//        mutablePair.right = this.diseaseClientFeign.findDiseasesByInitial(params.getDiseaseInitial());
        result.setData(mutablePair);
        return result;
    }


    @ApiOperation(value = "查询疾病信息列表 所有的首字母")
    public ListResponse<MutableTriple<String,Integer, String>> findDiseasesInitial(DiseaseRequest params){
        ListResponse<MutableTriple<String,Integer, String>> res = new ListResponse<MutableTriple<String,Integer, String>>();
        List<MutableTriple<String,Integer, String>> diseaseList = this.queryDiseasesInitial(params);
        res.setData(diseaseList);
        res.setTotal(diseaseList.size());
        return res;
    }

    @ApiOperation(value = "通过部门Id 分页查询疾病信息列表")
    public ListResponse<DiseasePo> findDiseasesByDepartmentId(DiseaseRequest params){
        ListResponse<DiseasePo> res = new ListResponse<DiseasePo>();
//        List<MutableTriple<String,Integer, List<DiseasePo>>> queryWholeDiseaseResult = this.queryDiseasesAllByRedis(params);
//        List<DiseasePo> diseaseListTotal = queryWholeDiseaseResult.stream().filter(t->t.left.equalsIgnoreCase(params.getDiseaseInitial())).findFirst().orElse(new MutableTriple<>()).right;
//        List<DiseasePo> diseaseList = diseaseListTotal.stream().skip(params.getSize()*(params.getPage()-1)).limit(params.getSize()).collect(Collectors.toList());
//        res.setData(diseaseList);
//        res.setTotal(diseaseListTotal.size());
//        logger.info("通过首字母 分页查询疾病信息列表 查询到的总列表是：{}",diseaseListTotal);
//        logger.info("通过首字母 分页查询疾病信息列表 查询到的总列表的分页结果：{} 目前的分页条件是：{}",diseaseList,params);
        Page<Disease> diseasePage = this.diseaseClientFeign.findByDepartmentId(params.getDepartmentId(),params.getPage(),params.getSize());
        List<Disease> diseaseList = diseasePage.getContent();
        List<DiseasePo> diseasePoList = Lists.newArrayList();
        diseaseList.forEach(t->{
            DiseasePo diseasePo = new DiseasePo();
            diseasePo.setDrugCount(t.getDrugCount());
            diseasePo.setName(t.getName());
            diseasePo.setId(t.getId());
            diseasePoList.add(diseasePo);
        });
        res.setData(diseasePoList);
        res.setTotal(diseasePage.getTotalElements());
        logger.info(res.toJsonString());
        return res;
    }
    @ApiOperation(value = "查询疾病信息列表 所有的有效的部门")
    public List<MutableTriple<String,Integer, Long>> findDiseasesDepartment(DiseaseRequest params){
        List<MutableTriple<String,Integer, Long>> result = Lists.newArrayList();
        String key = "findDiseasesDepartmentAll_";
        List<Department> departmentList = this.departmentClientFeign.findAll();
        List<Department> departmentListValid = departmentList.stream().filter(t->t.getId()>0&&t.getDieaseNum()>0).collect(Collectors.toList());
        departmentListValid.forEach(t->{
            MutableTriple<String,Integer, Long> mutableTriple = new MutableTriple<>();
            mutableTriple.left = t.getName();
            mutableTriple.right = t.getId();
            mutableTriple.middle = t.getDieaseNum();
            result.add(mutableTriple);
        });
        return result;
    }

    @ApiOperation(value = "查询疾病信息列表 所有的首字母")
    public List<MutableTriple<String,Integer, String>> queryDiseasesInitial(DiseaseRequest params){
        String key = "findDiseasesInitialAll_";
        List<MutableTriple<String,Integer, String>> result = redisService.getJsonObjectByKey(key, new TypeReference<List<MutableTriple<String,Integer, String>>>() {
        });
        if(result==null){
            List<MutableTriple<String,Integer, List<DiseasePo>>> queryWholeDiseaseResult = this.queryDiseasesAllByRedis(params);
            List<MutableTriple<String,Integer, String>> resultList = Lists.newArrayList();
            queryWholeDiseaseResult.forEach(t->{
                resultList.add(new MutableTriple<>(t.left,t.middle,"left 表示字母 middle表示有效疾病数"));
            });
            redisService.setJsonObjectBy(key,resultList,12L,TimeUnit.HOURS);
//            redisService.setJsonObjectBy(key,resultList,10L,TimeUnit.MINUTES);
            result = resultList;
        }
        return result;
    }

    @ApiOperation(value = "查询所有有效疾病信息列表，返回前台要的列表结果")
    public ListResponse<MutableTriple<String,Integer, List<DiseasePo>>> findDiseasesAll(DiseaseRequest params){
        ListResponse<MutableTriple<String,Integer, List<DiseasePo>>> result = new ListResponse<MutableTriple<String,Integer, List<DiseasePo>>>();
        List<MutableTriple<String,Integer, List<DiseasePo>>> queryWholeDiseaseResult = this.queryDiseasesAllByRedis(params);
        result.setData(queryWholeDiseaseResult);
        result.setTotal(queryWholeDiseaseResult.size());
        return result;
    }

    @ApiOperation(value = "根据设定的KEY[自动设定，全局统一]对应的列表，缓存中存在则会获取，如果不存在则调用DB查询所有有效疾病信息列表")
    public List<MutableTriple<String,Integer, List<DiseasePo>>> queryDiseasesAllByRedis(DiseaseRequest params){
        String key = "findDiseasesAll_";
        List<MutableTriple<String,Integer, List<DiseasePo>>> result = redisService.getJsonObjectByKey(key, new TypeReference<List<MutableTriple<String,Integer, List<DiseasePo>>>>() {
        });
        if(result==null || result.size()==0){
            result = this.queryDiseasesAll(params);
            redisService.setJsonObjectBy(key,result,12L,TimeUnit.HOURS);
//            redisService.setJsonObjectBy(key,result,10L,TimeUnit.MINUTES);
            logger.info("目前DB调用查询情况是：key-{} value：{}",key,JsonUtils.toJsonString(result));
        }
        return result;

    }


    @ApiOperation(value = "查询DB所有有效疾病信息列表")
    public List<MutableTriple<String,Integer, List<DiseasePo>>> queryDiseasesAll(DiseaseRequest params){
        List<MutableTriple<String,Integer, List<DiseasePo>>> list = Lists.newArrayList();
        Map<String, List<Disease>> all = this.diseaseClientFeign.findAll();
        for(Map.Entry<String, List<Disease>> entry:all.entrySet()){
            List<Disease> validDiseaseList = entry.getValue().stream().filter(t->t.getId()>0 && t.getDrugCount()>0 ).collect(Collectors.toList());// && t.getDrugCount()>0   && t.getName().equalsIgnoreCase("梅毒")
            if(validDiseaseList.size() >0) {
                MutableTriple<String,Integer, List<DiseasePo>> mutablePair = new MutableTriple<>();
                mutablePair.left = entry.getKey();
                List<DiseasePo> poList = Lists.newArrayList();
                validDiseaseList.forEach(t->{
                    DiseasePo po = new DiseasePo();
                    po.setId(t.getId());
                    po.setName(t.getName());
                    po.setDrugCount(t.getDrugCount());
                    poList.add(po);
                });
                mutablePair.right = poList;
                mutablePair.middle = poList.size();
                list.add(mutablePair);
            }
        }
        return list;
    }


    /**
     * 通过drugFeedbackList生成DrugFeedbackPoList,注意考虑患者信息,医药信息
     * @param drugFeedbackList
     * @return
     */
    private List<DrugFeedbackPo> generateDrugFeedbackPoList(List<DrugFeedback> drugFeedbackList) {
        List<DrugFeedbackPo> listDrugFeedbackPo = Lists.newArrayList();
        List<String> idsList = Lists.newArrayList();
        List<Long> patientIdList = Lists.newArrayList();
        for(DrugFeedback item : drugFeedbackList){
            DrugFeedbackPo po = new DrugFeedbackPo(item);
            int cycle = 0;
            try {
                cycle = Utils.daysBetween(item.getStartDate(),item.getEndDate());
            } catch (ParseException e) {
                logger.error(e.getMessage(),e);
            }
            po.setCycle(cycle+"天");
//            Patient patient = patientService.selectByPrimaryKey(item.getPatientId());
//            if(patient!=null){
//                String trueName = patient.getTrueName();
//                if(trueName.length()==2){
//                    trueName  = "*"+trueName.substring(1,2);
//                }
//                else if(trueName.length()>2){
//                    int length = trueName.length();
//                    String firstName = trueName.substring(0,1);
//                    String lastName = trueName.substring(length-1,length);
//                    trueName = firstName+createAsterisk(length-2)+lastName;
//                }
//                String avatarUrl = patient.getAvatarUrl();
//                po.setTrueName(trueName);
//                po.setAvatarUrl(avatarUrl);
//            }
            listDrugFeedbackPo.add(po);
            if(!patientIdList.contains(item.getPatientId())){
                patientIdList.add(item.getPatientId());
            }
            String drugIdString = item.getDrugId().toString();
            if(!idsList.contains(drugIdString)){
                idsList.add(drugIdString);
            }
        }
        if(idsList.size()>0){
            String idsString =  StringUtils.join(idsList.toArray(),",");
            List<Drug> drugList = drugClientFeign.findByIds(idsString);//一枪头获取药品信息
            listDrugFeedbackPo.forEach(t->{
                t.setDrug(drugList.stream().filter(d->d.getId().longValue()==t.getDrugId().longValue()).findFirst().orElse(new Drug()));
            });
        }
        if(patientIdList.size()>0){
            PatientRequest params= new PatientRequest();
            params.setListId(patientIdList);
            List<Patient> patientList = patientService.listPatient(params);
            listDrugFeedbackPo.forEach(t->{
                Patient patient = patientList.stream().filter(p->p.getId().longValue() == t.getPatientId().longValue()).findFirst().orElse(new Patient());
                String trueName = patient.getTrueName();
                if(trueName==null) trueName = "";
                if(trueName.length()==2){
                    trueName  = "*"+trueName.substring(1,2);
                }
                else if(trueName.length()>2){
                    int length = trueName.length();
                    String firstName = trueName.substring(0,1);
                    String lastName = trueName.substring(length-1,length);
                    trueName = firstName+createAsterisk(length-2)+lastName;
                }
                String avatarUrl = patient.getAvatarUrl();
                if(avatarUrl==null) avatarUrl= "";
                t.setTrueName(trueName);
                t.setAvatarUrl(avatarUrl);
            });
        }
        return listDrugFeedbackPo;
    }
    //生成很多个*号
    private String createAsterisk(int length) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < length; i++) {
            stringBuffer.append("*");
        }
        return stringBuffer.toString();
    }
}
