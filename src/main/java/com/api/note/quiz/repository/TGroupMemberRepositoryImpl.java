package com.api.note.quiz.repository;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public class TGroupMemberRepositoryImpl implements TGroupMemberRepository {
    private TGroupMemberMapper tGroupMemberMapper;

    public TGroupMemberRepositoryImpl(TGroupMemberMapper tGroupMemberMapper) {
        this.tGroupMemberMapper = tGroupMemberMapper;
    }

    @Override
    public TGroupMemberMapper getMapper() {
        return this.tGroupMemberMapper;
    }
}