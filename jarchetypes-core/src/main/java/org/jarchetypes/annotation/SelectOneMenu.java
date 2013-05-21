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
public @interface SelectOneMenu {
	String value() default "";
	String title() default "";
	String converter() default "";
	String var() default "";
	boolean required()  default false;
	String requiredMessage() default "";
	boolean items() default false;
	String style() default "";
	SelectItems  selectItems();
}
