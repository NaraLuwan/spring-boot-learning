package com.github.luwan.spring.boot.learning.fastdfs.controller;

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

    @ExceptionHandler(FastDFSException.class)
    @ResponseBody
    public ResponseData handleServiceException(FastDFSException e) {
        log.error("File server err!", e);
        return ResponseData.fail(ResponseCodeMsg.SERVER_ERR, e.getMessage());
    }

    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public ResponseData handleNullPointException(Throwable e) {
        log.error("Unknown server exception!", e);
        return ResponseData.fail(ResponseCodeMsg.SERVER_ERR);
    }
}
