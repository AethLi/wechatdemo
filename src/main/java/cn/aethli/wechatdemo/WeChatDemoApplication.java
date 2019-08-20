package cn.aethli.wechatdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class WeChatDemoApplication {

  public static void main(String[] args) {
    SpringApplication.run(WeChatDemoApplication.class, args);
  }


}
