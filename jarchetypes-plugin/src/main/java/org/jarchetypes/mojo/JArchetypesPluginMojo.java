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
import java.util.HashSet;
import java.util.Set;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.model.Dependency;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.jarchetypes.annotation.CRUD;
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
 * @phase process-sources
 */
public class JArchetypesPluginMojo extends AbstractMojo {
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
	private Set<Dependency> dependencies;

	public void execute() throws MojoExecutionException {

		Set<URL> urls = getDependenciesURLs();

		Reflections reflections = new Reflections(new ConfigurationBuilder()
				.addUrls(urls).setScanners(new TypeAnnotationsScanner(),
						new SubTypesScanner()));

		Set<Class<?>> archetypes = reflections
				.getTypesAnnotatedWith(CRUD.class);
		
		for(Class<?> archetype : archetypes){
			ArchetypesScanner.scan(archetype);
		}
	}

	private Set<URL> getDependenciesURLs() {
		Set<URL> urls = new HashSet<URL>();

		for (Dependency dependency : dependencies) {
			try {
				urls.add(new URL("file://"
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
