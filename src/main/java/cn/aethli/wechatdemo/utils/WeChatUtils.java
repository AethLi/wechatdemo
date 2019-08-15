package cn.aethli.wechatdemo.utils;

import cn.aethli.wechatdemo.entity.WeChatAccessToken;
import cn.aethli.wechatdemo.exception.WeChatException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import sun.net.www.protocol.https.Handler;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @device: Hades
 * @author: Termite
 * @date: 2019-08-12 14:27
 **/
@Component
public class WeChatUtils {

    private static String tokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&";
    private static String menuUrl = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=";
    private static String temporaryMediaUrl = "https://api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";
    private static String voice2StringUrl = "http://api.weixin.qq.com/cgi-bin/media/voice/addvoicetorecofortext?access_token=ACCESS_TOKEN&format=FORMAT&voice_id=VOICE_Id&lang=zh_CN";
    private static ObjectMapper objectMapper = new ObjectMapper();
    private static StringRedisTemplate stringRedisTemplate;

    /**
     * 获取token并写入redis
     *
     * @return
     * @throws IOException
     */
    public static WeChatAccessToken accessTokenGet() throws IOException {
        Properties properties = new Properties();
        String resourcePath = WeChatUtils.class.getClassLoader().getResource("").getPath();
        resourcePath += "/wechat.properties";
        InputStream inputStream = new FileInputStream(resourcePath);
        properties.load(inputStream);
        Map<String, String> params = new HashMap<>();
        params.put("appId", properties.getProperty("appId"));
        params.put("secret", properties.getProperty("appsecret"));
        StringBuffer sb = new StringBuffer();
        BufferedReader in = null;
        String result;
        String line;
        URL mainUrl = new URL(tokenUrl + StringUtils.parameterBuild(params));
        // 打开和URL之间的连接
        HttpURLConnection conn = (HttpURLConnection) mainUrl.openConnection();
        conn.setRequestProperty("content-type", "text/html;charset=utf-8");
        //设置请求方法
        conn.setRequestMethod("GET");
        //设置是否使用cookie
        conn.setUseCaches(false);
        //设置超时时间
        conn.setConnectTimeout(8000);
        conn.setReadTimeout(8000);
        // 建立实际的连接
        conn.connect();
        // 定义 BufferedReader输入流来读取URL的响应,设置接收格式
        in = new BufferedReader(new InputStreamReader(
                conn.getInputStream(), "UTF-8"));
        while ((line = in.readLine()) != null) {
            sb.append(line);
        }
        result = sb.toString();
        WeChatAccessToken token = objectMapper.readValue(result, WeChatAccessToken.class);
        stringRedisTemplate.opsForValue().set("accessToken", token.getAccessToken(), token.getExpiresIn(), TimeUnit.SECONDS);
        System.out.println(token);
        return token;
    }

    public static void menuUpdate() throws IOException, WeChatException {
        String menuJsonPath = WeChatUtils.class.getClassLoader().getResource("").getPath();
        menuJsonPath += "/templates/menu.json";
        JsonNode jsonNode = objectMapper.readTree(new File(menuJsonPath));
        String accessToken = stringRedisTemplate.opsForValue().get("accessToken");
        if (accessToken == null) {
            accessTokenGet();
            accessToken = stringRedisTemplate.opsForValue().get("accessToken");
            if (accessToken == null) {
                throw new WeChatException("access_token Get fail", WeChatException.ACCESS_TOKEN_GET_FAIL);
            }
        }
        URL url = new URL(menuUrl + accessToken + "&");
        String result;
        BufferedReader reader;
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setUseCaches(false);
        conn.setRequestProperty("Charset", "UTF-8");
        // 设置文件类型:
        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        conn.setRequestProperty("accept", "application/json");
        // 往服务器里面发送数据
        byte[] writeBytes = objectMapper.writeValueAsString(jsonNode).getBytes();
        // 设置文件长度
        conn.setRequestProperty("Content-Length", String.valueOf(writeBytes.length));
        OutputStream outWriteStream = conn.getOutputStream();
        outWriteStream.write(writeBytes);
        outWriteStream.flush();
        outWriteStream.close();
        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
            reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            result = reader.readLine();
            reader.close();
            System.out.println(result);
        }
    }

    public static String getWeChatTemporaryMedia(String mediaId) throws IOException, WeChatException {
        String accessToken = stringRedisTemplate.opsForValue().get("accessToken");
        if (accessToken == null) {
            accessTokenGet();
            accessToken = stringRedisTemplate.opsForValue().get("accessToken");
            if (accessToken == null) {
                throw new WeChatException("access_token Get fail", WeChatException.ACCESS_TOKEN_GET_FAIL);
            }
        }
        String replacedUrl = temporaryMediaUrl.replace("ACCESS_TOKEN", accessToken);
        replacedUrl = replacedUrl.replace("MEDIA_ID", mediaId);
        URL url = new URL(replacedUrl);
        // 打开和URL之间的连接
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        //设置请求方法
        conn.setRequestMethod("GET");
        // 建立实际的连接
        conn.connect();
        String filename = "";
        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {

            filename = conn.getHeaderField("Content-disposition");
            filename = filename.substring(filename.indexOf("=") + 1).replace("\"", "");

            //获得网络字节输入流对象
            InputStream is = conn.getInputStream();
            File file = new File("e:\\dl\\" + filename);
            //建立内存到硬盘的连接
            FileOutputStream fos = new FileOutputStream(file);

            //写文件
            byte[] b = new byte[1024];
            int len;
            while ((len = is.read(b)) != -1) {  //先读到内存
                fos.write(b, 0, len);
            }
            fos.flush();
            fos.close();
            is.close();
            System.out.println("下载成功");
        }
        return filename;
    }

    public static String voice2String(String fileName) throws IOException, WeChatException {
        //生成URL
        String voiceId = UUID.randomUUID().toString();
        String accessToken = stringRedisTemplate.opsForValue().get("accessToken");
        if (accessToken == null) {
            accessTokenGet();
            accessToken = stringRedisTemplate.opsForValue().get("accessToken");
            if (accessToken == null) {
                throw new WeChatException("access_token Get fail", WeChatException.ACCESS_TOKEN_GET_FAIL);
            }
        }
        String replacedUrl = voice2StringUrl.replace("ACCESS_TOKEN", accessToken);
        replacedUrl = replacedUrl.replace("VOICE_ID", voiceId);
        replacedUrl = replacedUrl.replace("FORMAT", fileName.substring(fileName.lastIndexOf(".")));
        URL url = new URL(null, replacedUrl, new Handler());

        //获取文件读取流
        File file = new File("e://dl" + File.separator + fileName);
        InputStream in = new FileInputStream(file);

        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setConnectTimeout(10 * 1000);
        conn.setDoOutput(true); // 允许输出
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Charset", "utf-8");
        String Boundary = UUID.randomUUID().toString();
        conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + Boundary);
        DataOutputStream out = new DataOutputStream(conn.getOutputStream());
        out.writeUTF("--" + Boundary + "\r\n"
                + "Content-Disposition: form-data; name=\"file\"; filename=\"filename\"\r\n"
                + "Content-Type: application/octet-stream; charset=utf-8" + "\r\n\r\n");
        byte[] b = new byte[1024];
        int l = 0;
        while ((l = in.read(b)) != -1) out.write(b, 0, l); // 写入文件
        out.writeUTF("\r\n--" + Boundary + "--\r\n");
        out.flush();
        out.close();
        in.close();
        BufferedReader bf = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
        String line = null;
        while ((line = bf.readLine()) != null) {
            System.out.println(line);
        }
        return voiceId;
    }

    @Autowired
    public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
        WeChatUtils.stringRedisTemplate = stringRedisTemplate;
    }
}
