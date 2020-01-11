package com.github.luwan.spring.boot.learning.fastdfs.exception;

import com.github.luwan.spring.boot.learning.fastdfs.constants.ResponseCodeMsg;
import lombok.Data;

/**
 * @author luwan
 * @date 2020/1/10
 */
@Data
public class FastDFSException extends Exception {

    /**
     * 错误码
     */
    private int code;

    /**
     * 错误消息
     */
    private String message;

    public FastDFSException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static FastDFSException instance(ResponseCodeMsg responseCodeMsg) {
        return new FastDFSException(responseCodeMsg.getCode(), responseCodeMsg.getMsg());
    }
}
