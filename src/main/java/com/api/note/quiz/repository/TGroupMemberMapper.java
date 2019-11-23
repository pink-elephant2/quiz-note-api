package com.api.note.quiz.repository;

import com.api.common.business.repository.BaseMapper;
import com.api.note.quiz.domain.TGroupMember;
import com.api.note.quiz.domain.TGroupMemberExample;
import com.api.note.quiz.domain.TGroupMemberKey;

public interface TGroupMemberMapper extends BaseMapper<TGroupMemberKey, TGroupMember, TGroupMemberExample> {
}