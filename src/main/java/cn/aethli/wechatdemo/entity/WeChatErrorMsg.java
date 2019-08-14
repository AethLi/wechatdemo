package cn.aethli.wechatdemo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * @device: Hades
 * @author: Termite
 * @date: 2019-08-13 11:47
 **/
@Data
@Entity
@NoArgsConstructor
@Table(name = "we_chat_error_msg")
public class WeChatErrorMsg {
    @Id
    @Column(name = "id", length = 36)
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
    private String id;
    @Column(name = "err_code", length = 15)
    @JsonProperty("errcode")
    private String errCode;
    @Column(name = "err_msg", length = 255)
    @JsonProperty("errmsg")
    private String errMsg;
    @Column(name = "create_date")
    private Date createDate;
}
