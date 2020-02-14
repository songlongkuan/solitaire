package cn.pencilso.solitaire.solitairebridge.service;

import cn.pencilso.solitaire.common.config.AliyunOSSProperties;
import cn.pencilso.solitaire.common.service.FileStoreService;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * @author pencilso
 * @date 2020/2/11 9:19 下午
 */
@Slf4j
@Validated
@Service
public class OSSStoreServiceBridge implements FileStoreService {

    @Autowired
    private AliyunOSSProperties aliyunOSSProperties;


    private OSS ossClient;

    /**
     * 初始化
     */
    @PostConstruct
    public void initStore() {
        // 创建OSSClient实例。
        ossClient = new OSSClientBuilder().build(aliyunOSSProperties.getOssEndpoint(), aliyunOSSProperties.getOssAccesskey(), aliyunOSSProperties.getOssAccessecret());
    }


    @Override
    public void putFile(@NotBlank String path, @NotBlank String filename, @NotNull byte[] bytes) {
        putFile(path, filename, new ByteArrayInputStream(bytes));

    }

    @Override
    public void putFile(@NotBlank String path, @NotNull String filename, @NotBlank String netUrl) {
        try {
            InputStream inputStream = new URL(netUrl).openStream();
            putFile(path, filename, inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void putFile(@NotBlank String path, @NotNull String filename, @NotNull InputStream inputStream) {
        PutObjectRequest putObjectRequest = new PutObjectRequest(aliyunOSSProperties.getOssBucket(), path + filename, inputStream);
        ossClient.putObject(putObjectRequest);
    }


}
