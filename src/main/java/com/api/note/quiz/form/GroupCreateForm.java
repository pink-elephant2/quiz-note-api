package com.api.note.quiz.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

/**
 * グループ登録フォーム
 */
@Data
public class GroupCreateForm {

	/** グループ名 */
	@NotNull
	@Size(max = 30)
	private String name;

	//	/** 管理者アカウントID */
	//	private Integer account;

	/** 公式フラグ */
	private Boolean official;

	/** 自己紹介 */
	@NotNull
	@Size(max = 120)
	private String description;
}
