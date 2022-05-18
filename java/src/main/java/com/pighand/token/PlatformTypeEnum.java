package com.pighand.token;

/**
 * @author shuli495
 */
public enum PlatformTypeEnum {
    // 微信
    WECHAT("wechat"),

    // 支付宝
    ALIPAY("alipay"),

    // 飞书
    FEISHU("feishu");

    private String platformType;

    PlatformTypeEnum(String platformType) {
        this.platformType = platformType;
    }

    public String value() {
        return platformType;
    }
}
