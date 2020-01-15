package com.github.luwan.spring.boot.learning.fastdfs.controller;

import com.github.luwan.spring.boot.learning.fastdfs.constants.ResponseCodeMsg;
import com.github.luwan.spring.boot.learning.fastdfs.constants.ResponseData;
import com.github.luwan.spring.boot.learning.fastdfs.exception.FastDFSException;
import com.github.luwan.spring.boot.learning.fastdfs.service.FastDFSService;
import com.github.luwan.spring.boot.learning.fastdfs.utils.FileUtil;
import org.csource.common.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author luwan
 * @date 2020/1/10
 */
@RestController
@RequestMapping("/file")
public class FileProxyController {

    @Autowired
    private FastDFSService fastDFSService;

    /**
     * 上传文件
     *
     * @param file
     * @return
     * @throws IOException
     * @throws FastDFSException
     * @throws MyException
     */
    @PostMapping(value = "/upload")
    public ResponseData upload(@RequestParam(name = "file") MultipartFile file) throws IOException {
        return ResponseData.success(fastDFSService.upload(file.getOriginalFilename(), file.getInputStream()));
    }

    /**
     * 获取Token
     *
     * @param fileName
     * @return
     */
    @GetMapping(value = "/token")
    public ResponseData token(@RequestParam(name = "fileName") String fileName) {
        return ResponseData.success(fastDFSService.getToken(fileName));
    }

    /**
     * 删除文件
     *
     * @param fileId
     * @return
     */
    @PostMapping("/del")
    public ResponseData delFile(@RequestParam(name = "fileId") String fileId) {
        if (fastDFSService.deleteFile(fileId)) {
            return ResponseData.success(Boolean.TRUE);
        }
        return ResponseData.fail(ResponseCodeMsg.FILE_SERVER_CONNECTION_FAILED);
    }

    /**
     * 下载文件
     *
     * @param fileId 文件索引
     * @return
     * @throws IOException
     */
    @GetMapping("/download")
    public FileSystemResource downloadFile(@RequestParam(name = "fileId") String fileId) throws IOException {
        String localFile = FileUtil.createTmpDirForDownload() + File.separator + fileId;
        if (fastDFSService.download(fileId, localFile)) {
            return new FileSystemResource(localFile);
        }
        return null;
    }
}
