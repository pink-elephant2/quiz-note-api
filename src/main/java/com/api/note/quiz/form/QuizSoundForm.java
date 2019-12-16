package com.api.note.quiz.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

/**
 * 問読み更新フォーム
 */
@Data
public class QuizSoundForm {

	/** クイズCD */
	@NotNull
	@Size(max = 50)
	private String cd;

	/** 音声ファイル */
	@NotNull
	private MultipartFile upfile;
}
