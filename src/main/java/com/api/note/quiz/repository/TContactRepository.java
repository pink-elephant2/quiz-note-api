package com.api.note.quiz.repository;

import com.api.common.business.repository.BaseRepository;
import com.api.note.quiz.domain.TContact;
import com.api.note.quiz.domain.TContactExample;
import com.api.note.quiz.domain.TContactKey;

public interface TContactRepository extends BaseRepository<TContactKey, TContact, TContactExample> {
}