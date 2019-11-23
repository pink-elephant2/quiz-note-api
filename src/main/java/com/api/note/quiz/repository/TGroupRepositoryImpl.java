package com.api.note.quiz.repository;

import org.springframework.stereotype.Repository;

@Repository
public class TGroupRepositoryImpl implements TGroupRepository {
    private TGroupMapper tGroupMapper;

    public TGroupRepositoryImpl(TGroupMapper tGroupMapper) {
        this.tGroupMapper = tGroupMapper;
    }

    @Override
    public TGroupMapper getMapper() {
        return this.tGroupMapper;
    }
}