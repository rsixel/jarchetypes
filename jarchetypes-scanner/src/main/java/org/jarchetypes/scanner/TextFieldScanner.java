package org.jarchetypes.scanner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.velocity.VelocityContext;
import org.jarchetypes.annotation.Filter;
import org.jarchetypes.annotation.TextField;
import org.jarchetypes.widget.FilterDescriptor;
import org.jarchetypes.widget.WidgetDescriptor;

public class TextFieldScanner extends ArchetypesScanner {

	private static final String TEMPLATE_NAME = "org/jarchetypes/scanner/templates/textfield.vm";

	static {
		TextFieldScanner scanner = new TextFieldScanner();
		register(TextField.class, scanner);
		registerDefaultType(String.class, scanner);
	}

	@Override
	protected void doScan(Class<?> archetype, Member member, String outputPath,
			VelocityContext context) {

		WidgetDescriptor descriptor = new WidgetDescriptor();

		TextField annotation = (TextField) getAnnotation(TextField.class,
				member);

		String name = ScannerUtil.uncaptalize(ScannerUtil.getFieldName(member));
		descriptor.setTemplateName(TEMPLATE_NAME);
		descriptor.setTitle(annotation != null ? annotation.title() : ScannerUtil.captalize(name) );
		descriptor.setFieldName(name);
		descriptor.setBeanName(ScannerUtil.uncaptalize(archetype.getSimpleName()));
		
		
		FilterDescriptor filterDescriptor = new FilterDescriptor(descriptor);
		filterDescriptor.setBeanName(ScannerUtil.uncaptalize(archetype.getSimpleName())+"SearchBean");
		

		((List<WidgetDescriptor>) context.get("widgets")).add(descriptor);
		
		if(getAnnotation(Filter.class, member)!=null)
			((List<FilterDescriptor>) context.get("filters")).add(filterDescriptor);

		// TODO Auto-generated method stub

	}

	
	private Annotation getAnnotation(
			Class<? extends Annotation> annotationType, Member member) {
		if (member instanceof Field) {
			return ((Field) member).getAnnotation(annotationType);
		} else {
			return ((Method) member).getAnnotation(annotationType);
		}

	}

}
