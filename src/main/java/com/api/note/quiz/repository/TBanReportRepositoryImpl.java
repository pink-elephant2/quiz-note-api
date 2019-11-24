package com.api.note.quiz.repository;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public class TBanReportRepositoryImpl implements TBanReportRepository {
    private TBanReportMapper tBanReportMapper;

    public TBanReportRepositoryImpl(TBanReportMapper tBanReportMapper) {
        this.tBanReportMapper = tBanReportMapper;
    }

    @Override
    public TBanReportMapper getMapper() {
        return this.tBanReportMapper;
    }
}