package org.jarchetypes.descriptor;

import java.util.HashMap;
import java.util.Map;

public class WidgetDescriptor {

	private String beanName;
	private String templateName;
	private String title;
	private String fieldName;
	private String fieldType;

	private Map<String, String> attributes = new HashMap<String, String>();

	public String getFieldType() {
		return fieldType;
	}

	public String getTitle() {
		return title;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;

	}

	public void setTitle(String title) {
		this.title = title;

	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;

	}

	public String getFieldName() {
		return fieldName;
	}

	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public String getAttribute(String attributeName) {
		return attributes.get(attributeName);
	}

	public void setAttribute(String attributeName, String value) {
		attributes.put(attributeName, value);
	}

}
