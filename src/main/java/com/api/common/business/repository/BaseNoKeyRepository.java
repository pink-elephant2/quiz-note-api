package com.api.common.business.repository;

import java.util.Date;

import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.util.StringUtils;

import com.api.common.business.domain.AbstractBaseEntity;
import com.api.common.business.domain.BaseExample;
import com.api.common.util.DateUtils;
import com.api.common.util.SecurityUtils;

public interface BaseNoKeyRepository<E extends AbstractBaseEntity, C extends BaseExample>
		extends BaseQueryRepository<E, C> {
	BaseNoKeyMapper<E, C> getMapper();

	default boolean create(E entity) {
		this.beforeInsert(entity);
		return this.getMapper().insert(entity) == 1;
	}

	default boolean deleteOneBy(C example) {
		int size = this.deleteBy(example);
		if (size > 1) {
			throw new IncorrectResultSizeDataAccessException(1, size);

		} else {
			return size == 1;
		}
	}

	default int deleteBy(C example) {
		return this.getMapper().deleteByExample(example);
	}

	default int updateBy(E entity, C example) {
		this.beforeUpdate(entity, false);
		int result = this.getMapper().updateByExample(entity, example);
		return result;
	}

	default boolean updateOneBy(E entity, C example) {
		this.beforeUpdate(entity, false);

		int size = this.getMapper().updateByExample(entity, example);
		if (size > 1) {
			throw new IncorrectResultSizeDataAccessException(1, size);

		} else {
			return size == 1;
		}
	}

	default int updatePartiallyBy(E entity, C example) {
		this.beforeUpdate(entity, true);

		int result = this.getMapper().updateByExampleSelective(entity, example);

		return result;
	}

	default void beforeInsert(E entity) {
		Date now = DateUtils.now();
		if (entity.getDeleted() == null) {
			entity.setDeleted("0");
		}
		entity.setCreatedAt(now);
		entity.setUpdatedAt(now);

		if (entity.canAutoSetUserId() && SecurityUtils.canGloballyAutoSetUserId()) {
			String loginId = SecurityUtils.getLoginId();
			if (!StringUtils.isEmpty(loginId)) {
				entity.setCreatedBy(loginId);
				entity.setUpdatedBy(loginId);
			}
		}

	}

	default void beforeUpdate(E entity) {
		this.beforeUpdate(entity, true);

	}

	default void beforeUpdate(E entity, boolean partial) {
		Date now = DateUtils.now();
		entity.setUpdatedAt(now);

		if (!partial && entity.getCreatedAt() == null) {
			entity.setCreatedAt(now);
		}

		if (entity.canAutoSetUserId() && SecurityUtils.canGloballyAutoSetUserId()) {
			String loginId = SecurityUtils.getLoginId();
			if (!StringUtils.isEmpty(loginId)) {
				entity.setUpdatedBy(loginId);

				if (!partial && StringUtils.isEmpty(entity.getCreatedBy())) {
					entity.setCreatedBy(loginId);
				}
			}
		}

	}
}
