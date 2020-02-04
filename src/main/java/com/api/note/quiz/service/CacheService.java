package com.api.note.quiz.service;

import javax.validation.constraints.NotNull;

/**
 * キャッシュサービス
 */
public interface CacheService {

	/**
	 * 文字列を取得する
	 */
	public String getValue(@NotNull String key);

	/**
	 * オブジェクトを取得する
	 */
	public <T> T getObject(@NotNull String key, Class<T> clazz);

	/**
	 * 文字列を保存する
	 */
	public void saveValue(@NotNull String key, String value);

	/**
	 * 文字列を保存する
	 *
	 * @param timeout 保持期間(秒)
	 */
	public void saveValue(@NotNull String key, String value, long timeout);

	/**
	 * オブジェクトを保存する
	 */
	public void saveObject(@NotNull String key, Object obj);

	/**
	 * オブジェクトを保存する
	 *
	 * @param timeout 保持期間(秒)
	 */
	public void saveObject(@NotNull String key, Object obj, long timeout);

	/**
	 * 削除する
	 */
	public void delete(@NotNull String key);
}
