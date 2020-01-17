package com.api.note.quiz.service;

import com.api.note.quiz.form.ContactForm;

/**
 * お問合せサービス
 */
public interface ContactService {

	/**
	 * お問合せ登録する
	 *
	 * @param form
	 *            お問合せフォーム
	 */
	public boolean save(ContactForm form);
}
