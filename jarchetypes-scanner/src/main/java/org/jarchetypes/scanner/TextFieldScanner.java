package org.jarchetypes.scanner;

import java.util.List;

import org.apache.velocity.VelocityContext;
import org.jarchetypes.annotation.CRUD;
import org.jarchetypes.annotation.TextField;
import org.jarchetypes.widget.WidgetDescriptor;

public class TextFieldScanner extends ArchetypesScanner {
	
	
	private static final String TEMPLATE_NAME = "org/jarchetypes/scanner/templates/textfield.vm";
	
	
	static {
		TextFieldScanner scanner = new TextFieldScanner();
		register(TextField.class, scanner);
		registerDefaultType(String.class,scanner);
	}


	@Override
	protected void doScan(Class<?> archetype, String outputPath,VelocityContext context) {
		
		WidgetDescriptor descriptor = new WidgetDescriptor();
		
		descriptor.setTemplateName(TEMPLATE_NAME);
		
		((List<WidgetDescriptor>)context.get("widgets")).add(descriptor);
		
		
		// TODO Auto-generated method stub

	}

}
