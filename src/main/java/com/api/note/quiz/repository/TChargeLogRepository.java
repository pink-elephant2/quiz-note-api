package com.api.note.quiz.repository;

import com.api.note.quiz.domain.TChargeLog;
import com.api.note.quiz.domain.TChargeLogExample;
import com.api.note.quiz.domain.TChargeLogKey;
import com.api.sns.common.business.repository.BaseRepository;

public interface TChargeLogRepository extends BaseRepository<TChargeLogKey, TChargeLog, TChargeLogExample> {
}