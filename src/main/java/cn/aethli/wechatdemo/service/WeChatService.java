package cn.aethli.wechatdemo.service;

import cn.aethli.wechatdemo.controller.feign.WeChatFeign;
import cn.aethli.wechatdemo.exception.WeChatException;
import cn.aethli.wechatdemo.utils.WeChatUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlFactory;
import java.io.IOException;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

/**
 * @device: Hades
 * @author: Termite
 * @date: 2019-08-15 09:36
 **/
@EnableAsync
@Service
public class WeChatService {

  @Autowired
  private WeChatFeign weChatFeign;
  @Autowired
  private StringRedisTemplate stringRedisTemplate;


  @Async
  public void MsgHandle(String paramStr) throws Exception {
    ObjectMapper mapper = new ObjectMapper(new XmlFactory());
    Map msg = null;
    try {
      msg = mapper.readValue(paramStr, Map.class);
    } catch (IOException e) {
      e.printStackTrace();
    }
    if (msg == null) {
      throw new WeChatException("error msg", WeChatException.ERROR_MSG_RECV);
    }
    if ("voice".equals(msg.get("MsgType"))) {
      String recognition = String.valueOf(msg.get("Recognition"));
      System.out.println(recognition);
      if (recognition.contains("全部")) {
//        String template = "{\n"
//            + "    \"touser\":\"OPENID\",\n"
//            + "    \"msgtype\":\"text\",\n"
//            + "    \"text\":\n"
//            + "    {\n"
//            + "         \"content\":\"MESSAGE\"\n"
//            + "    }\n"
//            + "}";
//        String accessToken = stringRedisTemplate.opsForValue().get("accessToken");
//        if (accessToken == null) {
//          WeChatUtils.accessTokenGet();
//          accessToken = stringRedisTemplate.opsForValue().get("accessToken");
//          if (accessToken == null) {
//            throw new WeChatException("access_token Get fail",
//                WeChatException.ACCESS_TOKEN_GET_FAIL);
//          }
//        }
//        weChatFeign.sendMsg(template, accessToken);
//        WeChatUtils.sendMessage("cn.bing.com", String.valueOf(msg.get("FromUserName")));
        WeChatUtils.sendMessage("8k0TORPXzrPUXjqvoyRVDtI4l8jl_KYmZl0WPCROp0xcTup8PB7MYqCwfO7gnPcm",
            String.valueOf(msg.get("FromUserName")));

      }
    }
  }
}
