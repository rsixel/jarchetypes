package org.jarchetypes.test.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;
import org.jarchetypes.annotation.CRUDList;
import org.jarchetypes.annotation.InputText;

@Entity
@CRUDList(title = "Places")
public class PlaceReregistration implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;

	@Column
	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn
	private Person person;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@InputText
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	@Override
	public boolean equals(Object obj) {

		return obj != null && obj.getClass().equals(this.getClass())
				&& obj instanceof PlaceReregistration
				&& ((PlaceReregistration) obj).getId().equals(id);
	}

}
