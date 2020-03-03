package com.api.note.quiz.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.api.common.business.repository.BaseRepository;
import com.api.note.quiz.domain.TGroup;
import com.api.note.quiz.domain.TGroupExample;
import com.api.note.quiz.domain.TGroupKey;

public interface TGroupRepository extends BaseRepository<TGroupKey, TGroup, TGroupExample> {

	/**
	 * グループIDからレコードを取得する
	 *
	 * @param groupId グループID
	 */
	public TGroup findOneById(Integer groupId);

	/**
	 * グループCDからレコードを取得する
	 *
	 * @param groupCd グループCD
	 */
	public TGroup findOneByCd(String groupCd);

	/**
	 * レコードを登録してIDを返却する
	 */
	public Integer createReturnId(TGroup tGroup);

	/**
	 * 所属していないグループを取得する
	 */
	public Page<TGroup> findPageWithout(Integer accountId, Pageable pageable);
}