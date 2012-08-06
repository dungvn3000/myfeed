/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.weka.mongodb.annotation;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * The Class Attribute.
 *
 * @author Nguyen Duc Dung
 * @since 8/7/12, 4:11 AM
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = { ElementType.FIELD })
public @interface Attribute {

    public String name();

    public String[] values() default {};

}
