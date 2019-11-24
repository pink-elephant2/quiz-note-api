package com.api.note.quiz.repository;

import com.api.common.business.repository.BaseMapper;
import com.api.note.quiz.domain.TActivity;
import com.api.note.quiz.domain.TActivityExample;
import com.api.note.quiz.domain.TActivityKey;

public interface TActivityMapper extends BaseMapper<TActivityKey, TActivity, TActivityExample> {
}