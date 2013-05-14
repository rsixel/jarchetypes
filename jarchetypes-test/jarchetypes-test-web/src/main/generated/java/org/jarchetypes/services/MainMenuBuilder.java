package org.jarchetypes.services;

import java.io.Serializable;
import java.util.HashMap;

import javax.faces.bean.ApplicationScoped;
import javax.inject.Named;

import org.jarchetypes.widget.ArchetypeDescriptor;


import org.jarchetypes.test.model.Person;			


@Named
@ApplicationScoped
public class MainMenuBuilder extends BaseMainMenuBuilder implements Serializable {
	
	private HashMap<String,ArchetypeDescriptor> archetypes = new HashMap<>();
	
	public MainMenuBuilder(){
		super();
		
		init();
	}
	
	protected void init(){
	
				archetypes.put("org.jarchetypes.test.model.Person",new ArchetypeDescriptor("org.jarchetypes.test.model.Person","","Person"));			
			}	
		
		
}
