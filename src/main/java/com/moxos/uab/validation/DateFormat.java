package com.moxos.uab.validation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;


@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = DateFormatConstraintValidator.class)
public @interface DateFormat {

    String message() default "No existe el usuario";

    String format() default "dd/MM/yyyy";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    boolean optional() default false;
}
