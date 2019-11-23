package com.api.note.quiz.repository;

import com.api.common.business.repository.BaseRepository;
import com.api.note.quiz.domain.TChargeLog;
import com.api.note.quiz.domain.TChargeLogExample;
import com.api.note.quiz.domain.TChargeLogKey;

public interface TChargeLogRepository extends BaseRepository<TChargeLogKey, TChargeLog, TChargeLogExample> {
}