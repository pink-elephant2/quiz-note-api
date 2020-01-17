package com.api.note.quiz.repository;

import com.api.common.business.repository.BaseMapper;
import com.api.note.quiz.domain.TContact;
import com.api.note.quiz.domain.TContactExample;
import com.api.note.quiz.domain.TContactKey;

public interface TContactMapper extends BaseMapper<TContactKey, TContact, TContactExample> {
}