package com.api.common.business.domain;

public interface BaseExample {
	void setLimitClause(Integer arg0);

	void setOffsetClause(Integer arg0);

	void setOrderByClause(String arg0);

	String getOrderByClause();
}
