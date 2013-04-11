package org.jarchetypes.scanner;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;

public class ScannerUtil {

	public static boolean isGetter(String name) {
		return name.startsWith("get")
				&& name.substring(3, 4).equals(
						name.substring(3, 4).toUpperCase());
	}

	public static boolean isSetter(String name) {
		return name.startsWith("set")
				&& name.substring(3, 4).equals(
						name.substring(3, 4).toUpperCase())
				|| name.startsWith("is")
				&& name.substring(2, 3).equals(
						name.substring(2, 3).toUpperCase());
	}

	public static boolean isGetter(Method method) {
		return isGetter(method.getName());
	}

	public static boolean isSetter(Method method) {
		return isSetter(method.getName());
	}

	
	public static String getFieldName(Member member) {
		if (member instanceof Field) {
			return ((Field) member).getName();
		} else {
			String name = ((Method) member).getName();

			if (ScannerUtil.isGetter(name) || ScannerUtil.isSetter(name)) {
				int delta = 0;
				if(ScannerUtil.isGetter(name) && name.startsWith("is")){
					delta=-1;
				}
				name = name.substring(3+delta, 4+delta).toLowerCase() + name.substring(4+delta);
			}
			return name;
		}
	}
	

	public static String captalize(String name) {
		
		return name.substring(0,1).toUpperCase()+name.substring(1);
	}
	
	public static String uncaptalize(String name) {
		return name.substring(0,1).toLowerCase()+name.substring(1);
	}

}
