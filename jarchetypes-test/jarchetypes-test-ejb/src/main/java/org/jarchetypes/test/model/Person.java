package org.jarchetypes.test.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.jarchetypes.annotation.CRUD;
import org.jarchetypes.annotation.Calendar;
import org.jarchetypes.annotation.Filter;
import org.jarchetypes.annotation.InputMask;
import org.jarchetypes.annotation.InputText;
import org.jarchetypes.annotation.SelectItems;
import org.jarchetypes.annotation.SelectOneMenu;

@Entity
@CRUD(title = "Person", generateAll = true)
public class Person implements Serializable {
	/** Default value included to remove warning. Remove or modify at will. **/
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@NotNull
	@Size(min = 1, max = 25)
	@Pattern(regexp = "[A-Za-z ]*", message = "must contain only letters and spaces")
	private String name;

	@NotNull
	@NotEmpty
	@Email
	private String email;

	@NotNull
//	@Size(min = 10, max = 12)
//	@Digits(fraction = 0, integer = 12)
	@Column(name = "phone_number")
	private String phoneNumber;

	private String nickname;
	
	@Column(name = "dateBirth")
	private Date dateBirth;
	
	@OneToMany(fetch = FetchType.LAZY, cascade=CascadeType.PERSIST, mappedBy = "person")
	private List<PlaceReregistration> placeReregistrations;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@InputText(title = "Nome")
	@Filter 
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@InputMask(title="Telefone", mask="(99)9999-9999?9")
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	@Calendar(title="Data Nascimento", pattern="dd/MM/yyyy", mode="popup", showOn="button")
	public Date getDateBirth() {
		return dateBirth;
	}

	public void setDateBirth(Date dateBirth) {
		this.dateBirth = dateBirth;
	}

	@SelectOneMenu(selectItems = @SelectItems(var="placeReregistrations", itemLabel="placeReregistration.name"),converter="selectOneUsingObjectConverter", items= true)
	@Filter
	public List<PlaceReregistration> getPlaceReregistrations() {
		placeReregistrations = new ArrayList<PlaceReregistration>();
		for (int i = 0; i < 4; i++) {
			PlaceReregistration placeReregistration = new PlaceReregistration();
			placeReregistration.setId(i);
			placeReregistration.setName("Cabo Frio " + i);
			placeReregistrations.add(placeReregistration);
		}
		return placeReregistrations;
	}

	public void setPlaceReregistrations(
			List<PlaceReregistration> placeReregistrations) {
		this.placeReregistrations = placeReregistrations;
	}
	
}