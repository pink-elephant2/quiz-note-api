package com.api.note.quiz.repository;

import com.api.note.quiz.domain.TQuizTag;
import com.api.note.quiz.domain.TQuizTagExample;
import com.api.note.quiz.domain.TQuizTagKey;
import com.api.sns.common.business.repository.BaseRepository;

public interface TQuizTagRepository extends BaseRepository<TQuizTagKey, TQuizTag, TQuizTagExample> {
}