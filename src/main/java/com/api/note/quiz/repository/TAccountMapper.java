package com.api.note.quiz.repository;

import com.api.common.business.repository.BaseMapper;
import com.api.note.quiz.domain.TAccount;
import com.api.note.quiz.domain.TAccountExample;
import com.api.note.quiz.domain.TAccountKey;

public interface TAccountMapper extends BaseMapper<TAccountKey, TAccount, TAccountExample> {
}