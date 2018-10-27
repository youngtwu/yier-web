package com.yier.platform.conf;

public class RongCloudConfig {
    private String appKey = "e5t4ouvpe6vpa";
    private String appSecret = "bW4zwHU6Rq";
    private String defaultPortraitUri = "https://bucketvv.oss-cn-beijing.aliyuncs.com/rongyunDefaulthead?Expires=1850266603&OSSAccessKeyId=LTAIvYuVOwMPVYsL&Signature=OM83i9GqLCB1vqYL2dROKtrAIjk%3D";//默认头像

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getDefaultPortraitUri() {
        return defaultPortraitUri;
    }

    public void setDefaultPortraitUri(String defaultPortraitUri) {
        this.defaultPortraitUri = defaultPortraitUri;
    }
}
