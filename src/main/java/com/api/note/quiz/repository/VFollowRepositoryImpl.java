package com.api.note.quiz.repository;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Primary
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