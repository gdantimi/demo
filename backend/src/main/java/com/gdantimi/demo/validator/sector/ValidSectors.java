package com.gdantimi.demo.validator.sector;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = SectorValidator.class)
@Target( { ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidSectors {
    String message() default "One or more sector ids are wrong";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
