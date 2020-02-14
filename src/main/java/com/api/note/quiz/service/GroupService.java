package com.api.note.quiz.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.api.note.quiz.form.GroupCreateForm;
import com.api.note.quiz.form.GroupImageForm;
import com.api.note.quiz.form.GroupUpdateForm;
import com.api.note.quiz.resources.GroupResource;

/**
 * グループサービス
 */
public interface GroupService {

	/**
	 * グループを取得する
	 *
	 * @param loginId
	 *            ログインID
	 * @param cd
	 *            グループコード
	 * @return グループ情報
	 */
	public GroupResource find(String loginId, String cd);

	/**
	 * グループ一覧を取得する
	 *
	 * @param loginId
	 *            ログインID
	 * @param pageable
	 *            ページ情報
	 * @param グループ一覧
	 */
	public Page<GroupResource> findList(String loginId, Pageable pageable);

	/**
	 * グループを登録する
	 *
	 * @param form
	 *            グループフォーム
	 * @return グループ情報
	 */
	public GroupResource create(GroupCreateForm form);

	/**
	 * グループを更新する
	 *
	 * @param form
	 *            グループフォーム
	 * @return グループ情報
	 */
	public GroupResource update(GroupUpdateForm form);

	/**
	 * グループ画像を更新する
	 *
	 * @param form
	 *            画像フォーム
	 */
	public boolean saveImage(GroupImageForm form);

	/**
	 * グループを削除する
	 *
	 * @param cd
	 *            コード
	 */
	public boolean remove(String cd);
}
