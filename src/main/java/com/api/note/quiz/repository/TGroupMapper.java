package com.api.note.quiz.repository;

import com.api.common.business.repository.BaseMapper;
import com.api.note.quiz.domain.TGroup;
import com.api.note.quiz.domain.TGroupExample;
import com.api.note.quiz.domain.TGroupKey;

public interface TGroupMapper extends BaseMapper<TGroupKey, TGroup, TGroupExample> {

	/**
	 * レコードを登録してIDを返却する
	 */
	public Integer insertReturnId(TGroup tGroup);
}