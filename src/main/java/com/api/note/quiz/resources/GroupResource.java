package com.api.note.quiz.resources;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * グループAPIレスポンス
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GroupResource {

	/** グループID */
	@JsonProperty("id")
	private Integer groupId;

	/** グループCD */
	@JsonProperty("cd")
	private String groupCd;

	/** グループ名 */
	private String name;

	/** 管理者アカウント */
	private AccountResource account;

	/** 公式フラグ */
	private Boolean official;

	/** 自己紹介 */
	private String description;

	/** 画像URL */
	private String imgUrl;

	/** ログイン状態の場合、ブロックされているかどうか */
	@JsonProperty("isBlocked")
	private boolean isBlocked;

	/** ログイン状態の場合、メンバーかどうか */
	@JsonProperty("isMember")
	private boolean isMember;
}
