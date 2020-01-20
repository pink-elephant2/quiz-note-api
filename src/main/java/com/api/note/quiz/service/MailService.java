package com.api.note.quiz.service;

import com.api.note.quiz.form.AccountCreateForm;
import com.api.note.quiz.form.ContactForm;

/**
 * メールサービス
 */
public interface MailService {

	/**
	 * アカウント登録完了メールを送信する
	 *
	 * @param form
	 *            アカウント作成フォーム
	 */
	public boolean sendAccountRegistComplete(AccountCreateForm form);

	/**
	 * お問合せ完了メールを送信する
	 *
	 * @param form
	 *            お問合せフォーム
	 */
	public boolean sendContactComplete(ContactForm form);
}
