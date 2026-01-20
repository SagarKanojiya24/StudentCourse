package com.kanojiya.studentcourse.custom.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

import com.kanojiya.studentcourse.custom.validators.MobileNumberValidator;

@Documented
@Constraint(validatedBy = MobileNumberValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidMobileNumber {

    // ❗ Default error message
    String message() default "Mobile number must be 10 digits and start with an even digit between 6 and 9";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
