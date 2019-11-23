package com.api.support.mybatis.generator.resovler;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.internal.types.JavaTypeResolverDefaultImpl;

public class MySqlJavaTypeResolver extends JavaTypeResolverDefaultImpl {
	public FullyQualifiedJavaType calculateJavaType(IntrospectedColumn introspectedColumn) {
		return super.calculateJavaType(introspectedColumn);
	}

	public String calculateJdbcTypeName(IntrospectedColumn introspectedColumn) {
		return super.calculateJdbcTypeName(introspectedColumn);
	}
}
