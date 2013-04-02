package org.jarchetypes.widget;

public class WidgetDescriptor {

	private String templateName;
	private String title;
	private String fieldName;

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
	
	

}
