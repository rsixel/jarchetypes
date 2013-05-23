package org.jarchetypes.scanner;

import java.io.File;
import java.lang.annotation.Annotation;

import org.apache.velocity.VelocityContext;
import org.jarchetypes.annotation.CRUDList;

public class CRUDListScanner extends BaseCRUDScanner {
	protected static final String TEMPLATE_NAME = "org/jarchetypes/scanner/templates/crudlist.vm";
	protected static final String CRUD_LIST_BEAN_NAME = "org/jarchetypes/scanner/templates/crudlistbean.vm";
	
	static {
		register(CRUDList.class, new CRUDListScanner());
	}

	@Override
	protected Annotation getArchetypeAnnotation(Class<?> archetype) {
		return archetype.getAnnotation(CRUDList.class);
	}

	@Override
	protected void afterScanMembers(Class<?> archetype,
			VelocityContext context, Annotation annotation) throws Exception {
		try {
			String outputPath = (String) context.get("outputPath");
			String sourceDirectory = (String) context.get("sourceDirectory");

			String targetPackagePath = ((String) context.get("targetPackage"))
					.replace(".", File.separator);

			generate(TEMPLATE_NAME, outputPath, archetype.getSimpleName(),
					".xhtml", context);

			generate(CRUD_LIST_BEAN_NAME, sourceDirectory + File.separator
					+ targetPackagePath, archetype.getSimpleName()
					+ "CRUDListBean", ".java", context);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void generate(Class<?> archetype, VelocityContext context) {
		// TODO Auto-generated method stub

	}
	
	@Override
	protected String getPath(Class<?> archetype) {
		return archetype.getSimpleName()+".jsf";
	}

}
