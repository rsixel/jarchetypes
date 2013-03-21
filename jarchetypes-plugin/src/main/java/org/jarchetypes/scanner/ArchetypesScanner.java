package org.jarchetypes.scanner;

import java.lang.annotation.Annotation;
import java.util.Map;

import org.jarchetypes.annotation.meta.Archetype;

public abstract class ArchetypesScanner {

	private static Map<Class<? extends Annotation>,ArchetypesScanner> scanners;

	public static void scan(Class<?> archetype) {
			for(Annotation annotation:archetype.getAnnotations()){
				if(annotation.annotationType().isAnnotationPresent(Archetype.class)){
					scanners.get(annotation.annotationType()).doScan(archetype);
				}
			}
	}
	
	public static void register(Class<? extends Annotation> archetypeAnnotation,ArchetypesScanner scanner){
		scanners.put(archetypeAnnotation, scanner);
	}
	
	protected abstract void doScan(Class<?> archetype);
	
}
