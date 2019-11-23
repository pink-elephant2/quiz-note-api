package com.api.note.quiz.repository;

import com.api.note.quiz.domain.TFollow;
import com.api.note.quiz.domain.TFollowExample;
import com.api.note.quiz.domain.TFollowKey;
import com.api.sns.common.business.repository.BaseMapper;

public interface TFollowMapper extends BaseMapper<TFollowKey, TFollow, TFollowExample> {
}