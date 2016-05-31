package edu.hm.webtech.utils;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Checks wether all given collections combined contain at least one element.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy=NotCompletelyEmptyValidator.class)
public @interface NotCompletelyEmpty {

    /**
     * Message to be shown when validation fails.
     *
     * @return the message
     */
    String message() default "At least one collection has to contain at least one element.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * Names of the fields that should be checked.
     * @return the fields
     */
    String[] fields();
}
