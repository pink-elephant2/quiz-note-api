package com.api.note.quiz.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.api.note.quiz.enums.ReportReasonEnum;
import com.api.note.quiz.form.QuizForm;
import com.api.note.quiz.resources.QuizResource;

/**
 * クイズサービス
 */
public interface QuizService {

	/**
	 * クイズを取得する
	 *
	 * @param cd
	 *            コード
	 * @return クイズ情報
	 */
	public QuizResource find(String cd);

	/**
	 * クイズ一覧を取得する
	 *
	 * @param loginId
	 *            ログインID
	 * @param pageable
	 *            ページ情報
	 * @param クイズ一覧
	 */
	public Page<QuizResource> findList(String loginId, Pageable pageable);

	/**
	 * クイズを登録する
	 *
	 * @param form
	 *            クイズフォーム
	 * @return クイズ情報
	 */
	public QuizResource create(QuizForm form);

	/**
	 * クイズにいいねをする/解除する
	 *
	 * @param cd
	 *            コード
	 * @param isLike
	 */
	public boolean like(String cd, boolean isLike);

	/**
	 * クイズを通報する
	 *
	 * @param cd
	 *            コード
	 * @param reason
	 *            理由
	 */
	public boolean report(String cd, ReportReasonEnum reason);

	/**
	 * クイズを削除する
	 *
	 * @param cd
	 *            コード
	 */
	public boolean remove(String cd);
}
