package org.jarchetypes.descriptor;

public class ArchetypeDescriptor {

	private String archetypeType;
	private String category;
	private String title;
	private String path;

	public ArchetypeDescriptor(String archetypeType, String category,
			String title, String path) {
		super();
		this.archetypeType = archetypeType;
		this.category = category;
		this.title = title;
		this.path = path;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
