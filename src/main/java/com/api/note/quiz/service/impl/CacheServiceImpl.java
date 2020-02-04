package com.api.note.quiz.service.impl;

import java.util.concurrent.TimeUnit;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.api.note.quiz.service.CacheService;

/**
 * キャッシュサービス
 */
@Service
public class CacheServiceImpl implements CacheService {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	/**
	 * 文字列を取得する
	 */
	@Override
	public String getValue(@NotNull String key) {
		return stringRedisTemplate.opsForValue().get(key);
	}

	/**
	 * オブジェクトを取得する
	 */
	@Override
	public <T> T getObject(@NotNull String key, Class<T> clazz) {
		String value = this.getValue(key);
		return JSON.parseObject(value, clazz);
	}

	/**
	 * 文字列を保存する
	 */
	@Override
	public void saveValue(@NotNull String key, String value) {
		stringRedisTemplate.opsForValue().set(key, value);
	}

	/**
	 * 文字列を保存する
	 *
	 * @param timeout 保持期間(秒)
	 */
	@Override
	public void saveValue(@NotNull String key, String value, long timeout) {
		stringRedisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
	}

	/**
	 * オブジェクトを保存する
	 */
	@Override
	public void saveObject(@NotNull String key, Object obj) {
		String value = JSON.toJSONString(obj);
		this.saveValue(key, value);
	}

	/**
	 * オブジェクトを保存する
	 *
	 * @param timeout 保持期間(秒)
	 */
	@Override
	public void saveObject(@NotNull String key, Object obj, long timeout) {
		String value = JSON.toJSONString(obj);
		this.saveValue(key, value, timeout);
	}

	/**
	 * 削除する
	 */
	@Override
	public void delete(@NotNull String key) {
		stringRedisTemplate.delete(key);
	}
}
