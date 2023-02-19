package com.springsecurity3withthymeleaf.configuration.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig {
  //to enable Cache in spring boot
  @Bean
  public CacheManager cacheManager() {
    return new ConcurrentMapCacheManager();
  }
//
//  @Bean
//  public KeyGenerator multiplyKeyGenerator() {
//    return (Object target, Method method, Object... params) -> method.getName() + "_" + Arrays.toString(params);
//  }
//
//  @Bean
//  public CacheConfiguration<Long, String> cacheConfiguration() {
//    return CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, String.class,
//                                                                  ResourcePoolsBuilder.newResourcePoolsBuilder()
//                                                                      .heap(100, EntryUnit.ENTRIES)
//                                                                      .offheap(1, MemoryUnit.MB))
//        .withExpiry(ExpiryPolicyBuilder.timeToIdleExpiration(Duration.ofSeconds(100)))
//        .build();
//  }

}
