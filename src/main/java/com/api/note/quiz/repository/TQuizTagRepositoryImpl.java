package com.api.note.quiz.repository;

import org.springframework.stereotype.Repository;

@Repository
public class TQuizTagRepositoryImpl implements TQuizTagRepository {
    private TQuizTagMapper tQuizTagMapper;

    public TQuizTagRepositoryImpl(TQuizTagMapper tQuizTagMapper) {
        this.tQuizTagMapper = tQuizTagMapper;
    }

    @Override
    public TQuizTagMapper getMapper() {
        return this.tQuizTagMapper;
    }
}