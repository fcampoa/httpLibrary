package org.library;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
@interface configurationClass {
    String basePackage();
    String[] basePackages();
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface LoadComponents {

}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
@interface autoWired {

}
