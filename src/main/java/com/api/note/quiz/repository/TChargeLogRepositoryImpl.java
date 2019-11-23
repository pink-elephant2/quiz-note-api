package com.api.note.quiz.repository;

import org.springframework.stereotype.Repository;

@Repository
public class TChargeLogRepositoryImpl implements TChargeLogRepository {
    private TChargeLogMapper tChargeLogMapper;

    public TChargeLogRepositoryImpl(TChargeLogMapper tChargeLogMapper) {
        this.tChargeLogMapper = tChargeLogMapper;
    }

    @Override
    public TChargeLogMapper getMapper() {
        return this.tChargeLogMapper;
    }
}