package com.github.luwan.spring.boot.learning.fastdfs.service;

import com.github.luwan.spring.boot.learning.fastdfs.constants.ResponseCodeMsg;
import com.github.luwan.spring.boot.learning.fastdfs.exception.FastDFSException;
import com.github.luwan.spring.boot.learning.fastdfs.utils.FileUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.csource.common.MyException;
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

    /**
     * 流方式上传文件
     *
     * @param fileName    待上传的文件名
     * @param inputStream 对应文件流
     * @return
     * @throws FastDFSException
     * @throws IOException
     * @throws MyException
     */
    public String upload(String fileName, InputStream inputStream) throws FastDFSException, IOException, MyException {
        TrackerServer trackerServer = TrackerServerPool.getTracker();
        if (trackerServer == null) {
            throw FastDFSException.instance(ResponseCodeMsg.FILE_SERVER_CONNECTION_FAILED);
        }
        StorageClient1 storageClient = new StorageClient1(trackerServer, null);
        String path = "";
        try {
            // 读取流
            byte[] fileBuff = new byte[inputStream.available()];
            inputStream.read(fileBuff, 0, fileBuff.length);

            String suffix = FileUtil.getFilenameSuffix(fileName);

            // 上传
            path = storageClient.upload_file1(fileBuff, suffix, null);

            if (StringUtils.isBlank(path)) {
                throw FastDFSException.instance(ResponseCodeMsg.FILE_UPLOAD_FAILED);
            }
        } finally {
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
}
