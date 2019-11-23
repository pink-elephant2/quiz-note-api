package com.api.note.quiz.repository;

import com.api.note.quiz.domain.TBanReport;
import com.api.note.quiz.domain.TBanReportExample;
import com.api.note.quiz.domain.TBanReportKey;
import com.api.sns.common.business.repository.BaseRepository;

public interface TBanReportRepository extends BaseRepository<TBanReportKey, TBanReport, TBanReportExample> {
}