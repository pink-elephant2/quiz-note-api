package com.api.note.quiz.repository;

import com.api.common.business.repository.BaseRepository;
import com.api.note.quiz.domain.TBanReport;
import com.api.note.quiz.domain.TBanReportExample;
import com.api.note.quiz.domain.TBanReportKey;

public interface TBanReportRepository extends BaseRepository<TBanReportKey, TBanReport, TBanReportExample> {
}