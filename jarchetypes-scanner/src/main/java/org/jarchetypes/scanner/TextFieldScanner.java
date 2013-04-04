package org.jarchetypes.scanner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.velocity.VelocityContext;
import org.jarchetypes.annotation.TextField;
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

		String name  = uncaptalize(getFieldName(member));
		descriptor.setTemplateName(TEMPLATE_NAME);
		descriptor.setTitle(annotation != null ? annotation.title() : captalize(name) );
		descriptor.setFieldName(name);
		descriptor.setBeanName(uncaptalize(archetype.getSimpleName()));
		

		((List<WidgetDescriptor>) context.get("widgets")).add(descriptor);

		// TODO Auto-generated method stub

	}

	private String captalize(String name) {
		
		return name.substring(0,1).toUpperCase()+name.substring(1);
	}
	
	private String uncaptalize(String name) {
		return name.substring(0,1).toLowerCase()+name.substring(1);
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
