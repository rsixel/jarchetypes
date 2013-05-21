package org.jarchetypes.scanner;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.apache.velocity.VelocityContext;
import org.archetypes.common.ArchetypesUtils;
import org.jarchetypes.annotation.Filter;
import org.jarchetypes.annotation.SelectOneMenu;
import org.jarchetypes.descriptor.FilterDescriptor;
import org.jarchetypes.descriptor.WidgetDescriptor;

/**
 * @author Fabio Sebastiao
 *
 */
public class SelectOneMenuScanner extends InputTextScanner {

	private static final String TEMPLATE_NAME = "org/jarchetypes/scanner/templates/selectOneMenu.vm";

	static {
		SelectOneMenuScanner scanner = new SelectOneMenuScanner();
		register(SelectOneMenu.class, scanner);
		//registerDefaultType(String.class, scanner);// define o registro pad�o
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void doScan(Class<?> archetype, Member member,
			VelocityContext context) {
		WidgetDescriptor descriptor = new WidgetDescriptor();

		SelectOneMenu annotation = (SelectOneMenu) getAnnotation(SelectOneMenu.class,
				member);

		String name = ArchetypesUtils.uncaptalize(ArchetypesUtils
				.getFieldName(member));
		descriptor.setTemplateName(TEMPLATE_NAME);
		descriptor.setTitle(annotation != null ? annotation.title()
				: ArchetypesUtils.captalize(name));
		
		
		ParameterizedType parametezed = (ParameterizedType)(member instanceof Method ? ((Method) member)
				.getGenericReturnType() : ((Field) member).getGenericType());
		Class<?> ob = (Class<?>) parametezed.getActualTypeArguments()[0];
		
		String nameclass = ob.getName();
		int len = ob.getSimpleName().length();
        String out = "";
        out += ob.getSimpleName().substring( 0, 1 ).toLowerCase();
        out += ob.getSimpleName().substring( 1,len );
		descriptor.setFieldName(out);
		descriptor.setFieldType(nameclass);
		FilterDescriptor filterDescriptor2 = new FilterDescriptor(descriptor);
		if (getAnnotation(Filter.class, member) != null)
			((List<FilterDescriptor>) context.get("filters"))
					.add(filterDescriptor2);
		
		
		descriptor.setFieldName(name);
		descriptor.setFieldObject(out);
		descriptor.setBeanName(ArchetypesUtils.uncaptalize(archetype
				.getSimpleName()));
				
		descriptor.setFieldType(member instanceof Method ? ((Method) member)
				.getGenericReturnType().toString() : ((Field) member).getGenericType().toString());
		
		FilterDescriptor filterDescriptor = new FilterDescriptor(descriptor);
		
		
		filterDescriptor.setBeanName(ArchetypesUtils.uncaptalize(archetype
				.getSimpleName()) + "SearchBean");

		((List<WidgetDescriptor>) context.get("widgets")).add(descriptor);

		scanForConverter(archetype, member, descriptor);
		scanForVar(archetype, member, descriptor);
		scanForRequiredMessage(archetype, member, descriptor);
		scanForItems(archetype, member, descriptor);
		scanForStyle(archetype, member, descriptor);
		scanForSelectItems(archetype, member, descriptor);
		
		if (getAnnotation(Filter.class, member) != null)
			((List<FilterDescriptor>) context.get("filters"))
					.add(filterDescriptor);
		
	}
	
	public void scanForConverter(Class<?> archetype, Member member,
			WidgetDescriptor descriptor){
		try{
			SelectOneMenu selectOneMenu = (SelectOneMenu) getAnnotation(SelectOneMenu.class,
					member);
			if(selectOneMenu!=null){
				descriptor.setAttribute("converter", selectOneMenu.converter());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void scanForVar(Class<?> archetype, Member member,
			WidgetDescriptor descriptor){
		try{
			SelectOneMenu selectOneMenu = (SelectOneMenu) getAnnotation(SelectOneMenu.class,
					member);
			if(selectOneMenu!=null){
				descriptor.setAttribute("var", selectOneMenu.var());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void scanForRequired(Class<?> archetype, Member member,
			WidgetDescriptor descriptor){
		try{
			SelectOneMenu selectOneMenu = (SelectOneMenu) getAnnotation(SelectOneMenu.class,
					member);
			if(selectOneMenu!=null){
				descriptor.setAttribute("required", selectOneMenu.required()?"true":"false");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void scanForRequiredMessage(Class<?> archetype, Member member,
			WidgetDescriptor descriptor){
		try{
			SelectOneMenu selectOneMenu = (SelectOneMenu) getAnnotation(SelectOneMenu.class,
					member);
			if(selectOneMenu!=null){
				descriptor.setAttribute("requiredMessage", selectOneMenu.requiredMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void scanForItems(Class<?> archetype, Member member,
			WidgetDescriptor descriptor){
		try{
			SelectOneMenu selectOneMenu = (SelectOneMenu) getAnnotation(SelectOneMenu.class,
					member);
			if(selectOneMenu!=null){
				descriptor.setAttribute("items", selectOneMenu.items()?"true":"false");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void scanForStyle(Class<?> archetype, Member member,
			WidgetDescriptor descriptor){
		try{
			SelectOneMenu selectOneMenu = (SelectOneMenu) getAnnotation(SelectOneMenu.class,
					member);
			if(selectOneMenu!=null){
				descriptor.setAttribute("style", selectOneMenu.style());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void scanForSelectItems(Class<?> archetype, Member member,
			WidgetDescriptor descriptor){
		try{
			SelectOneMenu selectOneMenu = (SelectOneMenu) getAnnotation(SelectOneMenu.class,
					member);
			if(selectOneMenu!=null){
				descriptor.setAttribute("selectItems", selectOneMenu.selectItems()!=null?"true":"false");
				descriptor.setAttribute("selectItemsVar", selectOneMenu.selectItems().var()!=null?selectOneMenu.selectItems().var():"");
				descriptor.setAttribute("itemLabel", selectOneMenu.selectItems().itemLabel()!=null?selectOneMenu.selectItems().itemLabel():"");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
