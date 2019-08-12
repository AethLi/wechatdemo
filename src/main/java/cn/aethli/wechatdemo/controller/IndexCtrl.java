package cn.aethli.wechatdemo.controller;

import cn.aethli.wechatdemo.utils.EncryptionUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
 * @device: Hades
 * @author: Termite
 * @date: 2019-08-12 11:08
 **/
@ControllerAdvice
@RequestMapping(value = "/")
public class IndexCtrl {

    //token
    private final String token = "aethli";

    @RequestMapping(value = "/wechat")
    public void wechatCtrl(HttpServletRequest request, HttpServletResponse response,
                           @RequestParam("signature") String signature, @RequestParam("timestamp") String timestamp,
                           @RequestParam("nonce") String nonce, @RequestParam("echostr") String echostr) throws IOException {
        String[] paramsStr = {token, timestamp, nonce};
        Arrays.sort(paramsStr);
        StringBuilder builder = new StringBuilder();
        for (String str : paramsStr) {
            builder.append(str);
        }
        String s = EncryptionUtils.toSHA1(builder.toString());
        if (!s.equals(signature)) {
            System.out.println("校验失败");
            return;
        }
        System.out.println("校验成功");
        response.getWriter().println(echostr);
    }
}
