package org.jarchetypes.scanner;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.velocity.VelocityContext;
import org.archetypes.common.ArchetypesUtils;
import org.jarchetypes.annotation.Filter;
import org.jarchetypes.annotation.SelectItems;
import org.jarchetypes.annotation.SelectOneMenu;
import org.jarchetypes.descriptor.FilterDescriptor;
import org.jarchetypes.descriptor.WidgetDescriptor;

/**
 * @author Fabio Sebastiao
 *
 */
public class SelectItemsScanner extends SelectOneMenuScanner {

	private static final String TEMPLATE_NAME = "org/jarchetypes/scanner/templates/selectOneMenu.vm";

	static {
		SelectItemsScanner scanner = new SelectItemsScanner();
		register(SelectItems.class, scanner);
		//registerDefaultType(String.class, scanner);// define o registro padão
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void doScan(Class<?> archetype, Member member,
			VelocityContext context) {

		WidgetDescriptor descriptor = new WidgetDescriptor();

		SelectItems annotation = (SelectItems) getAnnotation(SelectItems.class,
				member);

		String name = ArchetypesUtils.uncaptalize(ArchetypesUtils
				.getFieldName(member));
		descriptor.setTemplateName(TEMPLATE_NAME);
//		descriptor.setTitle(annotation != null ? annotation.title()
//				: ArchetypesUtils.captalize(name));
		descriptor.setFieldName(name);
		descriptor.setBeanName(ArchetypesUtils.uncaptalize(archetype
				.getSimpleName()));

		descriptor.setFieldType(member instanceof Method ? ((Method) member)
				.getReturnType().getName() : ((Field) member).getType()
				.getName());

		FilterDescriptor filterDescriptor = new FilterDescriptor(descriptor);
		filterDescriptor.setBeanName(ArchetypesUtils.uncaptalize(archetype
				.getSimpleName()) + "SearchBean");

		((List<WidgetDescriptor>) context.get("widgets")).add(descriptor);

		scanForVar(archetype, member, descriptor);

		scanForItemLabel(archetype, member, descriptor);

		if (getAnnotation(Filter.class, member) != null)
			((List<FilterDescriptor>) context.get("filters"))
					.add(filterDescriptor);

	}
	
	
	public void scanForVar(Class<?> archetype, Member member,
			WidgetDescriptor descriptor){
		try{
			SelectItems selectItems = (SelectItems) getAnnotation(SelectItems.class,
					member);
			if(selectItems!=null){
				descriptor.setAttribute("var", selectItems.var());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void scanForItemLabel(Class<?> archetype, Member member,
			WidgetDescriptor descriptor){
		try{
			SelectItems selectItems = (SelectItems) getAnnotation(SelectItems.class,
					member);
			if(selectItems!=null){
				descriptor.setAttribute("itemLabel", selectItems.itemLabel());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
