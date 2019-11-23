package com.api.note.quiz.repository;

import com.api.common.business.repository.BaseRepository;
import com.api.note.quiz.domain.TGroup;
import com.api.note.quiz.domain.TGroupExample;
import com.api.note.quiz.domain.TGroupKey;

public interface TGroupRepository extends BaseRepository<TGroupKey, TGroup, TGroupExample> {
}