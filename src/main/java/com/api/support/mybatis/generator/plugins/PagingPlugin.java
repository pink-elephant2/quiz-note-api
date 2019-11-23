package com.api.support.mybatis.generator.plugins;

import java.util.List;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.PrimitiveTypeWrapper;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

public class PagingPlugin extends PluginAdapter {
	static final String ATTRIBUTE_NAMESPACE = "namespace";
	static final String ATTRIBUTE_TYPE = "type";

	public boolean validate(List<String> warnings) {
		return true;
	}

	public boolean modelExampleClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		PrimitiveTypeWrapper integerWrapper = FullyQualifiedJavaType.getIntInstance().getPrimitiveTypeWrapper();

		Field limitClause = new Field();
		limitClause.setName("limitClause");
		limitClause.setVisibility(JavaVisibility.PROTECTED);
		limitClause.setType(integerWrapper);
		topLevelClass.addField(limitClause);
		this.context.getCommentGenerator().addFieldComment(limitClause, introspectedTable);

		Method limitClauseSet = new Method();
		limitClauseSet.setVisibility(JavaVisibility.PUBLIC);
		limitClauseSet.setName("setLimitClause");
		limitClauseSet.addParameter(new Parameter(integerWrapper, "limitClause"));
		limitClauseSet.addBodyLine("this.limitClause = limitClause;");
		topLevelClass.addMethod(limitClauseSet);
		this.context.getCommentGenerator().addGeneralMethodComment(limitClauseSet, introspectedTable);

		Method limitClauseGet = new Method();
		limitClauseGet.setVisibility(JavaVisibility.PUBLIC);
		limitClauseGet.setReturnType(integerWrapper);
		limitClauseGet.setName("getLimitClause");
		limitClauseGet.addBodyLine("return limitClause;");
		topLevelClass.addMethod(limitClauseGet);
		this.context.getCommentGenerator().addGeneralMethodComment(limitClauseGet, introspectedTable);

		Field offsetClause = new Field();
		offsetClause.setName("offsetClause");
		offsetClause.setVisibility(JavaVisibility.PROTECTED);
		offsetClause.setType(integerWrapper);
		topLevelClass.addField(offsetClause);
		this.context.getCommentGenerator().addFieldComment(offsetClause, introspectedTable);

		Method offsetClauseSet = new Method();
		offsetClauseSet.setVisibility(JavaVisibility.PUBLIC);
		offsetClauseSet.setName("setOffsetClause");
		offsetClauseSet.addParameter(new Parameter(integerWrapper, "offsetClause"));
		offsetClauseSet.addBodyLine("this.offsetClause = offsetClause;");
		topLevelClass.addMethod(offsetClauseSet);
		this.context.getCommentGenerator().addGeneralMethodComment(offsetClauseSet, introspectedTable);

		Method offsetClauseGet = new Method();
		offsetClauseGet.setVisibility(JavaVisibility.PUBLIC);
		offsetClauseGet.setReturnType(integerWrapper);
		offsetClauseGet.setName("getOffsetClause");
		offsetClauseGet.addBodyLine("return offsetClause;");
		topLevelClass.addMethod(offsetClauseGet);
		this.context.getCommentGenerator().addGeneralMethodComment(offsetClauseGet, introspectedTable);

		return true;
	}

	public boolean sqlMapSelectByExampleWithoutBLOBsElementGenerated(XmlElement element,
			IntrospectedTable introspectedTable) {
		XmlElement isNotNullElement = new XmlElement("if");
		isNotNullElement.addAttribute(new Attribute("test", "limitClause != null"));
		isNotNullElement.addElement(new TextElement("limit ${limitClause}"));
		element.getElements().add(isNotNullElement);

		isNotNullElement = new XmlElement("if");
		isNotNullElement.addAttribute(new Attribute("test", "offsetClause != null"));
		isNotNullElement.addElement(new TextElement("offset ${offsetClause}"));
		element.getElements().add(isNotNullElement);

		return true;
	}
}
