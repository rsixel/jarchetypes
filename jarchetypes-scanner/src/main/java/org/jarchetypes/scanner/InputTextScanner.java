package org.jarchetypes.scanner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

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

	@SuppressWarnings("unchecked")
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
		// filterDescriptor.setBeanName(ArchetypesUtils.uncaptalize(archetype
		// .getSimpleName()) + "SearchBean");

		((List<WidgetDescriptor>) context.get("widgets")).add(descriptor);
		filterDescriptor.setTemplateName(FILTER_TEMPLATE_NAME);

		scanForRequired(archetype, member, descriptor);

		scanForSize(archetype, member, descriptor);

		if (getAnnotation(Filter.class, member) != null)
			((List<FilterDescriptor>) context.get("filters"))
					.add(filterDescriptor);

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
			e.printStackTrace();
		}

		descriptor.setAttribute("required", required + "");

	}

	public void scanForRegex(Class<?> archetype, Member member,
			WidgetDescriptor descriptor) {

		Pattern pattern = (Pattern) getAnnotation(Pattern.class, member);
		String regex = null;
		try {
			boolean isMethodAndHasNotNullAnnotation = member instanceof Method
					&& (((Method) member).isAnnotationPresent(Pattern.class));

			boolean isGetterAndFieldHasNotNullAnnotation = member instanceof Method
					&& ArchetypesUtils.isGetter(member.getName())
					&& ArchetypesUtils.getField(archetype,
							ArchetypesUtils.getFieldName(member))
							.isAnnotationPresent(Pattern.class);

			if (isMethodAndHasNotNullAnnotation
					|| isGetterAndFieldHasNotNullAnnotation) {
				regex = pattern.regexp();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		descriptor.setAttribute("regex", regex);

	}

	public void scanForSize(Class<?> archetype, Member member,
			WidgetDescriptor descriptor) {

		boolean isPresent = false;

		String minlength = Integer.toString(0);
		String maxlength = Integer.toString(Integer.MAX_VALUE);

		Size size = (Size) getAnnotation(Size.class, member);

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
				minlength = size != null && size.min() > 0 ? Integer
						.toString(size.min()) : null;
				maxlength = size != null && size.max() < Integer.MAX_VALUE ? Integer
						.toString(size.max()) : null;
			} else {
				minlength = null;
				maxlength = null;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		descriptor.setAttribute("min", minlength);
		descriptor.setAttribute("max", maxlength);
	}

	public Annotation getAnnotation(Class<? extends Annotation> annotationType,
			Member member) {
		if (member instanceof Field) {
			return ((Field) member).getAnnotation(annotationType);
		} else {
			return ((Method) member).getAnnotation(annotationType);
		}
	}
}
