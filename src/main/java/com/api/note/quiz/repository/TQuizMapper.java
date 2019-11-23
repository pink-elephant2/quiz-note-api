package com.api.note.quiz.repository;

import com.api.note.quiz.domain.TQuiz;
import com.api.note.quiz.domain.TQuizExample;
import com.api.note.quiz.domain.TQuizKey;
import com.api.sns.common.business.repository.BaseMapper;

public interface TQuizMapper extends BaseMapper<TQuizKey, TQuiz, TQuizExample> {
}