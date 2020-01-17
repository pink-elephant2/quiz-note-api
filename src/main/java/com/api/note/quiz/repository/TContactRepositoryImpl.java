package com.api.note.quiz.repository;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public class TContactRepositoryImpl implements TContactRepository {
    private TContactMapper tContactMapper;

    public TContactRepositoryImpl(TContactMapper tContactMapper) {
        this.tContactMapper = tContactMapper;
    }

    @Override
    public TContactMapper getMapper() {
        return this.tContactMapper;
    }
}