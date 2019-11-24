package com.api.note.quiz.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.api.note.quiz.resources.AccountResource;

/**
 * フォローサービス
 */
public interface FollowService {

	/**
	 * フォローを取得する
	 *
	 * @param loginId
	 *            ログインID
	 * @param pageable
	 *            ページ情報
	 * @return アカウント情報
	 */
	public Page<AccountResource> findFollow(String loginId, Pageable pageable);

	/**
	 * フォローワーを取得する
	 *
	 * @param loginId
	 *            ログインID
	 * @param pageable
	 *            ページ情報
	 * @return アカウント情報
	 */
	public Page<AccountResource> findFollowers(String loginId, Pageable pageable);

	/**
	 * フォローする
	 *
	 * @param loginId
	 *            ログインID(フォロー対象)
	 */
	public boolean follow(String loginId) throws Exception;

	/**
	 * フォローを解除する
	 *
	 * @param loginId
	 *            ログインID(フォロー対象)
	 */
	public boolean unfollow(String loginId) throws Exception;
}
