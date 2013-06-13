package org.jarchetypes.scanner;

import java.io.File;
import java.lang.annotation.Annotation;

import org.apache.velocity.VelocityContext;
import org.archetypes.common.ArchetypesUtils;
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
		
		context.put("managedBean",
				ArchetypesUtils.uncaptalize(archetype.getSimpleName())
						+ "CRUDListBean");
		context.put("pathToBean","item");
		context.put("simpleInput",true);
		
	}

	@Override
	protected void generate(Class<?> archetype, VelocityContext context) {

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
			e.printStackTrace();
		}

	}
	
	@Override
	protected String getPath(Class<?> archetype) {
		return archetype.getSimpleName()+".jsf";
	}

}
