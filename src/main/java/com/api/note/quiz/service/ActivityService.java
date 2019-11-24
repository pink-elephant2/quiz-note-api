package com.api.note.quiz.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.api.note.quiz.resources.ActivityResource;

/**
 * アクティビティサービス
 */
public interface ActivityService {

	/**
	 * フォロー中のアクティビティを取得する
	 *
	 * @param pageable
	 *            ページ情報
	 * @return アクティビティ情報
	 */
	public Page<ActivityResource> findFollowing(Pageable pageable);

	/**
	 * 自分に対するアクティビティを取得する
	 *
	 * @param pageable
	 *            ページ情報
	 * @return アクティビティ情報
	 */
	public Page<ActivityResource> findMe(Pageable pageable);
}
