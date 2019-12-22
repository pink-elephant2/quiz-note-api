package com.api.note.quiz.repository;

import com.api.common.business.repository.BaseMapper;
import com.api.note.quiz.domain.TQuiz;
import com.api.note.quiz.domain.TQuizExample;
import com.api.note.quiz.domain.TQuizKey;

public interface TQuizMapper extends BaseMapper<TQuizKey, TQuiz, TQuizExample> {

	/**
	 * レコードを登録してIDを返却する
	 */
	public Long insertReturnId(TQuiz tQuiz);
}