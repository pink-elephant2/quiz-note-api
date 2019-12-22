package com.api.note.quiz.repository;

import java.util.List;

import com.api.common.business.repository.BaseRepository;
import com.api.note.quiz.domain.TQuizTag;
import com.api.note.quiz.domain.TQuizTagExample;
import com.api.note.quiz.domain.TQuizTagKey;

public interface TQuizTagRepository extends BaseRepository<TQuizTagKey, TQuizTag, TQuizTagExample> {

	/**
	 * クイズIDからレコードを取得する
	 *
	 * @param quizId クイズID
	 */
	public List<TQuizTag> findAllByQuizId(Long quizId);
}