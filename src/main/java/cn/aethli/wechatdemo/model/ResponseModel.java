package cn.aethli.wechatdemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseModel {
    public static final int STATUS_OK = 0;
    public static final int STATUS_FAIL = 1;
    public static final int STATUS_ERROR = 2;
    int status;
    String msg;
    Object data;

    public ResponseModel(int status) {
        this.status = status;
    }

    public ResponseModel(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public ResponseModel(int status, Object data) {
        this.status = status;
        this.data = data;
    }

}
