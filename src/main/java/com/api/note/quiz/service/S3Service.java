package com.api.note.quiz.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.api.note.quiz.enums.DocumentTypeEnum;

/**
 * S3サービス
 */
public interface S3Service {

	/**
	 * アップロード
	 *
	 * @param fileName
	 *            ファイル名
	 * @param inputFile
	 *            マルチパートファイル
	 * @return ファイルパス
	 * @throws IOException
	 */
	public String upload(DocumentTypeEnum documentType, String fileName, MultipartFile inputFile) throws IOException;
}
