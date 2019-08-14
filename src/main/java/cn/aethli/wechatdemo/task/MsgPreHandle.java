package cn.aethli.wechatdemo.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlFactory;

import java.io.IOException;
import java.util.Map;

/**
 * @device: Hades
 * @author: Termite
 * @date: 2019-08-14 17:56
 **/
public class MsgPreHandle implements Runnable {
    private String paramStr;

    public MsgPreHandle(String paramStr) {
        this.paramStr = paramStr;
    }

    @Override
    public void run() {
        ObjectMapper mapper = new ObjectMapper(new XmlFactory());
        Map msg = null;
        try {
            msg = mapper.readValue(paramStr, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (msg != null && "voice".equals(msg.get("MsgType"))) {

        }
    }
}
