package com.socialWork.Util;

import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisUtils {
	@Autowired
	private StringRedisTemplate redisTemplate;

	private static RedisUtils redisUtils;

	/**
	 * init
	 */
	@PostConstruct
	public void init() {
		redisUtils = this;
		redisUtils.redisTemplate = this.redisTemplate;
	}

	/**
	 * @param key
	 */
	public static Set<String> keys(String key) {
		return redisUtils.redisTemplate.keys(key);
	}

	/**
	 * get value by key
	 * 
	 * @param key
	 */
	public static Object get(String key) {
		return redisUtils.redisTemplate.opsForValue().get(key);
	}

	/**
	 * 
	 * @param key
	 * @param value
	 */
	public static void set(String key, String value) {
		redisUtils.redisTemplate.opsForValue().set(key, value);
	}

	/**
	 * 
	 * @param key
	 * @param value
	 * @param expire second
	 */
	public static void set(String key, String value, Integer expire) {
		redisUtils.redisTemplate.opsForValue().set(key, value, expire, TimeUnit.SECONDS);
	}

	/**
	 * 
	 * @param key
	 */
	public static void delete(String key) {
		redisUtils.redisTemplate.opsForValue().getOperations().delete(key);
	}

	/**
	 * 
	 * @param key     key
	 * @param hashKey hashKey
	 * @param object  target
	 */
	public static void hset(String key, String hashKey, Object object) {
		redisUtils.redisTemplate.opsForHash().put(key, hashKey, object);
	}

	/**
	 * 
	 * @param key     key
	 * @param hashKey hashKey
	 * @param object  target
	 * @param expire  second
	 */
	public static void hset(String key, String hashKey, Object object, Integer expire) {
		redisUtils.redisTemplate.opsForHash().put(key, hashKey, object);
		redisUtils.redisTemplate.expire(key, expire, TimeUnit.SECONDS);
	}

	/**
	 *
	 * @param key key
	 * @param map map data
	 */
	public static void hset(String key, HashMap<String, Object> map) {
		redisUtils.redisTemplate.opsForHash().putAll(key, map);
	}

	/**
	 * 
	 * @param key
	 * @param hashKey
	 * @param object
	 */
	public static void hsetAbsent(String key, String hashKey, Object object) {
		redisUtils.redisTemplate.opsForHash().putIfAbsent(key, hashKey, object);
	}

	/**
	 *
	 * @param key
	 * @param hashKey
	 * @return
	 */
	public static Object hget(String key, String hashKey) {
		return redisUtils.redisTemplate.opsForHash().get(key, hashKey);
	}

	/**
	 *
	 * @param key
	 * @return
	 */
	public static Object hget(String key) {
		return redisUtils.redisTemplate.opsForHash().entries(key);
	}

	/**
	 *
	 * @param key
	 */
	public static void deleteKey(String key) {
		redisUtils.redisTemplate.opsForHash().getOperations().delete(key);
	}

	/**
	 *
	 * @param key
	 */
	public static Boolean hasKey(String key) {
		return redisUtils.redisTemplate.opsForHash().getOperations().hasKey(key);
	}

	/**
	 *
	 * @param key
	 * @param hasKey
	 */
	public static Boolean hasKey(String key, String hasKey) {
		return redisUtils.redisTemplate.opsForHash().hasKey(key, hasKey);
	}

}
