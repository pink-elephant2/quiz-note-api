package com.api.note.quiz.resources;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * グループメンバーAPIレスポンス
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GroupMemberResource {

	/** グループメンバーID */
	@JsonProperty("id")
	private Integer groupMemberId;

	/** アカウント */
	private AccountResource account;

	/** 管理者アカウントかどうか */
	@JsonProperty("isManager")
	private boolean isManager;
}
