package com.github.luwan.spring.boot.learning.fastdfs.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.github.luwan.spring.boot.learning.fastdfs.constants.ResponseCodeMsg;
import com.github.luwan.spring.boot.learning.fastdfs.constants.ResponseData;
import com.github.luwan.spring.boot.learning.fastdfs.exception.FastDFSException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 异常统一处理
 *
 * @author luwan
 * @date 2020/1/10
 */
@Slf4j
@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(JsonParseException.class)
    @ResponseBody
    public ResponseData handleJsonFormatException(JsonParseException e) {
        log.error("JSON format parsing err!", e);
        return ResponseData.fail(ResponseCodeMsg.ILLEGAL_JSON);
    }

    @ExceptionHandler(FastDFSException.class)
    @ResponseBody
    public ResponseData handleServiceException(FastDFSException e) {
        log.error("File server err!", e);
        return ResponseData.fail(ResponseCodeMsg.SERVER_ERR, "File server err!");
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseData handleNullPointException(Exception e) {
        log.error("Unknown server exception!", e);
        return ResponseData.fail(ResponseCodeMsg.SERVER_ERR);
    }
}
