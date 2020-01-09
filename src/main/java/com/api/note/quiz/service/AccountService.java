package com.api.note.quiz.service;

import com.api.note.quiz.domain.TAccount;
import com.api.note.quiz.enums.ReportReasonEnum;
import com.api.note.quiz.form.AccountCreateForm;
import com.api.note.quiz.form.AccountImageForm;
import com.api.note.quiz.form.AccountUpdateForm;
import com.api.note.quiz.resources.AccountResource;

/**
 * アカウントサービス
 */
public interface AccountService {

	/**
	 * アカウントを登録する
	 *
	 * @param form
	 *            アカウント作成フォーム
	 */
	public boolean create(AccountCreateForm form);

	/**
	 * アカウントを取得する
	 *
	 * @param loginId
	 *            ログインID
	 * @return アカウント情報
	 */
	public AccountResource find(String loginId);

	/**
	 * アカウントを通報する
	 *
	 * @param loginId
	 *            ログインID
	 * @param reason
	 *            理由
	 */
	public boolean report(String loginId, ReportReasonEnum reason);

	/**
	 * アカウントをブロックする
	 *
	 * @param loginId
	 *            ログインID
	 */
	public boolean block(String loginId);

	/**
	 * プロフィールを更新する
	 *
	 * @param form
	 *            プロフィールフォーム
	 */
	public boolean saveProfile(AccountUpdateForm form);

	/**
	 * アカウント画像を更新する
	 *
	 * @param form
	 *            画像フォーム
	 */
	public boolean saveImage(AccountImageForm form);

	/**
	 * FacebookIDからアカウントを取得する
	 */
	public TAccount findByFacebookId(String facebookId);
}
