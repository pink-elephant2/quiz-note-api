package com.api.note.quiz.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

/**
 * アカウント作成フォーム
 */
@Data
public class AccountCreateForm {

	/** ログインID */
	@NotNull
	@Size(max = 30)
	private String loginId;

	/** アカウント名 */
	@Size(max = 30)
	private String name;

	/** メールアドレス */
	@NotNull
	@Size(max = 256)
	private String mail;

	/** パスワード */
	@NotNull
	@Size(max = 30)
	private String password;

	/** 画像パス */
	@Size(max = 256)
	private String imgUrl;

	/** Facebookアカウント */
	@Size(max = 30)
	private String facebook;

	// TODO パスワードを出力しない
	//	@Override
	//	public String toString() {
	//
	//	}
}
