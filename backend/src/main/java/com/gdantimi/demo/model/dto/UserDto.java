package com.gdantimi.demo.model.dto;


import com.gdantimi.demo.validator.DatabaseConstraintValidationGroup;
import com.gdantimi.demo.validator.sector.ValidSectors;
import com.gdantimi.demo.validator.ValidationOrder;
import lombok.*;

import javax.validation.GroupSequence;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@GroupSequence({UserDto.class, ValidationOrder.class})
public class UserDto {

    private Long id;

    @NotNull
    @Size(min = 3, max = 255)
    private String name;

    @ValidSectors(groups = {DatabaseConstraintValidationGroup.class})
    @NotNull
    @Size(min = 1)
    private List<Long> sectorsIds;

    @AssertTrue
    private boolean termsAgreed;
}
