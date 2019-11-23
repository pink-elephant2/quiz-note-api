package com.api.common.business.repository;

import org.apache.ibatis.annotations.Param;

import com.api.common.business.domain.AbstractBaseEntity;
import com.api.common.business.domain.BaseExample;

public interface BaseNoKeyMapper<E extends AbstractBaseEntity, C extends BaseExample> extends BaseQueryMapper<E, C> {
   int deleteByExample(C arg0);

   int insert(E arg0);

   int insertSelective(E arg0);

   int updateByExampleSelective(@Param("record") E arg0, @Param("example") C arg1);

   int updateByExample(@Param("record") E arg0, @Param("example") C arg1);
}
