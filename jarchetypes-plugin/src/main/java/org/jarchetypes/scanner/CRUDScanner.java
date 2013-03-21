package org.jarchetypes.scanner;

import java.io.FileWriter;
import java.io.IOException;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.jarchetypes.annotation.CRUD;

public class CRUDScanner extends ArchetypesScanner {

	static {
		register(CRUD.class, new CRUDScanner());
	}

	@Override
	protected void doScan(Class<?> archetype) {
		CRUD crud = archetype.getAnnotation(CRUD.class);

		/* first, get and initialize an engine */
		VelocityEngine ve = new VelocityEngine();
		ve.init();
		/* next, get the Template */
		Template t = ve.getTemplate("templates/crud.vm");

		/* create a context and add data */
		VelocityContext context = new VelocityContext();
		context.put("title", crud.title());
		/* now render the template into a StringWriter */

		FileWriter writer = null;
		try {
			writer = new FileWriter(archetype.getSimpleName()+".xhtml");

			t.merge(context, writer);

		} catch (IOException e) {
			e.printStackTrace();

		} finally {
			if (writer != null)
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}

	}

}
