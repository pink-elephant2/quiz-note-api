package com.api.note.quiz.repository;

import org.springframework.stereotype.Repository;

@Repository
public class TQuizRepositoryImpl implements TQuizRepository {
    private TQuizMapper tQuizMapper;

    public TQuizRepositoryImpl(TQuizMapper tQuizMapper) {
        this.tQuizMapper = tQuizMapper;
    }

    @Override
    public TQuizMapper getMapper() {
        return this.tQuizMapper;
    }
}