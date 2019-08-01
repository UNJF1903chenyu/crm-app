package com.uek.project.crm.config;

import java.lang.reflect.Method;
import java.time.Duration;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * 配置Redis缓存机制的Key和Value的生产策略
 * 如果不需要改变，可以不需要写此配置类
 * @author l
 *
 */
@Configuration
@EnableCaching
public class RedisConfig  extends CachingConfigurerSupport{
	@Bean
	public KeyGenerator keyGenerator(){
		return new KeyGenerator() {
			
			@Override
			public Object generate(Object target, Method method, Object... params) {
				StringBuffer sb = new StringBuffer();
				sb.append(target.getClass().getSimpleName()+".");
				sb.append(method.getName());
				sb.append("[");
				for (Object obj : params) {
					sb.append(obj.toString());
				}
				sb.append("]");
				return sb.toString();
			}
		};
	}
	@Bean
	public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory){
		//-- 初始化一个RedisCacheWruter
		RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory);
		//-- 设置cacheManager的值序列化方式为JSON序列化
		RedisSerializer<Object> jsonSerializer = new GenericJackson2JsonRedisSerializer();
		RedisSerializationContext.SerializationPair<Object> pair = RedisSerializationContext.SerializationPair.fromSerializer(jsonSerializer);
		//--2.初始化配置
		RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig().serializeValuesWith(pair);
		//-- 设置默认超过期时间是30秒
		defaultCacheConfig.entryTtl(Duration.ofSeconds(30));
		//-- 初始化RedisCacheManager
		return new RedisCacheManager(redisCacheWriter,defaultCacheConfig);
	
	}
}
