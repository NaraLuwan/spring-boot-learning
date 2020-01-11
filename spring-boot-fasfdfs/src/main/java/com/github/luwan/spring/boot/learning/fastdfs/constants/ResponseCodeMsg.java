package com.github.luwan.spring.boot.learning.fastdfs.constants;

/**
 * @author luwan
 * @date 2020/1/10
 */
public enum ResponseCodeMsg {

    // 正常
    SUCCESS(0, "OK!"),

    // =========================== 参数异常 ====================================

    ILLEGAL_JSON(1001, "JSON format parsing err!"),

    FILE_NOT_EXISTS(1002, "File not exists!"),

    FILE_OUT_OF_SIZE(1003, "File out of size!"),

    // =========================== 代码错误 ====================================
    SERVER_ERR(2001, "Unknown server exception!"),

    FILE_UPLOAD_FAILED(2003, "File upload err!"),

    FILE_DOWNLOAD_FAILED(2004, "File download err!"),

    FILE_SERVER_CONNECTION_FAILED(2002, "File server connection failed!");

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
