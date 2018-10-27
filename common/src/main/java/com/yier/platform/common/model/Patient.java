package com.yier.platform.common.model;


import com.yier.platform.common.jsonResponse.BaseJsonObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * 患者信息 model
 */
@ApiModel(value = "患者信息")
public class Patient extends BaseJsonObject {
    private final Logger logger = LoggerFactory.getLogger(Patient.class);
    @ApiModelProperty(value = "主键ID")
    private Long id; //主键ID
    @ApiModelProperty(value = "患者姓名")
    private String trueName ;//患者姓名
    @ApiModelProperty(value = "患者昵称")
    private String userName ;//患者昵称
    @ApiModelProperty(value = "身份证号码")
    private String idCardNo ;//身份证号码
    @ApiModelProperty(value = "头像URL")
    private String avatarUrl ;//头像URL
    @ApiModelProperty(value = "密码")
    private String password ;//密码
    @ApiModelProperty(value = "加盐，随机数")
    private String passSalt;
    @ApiModelProperty(value = "出生日期")
    private Date birthday;
    @ApiModelProperty(value = "电子邮箱")
    private String email ;//电子邮箱
    @ApiModelProperty(value = "手机号码")
    private String phoneNumber ;//手机号码
    @ApiModelProperty(value = "用户性别")
    private String sex ;//用户性别
    @ApiModelProperty(value = "审核URL")
    private String checkUrl;
    @ApiModelProperty(value = "第三方即时通讯工具token")
    private String imtoken;
    @ApiModelProperty(value = "状态（0正常有效 审核通过 1删除 2未提交 点击保存 4无效 5未通过 A审核中 未审核  B未复审 复审中 未通过的提交")
    private String status ;
    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate; //创建时间
    @ApiModelProperty(value = "修改时间")
    private Date gmtModified;//修改时间
    @ApiModelProperty(value = "备注信息")
    private String remarks ;//备注信息

    public Patient(Long id, String trueName, String userName, String idCardNo, String avatarUrl, String password, String passSalt, Date birthday, String email, String phoneNumber, String sex, String checkUrl, String imtoken, String status, Date gmtCreate, Date gmtModified, String remarks) {//resultMap="BaseResultMap" 时对应需要这个默认构造函数

        this.id = id;
        this.trueName = trueName;
        this.idCardNo = idCardNo;
        this.avatarUrl = avatarUrl;
        this.password = password;
        this.passSalt = passSalt;
        this.birthday = birthday;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.sex = sex;
        this.checkUrl = checkUrl;
        this.imtoken = imtoken;
        this.status = status;
        this.gmtCreate = gmtCreate;
        this.gmtModified = gmtModified;
        this.remarks = remarks;
        this.userName = userName;

    }

    public Patient(Patient patient){
        this.id = patient.id;
        this.trueName = patient.trueName;
        this.userName = patient.userName;
        this.idCardNo = patient.idCardNo;
        this.avatarUrl = patient.avatarUrl;
        this.password = patient.password;
        this.passSalt = patient.passSalt;
        this.birthday = patient.birthday;
        this.email = patient.email;
        this.phoneNumber = patient.phoneNumber;
        this.sex = patient.sex;
        this.checkUrl = patient.checkUrl;
        this.imtoken = patient.imtoken;
        this.status = patient.status;
        this.gmtCreate = patient.gmtCreate;
        this.gmtModified = patient.gmtModified;
        this.remarks = patient.remarks;
    }

    public Patient() {
        super();
    }

    /**
     * 思考特殊域操作功能
     */
    public void thinkField(){
        this.password = "密码被覆盖";
    }

    public String getPassSalt() {
        return passSalt;
    }

    public void setPassSalt(String passSalt) {
        this.passSalt = passSalt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCheckUrl() {
        return checkUrl;
    }

    public void setCheckUrl(String checkUrl) {
        this.checkUrl = checkUrl;
    }

    public String getImtoken() {
        return imtoken;
    }

    public void setImtoken(String imtoken) {
        this.imtoken = imtoken;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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