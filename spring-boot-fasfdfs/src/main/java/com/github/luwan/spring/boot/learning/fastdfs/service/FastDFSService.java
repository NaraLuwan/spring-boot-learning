package com.github.luwan.spring.boot.learning.fastdfs.service;

import com.github.luwan.spring.boot.learning.fastdfs.constants.ResponseCodeMsg;
import com.github.luwan.spring.boot.learning.fastdfs.exception.FastDFSException;
import com.github.luwan.spring.boot.learning.fastdfs.utils.FileUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.ProtoCommon;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.TrackerServer;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;

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
    public String upload(String fileName, InputStream inputStream) throws FastDFSException {
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
        } catch (Exception e) {
            log.error("upload file {} err!", fileName, e);
        } finally {
            TrackerServerPool.returnTracker(trackerServer);
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    log.error("close stream err!", e);
                }
            }
        }
        return path;
    }

    public String getToken(String filepath) {
        // unix seconds
        int ts = (int) Instant.now().getEpochSecond();
        // token
        String token = "null";
        try {
            token = ProtoCommon.getToken(getFilename(filepath), ts, ClientGlobal.getG_secret_key());
        } catch (Exception e) {
            throw FastDFSException.instance(ResponseCodeMsg.FILE_GET_TOKEN_FAILED);
        }

        StringBuilder sb = new StringBuilder();
        sb.append("token=").append(token);
        sb.append("&ts=").append(ts);

        return sb.toString();
    }

    /**
     * 获取FastDFS文件的名称，不包含组名
     *
     * @param fileId
     * @return
     */
    private static String getFilename(String fileId) {
        String[] results = new String[2];
        StorageClient1.split_file_id(fileId, results);

        return results[1];
    }

    /**
     * 下载文件到指定位置
     *
     * @param fileId
     * @param localFile
     */
    public boolean download(String fileId, String localFile) {
        boolean res = false;
        TrackerServer trackerServer = null;
        try {
            if (StringUtils.isBlank(fileId)) {
                throw FastDFSException.instance(ResponseCodeMsg.ILLEGAL_PARAM);
            }

            trackerServer = TrackerServerPool.getTracker();
            if (trackerServer == null) {
                throw FastDFSException.instance(ResponseCodeMsg.FILE_SERVER_CONNECTION_FAILED);
            }

            StorageClient1 storageClient = new StorageClient1(trackerServer, null);
            /*File file = new File(localFile);
            if (!file.exists()) {
                file.createNewFile();
            }*/
            res = storageClient.download_file1(FileUtil.toLocal(fileId), localFile) == 0;
        } catch (Exception e) {
            log.error("download file {} err!", fileId, e);
            throw FastDFSException.instance(ResponseCodeMsg.FILE_DOWNLOAD_FAILED);
        } finally {
            TrackerServerPool.returnTracker(trackerServer);
        }
        return res;
    }

    /**
     * 删除文件
     *
     * @param fileId 文件路径
     * @return 删除成功返回 0, 失败返回其它
     */
    public boolean deleteFile(String fileId) throws FastDFSException {
        boolean res = false;
        if (StringUtils.isBlank(fileId)) {
            throw FastDFSException.instance(ResponseCodeMsg.ILLEGAL_PARAM);
        }

        TrackerServer trackerServer = TrackerServerPool.getTracker();
        if (trackerServer == null) {
            throw FastDFSException.instance(ResponseCodeMsg.FILE_SERVER_CONNECTION_FAILED);
        }
        StorageClient1 storageClient = new StorageClient1(trackerServer, null);
        try {
            res = storageClient.delete_file1(fileId) == 0;
        } catch (Exception e) {
            log.error("delete file {} err!", fileId, e);
            throw FastDFSException.instance(ResponseCodeMsg.FILE_DEL_FAILED);
        } finally {
            TrackerServerPool.returnTracker(trackerServer);
        }
        return res;
    }
}
