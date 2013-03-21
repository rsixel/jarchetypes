package org.jarchetypes.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.jarchetypes.annotation.meta.Archetype;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Archetype
public @interface CRUD {

	String title();
	
	
}
