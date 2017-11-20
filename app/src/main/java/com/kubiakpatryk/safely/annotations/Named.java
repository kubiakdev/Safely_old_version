package com.kubiakpatryk.safely.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by Patryk on 2017-11-16.
 */

@Qualifier
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface Named {

    String value() default "";
}
