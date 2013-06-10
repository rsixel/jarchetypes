package org.jarchetypes.scanner;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.apache.velocity.VelocityContext;
import org.archetypes.common.ArchetypesUtils;
import org.jarchetypes.annotation.ListFilter;
import org.jarchetypes.annotation.SelectOneMenu;
import org.jarchetypes.descriptor.ListFilterDescriptor;
import org.jarchetypes.descriptor.WidgetDescriptor;

/**
 * @author Fabio Sebastiao
 * 
 */
public class SelectOneMenuScanner extends InputTextScanner {

	private static final String TEMPLATE_NAME = "org/jarchetypes/scanner/templates/selectOneMenu.vm";
	private static final String SELECT_ONE_OBJECT_CONVERTER_TEMPLATE_NAME = "org/jarchetypes/scanner/templates/selectOneUsingObjectConverter.vm";

	private static Boolean generated = false;

	static {
		SelectOneMenuScanner scanner = new SelectOneMenuScanner();
		register(SelectOneMenu.class, scanner);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void doScan(Class<?> archetype, Member member,
			VelocityContext context) {
		synchronized (generated) {
			if (!generated) {
				generate(context);
				generated = true;
			}
		}

		WidgetDescriptor descriptor = new WidgetDescriptor();

		SelectOneMenu annotation = (SelectOneMenu) getAnnotation(
				SelectOneMenu.class, member);

		String name = ArchetypesUtils.uncaptalize(ArchetypesUtils
				.getFieldName(member));
		descriptor.setTemplateName(TEMPLATE_NAME);
		descriptor.setTitle(annotation != null ? annotation.title()
				: ArchetypesUtils.captalize(name));

//		ParameterizedType parametezed = (ParameterizedType) (member instanceof Method ? ((Method) member)
//				.getGenericReturnType() : ((Field) member).getGenericType());
		
		Class<?> ob = (member instanceof Method ? ((Method) member)
				.getReturnType() : ((Field) member).getType());
		
//		Class<?> ob = (Class<?>) parametezed.getActualTypeArguments()[0];

		String nameclass = ob.getName();
		int len = ob.getSimpleName().length();
		String out = "";
		out += ob.getSimpleName().substring(0, 1).toLowerCase();
		out += ob.getSimpleName().substring(1, len);
		descriptor.setFieldName(out);
		descriptor.setFieldType(nameclass);
//		ListFilterDescriptor listFilterDescriptor2 = new ListFilterDescriptor(
//				descriptor);
//
		List<ListFilterDescriptor> listFilters = (List<ListFilterDescriptor>) context
				.get("listFilters");
//
//		if (getAnnotation(ListFilter.class, member) != null) {
//			listFilters.add(listFilterDescriptor2);
//		}

		descriptor.setFieldName(name);
		descriptor.setFieldObject(out);
		descriptor.setBeanName(ArchetypesUtils.uncaptalize(archetype
				.getSimpleName()));

		descriptor.setFieldType(member instanceof Method ? ((Method) member)
				.getReturnType().getName() : ((Field) member)
				.getType().getName());
		
//		descriptor.setAttribute("genericType", nameclass);

		ListFilterDescriptor listFilterDescriptor = new ListFilterDescriptor(
				descriptor);

		listFilterDescriptor.setBeanName(ArchetypesUtils.uncaptalize(archetype
				.getSimpleName()) + "SearchBean");

		//((List<WidgetDescriptor>) context.get("widgets")).add(descriptor);

		scanForConverter(archetype, member, descriptor);
		scanForVar(archetype, member, descriptor);
		scanForRequiredMessage(archetype, member, descriptor);
		scanForItems(archetype, member, descriptor);
		scanForStyle(archetype, member, descriptor);
		scanForSelectItems(archetype, member, descriptor);
<<<<<<< HEAD
		
		if(!isPresentAnnotationPanel(member instanceof Method ? ((Method) member)
				.getAnnotations() : ((Field) member).getAnnotations(), context,descriptor)){
			((List<WidgetDescriptor>) context.get("widgets")).add(descriptor);
		}
		
=======

>>>>>>> e2676a14433e09e40f52a966fc1c86bb66c864f4
		if (getAnnotation(ListFilter.class, member) != null)
			listFilters.add(listFilterDescriptor);

	}

	protected void generate(VelocityContext context) {
		try {
			String outputPath = (String) context.get("outputPath");
			String sourceDirectory = (String) context.get("sourceDirectory");

			String targetPackagePath = ((String) context.get("targetPackage"))
					.replace(".", File.separator);

			generate(SELECT_ONE_OBJECT_CONVERTER_TEMPLATE_NAME, sourceDirectory
					+ File.separator + targetPackagePath,
					"SelectOneUsingObjectConverter", ".java", context);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void scanForConverter(Class<?> archetype, Member member,
			WidgetDescriptor descriptor) {
		try {
			SelectOneMenu selectOneMenu = (SelectOneMenu) getAnnotation(
					SelectOneMenu.class, member);
			if (selectOneMenu != null) {
				descriptor.setAttribute("converter", selectOneMenu.converter());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void scanForVar(Class<?> archetype, Member member,
			WidgetDescriptor descriptor) {
		try {
			SelectOneMenu selectOneMenu = (SelectOneMenu) getAnnotation(
					SelectOneMenu.class, member);
			if (selectOneMenu != null) {
				descriptor.setAttribute("var", selectOneMenu.var());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void scanForRequired(Class<?> archetype, Member member,
			WidgetDescriptor descriptor) {
		try {
			SelectOneMenu selectOneMenu = (SelectOneMenu) getAnnotation(
					SelectOneMenu.class, member);
			if (selectOneMenu != null) {
				descriptor.setAttribute("required",
						selectOneMenu.required() ? "true" : "false");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void scanForRequiredMessage(Class<?> archetype, Member member,
			WidgetDescriptor descriptor) {
		try {
			SelectOneMenu selectOneMenu = (SelectOneMenu) getAnnotation(
					SelectOneMenu.class, member);
			if (selectOneMenu != null) {
				descriptor.setAttribute("requiredMessage",
						selectOneMenu.requiredMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void scanForItems(Class<?> archetype, Member member,
			WidgetDescriptor descriptor) {
		try {
			SelectOneMenu selectOneMenu = (SelectOneMenu) getAnnotation(
					SelectOneMenu.class, member);
			if (selectOneMenu != null) {
				descriptor.setAttribute("items", selectOneMenu.items() ? "true"
						: "false");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void scanForStyle(Class<?> archetype, Member member,
			WidgetDescriptor descriptor) {
		try {
			SelectOneMenu selectOneMenu = (SelectOneMenu) getAnnotation(
					SelectOneMenu.class, member);
			if (selectOneMenu != null) {
				descriptor.setAttribute("style", selectOneMenu.style());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void scanForSelectItems(Class<?> archetype, Member member,
			WidgetDescriptor descriptor) {
		try {
			SelectOneMenu selectOneMenu = (SelectOneMenu) getAnnotation(
					SelectOneMenu.class, member);
			if (selectOneMenu != null) {
				descriptor.setAttribute("selectItems",
						selectOneMenu.selectItems() != null ? "true" : "false");
				descriptor.setAttribute("selectItemsVar", selectOneMenu
						.selectItems().var() != null ? selectOneMenu
						.selectItems().var() : "");
				descriptor.setAttribute("itemLabel", selectOneMenu
						.selectItems().itemLabel() != null ? selectOneMenu
						.selectItems().itemLabel() : "");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
