package com.api.note.quiz.resources;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * アカウントAPIレスポンス
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountResource {

	/** アカウントID */
	@JsonProperty("id")
	private Integer accountId;

	/** ログインID */
	private String loginId;

	/** アカウント名 */
	private String name;

	/** 自己紹介 */
	private String description;

	/** 画像パス */ // TODO 画像情報クラス
	private String imgUrl;

	/** Twitterアカウント */
	private String twitter;

	/** Instagramアカウント */
	private String instagram;

	/** ログイン状態の場合、フォローしているかどうか */
	@JsonProperty("isFollow")
	private boolean isFollow;

	/** ログイン状態の場合、フォローされているかどうか */
	@JsonProperty("isFollower")
	private boolean isFollower;

	/** ログイン状態の場合、ブロックされているかどうか */
	@JsonProperty("isBlocked")
	private boolean isBlocked;
}
