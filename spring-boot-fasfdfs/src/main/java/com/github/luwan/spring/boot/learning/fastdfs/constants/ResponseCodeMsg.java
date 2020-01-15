package com.github.luwan.spring.boot.learning.fastdfs.constants;

/**
 * @author luwan
 * @date 2020/1/10
 */
public enum ResponseCodeMsg {

    // 正常
    SUCCESS(0, "OK!"),

    ILLEGAL_PARAM(1001, "illegal param!"),

    SERVER_ERR(2001, "Unknown server exception!"),

    FILE_SERVER_CONNECTION_FAILED(2002, "File server connection failed!"),

    FILE_UPLOAD_FAILED(2003, "File upload err!"),

    FILE_DOWNLOAD_FAILED(2004, "File download err!"),

    FILE_DEL_FAILED(2005, "File delete err!"),

    FILE_GET_TOKEN_FAILED(2006, "get token err!");


    /**
     * 返回提示码
     */
    private int code;

    /**
     * 返回提示信息
     */
    private String msg;

    ResponseCodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
