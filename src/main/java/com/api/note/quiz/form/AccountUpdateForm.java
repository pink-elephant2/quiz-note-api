package com.api.note.quiz.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

/**
 * アカウント更新フォーム
 */
@Data
public class AccountUpdateForm {

	/** アカウント名 */
	@NotNull
	@Size(max = 30)
	private String name;

	/** 自己紹介 */
	@Size(max = 120)
	private String description;
}
