package com.github.luwan.spring.boot.learning.fastdfs.constants;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * @author luwan
 * @date 2020/1/10
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseData {

    private ResponseData() {
    }

    public static ResponseData fail(ResponseCodeMsg responseCodeMsg) {
        return new ResponseData(responseCodeMsg.getCode(), responseCodeMsg.getMsg(), null);
    }

    public static ResponseData fail(ResponseCodeMsg responseCodeMsg, String detailMsg) {
        return new ResponseData(responseCodeMsg.getCode(), detailMsg, null);
    }

    public static ResponseData success(Object result) {
        return new ResponseData(ResponseCodeMsg.SUCCESS.getCode(), ResponseCodeMsg.SUCCESS.getMsg(), result);
    }

    /**
     * 响应码
     */
    private int code;

    /**
     * 提示信息
     */
    private String msg;

    /**
     * 响应内容，一般存放数据
     */
    private Object result;

    private ResponseData(int code, String msg, Object result) {
        this.code = code;
        this.msg = msg;
        this.result = result;
    }
}
