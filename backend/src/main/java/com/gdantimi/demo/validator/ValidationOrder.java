package com.gdantimi.demo.validator;

import javax.validation.GroupSequence;
import javax.validation.groups.Default;

@GroupSequence({Default.class, DatabaseConstraintValidationGroup.class})
public interface ValidationOrder {
}
