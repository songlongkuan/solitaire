package cn.pencilso.solitaire.common.service;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.InputStream;

/**
 * 文件存储服务
 *
 * @author pencilso
 * @date 2020/2/11 9:15 下午
 */
public interface FileStoreService {

    /**
     * 上传文件
     *
     * @param path     存储路径
     * @param filename 文件名
     * @param bytes    字节上传
     */
    void putFile(@NotBlank String path, @NotBlank String filename, @NotNull byte[] bytes);

    /**
     * 上传文件
     *
     * @param path        存储路径
     * @param filename    文件名
     * @param inputStream 输入流
     */
    void putFile(@NotBlank String path, @NotNull String filename, @NotNull InputStream inputStream);

    /**
     * 上传文件 -> 网络资源
     *
     * @param path     存储路径
     * @param filename 文件名
     * @param netUrl   网络资源
     */
    void putFile(@NotBlank String path, @NotNull String filename, @NotBlank String netUrl);
}
