package com.github.luwan.spring.boot.learning.fastdfs.service;

import com.github.luwan.spring.boot.learning.fastdfs.constants.ResponseCodeMsg;
import com.github.luwan.spring.boot.learning.fastdfs.exception.FastDFSException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.TrackerServer;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author luwan
 * @date 2020/1/10
 */
@Slf4j
@Component
public class TrackerServerPool implements InitializingBean {
    /**
     * TrackerServer 配置文件路径
     */
    private static final String FASTDFS_CONFIG_PATH = "fastdfs-client.properties";

    /**
     * 最大连接数
     */
    @Value("${max_storage_connection}")
    private static int maxStorageConnection;

    /**
     * TrackerServer 对象池.
     * GenericObjectPool 没有无参构造
     */
    private static GenericObjectPool<TrackerServer> trackerServerPool;

    private TrackerServerPool() {
    }

    private static GenericObjectPool<TrackerServer> getPool() {
        return trackerServerPool;
    }

    /**
     * 获取 TrackerServer
     *
     * @return TrackerServer
     * @throws FastDFSException
     */
    public static TrackerServer getTracker() {
        TrackerServer trackerServer = null;
        try {
            trackerServer = getPool().borrowObject();
        } catch (Exception e) {
            log.error("get tracker err!", e);
        }
        return trackerServer;
    }

    /**
     * 回收 TrackerServer
     *
     * @param trackerServer 需要回收的 TrackerServer
     */
    public static void returnTracker(TrackerServer trackerServer) throws FastDFSException {
        if (trackerServer == null) {
            return;
        }
        getPool().returnObject(trackerServer);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (trackerServerPool == null) {
            init();
        }
    }

    /**
     * 初始化线程池
     * @return
     * @throws FastDFSException
     */
    private static synchronized GenericObjectPool<TrackerServer> init() throws FastDFSException {
        if (trackerServerPool == null) {
            try {
                ClientGlobal.initByProperties(FASTDFS_CONFIG_PATH);
            } catch (Exception e) {
                throw FastDFSException.instance(ResponseCodeMsg.FILE_SERVER_CONNECTION_FAILED);
            }

            // Pool配置
            GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
            poolConfig.setMinIdle(2);
            if (maxStorageConnection > 0) {
                poolConfig.setMaxTotal(maxStorageConnection);
            }

            trackerServerPool = new GenericObjectPool<>(new TrackerServerFactory(), poolConfig);

            log.info("ClientGlobal configInfo: {}", ClientGlobal.configInfo());
        }
        return trackerServerPool;
    }
}
