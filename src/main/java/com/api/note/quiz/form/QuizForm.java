package com.api.note.quiz.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

/**
 * クイズフォーム
 * TODO メンバ変数修正
 */
@Data
public class QuizForm {

	/** 画像ファイル */
	@NotNull
	private MultipartFile upfile;

	/** キャプション */
	@Size(max = 1000)
	private String caption;
}
