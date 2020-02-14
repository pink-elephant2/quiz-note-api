package com.api.note.quiz.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

/**
 * グループ画像フォーム
 */
@Data
public class GroupImageForm {

	/** グループCD */
	@NotNull
	@Size(max = 50)
	private String cd;

	/** 画像ファイル */
	@NotNull
	private MultipartFile upfile;

}
