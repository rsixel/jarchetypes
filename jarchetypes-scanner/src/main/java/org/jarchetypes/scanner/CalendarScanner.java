package org.jarchetypes.scanner;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.List;
import org.apache.velocity.VelocityContext;
import org.archetypes.common.ArchetypesUtils;
import org.jarchetypes.annotation.Calendar;
import org.jarchetypes.annotation.Filter;
import org.jarchetypes.descriptor.FilterDescriptor;
import org.jarchetypes.descriptor.WidgetDescriptor;

/**
 * @author fabio Sebastiao
 *
 */
public class CalendarScanner extends InputTextScanner {
	private static final String TEMPLATE_NAME = "org/jarchetypes/scanner/templates/calendar.vm";

	static {
		CalendarScanner scanner = new CalendarScanner();
		register(Calendar.class, scanner);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void doScan(Class<?> archetype, Member member,
			VelocityContext context) {
		WidgetDescriptor descriptor = new WidgetDescriptor();

		Calendar annotation = (Calendar) getAnnotation(Calendar.class,
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

	//	((List<WidgetDescriptor>) context.get("widgets")).add(descriptor);

//		scanForRequired(archetype, member, descriptor);
//
		scanForMode(archetype, member, descriptor);
		scanForMaxdate(archetype, member, descriptor);
		scanForMindate(archetype, member, descriptor);
		scanForNavigator(archetype, member, descriptor);
		scanForPattern(archetype, member, descriptor);
		scanForShowOn(archetype, member, descriptor);
		
		if(!isPresentAnnotationPanel(member instanceof Method ? ((Method) member)
				.getAnnotations() : ((Field) member).getAnnotations(), context,descriptor)){
			((List<WidgetDescriptor>) context.get("widgets")).add(descriptor);
		}
		
		if (getAnnotation(Filter.class, member) != null)
			((List<FilterDescriptor>) context.get("filters"))
					.add(filterDescriptor);
	}
	
	public void scanForMode(Class<?> archetype, Member member,
			WidgetDescriptor descriptor){
		try{
			Calendar calendar = (Calendar) getAnnotation(Calendar.class,
					member);
			if(calendar!=null){
				descriptor.setAttribute("mode", calendar.mode());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void scanForMaxdate(Class<?> archetype, Member member,
			WidgetDescriptor descriptor){
		try{
			Calendar calendar = (Calendar) getAnnotation(Calendar.class,
					member);
			if(calendar!=null){
				descriptor.setAttribute("maxdate", calendar.maxdate());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void scanForMindate(Class<?> archetype, Member member,
			WidgetDescriptor descriptor){
		try{
			Calendar calendar = (Calendar) getAnnotation(Calendar.class,
					member);
			if(calendar!=null){
				descriptor.setAttribute("mindate", calendar.mindate());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void scanForNavigator(Class<?> archetype, Member member,
			WidgetDescriptor descriptor){
		try{
			Calendar calendar = (Calendar) getAnnotation(Calendar.class,
					member);
			if(calendar!=null){
				descriptor.setAttribute("navigator", calendar.navigator());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void scanForPattern(Class<?> archetype, Member member,
			WidgetDescriptor descriptor){
		try{
			Calendar calendar = (Calendar) getAnnotation(Calendar.class,
					member);
			if(calendar!=null){
				descriptor.setAttribute("pattern", calendar.pattern());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void scanForShowOn(Class<?> archetype, Member member,
			WidgetDescriptor descriptor){
		try{
			Calendar calendar = (Calendar) getAnnotation(Calendar.class,
					member);
			if(calendar!=null){
				descriptor.setAttribute("showOn", calendar.showOn());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
