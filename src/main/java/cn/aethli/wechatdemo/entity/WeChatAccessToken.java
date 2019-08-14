package cn.aethli.wechatdemo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @device: Hades
 * @author: Termite
 * @date: 2019-08-12 14:19
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeChatAccessToken {
    //获取到的凭证
    @JsonProperty("access_token")
    private String accessToken;
    //凭证有效时间，单位：秒
    @JsonProperty("expires_in")
    private Long expiresIn;
}
