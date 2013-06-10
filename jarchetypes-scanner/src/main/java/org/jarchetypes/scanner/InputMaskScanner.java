/**
 * 
 */
package org.jarchetypes.scanner;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.List;
import org.apache.velocity.VelocityContext;
import org.archetypes.common.ArchetypesUtils;
import org.jarchetypes.annotation.Filter;
import org.jarchetypes.annotation.InputMask;
import org.jarchetypes.descriptor.FilterDescriptor;
import org.jarchetypes.descriptor.WidgetDescriptor;

/**
 * @author Fabio Sebastiao
 *
 */
public class InputMaskScanner extends InputTextScanner {

	private static final String TEMPLATE_NAME = "org/jarchetypes/scanner/templates/inputMask.vm";

	static {
		InputMaskScanner scanner = new InputMaskScanner();
		register(InputMask.class, scanner);
		//registerDefaultType(String.class, scanner);// define o registro pad�o
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void doScan(Class<?> archetype, Member member,
			VelocityContext context) {
		WidgetDescriptor descriptor = new WidgetDescriptor();

		InputMask annotation = (InputMask) getAnnotation(InputMask.class,
				member);

		String name = ArchetypesUtils.uncaptalize(ArchetypesUtils
				.getFieldName(member));
		descriptor.setTemplateName(TEMPLATE_NAME);
		descriptor.setTitle(annotation != null ? annotation.title()
				: ArchetypesUtils.captalize(name));
		descriptor.setFieldName(name);
		descriptor.setBeanName(ArchetypesUtils.uncaptalize(archetype
				.getSimpleName()));

		descriptor.setFieldType(member instanceof Method ? ((Method) member)
				.getReturnType().getName() : ((Field) member).getType()
				.getName());

		FilterDescriptor filterDescriptor = new FilterDescriptor(descriptor);
		filterDescriptor.setBeanName(ArchetypesUtils.uncaptalize(archetype
				.getSimpleName()) + "SearchBean");

		//((List<WidgetDescriptor>) context.get("widgets")).add(descriptor);

		scanForRequired(archetype, member, descriptor);

		scanForSize(archetype, member, descriptor);
		
		scanForMask(archetype, member, descriptor);
		
		if(!isPresentAnnotationPanel(member instanceof Method ? ((Method) member)
				.getAnnotations() : ((Field) member).getAnnotations(), context,descriptor)){
			((List<WidgetDescriptor>) context.get("widgets")).add(descriptor);
		}

		if (getAnnotation(Filter.class, member) != null)
			((List<FilterDescriptor>) context.get("filters"))
					.add(filterDescriptor);
	}
	
	public void scanForMask(Class<?> archetype, Member member,
			WidgetDescriptor descriptor){
		try{
			InputMask inputMask = (InputMask) getAnnotation(InputMask.class,
					member);
			descriptor.setAttribute("mask", inputMask!=null ? inputMask.mask():"");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
