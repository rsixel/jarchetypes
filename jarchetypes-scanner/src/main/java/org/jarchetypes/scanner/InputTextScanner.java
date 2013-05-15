<<<<<<< HEAD
package org.jarchetypes.scanner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.velocity.VelocityContext;
import org.archetypes.common.ArchetypesUtils;
import org.jarchetypes.annotation.Filter;
import org.jarchetypes.annotation.InputText;
import org.jarchetypes.descriptor.FilterDescriptor;
import org.jarchetypes.descriptor.WidgetDescriptor;

public class InputTextScanner extends ArchetypesScanner {

	private static final String TEMPLATE_NAME = "org/jarchetypes/scanner/templates/inputtext.vm";

	static {
		InputTextScanner scanner = new InputTextScanner();
		register(InputText.class, scanner);
		registerDefaultType(String.class, scanner);
	}


	@Override
	protected void doScan(Class<?> archetype, Member member,
			VelocityContext context) {

		WidgetDescriptor descriptor = new WidgetDescriptor();

		InputText annotation = (InputText) getAnnotation(InputText.class,
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

		((List<WidgetDescriptor>) context.get("widgets")).add(descriptor);

		scanForRequired(archetype, member, descriptor);

		scanForMaxlength(archetype, member, descriptor);

		if (getAnnotation(Filter.class, member) != null)
			((List<FilterDescriptor>) context.get("filters"))
					.add(filterDescriptor);

		// TODO Auto-generated method stub

	}

	public void scanForRequired(Class<?> archetype, Member member,
			WidgetDescriptor descriptor) {

		boolean required = false;
		try {
			boolean isMethodAndHasNotNullAnnotation = member instanceof Method
					&& (((Method) member).isAnnotationPresent(NotNull.class));

			boolean isGetterAndFieldHasNotNullAnnotation = member instanceof Method
					&& ArchetypesUtils.isGetter(member.getName())
					&& ArchetypesUtils.getField(archetype,
							ArchetypesUtils.getFieldName(member))
							.isAnnotationPresent(NotNull.class);

			required = isMethodAndHasNotNullAnnotation
					|| isGetterAndFieldHasNotNullAnnotation;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		descriptor.setAttribute("required", required + "");

	}

	public void scanForMaxlength(Class<?> archetype, Member member,
			WidgetDescriptor descriptor) {

		boolean isPresent = false;

		String maxlength = Integer.toString(Integer.MAX_VALUE);
		
		Size size = (Size) getAnnotation(Size.class,
				member);

		
		try {
			
			boolean isMethodAndHasNotNullAnnotation = member instanceof Method
					&& (((Method) member).isAnnotationPresent(Size.class));

			boolean isGetterAndFieldHasNotNullAnnotation = member instanceof Method
					&& ArchetypesUtils.isGetter(member.getName())
					&& ArchetypesUtils.getField(archetype,
							ArchetypesUtils.getFieldName(member))
							.isAnnotationPresent(Size.class);
		
			isPresent = isMethodAndHasNotNullAnnotation
					|| isGetterAndFieldHasNotNullAnnotation;
			
			
			if (isPresent == true) {
				maxlength = Integer.toString(size != null ? size.max() : 22);
			}else {
				maxlength = Integer.toString(7);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		descriptor.setAttribute("max", maxlength);
	}

	public Annotation getAnnotation(
			Class<? extends Annotation> annotationType, Member member) {
		if (member instanceof Field) {
			return ((Field) member).getAnnotation(annotationType);
		} else {
			return ((Method) member).getAnnotation(annotationType);
		}
	}
}
=======
package org.jarchetypes.scanner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.apache.commons.collections.functors.NotNullPredicate;
import org.apache.velocity.VelocityContext;
import org.archetypes.common.ArchetypesUtils;
import org.jarchetypes.annotation.Filter;
import org.jarchetypes.annotation.InputText;
import org.jarchetypes.descriptor.FilterDescriptor;
import org.jarchetypes.descriptor.WidgetDescriptor;

public class InputTextScanner extends ArchetypesScanner {

	private static final String TEMPLATE_NAME = "org/jarchetypes/scanner/templates/inputtext.vm";
	private static final String FILTER_TEMPLATE_NAME = "org/jarchetypes/scanner/templates/inputtextfilter.vm";

	static {
		InputTextScanner scanner = new InputTextScanner();
		register(InputText.class, scanner);
		registerDefaultType(String.class, scanner);
	}

	@Override
	protected void doScan(Class<?> archetype, Member member,
			VelocityContext context) {

		WidgetDescriptor descriptor = new WidgetDescriptor();

		InputText annotation = (InputText) getAnnotation(InputText.class,
				member);

		String name = ArchetypesUtils.uncaptalize(ArchetypesUtils.getFieldName(member));
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
				.getSimpleName()) + "CRUDBean");

		((List<WidgetDescriptor>) context.get("widgets")).add(descriptor);
		filterDescriptor.setTemplateName(FILTER_TEMPLATE_NAME);

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
					&& ArchetypesUtils.isGetter(member.getName()) && ArchetypesUtils.getField(archetype,ArchetypesUtils.getFieldName(member))
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
>>>>>>> remotes/origin/master
