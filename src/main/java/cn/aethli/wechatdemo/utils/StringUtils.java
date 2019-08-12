package cn.aethli.wechatdemo.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * @author 93162
 * @computer Apollo
 * @date 2019/7/14 19:27
 */
public class StringUtils {

    /**
     * 网址后续参数构成
     *
     * @param params
     * @return
     */
    public static String parameterBuild(Map<String, String> params) {
        StringBuilder result = new StringBuilder();
        if (params.size() != 0) {
            for (String paramsName : params.keySet()) {
                result.append(paramsName + "=" + params.get(paramsName) + "&");
            }
            return result.toString().substring(0, result.length() - 1);
        }
        return "";
    }
}
