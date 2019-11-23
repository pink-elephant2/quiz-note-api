package com.api.common.business.repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import com.api.common.business.domain.BaseExample;
import com.api.common.util.SearchUtils;

public interface BaseQueryRepository<E, C extends BaseExample> {
	BaseQueryMapper<E, C> getMapper();

	default E findOneBy(C example) {
		List<E> result = this.getMapper().selectByExample(example);

		if (result.size() == 0) {
			return null;
		} else if (result.size() > 1) {
			throw new IncorrectResultSizeDataAccessException(1, result.size());
		} else {
			return result.get(0);
		}
	}

	default List<E> findAll() {
		return this.findAllBy(null);
	}

	default List<E> findAllBy(C example) {
		return this.getMapper().selectByExample(example);
	}

	default Page<E> findPageBy(C example, Pageable pageable) {
		long total = this.getMapper().countByExample(example);

		if (total == 0L) {
			return new PageImpl<E>(Collections.emptyList(), pageable, total);

		} else {
			if (!StringUtils.hasText(example.getOrderByClause())) {

				Optional<String> pageContent = SearchUtils.createPageOrderSql(pageable.getSort());
				pageContent.ifPresent((orderSql) -> {
					example.setOrderByClause(orderSql);
				});
			}

			example.setOffsetClause((int) (pageable.getOffset()));
			example.setLimitClause(Integer.valueOf(pageable.getPageSize()));

			List<E> pageContent1 = this.getMapper().selectByExample(example);

			return new PageImpl<E>(pageContent1, pageable, total);
		}
	}

	default long countAll() {
		return this.countBy(null);
	}

	default boolean existsAll() {
		return this.countAll() >= 1L;
	}

	default long countBy(C example) {
		return this.getMapper().countByExample(example);
	}

	default boolean existsBy(C example) {
		return this.countBy(example) >= 1L;
	}
}
