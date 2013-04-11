package org.jarchetypes.widget;

import java.lang.reflect.Method;

import org.jarchetypes.scanner.ScannerUtil;

public class FilterDescriptor extends WidgetDescriptor {

	public FilterDescriptor(WidgetDescriptor descriptor) {
		super();
		assignFrom(descriptor);
	}
	
	protected void assignFrom(Object from) {
		
		for(Method m:from.getClass().getMethods()){
			if(ScannerUtil.isGetter(m)){
				String name = ScannerUtil.getFieldName(m);
				try {
					Method setter = getClass().getMethod("set"+ScannerUtil.captalize(name),m.getReturnType());
					
					setter.invoke(this, m.invoke(from));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
