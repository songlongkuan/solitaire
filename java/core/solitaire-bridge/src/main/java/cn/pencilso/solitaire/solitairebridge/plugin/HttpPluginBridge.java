package cn.pencilso.solitaire.solitairebridge.plugin;

import cn.pencilso.solitaire.common.plugin.HttpPlugin;
import cn.pencilso.solitaire.common.toolkit.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

/**
 * @author pencilso
 * @date 2020/2/10 9:04 下午
 */
@Validated
@Slf4j
@Component
public class HttpPluginBridge implements HttpPlugin {

    private final OkHttpClient okHttpClient = new OkHttpClient();

    private final MediaType MEDIA_TYPE_XML = MediaType.parse("application/xml");
    private final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json");


    @Override
    public <T> Optional<T> postJson(@NotBlank String url, @NotBlank String json, @NotNull Class<T> resultClass) {
        Optional<String> resultJson = postServer(url, MEDIA_TYPE_JSON, json);
        if (!resultJson.isPresent()) {
            return Optional.empty();
        }
        return resultJsonToObject(resultJson.get(), resultClass);
    }

    @Override
    public <T> Optional<T> postXml(@NotBlank String url, @NotBlank String xml, @NotNull Class<T> resultClass) {
        Optional<String> resultJson = postServer(url, MEDIA_TYPE_XML, xml);

        return null;
    }

    @Override
    public <T> Optional<T> get(@NotBlank String url, Map<String, Object> param, @NotNull Class<T> resultClass) {
        String resultStr = null;
        try {
            if (param != null && !param.isEmpty()) {
                StringBuilder stringBuilder = new StringBuilder();
                param.forEach((k, v) -> stringBuilder.append(k).append("=").append(v).append("&"));
                url += "?" + stringBuilder.toString();
            }
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            try (Response response = okHttpClient.newCall(request).execute()) {
                resultStr = response.body().string();
            }
        } catch (Exception e) {
            log.warn("get server error url: [{}]  param: [{}]", url, param, e);
        }
        if (StringUtils.isEmpty(resultStr)) return Optional.empty();
        return resultJsonToObject(resultStr, resultClass);
    }


    private Optional<String> postServer(String url, MediaType mediaType, String data) {
        String resultStr = null;
        try {
            RequestBody requestBody = RequestBody.create(data, mediaType);
            Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build();
            try (Response response = okHttpClient.newCall(request).execute()) {
                resultStr = response.body().string();
            }
        } catch (Exception e) {
            log.warn("post server error url: [{}] mediaType: [{}] data: [{}]", url, mediaType, data, e);
        }
        return Optional.ofNullable(resultStr);
    }


    private <T> Optional<T> resultJsonToObject(@NotBlank String json, @NotNull Class<T> resultClass) {
        if (resultClass.isAssignableFrom(String.class)) {
            return Optional.of((T) json);
        }
        T resultData = null;
        try {
            resultData = JsonUtils.jsonToObject(json, resultClass);
        } catch (IOException e) {
            log.warn("result json de serialization json fail json: [{}]", json, e);
        }
        return Optional.ofNullable(resultData);
    }

}
