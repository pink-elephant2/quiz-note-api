package com.api.note.quiz.repository;

import com.api.note.quiz.domain.TGroupMember;
import com.api.note.quiz.domain.TGroupMemberExample;
import com.api.note.quiz.domain.TGroupMemberKey;
import com.api.sns.common.business.repository.BaseMapper;

public interface TGroupMemberMapper extends BaseMapper<TGroupMemberKey, TGroupMember, TGroupMemberExample> {
}