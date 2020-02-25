package com.api.note.quiz.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

/**
 * グループメンバー登録フォーム
 */
@Data
public class GroupMemberCreateForm {

	/** グループCD */
	@NotNull
	@Size(max = 50)
	private String cd;

	/** 登録するログインID */
	@NotNull
	@Size(max = 30)
	private String memberLoginId;

}
