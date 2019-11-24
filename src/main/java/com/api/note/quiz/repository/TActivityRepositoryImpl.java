package com.api.note.quiz.repository;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public class TActivityRepositoryImpl implements TActivityRepository {
    private TActivityMapper tActivityMapper;

    public TActivityRepositoryImpl(TActivityMapper tActivityMapper) {
        this.tActivityMapper = tActivityMapper;
    }

    @Override
    public TActivityMapper getMapper() {
        return this.tActivityMapper;
    }
}