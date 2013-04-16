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
import org.jarchetypes.annotation.CRUD;
import org.jarchetypes.annotation.meta.Widget;
import org.jarchetypes.widget.FilterDescriptor;
import org.jarchetypes.widget.WidgetDescriptor;

public class CRUDScanner extends ArchetypesScanner {

	private static final String TEMPLATE_NAME = "org/jarchetypes/scanner/templates/crud.vm";
	private static final String SEARCH_TEMPLATE_NAME = "org/jarchetypes/scanner/templates/search.vm";
	private static final String SEARCH_BEAN_NAME = "org/jarchetypes/scanner/templates/searchbean.vm";

	static {
		register(CRUD.class, new CRUDScanner());
	}

	@Override
	protected void doScan(Class<?> archetype, Member member,
			VelocityContext context) {
		CRUD crud = archetype.getAnnotation(CRUD.class);

		context.put("title", crud.title());
		context.put("beanName", archetype.getSimpleName());
		context.put("beanPathName", archetype.getName());

		context.put("widgets", new ArrayList<WidgetDescriptor>());
		context.put("filters", new ArrayList<FilterDescriptor>());
		
		context.put("ScannerUtil", ScannerUtil.class);

		for (Method method : archetype.getMethods()) {
			boolean found = false;
			for (Annotation annotation : method.getAnnotations()) {
				if (annotation.annotationType().isAnnotationPresent(
						Widget.class)) {
					scan(annotation, archetype, method, context);
					found = true;
				}
			}

			if (!found && crud.generateAll() && ScannerUtil.isGetter(method)) {
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

			generate(SEARCH_BEAN_NAME, sourceDirectory + File.separator
					+ targetPackagePath, archetype.getSimpleName()
					+ "SearchBean", ".java", context);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
