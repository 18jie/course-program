package com.fengjie.courseprogram.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * @author fengjie
 * @date 2019:04:12
 */
@Configuration
public class CacheConfig {

    private static final int TTL = 300;
    private static final int DEFAULT_MAXSIZE = 10000;

    public enum Caches {
        /**
         * 数据缓存
         */
        data_cache;

        private int ttl = TTL;
        private int maxSize = DEFAULT_MAXSIZE;

        Caches() {
        }

        Caches(int ttl) {
            this.ttl = ttl;
        }

        Caches(int ttl, int maxSize) {
            this.ttl = ttl;
            this.maxSize = maxSize;
        }

        public int getTtl() {
            return ttl;
        }

        public void setTtl(int ttl) {
            this.ttl = ttl;
        }

        public int getMaxSize() {
            return maxSize;
        }

        public void setMaxSize(int maxSize) {
            this.maxSize = maxSize;
        }

    }

    @Bean("metadataCacheManager")
    public CacheManager createCacheManager() {
        SimpleCacheManager manager = new SimpleCacheManager();
        ArrayList<CaffeineCache> caches = new ArrayList<>();
        for (Caches cache : Caches.values()) {
            caches.add(new CaffeineCache(cache.name(), Caffeine.newBuilder().recordStats()
                    .expireAfterWrite(cache.getTtl(), TimeUnit.SECONDS).maximumSize(cache.getMaxSize()).build()));
        }
        manager.setCaches(caches);
        return manager;
    }

    @Bean("emailCache")
    public CaffeineCache createCache() {
        return new CaffeineCache("emailCache", Caffeine.newBuilder().recordStats()
                .expireAfterWrite(TTL, TimeUnit.SECONDS).maximumSize(DEFAULT_MAXSIZE).build());
    }

}
