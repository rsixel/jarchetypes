package org.jarchetypes.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.jarchetypes.annotation.meta.Widget;

/**
 * @author Fabio Sebastiao
 *
 */
@Widget
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.FIELD})
public @interface Panel {
	String id() default "panel_01";
	String header() default "";
	String rendered() default "";
	String binding() default "";
	String widgetVar() default "";
	String footer() default "";
	String toggleable() default "";
	String toggleSpeed() default "";
	String style() default "";
	String styleClass() default "";
	String collapsed() default "";
	String closable() default "";
	String closeSpeed() default "";
	String visible() default "";
	String closeTitle() default "";
	String toggleTitle() default "";
	String menuTitle() default "";
	String toggleOrientation() default "";
	
}
