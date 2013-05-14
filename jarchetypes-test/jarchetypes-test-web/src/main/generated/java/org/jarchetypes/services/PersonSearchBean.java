package org.jarchetypes.services;

import java.util.List;
import java.util.ArrayList;

import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.HashMap;
import org.jarchetypes.service.search.GenericSearchBean;
import org.jarchetypes.test.model.Person;


@Named
@SessionScoped
public class PersonSearchBean  extends GenericSearchBean<Person> {
	
	
	
		java.lang.String name;			
		
	
	public PersonSearchBean() {
		super();
	}
	
	
	public List<Person>  search(){
		List<Person> list = new ArrayList<Person>();
		
		HashMap<String,Object> parameters = new HashMap<String,Object>(); 
		
					parameters.put("name",name);			
				
		
		try {
			list = search(Person.class,parameters);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
			
	}
	
	
		public String get$ScannerUtil.captalize($widget.getFieldName())() {
		return name;
	}

	public void set$ScannerUtil.captalize($widget.getFieldName())(java.lang.String name) {
		this.name = name;
	}
			
}
