package org.jarchetypes.descriptor;

import java.io.Serializable;

public class SearchColumnDescriptor implements Serializable {

	private static final long serialVersionUID = 1L;
	private String title;
	private String action;
	private String fieldName;

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public SearchColumnDescriptor(String title, String fieldName) {
		super();
		this.title = title;
		this.fieldName = fieldName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

}
