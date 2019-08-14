package cn.aethli.wechatdemo.repository;

import cn.aethli.wechatdemo.entity.WeChatErrorMsg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @device: Hades
 * @author: Termite
 * @date: 2019-08-13 14:21
 **/
public interface WeChatErrorMsgRepository extends JpaRepository<WeChatErrorMsg, String>, JpaSpecificationExecutor<WeChatErrorMsg> {
}
