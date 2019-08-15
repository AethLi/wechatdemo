package cn.aethli.wechatdemo.controller;

import cn.aethli.wechatdemo.service.WeChatService;
import cn.aethli.wechatdemo.utils.EncryptionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * @device: Hades
 * @author: Termite
 * @date: 2019-08-12 11:08
 **/
@ControllerAdvice
@RequestMapping(value = "/")
@Slf4j
public class WeChatCtrl {

    //token
    private final String token = "aethli";
    @Autowired
    private WeChatService weChatService;

    @RequestMapping(value = "/wechat")
    public void wechatCtrl(HttpServletRequest request, HttpServletResponse response,
                           @RequestParam(value = "signature", required = false) String signature,
                           @RequestParam(value = "timestamp", required = false) String timestamp,
                           @RequestParam(value = "nonce", required = false) String nonce,
                           @RequestParam(value = "echostr", required = false) String echostr,
                           @RequestBody(required = false) String paramStr) throws Exception {
        String[] paramsStr = {token, timestamp, nonce};
        Arrays.sort(paramsStr);
        StringBuilder builder = new StringBuilder();
        for (String str : paramsStr) {
            builder.append(str);
        }
        String s = EncryptionUtils.toSHA1(builder.toString());
        if (!s.equals(signature)) {
            log.error("校验失败");
            return;
        }
        if (echostr == null) {
            echostr = "";
        }
        if (paramStr != null) {
            weChatService.MsgHandle(paramStr);
        }
        response.getWriter().println(echostr);
    }
}
