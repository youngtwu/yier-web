package com.yier.platform.common.po;

import com.google.common.collect.Lists;
import com.yier.platform.common.jsonResponse.BaseJsonObject;
import com.yier.platform.common.model.DoctorPo;
import com.yier.platform.common.model.HospitalPo;
import com.yier.platform.common.model.PharmacistPo;

import java.util.List;

public class HospitalDoctorList   extends BaseJsonObject {
    private List<HospitalPo> hospitalList = Lists.newArrayList();
    private List<DoctorPo> doctorList = Lists.newArrayList();
    private List<PharmacistPo> pharmacistList = Lists.newArrayList();
    public List<HospitalPo> getHospitalList() {
        return hospitalList;
    }

    public void setHospitalList(List<HospitalPo> hospitalList) {
        this.hospitalList = hospitalList;
    }

    public List<DoctorPo> getDoctorList() {
        return doctorList;
    }

    public void setDoctorList(List<DoctorPo> doctorList) {
        this.doctorList = doctorList;
    }

    public List<PharmacistPo> getPharmacistList() {
        return pharmacistList;
    }

    public void setPharmacistList(List<PharmacistPo> pharmacistList) {
        this.pharmacistList = pharmacistList;
    }
}
