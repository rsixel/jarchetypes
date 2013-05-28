package org.jarchetypes.descriptor;

import java.lang.reflect.Method;

import org.archetypes.common.ArchetypesUtils;

public class FilterDescriptor extends WidgetDescriptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FilterDescriptor(WidgetDescriptor descriptor) {
		super();
		assignFrom(descriptor);
	}
	
	protected void assignFrom(Object from) {
		
		for(Method m:from.getClass().getMethods()){
			if(ArchetypesUtils.isGetter(m)){
				String name = ArchetypesUtils.getFieldName(m);
				try {
					Method setter = getClass().getMethod("set"+ArchetypesUtils.captalize(name),m.getReturnType());
					
					setter.invoke(this, m.invoke(from));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}
