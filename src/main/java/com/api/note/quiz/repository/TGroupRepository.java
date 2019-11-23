package com.api.note.quiz.repository;

import com.api.note.quiz.domain.TGroup;
import com.api.note.quiz.domain.TGroupExample;
import com.api.note.quiz.domain.TGroupKey;
import com.api.sns.common.business.repository.BaseRepository;

public interface TGroupRepository extends BaseRepository<TGroupKey, TGroup, TGroupExample> {
}