package cn.pencilso.solitaire.solitaireservice.configuration;

import cn.pencilso.solitaire.common.cache.CacheType;
import cn.pencilso.solitaire.common.config.AliyunOSSProperties;
import cn.pencilso.solitaire.common.config.SolitaireProperties;
import cn.pencilso.solitaire.common.plugin.JwtPlugin;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 初始化bean
 *
 * @author pencilso
 * @date 2020/2/10 10:06 下午
 */
@Configuration
public class BeamConfiguration {

    @Bean
    public SolitaireProperties solitaireProperties() {
        return new SolitaireProperties();
    }

    @Bean
    public AliyunOSSProperties aliyunOSSProperties() {
        return new AliyunOSSProperties();
    }

    @Bean
    public JwtPlugin jwtPlugin() {
        return new JwtPlugin();
    }

    @Bean
    public CacheManager caffeineCacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();

        List<CaffeineCache> caffeineCaches = new ArrayList<>();
        for (CacheType cacheType : CacheType.values()) {
            caffeineCaches.add(new CaffeineCache(cacheType.name(),
                    Caffeine.newBuilder()
                            .expireAfterWrite(cacheType.getExpires(), TimeUnit.MILLISECONDS)
                            .maximumSize(cacheType.getMaximumSize())
                            .build()));
        }
        cacheManager.setCaches(caffeineCaches);
        return cacheManager;
    }

}
