package com.pighand.token;

import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author shuli495
 */
public class HttpUtils {
    private static final String RESPONSE_CODE = "code";
    private static final String RESPONSE_DATA = "data";
    private static final String RESPONSE_ERROR_MESSAGES = "errorMsg";

    /**
     * 接口调用
     *
     * @param method
     * @param url
     * @return
     * @throws Exception
     */
    public static String connect(String method, String url) throws Exception {
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        try (AutoCloseable ac = () -> conn.disconnect()) {
            conn.setRequestMethod(method);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.connect();

            int code = conn.getResponseCode();
            if (code != HttpURLConnection.HTTP_OK) {
                throw new Exception(conn.getResponseMessage());
            }

            String resultString = "";
            String line = null;
            try (BufferedReader reader =
                    new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                while ((line = reader.readLine()) != null) {
                    resultString += line + "\n";
                }
            }

            return result(resultString);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 处理返回值
     *
     * @param result
     * @return
     * @throws Exception
     */
    private static String result(String result) throws Exception {
        JSONObject resultObject = JSONObject.parseObject(result);

        if (resultObject.getInteger(RESPONSE_CODE) != HttpURLConnection.HTTP_OK) {
            throw new Exception(resultObject.getString(RESPONSE_DATA));
        }

        return resultObject.getString(RESPONSE_ERROR_MESSAGES);
    }
}
