package com.api.note.quiz.service;

import com.restfb.types.User;

/**
 * Facebookサービス
 */
public interface FacebookService {

	/**
	 * Facebook認証用URLを生成する
	 */
	public String createFacebookAuthorizationURL();

	/**
	 * Facebookユーザーを取得する
	 */
	public User getFacebookUser(String code);
}
