package org.jarchetypes.mojo;

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
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.model.Dependency;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.apache.velocity.VelocityContext;
import org.jarchetypes.annotation.CRUD;
import org.jarchetypes.descriptor.ArchetypeDescriptor;
import org.jarchetypes.scanner.ArchetypesScanner;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ConfigurationBuilder;

/**
 * Goal generate archetypes' xhtml.
 * 
 * @goal generate
 * 
 * @phase generate-sources
 */
public class JArchetypesPluginMojo extends AbstractMojo {
	
	private static final String MAIN_MENU_TEMPLATE_NAME = "org/jarchetypes/scanner/templates/mainmenubean.vm";
	
	/**
	 * Location of the file.
	 * 
	 * @parameter expression="${project.build.directory}"
	 * @required
	 */
	private File outputDirectory;

	/**
	 * @parameter default-value="${project}"
	 */
	private MavenProject project;

	/**
	 * @parameter default-value="${project.dependencies}
	 */
	private List<Dependency> dependencies;

	/**
	 * Location of the generated source.
	 * @parameter default-value="${sourceDirectory}
	 * @required
	 */
	private File sourceDirectory;
	
	/**
	 * Location of the generated source.
	 * @parameter default-value="${targetPackage}
	 * @required
	 */
	private String targetPackage;

	public void execute() throws MojoExecutionException {

		Set<URL> urls = getDependenciesURLs();

		URLClassLoader loader = new URLClassLoader(urls.toArray(new URL[0]),
				getClassLoader());

		Reflections reflections = new Reflections(new ConfigurationBuilder()
				.addUrls(urls)
				.setScanners(new TypeAnnotationsScanner(),
						new SubTypesScanner()).addClassLoader(loader));

		Set<String> subTypesOf = reflections.getStore().getSubTypesOf(
				ArchetypesScanner.class.getName());

		for (String subType : subTypesOf) {

			try {
				Class.forName(subType, true, loader);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// TODO: Needs to use meta annotation @Archetype to search other archetypes
		Set<String> archetypes = reflections.getStore().getTypesAnnotatedWith(
				CRUD.class.getName());
		
		VelocityContext context = new VelocityContext();

		context.put("outputPath", outputDirectory.getAbsolutePath());
		context.put("sourceDirectory", sourceDirectory.getAbsolutePath());
		context.put("targetPackage", targetPackage);
		
		List<ArchetypeDescriptor> archetypesDescriptors = new ArrayList<>();
		
		context.put("archetypes", archetypesDescriptors );
		context.put("archetypesDescriptors", archetypesDescriptors );

		for (String archetype : archetypes) {

			try {
				ArchetypesScanner.scan(Class.forName(archetype, true, loader),
						context);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		afterScan(context);
	}

	private void afterScan(VelocityContext context) {
		try {
			String outputPath = (String) context.get("outputPath");
			String sourceDirectory = (String) context.get("sourceDirectory");

			String targetPackagePath = ((String) context.get("targetPackage"))
					.replace(".", File.separator);


			ArchetypesScanner.generate(MAIN_MENU_TEMPLATE_NAME, sourceDirectory + File.separator
					+ targetPackagePath, "MainMenuBuilder", ".java", context);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private ClassLoader getClassLoader() {
		return Thread.currentThread().getContextClassLoader();
	}
 
	private Set<URL> getDependenciesURLs() {
		Set<URL> urls = new HashSet<URL>();

		for (Dependency dependency : dependencies) {
			try {
				urls.add(new URL("file:///"
						+ findDependecyArtifactPath(dependency)));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
		return urls;
	}

	private String findDependecyArtifactPath(Dependency dependency) {
		Set<Artifact> artifacts = project.getDependencyArtifacts();

		for (Artifact artifact : artifacts) {
			if (artifact.getGroupId().equals(dependency.getGroupId())
					&& artifact.getArtifactId().equals(
							dependency.getArtifactId())) {
				return artifact.getFile().getPath();
			}
		}
		return null;
	}
}
