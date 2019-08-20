package cn.aethli.wechatdemo.controller.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @device: Hades
 * @author: Termite
 * @date: 2019-08-20 16:07
 **/
@FeignClient(url = "https://api.weixin.qq.com", name = "weChat")
public interface WeChatFeign {

  @RequestMapping(value = "/message/custom/send?access_token={access_token}", method = RequestMethod.POST)
  String sendMsg(@RequestBody String context,
      @PathVariable("access_token") String access_token);

}
