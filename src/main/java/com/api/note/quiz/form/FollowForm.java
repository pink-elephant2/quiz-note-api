package com.api.note.quiz.form;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * フォローフォーム
 */
@Data
public class FollowForm {

	/** ログインID(フォロー対象) */
	@NotNull
	private String loginId;
}
