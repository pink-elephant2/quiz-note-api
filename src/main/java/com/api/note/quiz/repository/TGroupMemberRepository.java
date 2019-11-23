package com.api.note.quiz.repository;

import com.api.common.business.repository.BaseRepository;
import com.api.note.quiz.domain.TGroupMember;
import com.api.note.quiz.domain.TGroupMemberExample;
import com.api.note.quiz.domain.TGroupMemberKey;

public interface TGroupMemberRepository extends BaseRepository<TGroupMemberKey, TGroupMember, TGroupMemberExample> {
}