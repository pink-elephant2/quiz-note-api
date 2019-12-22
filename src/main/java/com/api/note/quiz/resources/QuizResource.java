package com.api.note.quiz.resources;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * クイズAPIレスポンス
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuizResource {

	/** ID */
	@JsonProperty("id")
	private Long quizId;

	/** CD */
	@JsonProperty("cd")
	private String quizCd;

	/** 問題 */
	private String question;

	/** 答え */
	private String answer;

	/** ヒント */
	private String hint;

	/** 解説 */
	private String explanation;

	/** 音声パス */
	private String soundUrl;

	/** タグ */
	private List<String> tags;

	/** 投稿日時 */
	private Date createdAt;

	/** 投稿ユーザー */
	public AccountResource account;

	/** いいね件数 */
	private long likeCount;

	/** 自分がいいねしたか */
	@JsonProperty("isLike")
	private boolean isLike;

}
