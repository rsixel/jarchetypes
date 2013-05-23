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
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.apache.velocity.VelocityContext;
import org.archetypes.common.ArchetypesUtils;
import org.jarchetypes.annotation.CRUD;
import org.jarchetypes.annotation.meta.Widget;
import org.jarchetypes.descriptor.FilterDescriptor;
import org.jarchetypes.descriptor.ListFilterDescriptor;
import org.jarchetypes.descriptor.SearchColumnDescriptor;
import org.jarchetypes.descriptor.WidgetDescriptor;

public class CRUDScanner extends ArchetypesScanner {

	private static final String TEMPLATE_NAME = "org/jarchetypes/scanner/templates/crud.vm";
	private static final String SEARCH_TEMPLATE_NAME = "org/jarchetypes/scanner/templates/search.vm";
	private static final String CRUD_BEAN_NAME = "org/jarchetypes/scanner/templates/crudbean.vm";

	static {
		register(CRUD.class, new CRUDScanner());
	}

	@Override
	protected void doScan(Class<?> archetype, Member member,
			VelocityContext context) {
		
		CRUD crud = archetype.getAnnotation(CRUD.class);
		
		addArchetypeDescriptor(archetype,crud,context);
		

		context.put("title", crud.title());
		context.put("beanName", archetype.getSimpleName());
		context.put("beanPathName", archetype.getName());

		context.put("widgets", new ArrayList<WidgetDescriptor>());
		context.put("listFilters", new ArrayList<ListFilterDescriptor>());
		context.put("filters", new ArrayList<FilterDescriptor>());		
		
		context.put("ArchetypesUtils", ArchetypesUtils.class);
		
		context.put("CRUDBean", ArchetypesUtils.uncaptalize(archetype
				.getSimpleName()) + "CRUDBean");
		
		scanSearchColumns(crud,context);

		for (Method method : archetype.getMethods()) {
			boolean found = false;
			for (Annotation annotation : method.getAnnotations()) {
				if (annotation.annotationType().isAnnotationPresent(
						Widget.class)) {
					scan(annotation, archetype, method, context);
					found = true;
					break;
				}
			}

			if (!found && crud.generateAll() && ArchetypesUtils.isGetter(method)) {
				scanByType(method, archetype, context);
			}
		}

		try {
			String outputPath = (String) context.get("outputPath");
			String sourceDirectory = (String) context.get("sourceDirectory");

			String targetPackagePath = ((String) context.get("targetPackage"))
					.replace(".", File.separator);

			generate(TEMPLATE_NAME, outputPath, archetype.getSimpleName(),
					".xhtml", context);

			generate(SEARCH_TEMPLATE_NAME, outputPath,
					archetype.getSimpleName() + "Search", ".xhtml", context);

			generate(CRUD_BEAN_NAME, sourceDirectory + File.separator
					+ targetPackagePath, archetype.getSimpleName()
					+ "CRUDBean", ".java", context);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void scanSearchColumns(CRUD crud, VelocityContext context) {
		ArrayList<SearchColumnDescriptor> searchColumns = new ArrayList<SearchColumnDescriptor>();
		
		context.put("searchColumns", searchColumns);
		
		for(String column:crud.resultFields()){
			SearchColumnDescriptor descriptor = new SearchColumnDescriptor(column,getColumnTitle(column));
			
			searchColumns.add(descriptor);
		}
		
	}

	private String getColumnTitle(String column) {
		return column;
	}
}
