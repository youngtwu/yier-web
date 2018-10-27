package com.yier.platform.common.po;

import com.yier.platform.common.jsonResponse.JsonResponse;
import io.swagger.annotations.ApiModel;

@ApiModel(value = "身份证信息类")
public class IdCardClass  extends JsonResponse {
    private String resultcode = "";
    private String reason = "";
    private IdCardInfo result;
    private Integer error_code ;

    public static class IdCardInfo {
        private String area = "";
        private String sex = "";
        private String birthday = "";
        private String verify = "";

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getVerify() {
            return verify;
        }

        public void setVerify(String verify) {
            this.verify = verify;
        }
    }

    public String getResultcode() {
        return resultcode;
    }

    public void setResultcode(String resultcode) {
        this.resultcode = resultcode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public IdCardInfo getResult() {
        return result;
    }

    public void setResult(IdCardInfo result) {
        this.result = result;
    }

    public Integer getError_code() {
        return error_code;
    }

    public void setError_code(Integer error_code) {
        this.error_code = error_code;
    }
}
