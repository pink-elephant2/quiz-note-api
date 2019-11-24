package com.api.note.quiz.repository;

import org.springframework.stereotype.Repository;

@Repository
public class VFollowRepositoryImpl implements VFollowRepository {
    private VFollowMapper vFollowMapper;

    public VFollowRepositoryImpl(VFollowMapper vFollowMapper) {
        this.vFollowMapper = vFollowMapper;
    }

    @Override
    public VFollowMapper getMapper() {
        return this.vFollowMapper;
    }
}