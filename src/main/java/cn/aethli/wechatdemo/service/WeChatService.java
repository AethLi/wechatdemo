package cn.aethli.wechatdemo.service;

import cn.aethli.wechatdemo.exception.WeChatException;
import cn.aethli.wechatdemo.utils.WeChatUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

/**
 * @device: Hades
 * @author: Termite
 * @date: 2019-08-15 09:36
 **/
@EnableAsync
@Service
public class WeChatService {

    @Async
    public void MsgHandle(String paramStr) throws Exception {
        Thread.sleep(1000);
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
            String fileName = WeChatUtils.getWeChatTemporaryMedia(String.valueOf(msg.get("MediaId")));
            Thread.sleep(1000);
            String voiceId = WeChatUtils.voice2String(fileName);
        }
    }
}
