package cn.aethli.wechatdemo;

import cn.aethli.wechatdemo.entity.WeChatAccessToken;
import cn.aethli.wechatdemo.utils.WeChatUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class WeChatDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeChatDemoApplication.class, args);
        try {
            WeChatAccessToken token = WeChatUtils.accessTokenGet();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            WeChatUtils.menuUpdate();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
