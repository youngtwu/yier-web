package com.yier.platform.service;

import com.yier.platform.TestBase;
import com.yier.platform.common.model.Patient;
import com.yier.platform.common.requestParam.PatientRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.util.List;

@ApiModel(value = "患者服务接口测试")
public class PatientServiceTest extends TestBase {
    private static final Logger logger = LoggerFactory.getLogger(PatientServiceTest.class);
    @Autowired
    private PatientService servicePatientService;
    //保证库里面存在的一条数据
    private Patient exitData ;
    public Patient buildPatient(){
        Patient exitData = new Patient();
        exitData.setRemarks("备注信息");
        exitData.setPhoneNumber("18788888888");
        exitData.setEmail("email@163.com");
        exitData.setPassword("password");//目前暂时明文
        exitData.setAvatarUrl("https://i02picsos.sogoucdn.com/eacf9ba5d5a011f1");
        exitData.setUserName("测试用户名");
        exitData.setTrueName("真实名字");
        exitData.setIdCardNo("522522197810012814");
        servicePatientService.registerPatientSelective(exitData,"h5");
        return exitData;
    }
    @Before
    public void setUp() throws Exception {
        logger.info("begin to test.....");
        this.exitData = this.buildPatient();
        logger.info("新插入数据信息："+exitData.toJsonString());
    }

    @After
    public void tearDown() throws Exception {
        logger.info("end the test....");
    }

    @ApiOperation(value = "通过查询获取患者信息列表")
    @Test
    public void listPatient() {
        PatientRequest params = new PatientRequest();
        params.setStart(0);
        params.setSize(5);;
        //查询前五条数据
        List<Patient> list = servicePatientService.listPatient(params);
        Assert.isTrue(list.size()>0, "库里面没有数据");
        logger.info("result first:"+list.get(0).toJsonString());
    }
    @ApiOperation(value = "通过查询获取患者信息列表总数")
    @Test
    public void listPatientCount() {
        PatientRequest params = new PatientRequest();
        params.setStart(0);
        params.setSize(5);;
        //查询前五条数据
        int count = servicePatientService.listPatientCount(params);
        Assert.isTrue(count>0, "库里面没有数据");
        logger.info("result count:" + count);
    }
    @ApiOperation(value = "通过ID查询获取患者信息")
    @Test
    public void selectByPrimaryKey() {
        PatientRequest params = new PatientRequest();
        params.setStart(0);
        params.setSize(1);
        //查询前五条数据
        List<Patient> list = servicePatientService.listPatient(params);
        Assert.isTrue(list.size()>0, "库里面没有数据");
        Patient patient = servicePatientService.selectByPrimaryKey(list.get(0).getId());
        Assert.isTrue(patient!=null, "没有查询到的数据");
        logger.info(patient.toJsonString());

        Patient patientExit = servicePatientService.selectByPrimaryKey(this.exitData.getId());
        Assert.isTrue(patientExit!=null, "没有查询到测试数据");
        logger.info("测试数据是："+patientExit.toJsonString());
    }

    @ApiOperation(value = "更新患者信息")
    @Test
    public void updateByPrimaryKeySelective() {
        this.exitData.setRemarks("更新数据");
        int updateCount = servicePatientService.updatePatientBySelective(this.exitData);
        Assert.isTrue(updateCount>0, "没有更新对应的数据");
    }
}