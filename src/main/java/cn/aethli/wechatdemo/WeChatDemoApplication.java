package cn.aethli.wechatdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WeChatDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeChatDemoApplication.class, args);
//        try {
//            WeChatAccessToken token = WeChatUtils.accessTokenGet();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        try {
//            WeChatUtils.menuUpdate();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (WeChatException e) {
//            e.printStackTrace();
//        }
    }


}
