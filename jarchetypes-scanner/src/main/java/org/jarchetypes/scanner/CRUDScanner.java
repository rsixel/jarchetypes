package org.jarchetypes.scanner;

/*
 * Copyright 2013 Ricardo Girardi Sixel
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.apache.velocity.VelocityContext;
import org.jarchetypes.annotation.CRUD;
import org.jarchetypes.annotation.TextField;
import org.jarchetypes.annotation.meta.Widget;
import org.jarchetypes.widget.WidgetDescriptor;

public class CRUDScanner extends ArchetypesScanner {


	private static final String TEMPLATE_NAME = "org/jarchetypes/scanner/templates/crud.vm";

	static {
		register(CRUD.class, new CRUDScanner());
	}

	@Override
	protected void doScan(Class<?> archetype, String outputPath,
			VelocityContext context) {
		CRUD crud = archetype.getAnnotation(CRUD.class);
		
		
		context.put("title", crud.title());
		
		context.put("widgets", new ArrayList<WidgetDescriptor>());

		
		
		for(Method method:archetype.getMethods()){
			boolean found = false;
			for(Annotation annotation: method.getAnnotations()){
				if(annotation.annotationType().isAnnotationPresent(Widget.class)){
					scan(annotation,archetype,outputPath,context);
					found = true;
				}
			}
			
			if(!found && crud.generateAll()){
				scanByType(method.getReturnType(),archetype,outputPath,context);
			}
		}


		try {
			generate(TEMPLATE_NAME, outputPath
					+ File.separator + archetype.getSimpleName(), context);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	

}
