package com.api.support.mybatis.generator.plugins;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.config.TableConfiguration;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

public class RepositoryPlugin extends PluginAdapter {
	private boolean isView = false;

	public boolean validate(List<String> warnings) {
		return true;
	}

	public void initialized(IntrospectedTable introspectedTable) {
		introspectedTable.setMyBatis3JavaMapperType(introspectedTable.getMyBatis3JavaMapperType());

		this.isView = this.isView(introspectedTable);

		TableConfiguration conf = introspectedTable.getTableConfiguration();
		if (this.isView) {

			conf.setDeleteByExampleStatementEnabled(false);
			conf.setDeleteByPrimaryKeyStatementEnabled(false);
			conf.setInsertStatementEnabled(false);
			conf.setUpdateByExampleStatementEnabled(false);
			conf.setUpdateByPrimaryKeyStatementEnabled(false);
		} else if (!introspectedTable.hasPrimaryKeyColumns()) {
			conf.setDeleteByPrimaryKeyStatementEnabled(false);
			conf.setUpdateByPrimaryKeyStatementEnabled(false);

		}

	}

	private boolean isView(IntrospectedTable introspectedTable) {
		return "view".equalsIgnoreCase(introspectedTable.getTableType());
	}

	public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass,
			IntrospectedTable introspectedTable) {
		String baseTypeName = this.getClassName(introspectedTable.getBaseRecordType());

		if (this.isView) {

			interfaze.addImportedType(
					new FullyQualifiedJavaType("com.api.common.business.repository.BaseQueryMapper"));

			interfaze.addSuperInterface(
					new FullyQualifiedJavaType("BaseQueryMapper<" + baseTypeName + ", " + baseTypeName + "Example>"));

		} else if (introspectedTable.hasPrimaryKeyColumns()) {

			interfaze.addImportedType(new FullyQualifiedJavaType("com.api.common.business.repository.BaseMapper"));

			interfaze.addImportedType(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType() + "Key"));

			interfaze.addSuperInterface(new FullyQualifiedJavaType(
					"BaseMapper<" + baseTypeName + "Key, " + baseTypeName + ", " + baseTypeName + "Example>"));

		} else {
			interfaze.addImportedType(
					new FullyQualifiedJavaType("com.api.common.business.repository.BaseNoKeyMapper"));

			interfaze.addSuperInterface(
					new FullyQualifiedJavaType("BaseNoKeyMapper<" + baseTypeName + ", " + baseTypeName + "Example>"));

		}

		interfaze.getMethods().clear();

		ArrayList imports = new ArrayList();
		imports.addAll(interfaze.getImportedTypes());

		try {
			Field ex = Interface.class.getDeclaredField("importedTypes");
			ex.setAccessible(true);
			((Set) ex.get(interfaze)).clear();
		} catch (Exception arg6) {
			arg6.printStackTrace();
		}

		imports.stream().filter((type) -> {
			return !((FullyQualifiedJavaType) type).getFullyQualifiedName().equals("java.util.List")
					&& !((FullyQualifiedJavaType) type).getFullyQualifiedName().equals("org.apache.ibatis.annotations.Param");

		}).forEach((type) -> {
			interfaze.addImportedType((FullyQualifiedJavaType) type);
		});
		return true;
	}

	String getClassName(String fullyQualifiedJavaType) {
		return fullyQualifiedJavaType.replaceFirst(".*\\.", "");
	}

	public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
		String mapper = introspectedTable.getMyBatis3JavaMapperType();
		String mapperName = this.getClassName(mapper);
		String repoClz = mapper.replaceFirst("Mapper$", "Repository").replaceFirst("\\.integration\\.", ".business.");

		String repoClzImp = (repoClz + "Impl").replaceFirst("\\.business\\.", ".integration.");

		String repoClzImpName = this.getClassName(repoClzImp);

		String baseTypeName = this.getClassName(introspectedTable.getBaseRecordType());

		String targetProject = this.getContext().getJavaModelGeneratorConfiguration().getTargetProject();

		Interface clsInterface = new Interface(repoClz);

		clsInterface.addImportedType(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()));

		clsInterface.addImportedType(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType() + "Example"));

		if (introspectedTable.hasPrimaryKeyColumns()) {
			clsInterface.addImportedType(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType() + "Key"));

		}

		if (this.isView) {
			clsInterface.addImportedType(
					new FullyQualifiedJavaType("com.api.common.business.repository.BaseQueryRepository"));

			clsInterface.addSuperInterface(new FullyQualifiedJavaType(
					"BaseQueryRepository<" + baseTypeName + ", " + baseTypeName + "Example>"));

		} else if (introspectedTable.hasPrimaryKeyColumns()) {

			clsInterface.addImportedType(
					new FullyQualifiedJavaType("com.api.common.business.repository.BaseRepository"));

			clsInterface.addSuperInterface(new FullyQualifiedJavaType(
					"BaseRepository<" + baseTypeName + "Key, " + baseTypeName + ", " + baseTypeName + "Example>"));

		} else {
			clsInterface.addImportedType(
					new FullyQualifiedJavaType("com.api.common.business.repository.BaseNoKeyRepository"));

			clsInterface.addSuperInterface(new FullyQualifiedJavaType(
					"BaseNoKeyRepository<" + baseTypeName + ", " + baseTypeName + "Example>"));

		}

		clsInterface.setVisibility(JavaVisibility.PUBLIC);

		TopLevelClass clsImp = new TopLevelClass(repoClzImp);

		clsImp.addAnnotation("@Repository");
		clsImp.addImportedType("org.springframework.stereotype.Repository");
		clsImp.addImportedType(new FullyQualifiedJavaType(mapper));
		clsImp.addImportedType(new FullyQualifiedJavaType(repoClz));
		clsImp.setVisibility(JavaVisibility.PUBLIC);
		clsImp.addSuperInterface(new FullyQualifiedJavaType(repoClz));

		String uncapitalizeMapperName = StringUtils.uncapitalize(mapperName);

		org.mybatis.generator.api.dom.java.Field fmapper = new org.mybatis.generator.api.dom.java.Field();
		fmapper.setType(new FullyQualifiedJavaType(mapper));
		fmapper.setName(uncapitalizeMapperName);
		fmapper.setVisibility(JavaVisibility.PRIVATE);

		clsImp.addField(fmapper);
		Method construct = new Method(repoClzImpName);

		construct.setConstructor(true);
		construct.setVisibility(JavaVisibility.PUBLIC);
		construct.addParameter(new Parameter(new FullyQualifiedJavaType(mapper), uncapitalizeMapperName));

		construct.addBodyLine("this." + uncapitalizeMapperName + " = " + uncapitalizeMapperName + ";");

		clsImp.addMethod(construct);

		Method getMapper = new Method("getMapper");

		getMapper.setVisibility(JavaVisibility.PUBLIC);
		getMapper.addBodyLine("return this." + uncapitalizeMapperName + ";");
		getMapper.addAnnotation("@Override");
		getMapper.setReturnType(new FullyQualifiedJavaType(mapper));
		clsImp.addMethod(getMapper);

		GeneratedJavaFile repoImp = new GeneratedJavaFile(clsImp, targetProject, "utf-8",
				this.getContext().getJavaFormatter());

		GeneratedJavaFile repoInterface = new GeneratedJavaFile(clsInterface, targetProject, "utf-8",
				this.getContext().getJavaFormatter());

		ArrayList repoList = new ArrayList();
		repoList.add(repoInterface);
		repoList.add(repoImp);
		return repoList;
	}

	private boolean skipExistFile(String repoClz, String repoClzImp, String targetProject) {
		String filePath = targetProject + "/" + repoClzImp.replaceAll("\\.", "/") + ".java";

		if ((new File(filePath)).exists()) {
			System.out.println("[SKIPPING] " + repoClz);
			return true;

		} else {
			return false;
		}
	}

	public boolean modelExampleClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		topLevelClass.addSuperInterface(new FullyQualifiedJavaType("BaseExample"));
		topLevelClass.addImportedType(new FullyQualifiedJavaType("com.api.common.business.domain.BaseExample"));

		return true;
	}

	public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		topLevelClass.addImportedType(new FullyQualifiedJavaType("lombok.EqualsAndHashCode"));
		topLevelClass.addImportedType(new FullyQualifiedJavaType("lombok.ToString"));

		if (this.isView) {

			Field importField = ReflectionUtils.findField(TopLevelClass.class, "importedTypes");

			importField.setAccessible(true);

			try {
				Set exp = (Set) importField.get(topLevelClass);

				exp.remove(new FullyQualifiedJavaType("com.api.common.business.domain.AbstractBaseEntity"));

			} catch (IllegalArgumentException arg4) {

				arg4.printStackTrace();
			} catch (IllegalAccessException arg5) {

				arg5.printStackTrace();
			}

			topLevelClass.setSuperClass((FullyQualifiedJavaType) null);

			topLevelClass.addAnnotation("@EqualsAndHashCode");
			topLevelClass.addAnnotation("@ToString");

		} else {
			topLevelClass.addAnnotation("@EqualsAndHashCode(callSuper = true)");
			topLevelClass.addAnnotation("@ToString(callSuper = true)");

		}

		return true;
	}

	public boolean modelPrimaryKeyClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		topLevelClass.addImportedType(new FullyQualifiedJavaType("lombok.Data"));
		topLevelClass.addImportedType(new FullyQualifiedJavaType("lombok.EqualsAndHashCode"));
		topLevelClass.addImportedType(new FullyQualifiedJavaType("lombok.ToString"));

		topLevelClass.addAnnotation("@Data");
		topLevelClass.addAnnotation("@EqualsAndHashCode(callSuper = true)");
		topLevelClass.addAnnotation("@ToString(callSuper = true)");

		// TODO FIXME createメソッドがうまく生成できない
//		String className = topLevelClass.getType().getShortNameWithoutTypeArguments();
//
//		Method create = new Method("create");
//
//		create.addJavaDocLine("");
//
//		StringBuilder sb = new StringBuilder();
//		create.addFormattedJavadoc(sb, 1);
//
//		create.addBodyLine("return key;");
//
//		topLevelClass.addMethod(create);

		return true;
	}

	public boolean sqlMapGenerated(GeneratedXmlFile sqlMap, IntrospectedTable introspectedTable) {
		if (sqlMap.getTargetProject().contains("src/main/java")) {
			System.out.println(
					"[ERROR] xml mapper should be under src/main/resources check [mybatis-generator-config.xml]");

		}

		return true;
	}
}
