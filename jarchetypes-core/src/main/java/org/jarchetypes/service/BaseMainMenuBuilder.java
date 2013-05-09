package org.jarchetypes.service;

import java.util.HashMap;

import javax.faces.component.UIComponentBase;

import org.jarchetypes.descriptor.ArchetypeDescriptor;
import org.primefaces.component.menuitem.MenuItem;
import org.primefaces.component.submenu.Submenu;
import org.primefaces.model.DefaultMenuModel;
import org.primefaces.model.MenuModel;


public class BaseMainMenuBuilder {

	protected MenuModel menuModel = new DefaultMenuModel();
	protected static HashMap<String, ArchetypeDescriptor> archetypes = new HashMap<>();

	protected HashMap<String, UIComponentBase> menuItems = new HashMap<>();

	protected void buildMenuModel() {

		for (String archetype : archetypes.keySet()) {
			ArchetypeDescriptor descriptor = archetypes.get(archetype);

			String path = descriptor.getCategory() + "."
					+ descriptor.getTitle();

			String[] levels = path.split("\\.");

			int l = 0;

			String sep = "";
			String curPath = "";
			UIComponentBase parent = null;
			UIComponentBase menuItem = null;

			for (String level : levels) {
				
				String label=level.equals("") && l==0?"Menu":level;
				
				curPath = curPath + sep + label;
				sep = ".";

				parent = menuItem;
				menuItem = menuItems.get(curPath);

				if (menuItem == null) {

					if (l == levels.length-1) {
						MenuItem item = new MenuItem();

						item.setValue(descriptor.getTitle());
						item.setOutcome(descriptor.getPath());

						menuItem = item;
					} else {
						Submenu submenu = new Submenu();

						submenu.setLabel(label);
						menuItem = submenu;
					}

					menuItems.put(curPath, menuItem);
					if (parent != null) {
						parent.getChildren().add(menuItem);
					} else {
						if (menuItem instanceof Submenu) {
							menuModel.addSubmenu((Submenu) menuItem);
						} else {
							menuModel.addMenuItem((MenuItem) menuItem);
						}
					}
				}

				l++;
			}

		}
	}
}
