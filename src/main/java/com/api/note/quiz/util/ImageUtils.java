package com.api.note.quiz.util;

import java.io.IOException;
import java.util.Base64;

import org.springframework.web.multipart.MultipartFile;

/**
 * 画像ユーティリティ
 */
public class ImageUtils {

	/**
	 * data URLを生成する
	 * <p>
	 * data:[&lt;MIME-type&gt;][;charset=&lt;encoding&gt;][;base64],&lt;data&gt;
	 * </p>
	 */
	public static String getDataUrl(MultipartFile upfile) {
		StringBuilder sb = new StringBuilder();

		try {
			byte[] data = upfile.getBytes();
			String base64str = Base64.getEncoder().encodeToString(data);

			sb.append("data:");
			sb.append(upfile.getContentType());
			sb.append(";base64,");
			sb.append(base64str);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return sb.toString();
	}
}
