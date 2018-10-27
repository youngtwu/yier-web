package com.yier.platform.common.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yier.platform.common.jsonResponse.BaseJsonObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@ApiModel(value = "上门就诊表")
public class MedicalOrder extends BaseJsonObject {
    @ApiModelProperty(value = "主键标识Id")
    private Long id;
    @ApiModelProperty(value = "订单号")
    private String orderNo;
    @ApiModelProperty(value = "医院ID,关联t_hospital.id")
    private Long hospitalId;
    @ApiModelProperty(value = "医院名字 t_patient.name")
    private String hospitalName;
    @ApiModelProperty(value = "医院地址")
    private String hospitalAddress;
    @ApiModelProperty(value = "联系电话")
    private String hospitalContact;
    @ApiModelProperty(value = "所属科室类别ID。关联到t_department_catalog")
    private Long catalogId;
    @ApiModelProperty(value = "所属科室ID。关联到t_hospital_department")
    private Long departmentId;
    @ApiModelProperty(value = "科室名称")
    private String departmentName;
    @ApiModelProperty(value = "患者ID,关联t_patient.id")
    private Long patientId;
    @ApiModelProperty(value = "患者名字 t_patient.true_name")
    private String patientName;
    @ApiModelProperty(value = "患者身份证号码")
    private String patientIdCardNo;
    @ApiModelProperty(value = "患者地址摘要")
    private String patientAddressSummary;
    @ApiModelProperty(value = "患者地址")
    private String patientAddress;
    @ApiModelProperty(value = "患者联系电话")
    private String patientContact;
    @ApiModelProperty(value = "患者用户性别")
    private String patientSex;
    @ApiModelProperty(value = "联系电话")
    private String patientYearOld;
    @ApiModelProperty(value = "患者头像")
    private String patientAvatarUrl;
    @ApiModelProperty(value = "患者到现场的经度")
    private Double patientLng;
    @ApiModelProperty(value = "患者到现场的纬度")
    private Double patientLat;
    @ApiModelProperty(value = "医生ID,关联t_doctor.id")
    private Long doctorId;
    @ApiModelProperty(value = "医生名字 t_doctor.true_name")
    private String doctorName;
    @ApiModelProperty(value = "开方医生职称")
    private String doctorTitle;
    @ApiModelProperty(value = "医生电子签名 担责")
    private String doctorSignature;
    @ApiModelProperty(value = "医生到现场的经度")
    private Double doctorLng;
    @ApiModelProperty(value = "医生到现场的纬度")
    private Double doctorLat;
    @ApiModelProperty(value = "医疗情况:初诊 还是复诊")
    private String medicalCase;
    @ApiModelProperty(value = "医疗疾病信息")
    private String medicalInfo;
    @ApiModelProperty(value = "化验报告")
    private String medicalPictures;
    @ApiModelProperty(value = "门诊医生预约日期id")
    private Long medicalDateId;
    @ApiModelProperty(value = "预约上门就诊时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd") // 出参
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8") // 界面入参
    private Date planTime;
    @ApiModelProperty(value = "11表示全天，10表示上午，01表示下午")
    private String workTime;
    @ApiModelProperty(value = "开始时间，就诊开始区间")
    private Date workFrom;
    @ApiModelProperty(value = "结束时间，就诊结束区间")
    private Date workTo;
    @ApiModelProperty(value = "医生离开现场的经度")
    private Double doctorLeaveLng;
    @ApiModelProperty(value = "医生离开现场的纬度")
    private Double doctorLeaveLat;
    @ApiModelProperty(value = "支付费用 单位是分")
    private Integer payment;
    @ApiModelProperty(value = "提醒,注意信息")
    private String remind;
    @ApiModelProperty(value = "其他订单信息")
    private String other;
    @ApiModelProperty(value = "医生上门上传图片凭证")
    private String homePicture;
    @ApiModelProperty(value = "药房 药库ID,关联t_pharmacy.id可能是第三方")
    private String pharmacyId;
    @ApiModelProperty(value = "药房名字 t_patient.true_name")
    private String pharmacyName;
    @ApiModelProperty(value = "药房名字 地址")
    private String pharmacyAddress;
    @ApiModelProperty(value = "联系电话")
    private String pharmacyContact;
    @ApiModelProperty(value = "订单终止时间")
    private Date overTime;
    @ApiModelProperty(value = "订单终止类型")
    private String overType;
    @ApiModelProperty(value = "状态")
    private String status;
    @ApiModelProperty(value = "医生面看到的状态")
    private String statusDetail;
    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;
    @ApiModelProperty(value = "修改时间")
    private Date gmtModified;
    @ApiModelProperty(value = "备注信息")
    private String remarks;
    @ApiModelProperty(value = "门诊图片")
    private List<String> medicalPicturesList;

    @ApiModelProperty(value = "订单号列表")
    private List<String> orderNoList;

    public List<String> getOrderNoList() {
        return orderNoList;
    }

    public void setOrderNoList(List<String> orderNoList) {
        this.orderNoList = orderNoList;
    }

    public List<String> getMedicalPicturesList() {
        return medicalPicturesList;
    }

    public void setMedicalPicturesList(List<String> medicalPicturesList) {
        this.medicalPicturesList = medicalPicturesList;
    }
    public MedicalOrder(Long id, String orderNo, Long hospitalId, String hospitalName, String hospitalAddress, String hospitalContact, Long catalogId, Long departmentId, String departmentName, Long patientId, String patientName, String patientIdCardNo, String patientAddressSummary, String patientAddress, String patientContact, String patientSex, String patientYearOld, String patientAvatarUrl, Double patientLng, Double patientLat, Long doctorId, String doctorName, String doctorTitle, String doctorSignature, Double doctorLng, Double doctorLat, String medicalCase, String medicalInfo, String medicalPictures, Long medicalDateId, Date planTime, String workTime, Date workFrom, Date workTo, Double doctorLeaveLng, Double doctorLeaveLat, Integer payment, String remind, String other, String homePicture, String pharmacyId, String pharmacyName, String pharmacyAddress, String pharmacyContact, Date overTime, String overType, String status, String statusDetail, Date gmtCreate, Date gmtModified, String remarks) {
        this.id = id;
        this.orderNo = orderNo;
        this.hospitalId = hospitalId;
        this.hospitalName = hospitalName;
        this.hospitalAddress = hospitalAddress;
        this.hospitalContact = hospitalContact;
        this.catalogId = catalogId;
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.patientId = patientId;
        this.patientName = patientName;
        this.patientIdCardNo = patientIdCardNo;
        this.patientAddressSummary = patientAddressSummary;
        this.patientAddress = patientAddress;
        this.patientContact = patientContact;
        this.patientSex = patientSex;
        this.patientYearOld = patientYearOld;
        this.patientAvatarUrl = patientAvatarUrl;
        this.patientLng = patientLng;
        this.patientLat = patientLat;
        this.doctorId = doctorId;
        this.doctorName = doctorName;
        this.doctorTitle = doctorTitle;
        this.doctorSignature = doctorSignature;
        this.doctorLng = doctorLng;
        this.doctorLat = doctorLat;
        this.medicalCase = medicalCase;
        this.medicalInfo = medicalInfo;
        this.medicalPictures = medicalPictures;
        this.medicalDateId = medicalDateId;
        this.planTime = planTime;
        this.workTime = workTime;
        this.workFrom = workFrom;
        this.workTo = workTo;
        this.doctorLeaveLng = doctorLeaveLng;
        this.doctorLeaveLat = doctorLeaveLat;
        this.payment = payment;
        this.remind = remind;
        this.other = other;
        this.homePicture = homePicture;
        this.pharmacyId = pharmacyId;
        this.pharmacyName = pharmacyName;
        this.pharmacyAddress = pharmacyAddress;
        this.pharmacyContact = pharmacyContact;
        this.overTime = overTime;
        this.overType = overType;
        this.status = status;
        this.statusDetail = statusDetail;
        this.gmtCreate = gmtCreate;
        this.gmtModified = gmtModified;
        this.remarks = remarks;
    }

    public MedicalOrder() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Long getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(Long hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getHospitalAddress() {
        return hospitalAddress;
    }

    public void setHospitalAddress(String hospitalAddress) {
        this.hospitalAddress = hospitalAddress;
    }

    public String getHospitalContact() {
        return hospitalContact;
    }

    public void setHospitalContact(String hospitalContact) {
        this.hospitalContact = hospitalContact;
    }

    public Long getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(Long catalogId) {
        this.catalogId = catalogId;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientIdCardNo() {
        return patientIdCardNo;
    }

    public void setPatientIdCardNo(String patientIdCardNo) {
        this.patientIdCardNo = patientIdCardNo;
    }

    public String getPatientAddressSummary() {
        return patientAddressSummary;
    }

    public void setPatientAddressSummary(String patientAddressSummary) {
        this.patientAddressSummary = patientAddressSummary;
    }

    public String getPatientAddress() {
        return patientAddress;
    }

    public void setPatientAddress(String patientAddress) {
        this.patientAddress = patientAddress;
    }

    public String getPatientContact() {
        return patientContact;
    }

    public void setPatientContact(String patientContact) {
        this.patientContact = patientContact;
    }

    public String getPatientSex() {
        return patientSex;
    }

    public void setPatientSex(String patientSex) {
        this.patientSex = patientSex;
    }

    public String getPatientYearOld() {
        return patientYearOld;
    }

    public void setPatientYearOld(String patientYearOld) {
        this.patientYearOld = patientYearOld;
    }

    public String getPatientAvatarUrl() {
        return patientAvatarUrl;
    }

    public void setPatientAvatarUrl(String patientAvatarUrl) {
        this.patientAvatarUrl = patientAvatarUrl;
    }

    public Double getPatientLng() {
        return patientLng;
    }

    public void setPatientLng(Double patientLng) {
        this.patientLng = patientLng;
    }

    public Double getPatientLat() {
        return patientLat;
    }

    public void setPatientLat(Double patientLat) {
        this.patientLat = patientLat;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorTitle() {
        return doctorTitle;
    }

    public void setDoctorTitle(String doctorTitle) {
        this.doctorTitle = doctorTitle;
    }

    public String getDoctorSignature() {
        return doctorSignature;
    }

    public void setDoctorSignature(String doctorSignature) {
        this.doctorSignature = doctorSignature;
    }

    public Double getDoctorLng() {
        return doctorLng;
    }

    public void setDoctorLng(Double doctorLng) {
        this.doctorLng = doctorLng;
    }

    public Double getDoctorLat() {
        return doctorLat;
    }

    public void setDoctorLat(Double doctorLat) {
        this.doctorLat = doctorLat;
    }

    public String getMedicalCase() {
        return medicalCase;
    }

    public void setMedicalCase(String medicalCase) {
        this.medicalCase = medicalCase;
    }

    public String getMedicalInfo() {
        return medicalInfo;
    }

    public void setMedicalInfo(String medicalInfo) {
        this.medicalInfo = medicalInfo;
    }

    public String getMedicalPictures() {
        return medicalPictures;
    }

    public void setMedicalPictures(String medicalPictures) {
        this.medicalPictures = medicalPictures;
    }

    public Long getMedicalDateId() {
        return medicalDateId;
    }

    public void setMedicalDateId(Long medicalDateId) {
        this.medicalDateId = medicalDateId;
    }

    public Date getPlanTime() {
        return planTime;
    }

    public void setPlanTime(Date planTime) {
        this.planTime = planTime;
    }

    public String getWorkTime() {
        return workTime;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }

    public Date getWorkFrom() {
        return workFrom;
    }

    public void setWorkFrom(Date workFrom) {
        this.workFrom = workFrom;
    }

    public Date getWorkTo() {
        return workTo;
    }

    public void setWorkTo(Date workTo) {
        this.workTo = workTo;
    }

    public Double getDoctorLeaveLng() {
        return doctorLeaveLng;
    }

    public void setDoctorLeaveLng(Double doctorLeaveLng) {
        this.doctorLeaveLng = doctorLeaveLng;
    }

    public Double getDoctorLeaveLat() {
        return doctorLeaveLat;
    }

    public void setDoctorLeaveLat(Double doctorLeaveLat) {
        this.doctorLeaveLat = doctorLeaveLat;
    }

    public Integer getPayment() {
        return payment;
    }

    public void setPayment(Integer payment) {
        this.payment = payment;
    }

    public String getRemind() {
        return remind;
    }

    public void setRemind(String remind) {
        this.remind = remind;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getHomePicture() {
        return homePicture;
    }

    public void setHomePicture(String homePicture) {
        this.homePicture = homePicture;
    }

    public String getPharmacyId() {
        return pharmacyId;
    }

    public void setPharmacyId(String pharmacyId) {
        this.pharmacyId = pharmacyId;
    }

    public String getPharmacyName() {
        return pharmacyName;
    }

    public void setPharmacyName(String pharmacyName) {
        this.pharmacyName = pharmacyName;
    }

    public String getPharmacyAddress() {
        return pharmacyAddress;
    }

    public void setPharmacyAddress(String pharmacyAddress) {
        this.pharmacyAddress = pharmacyAddress;
    }

    public String getPharmacyContact() {
        return pharmacyContact;
    }

    public void setPharmacyContact(String pharmacyContact) {
        this.pharmacyContact = pharmacyContact;
    }

    public Date getOverTime() {
        return overTime;
    }

    public void setOverTime(Date overTime) {
        this.overTime = overTime;
    }

    public String getOverType() {
        return overType;
    }

    public void setOverType(String overType) {
        this.overType = overType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusDetail() {
        return statusDetail;
    }

    public void setStatusDetail(String statusDetail) {
        this.statusDetail = statusDetail;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}