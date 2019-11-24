package com.api.note.quiz.repository;

import com.api.common.business.repository.BaseRepository;
import com.api.note.quiz.domain.TQuizLike;
import com.api.note.quiz.domain.TQuizLikeExample;
import com.api.note.quiz.domain.TQuizLikeKey;

public interface TQuizLikeRepository extends BaseRepository<TQuizLikeKey, TQuizLike, TQuizLikeExample> {
}