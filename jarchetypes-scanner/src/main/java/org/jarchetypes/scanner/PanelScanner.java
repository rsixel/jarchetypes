package org.jarchetypes.scanner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.velocity.VelocityContext;
import org.archetypes.common.ArchetypesUtils;
import org.jarchetypes.annotation.Panel;
import org.jarchetypes.descriptor.WidgetDescriptor;

public class PanelScanner extends ArchetypesScanner {

	private static final String TEMPLATE_NAME = "org/jarchetypes/scanner/templates/Panel.vm";

	static {
		PanelScanner scanner = new PanelScanner();
		register(Panel.class, scanner);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void doScan(Class<?> archetype, Member member,
			VelocityContext context) {

		WidgetDescriptor descriptor = new WidgetDescriptor();
		Panel panel = (Panel) getAnnotation(Panel.class, member);

		String name = ArchetypesUtils.uncaptalize(ArchetypesUtils
				.getFieldName(member));
		descriptor.setTemplateName(TEMPLATE_NAME);
		// descriptor.setTitle(annotation != null ? annotation.title()
		// : ArchetypesUtils.captalize(name));

		descriptor.setFieldName(name);
		descriptor.setBeanName(ArchetypesUtils.uncaptalize(archetype
				.getSimpleName()));

		attributePanel(panel, descriptor);

		descriptor.setFieldType(member instanceof Method ? ((Method) member)
				.getReturnType().getName() : ((Field) member).getType()
				.getName());
		boolean isPanelPresent = true;
		for (WidgetDescriptor widgetDescriptor : ((List<WidgetDescriptor>) context
				.get("widgets"))) {
			if (descriptor.getAttribute("id").equals(
					widgetDescriptor.getAttribute("id"))) {
				isPanelPresent = false;
			//break;
			}
		}
		
		if (isPanelPresent) {
			((List<WidgetDescriptor>) context.get("widgets")).add(descriptor);
		}
		boolean found = true;
		for (String key : ((HashMap<String, ArrayList<WidgetDescriptor>>) context
				.get("attributePanels")).keySet()) {
			if (key.equals(descriptor.getAttribute("id"))) {
				found = false;
				break;
			}
		}
		if (found) {
			((HashMap<String, WidgetDescriptor>) context
					.get("attributePanels"))
					.put(descriptor.getAttribute("id"),descriptor);
		}
	}

	public Annotation getAnnotation(Class<? extends Annotation> annotationType,
			Member member) {
		if (member instanceof Field) {
			return ((Field) member).getAnnotation(annotationType);
		} else {
			return ((Method) member).getAnnotation(annotationType);
		}
	}

	public void attributePanel(Panel panel, WidgetDescriptor descriptor) {
		if (panel != null) {
			descriptor.setAttribute("id", panel.id());
			descriptor.setAttribute("header", panel.header());
			descriptor.setAttribute("rendered", panel.rendered());
			descriptor.setAttribute("binding", panel.binding());
			descriptor.setAttribute("widgetVar", panel.widgetVar());
			descriptor.setAttribute("footer", panel.footer());
			descriptor.setAttribute("toggleable", panel.toggleable());
			descriptor.setAttribute("toggleSpeed", panel.toggleSpeed());
			descriptor.setAttribute("style", panel.style());
			descriptor.setAttribute("styleClass", panel.styleClass());
			descriptor.setAttribute("collapsed", panel.collapsed());
			descriptor.setAttribute("closable", panel.closable());
			descriptor.setAttribute("closeSpeed", panel.closeSpeed());
			descriptor.setAttribute("visible", panel.visible());
			descriptor.setAttribute("closeTitle", panel.closeTitle());
			descriptor.setAttribute("toggleTitle", panel.toggleTitle());
			descriptor.setAttribute("menuTitle", panel.menuTitle());
			descriptor.setAttribute("toggleOrientation",
					panel.toggleOrientation());
		}
	}

	@Override
	protected String getPath(Class<?> archetype) {
		// TODO Auto-generated method stub
		return null;
	}

}
