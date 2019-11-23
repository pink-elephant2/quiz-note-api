package com.api.common.business.repository;

import com.api.common.business.domain.AbstractBaseEntity;
import com.api.common.business.domain.BaseExample;

public interface BaseMapper<K extends AbstractBaseEntity, E extends K, C extends BaseExample> extends BaseNoKeyMapper<E, C> {
   int deleteByPrimaryKey(K arg0);

   E selectByPrimaryKey(K arg0);

   int updateByPrimaryKeySelective(E arg0);

   int updateByPrimaryKey(E arg0);
}
