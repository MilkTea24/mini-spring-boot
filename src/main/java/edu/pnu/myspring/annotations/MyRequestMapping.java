package edu.pnu.myspring.annotations;

import edu.pnu.myspring.utils.RequestMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MyRequestMapping {
    String value();
    String method() default "GET";
}
