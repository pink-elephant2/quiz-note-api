package com.api.note.quiz.enums;

import lombok.Getter;

/**
 * ドキュメント種別
 * <p>
 * ファイルをS3にアップロードする際に使用
 * </p>
 */
@Getter
public enum DocumentTypeEnum {

	/** アカウント画像 */
	ACCOUNT("account"),
	/** 写真 */
	PHOTO("photo");

	DocumentTypeEnum(String uploadPath) {
		this.uploadPath = uploadPath;
	}

	/** パス */
	private final String uploadPath;
}
