package com.api.note.quiz.repository;

import com.api.common.business.repository.BaseRepository;
import com.api.note.quiz.domain.TQuiz;
import com.api.note.quiz.domain.TQuizExample;
import com.api.note.quiz.domain.TQuizKey;

public interface TQuizRepository extends BaseRepository<TQuizKey, TQuiz, TQuizExample> {
}