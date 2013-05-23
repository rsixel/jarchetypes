package org.jarchetypes.descriptor;

/**
 * @author Fabio Sebastiao
 *
 */
public class ListFilterDescriptor extends FilterDescriptor {

	private static final long serialVersionUID = 1L;
	
	public ListFilterDescriptor(WidgetDescriptor descriptor) {
		super(descriptor);
		assignFrom(descriptor);
	}

}
