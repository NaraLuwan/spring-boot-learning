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

    /**
     * 全局捕获自定义异常，返回具体异常信息
     *
     * @param e
     * @return
     */
    @ExceptionHandler(FastDFSException.class)
    @ResponseBody
    public ResponseData handleServiceException(FastDFSException e) {
        log.error("File server err!", e);
        return ResponseData.fail(ResponseCodeMsg.SERVER_ERR, e.getMessage());
    }

    /**
     * 捕获所有未知异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public ResponseData handleNullPointException(Throwable e) {
        log.error("Unknown server exception!", e);
        return ResponseData.fail(ResponseCodeMsg.SERVER_ERR);
    }
}
