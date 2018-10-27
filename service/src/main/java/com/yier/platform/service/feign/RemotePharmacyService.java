package com.yier.platform.service.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Component
@FeignClient(name = "yey-service-pharmacy")
public interface RemotePharmacyService {
    /**
     * 获取指定药房药品列表
     * @param pharmacyId 药房编号
     * @param name 药品名称
     * @param s 起始记录
     * @param n 返回数量
     * @return
     */
    @RequestMapping(value = "/pharmacy/{id}/drugs", method = RequestMethod.GET)
    Map<String, Object> listDrugs(@PathVariable("id")String pharmacyId, @RequestParam("name")String name, @RequestParam("s")Integer s, @RequestParam("n")Integer n);

    /**
     * 根据药品获取药房列表
     * @param drugList 药品列表
     * @return
     */
    @RequestMapping(value = "/pharmacies", method = RequestMethod.POST)
    Map<String, Object> listPharmacies(@RequestBody List<LockDrug> drugList);
    /**
     * 获取指定药房药品库存
     * @param pharmacyId 药房编号
     * @param drugCodes 药品编码，多个编码用“,”分开
     * @return
     */
    @RequestMapping(value = "/pharmacy/{id}/stock", method = RequestMethod.GET)
    Map<String, Object> checkStock(@PathVariable("id")String pharmacyId, @RequestParam("drugCodes")String drugCodes);
    /**
     * 上报处方
     * @param expireTime 锁定药品超时时间，单位MS
     * @param prescription 处方信息
     * @return
     */
    @RequestMapping(value = "/prescription", method = RequestMethod.POST)
    Map<String, Object> uploadPrescription(@RequestParam("expireTime") Integer expireTime, @RequestBody Prescription prescription);

    /**
     * 上报审核未通过处方
     * @param prescription 处方信息
     * @return
     */
    @RequestMapping(value = "/prescription/reject", method = RequestMethod.PATCH)
    Map<String, Object> rejectPrescription(@RequestBody Prescription prescription);

    /**
     * 上报审核已通过处方
     * @param prescription 处方信息
     * @return
     */
    @RequestMapping(value = "/prescription/accept", method = RequestMethod.PATCH)
    Map<String, Object> acceptPrescription(@RequestBody Prescription prescription);

    /**
     * 上报已支付成功处方
     * @param prescriptionList 支付处方列表
     * @return
     */
    @RequestMapping(value = "/prescription/paid", method = RequestMethod.PATCH)
    Map<String, Object> paidPrescription(@RequestBody List<Prescription> prescriptionList);

    /**
     * 处方取药并解锁对应药品库存
     * @param prescriptionList 取药处方列表
     * @param pharmacyId 取药药房编号
     * @param pharmacyName 取药药房名称
     * @return
     */
    @RequestMapping(value = "/prescription/used", method = RequestMethod.PATCH)
    Map<String, Object> cancelPrescription(@RequestBody List<Prescription> prescriptionList, @RequestParam("pharmacyId")String pharmacyId, @RequestParam("pharmacyName")String pharmacyName);

    /**
     * 根据患者ID查询处方信息
     * @param patientId 患者身份证号码
     * @return
     */
    @RequestMapping(value = "/prescriptions", method = RequestMethod.GET)
    Map<String, Object> listPrescriptions(@RequestParam("id")String patientId);

    /**
     * 根据处方编号查询处方信息
     * @param hospitalId 药房ID
     * @param prescriptionId 处方编号
     * @return
     */
    @RequestMapping(value = "/hospital/{hid}/prescription/{pid}", method = RequestMethod.GET)
    Map<String, Object> getPrescription(@PathVariable("hid")String hospitalId, @PathVariable("pid")String prescriptionId);
}