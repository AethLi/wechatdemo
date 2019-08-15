package cn.aethli.wechatdemo.exception;

import lombok.Data;

/**
 * @device: Hades
 * @author: Termite
 * @date: 2019-08-12 14:17
 **/
@Data
public class WeChatException extends Exception {
    public static final int ACCESS_TOKEN_GET_FAIL = 1;
    public static final int ERROR_MSG_RECV = 2;

    private int type;

    public WeChatException(String message, int type) {
        super(message);
        this.type = type;
    }
}
