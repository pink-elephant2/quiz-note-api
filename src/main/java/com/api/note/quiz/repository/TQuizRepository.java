package com.api.note.quiz.repository;

import com.api.note.quiz.domain.TQuiz;
import com.api.note.quiz.domain.TQuizExample;
import com.api.note.quiz.domain.TQuizKey;
import com.api.sns.common.business.repository.BaseRepository;

public interface TQuizRepository extends BaseRepository<TQuizKey, TQuiz, TQuizExample> {
}