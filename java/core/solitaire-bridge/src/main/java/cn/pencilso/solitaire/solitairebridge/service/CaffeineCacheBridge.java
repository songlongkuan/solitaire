package cn.pencilso.solitaire.solitairebridge.service;

import cn.pencilso.solitaire.common.model.cache.CacheWrapper;
import cn.pencilso.solitaire.common.cache.CacheService;
import cn.pencilso.solitaire.common.toolkit.DateUtils;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Expiry;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Optional;

/**
 * @author pencilso
 * @date 2020/2/12 8:06 下午
 */
@Service
public class CaffeineCacheBridge implements CacheService {

    private final int maximumSize = 1000;


    private Cache<String, CacheWrapper> caffeine;


    /**
     * 初始化caffeine
     */
    @PostConstruct
    public void initCaffeine() {
        caffeine = Caffeine.newBuilder()
                .expireAfter(new Expiry<String, CacheWrapper<?>>() {
                    @Override
                    public long expireAfterCreate(@NonNull String key, @NonNull CacheWrapper<?> value, long currentTime) {
                        return value.getExpireAt() * 1000;
                    }

                    @Override
                    public long expireAfterUpdate(@NonNull String key, @NonNull CacheWrapper<?> value, long currentTime, @NonNegative long currentDuration) {
                        return currentDuration;
                    }

                    @Override
                    public long expireAfterRead(@NonNull String key, @NonNull CacheWrapper<?> value, long currentTime, @NonNegative long currentDuration) {
                        return currentDuration;
                    }
                })
                .maximumSize(maximumSize)
                .build();
    }


    @Override
    public void putCacheString(@NotBlank String key, @NotBlank String value) {
        CacheWrapper<String> cacheWrapper = new CacheWrapper<String>()
                .setData(value)
                .setCreateAt(System.currentTimeMillis())
                .setExpireAt(System.currentTimeMillis() + DateUtils.MillisecondConst.HOUR);
        putCache(key, cacheWrapper);
    }

    @Override
    public Optional<String> getCacheString(@NotBlank String key) {
        Optional<CacheWrapper<String>> cacheWrapperOptional = getCache(key);
        String result = null;
        if (cacheWrapperOptional.isPresent()) {
            result = cacheWrapperOptional.get().getData();
        }
        return Optional.ofNullable(result);
    }

    @Override
    public void putCache(@NotBlank String key, @NotNull CacheWrapper<?> cacheWrapper) {
        caffeine.put(key, cacheWrapper);
    }

    @Override
    public <T> Optional<CacheWrapper<T>> getCache(@NotBlank String key) {
        CacheWrapper<T> cacheWrapper = caffeine.getIfPresent(key);
        return Optional.ofNullable(cacheWrapper);
    }
}
