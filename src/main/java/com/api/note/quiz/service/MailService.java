package com.api.note.quiz.service;

import com.api.note.quiz.form.ContactForm;

/**
 * メールサービス
 */
public interface MailService {

	/**
	 * お問合せ完了メールを送信する
	 *
	 * @param form
	 *            お問合せフォーム
	 */
	public boolean sendContactComplete(ContactForm form);
}
