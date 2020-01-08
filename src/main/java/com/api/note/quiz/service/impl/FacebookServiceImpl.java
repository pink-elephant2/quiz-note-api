package com.api.note.quiz.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.api.note.quiz.service.FacebookService;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.json.JsonObject;
import com.restfb.scope.FacebookPermissions;
import com.restfb.scope.ScopeBuilder;
import com.restfb.types.User;

import lombok.extern.slf4j.Slf4j;

/**
 * Facebookサービス
 */
@Slf4j
@Service
public class FacebookServiceImpl implements FacebookService {

	@Value("${spring.social.facebook.appId}")
	private String facebookAppId;

	@Value("${spring.social.facebook.appSecret}")
	private String facebookSecret;

	@Value("${spring.social.facebook.redirectUri}")
	private String redirectUri;

	@Override
	public String createFacebookAuthorizationURL() {
		ScopeBuilder scopeBuilder = new ScopeBuilder();
		//scopeBuilder.addPermission(FacebookPermissions.PUBLIC_PROFILE);
		scopeBuilder.addPermission(FacebookPermissions.EMAIL);
		scopeBuilder.addPermission(FacebookPermissions.USER_GENDER);
		//scopeBuilder.addPermission(FacebookPermissions.USER_BIRTHDAY);

		FacebookClient client = getFacebookClient(null, facebookSecret);
		return client.getLoginDialogUrl(facebookAppId, redirectUri, scopeBuilder);
	}

	@Override
	public User getFacebookUser(String code) {
		// get accessToken
		FacebookClient client = getFacebookClient(null, facebookSecret);
		FacebookClient.AccessToken accessToken = client.obtainUserAccessToken(facebookAppId, facebookSecret,
				redirectUri, code);

		// get details of current user
		client = getFacebookClient(accessToken.getAccessToken(), facebookSecret);
		User user = client.fetchObject("me", User.class, Parameter.with("fields", "id,name,email,picture,gender"));

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
		log.info("facebook user info :{}", user);
		return user;
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
		Version apiVersion = Version.VERSION_3_1;
		if (StringUtils.isAnyBlank(accessToken, appSecret)) {
			return new DefaultFacebookClient(apiVersion);
		} else {
			return new DefaultFacebookClient(accessToken, appSecret, apiVersion);
		}
	}

}
