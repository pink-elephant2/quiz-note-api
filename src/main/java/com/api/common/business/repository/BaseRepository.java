package com.api.common.business.repository;

import com.api.common.business.domain.AbstractBaseEntity;
import com.api.common.business.domain.BaseExample;

public interface BaseRepository<K extends AbstractBaseEntity, E extends K, C extends BaseExample>
		extends BaseNoKeyRepository<E, C> {
	BaseMapper<K, E, C> getMapper();

	default E findOneBy(K key) {
		return this.getMapper().selectByPrimaryKey(key);
	}

	default boolean delete(K key) {
		return this.getMapper().deleteByPrimaryKey(key) == 1;
	}

	default boolean update(E entity) {
		this.beforeUpdate(entity, false);
		return this.getMapper().updateByPrimaryKey(entity) == 1;
	}

	default boolean updatePartially(E entity) {
		this.beforeUpdate(entity, true);

		return this.getMapper().updateByPrimaryKeySelective(entity) == 1;
	}
}
