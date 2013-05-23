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
import org.jarchetypes.descriptor.SearchColumnDescriptor;
import org.jarchetypes.descriptor.WidgetDescriptor;

public abstract class BaseCRUDScanner extends ArchetypesScanner {


	@Override
	protected void doScan(Class<?> archetype, Member member,
			VelocityContext context) {

		try {
			Annotation annotation = getArchetypeAnnotation(archetype);

			addArchetypeDescriptor(archetype, annotation, context);

			context.put("title", ArchetypesUtils.get(annotation,"title"));
			context.put("beanName", archetype.getSimpleName());
			context.put("beanPathName", archetype.getName());

			context.put("widgets", new ArrayList<WidgetDescriptor>());
			context.put("filters", new ArrayList<FilterDescriptor>());

			context.put("ArchetypesUtils", ArchetypesUtils.class);

			context.put("CRUDBean",
					ArchetypesUtils.uncaptalize(archetype.getSimpleName())
							+ "CRUDBean");

			scanMembers(archetype, context, annotation);

			afterScanMembers(archetype, context, annotation);

			generate(archetype, context);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected abstract void afterScanMembers(Class<?> archetype, VelocityContext context,
			Annotation annotation) throws Exception ;

	protected abstract Annotation getArchetypeAnnotation(Class<?> archetype) ;

	private void scanMembers(Class<?> archetype, VelocityContext context,
			Annotation annotation) throws Exception {
		for (Method method : archetype.getMethods()) {
			boolean found = false;
			for (Annotation a : method.getAnnotations()) {
				if (annotation.annotationType().isAnnotationPresent(
						Widget.class)) {
					scan(annotation, archetype, method, context);
					found = true;
					break;
				}
			}

			if (!found && (Boolean)ArchetypesUtils.get(annotation,"generateAll")
					&& ArchetypesUtils.isGetter(method)) {
				scanByType(method, archetype, context);
			}
		}
	}

	protected abstract void generate(Class<?> archetype, VelocityContext context) ;


	protected String getColumnTitle(VelocityContext context, String column) {

		ArrayList<WidgetDescriptor> widgets = (ArrayList<WidgetDescriptor>) context
				.get("widgets");

		for (WidgetDescriptor widget : widgets) {
			if (widget.getFieldName().equals(column)) {
				return widget.getTitle();
			}
		}

		return column;
	}
}
