package com.api.note.quiz.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.api.note.quiz.service.FacebookService;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.exception.FacebookOAuthException;
import com.restfb.json.JsonObject;
import com.restfb.scope.FacebookPermissions;
import com.restfb.scope.ScopeBuilder;
import com.restfb.types.User;

/**
 * Facebookサービス
 */
@Service
public class FacebookServiceImpl implements FacebookService {

	@Value("${spring.social.facebook.appId}")
	private String facebookAppId;

	@Value("${spring.social.facebook.appSecret}")
	private String facebookSecret;

	@Value("${spring.social.facebook.redirectUri}")
	private String redirectUri;

	/**
	 * Facebook認証用URLを生成する
	 */
	@Override
	public String createFacebookAuthorizationURL() {
		ScopeBuilder scopeBuilder = new ScopeBuilder();
		scopeBuilder.addPermission(FacebookPermissions.EMAIL);

		FacebookClient client = getFacebookClient(null, facebookSecret);
		return client.getLoginDialogUrl(facebookAppId, redirectUri, scopeBuilder);
	}

	/**
	 * Facebookユーザーを取得する
	 */
	@Override
	public User getFacebookUser(String code) {
		try {
			// get accessToken
			FacebookClient client = getFacebookClient(null, facebookSecret);
			FacebookClient.AccessToken accessToken = client.obtainUserAccessToken(facebookAppId, facebookSecret,
					redirectUri, code);

			// get details of current user
			client = getFacebookClient(accessToken.getAccessToken(), facebookSecret);

			User user = client.fetchObject("me", User.class);

			// get max picture
			if (user.getPicture() != null) {
				JsonObject pictureJson = client.fetchObject("me/picture", JsonObject.class,
						Parameter.with("width", "132"),
						Parameter.with("height", "132"),
						Parameter.with("redirect", false));

				if (pictureJson != null) {
					if (pictureJson.get("data") != null) {
						String url = pictureJson.get("data").asObject().get("url").asString();
						if (StringUtils.isNotBlank(url)) {
							user.getPicture().setUrl(url);
						}
					}
				}
			}
			return user;
		} catch (FacebookOAuthException e) {
			// TODO コードが誤っている場合
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * get the client of facebook.
	 * If the accessToken is empty, the default client is returned.
	 * If the accessToken is not empty, then the authorized client is returned.
	 *
	 * @param accessToken accessToken
	 * @param appSecret   appSecret
	 * @return the client of facebook
	 */
	private FacebookClient getFacebookClient(String accessToken, String appSecret) {
		Version apiVersion = Version.VERSION_3_2;

		if (StringUtils.isAnyBlank(accessToken, appSecret)) {
			return new DefaultFacebookClient(apiVersion);
		} else {
			return new DefaultFacebookClient(accessToken, appSecret, apiVersion);
		}
	}

}
