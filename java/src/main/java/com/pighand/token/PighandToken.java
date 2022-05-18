package com.pighand.token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author shuli495
 */
@Component
public class PighandToken {

    /** 禁止全部环境redis */
    private static final String DISABLED_REDIS_ACTIVE_ALL = "all";

    @Value("${spring.profiles.active}")
    private String env;

    @Autowired private RedisTemplate redisTemplate;

    private static String staticEnv;

    private static RedisTemplate staticRedisTemplate;

    @PostConstruct
    public void init() {
        staticEnv = env;
        staticRedisTemplate = redisTemplate;
    }

    /**
     * 查询token。如果有redis，优先redis查询；未查询到从接口查询。
     *
     * @param platformType
     * @param appid
     * @return token
     * @throws Exception
     */
    public static String getToken(PlatformTypeEnum platformType, String appid) throws Exception {
        String tokenString = getTokenFromRedis(platformType, appid);

        if (!StringUtils.hasText(tokenString)) {
            String url = PighandConfiguration.token.getUrl(platformType, appid);
            tokenString = HttpUtils.connect("GET", url);
        }

        return tokenString;
    }

    /**
     * 刷新token
     *
     * @param platformType
     * @param appid
     * @return token
     * @throws Exception
     */
    public static String refreshToken(PlatformTypeEnum platformType, String appid)
            throws Exception {
        String url = PighandConfiguration.token.getUrl(platformType, appid);
        String tokenString = HttpUtils.connect("PUT", url);
        return tokenString;
    }

    /**
     * 从redis查询token
     *
     * @param platformType
     * @param appid
     * @return token
     */
    private static String getTokenFromRedis(PlatformTypeEnum platformType, String appid) {
        if (staticRedisTemplate == null) {
            return null;
        }

        // 跳过禁用redis的环境
        List<String> disabledRedisActive =
                Optional.ofNullable(PighandConfiguration.token.getDisabledRedisActive())
                        .orElse(new ArrayList<>(0));
        if (staticEnv.toLowerCase().equals(DISABLED_REDIS_ACTIVE_ALL)
                || disabledRedisActive.contains(staticEnv)) {
            return null;
        }

        try {
            String key = platformType.value() + "_" + appid;
            Object redisTokenObject = staticRedisTemplate.opsForValue().get(key);
            if (redisTokenObject != null) {
                return String.valueOf(redisTokenObject);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
