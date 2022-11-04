package org.library;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE_USE, ElementType.ANNOTATION_TYPE})
public @interface StartupClass {
    String basePackage() default "";
    String[] basePackages() default {""};
    String[] servicesPackages() default {""};
    String[] controllersPackages() default {""};
    String[] repositoriesPackages() default {""};
}
