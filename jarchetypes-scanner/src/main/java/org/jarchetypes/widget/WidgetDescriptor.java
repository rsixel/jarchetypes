package org.jarchetypes.widget;

public class WidgetDescriptor {

	private String beanName;
	private String templateName;
	private String title;
	private String fieldName;
	private String fieldType;

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
	
	

}
