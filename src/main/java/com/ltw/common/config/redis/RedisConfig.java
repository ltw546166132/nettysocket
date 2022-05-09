package com.ltw.common.config.redis;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.ltw.common.service.RedisService;
import com.ltw.common.service.impl.RedisServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.*;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
* 开启缓存支持
* @Return:
*/
@Slf4j
@EnableCaching
@Configuration
public class RedisConfig extends CachingConfigurerSupport {
	@Value("${spring.redis.host}")
	private String host;
	@Value("${spring.redis.port}")
	private int port;
	@Value("${spring.redis.password}")
	private String password;
	@Value("${spring.redis.database}")
	private int database;
	@Value("${spring.redis.cluster.nodes}")
	private List<String> nodes;

	@Bean
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
		RedisSerializer<Object> serializer = redisSerializer();
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisConnectionFactory);
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(serializer);
		redisTemplate.setHashKeySerializer(new StringRedisSerializer());
		redisTemplate.setHashValueSerializer(serializer);
		redisTemplate.afterPropertiesSet();
		return redisTemplate;
	}

	@Bean
	public RedisSerializer<Object> redisSerializer() {
		//创建JSON序列化器
		Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		//必须设置，否则无法将JSON转化为对象，会转化成Map类型
		objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance,ObjectMapper.DefaultTyping.NON_FINAL);
		serializer.setObjectMapper(objectMapper);
		return serializer;
	}

	@Bean
	public RedisCacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory) {
		RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory);
		//设置Redis缓存有效期为1天
		RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
				.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer())).entryTtl(Duration.ofDays(1));
		return new RedisCacheManager(redisCacheWriter, redisCacheConfiguration);
	}


	@Bean
	public RedisService redisService(){
		return new RedisServiceImpl();
	}

//	@Bean
//	public RedissonClient redissonClient() {
//		Config config = new Config();
//		config.useSingleServer()
//				.setAddress("redis://" + host + ":" + port)
//				.setDatabase(database);
//		return Redisson.create(config);
//	}

	/**
	 * 集群版配置
	 * @return
	 */
	@Bean
	public RedissonClient redissonClient() {
		//redisson版本是3.5，集群的ip前面要加上“redis://”，不然会报错，3.2版本可不加
		List<String> clusterNodes = new ArrayList<>();
		for (int i = 0; i < nodes.size(); i++) {
			clusterNodes.add("redis://" + nodes.get(i));
		}
		Config config = new Config();
		// 添加集群地址
		ClusterServersConfig clusterServersConfig = config.useClusterServers().addNodeAddress(clusterNodes.toArray(new String[clusterNodes.size()]));
		// 设置密码
		if(StrUtil.isNotBlank(password)){
			clusterServersConfig.setPassword(password);
		}
		RedissonClient redissonClient = Redisson.create(config);
		return redissonClient;
	}

}
