package com.api.note.quiz.repository;

import com.api.common.business.repository.BaseRepository;
import com.api.note.quiz.domain.TFollow;
import com.api.note.quiz.domain.TFollowExample;
import com.api.note.quiz.domain.TFollowKey;

public interface TFollowRepository extends BaseRepository<TFollowKey, TFollow, TFollowExample> {
}