package com.api.support.mybatis.generator.plugins;

import java.util.List;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Element;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.springframework.util.StringUtils;

public class OptimisticLockPlugin extends PluginAdapter {
	private static final String LOCK_VERSION_DB = "lock_version";
	private static final String LOCK_VERSION_JAVA = "lockVersion";

	public boolean validate(List<String> warnings) {
		return true;
	}

	public boolean modelExampleClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		return true;
	}

	private boolean hasLockVersionField(IntrospectedTable introspectedTable) {
		return introspectedTable.getAllColumns().stream().anyMatch((col) -> {
			return col.getJavaProperty().equals("lockVersion");
		});
	}

	private String getTableAliasPrefix(IntrospectedTable introspectedTable) {
		String alias = introspectedTable.getFullyQualifiedTable().getAlias();

		return !StringUtils.hasText(alias) ? "" : StringUtils.trimWhitespace(alias) + ".";
	}

	public boolean sqlMapExampleWhereClauseElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
		if (!this.hasLockVersionField(introspectedTable)) {
			return true;

		} else if (element.getAttributes().stream().filter((attr) -> {
			return attr.getName().equals("id") && attr.getValue().equals("Update_By_Example_Where_Clause");

		}).count() <= 0L) {
			return true;

		} else {
			for (int i = 0; i < element.getElements().size(); ++i) {

				Element el = (Element) element.getElements().get(i);
				if (el instanceof XmlElement) {
					XmlElement where = (XmlElement) el;

					XmlElement optimisticLock = new XmlElement("if");
					optimisticLock.addAttribute(
							new Attribute("test", "record.optimisticLockEnabled  and record.lockVersion != null"));

					optimisticLock.addElement(new TextElement(" and " + this.getTableAliasPrefix(introspectedTable)
							+ "lock_version" + " = #{record." + "lockVersion" + ",jdbcType=INTEGER}"));

					where.getElements().add(0, optimisticLock);

				}
			}

			return true;
		}
	}

	public boolean sqlMapUpdateByExampleWithoutBLOBsElementGenerated(XmlElement element,
			IntrospectedTable introspectedTable) {
		if (!this.hasLockVersionField(introspectedTable)) {
			return true;

		} else {
			for (int i = 0; i < element.getElements().size(); ++i) {

				Element el = (Element) element.getElements().get(i);

				if (el instanceof TextElement) {
					TextElement txt = (TextElement) el;

					String text = txt.getContent();
					if (text.contains("lock_version")) {

						XmlElement isNotNullElement = new XmlElement("if");
						isNotNullElement.addAttribute(new Attribute("test", "record.optimisticLockEnabled == false"));

						isNotNullElement.addElement(new TextElement(text));

						element.getElements().remove(i);
						element.getElements().add(i, isNotNullElement);

						XmlElement optimisticLock = new XmlElement("if");
						optimisticLock.addAttribute(new Attribute("test", "record.optimisticLockEnabled"));

						optimisticLock.addElement(new TextElement(
								" " + this.getTableAliasPrefix(introspectedTable) + "lock_version" + "= "
										+ this.getTableAliasPrefix(introspectedTable) + "lock_version" + " + 1,"));

						element.getElements().add(i, optimisticLock);

						break;

					}
				}
			}

			return true;
		}
	}

	public boolean sqlMapUpdateByExampleSelectiveElementGenerated(XmlElement element,
			IntrospectedTable introspectedTable) {
		for (int out = 0; out < element.getElements().size(); ++out) {
			Element outele = (Element) element.getElements().get(out);

			if (outele instanceof XmlElement) {

				XmlElement xmlout = (XmlElement) outele;
				for (int i = 0; i < xmlout.getElements().size(); ++i) {

					Element el = (Element) xmlout.getElements().get(i);

					if (el instanceof XmlElement) {
						XmlElement xmlEl = (XmlElement) el;

						long count = xmlEl.getAttributes().stream().filter((attr) -> {
							return attr.getName().equals("test") && attr.getValue().contains("record.lockVersion");

						}).count();
						if (count > 0L) {
							String content = ((Element) xmlEl.getElements().stream().filter((txtC) -> {
								return txtC instanceof TextElement;

							}).findFirst().get()).getFormattedContent(0);
							XmlElement isNotNullElement = new XmlElement("if");
							isNotNullElement.addAttribute(new Attribute("test",
									"record.optimisticLockEnabled == false and record.lockVersion != null"));

							isNotNullElement.addElement(new TextElement(content));

							xmlout.getElements().remove(i);
							xmlout.getElements().add(i, isNotNullElement);

							XmlElement optimisticLock = new XmlElement("if");
							optimisticLock.addAttribute(new Attribute("test", "record.optimisticLockEnabled "));

							optimisticLock.addElement(new TextElement(
									" " + this.getTableAliasPrefix(introspectedTable) + "lock_version" + " = "
											+ this.getTableAliasPrefix(introspectedTable) + "lock_version" + " + 1,"));

							xmlout.getElements().add(i, optimisticLock);

							break;

						}
					}
				}
			}
		}

		return true;
	}

	public boolean sqlMapUpdateByPrimaryKeyWithoutBLOBsElementGenerated(XmlElement element,
			IntrospectedTable introspectedTable) {
		for (int i = 0; i < element.getElements().size(); ++i) {

			Element el = (Element) element.getElements().get(i);

			if (el instanceof TextElement) {
				TextElement xmlEl = (TextElement) el;

				String content = xmlEl.getContent();
				XmlElement optimisticLock;
				if (content.contains("lock_version")) {
					optimisticLock = new XmlElement("if");
					optimisticLock.addAttribute(new Attribute("test", "optimisticLockEnabled == false"));

					optimisticLock.addElement(new TextElement(content));

					element.getElements().remove(i);
					element.getElements().add(i, optimisticLock);

					XmlElement optimisticLock1 = new XmlElement("if");
					optimisticLock1.addAttribute(new Attribute("test", "optimisticLockEnabled "));
					optimisticLock1.addElement(
							new TextElement(" " + this.getTableAliasPrefix(introspectedTable) + "lock_version" + " = "
									+ this.getTableAliasPrefix(introspectedTable) + "lock_version" + " + 1,"));

					element.getElements().add(i, optimisticLock1);

				} else if (content.contains("where id")) {

					optimisticLock = new XmlElement("if");
					optimisticLock.addAttribute(new Attribute("test", "optimisticLockEnabled"));
					optimisticLock.addElement(new TextElement(" and " + this.getTableAliasPrefix(introspectedTable)
							+ "lock_version" + " = #{" + "lockVersion" + ",jdbcType=INTEGER}"));

					element.getElements().add(i + 1, optimisticLock);

					break;

				}
			}
		}

		return true;
	}

	public boolean sqlMapUpdateByPrimaryKeySelectiveElementGenerated(XmlElement element,
			IntrospectedTable introspectedTable) {
		for (int out = 0; out < element.getElements().size(); ++out) {
			Element outele = (Element) element.getElements().get(out);

			if (outele instanceof TextElement) {
				String xmlout = ((TextElement) outele).getContent();
				if (xmlout.contains("where id")) {

					XmlElement arg14 = new XmlElement("if");
					arg14.addAttribute(new Attribute("test", "optimisticLockEnabled"));
					arg14.addElement(new TextElement(" and " + this.getTableAliasPrefix(introspectedTable)
							+ "lock_version" + " = #{" + "lockVersion" + ",jdbcType=INTEGER}"));

					element.getElements().add(out + 1, arg14);

					break;

				}
			}

			if (outele instanceof XmlElement) {

				XmlElement arg13 = (XmlElement) outele;
				for (int i = 0; i < arg13.getElements().size(); ++i) {

					Element el = (Element) arg13.getElements().get(i);

					if (el instanceof XmlElement) {
						XmlElement xmlEl = (XmlElement) el;

						long count = xmlEl.getAttributes().stream().filter((attr) -> {
							return attr.getName().equals("test") && attr.getValue().contains("lockVersion");

						}).count();
						if (count > 0L) {
							String content = ((Element) xmlEl.getElements().stream().filter((txtC) -> {
								return txtC instanceof TextElement;

							}).findFirst().get()).getFormattedContent(0);
							XmlElement isNotNullElement = new XmlElement("if");
							isNotNullElement.addAttribute(
									new Attribute("test", "optimisticLockEnabled == false and lockVersion != null"));

							isNotNullElement.addElement(new TextElement(content));

							arg13.getElements().remove(i);
							arg13.getElements().add(i, isNotNullElement);

							XmlElement optimisticLock = new XmlElement("if");
							optimisticLock.addAttribute(new Attribute("test", "optimisticLockEnabled"));
							optimisticLock.addElement(new TextElement(
									" " + this.getTableAliasPrefix(introspectedTable) + "lock_version" + " = "
											+ this.getTableAliasPrefix(introspectedTable) + "lock_version" + " + 1,"));

							arg13.getElements().add(i, optimisticLock);
							break;

						}
					}
				}
			}
		}

		return true;
	}
}
