package com.gdantimi.demo.validator.sector;

import com.gdantimi.demo.service.SectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

@Component
public class SectorValidator implements ConstraintValidator<ValidSectors, List<Long>> {

    @Autowired
    private SectorService sectorService;

    @Override
    public void initialize(ValidSectors validSectors) {

    }

    @Override
    public boolean isValid(List<Long> sectorsIds, ConstraintValidatorContext constraintValidatorContext) {
        return sectorsIds.stream()
                .allMatch(sectorId -> sectorService.exists(sectorId));
    }
}
