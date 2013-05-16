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
public @interface SelectItems {
	String var() default "";
	String itemLabel() default "";
}
