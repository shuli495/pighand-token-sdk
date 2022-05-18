package com.pighand.token;

import java.util.List;

/**
 * pighand_token 配置
 *
 * @author shuli495
 */
public class PighandTokenProperties {
    private String host;

    private static final String API = "/token/%s/%s";

    /**
     * 禁用redis的环境。用于区分不同环境的不同取token方式
     *
     * <p>eg: dev、test从url取token；prod从redis中取
     *
     * @param all 全部环境禁用
     */
    private List<String> disabledRedisActive;

    /**
     * 获取pighand_token服务完整url
     *
     * @param platformType
     * @param appid
     * @return
     */
    public String getUrl(PlatformTypeEnum platformType, String appid) {
        return String.format(host + API, platformType.value(), appid);
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public List<String> getDisabledRedisActive() {
        return disabledRedisActive;
    }

    public void setDisabledRedisActive(List<String> disabledRedisActive) {
        this.disabledRedisActive = disabledRedisActive;
    }
}
