package org.jarchetypes.widget;

public class ArchetypeDescriptor {

	private String archetypeType;
	private String category;

	public ArchetypeDescriptor(String archetypeType, String category) {
		super();
		this.archetypeType = archetypeType;
		this.category = category;
	}

	public String getArchetypeType() {
		return archetypeType;
	}

	public void setArchetypeType(String archetypeType) {
		this.archetypeType = archetypeType;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

}
