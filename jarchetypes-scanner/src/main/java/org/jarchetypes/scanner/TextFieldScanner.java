package org.jarchetypes.scanner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.apache.commons.collections.functors.NotNullPredicate;
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
	protected void doScan(Class<?> archetype, Member member,
			VelocityContext context) {

		WidgetDescriptor descriptor = new WidgetDescriptor();

		TextField annotation = (TextField) getAnnotation(TextField.class,
				member);

		String name = ScannerUtil.uncaptalize(ScannerUtil.getFieldName(member));
		descriptor.setTemplateName(TEMPLATE_NAME);
		descriptor.setTitle(annotation != null ? annotation.title()
				: ScannerUtil.captalize(name));
		descriptor.setFieldName(name);
		descriptor.setBeanName(ScannerUtil.uncaptalize(archetype
				.getSimpleName()));

		descriptor.setFieldType(member instanceof Method ? ((Method) member)
				.getReturnType().getName() : ((Field) member).getType()
				.getName());

		FilterDescriptor filterDescriptor = new FilterDescriptor(descriptor);
		filterDescriptor.setBeanName(ScannerUtil.uncaptalize(archetype
				.getSimpleName()) + "SearchBean");

		((List<WidgetDescriptor>) context.get("widgets")).add(descriptor);

		scanForRequired(archetype, member, descriptor);

		if (getAnnotation(Filter.class, member) != null)
			((List<FilterDescriptor>) context.get("filters"))
					.add(filterDescriptor);

		// TODO Auto-generated method stub

	}

	private void scanForRequired(Class<?> archetype, Member member,
			WidgetDescriptor descriptor) {

		boolean required = false;
		try {
			boolean isMethodAndHasNotNullAnnotation = member instanceof Method
					&& (((Method) member).isAnnotationPresent(NotNull.class));
			
			boolean isGetterAndFieldHasNotNullAnnotation = member instanceof Method
					&& ScannerUtil.isGetter(member.getName()) && ScannerUtil.getField(archetype,ScannerUtil.getFieldName(member))
					.isAnnotationPresent(NotNull.class);
			
			required = isMethodAndHasNotNullAnnotation
					|| isGetterAndFieldHasNotNullAnnotation;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		descriptor.setAttribute("required", required + "");

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
