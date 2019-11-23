package com.api.note.quiz.repository;

import com.api.note.quiz.domain.TAccount;
import com.api.note.quiz.domain.TAccountExample;
import com.api.note.quiz.domain.TAccountKey;
import com.api.sns.common.business.repository.BaseMapper;

public interface TAccountMapper extends BaseMapper<TAccountKey, TAccount, TAccountExample> {
}