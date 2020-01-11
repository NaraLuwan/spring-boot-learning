package com.github.luwan.spring.boot.learning.fastdfs.service;

import com.github.luwan.spring.boot.learning.fastdfs.constants.ResponseCodeMsg;
import com.github.luwan.spring.boot.learning.fastdfs.exception.FastDFSException;
import com.github.luwan.spring.boot.learning.fastdfs.utils.FileUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.TrackerServer;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author luwan
 * @date 2020/1/10
 */
@Data
@Slf4j
@Service
public class FastDFSService {

    public String upload(String fileName, InputStream inputStream) throws FastDFSException {
        TrackerServer trackerServer = TrackerServerPool.getTracker();
        if (trackerServer == null) {
            throw new FastDFSException(ResponseCodeMsg.FILE_SERVER_CONNECTION_FAILED.getCode(), ResponseCodeMsg.FILE_SERVER_CONNECTION_FAILED.getMsg());
        }
        StorageClient1 storageClient = new StorageClient1(trackerServer, null);
        String path = "";
        try {
            // 读取流
            byte[] fileBuff = new byte[inputStream.available()];
            inputStream.read(fileBuff, 0, fileBuff.length);

            // 文件名后缀
            String suffix = FileUtil.getFilenameSuffix(fileName);

            // 上传
            path = storageClient.upload_file1(fileBuff, suffix, null);

            if (StringUtils.isBlank(path)) {
                throw new FastDFSException(ResponseCodeMsg.FILE_UPLOAD_FAILED.getCode(), ResponseCodeMsg.FILE_UPLOAD_FAILED.getMsg());
            }

        } catch (Throwable e) {
            log.error("upload file err", e);
            throw new FastDFSException(ResponseCodeMsg.SERVER_ERR.getCode(), ResponseCodeMsg.SERVER_ERR.getMsg());
        } finally {
            // 关闭流
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        // 返还对象
        TrackerServerPool.returnTracker(trackerServer);

        return path;
    }

    public String download(String fileName) {
        return "file.server.com" + fileName;
    }
}
