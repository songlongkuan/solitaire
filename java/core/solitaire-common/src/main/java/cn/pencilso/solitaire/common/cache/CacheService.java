package cn.pencilso.solitaire.common.cache;

import cn.pencilso.solitaire.common.model.cache.CacheWrapper;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Optional;

/**
 * @author pencilso
 * @date 2020/2/12 7:58 下午
 */
public interface CacheService {
    /**
     * 设置一个 key value 都为string的缓存
     *
     * @param key
     * @param value
     */
    void putCacheString(@NotBlank String key, @NotBlank String value);

    /**
     * 获取String类型的 缓存
     *
     * @param key
     * @return
     */
    Optional<String> getCacheString(@NotBlank String key);


    void putCache(@NotBlank String key, @NotNull CacheWrapper<?> cacheWrapper);

    <T> Optional<CacheWrapper<T>> getCache(@NotBlank String key);
}
