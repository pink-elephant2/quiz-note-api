package com.api.note.quiz.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

/**
 * お問合せフォーム
 */
@Data
public class ContactForm {

	/** 名前 */
	@NotNull
	@Size(max = 50)
	private String name;

	/** メールアドレス */
	@NotNull
	@Size(max = 256)
	@Email
	private String mail;

	/** 内容 */
	@NotNull
	@Size(max = 1000)
	private String content;
}
