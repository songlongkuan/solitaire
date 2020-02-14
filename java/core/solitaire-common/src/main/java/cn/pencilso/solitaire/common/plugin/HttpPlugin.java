package cn.pencilso.solitaire.common.plugin;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.Optional;

/**
 * @author pencilso
 * @date 2020/2/10 8:58 下午
 */
public interface HttpPlugin {
    /**
     * post json data to server
     *
     * @param url         url
     * @param json        json data
     * @param resultClass result bean class
     * @param <T>
     * @return
     */
    <T> Optional<T> postJson(@NotBlank String url, @NotBlank String json, @NotNull Class<T> resultClass);

    /**
     * post xml data to server
     *
     * @param url         url
     * @param xml         xml data
     * @param resultClass result bean class
     * @param <T>
     * @return
     */
    <T> Optional<T> postXml(@NotBlank String url, @NotBlank String xml, @NotNull Class<T> resultClass);

    /**
     * get server
     *
     * @param url   url
     * @param param param
     * @param <T>
     * @return
     */
    <T> Optional<T> get(@NotBlank String url, Map<String, Object> param,@NotNull Class<T> resultClass);
}