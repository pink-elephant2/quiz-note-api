package com.api.note.quiz.repository;

import org.springframework.stereotype.Repository;

@Repository
public class TQuizLikeRepositoryImpl implements TQuizLikeRepository {
    private TQuizLikeMapper tQuizLikeMapper;

    public TQuizLikeRepositoryImpl(TQuizLikeMapper tQuizLikeMapper) {
        this.tQuizLikeMapper = tQuizLikeMapper;
    }

    @Override
    public TQuizLikeMapper getMapper() {
        return this.tQuizLikeMapper;
    }
}