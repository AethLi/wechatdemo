package cn.aethli.wechatdemo.exception;

import lombok.Data;

/**
 * @device: Hades
 * @author: Termite
 * @date: 2019-08-12 14:17
 **/
@Data
public class WeChatException extends Throwable {
    private int type;
}
