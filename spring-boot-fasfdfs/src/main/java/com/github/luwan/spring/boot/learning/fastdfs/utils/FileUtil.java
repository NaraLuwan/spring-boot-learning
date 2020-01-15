package com.github.luwan.spring.boot.learning.fastdfs.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.File;

/**
 * @author luwan
 * @date 2020/1/10
 */
@Slf4j
public class FileUtil {

    /**
     * 路径分隔符
     */
    public static final String SEPARATOR = "/";
    /**
     * Point
     */
    public static final String POINT = ".";

    public static String getFilenameSuffix(String filename) {
        String suffix = null;
        String originalFilename = filename;
        if (StringUtils.isNotBlank(filename)) {
            if (filename.contains(SEPARATOR)) {
                filename = filename.substring(filename.lastIndexOf(SEPARATOR) + 1);
            }
            if (filename.contains(POINT)) {
                suffix = filename.substring(filename.lastIndexOf(POINT) + 1);
            } else {
                if (log.isErrorEnabled()) {
                    log.error("filename error without suffix : {}", originalFilename);
                }
            }
        }
        return suffix;
    }

    /**
     * 转换路径中的 '\' 为 '/' <br>
     * 并把文件后缀转为小写
     *
     * @param path 路径
     * @return
     */
    public static String toLocal(String path) {
        if (StringUtils.isNotBlank(path)) {
            path = path.replaceAll("\\\\", SEPARATOR);

            if (path.contains(POINT)) {
                String pre = path.substring(0, path.lastIndexOf(POINT) + 1);
                String suffix = path.substring(path.lastIndexOf(POINT) + 1).toLowerCase();
                path = pre + suffix;
            }
        }
        return path;
    }

    /**
     * 创建临时下载目录
     *
     * @return
     */
    public static String createTmpDirForDownload() {
        String dirPath = String.format("%s%s%s", System.getProperty("user.home"), File.separator, "download");
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dirPath;
    }

}
