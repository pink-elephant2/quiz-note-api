package com.api.note.quiz.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

/**
 * クイズフォーム
 */
@Data
public class QuizForm {

	/** 問題 */
	@NotNull
	@Size(max = 200)
	private String question;

	/** 答え */
	@NotNull
	@Size(max = 50)
	private String answer;

	/** ヒント */
	@Size(max = 50)
	private String hint;

	/** 解説 */
	@Size(max = 100)
	private String explanation;
}
