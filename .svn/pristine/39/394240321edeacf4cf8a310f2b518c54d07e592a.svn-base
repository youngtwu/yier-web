package com.yier.platform.service;

import com.yier.platform.TestBase;
import com.yier.platform.common.requestParam.DiseaseRequest;
import com.yier.platform.common.requestParam.DrugRequest;
import com.yier.platform.service.feign.DrugDiseaseService;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class DrugDiseaseServiceTest extends TestBase {
    private static final Logger logger = LoggerFactory.getLogger(DrugDiseaseServiceTest.class);
    @Autowired
    private DrugDiseaseService drugDiseaseService;
    @Test
    public void findDrugsByName() {
        DrugRequest params = new DrugRequest();
        params.setDrugName("A");
        params.setPage(1);
        params.setSize(10);
        drugDiseaseService.findDrugsByName(params);
    }

    @Test
    public void findDrugsByDiseaseId() {
        DrugRequest params = new DrugRequest();
        params.setDiseaseId(1L);
        params.setPage(1);
        params.setSize(10);
        drugDiseaseService.findDrugsByDiseaseId(params);
    }

    @Test
    public void findDiseasesByInitial() {
        DiseaseRequest params = new DiseaseRequest();
        params.setDiseaseInitial("A");
        params.setPage(1);
        params.setSize(10);
        drugDiseaseService.findDiseasesByInitial(params);
    }

    @Test
    public void findDiseasesAllByInitial() {
        DiseaseRequest params = new DiseaseRequest();
        params.setDiseaseInitial("A");
        drugDiseaseService.findDiseasesAllByInitial(params);
    }

    @Test
    public void findDiseasesInitial() {
        DiseaseRequest params = new DiseaseRequest();
        drugDiseaseService.findDiseasesInitial(params);
    }

    @Test
    public void queryDiseasesInitial() {
        DiseaseRequest params = new DiseaseRequest();
        drugDiseaseService.queryDiseasesInitial(params);
    }

    @Test
    public void findDiseasesAll() {
        DiseaseRequest params = new DiseaseRequest();
        drugDiseaseService.findDiseasesAll(params);
    }

    @Test
    public void queryDiseasesAllByRedis() {
        DiseaseRequest params = new DiseaseRequest();
        drugDiseaseService.queryDiseasesAllByRedis(params);
    }

    @Test
    public void queryDiseasesAll() {
        DiseaseRequest params = new DiseaseRequest();
        drugDiseaseService.queryDiseasesAll(params);
    }
}