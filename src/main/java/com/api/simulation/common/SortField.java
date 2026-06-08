package com.api.simulation.common;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SortField {

    String property();

    boolean nullsLast() default false;
}
