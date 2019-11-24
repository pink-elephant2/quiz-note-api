package com.api.note.quiz.repository;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import com.api.note.quiz.consts.CommonConst;
import com.api.note.quiz.domain.TQuiz;
import com.api.note.quiz.domain.TQuizExample;
import com.api.note.quiz.exception.NotFoundException;

@Primary
@Repository
public class TQuizRepositoryImpl implements TQuizRepository {
	private TQuizMapper tQuizMapper;

	public TQuizRepositoryImpl(TQuizMapper tQuizMapper) {
		this.tQuizMapper = tQuizMapper;
	}

	@Override
	public TQuizMapper getMapper() {
		return this.tQuizMapper;
	}

	/**
	 * クイズIDからレコードを取得する
	 *
	 * @param quizId クイズID
	 */
	@Override
	public TQuiz findOneById(Long quizId) {
		TQuizExample quizExample = new TQuizExample();
		quizExample.createCriteria().andQuizIdEqualTo(quizId).andDeletedEqualTo(CommonConst.DeletedFlag.OFF);
		TQuiz quiz = findOneBy(quizExample);
		if (quiz == null) {
			throw new NotFoundException("クイズが存在しません");
		}
		return quiz;
	}

	/**
	 * クイズCDからレコードを取得する
	 *
	 * @param quizCd クイズCD
	 */
	@Override
	public TQuiz findOneByCd(String quizCd) {
		TQuizExample quizExample = new TQuizExample();
		quizExample.createCriteria().andQuizCdEqualTo(quizCd).andDeletedEqualTo(CommonConst.DeletedFlag.OFF);
		TQuiz quiz = findOneBy(quizExample);
		if (quiz == null) {
			 throw new NotFoundException("クイズが存在しません");
		}
		return quiz;
	}
}