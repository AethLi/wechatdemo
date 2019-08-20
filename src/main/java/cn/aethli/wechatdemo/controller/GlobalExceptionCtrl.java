package cn.aethli.wechatdemo.controller;

import cn.aethli.wechatdemo.exception.WeChatException;
import cn.aethli.wechatdemo.model.ResponseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @device: Hades
 * @author: Termite
 * @date: 2019-08-05 10:15
 **/
@RestControllerAdvice
public class GlobalExceptionCtrl {

  private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionCtrl.class);

  /**
   * 全局所有controller默认异常处理
   *
   * @param e
   * @return
   */
  @ExceptionHandler(value = Exception.class)
  public Object exceptionCatch(Exception e) {
    logger.info("exceptionCatch");
    logger.error(e.toString());
    e.printStackTrace();
    return new ResponseModel(ResponseModel.STATUS_ERROR, e.getMessage());
  }

  @ExceptionHandler(value = WeChatException.class)
  public Object weChatExceptionCatch(Exception e) {
    logger.info("weChatExceptionCatch");
    logger.error(e.toString());
    return null;
  }
}
