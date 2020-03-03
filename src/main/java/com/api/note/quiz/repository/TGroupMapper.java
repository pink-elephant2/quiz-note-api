package com.api.note.quiz.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.api.common.business.repository.BaseMapper;
import com.api.note.quiz.domain.TGroup;
import com.api.note.quiz.domain.TGroupExample;
import com.api.note.quiz.domain.TGroupKey;

public interface TGroupMapper extends BaseMapper<TGroupKey, TGroup, TGroupExample> {

	/**
	 * レコードを登録してIDを返却する
	 */
	public Integer insertReturnId(TGroup tGroup);

	/**
	 * 所属していないグループを取得する
	 */
	public long countByWithout(@Param("accountId") Integer accountId);

	/**
	 * 所属していないグループを取得する
	 */
	public List<TGroup> selectByWithout(@Param("accountId") Integer accountId, @Param("limitClause") Integer limit,
			@Param("offsetClause") int offset);
}