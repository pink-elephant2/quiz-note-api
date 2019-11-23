package com.api.note.quiz.repository;

import com.api.note.quiz.domain.TGroup;
import com.api.note.quiz.domain.TGroupExample;
import com.api.note.quiz.domain.TGroupKey;
import com.api.sns.common.business.repository.BaseMapper;

public interface TGroupMapper extends BaseMapper<TGroupKey, TGroup, TGroupExample> {
}