package com.api.note.quiz.repository;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import com.api.note.quiz.consts.CommonConst;
import com.api.note.quiz.domain.TQuizTag;
import com.api.note.quiz.domain.TQuizTagExample;

@Primary
@Repository
public class TQuizTagRepositoryImpl implements TQuizTagRepository {
	private TQuizTagMapper tQuizTagMapper;

	public TQuizTagRepositoryImpl(TQuizTagMapper tQuizTagMapper) {
		this.tQuizTagMapper = tQuizTagMapper;
	}

	@Override
	public TQuizTagMapper getMapper() {
		return this.tQuizTagMapper;
	}

	/**
	 * クイズIDからレコードを取得する
	 *
	 * @param quizId クイズID
	 */
	@Override
	public List<TQuizTag> findAllByQuizId(Long quizId) {
		TQuizTagExample quizTagExample = new TQuizTagExample();
		quizTagExample.createCriteria().andQuizIdEqualTo(quizId).andDeletedEqualTo(CommonConst.DeletedFlag.OFF);
		List<TQuizTag> quizTagList = findAllBy(quizTagExample);
		return quizTagList;
	}

}