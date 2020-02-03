package com.api.note.quiz.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

/**
 * パスワード再設定フォーム
 */
@Data
public class PasswordResetForm {

	/** メールアドレス */
	@NotNull
	@Size(max = 256)
	@Email
	private String mail;

	/** パスワード */
	@NotNull
	@Size(max = 30)
	private String password;

	/** ワンタイムトークン */
	@NotNull
	@Size(max = 256)
	private String token;

}
