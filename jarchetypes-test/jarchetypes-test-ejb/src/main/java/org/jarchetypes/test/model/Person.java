package org.jarchetypes.test.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.jarchetypes.annotation.CRUD;
import org.jarchetypes.annotation.Calendar;
import org.jarchetypes.annotation.Filter;
import org.jarchetypes.annotation.InputMask;
import org.jarchetypes.annotation.InputText;
import org.jarchetypes.annotation.ListFilter;
import org.jarchetypes.annotation.Panel;
import org.jarchetypes.annotation.SelectItems;
import org.jarchetypes.annotation.SelectOneMenu;

@Entity
@CRUD(title = "Person", generateAll = true, resultFields = { "name",
		"nickname", "email", "phoneNumber" })
public class Person implements Serializable {
	/** Default value included to remove warning. Remove or modify at will. **/
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;

	@NotNull(message = "The field name cannot be empty")
	@Size(min = 1, max = 25)
	@Pattern(regexp = "[A-Za-z ]*", message = "must contain only letters and spaces")
	private String name;

	@NotNull(message = "The field name cannot be null")
	@NotEmpty(message = "The field name cannot be empty")
	@Email
	private String email;

	@NotNull(message = "The field phone number cannot be null")
	// @Size(min = 10, max = 12)
	// @Digits(fraction = 0, integer = 12)
	@Column(name = "phone_number")
	private String phoneNumber;

	private String nickname;

	@Column(name = "dateBirth")
	private Date dateBirth;

	private PlaceReregistration placeReregistration;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@InputText(title = "Nome")
	@Filter 
	@Panel(id="pnlName", header="Teste Fabio")
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
	@Panel(id="pnlName", header="Teste Fabio")
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
	@Panel(id="pnlName2", header="testando novamente")
	public Date getDateBirth() {
		return dateBirth;
	}

	public void setDateBirth(Date dateBirth) {
		this.dateBirth = dateBirth;
	}

	@SelectOneMenu(selectItems = @SelectItems(var = "placeReregistrations", itemLabel = "placeReregistration.name"), converter = "selectOneUsingObjectConverter", items = true)
	@ListFilter
	@ManyToOne
	@Panel(id="pnlName2", header="testando novamente")
	public PlaceReregistration getPlaceReregistration() {
		
		return placeReregistration;
	}

	public void setPlaceReregistration(
			PlaceReregistration placeReregistration) {
		this.placeReregistration = placeReregistration;
	}

}