package com.api.note.quiz.repository;

import org.springframework.stereotype.Repository;

@Repository
public class TAccountRepositoryImpl implements TAccountRepository {
    private TAccountMapper tAccountMapper;

    public TAccountRepositoryImpl(TAccountMapper tAccountMapper) {
        this.tAccountMapper = tAccountMapper;
    }

    @Override
    public TAccountMapper getMapper() {
        return this.tAccountMapper;
    }
}