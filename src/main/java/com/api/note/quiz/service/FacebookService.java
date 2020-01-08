package com.api.note.quiz.service;

import com.restfb.types.User;

/**
 * Facebookサービス
 */
public interface FacebookService {

	public String createFacebookAuthorizationURL();

	public User getFacebookUser(String code);
}
