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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.jarchetypes.annotation.meta.Archetype;

public abstract class ArchetypesScanner {

	private static Map<Class<? extends Annotation>, ArchetypesScanner> scanners = new HashMap<Class<? extends Annotation>, ArchetypesScanner>();

	private static Map<Class<?>, ArchetypesScanner> defaultScanner = new HashMap<Class<?>, ArchetypesScanner>();

	public static void scan(Class<?> archetype, VelocityContext context) {
		for (Annotation annotation : archetype.getAnnotations()) {
			if (annotation.annotationType()
					.isAnnotationPresent(Archetype.class)) {

				ArchetypesScanner scanner = scanners.get(annotation
						.annotationType());
				if (scanner != null)
					scanner.doScan(archetype, null, context);
			}
		}
	}

	public void scan(Annotation annotation, Class<?> archetype, Member member,
			VelocityContext context) {

		scanners.get(annotation.annotationType()).doScan(archetype, member,
				context);

	}

	public static void register(
			Class<? extends Annotation> archetypeAnnotation,
			ArchetypesScanner scanner) {
		scanners.put(archetypeAnnotation, scanner);
	}

	private Object properties;

	protected abstract void doScan(Class<?> archetype, Member member,
			VelocityContext context);

	/**
	 * http://stackoverflow.com/questions/2931516/loading-velocity-template-
	 * inside-a-jar-file
	 * 
	 * @param outputFilename
	 * @throws Exception
	 */
	public void generate(String templatePath, String outputDirectory, String outputFilename, String extension,
			VelocityContext context) throws Exception {
		
		File directory = new File(outputDirectory);
		
		if(!directory.exists()){
			directory.mkdirs();
		}
		
		VelocityEngine ve = new VelocityEngine();
		ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		ve.setProperty("classpath.resource.loader.class",
				ClasspathResourceLoader.class.getName());

		ve.init();

		InputStream input = this.getClass().getClassLoader()
				.getResourceAsStream(templatePath);
		if (input == null) {
			throw new IOException("Template file doesn't exist");
		}

		InputStreamReader reader = new InputStreamReader(input);

		Template template = ve.getTemplate(templatePath, "UTF-8");

		BufferedWriter writer = new BufferedWriter(new FileWriter(new File(
				outputDirectory + File.separator+ outputFilename + extension), false));

		System.out.println("Writing " + outputDirectory + File.separator+ outputFilename + extension);

		template.merge(context, writer);

		writer.flush();
		writer.close();
	}

	protected static void registerDefaultType(Class<String> class1,
			ArchetypesScanner scanner) {
		defaultScanner.put(class1, scanner);
	}

	protected void scanByType(Member member, Class<?> archetype,
			VelocityContext context) {

		ArchetypesScanner scanner = defaultScanner.get(getType(member));
		if (scanner != null)
			scanner.doScan(archetype, member, context);

	}

	protected Class<?> getType(Member member) {

		Class<?> result = null;

		if (member instanceof Method) {
			result = ((Method) member).getReturnType();
		} else {
			result = ((Field) member).getType();
		}

		return result;
	}

}
