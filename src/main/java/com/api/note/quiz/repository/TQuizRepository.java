package com.api.note.quiz.repository;

import com.api.common.business.repository.BaseRepository;
import com.api.note.quiz.domain.TQuiz;
import com.api.note.quiz.domain.TQuizExample;
import com.api.note.quiz.domain.TQuizKey;

public interface TQuizRepository extends BaseRepository<TQuizKey, TQuiz, TQuizExample> {

	/**
	 * クイズIDからレコードを取得する
	 *
	 * @param quizId クイズID
	 */
	public TQuiz findOneById(Long quizId);

	/**
	 * クイズCDからレコードを取得する
	 *
	 * @param quizCd クイズCD
	 */
	public TQuiz findOneByCd(String quizCd);

	/**
	 * レコードを登録してIDを返却する
	 */
	public Long createReturnId(TQuiz tQuiz);
}