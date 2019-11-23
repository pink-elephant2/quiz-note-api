package com.api.common.business.repository;

import java.util.List;

import com.api.common.business.domain.BaseExample;

public interface BaseQueryMapper<E, C extends BaseExample> {
   long countByExample(C arg0);

   List<E> selectByExample(C arg0);
}
