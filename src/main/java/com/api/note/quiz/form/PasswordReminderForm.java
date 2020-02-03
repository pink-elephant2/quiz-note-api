package com.api.note.quiz.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

/**
 * パスワードリマインダー設定フォーム
 */
@Data
public class PasswordReminderForm {

	/** メールアドレス */
	@NotNull
	@Size(max = 256)
	@Email
	private String mail;

}
